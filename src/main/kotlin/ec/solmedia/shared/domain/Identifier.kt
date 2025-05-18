package ec.solmedia.shared.domain

import java.util.*

abstract class Identifier(value: String) {

    init {
        ensureValidUuid(value)
    }

    companion object {
        fun random() = UUID.randomUUID()
    }

    @Throws(IllegalArgumentException::class)
    private fun ensureValidUuid(value: String): UUID {
        return UUID.fromString(value)
    }
}