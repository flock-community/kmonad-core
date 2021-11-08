package community.flock.kmonad.core.sith.pipe

import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.sith.data.Sith
import java.util.UUID


interface Context : Has.SithRepository, Has.Logger


suspend fun Context.bindGet() = getAll()

suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })

suspend fun Context.bindPost(sith: Sith) = save(sith)

suspend fun Context.bindDelete(uuidString: String?) = deleteByUUID(validate { UUID.fromString(uuidString) })


private fun <A> validate(block: () -> A) = try {
    block()
} catch (e: Exception) {
    throw BadRequest()
}
