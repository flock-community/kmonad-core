package community.flock.kmonad.core.droids

import arrow.core.continuations.effect
import arrow.core.left
import arrow.core.right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.droids.model.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface Context : HasDroidRepository, HasLogger


suspend fun Context.bindGet() = effect<AppException, Flow<Droid>> {
    getAll()
}

suspend fun Context.bindGet(uuidString: String?) = effect<AppException, Droid> {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    getByUUID(uuid)
}

suspend fun Context.bindPost(droid: Droid) = effect<AppException, Droid> {
    save(droid)
}

suspend fun Context.bindDelete(uuidString: String?) = effect<AppException, Droid> {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    deleteByUUID(uuid)
}


private fun <A> validate(block: () -> A) = runCatching(block)
    .fold({ it.right() }, { BadRequest(it).left() })
