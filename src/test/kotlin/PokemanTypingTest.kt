import errors.InvalidPokemanTyping
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import types.DualTyping
import types.PokemanType
import types.TripleTyping

class PokemanTypingTest :
  DescribeSpec({
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
