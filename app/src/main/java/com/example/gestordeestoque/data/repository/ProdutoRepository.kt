package com.example.gestordeestoque.data.repository
import com.example.gestordeestoque.data.models.Produto
class ProdutoRepository {
    private val produtos = mutableListOf(
        Produto(id = "aa", nome = "Teste"),
        Produto(id = "bb", nome = "Batata"),
        Produto(id = "cc", nome = "Corsa"),
        Produto(id = "dd", nome = "Onix")
    )


    fun getAll(): List<Produto> = produtos

    fun deleteProduto(produto:Produto){
        produtos.remove(produto)
    }
    fun addProduto(produto: Produto){
        produtos.add(produto)
    }

}