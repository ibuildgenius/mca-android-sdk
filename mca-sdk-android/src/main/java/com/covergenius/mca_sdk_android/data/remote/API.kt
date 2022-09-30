import retrofit2.http.POST

interface API {
    @POST("v1/sdk/initialize")
    suspend fun getPr(token: String) {

    }
}