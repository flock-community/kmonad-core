package community.flock.kmonad.core.forcewielder

import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.jedi.HasJediRepository
import community.flock.kmonad.core.sith.HasSithRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.UUID


interface ForceWielderContext : HasJediRepository, HasSithRepository, HasLogger


@ExperimentalCoroutinesApi
suspend fun ForceWielderContext.bindGet() = getAllForceWielders()

suspend fun ForceWielderContext.bindGet(uuidString: String?) = getForceWielderByUUID(validate { UUID.fromString(uuidString) })


private fun <A> validate(block: () -> A) = try {
    block()
} catch (e: Exception) {
    throw BadRequest(e)
}
