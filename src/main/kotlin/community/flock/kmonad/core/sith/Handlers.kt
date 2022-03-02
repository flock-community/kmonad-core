package community.flock.kmonad.core.sith

import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID


interface Context : HasSithRepository, HasLogger


suspend fun Context.bindGet() = getAll()

suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })

suspend fun Context.bindPost(sith: Sith) = save(sith)

suspend fun Context.bindDelete(uuidString: String?) = deleteByUUID(validate { UUID.fromString(uuidString) })


private fun <A> validate(block: () -> A) = try {
    block()
} catch (e: Exception) {
    throw BadRequest()
}
