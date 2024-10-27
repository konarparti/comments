package konarparti.comments.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import konarparti.comments.Models.Comment
import konarparti.comments.Models.Post
import konarparti.comments.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set
    var comments by mutableStateOf<List<Comment>>(emptyList())
        private set

    var selectedPostId by mutableStateOf<Int?>(null)

    fun loadPosts() {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getPosts()
            if (response.isSuccessful) {
                posts = response.body() ?: emptyList()
            }
        }
    }

    fun loadComments(postId: Int) {
        viewModelScope.launch {
            selectedPostId = postId
            val response = RetrofitInstance.api.getComments(postId)
            if (response.isSuccessful) {
                comments = response.body() ?: emptyList()
            }
        }
    }
}