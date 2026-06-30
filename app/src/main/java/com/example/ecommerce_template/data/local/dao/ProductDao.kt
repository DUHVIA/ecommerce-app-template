package com.example.ecommerce_template.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce_template.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para la entidad [ProductEntity].
 * Define las operaciones de base de datos permitidas.
 */
@Dao
interface ProductDao {

    /**
     * Inserta un producto si no existe, o lo actualiza si ya existe.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(product: ProductEntity)

    /**
     * Inserta o actualiza una lista de productos de una sola vez.
     * Útil para cuando recibimos datos actualizados de la API.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(products: List<ProductEntity>)

    /**
     * Obtiene todos los productos de la base de datos local.
     * Retorna un Flow para observar cambios en tiempo real, lo que es
     * ideal para la arquitectura Offline-First.
     */
    @Query("SELECT * FROM products")
    fun getAllProductsFlow(): Flow<List<ProductEntity>>
}
