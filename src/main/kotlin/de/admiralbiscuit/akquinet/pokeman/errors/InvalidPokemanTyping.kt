package de.admiralbiscuit.akquinet.pokeman.errors

sealed interface PokemanTypingError : GeneralError {
  data class InvalidNumberOfTypes(val number: Int) : PokemanTypingError {
    override val message: String
      get() = "A PokemanTyping contains either 1, 2, or 3 types. Got $number instead."
  }

  data object DuplicateTypes : PokemanTypingError {
    override val message: String
      get() = "There were multiple occurrences of the same type."
  }
}
