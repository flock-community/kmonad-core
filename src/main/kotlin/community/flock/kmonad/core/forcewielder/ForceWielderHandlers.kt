package community.flock.kmonad.core.forcewielder

import community.flock.kmonad.core.AppException.BadRequest
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
suspend fun ForceWielderContext.bindGet() = getAllForceWielders()

suspend fun ForceWielderContext.bindGet(uuidString: String?) =
    getForceWielderByUUID(validate { UUID.fromString(uuidString) })

private fun <A> validate(block: () -> A) = try {
    block()
} catch (e: Exception) {
    throw BadRequest(e)
}
