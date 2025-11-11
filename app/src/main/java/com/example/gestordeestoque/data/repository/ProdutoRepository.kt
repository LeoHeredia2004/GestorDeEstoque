package com.example.gestordeestoque.data.repository

import com.example.gestordeestoque.data.local.ProdutoDao
import com.example.gestordeestoque.data.local.toEntity
import com.example.gestordeestoque.data.local.toModel
import com.example.gestordeestoque.data.models.Produto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await


class ProdutoRepository(private val produtoDao: ProdutoDao) {

    private val db = FirebaseFirestore.getInstance()
    private val colecaoProdutos = db.collection("produtos")

    fun getAll(): Flow<List<Produto>> {
        return produtoDao.getAllProdutos().map { listaDeEntities ->
            // Converte a lista de Entity para Model
            listaDeEntities.map { it.toModel() }
        }
    }

    suspend fun syncFirebaseToLocal() {
        try {

            val querySnapshot = colecaoProdutos.get().await()
            val produtosDoFirebase = querySnapshot.documents.mapNotNull { document ->
                val produto = document.toObject<Produto>()
                produto?.copy(id = document.id)
            }

            produtoDao.insertAll(produtosDoFirebase.map { it.toEntity() })

        } catch (e: Exception) {

        }
    }


    suspend fun addProduto(produto: Produto) {
        try {
            val docRef = colecaoProdutos.add(produto).await()
            produtoDao.insert(produto.toEntity(generatedId = docRef.id))
        } catch (e: Exception) {
        }
    }

    suspend fun deleteProduto(produtoId: String) {
        try {
            colecaoProdutos.document(produtoId).delete().await()
            produtoDao.deleteById(produtoId)
        } catch (e: Exception) {
        }
    }

    suspend fun updateProduto(produtoId: String, produto: Produto) {
        try {
            colecaoProdutos.document(produtoId).set(produto).await()
            produtoDao.insert(produto.toEntity())
        } catch (e: Exception) {
        }
    }
}