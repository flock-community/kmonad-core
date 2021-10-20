package community.flock.kmonad.core.jedi.pipe

import arrow.core.Either.Left
import arrow.core.Either.Right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.toReader
import java.util.UUID


interface Context : Has.Logger, Has.JediRepository


fun bindGet() = getAll<Context>()

fun bindGet(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ it.toReader() }, { getByUUID<Context>(it) })

fun bindPost(jedi: Jedi) = save<Context>(jedi)

fun bindDelete(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ it.toReader() }, { deleteByUUID<Context>(it) })


private fun <A> validate(block: () -> A) = try {
    Right(block())
} catch (e: Exception) {
    Left(AppException.BadRequest())
}
