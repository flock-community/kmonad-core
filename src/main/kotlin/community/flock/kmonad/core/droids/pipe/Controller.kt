package community.flock.kmonad.core.droids.pipe

import arrow.core.computations.either
import arrow.core.left
import arrow.core.right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.droids.data.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface Context : Has.DroidRepository, Has.Logger


suspend fun Context.bindGet() = either<AppException, Flow<Droid>> {
    getAll().bind()
}

suspend fun Context.bindGet(uuidString: String?) = either<AppException, Droid> {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    getByUUID(uuid).bind()
}

suspend fun Context.bindPost(droid: Droid) = either<AppException, Droid> {
    save(droid).bind()
}

suspend fun Context.bindDelete(uuidString: String?) = either<AppException, Droid> {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    deleteByUUID(uuid).bind()
}


private fun <A> validate(block: () -> A) = runCatching(block)
    .fold({ it.right() }, { AppException.BadRequest(it).left() })
