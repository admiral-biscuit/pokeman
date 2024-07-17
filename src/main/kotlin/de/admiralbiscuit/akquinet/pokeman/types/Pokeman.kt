package de.admiralbiscuit.akquinet.pokeman.types

data class Pokeman(
  val number: NaturalNumber,
  val name: PokemanName,
  val description: String,
  val typing: PokemanTyping,
) {
  // conversion to an object that can easily be serialized
  fun toRawPokeman(): RawPokeman =
    RawPokeman(number.value, name.value, description, typing.toPokemanTypeList())
}
