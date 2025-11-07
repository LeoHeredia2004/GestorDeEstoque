package com.example.gestordeestoque.presentation.screen.categoria


import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestordeestoque.presentation.screen.produto.ProdutoViewModel

import kotlin.Unit;

public class DetalhesCategoriaPage() {

    @Composable
    fun Detalhes( categoriaId: String, onVoltarParaProduto: () -> Unit,categoriaVM: CategoriaViewModel) {
        val categoria = categoriaVM.getById(categoriaId)

        if (categoria == null) {
            Text(
                text = "Produto não encontrado.",
                modifier = Modifier.padding(16.dp)
            )
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Detalhes do Produto", fontSize = 24.sp)
            Spacer(Modifier.height(16.dp))
            Text(text = "ID: ${categoria.id}")
            Text(text = "Nome: ${categoria.nome}")
            Text(text = "Descricao: ${categoria.descricao}")
            Spacer(Modifier.height(24.dp))
            Button(onClick = onVoltarParaProduto) {
                Text("Voltar")
            }
            Button(onClick = {}) {
                Text("Excluir")
            }
        }
    }
}
