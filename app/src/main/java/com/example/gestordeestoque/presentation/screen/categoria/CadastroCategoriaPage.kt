package com.example.gestordeestoque.presentation.screen.categoria

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class CadastroCategoriaPage {

    @Composable
    fun CadastroCategoria(
        onVoltar: () -> Unit,
        categoriaVM: CategoriaViewModel
    ) {
        var nome by remember { mutableStateOf("") }
        var descricao by remember { mutableStateOf("") }

        val context = LocalContext.current

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nova Categoria", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da Categoria") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nome.isNotBlank()) {
                        categoriaVM.adicionarCategoria(
                            nome = nome,
                            descricao = descricao
                        )
                        Toast.makeText(context, "$nome cadastrada!", Toast.LENGTH_SHORT).show()
                        onVoltar()
                    } else {
                        Toast.makeText(context, "O nome não pode ser vazio", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Categoria")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Voltar")
            }
        }
    }
}