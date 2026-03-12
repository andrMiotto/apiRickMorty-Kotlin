package com.example.rickandmortyapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmortyapi.model.Character
import com.example.rickandmortyapi.repository.CharacterRepository
import com.example.rickandmortyapi.ui.theme.RickAndMortyAPITheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAPITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RickMortyScreen()
                }
            }
        }
    }
}

@Composable
fun RickMortyScreen() {
    val repository = CharacterRepository()
    val scope = rememberCoroutineScope()

    var character by remember { mutableStateOf<Character?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var idPersonagem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        if (isLoading) {
            CircularProgressIndicator()
        } else {
            character?.let {
                AsyncImage(
                    model = it.image,
                    contentDescription = "Foto de ${it.name}",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Nome: ${it.name}", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Espécie: ${it.species}")
                Text(text = "Origem: ${it.origin.locationName}")
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        TextField(
            value = idPersonagem,
            onValueChange = { novoTexto -> idPersonagem = novoTexto },
            label = { Text("ID do Personagem") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )


        Button(
            onClick = {
                val idParaBuscar = idPersonagem.toIntOrNull()
                if (idParaBuscar != null) {
                    scope.launch {
                        isLoading = true
                        val result = repository.fetchCharacter(idParaBuscar)
                        character = result
                        isLoading = false
                    }
                }
            },
            enabled = idPersonagem.isNotEmpty()
        ) {
            Text("Buscar")
        }
    }
}