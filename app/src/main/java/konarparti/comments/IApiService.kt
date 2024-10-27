package konarparti.comments;

import konarparti.comments.Models.Comment
import konarparti.comments.Models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

public interface IApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<Comment>>
}
