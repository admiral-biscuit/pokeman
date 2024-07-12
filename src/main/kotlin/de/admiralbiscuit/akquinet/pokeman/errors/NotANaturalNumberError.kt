package de.admiralbiscuit.akquinet.pokeman.errors

data class NotANaturalNumberError(val value: Int) : GeneralError {
  override val message: String
    get() =
      when (value) {
        0 -> "In my universe, 0 is not a natural number."
        else -> "$value is not a natural number."
      }
}
