package com.juanetoh.mostaza.api
import com.juanetoh.mostaza.api.model.NewPostRequest
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.auth.Identity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.juanetoh.mostaza.api.model.Response as AppResponse

class ApiClient @Inject constructor(
    private val identity: Identity,
    private val client: MostazaService,
) {
    suspend fun getPosts(): AppResponse<List<Post>> = suspendCoroutine { cont ->
        client.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val body = response.body()
                body?.let {
                    cont.resume(AppResponse.success(body))
                } ?: run {
                    cont.resume(AppResponse.failure("Empty body"))
                }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                cont.resume(AppResponse.failure("Exception when getting posts: ${t.message}"))
            }
        })
    }

    suspend fun submitPost(postContent: String?): AppResponse<String> {
        if (postContent.isNullOrEmpty()) {
            return AppResponse.failure("No post data")
        }

        return suspendCoroutine { cont ->
            val usingIdentity = identity.currentIdentity.value
            client
                .submitPost(
                    NewPostRequest(
                        content = postContent,
                        authorId = usingIdentity.userId,
                        authorName = usingIdentity.userName,
                    )
                )
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        cont.resume(AppResponse.success("Posted"))
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        cont.resume(AppResponse.failure("Error: ${t.message}"))
                    }
                })
        }
    }
}