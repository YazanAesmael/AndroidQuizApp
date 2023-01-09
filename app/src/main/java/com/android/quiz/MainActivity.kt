package com.android.quiz

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.quiz.ui.theme.QuizTheme
import com.android.quiz.ui.theme.TopColor
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTheme {
                Navigation()
                val activity = (LocalContext.current as? Activity)
                var doubleBackToExitPressedOnce = false
                fun onBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        activity?.finish()
                        return
                    } else {
                        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT)
                            .show()
                        doubleBackToExitPressedOnce = true
                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackToExitPressedOnce = false
                        }, 2000)
                    }
                }
                BackHandler {
                    onBackPressed()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable(
            route = "splash_screen"
        ) {
            SplashScreen(navController)
        }
        composable(
            route = "main_screen"
        ) {
            QuestionActivity()
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        delay(400L)
        isVisible = true
        delay(1000L)
        navController.navigate("main_screen") {
            popUpTo(0)
            isVisible = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TopColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = expandVertically { 1 },
                exit = fadeOut(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.quiz1),
                        contentDescription = "Quiz logo",
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Box(modifier = Modifier.padding(22.dp)) {
                Text(
                    text = "",
                    color = fontColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
