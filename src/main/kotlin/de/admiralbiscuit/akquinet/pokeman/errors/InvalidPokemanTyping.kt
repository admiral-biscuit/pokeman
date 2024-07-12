package de.admiralbiscuit.akquinet.pokeman.errors

data object InvalidPokemanTyping : GeneralError {
  override val message: String
    get() = "There were multiple occurrences of the same type."
}
