package com.example.animalia

class QuestionBook() {
    // TODO convert to list of objects
    // TODO show answer an description
    private val questions = listOf(
        "Papegaaien kunnen menselijke taal begrijpen en betekenisvolle gesprekken voeren.", "Fout", "Hoewel papegaaien in staat zijn om enkele woorden of zinnen na te bootsen, begrijpen ze meestal niet de betekenis van wat ze zeggen.",
        "De blauwe vinvis is het grootste dier dat ooit op aarde heeft geleefd.", "Juist", "De blauwe vinvis is het grootste dier dat ooit bekend is, zowel in het heden als in het verleden.",
        "Kakkerlakken kunnen zonder hun hoofd blijven leven.", "Juist", "Kakkerlakken kunnen gedurende enige tijd zonder hun hoofd blijven leven vanwege hun open bloedsomloop en het vermogen om te ademen door kleine openingen in hun lichaam.",
        "Een kangoeroe is een buideldier.", "Juist", "Kangoeroes behoren tot de buideldierenfamilie, wat betekent dat ze hun jongen in een buidel dragen om ze te beschermen en te voeden totdat ze volledig zijn ontwikkeld."
    )

    fun getQuestion(questionIndex: Int): String {
        return questions[questionIndex * 3]
    }

    fun isAnswerTrue(questionIndex: Int): Boolean {
        return questions[questionIndex * 3 + 1] == "Juist"
    }

    fun isAnswerFalse(questionIndex: Int): Boolean {
        return questions[questionIndex * 3 + 1] == "Fout"
    }
}