package ar.com.astroapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.astroapp.core.Result
import ar.com.astroapp.data.model.Apod
import ar.com.astroapp.repository.ApodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by Fernando Moreno on 21/8/2021.
 */
class ApodViewModel (
    private val repo: ApodRepository
) : ViewModel() {

    private val apod = MutableStateFlow<Result<Apod>>(Result.Loading())

    fun fetchApod() = viewModelScope.launch {
        kotlin.runCatching {
            repo.getAstronomyApod()
        }.onSuccess {
            apod.value = Result.Success(it)
        }.onFailure { e->
            apod.value = Result.Failure(Exception(e))
        }
    }

    fun getApod(): StateFlow<Result<Apod>> = apod

}