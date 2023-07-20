package com.example.animalia

import kotlin.math.ceil

val TOTAL_QUESTIONS = 3

class QuestionBook(var numQuestions: Int = 0, var goodQuestions: Int = 0) {
    var currentQuestion = ""
    private var currentQuestionIndex = 0

    // TODO convert to list of objects
    // TODO show answer an description
    private val questions = listOf(
        "Papegaaien kunnen menselijke taal begrijpen en betekenisvolle gesprekken voeren.", "Fout", "Hoewel papegaaien in staat zijn om enkele woorden of zinnen na te bootsen, begrijpen ze meestal niet de betekenis van wat ze zeggen.",
        "De blauwe vinvis is het grootste dier dat ooit op aarde heeft geleefd.", "Juist", "De blauwe vinvis is het grootste dier dat ooit bekend is, zowel in het heden als in het verleden.",
        "Kakkerlakken kunnen zonder hun hoofd blijven leven.", "Juist", "Kakkerlakken kunnen gedurende enige tijd zonder hun hoofd blijven leven vanwege hun open bloedsomloop en het vermogen om te ademen door kleine openingen in hun lichaam.",
        "Een kangoeroe is een buideldier.", "Juist", "Kangoeroes behoren tot de buideldierenfamilie, wat betekent dat ze hun jongen in een buidel dragen om ze te beschermen en te voeden totdat ze volledig zijn ontwikkeld."
    )

    fun getNextQuestion() {
        currentQuestion = questions[currentQuestionIndex]
        currentQuestionIndex += 3
    }

    fun evaluateTrue() {
        if (questions[currentQuestionIndex + 1] == "Juist") {
            goodQuestions++
        }
        numQuestions++
    }

    fun evaluateFalse() {
        if (questions[currentQuestionIndex + 1] == "Fout") {
            goodQuestions++
        }
        numQuestions++
    }

    fun isEnded() = numQuestions >= TOTAL_QUESTIONS
    fun isWon() = goodQuestions > ceil((TOTAL_QUESTIONS / 2).toDouble())
}