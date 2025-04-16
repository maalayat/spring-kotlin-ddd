package ec.solmedia.shared.domain

abstract class IntValueObject(private val value: Int) {

    fun value(): Int {
        return value
    }
}