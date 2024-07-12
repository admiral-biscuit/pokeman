package de.admiralbiscuit.akquinet.pokeman.types

import arrow.core.*
import de.admiralbiscuit.akquinet.pokeman.errors.PokemanNameError
import de.admiralbiscuit.akquinet.pokeman.errors.PokemanNameErrors

@JvmInline
value class PokemanName private constructor(val value: String) {
  companion object {
    private const val MAX_LENGTH = 16
    private val REGEX = Regex("^[a-zA-z]+\$")
    private val ALLOWED_SUFFIXES = nonEmptyListOf("chu", "mon")

    fun fromString(string: String): Either<PokemanNameErrors, PokemanName> {
      val errors = mutableListOf<PokemanNameError>()

      if (string.isBlank()) {
        errors.add(PokemanNameError.Blank)
      }

      if (!REGEX.matches(string)) {
        errors.add(PokemanNameError.InvalidRegex(string, REGEX))
      }

      if (string.length > MAX_LENGTH) {
        errors.add(PokemanNameError.TooLong(string, MAX_LENGTH))
      }

      if (ALLOWED_SUFFIXES.all { !string.endsWith(it, ignoreCase = true) }) {
        errors.add(PokemanNameError.InvalidSuffix(string, ALLOWED_SUFFIXES))
      }

      return if (errors.isEmpty()) {
        val capitalisedString = string.lowercase().replaceFirstChar { it.uppercase() }
        PokemanName(capitalisedString).right()
      } else {
        errors.toNonEmptyListOrNull()!!.left()
      }
    }
  }
}

fun String.toPokemanNameEither(): Either<PokemanNameErrors, PokemanName> =
  PokemanName.fromString(this)
