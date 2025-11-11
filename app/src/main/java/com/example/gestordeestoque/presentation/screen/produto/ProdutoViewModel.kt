package com.example.gestordeestoque.presentation.screen.produto

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeestoque.data.local.AppDatabase // Importe o AppDatabase
import com.example.gestordeestoque.data.models.Produto
import com.example.gestordeestoque.data.repository.ProdutoRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProdutoViewModel(context: Context) : ViewModel() {
    private val dao = AppDatabase.getInstance(context.applicationContext).produtoDao()
    private val repository = ProdutoRepository(dao)

    private val _produtos = MutableStateFlow<List<Produto>>(emptyList())
    val produtos: StateFlow<List<Produto>> = _produtos.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { listaVindaDoRoom ->
                _produtos.value = listaVindaDoRoom
            }
        }

        viewModelScope.launch {
            repository.syncFirebaseToLocal()
        }
    }

    val filtrados: StateFlow<List<Produto>> =
        combine(produtos, query) { lista, q ->
            if (q.isBlank()) lista
            else lista.filter { it.nome.contains(q, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, _produtos.value)

    fun onQueryChange(nova: String) {
        _query.value = nova
    }

    fun getById(id: String): Produto? = _produtos.value.find { it.id == id }

    fun deletarProduto(id: String) {
        viewModelScope.launch {
            repository.deleteProduto(id)
        }
    }

    fun adicionarProduto(nome: String, preco: Double, qtd: Int, categoriaId: String) {
        viewModelScope.launch {
            val novoProduto = Produto(
                nome = nome,
                preco = preco,
                quantidade = qtd,
                categoriaId = categoriaId,
                dataCadastro = Timestamp.now()
            )
            repository.addProduto(novoProduto)
        }
    }

    fun atualizarProduto(produtoId: String, nome: String, preco: Double, qtd: Int, categoriaId: String, dataCadastro: Timestamp) {
        viewModelScope.launch {
            val produtoAtualizado = Produto(
                id = produtoId,
                nome = nome,
                preco = preco,
                quantidade = qtd,
                categoriaId = categoriaId,
                dataCadastro = dataCadastro
            )
            repository.updateProduto(produtoId, produtoAtualizado)
        }
    }
}