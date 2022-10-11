package uz.mobiler.ktorexample.networking

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import uz.mobiler.ktorexample.models.UserData

interface ApiService {
    suspend fun getUserById(id: String): Response<UserData>
    suspend fun getUsers(): Response<List<UserData>>

    companion object {
        val client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}