package com.nikasov.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikasov.data.local.entity.FavoriteHomeEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteHomeEntity)

    @Query("DELETE FROM favorite_homes WHERE id = :homeId")
    suspend fun removeFavoriteById(homeId: Int)

    @Query("SELECT id FROM favorite_homes WHERE id = :homeId LIMIT 1")
    suspend fun isFavorite(homeId: Int): Boolean

}