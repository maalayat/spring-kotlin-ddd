package ec.solmedia.shared

import net.datafaker.Faker

object MotherCreator {
    private val faker = Faker()

    fun random(): Faker = faker
}
