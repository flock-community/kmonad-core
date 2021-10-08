package community.flock.kmonad.core.jedi.pipe

import arrow.core.Either.Left
import arrow.core.Either.Right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.toReader
import java.util.UUID

@Suppress("unused") // Used in either Spring or Ktor
fun bindGet() = getAll<Context>()

@Suppress("unused") // Used in either Spring or Ktor
fun bindGet(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ it.toReader() }, { getByUUID<Context>(it) })

@Suppress("unused") // Used in either Spring or Ktor
fun bindPost(jedi: Jedi) = save<Context>(jedi)

@Suppress("unused") // Used in either Spring or Ktor
fun bindDelete(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ it.toReader() }, { deleteByUUID<Context>(it) })

private fun <R> validate(block: () -> R) = try {
    Right(block())
} catch (e: Exception) {
    Left(AppException.BadRequest())
}
