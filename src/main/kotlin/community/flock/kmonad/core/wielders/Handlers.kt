package community.flock.kmonad.core.wielders

import community.flock.kmonad.core.AppException.BadRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.UUID


interface Context : Domain


@ExperimentalCoroutinesApi
suspend fun Context.bindGet() = getAll()

suspend fun Context.bindGet(uuidString: String?) = getByUUID(validate { UUID.fromString(uuidString) })


private fun <A> validate(block: () -> A) = try {
    block()
} catch (e: Exception) {
    throw BadRequest(e)
}
