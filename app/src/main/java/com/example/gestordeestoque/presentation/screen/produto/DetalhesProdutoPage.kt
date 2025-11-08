package com.example.gestordeestoque.presentation.screen.produto

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gestordeestoque.data.models.Categoria
import com.example.gestordeestoque.presentation.screen.categoria.CategoriaViewModel

public class DetalhesProdutoPage() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Detalhes(
        produtoId: String,
        onVoltarParaProduto: () -> Unit,
        produtoVM: ProdutoViewModel,
        categoriaVM: CategoriaViewModel
    ) {
        val produtoOriginal = produtoVM.getById(produtoId)

        if (produtoOriginal == null) {
            Text(text = "Produto não encontrado.", modifier = Modifier.padding(16.dp))
            return
        }

        var nome by remember { mutableStateOf(produtoOriginal.nome) }
        var preco by remember { mutableStateOf(produtoOriginal.preco.toString()) }
        var quantidade by remember { mutableStateOf(produtoOriginal.quantidade.toString()) }

        val categorias by categoriaVM.categorias.collectAsStateWithLifecycle()
        var expanded by remember { mutableStateOf(false) }

        var categoriaSelecionada by remember {
            mutableStateOf(categorias.find { it.id == produtoOriginal.categoriaId })
        }

        LaunchedEffect(categorias) {
            if (categoriaSelecionada == null) {
                categoriaSelecionada = categorias.find { it.id == produtoOriginal.categoriaId }
            }
        }


        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Editar Produto", fontSize = 24.sp)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = preco,
                onValueChange = { preco = it },
                label = { Text("Preço (ex: 29.90)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = quantidade,
                onValueChange = { quantidade = it },
                label = { Text("Quantidade (ex: 15)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = categoriaSelecionada?.nome ?: "Selecione uma Categoria",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.nome) },
                            onClick = {
                                categoriaSelecionada = categoria
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nome.isNotBlank() && preco.isNotBlank() && quantidade.isNotBlank() && categoriaSelecionada != null) {
                        produtoVM.atualizarProduto(
                            produtoId = produtoOriginal.id, // O ID original
                            nome = nome,
                            preco = preco.toDoubleOrNull() ?: 0.0,
                            qtd = quantidade.toIntOrNull() ?: 0,
                            categoriaId = categoriaSelecionada!!.id,
                            dataCadastro = produtoOriginal.dataCadastro // Mantém a data original
                        )
                        Toast.makeText(context, "$nome atualizado!", Toast.LENGTH_SHORT).show()
                        onVoltarParaProduto()
                    } else {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Alterações")
            }

            Spacer(Modifier.height(8.dp))


            Button(
                onClick = {
                    produtoVM.deletarProduto(produtoOriginal.id)
                    Toast.makeText(context, "${produtoOriginal.nome} excluído!", Toast.LENGTH_SHORT).show()
                    onVoltarParaProduto()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Excluir Produto")
            }

            Spacer(Modifier.height(8.dp))

            Button(onClick = onVoltarParaProduto, modifier = Modifier.fillMaxWidth()) {
                Text("Voltar")
            }
        }
    }
}
