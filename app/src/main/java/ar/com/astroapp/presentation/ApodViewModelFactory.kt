package ar.com.astroapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.com.astroapp.repository.ApodRepository

/**
 * Created by Fernando Moreno on 25/8/2021.
 */
class ApodViewModelFactory(private val repo: ApodRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApodRepository::class.java).newInstance(repo)
    }
}