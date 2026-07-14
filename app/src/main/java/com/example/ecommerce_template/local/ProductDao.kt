package com.example.ecommerce_template.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY name COLLATE NOCASE ASC")
    fun observeAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    fun observeById(id: String): Flow<ProductEntity?>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProductEntity?

    @Query("SELECT COUNT(*) FROM products")
    suspend fun count(): Int

    @Query("SELECT IFNULL(MAX(cachedAt), 0) FROM products")
    fun observeLastSyncAt(): Flow<Long>

    @Query("SELECT IFNULL(MAX(cachedAt), 0) FROM products")
    suspend fun lastSyncAt(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clear()

    @Transaction
    suspend fun replaceAll(products: List<ProductEntity>, now: Long) {
        clear()
        upsertAll(products.map { it.copy(cachedAt = now) })
    }
}
