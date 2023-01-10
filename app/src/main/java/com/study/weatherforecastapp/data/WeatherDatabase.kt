package com.study.weatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.weatherforecastapp.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
