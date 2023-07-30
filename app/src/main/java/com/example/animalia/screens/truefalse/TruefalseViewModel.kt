package com.example.animalia.screens.truefalse

import android.app.Application
import androidx.lifecycle.*
import com.example.animalia.database.questions.QuizElement
import com.example.animalia.database.questions.QuizElementDatabaseDao
import kotlinx.coroutines.*
import kotlin.math.ceil

const val TOTAL_QUESTIONS = 3
// TODO fix issue when playing second time

class TruefalseViewModel(
    private val database: QuizElementDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    var questions: MutableList<QuizElement> = mutableListOf()

    // TODO remember questions that are already done
    private var currentQuestionIndex = 0

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _currentQuestion = MutableLiveData("")
    val currentQuestion: LiveData<String>
        get() = _currentQuestion

    private val _numberOfQuestionsAsked = MutableLiveData<Int>()
    val numberOfQuestionsAsked: LiveData<Int>
        get() = _numberOfQuestionsAsked

    private val _goodQuestions = MutableLiveData<Int>()
    val goodQuestions: LiveData<Int>
        get() = _goodQuestions

    private val _shouldEvaluate = MutableLiveData<Boolean>()
    val shouldEvaluate: LiveData<Boolean>
        get() = _shouldEvaluate

    init {
        initializeLiveData()
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
    }

    private fun initializeLiveData() {
        uiScope.launch {
            addQuizElementsToDatabase()
            getQuestionFromDatabase()
            _currentQuestion.value = questions[0].question
        }
    }

    private suspend fun getQuestionFromDatabase() {
        return withContext(Dispatchers.IO) {
            for (i in 0 until TOTAL_QUESTIONS) {
                val quizElement = database.getByIndex(currentQuestionIndex + i)
                questions.add(quizElement)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun answerTrue() {
        if (questions[currentQuestionIndex].answer == "Juist") {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        increaseIndex()
        getQuestion()
    }

    fun answerFalse() {
        if (questions[currentQuestionIndex].answer == "Fout") {
            _goodQuestions.value = _goodQuestions.value!! + 1
        }
        increaseIndex()
        getQuestion()
    }

    private fun increaseIndex() {
        _numberOfQuestionsAsked.value = _numberOfQuestionsAsked.value!! + 1
        currentQuestionIndex++
    }

    private fun getQuestion() {
        if (isEnded()) {
            _shouldEvaluate.value = true
        } else {
            _currentQuestion.value = questions[currentQuestionIndex].question
        }
    }

    fun endGame() {
        _goodQuestions.value = 0
        _numberOfQuestionsAsked.value = 0
        _shouldEvaluate.value = false
        _currentQuestion.value = ""
    }

    private fun isEnded() = _numberOfQuestionsAsked.value!! >= TOTAL_QUESTIONS

    fun isWon() = _goodQuestions.value!! > ceil((TOTAL_QUESTIONS / 2).toDouble())

    private suspend fun addQuizElementsToDatabase() {
        return withContext(Dispatchers.IO) {
            // TODO read out from actual database
            database.clear()
            val newQuestion = QuizElement(
                100,
                0,
                "Papegaaien kunnen menselijke taal begrijpen en betekenisvolle gesprekken voeren.",
                "Fout",
                "Hoewel papegaaien in staat zijn om enkele woorden of zinnen na te bootsen, begrijpen ze meestal niet de betekenis van wat ze zeggen."
            )
            val newQuestion1 =
                QuizElement(
                    101,
                    1,
                    "De blauwe vinvis is het grootste dier dat ooit op aarde heeft geleefd.",
                    "Juist",
                    "De blauwe vinvis is het grootste dier dat ooit bekend is, zowel in het heden als in het verleden."
                )
            val newQuestion2 =
                QuizElement(
                    102,
                    2,
                    "Kakkerlakken kunnen zonder hun hoofd blijven leven.",
                    "Juist",
                    "Kakkerlakken kunnen gedurende enige tijd zonder hun hoofd blijven leven vanwege hun open bloedsomloop en het vermogen om te ademen door kleine openingen in hun lichaam."
                )
            val newQuestion3 =
                QuizElement(
                    103,
                    3,
                    "Een kangoeroe is een buideldier.",
                    "Juist",
                    "Kangoeroes behoren tot de buideldierenfamilie, wat betekent dat ze hun jongen in een buidel dragen om ze te beschermen en te voeden totdat ze volledig zijn ontwikkeld."
                )
            val newQuestion4 =
                QuizElement(
                    104,
                    4,
                    "Pinguïns kunnen vliegen.",
                    "Fout",
                    "Pinguïns zijn vogels, maar ze kunnen niet vliegen. Ze hebben vleugels die ze gebruiken om te zwemmen, maar ze kunnen niet in de lucht vliegen zoals andere vogels."
                )
            val newQuestion5 = QuizElement(
                105,
                5,
                "Spinnen zijn insecten.",
                "Fout",
                "Spinnen behoren tot de klasse van spinachtigen, terwijl insecten tot een andere klasse behoren. Ze hebben verschillende lichaamskenmerken en aantallen poten."
            )
            val newQuestion6 = QuizElement(
                106,
                5,
                "Kangoeroes komen oorspronkelijk uit Zuid-Amerika.",
                "Fout",
                "Kangoeroes zijn afkomstig uit Australië, niet uit Zuid-Amerika. Ze zijn uniek voor Australië en aangrenzende eilanden."
            )
            val newQuestion7 = QuizElement(
                107,
                5,
                "Goudvissen hebben een geheugen van slechts enkele seconden.",
                "Fout",
                "Hoewel het vaak wordt gezegd dat goudvissen een kort geheugen hebben, is uit onderzoek gebleken dat ze een geheugen kunnen hebben dat langer dan enkele maanden kan duren."
            )
            val newQuestion8 = QuizElement(
                108,
                5,
                "Mensen en dolfijnen zijn de enige dieren die zichzelf in een spiegel kunnen herkennen.",
                "Fout",
                "Naast mensen en dolfijnen kunnen sommige andere dieren, zoals grote mensapen en olifanten, zichzelf ook herkennen in een spiegel."
            )
            val newQuestion9 = QuizElement(
                109,
                5,
                "Krokodillen zijn reptielen.",
                "Juist",
                "Krokodillen behoren tot de reptielenfamilie en zijn nauw verwant aan alligators."
            )
            val newQuestion10 = QuizElement(
                110,
                5,
                "Een mannetjesbij steekt niet.",
                "Juist",
                "Mannetjesbijen, ook wel darren genoemd, hebben geen angel en kunnen niet steken zoals de vrouwelijke werksters en de koninginnen."
            )
            val newQuestion11 = QuizElement(
                111,
                5,
                "Een octopus heeft drie harten.",
                "Juist",
                "Een octopus heeft inderdaad drie harten: twee kieuwharten die het bloed door de kieuwen pompen en een systeemhart die het bloed door het lichaam stuwt."
            )
            val newQuestion12 = QuizElement(
                112,
                5,
                "De neushoorn is het grootste landdier ter wereld.",
                "Fout",
                "De neushoorn is groot, maar het grootste landdier ter wereld is de Afrikaanse olifant."
            )
            val newQuestion13 = QuizElement(
                113,
                5,
                "Alle slangen zijn giftig.",
                "Fout",
                "Niet alle slangen zijn giftig. Er zijn veel soorten slangen die niet giftig zijn en geen gevaar vormen voor de mens."
            )
            database.insert(newQuestion)
            database.insert(newQuestion1)
            database.insert(newQuestion2)
            database.insert(newQuestion3)
            database.insert(newQuestion4)
            database.insert(newQuestion5)
            database.insert(newQuestion6)
            database.insert(newQuestion7)
            database.insert(newQuestion8)
            database.insert(newQuestion9)
            database.insert(newQuestion10)
            database.insert(newQuestion11)
            database.insert(newQuestion12)
            database.insert(newQuestion13)

        }
    }
}
