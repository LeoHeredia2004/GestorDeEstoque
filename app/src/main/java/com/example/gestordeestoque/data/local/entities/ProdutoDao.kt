package com.example.gestordeestoque.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gestordeestoque.data.local.entities.ProdutoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {


    @Query("SELECT * FROM produtos ORDER BY nome")
    fun getAllProdutos(): Flow<List<ProdutoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(produtos: List<ProdutoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(produto: ProdutoEntity)

    @Query("DELETE FROM produtos WHERE id = :id")
    suspend fun deleteById(id: String)
}