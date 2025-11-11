package com.example.gestordeestoque.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaEntity(
    @PrimaryKey
    val id: String,
    val nome: String,
    val descricao: String
)