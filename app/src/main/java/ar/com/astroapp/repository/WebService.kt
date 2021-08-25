package ar.com.astroapp.repository

import ar.com.astroapp.data.model.Apod
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Fernando Moreno on 21/8/2021.
 */
interface WebService {

    @GET("apod")
    suspend fun getAstronomyApod(@Query("api_key") apiKey: String): Apod
}