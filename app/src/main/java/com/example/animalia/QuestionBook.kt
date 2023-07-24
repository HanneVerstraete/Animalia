package com.example.animalia

class QuestionBook() {
    // TODO convert to list of objects
    // TODO show answer an description
    private val questions = listOf(
        "Papegaaien kunnen menselijke taal begrijpen en betekenisvolle gesprekken voeren.", "Fout", "Hoewel papegaaien in staat zijn om enkele woorden of zinnen na te bootsen, begrijpen ze meestal niet de betekenis van wat ze zeggen.",
        "De blauwe vinvis is het grootste dier dat ooit op aarde heeft geleefd.", "Juist", "De blauwe vinvis is het grootste dier dat ooit bekend is, zowel in het heden als in het verleden.",
        "Kakkerlakken kunnen zonder hun hoofd blijven leven.", "Juist", "Kakkerlakken kunnen gedurende enige tijd zonder hun hoofd blijven leven vanwege hun open bloedsomloop en het vermogen om te ademen door kleine openingen in hun lichaam.",
        "Een kangoeroe is een buideldier.", "Juist", "Kangoeroes behoren tot de buideldierenfamilie, wat betekent dat ze hun jongen in een buidel dragen om ze te beschermen en te voeden totdat ze volledig zijn ontwikkeld.",
        "Pinguïns kunnen vliegen.", "Fout", "Pinguïns zijn vogels, maar ze kunnen niet vliegen. Ze hebben vleugels die ze gebruiken om te zwemmen, maar ze kunnen niet in de lucht vliegen zoals andere vogels.",
        "Spinnen zijn insecten.", "Fout", "Spinnen behoren tot de klasse van spinachtigen, terwijl insecten tot een andere klasse behoren. Ze hebben verschillende lichaamskenmerken en aantallen poten.",
        "Kangoeroes komen oorspronkelijk uit Zuid-Amerika.", "Fout", "Kangoeroes zijn afkomstig uit Australië, niet uit Zuid-Amerika. Ze zijn uniek voor Australië en aangrenzende eilanden.",
        "Goudvissen hebben een geheugen van slechts enkele seconden.", "Fout", "Hoewel het vaak wordt gezegd dat goudvissen een kort geheugen hebben, is uit onderzoek gebleken dat ze een geheugen kunnen hebben dat langer dan enkele maanden kan duren.",
        "Mensen en dolfijnen zijn de enige dieren die zichzelf in een spiegel kunnen herkennen.", "Fout", "Naast mensen en dolfijnen kunnen sommige andere dieren, zoals grote mensapen en olifanten, zichzelf ook herkennen in een spiegel.",
        "Krokodillen zijn reptielen.", "Juist", "Krokodillen behoren tot de reptielenfamilie en zijn nauw verwant aan alligators.",
        "Een mannetjesbij steekt niet.", "Juist", "Mannetjesbijen, ook wel darren genoemd, hebben geen angel en kunnen niet steken zoals de vrouwelijke werksters en de koninginnen.",
        "Een octopus heeft drie harten.", "Juist", "Een octopus heeft inderdaad drie harten: twee kieuwharten die het bloed door de kieuwen pompen en een systeemhart die het bloed door het lichaam stuwt.",
        "De neushoorn is het grootste landdier ter wereld.", "Fout", "De neushoorn is groot, maar het grootste landdier ter wereld is de Afrikaanse olifant.",
        "Alle slangen zijn giftig.", "Fout", "Niet alle slangen zijn giftig. Er zijn veel soorten slangen die niet giftig zijn en geen gevaar vormen voor de mens."
    )

    fun getQuestion(questionIndex: Int): String {
        val question = if(!isLastQuestion(questionIndex)) {
            questions[questionIndex * 3]
        } else {
            // TODO better handling
            "Finished all questions"
        }

        return question
    }

    fun isAnswerTrue(questionIndex: Int): Boolean {
        return questions[questionIndex * 3 + 1] == "Juist"
    }

    fun isAnswerFalse(questionIndex: Int): Boolean {
        return questions[questionIndex * 3 + 1] == "Fout"
    }

    private fun isLastQuestion(questionIndex: Int): Boolean {
        return questions.size - 1 < questionIndex * 3
    }
}