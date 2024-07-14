// Private constructors for data classes don't really make sense at the moment,
// since the .copy() method is always publicly accessible.
// AFAIK, this will be fixed in a future Kotlin version.

@file:Suppress("DataClassPrivateConstructor")

package de.admiralbiscuit.akquinet.pokeman.types

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import de.admiralbiscuit.akquinet.pokeman.errors.InvalidPokemanTyping

// A Pokeman typing is valid if and only if it consists of distinct types.
private fun <R : PokemanTyping> validateTyping(
  vararg type: PokemanType,
  f: () -> R,
): Either<InvalidPokemanTyping, R> =
  if (type.distinct().size == type.size) {
    f().right()
  } else {
    InvalidPokemanTyping.left()
  }

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
      validateTyping(firstType, secondType) { DualTyping(firstType, secondType) }
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
      validateTyping(firstType, secondType, thirdType) {
        TripleTyping(firstType, secondType, thirdType)
      }
  }
}
