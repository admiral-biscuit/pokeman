package de.admiralbiscuit.akquinet.pokeman

import de.admiralbiscuit.akquinet.pokeman.errors.getOrThrow
import de.admiralbiscuit.akquinet.pokeman.errors.toSingleGeneralError
import de.admiralbiscuit.akquinet.pokeman.types.*

fun main() {
  val piggechu =
    Pokeman(
      number = 1.toNaturalNumberEither().getOrThrow(),
      name =
        "Piggechu"
          .toPokemanNameEither()
          .mapLeft { errors -> errors.toSingleGeneralError() }
          .getOrThrow(),
      description = "Piggechu is a cute Pokeman.",
      typing = SingleTyping(PokemanType.ELECTRIC),
    )

  println(piggechu)
}
