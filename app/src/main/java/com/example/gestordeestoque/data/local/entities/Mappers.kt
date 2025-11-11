package com.example.gestordeestoque.data.local

import com.example.gestordeestoque.data.local.entities.CategoriaEntity
import com.example.gestordeestoque.data.local.entities.ProdutoEntity
import com.example.gestordeestoque.data.models.Categoria
import com.example.gestordeestoque.data.models.Produto
import com.google.firebase.Timestamp

// --- Mappers de Categoria ---

fun Categoria.toEntity(): CategoriaEntity {
    return CategoriaEntity(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao
    )
}

fun CategoriaEntity.toModel(): Categoria {
    return Categoria(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao
    )
}

// --- Mappers de Produto ---

fun Produto.toEntity(generatedId: String? = null): ProdutoEntity {
    return ProdutoEntity(
        id = generatedId ?: this.id,
        nome = this.nome,
        descricao = this.descricao,
        quantidade = this.quantidade,
        preco = this.preco,
        dataCadastro = this.dataCadastro.toDate().time,
        categoriaId = this.categoriaId
    )
}

fun ProdutoEntity.toModel(): Produto {
    val timestamp = Timestamp(this.dataCadastro / 1000,
        ((this.dataCadastro % 1000) * 1000000).toInt())

    return Produto(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        quantidade = this.quantidade,
        preco = this.preco,
        dataCadastro = timestamp,
        categoriaId = this.categoriaId
    )
}