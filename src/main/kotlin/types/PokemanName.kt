package types

@JvmInline
value class PokemanName private constructor(val value: String) {
  companion object {
    fun fromString(string: String) {
      TODO()
    }
  }
}
