package ar.com.astroapp.repository

import ar.com.astroapp.data.model.Apod

/**
 * Created by Fernando Moreno on 21/8/2021.
 */
interface ApodRepository {

    suspend fun getAstronomyApod(): Apod
}