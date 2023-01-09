package com.android.quiz

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.android.quiz.R.drawable
import com.android.quiz.R.font
import com.android.quiz.ui.theme.*

val fontCompose = FontFamily(Font(font.lato))
val fontColor = Color.White.copy(0.8f)

@Composable
fun QuestionActivity() {
    var isClick by remember {
        mutableStateOf(false)
    }
    var isDone by remember {
        mutableStateOf(false)
    }
    var isDown by remember {
        mutableStateOf(true)
    }
    var isPlaying by remember {
        mutableStateOf(false)
    }
    var currentPosition by remember {
        mutableStateOf(
            if (isDone) 1 else 1
        )
    }
    val questionsArray = Constants.getQuestions()
    val question: Question = questionsArray[currentPosition - 1]
    var score by remember {
        mutableStateOf(
            if (isDone) 0 else 0
        )
    }
    var currentProgress by remember {
        mutableStateOf(0.2f)
    }
    var selectedIndex by remember { mutableStateOf(-1) }
    isClick = selectedIndex != -1
    val onItemClick = { index: Int ->
        selectedIndex = index
    }
    val buttonText = if (currentPosition == 5) "Finish" else "Next"
    val buttonTextFirst = if (isPlaying) {
        "restart"
    } else {
        "start"
    }
    val buttonTextSecond = if (isPlaying) {
        if (isDone) {
            "Close"
        } else {
            "Resume"
        }
    } else {
        "Close"
    }
    val modifierOne = if (isDown) {
        Modifier
            .padding(top = 0.dp, bottom = 10.dp, end = 10.dp, start = 10.dp)
            .background(TopColor, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .fillMaxSize()

    } else {
        Modifier
            .padding(top = 0.dp, bottom = 0.dp, end = 10.dp, start = 10.dp)
            .background(TopColor, RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .fillMaxWidth()
    }
    val scoreText = when (score) {
        5 -> {
            "Wow! You're on fire."
        }
        4 -> {
            "Not bad... ;)"
        }
        3 -> {
            "You're doing good :)"
        }
        2 -> {
            "You can do better :("
        }
        1 -> {
            "Bruh..!"
        }
        0 -> {
            "Grab a book maybe :/"
        }
        else -> {
            ""
        }
    }
    val topText = if (isDone) {
        ""
//        ${score}/5
    } else {
        "TAKE A QUICK QUIZ"
    }
    val activity = (LocalContext.current as? Activity)
    var isCloseBtn by remember {
        mutableStateOf(false)
    }
    Constants.getQuestions()
    AnimatedVisibility(visible = isCloseBtn) {
        AlertDialog(
            onDismissRequest = { },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            activity?.finish()
                        },
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 10.dp)
                            .fillMaxWidth()
                            .alpha(1f)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SecondColor
                        ),
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 6.dp
                        )
                    ) {
                        Text(
                            text = "confirm",
                            fontSize = 14.sp,
                            color = SecondaryVariant,
                            fontWeight = FontWeight.Normal,
                            fontFamily = fontCompose
                        )
                    }

                }

            },
            title = {},
            text = {
                Column(
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 0.dp)
                            .clip(RoundedCornerShape(90.dp))
                            .clickable {
                                isCloseBtn = false
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = fontColor,
                            modifier = Modifier
                        )
                    }
                    Text(
                        text = "Are you sure you want to close Quiz?",
                        color = fontColor,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            },
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            backgroundColor = MainColor,
            shape = RoundedCornerShape(20.dp),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isDown,
            enter = expandVertically { 1 },
            exit = shrinkVertically { 0 }
        ) {
            Column(
                modifier = modifierOne,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WelcomeScreen(
                    scoreText = scoreText,
                    isDone = isDone,
                    score = score,
                    isVisible = !isDone,
                    topText = topText,
                    buttonTextFirst = buttonTextFirst,
                    buttonTextSecond = buttonTextSecond,
                    onClickStart = {
                        if (!isPlaying) {
                            isDown = !isDown
                            isDone = false
                        } else {
                            currentPosition = 1
                            selectedIndex = -1
                            currentProgress = 0.2f
                            score = 0
                            isDown = false
                            isDone = false
                            isPlaying = false
                        }
                    },
                    onClickSecond = {
                        if (buttonTextSecond == "Close") {
                            activity?.finish()
                        } else {
                            isDown = false
                        }
                    }
                )
            }
        }
        AnimatedVisibility(
            visible = !isDown,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.TopStart
                    ) {
                        IconButton(
                            onClick = {
                                isCloseBtn = true
                            },
                            modifier = Modifier
                                .padding(start = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close icon",
                                tint = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        ScoreButton(score.toString()) {}
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconButton(
                            onClick = {
                                isDown = !isDown
                            },
                            modifier = Modifier
                                .padding(end = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "Menu icon",
                                tint = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    WantedStars(
                        paddingValues = PaddingValues(top = 0.dp, bottom = 0.dp),
                        count = score,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .shadow(elevation = 80.dp, RoundedCornerShape(20.dp))
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .fillMaxSize()
                        .background(TopColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Text(
//                            "$currentPosition/5",
//                            color = fontColor,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.padding(start = 10.dp)
//                        )
                        TopProgressBar(currentProgress)
                    }
                    Image(
                        painter = painterResource(id = question.image),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .width(300.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "${currentPosition}. ${question.question}",
                        color = fontColor,
                        fontSize = 22.sp,
                        fontFamily = fontCompose,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            top = 20.dp,
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 10.dp
                        ),
                        maxLines = 2
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 0.dp)
                            .fillMaxWidth()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 10.dp)
                                .fillMaxWidth()
                                .clickable {},
                        ) {
                            items(questionsArray.size - 1) { index ->
                                val text = when (index) {
                                    0 -> {
                                        question.optionOne.first
                                    }
                                    1 -> {
                                        question.optionTwo.first
                                    }
                                    2 -> {
                                        question.optionThree.first
                                    }
                                    3 -> {
                                        question.optionFour.first
                                    }
                                    else -> {
                                        ""
                                    }
                                }
                                OptionItem(
                                    text,
                                    index = index,
                                    selected = selectedIndex == index,
                                    onClick = onItemClick,
                                )
                            }
                        }
                        Button(
                            onClick = {
                                if (currentPosition > 0) {
                                    isPlaying = true
                                }
                                if (currentPosition < 5) {
                                    if (selectedIndex + 1 == question.correctAnswer) {
                                        currentProgress += 0.2f
                                        currentPosition++
                                        score++
                                        selectedIndex = -1
                                    } else {
                                        currentProgress += 0.2f
                                        currentPosition++
                                        selectedIndex = -1
                                    }
                                } else {
                                    if (selectedIndex + 1 == question.correctAnswer) {
                                        currentProgress = 0.2f
                                        score++
                                        isDone = true
                                        isDown = true
                                    } else {
                                        currentProgress = 0.2f
                                        isDone = true
                                        isDown = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .alpha(1f),
                            enabled = isClick,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = SecondColor
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 6.dp
                            )
                        ) {
                            Text(
                                text = buttonText,
                                fontSize = 14.sp,
                                color = SecondaryVariant,
                                fontWeight = FontWeight.Normal,
                                fontFamily = fontCompose
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WantedStars(paddingValues: PaddingValues, count: Int) {
    val colors: List<Color>
    when (count) {
        1 -> {
            colors = listOf(
                SecondColor,
                RaisinBlack,
                RaisinBlack,
                RaisinBlack,
                RaisinBlack,
            )
        }
        2 -> {
            colors = listOf(
                SecondColor,
                SecondColor,
                RaisinBlack,
                RaisinBlack,
                RaisinBlack,
            )
        }
        3 -> {
            colors = listOf(
                SecondColor,
                SecondColor,
                SecondColor,
                RaisinBlack,
                RaisinBlack,
            )
        }
        4 -> {
            colors = listOf(
                SecondColor,
                SecondColor,
                SecondColor,
                SecondColor,
                RaisinBlack,
            )
        }
        5 -> {
            colors = listOf(
                SecondColor,
                SecondColor,
                SecondColor,
                SecondColor,
                SecondColor,
            )
        }
        else -> {
            colors = listOf(
                RaisinBlack,
                RaisinBlack,
                RaisinBlack,
                RaisinBlack,
                RaisinBlack,
            )
        }
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .padding(20.dp)
                    .height(1.dp)
            )
        }
        items(1) {
            colors.forEach { colorTint ->
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Yellow score star icon.",
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 0.dp),
                    tint = colorTint
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .padding(20.dp)
                    .height(1.dp)
            )
        }
    }
}

@Composable
fun WelcomeScreen(
    scoreText: String,
    isDone: Boolean,
    score: Int,
    isVisible: Boolean,
    topText: String,
    onClickStart: () -> Unit,
    buttonTextFirst: String,
    buttonTextSecond: String,
    onClickSecond: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .background(TopColor)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        AnimatedVisibility(
            visible = isVisible
        ) {
            Image(
                painter = painterResource(id = drawable.quiz2),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 0.dp, start = 10.dp, end = 10.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = topText,
            color = fontColor,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp),
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )

        val icon = when (score) {
            1 -> {
                painterResource(id = drawable.three)
            }
            2 -> {
                painterResource(id = drawable.three)
            }
            3 -> {
                painterResource(id = drawable.two)
            }
            4 -> {
                painterResource(id = drawable.two)
            }
            5 -> {
                painterResource(id = drawable.one)
            }
            else -> {
                painterResource(id = drawable.three)
            }
        }

        if (isDone) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = scoreText,
                    color = fontColor,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp),
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    WantedStars(
                        paddingValues = PaddingValues(top = 20.dp, bottom = 0.dp),
                        count = score,
                    )
                }
                Image(
                    painter = icon,
                    contentDescription = "reward icon",
                    modifier = Modifier
                        .padding(start = 0.dp, end = 0.dp, top = 20.dp, bottom = 0.dp)
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        if (isVisible) {
            Text(
                text = "WITH US!",
                color = fontColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        }


        Row(
            modifier = Modifier
                .padding(bottom = 10.dp, top = 40.dp, start = 10.dp, end = 10.dp)
        ) {
            Button(
                onClick = { onClickStart.invoke() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SecondColor,
                ),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = buttonTextFirst,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { onClickSecond.invoke() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SecondColor,
                ),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = buttonTextSecond,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OptionItem(text: String, index: Int, selected: Boolean, onClick: (Int) -> Unit) {
    val fontSize by remember {
        mutableStateOf(42.sp)
    }
    val colorListGradient = listOf(
        SecondColor,
//        TopColor,
        ButtonColor,
    )
    val currentFontSizePx = with(LocalDensity.current) { fontSize.toPx() }
    val currentFontSizeDoublePx = currentFontSizePx * 2
    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizeDoublePx,
        animationSpec = infiniteRepeatable(animation = tween(1800, easing = LinearEasing))
    )
    val brush = Brush.linearGradient(
        colorListGradient,
        start = Offset(offset, offset),
        end = Offset(offset + currentFontSizePx, offset + currentFontSizePx),
        tileMode = TileMode.Mirror
    )
    var padding by remember {
        mutableStateOf(8.dp)
    }
    val paddingValue by animateDpAsState(targetValue = padding)
    padding = when (selected) {
        true -> {
            12.dp
        }
        else -> {
            8.dp
        }
    }
    Text(
        text = text,
        modifier = Modifier
            .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 4.dp)
            .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
            .clickable {
                onClick.invoke(index)
            }
            .background(
                if (selected) SecondColor else Color(0xFFBDC3DA),
                RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .border(
                width = if (selected) 1.dp else Dp.Unspecified,
                brush = brush,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(paddingValue),
        textAlign = TextAlign.Center,
        color = if (selected) fontColor else RaisinBlack,
        fontFamily = fontCompose,
        fontSize = 16.sp,
        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
    )
}

@Composable
fun ScoreButton(text: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            enabled = true,
            onClick = {
                onClick.invoke()
            },
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, bottom = 0.dp, top = 0.dp)
                .width(80.dp)
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = TopColor
            ),
            shape = RoundedCornerShape(bottomEnd = 60.dp, bottomStart = 60.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 6.dp
            )
        ) {
            Text(text = text, color = SecondaryVariant, fontFamily = fontCompose, fontSize = 36.sp)
        }
    }
}

@Composable
fun TopProgressBar(progress: Float) {
    val currentProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    LinearProgressIndicator(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        color = ButtonColor,
        backgroundColor = ButtonColor.copy(0.4f),
        progress = currentProgress
    )
}