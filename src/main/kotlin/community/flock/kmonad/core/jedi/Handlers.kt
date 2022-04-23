package community.flock.kmonad.core.jedi

import arrow.core.left
import arrow.core.right
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.jedi.model.Jedi
import community.flock.kmonad.core.toReader
import java.util.UUID


interface Context : HasJediRepository, HasLogger


fun bindGet() = getAll<Context>()

fun bindGet(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ it.toReader() }, { getByUUID<Context>(it) })

fun bindPost(jedi: Jedi) = save<Context>(jedi)

fun bindDelete(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ it.toReader() }, { deleteByUUID<Context>(it) })


private fun <A> validate(block: () -> A) = try {
    block().right()
} catch (e: Exception) {
    BadRequest().left()
}
