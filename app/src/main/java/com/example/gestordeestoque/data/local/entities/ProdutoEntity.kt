package com.example.gestordeestoque.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtos")
data class ProdutoEntity(
    @PrimaryKey
    val id: String,
    val nome: String,
    val descricao: String,
    val quantidade: Int,
    val preco: Double,
    val dataCadastro: Long,
    val categoriaId: String
)