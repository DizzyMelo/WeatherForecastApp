package com.study.weatherforecastapp.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.weatherforecastapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.study.weatherforecastapp.model.Unit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {
    private val _units = MutableStateFlow<List<Unit>>(emptyList())
    val units = _units.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty()) {
                        _units.value = it
                    }
                }
        }
    }

    fun addUnit(unit: Unit) = viewModelScope.launch { repository.addUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }
}