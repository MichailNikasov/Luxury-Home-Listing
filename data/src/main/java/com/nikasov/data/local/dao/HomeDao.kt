package com.nikasov.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikasov.data.local.entity.HomeEntity


@Dao
interface HomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHomes(homes: List<HomeEntity>)

    @Query("SELECT * FROM homes WHERE id = :id")
    suspend fun getHomeById(id: Int): HomeEntity?

    @Query("DELETE FROM homes")
    suspend fun clearHomes()

    @Query("SELECT * FROM homes")
    fun getHomes(): PagingSource<Int, HomeEntity>

    @Query("SELECT * FROM homes WHERE isFavorite = 1")
    fun getFavoriteHomes(): PagingSource<Int, HomeEntity>

    @Query("UPDATE homes SET isFavorite = NOT isFavorite WHERE id = :homeId")
    suspend fun toggleFavorite(homeId: Int)

}