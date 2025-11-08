package com.example.gestordeestoque.data.repository
import com.example.gestordeestoque.data.models.Produto

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ProdutoRepository {

    private val db = FirebaseFirestore.getInstance();

    private val colecaoProdutos = db.collection("produtos");

    fun getAll(): Flow<List<Produto>> {
        return colecaoProdutos.snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.mapNotNull { document ->
                    val produto = document.toObject<Produto>()
                    produto?.copy(id = document.id)
                }
            }
    }

    suspend fun addProduto(produto: Produto) {
        try {
            colecaoProdutos.add(produto)
        } catch (e: Exception) {
        }
    }

    suspend fun deleteProduto(produto: String) {
        try {
            colecaoProdutos.document(produto).delete().await()
        } catch (e: Exception) {

        }
    }

    suspend fun updateProduto(produtoId: String, produto: Produto) {
        try {
            colecaoProdutos.document(produtoId).set(produto).await()
        } catch (e: Exception) {
        }
    }

}