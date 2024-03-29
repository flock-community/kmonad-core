package community.flock.kmonad.core.droid

import arrow.core.continuations.EffectScope
import arrow.core.continuations.effect
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.core.common.TestLogger
import community.flock.kmonad.core.common.assertLeft
import community.flock.kmonad.core.droid.model.Droid
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DroidsTest {

    private val droidContext = object {
        operator fun invoke() = object : DroidContext {
            override val droidRepository: DroidRepository = TestRepository
            override val logger: Logger = TestLogger
        }
    }

    @Test
    fun testBindGet() = runTest {
        val list = droidContext().bindGet().bind().take(2).toList()
        list.let { (c3po, r2d2) ->
            c3po.assertC3PO()
            r2d2.assertR2D2()
        }
    }

    @Test
    fun testBindGetByUUID() = runTest {
        with(droidContext()) {
            assertLeft(BadRequest::class) { bindGet("Not a UUID") }
            val droid = bindGet(TestRepository.c3poUUID).bind()
            droid.assertC3PO()
        }
    }

    @Test
    fun testBindPost() = runTest {
        val testDroid = Droid(designation = "TestDroidDesignation", type = Droid.Type.Protocol)
        val droid = droidContext().bindPost(testDroid).bind()
        droid.assertEquals(testDroid)
    }

    @Test
    fun testBindDelete() = runTest {
        with(droidContext()) {
            assertLeft(BadRequest::class) { bindDelete("Not a UUID") }
            val droid = bindDelete(TestRepository.c3poUUID).bind()
            droid.assertC3PO()
        }
    }

    private fun runTest(block: suspend EffectScope<AppException>.() -> Unit): Unit = runBlocking {
        effect { block() }.toEither().getOrHandle { throw it }
    }

    private fun Droid.assertC3PO() {
        Assertions.assertEquals(TestRepository.c3poUUID, id)
        Assertions.assertEquals("C3PO", designation)
        Assertions.assertEquals(Droid.Type.Protocol, type)
    }

    private fun Droid.assertR2D2() {
        Assertions.assertEquals(TestRepository.r2d2UUID, id)
        Assertions.assertEquals("R2D2", designation)
        Assertions.assertEquals(Droid.Type.Astromech, type)
    }

    private fun Droid.assertEquals(droid: Droid) {
        Assertions.assertEquals(droid.designation, designation)
        Assertions.assertEquals(droid.type, type)
    }

}
