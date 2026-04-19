package com.example.chatbotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier

// Data model for each message in the chat
data class Message(
    val text: String,
    val isUser: Boolean
)

// Main entry point of the application
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

// Main screen composable function
@Composable
fun MainScreen() {

    // Stores the current text typed by the user
    var userInput by remember { mutableStateOf("") }

    // Stores the list of messages displayed in the chat
    var messages by remember {
        mutableStateOf(
            listOf(
                Message("Hi I'm your chatbot, how can I help you?", false)
            )
        )
    }

    // UI elements for the chat interface
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Display the list of messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(messages) { msg ->

                val isUser = msg.isUser

                // Container to align messages left/right
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    // Message bubble with rounded corners
                    Text(
                        text = msg.text,
                        fontSize = 16.sp,
                        color = if (isUser) Color.Black else Color.DarkGray,
                        modifier = Modifier
                            .background(
                                color = if (isUser) Color(0xFFD0E7FF) else Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input field for user to type messages
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Type something...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Send button to send the user's message
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    // Prevent empty messages
                    if (userInput.isNotBlank()) {
                        // Add the user's message to the list
                        messages = messages + Message(userInput, true)
                        userInput = "" // Clear the input field
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}