package com.example.gestordeestoque.presentation.screen.categoria

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeestoque.data.models.Categoria
import com.example.gestordeestoque.data.repository.CategoriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriaViewModel(context: Context) : ViewModel() {

    private val repository = CategoriaRepository()

    private val _categoria = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias: StateFlow<List<Categoria>> = _categoria.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        carregarCategorias()
    }

    private fun carregarCategorias() {
        viewModelScope.launch {
            _categoria.value = repository.getCategorias()
        }
    }

    val filtrados: StateFlow<List<Categoria>> =
        combine(categorias, query) { lista, q ->
            if (q.isBlank()) lista
            else lista.filter { it.nome.contains(q, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, _categoria.value)

    fun onQueryChange(nova: String) {
        _query.value = nova
    }

    fun getById(id: String): Categoria? = _categoria.value.find { it.id == id }


    fun adicionarCategoria(nome: String, descricao: String) {
        viewModelScope.launch {
            val novaCategoria = Categoria(nome = nome, descricao = descricao)
            repository.addCategoria(novaCategoria)
            carregarCategorias()
        }
    }

    fun atualizarCategoria(id: String, nome: String, descricao: String) {
        viewModelScope.launch {
            val categoriaAtualizada = Categoria(id = id, nome = nome, descricao = descricao)
            repository.updateCategoria(id, categoriaAtualizada)
            carregarCategorias()
        }
    }

    fun deletarCategoria(id: String) {
        viewModelScope.launch {
            repository.deleteCategoria(id)
            carregarCategorias()
        }
    }
}