package community.flock.kmonad.core.wielders

import community.flock.kmonad.core.common.TestLogger
import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.core.jedi.TestRepository.lukeUUID
import community.flock.kmonad.core.wielders.data.ForceWielder
import community.flock.kmonad.core.wielders.data.ForceWielder.Type.DARK
import community.flock.kmonad.core.wielders.data.ForceWielder.Type.LIGHT
import community.flock.kmonad.core.wielders.pipe.Context
import community.flock.kmonad.core.wielders.pipe.bindGet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import community.flock.kmonad.core.jedi.TestRepository as TestJediRepository
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.core.sith.TestRepository as TestSithRepository
import community.flock.kmonad.core.sith.pipe.Repository as SithRepository

@ExperimentalCoroutinesApi
class ForceWielderTest {

    private val context = object : Context {
        override val logger: Logger = TestLogger
        override val jediRepository: JediRepository = TestJediRepository
        override val sithRepository: SithRepository = TestSithRepository
    }

    @Test
    fun testBindGet() = runBlocking {
        context.bindGet()
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
        context.bindGet(lukeUUID)
            .assertLight()
    }


    private fun ForceWielder.assertLight() {
        assertEquals(LIGHT, forceType)
    }

    private fun ForceWielder.assertDark() {
        assertEquals(DARK, forceType)
    }
}