package de.admiralbiscuit.akquinet.pokeman.types

data class Pokeman(
  val number: NaturalNumber,
  val name: PokemanName,
  val description: String,
  val typing: PokemanTyping,
)
