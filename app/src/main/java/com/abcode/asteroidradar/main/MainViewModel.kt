package com.abcode.asteroidradar.main

import androidx.lifecycle.*
import com.abcode.asteroidradar.repository.AsteroidRepository
import com.abcode.asteroidradar.repository.FilterEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: AsteroidRepository
) : ViewModel() {

    private val _filter = MutableLiveData(FilterEnum.ALL)

    private var _errorFetching = MutableLiveData<Boolean>(false)
    val errorFetching: LiveData<Boolean>
        get() = _errorFetching

    val asteroids = Transformations.switchMap(_filter) {
        repository.getAsteroids(filter = it).asLiveData()
    }

    fun filterAsteroids(newValue: FilterEnum) {
        _filter.postValue(newValue)
    }

    init {
        viewModelScope.launch {
            val result = repository.refreshAsteroids()
            _errorFetching.value = !result
        }
    }
}