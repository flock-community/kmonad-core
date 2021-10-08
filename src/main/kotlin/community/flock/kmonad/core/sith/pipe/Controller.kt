package community.flock.kmonad.core.sith.pipe

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.sith.data.Sith
import java.util.UUID

@Suppress("unused") // Used in either Spring or Ktor
suspend fun Context.bindGet() = getAll()

@Suppress("unused") // Used in either Spring or Ktor
suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })

@Suppress("unused") // Used in either Spring or Ktor
suspend fun Context.bindPost(sith: Sith) = save(sith)

@Suppress("unused") // Used in either Spring or Ktor
suspend fun Context.bindDelete(uuidString: String?) = deleteByUUID(validate { UUID.fromString(uuidString) })

private fun <R> validate(block: () -> R) = try {
    block()
} catch (e: Exception) {
    throw AppException.BadRequest()
}
