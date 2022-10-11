package uz.mobiler.ktorexample.networking

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import uz.mobiler.ktorexample.models.UserData

class ApiServiceImpl(private val client: HttpClient) : ApiService {

    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    override suspend fun getUserById(id: String): Response<UserData> {
        return try {
            val response = client.get<UserData> {
                url("$BASE_URL/users/$id")
            }
            return Response.Success(response)
        } catch (e: RedirectResponseException) { // 3xx
            Response.Error(e.response.status.description)
        } catch (e: ClientRequestException) { // 4xx
            Response.Error(e.response.status.description)
        } catch (e: ServerResponseException) { // 5xx
            Response.Error(e.response.status.description)
        }
    }

    override suspend fun getUsers(): Response<List<UserData>> {
        return try {
            val response = client.get<List<UserData>> {
                url("$BASE_URL/users")
            }
            return Response.Success(response)
        } catch (e: RedirectResponseException) { // 3xx
            Response.Error(e.response.status.description)
        } catch (e: ClientRequestException) { // 4xx
            Response.Error(e.response.status.description)
        } catch (e: ServerResponseException) { // 5xx
            Response.Error(e.response.status.description)
        }
    }
}