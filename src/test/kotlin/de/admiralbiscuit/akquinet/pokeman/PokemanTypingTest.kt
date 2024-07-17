package de.admiralbiscuit.akquinet.pokeman

import de.admiralbiscuit.akquinet.pokeman.errors.PokemanTypingError
import de.admiralbiscuit.akquinet.pokeman.types.*
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class PokemanTypingTest :
  DescribeSpec({
    describe("implementation-level eitherFrom") {
      describe("DualTyping") {
        it("is valid") {
          val typing = DualTyping.eitherFrom(PokemanType.FIRE, PokemanType.FLYING).shouldBeRight()
          typing.firstType shouldBe PokemanType.FIRE
          typing.secondType shouldBe PokemanType.FLYING
        }

        it("is not valid") {
          val error = DualTyping.eitherFrom(PokemanType.FIRE, PokemanType.FIRE).shouldBeLeft()
          error shouldBe PokemanTypingError.DuplicateTypes
        }
      }

      describe("TripleTyping") {
        it("is valid") {
          val typing =
            TripleTyping.eitherFrom(PokemanType.DRAGON, PokemanType.FIRE, PokemanType.FLYING)
              .shouldBeRight()
          typing.firstType shouldBe PokemanType.DRAGON
          typing.secondType shouldBe PokemanType.FIRE
          typing.thirdType shouldBe PokemanType.FLYING
        }

        it("is not valid") {
          val typings =
            listOf(
              TripleTyping.eitherFrom(PokemanType.DRAGON, PokemanType.DRAGON, PokemanType.FLYING),
              TripleTyping.eitherFrom(PokemanType.DRAGON, PokemanType.FIRE, PokemanType.FIRE),
              TripleTyping.eitherFrom(PokemanType.FLYING, PokemanType.FIRE, PokemanType.FLYING),
              TripleTyping.eitherFrom(PokemanType.DRAGON, PokemanType.DRAGON, PokemanType.DRAGON),
            )

          typings.forEach {
            val error = it.shouldBeLeft()
            error shouldBe PokemanTypingError.DuplicateTypes
          }
        }
      }
    }

    describe("interface-level eitherFrom") {
      it("creates a SingleTyping") {
        val typing =
          PokemanTyping.eitherFrom(PokemanType.FIRE)
            .shouldBeRight()
            .shouldBeInstanceOf<SingleTyping>()

        typing.type shouldBe PokemanType.FIRE
      }

      it("creates a DualTyping") {
        val typing =
          PokemanTyping.eitherFrom(PokemanType.FIRE, PokemanType.FLYING)
            .shouldBeRight()
            .shouldBeInstanceOf<DualTyping>()

        typing.firstType shouldBe PokemanType.FIRE
        typing.secondType shouldBe PokemanType.FLYING
      }

      it("creates a TripleTyping") {
        val typing =
          PokemanTyping.eitherFrom(PokemanType.DRAGON, PokemanType.FIRE, PokemanType.FLYING)
            .shouldBeRight()
            .shouldBeInstanceOf<TripleTyping>()

        typing.firstType shouldBe PokemanType.DRAGON
        typing.secondType shouldBe PokemanType.FIRE
        typing.thirdType shouldBe PokemanType.FLYING
      }

      it("fails for 0 types") {
        val error = PokemanTyping.eitherFrom().shouldBeLeft()

        error shouldBe PokemanTypingError.InvalidNumberOfTypes(0)
      }

      it("fails for 4 types") {
        val error =
          PokemanTyping.eitherFrom(
              PokemanType.DRAGON,
              PokemanType.FIRE,
              PokemanType.WATER,
              PokemanType.FLYING,
            )
            .shouldBeLeft()

        error shouldBe PokemanTypingError.InvalidNumberOfTypes(4)
      }
    }
  })
