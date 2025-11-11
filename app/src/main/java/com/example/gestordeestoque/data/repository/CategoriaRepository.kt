package com.example.gestordeestoque.data.repository

import com.example.gestordeestoque.data.local.CategoriaDao
import com.example.gestordeestoque.data.local.toEntity
import com.example.gestordeestoque.data.local.toModel
import com.example.gestordeestoque.data.models.Categoria
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

// 1. Modifique o construtor para receber o DAO
class CategoriaRepository(private val categoriaDao: CategoriaDao) {

    private val db = FirebaseFirestore.getInstance()
    private val colecaoCategorias = db.collection("categorias")

    fun getCategorias(): Flow<List<Categoria>> {
        return categoriaDao.getAllCategorias().map { listaDeEntities ->
            // Converte a lista de Entity para Model
            listaDeEntities.map { it.toModel() }
        }
    }

    suspend fun syncFirebaseToLocal() {
        try {
            val querySnapshot = colecaoCategorias.get().await()
            val categoriasDoFirebase = querySnapshot.documents.mapNotNull { document ->
                val categoria = document.toObject<Categoria>()
                categoria?.copy(id = document.id)
            }

            categoriaDao.insertAll(categoriasDoFirebase.map { it.toEntity() })

        } catch (e: Exception) {
        }
    }

    suspend fun addCategoria(categoria: Categoria) {
        try {
            val documentReference = colecaoCategorias.add(categoria).await()
            val categoriaComId = categoria.copy(id = documentReference.id)

            categoriaDao.insert(categoriaComId.toEntity())
        } catch (e: Exception) {
        }
    }

    suspend fun updateCategoria(categoriaId: String, categoria: Categoria) {
        try {
            colecaoCategorias.document(categoriaId).set(categoria).await()
            categoriaDao.insert(categoria.toEntity())
        } catch (e: Exception) {
        }
    }

    suspend fun deleteCategoria(categoriaId: String) {
        try {
            colecaoCategorias.document(categoriaId).delete().await()
            categoriaDao.deleteById(categoriaId)
        } catch (e: Exception) {
        }
    }
}

