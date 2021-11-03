package community.flock.kmonad.core.common

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import kotlin.reflect.KClass

fun <E : AppException> assertThrows(clazz: KClass<E>, block: suspend () -> Any) {
    Assertions.assertThrows(clazz.java) { runBlocking { block() } }
}

fun <E : AppException> assertLeft(clazz: KClass<E>, block: suspend () -> Either<AppException, Any>) {
    assertThrows(clazz) { block().getOrHandle { throw it } }
}
