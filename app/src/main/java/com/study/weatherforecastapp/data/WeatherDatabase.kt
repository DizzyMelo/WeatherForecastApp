package com.study.weatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.study.weatherforecastapp.model.Favorite
import com.study.weatherforecastapp.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
