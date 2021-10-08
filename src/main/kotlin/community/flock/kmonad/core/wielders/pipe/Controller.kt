package community.flock.kmonad.core.wielders.pipe

import community.flock.kmonad.core.AppException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.UUID

@ExperimentalCoroutinesApi
@Suppress("unused") // Used in either Spring or Ktor
suspend fun Context.bindGet() = getAll()

@Suppress("unused") // Used in either Spring or Ktor
suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })

private fun <R> validate(block: () -> R) = try {
    block()
} catch (e: Exception) {
    throw AppException.BadRequest(e)
}
