package com.study.weatherforecastapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.study.weatherforecastapp.model.Favorite
import com.study.weatherforecastapp.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    // CRUD for favorite table

    @Query("SELECT * FROM favorite_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite_tbl WHERE city = :city")
    suspend fun getFavoriteById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    // CRUD for settings table
    @Query("SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE FROM settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)

}