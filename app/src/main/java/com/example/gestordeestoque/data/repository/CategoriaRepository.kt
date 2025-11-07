package com.example.gestordeestoque.data.repository
import com.example.gestordeestoque.data.models.Categoria
import com.example.gestordeestoque.data.models.Produto

class CategoriaRepository {
    private val categorias = mutableListOf(
        Categoria(id = "aa", nome = "Categoria1", descricao = "Descrição da categoria 1"),
        Categoria(id = "bb", nome = "Categoria2",descricao = "Descrição da categoria 2"),
        Categoria(id = "cc", nome = "Categoria3",descricao = "Descrição da categoria 3"),
        Categoria(id = "dd", nome = "Categoria4",descricao = "Descrição da categoria 4")
    )
    fun getAll(): List<Categoria> = categorias
    fun deleteProduto(categoria:Categoria){
        categorias.remove(categoria)
    }
    fun addProduto(categoria: Categoria){
        categorias.add(categoria)
    }

}

