package com.example.gestordeestoque.data.repository

import com.example.gestordeestoque.data.models.Categoria
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class CategoriaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val colecaoCategorias = db.collection("categorias") //

    suspend fun getCategorias(): List<Categoria> {
        return try {
            val querySnapshot = colecaoCategorias.get().await()
            querySnapshot.documents.mapNotNull { document ->
                val categoria = document.toObject<Categoria>()
                categoria?.copy(id = document.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addCategoria(categoria: Categoria) {
        try {
            colecaoCategorias.add(categoria).await()
        } catch (e: Exception) {

        }
    }

    suspend fun updateCategoria(categoriaId: String, categoria: Categoria) {
        try {
            colecaoCategorias.document(categoriaId).set(categoria).await()
        } catch (e: Exception) {
        }
    }

    suspend fun deleteCategoria(categoriaId: String) {
        try {
            colecaoCategorias.document(categoriaId).delete().await()
        } catch (e: Exception) {
        }
    }
}

