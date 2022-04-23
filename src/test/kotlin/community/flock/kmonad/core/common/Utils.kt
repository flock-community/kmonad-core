package community.flock.kmonad.core.common

import arrow.core.continuations.Effect
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.monads.Either
import community.flock.kmonad.core.common.monads.getOrHandle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import kotlin.reflect.KClass

inline fun <reified E : AppException> assertThrows(clazz: KClass<E>, crossinline block: suspend () -> Any) {
    Assertions.assertThrows(clazz.java) { runBlocking { block() } }
}

inline fun <reified E : AppException, A : Any> assertLeft(clazz: KClass<E>, crossinline block: () -> Either<AppException, A>) {
    assertThrows(clazz) { block().getOrHandle { throw it } }
}

inline fun <reified E : AppException, A : Any> assertLeftEffect(clazz: KClass<E>, crossinline block: suspend () -> Effect<AppException, A>) {
    assertThrows(clazz) { block().toEither().getOrHandle { throw it } }
}
