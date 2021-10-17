package community.flock.kmonad.core.wielders.pipe

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.define.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.UUID
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.core.sith.pipe.Context as SithContext
import community.flock.kmonad.core.sith.pipe.Repository as SithRepository


interface Context : JediContext, SithContext {
    override val jediRepository: JediRepository
    override val sithRepository: SithRepository
    override val logger: Logger
}


@ExperimentalCoroutinesApi
suspend fun Context.bindGet() = getAll()

suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })


private fun <R> validate(block: () -> R) = try {
    block()
} catch (e: Exception) {
    throw AppException.BadRequest(e)
}
