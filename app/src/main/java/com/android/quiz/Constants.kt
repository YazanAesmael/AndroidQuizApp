package com.android.quiz

object Constants {
    fun getQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()

        val queOne = Question(
            1,
            "What element on the periodic table does 'O' represent?",
            R.drawable.q1,
            Pair("Oxygen", 1),
            Pair("Ozone", 2),
            Pair("Helium", 3),
            Pair("Potassium", 4),
            1
        )

        val queTwo = Question(
            2,
            "What's the name of the river that runs through Egypt?",
            R.drawable.q2,
            Pair("The Nile", 1),
            Pair("The Amazon", 2),
            Pair("Seine", 3),
            Pair("Ganges", 4),
            1
        )

        val queThree = Question(
            3,
            "What breed of dog does Queen Elizabeth II famously own?",
            R.drawable.q3,
            Pair("Poodle", 1),
            Pair("Bulldog", 2),
            Pair("Corgis", 3),
            Pair("Dachshund", 4),
            3
        )

        val queFour = Question(
            4,
            "Which word in the English that has no true rhyme?",
            R.drawable.q4,
            Pair("Sweet", 1),
            Pair("Orange", 2),
            Pair("Dam", 3),
            Pair("Apple", 4),
            2
        )

        val queFive = Question(
            5,
            "Which town does the UK version of The Office take place?",
            R.drawable.q5,
            Pair("Liverpool", 1),
            Pair("Red Castle", 2),
            Pair("Windsor Castle", 3),
            Pair("Slough", 4),
            4
        )

        questionList.add(queOne)
        questionList.add(queTwo)
        questionList.add(queThree)
        questionList.add(queFour)
        questionList.add(queFive)

        return questionList
    }
}