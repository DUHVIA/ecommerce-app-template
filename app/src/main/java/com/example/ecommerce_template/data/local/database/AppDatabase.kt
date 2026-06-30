package com.example.ecommerce_template.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommerce_template.data.local.dao.ProductDao
import com.example.ecommerce_template.data.local.entity.ProductEntity

/**
 * Base de datos principal de la aplicación.
 * Registramos [ProductEntity] como tabla y exponemos [ProductDao].
 */
@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
