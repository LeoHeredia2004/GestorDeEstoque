package com.example.gestordeestoque.presentation.screen.categoria

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestordeestoque.data.models.Categoria
import com.example.gestordeestoque.data.models.Produto
import com.example.gestordeestoque.data.repository.CategoriaRepository
import com.example.gestordeestoque.data.repository.ProdutoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class CategoriaViewModel(context: Context): ViewModel() {


    private val _categoria = MutableStateFlow(CategoriaRepository().getAll())
    val categorias: StateFlow<List<Categoria>> = _categoria.asStateFlow()
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // lista filtrada em tempo real
    val filtrados: StateFlow<List<Categoria>> =
        combine(categorias, query) { lista, q ->
            if (q.isBlank()) lista
            else lista.filter { it.nome.contains(q, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, _categoria.value)

    fun onQueryChange(nova: String) {
        _query.value = nova
    }

    fun getById(id: String): Categoria? = _categoria.value.find { it.id == id }
}