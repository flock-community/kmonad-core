package community.flock.kmonad.core.forcewielder

import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.core.common.TestLogger
import community.flock.kmonad.core.forcewielder.model.ForceWielder
import community.flock.kmonad.core.forcewielder.model.ForceWielder.Type.DARK
import community.flock.kmonad.core.forcewielder.model.ForceWielder.Type.LIGHT
import community.flock.kmonad.core.jedi.JediRepository
import community.flock.kmonad.core.jedi.TestJediRepository
import community.flock.kmonad.core.jedi.TestJediRepository.Companion.lukeUUID
import community.flock.kmonad.core.sith.SithRepository
import community.flock.kmonad.core.sith.TestSithRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ForceWielderTest {

    private val forceWieldersContext = object {
        operator fun invoke() = object : ForceWielderContext {
            override val logger: Logger = TestLogger
            override val jediRepository: JediRepository = TestJediRepository()
            override val sithRepository: SithRepository = TestSithRepository()
        }
    }

    @Test
    fun testBindGet() = runBlocking {
        forceWieldersContext().bindGet()
            .take(4).toList()
            .let { (jedi1, jedi2, sith1, sith2) ->
                jedi1.assertLight()
                jedi2.assertLight()
                sith1.assertDark()
                sith2.assertDark()
            }
    }

    @Test
    fun testBindGetByUUID(): Unit = runBlocking {
        forceWieldersContext().bindGet(lukeUUID)
            .assertLight()
    }


    private fun ForceWielder.assertLight() {
        assertEquals(LIGHT, forceType)
    }

    private fun ForceWielder.assertDark() {
        assertEquals(DARK, forceType)
    }
}
