// Private constructors for data classes don't really make sense at the moment,
// since the .copy() method is always publicly accessible.
// AFAIK, this will be fixed in a future Kotlin version.

@file:Suppress("DataClassPrivateConstructor")

package de.admiralbiscuit.akquinet.pokeman.types

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import de.admiralbiscuit.akquinet.pokeman.errors.InvalidPokemanTyping

sealed interface PokemanTyping {
  fun joinToString(): String =
    when (this) {
      is SingleTyping -> type.string
      is DualTyping -> "${firstType.string} / ${secondType.string}"
      is TripleTyping -> "${firstType.string} / ${secondType.string} / ${thirdType.string}"
    }
}

data class SingleTyping(val type: PokemanType) : PokemanTyping

data class DualTyping private constructor(val firstType: PokemanType, val secondType: PokemanType) :
  PokemanTyping {
  companion object {
    fun of(
      firstType: PokemanType,
      secondType: PokemanType,
    ): Either<InvalidPokemanTyping, DualTyping> =
      if (firstType != secondType) {
        DualTyping(firstType, secondType).right()
      } else {
        InvalidPokemanTyping.left()
      }
  }
}

data class TripleTyping
private constructor(
  val firstType: PokemanType,
  val secondType: PokemanType,
  val thirdType: PokemanType,
) : PokemanTyping {
  companion object {
    fun of(
      firstType: PokemanType,
      secondType: PokemanType,
      thirdType: PokemanType,
    ): Either<InvalidPokemanTyping, TripleTyping> =
      if (firstType != secondType && secondType != thirdType && thirdType != firstType) {
        TripleTyping(firstType, secondType, thirdType).right()
      } else {
        InvalidPokemanTyping.left()
      }
  }
}
