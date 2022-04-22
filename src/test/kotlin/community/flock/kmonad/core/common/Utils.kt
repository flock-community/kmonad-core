package community.flock.kmonad.core.common

import arrow.core.continuations.Effect
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import kotlin.reflect.KClass

fun <E : AppException> assertThrows(clazz: KClass<E>, block: suspend () -> Any) {
    Assertions.assertThrows(clazz.java) { runBlocking { block() } }
}

fun <E : AppException, A: Any> assertLeft(clazz: KClass<E>, block: suspend () -> Effect<AppException, A>) {
    assertThrows(clazz) { block().toEither().getOrHandle { throw it } }
}
