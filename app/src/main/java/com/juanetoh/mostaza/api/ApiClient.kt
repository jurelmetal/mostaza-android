package com.juanetoh.mostaza.api
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.juanetoh.mostaza.api.model.NewPostRequest
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.auth.Identity
import com.juanetoh.mostaza.auth.IdentityData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.juanetoh.mostaza.api.model.Response as AppResponse

class ApiClient @Inject constructor(
    private val identity: Identity
) {
    private val logger by lazy { Logger.getLogger("ApiClient") }

    @OptIn(ExperimentalSerializationApi::class)
    val client: MostazaService by lazy {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val contentType = MediaType.parse("application/json") ?: throw Exception()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(client)
            .build()
        retrofit.create(MostazaService::class.java)
    }

    suspend fun getPosts() = suspendCoroutine<List<Post>> { cont ->
        client.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val body = response.body()
                body?.let { cont.resume(body) } ?: run { cont.resume(listOf()) }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                cont.resumeWithException(t)
            }
        })
    }

    suspend fun submitPost(postContent: String?): AppResponse<*> {
        if (postContent.isNullOrEmpty()) {
            return AppResponse.Failure("No post data")
        }

        return suspendCoroutine { cont ->
            val usingIdentity = identity.currentIdentity.value
            client
                .submitPost(
                    NewPostRequest(
                        content = postContent,
                        authorId = usingIdentity.userName
                    )
                )
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        cont.resume(AppResponse.Success("Posted"))
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        cont.resume(AppResponse.Failure("Error: ${t.message}"))
                    }
                })
        }
    }

    companion object {
        const val BASE_URL = "https://gin-production-1651.up.railway.app/"
    }
}