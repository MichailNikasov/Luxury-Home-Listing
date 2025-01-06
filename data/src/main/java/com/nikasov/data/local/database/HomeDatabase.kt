package com.nikasov.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikasov.data.local.dao.HomeDao
import com.nikasov.data.local.entity.HomeEntity

@Database(entities = [HomeEntity::class], version = 1, exportSchema = false)
abstract class HomeDatabase : RoomDatabase() {
    abstract fun homeDao(): HomeDao
}