package community.flock.kmonad.core.droid

import arrow.core.continuations.effect
import arrow.core.left
import arrow.core.right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.droid.model.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface DroidContext : HasDroidRepository, HasLogger


suspend fun DroidContext.bindGet() = effect<AppException, Flow<Droid>> {
    getAll()
}

suspend fun DroidContext.bindGet(uuidString: String?) = effect<AppException, Droid> {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    getByUUID(uuid)
}

suspend fun DroidContext.bindPost(droid: Droid) = effect<AppException, Droid> {
    save(droid)
}

suspend fun DroidContext.bindDelete(uuidString: String?) = effect<AppException, Droid> {
    val uuid = validate { UUID.fromString(uuidString) }.bind()
    deleteByUUID(uuid)
}


private fun <A> validate(block: () -> A) = runCatching(block)
    .fold({ it.right() }, { BadRequest(it).left() })
