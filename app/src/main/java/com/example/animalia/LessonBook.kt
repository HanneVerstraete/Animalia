package com.example.animalia

class LessonBook {
    var currentLesson = ""

    private val lessons = listOf(
        "A dog barks",
        "Stap binnen in de betoverende wereld van leeuwen! Wat hun zo speciaal maakt, is niet alleen hun indrukwekkende uiterlijk, maar ook hun buitengewone sociale structuur. Leeuwen leven in hechte familiegroepen, ook wel troepen genoemd, waarin je meestal 10 tot 30 leden vindt, waaronder vrouwtjes, welpen en enkele mannetjes. Een van de meest verbazingwekkende eigenschappen van leeuwen is hun snelheid en behendigheid, ondanks hun luie en kalme reputatie. Als jagers van nature kunnen ze snelheden tot wel 80 kilometer per uur bereiken in korte sprints. Dit maakt hen tot formidabele en succesvolle jagers, met een voorkeur voor grote herbivoren zoals zebra's en gnoes. Maar wat het echt opmerkelijk maakt, is hoe leeuwinnen samenwerken bij de jacht. Als een geolied voetbalteam werken ze slim samen om hun prooi te omsingelen voordat ze toeslaan. Deze vakkundige tactiek heeft ervoor gezorgd dat ze enkele van de meest succesvolle jagers in het dierenrijk zijn. En laten we het niet vergeten: de brul van een leeuw! Het is een oorverdovend en krachtig geluid dat tot wel 8 kilometer ver kan reiken. Deze majestueuze roep wordt gebruikt om territorium af te bakenen en om te communiceren met andere leden van de troep. Het is een manier om te laten zien wie de koning van het gebied is. Met hun opvallende manen en elegante, trotse houding zijn leeuwen een waar symbool van kracht en schoonheid in de natuur.",
        "A duck quacks"
    )

    fun getNextLesson(lessonNumber: Int) {
        val lesson = if (lessons.size > lessonNumber) {
            lessons.elementAt(lessonNumber)
        } else {
            "Finished all lessons"
        }
        currentLesson = lesson
    }
}
