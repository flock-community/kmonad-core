package community.flock.kmonad.core.sith

import arrow.core.flatMap
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID


interface SithContext : HasSithRepository, HasLogger


suspend fun SithContext.bindGet(): Result<List<Sith>> = getAllSith()

suspend fun SithContext.bindGet(uuidString: String?): Result<Sith> = validate { UUID.fromString(uuidString) }
    .let { getSithByUUID(it).flatMap { sith -> runCatching { sith ?: throw NotFound(it) } } }

suspend fun SithContext.bindPost(sith: Sith): Result<Sith> = saveSith(sith)

suspend fun SithContext.bindDelete(uuidString: String?): Result<Sith> =
    deleteSithByUUID(validate { UUID.fromString(uuidString) })


private fun <A> validate(block: () -> A) = try {
    block()
} catch (e: Exception) {
    throw BadRequest()
}
