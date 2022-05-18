package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.monads.Either.Companion.left
import community.flock.kmonad.core.common.monads.Either.Companion.right
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.Reader.Factory.just
import community.flock.kmonad.core.common.monads.flatMap
import community.flock.kmonad.core.jedi.model.Jedi
import java.util.UUID


interface JediContext : HasJediRepository, HasLogger


fun bindGet() = getAllJedi<JediContext>()

fun bindGet(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .let { eitherUuid ->
        eitherUuid
            .fold({ just(IO { it.left() }) }, { getJediByUUID<JediContext>(it) })
            .map { io ->
                io.map { either ->
                    either.flatMap { option ->
                        option.fold(
                            { eitherUuid.flatMap { NotFound(it).left() } },
                            { it.right() })
                    }
                }
            }
    }

fun bindPost(jedi: Jedi) = saveJedi<JediContext>(jedi)

fun bindDelete(uuidString: String?) = validate { UUID.fromString(uuidString) }
    .fold({ just(IO { it.left() }) }, { deleteJediByUUID<JediContext>(it) })


private fun <A : Any> validate(block: () -> A) = try {
    block().right()
} catch (e: Exception) {
    BadRequest().left()
}
