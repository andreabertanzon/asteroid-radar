package com.abcode.asteroidradar.main

import androidx.lifecycle.*
import com.abcode.asteroidradar.Asteroid
import com.abcode.asteroidradar.PictureOfDay
import com.abcode.asteroidradar.repository.AsteroidRepository
import com.abcode.asteroidradar.repository.FilterEnum
import com.abcode.asteroidradar.repository.LocalInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val asteroidRepository: AsteroidRepository,
    private val localInfoRepository: LocalInfoRepository
) : ViewModel() {

    private val _filter = MutableLiveData(FilterEnum.ALL)

    private var _loadStatus = MutableLiveData<LoadStatus>(LoadStatus.Idle)
    val loadStatus: LiveData<LoadStatus>
        get() = _loadStatus

    private var _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    val asteroids = Transformations.switchMap(_filter) {
        asteroidRepository.getAsteroids(filter = it).asLiveData()
    }

    private var _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    fun filterAsteroids(newValue: FilterEnum) {
        _filter.postValue(newValue)
    }

    fun fetchData() {
        _loadStatus.value = LoadStatus.Loading

        viewModelScope.launch {

            /*
            * It is pointless to fetch every time the app is opened. By saving on the db if I already
            * fetched or not today, I can prevent doing excessive requests to the db
            * */
            val fetched = localInfoRepository.getLocalInfo()

            if (!fetched) {
                val result = asteroidRepository.refreshAsteroids()
                localInfoRepository.upsertLocalInfo()

                if (result) {
                    _loadStatus.value = LoadStatus.Idle
                } else {
                    _loadStatus.value = LoadStatus.Error
                }
            }

            val result = asteroidRepository.refreshPictureOfDay()
            if (result != null) {
                _pictureOfTheDay.value = result!!
                _loadStatus.value = LoadStatus.Idle
            } else {
                _loadStatus.value = LoadStatus.Error
            }
        }
    }

    fun onAsteroidClicked(asteroid:Asteroid){
        _navigateToDetail.value = asteroid
    }

    fun navigationDone(){
        _navigateToDetail.value = null
    }
}

sealed class LoadStatus {
    object Loading : LoadStatus()
    object Error : LoadStatus()
    object Idle : LoadStatus()
}