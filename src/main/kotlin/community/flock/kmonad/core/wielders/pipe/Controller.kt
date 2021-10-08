@file:Suppress("unused")

package community.flock.kmonad.core.wielders.pipe

import community.flock.kmonad.core.AppException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.UUID

@ExperimentalCoroutinesApi
suspend fun Context.bindGet() = getAll()

suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })

private fun <R> validate(block: () -> R) = try {
    block()
} catch (e: Exception) {
    throw AppException.BadRequest(e)
}
