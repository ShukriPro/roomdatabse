package com.shukri.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.shukri.roomdatabase.ui.theme.RoomdatabaseTheme
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.shukri.roomdatabase.database.DatabaseProvider
import com.shukri.roomdatabase.database.User
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Initialize the database
    private val db by lazy {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {}
        lifecycleScope.launch {
            val userDao = DatabaseProvider.getDatabase(this@MainActivity).userDao()
            userDao.insertUser(User(age = 23, name = "Shukri Ali"))
            val users = userDao.getAllUsers()
            users.forEach { println(it.name) }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomdatabaseTheme {
        Greeting("Android")
    }
}