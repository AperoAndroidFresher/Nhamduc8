package com.example.nhamngocduc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.nhamngocduc.ui.editor.EditScreen
import com.example.nhamngocduc.ui.playlist.PlaylistScreen
import com.example.nhamngocduc.ui.theme.NhamNgocDucTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NhamNgocDucTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                PlaylistScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.background(Color.Black).padding(16.dp)
    ) {
        ButtonComponent(
            modifier = Modifier.padding(horizontal = 56.dp, vertical = 16.dp),
            myText = "Composable_1",
            myFont = FontWeight.SemiBold,
            myFontColor = Color.White,
            buttonColor = Color.Magenta,
            buttonShapes = MaterialTheme.shapes.extraLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonComponent(
            modifier = Modifier.padding(horizontal = 56.dp, vertical = 16.dp),
            myText = "Composable_1",
            myFont = FontWeight.SemiBold,
            myFontColor = Color.Yellow,
            buttonColor = MaterialTheme.colorScheme.primary,
            buttonShapes = MaterialTheme.shapes.extraLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))

        ButtonComponent(
            modifier = Modifier.padding(8.dp),
            myText = "Composable_1",
            myFont = FontWeight.SemiBold,
            myFontColor = Color.Cyan,
            myFontStyle = MaterialTheme.typography.titleSmall,
            buttonColor = MaterialTheme.colorScheme.inversePrimary,
            buttonShapes = MaterialTheme.shapes.small,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = painterResource(R.drawable.model),
                        contentDescription = ""
                    )
                    Box(
                        modifier = Modifier
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                        )
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color.Green),
                        )
                    }

                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Eimi Fukada",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Active",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Light,
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    myText: String,
    myFont: FontWeight = FontWeight.Bold,
    myFontStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    myFontColor: Color,
    buttonColor: Color,
    buttonShapes: Shape
) {
    Surface(
        color = buttonColor,
        shape = buttonShapes
    ) {
        Text(
            modifier = modifier,
            text = myText,
            style = myFontStyle,
            fontWeight = myFont,
            color = myFontColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NhamNgocDucTheme {
        Greeting("Android")
    }
}