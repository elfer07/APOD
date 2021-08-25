package ar.com.astroapp.data.remote

import ar.com.astroapp.application.Constants
import ar.com.astroapp.data.model.Apod
import ar.com.astroapp.repository.WebService

/**
 * Created by Fernando Moreno on 21/8/2021.
 */
class RemoteApodDataSource (private val webService: WebService) {

    suspend fun getAstronomyApod(): Apod = webService.getAstronomyApod(Constants.API_KEY)
}