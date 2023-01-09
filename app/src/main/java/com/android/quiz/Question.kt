package com.android.quiz

data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    val optionOne: Pair<String, Int>,
    val optionTwo: Pair<String, Int>,
    val optionThree: Pair<String, Int>,
    val optionFour: Pair<String, Int>,
    val correctAnswer: Int,
)