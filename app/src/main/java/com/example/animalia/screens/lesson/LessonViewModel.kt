package com.example.animalia.screens.lesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalia.database.lessons.DatabaseLesson
import com.example.animalia.database.lessons.LessonDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonViewModel(
    private val database: LessonDatabaseDao
) : ViewModel() {
    var databaseLessons: Array<DatabaseLesson> = listOf<DatabaseLesson>().toTypedArray()

    private var lessonNumber: Int = 0

    private var _currentDatabaseLesson = MutableLiveData<DatabaseLesson>()
    val currentDatabaseLesson: LiveData<DatabaseLesson>
        get() = _currentDatabaseLesson

    private val _shouldEnd = MutableLiveData<Boolean>()
    val shouldEnd: LiveData<Boolean>
        get() = _shouldEnd

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            addLessonsToDatabase()
            getLessonsFromDatabase()
            getNextLesson()
        }
    }

    // TODO handle when no more lessons
    fun getNextLesson() {
        _currentDatabaseLesson.value = databaseLessons[lessonNumber]

        if (isLastLesson(lessonNumber)) {
            _shouldEnd.value = true
        } else {
            lessonNumber++
        }
    }

    fun isLastLesson(lessonNumber: Int): Boolean {
        return databaseLessons.size - 1 < lessonNumber
    }

    // TODO get from actual database
    private suspend fun addLessonsToDatabase() {
        return withContext(Dispatchers.IO) {
            database.clear()
            val newDatabaseLesson0 = DatabaseLesson(
                200,
                0,
                "Welkom in de liefdevolle wereld van honden!\n\n" +
                        "Deze trouwe metgezellen staan bekend om hun onvoorwaardelijke genegenheid en vreugdevolle persoonlijkheden.\n\n" +
                        "Naast hun liefdevolle aard zijn honden ook buitengewoon intelligent. Ze zijn in staat om complexe taken uit te voeren en hebben vaak een diep begrip van menselijke emoties.\n\n" +
                        "Met hun scherpe zintuigen en unieke vaardigheden kunnen ze ons op talloze manieren verbazen en verrassen.",
                "Honden manually from database"
            )
            val newDatabaseLesson1 = DatabaseLesson(
                201,
                1,
                "Stap binnen in de betoverende wereld van leeuwen!\n\n" +
                        "Wat hun zo speciaal maakt, is niet alleen hun indrukwekkende uiterlijk, maar ook hun buitengewone sociale structuur. Leeuwen leven in hechte familiegroepen, ook wel troepen genoemd, waarin je meestal 10 tot 30 leden vindt, waaronder vrouwtjes, welpen en enkele mannetjes.\n\n" +
                        "Een van de meest verbazingwekkende eigenschappen van leeuwen is hun snelheid en behendigheid, ondanks hun luie en kalme reputatie. Als jagers van nature kunnen ze snelheden tot wel 80 kilometer per uur bereiken in korte sprints. Dit maakt hen tot formidabele en succesvolle jagers, met een voorkeur voor grote herbivoren zoals zebra's en gnoes.\n\n" +
                        "En laten we het niet vergeten: de brul van een leeuw! Het is een oorverdovend en krachtig geluid dat tot wel 8 kilometer ver kan reiken. Deze majestueuze roep wordt gebruikt om territorium af te bakenen en om te communiceren met andere leden van de troep. Het is een manier om te laten zien wie de koning van het gebied is.\n\n" +
                        "Met hun opvallende manen en elegante, trotse houding zijn leeuwen een waar symbool van kracht en schoonheid in de natuur.",
                "Leeuwen manually from database"
            )
            val newDatabaseLesson2 = DatabaseLesson(
                202,
                2,
                "Welkom in de charmante wereld van eenden!\n\n" +
                        "Deze gevederde vrienden staan bekend om hun speelse en levendige karakter. Eenden voelen zich als een vis in het water en zijn meesters in het zwemmen en duiken.\n\n" +
                        "Eenden zijn ook verrassend slim. Ze hebben een goed ontwikkeld navigatievermogen en kunnen hun weg vinden over lange afstanden tijdens hun jaarlijkse migraties.\n\n" +
                        "Met hun ondeugende geest en innemende persoonlijkheden blijven eenden een bron van verwondering en inspiratie voor ons allemaal!",
                "Eenden manually from database"
            )
            database.insert(newDatabaseLesson0)
            database.insert(newDatabaseLesson1)
            database.insert(newDatabaseLesson2)
        }
    }

    private suspend fun getLessonsFromDatabase() {
        return withContext(Dispatchers.IO) {
                val lessonsFromDatabase = database.getAllLessons()
                databaseLessons = lessonsFromDatabase
        }
    }
}