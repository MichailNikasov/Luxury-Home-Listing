package com.nikasov.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nikasov.data.local.entity.HomeEntity
import com.nikasov.data.local.entity.HomeWithFavorite

@Dao
interface HomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHomes(homes: List<HomeEntity>)

    @Query("DELETE FROM homes")
    suspend fun clearHomes()

    @Transaction
    @Query("SELECT * FROM homes")
    fun getHomesWithFavorites(): PagingSource<Int, HomeWithFavorite>

}