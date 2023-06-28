package community.flock.kmonad.core.droid

import arrow.core.Either
import arrow.core.continuations.either
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.droid.model.Droid
import java.util.UUID

suspend fun DroidContext.bindGet() = either { getAll() }

suspend fun DroidContext.bindGet(uuidString: String?) = either {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    getByUUID(uuid)
}

suspend fun DroidContext.bindPost(droid: Droid) = either { save(droid) }

suspend fun DroidContext.bindDelete(uuidString: String?) = either {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    deleteByUUID(uuid)
}

private fun <A> validate(block: () -> A) = Either.catch(block).mapLeft(::BadRequest)
