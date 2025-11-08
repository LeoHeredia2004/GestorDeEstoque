package com.example.gestordeestoque.presentation.screen.cadastro

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gestordeestoque.data.models.Categoria
import com.example.gestordeestoque.presentation.screen.categoria.CategoriaViewModel
import com.example.gestordeestoque.presentation.screen.produto.ProdutoViewModel

class cadastro {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Cadastro(
        onVoltarParaPrincipal: () -> Unit,
        produtoVM: ProdutoViewModel,
        categoriaVM: CategoriaViewModel
    ) {
        var nome by remember { mutableStateOf("") }
        var preco by remember { mutableStateOf("") }
        var quantidade by remember { mutableStateOf("") }


        val categorias by categoriaVM.categorias.collectAsStateWithLifecycle()
        var expanded by remember { mutableStateOf(false) }
        var categoriaSelecionada by remember { mutableStateOf<Categoria?>(null) }

        val context = LocalContext.current

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Cadastrar Novo Produto", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = preco,
                onValueChange = { preco = it },
                label = { Text("Preço (ex: 29.90)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nome.isNotBlank() && preco.isNotBlank() && quantidade.isNotBlank() && categoriaSelecionada != null) {
                        produtoVM.adicionarProduto(
                            nome = nome,
                            preco = preco.toDoubleOrNull() ?: 0.0,
                            qtd = quantidade.toIntOrNull() ?: 0,
                            categoriaId = categoriaSelecionada!!.id
                        )
                        Toast.makeText(context, "$nome cadastrado!", Toast.LENGTH_SHORT).show()
                        onVoltarParaPrincipal()
                    } else {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Produto")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onVoltarParaPrincipal, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Voltar")
            }
        }
    }
}