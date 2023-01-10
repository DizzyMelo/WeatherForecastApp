package com.study.weatherforecastapp.repository

import com.study.weatherforecastapp.data.WeatherDao
import com.study.weatherforecastapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val dao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = dao.getFavorites()
    suspend fun getFavorite(city: String): Favorite = dao.getFavoriteById(city)
    suspend fun addFavorite(favorite: Favorite) = dao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = dao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = dao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = dao.deleteFavorite(favorite)
}