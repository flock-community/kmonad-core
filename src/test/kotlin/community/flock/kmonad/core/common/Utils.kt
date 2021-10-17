package community.flock.kmonad.core.common

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.define.Data
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import kotlin.reflect.KClass

fun <T : AppException> assertThrows(clazz: KClass<T>, block: suspend () -> Data) {
    Assertions.assertThrows(clazz.java) {
        runBlocking { block() }
    }
}