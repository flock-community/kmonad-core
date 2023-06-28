package community.flock.kmonad.core.common

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import kotlin.reflect.KClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions

inline fun <reified E : AppException> assertThrows(clazz: KClass<E>, crossinline block: suspend () -> Any) {
    Assertions.assertThrows(clazz.java) { runBlocking { block() } }
}

inline fun <reified E : AppException, A : Any> assertLeft(clazz: KClass<E>, crossinline block: suspend () -> Either<AppException, A>) {
    assertThrows(clazz) { block().getOrHandle { throw it } }
}
