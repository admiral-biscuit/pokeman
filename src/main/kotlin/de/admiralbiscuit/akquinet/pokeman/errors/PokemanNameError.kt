package de.admiralbiscuit.akquinet.pokeman.errors

sealed interface PokemanNameError : GeneralError {
  data object BlankName : PokemanNameError {
    override val message: String
      get() = "The given name is blank."
  }

  // data class NameTooLong(val )
}
