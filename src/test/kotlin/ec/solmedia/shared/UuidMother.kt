package ec.solmedia.shared

import java.util.*

object UuidMother {
    fun random(): String = UUID.randomUUID().toString()
}

object WordMother {
    fun random(): String = MotherCreator.random().lorem().word()
}
