package de.admiralbiscuit.akquinet.pokeman.errors

import arrow.core.NonEmptyList

typealias PokemanNameErrors = NonEmptyList<PokemanNameError>

sealed interface PokemanNameError : GeneralError {
  data object Blank : PokemanNameError {
    override val message: String
      get() = "The given name is blank."
  }

  data class InvalidRegex(val name: String, val regex: Regex) : PokemanNameError {
    override val message: String
      get() = "The name \"$name\" does not match the Regex $regex."
  }

  data class TooLong(val name: String, val maxLength: Int) : PokemanNameError {
    override val message: String
      get() =
        "The name \"$name\" (${name.length} characters) exceeds the maximum length of $maxLength characters."
  }

  data class InvalidSuffix(val name: String, val allowedSuffixes: NonEmptyList<String>) :
    PokemanNameError {
    override val message: String
      get() = "The name \"$name\" must end with one of ${allowedSuffixes.joinToString()}."
  }
}
