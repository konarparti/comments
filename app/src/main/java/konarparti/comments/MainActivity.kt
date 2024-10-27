package konarparti.comments

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import konarparti.comments.Models.Comment
import konarparti.comments.Models.Post
import konarparti.comments.ViewModels.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PostScreen()
                }
            }
        }
    }
}

@Composable
fun PostScreen(viewModel: MainViewModel = viewModel()) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Button(onClick = { viewModel.loadPosts() }) {
            Text(stringResource(R.string.load_posts))
        }

        if (viewModel.selectedPostId != null && viewModel.comments.isNotEmpty()) {
            Button(onClick = { viewModel.selectedPostId = null }) {
                Text(stringResource(R.string.back_to_posts))
            }

            Text(stringResource(R.string.comments), modifier = Modifier.padding(top = 16.dp))
            LazyColumn {
                items(viewModel.comments.size) { index ->
                    CommentItem(viewModel.comments[index])
                }
            }
        } else {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)) {
                items(viewModel.posts.size) { index ->
                    val post = viewModel.posts[index]
                    PostItem(post, onClick = {
                        viewModel.loadComments(post.id)
                    })
                }
            }
        }
    }
}



@Composable
fun PostItem(post: Post, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = post.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.body,
            fontSize = 14.sp,
            color = Color.Gray,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
            )
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = comment.name, fontWeight = FontWeight.Bold)
        Text(text = comment.body, modifier = Modifier.padding(top = 4.dp))
        Text(text = stringResource(R.string.comment_author, comment.email), style = androidx.compose.material.MaterialTheme.typography.caption)
    }
}

