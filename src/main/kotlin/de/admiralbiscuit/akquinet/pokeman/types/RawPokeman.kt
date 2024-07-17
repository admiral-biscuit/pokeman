package de.admiralbiscuit.akquinet.pokeman.types

import arrow.core.Either
import arrow.core.raise.either
import de.admiralbiscuit.akquinet.pokeman.errors.GeneralError
import de.admiralbiscuit.akquinet.pokeman.errors.toSingleGeneralError

// A class that models a Pokeman right from a database for easy (de)serialization.
// Use the converter to get a representation with strong types instead.

data class RawPokeman(
  val number: Int,
  val name: String,
  val description: String,
  val typing: List<PokemanType>,
) {
  fun toPokemanEither(): Either<GeneralError, Pokeman> = either {
    Pokeman(
      number.toNaturalNumberEither().bind(),
      name.toPokemanNameEither().mapLeft { errors -> errors.toSingleGeneralError() }.bind(),
      description,
      typing.toPokemanTypingEither().bind(),
    )
  }
}
