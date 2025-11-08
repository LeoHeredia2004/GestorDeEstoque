package com.example.gestordeestoque.presentation.screen.categoria


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

public class DetalhesCategoriaPage() {

    @Composable
    fun Detalhes(
        categoriaId: String,
        onVoltarParaProduto: () -> Unit,
        categoriaVM: CategoriaViewModel
    ) {

        val categoriaOriginal = categoriaVM.getById(categoriaId)

        if (categoriaOriginal == null) {
            Text(
                text = "Categoria não encontrada.",
                modifier = Modifier.padding(16.dp)
            )
            return
        }

        var nome by remember { mutableStateOf(categoriaOriginal.nome) }
        var descricao by remember { mutableStateOf(categoriaOriginal.descricao) }

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Editar Categoria", fontSize = 24.sp)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da Categoria") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nome.isNotBlank()) {
                        categoriaVM.atualizarCategoria(
                            id = categoriaOriginal.id,
                            nome = nome,
                            descricao = descricao
                        )
                        Toast.makeText(context, "$nome atualizada!", Toast.LENGTH_SHORT).show()
                        onVoltarParaProduto()
                    } else {
                        Toast.makeText(context, "O nome não pode ser vazio", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Alterações")
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    categoriaVM.deletarCategoria(categoriaOriginal.id)
                    Toast.makeText(context, "${categoriaOriginal.nome} excluída!", Toast.LENGTH_SHORT).show()
                    onVoltarParaProduto()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Excluir Categoria")
            }

            Spacer(Modifier.height(8.dp))

            Button(onClick = onVoltarParaProduto, modifier = Modifier.fillMaxWidth()) {
                Text("Voltar")
            }
        }
    }
}