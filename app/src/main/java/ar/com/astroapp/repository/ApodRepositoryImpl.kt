package ar.com.astroapp.repository

import ar.com.astroapp.data.model.Apod
import ar.com.astroapp.data.remote.RemoteApodDataSource

/**
 * Created by Fernando Moreno on 21/8/2021.
 */
class ApodRepositoryImpl (
    private val dataSourceRemote: RemoteApodDataSource
) : ApodRepository {

    override suspend fun getAstronomyApod(): Apod {
        return dataSourceRemote.getAstronomyApod()
    }
}