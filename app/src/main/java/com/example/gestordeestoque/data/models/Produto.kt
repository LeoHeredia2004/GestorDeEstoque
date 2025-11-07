package com.example.gestordeestoque.data.models

import com.google.firebase.Timestamp

data class Produto(
    val id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val quantidade: Int = 0,
    val preco: Double = 0.0,
    val dataCadastro: Timestamp = Timestamp.now(),
    val categoriaId: String = ""
)