package de.admiralbiscuit.akquinet.pokeman

import de.admiralbiscuit.akquinet.pokeman.errors.InvalidPokemanTyping
import de.admiralbiscuit.akquinet.pokeman.types.DualTyping
import de.admiralbiscuit.akquinet.pokeman.types.PokemanType
import de.admiralbiscuit.akquinet.pokeman.types.SingleTyping
import de.admiralbiscuit.akquinet.pokeman.types.TripleTyping
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PokemanTypingTest :
  DescribeSpec({
    describe("joinToString") {
      it("wörks") {
        val typing1 = SingleTyping(PokemanType.FIRE)
        typing1.joinToString() shouldBe "Fire"

        val typing2 = DualTyping.of(PokemanType.FIRE, PokemanType.FLYING).shouldBeRight()
        typing2.joinToString() shouldBe "Fire / Flying"

        val typing3 =
          TripleTyping.of(PokemanType.DRAGON, PokemanType.FIRE, PokemanType.FLYING).shouldBeRight()
        typing3.joinToString() shouldBe "Dragon / Fire / Flying"
      }
    }

    describe("DualTyping") {
      it("is valid") {
        val typing = DualTyping.of(PokemanType.FIRE, PokemanType.FLYING).shouldBeRight()
        typing.firstType shouldBe PokemanType.FIRE
        typing.secondType shouldBe PokemanType.FLYING
      }

      it("is not valid") {
        val error = DualTyping.of(PokemanType.FIRE, PokemanType.FIRE).shouldBeLeft()
        error shouldBe InvalidPokemanTyping
      }
    }

    describe("TripleTyping") {
      it("is valid") {
        val typing =
          TripleTyping.of(PokemanType.DRAGON, PokemanType.FIRE, PokemanType.FLYING).shouldBeRight()
        typing.firstType shouldBe PokemanType.DRAGON
        typing.secondType shouldBe PokemanType.FIRE
        typing.thirdType shouldBe PokemanType.FLYING
      }

      it("is not valid") {
        val typings =
          listOf(
            TripleTyping.of(PokemanType.DRAGON, PokemanType.DRAGON, PokemanType.FLYING),
            TripleTyping.of(PokemanType.DRAGON, PokemanType.FIRE, PokemanType.FIRE),
            TripleTyping.of(PokemanType.FLYING, PokemanType.FIRE, PokemanType.FLYING),
            TripleTyping.of(PokemanType.DRAGON, PokemanType.DRAGON, PokemanType.DRAGON),
          )

        typings.forEach {
          val error = it.shouldBeLeft()
          error shouldBe InvalidPokemanTyping
        }
      }
    }
  })
