package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.monads.Either.Companion.left
import community.flock.kmonad.core.common.monads.Either.Companion.right
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.flatMap
import community.flock.kmonad.core.common.monads.toOption
import community.flock.kmonad.core.jedi.model.Jedi
import java.util.UUID

class TestJediRepository : JediRepository {
    companion object {
        const val lukeUUID = "220ae1b9-6300-4082-a2f0-5884aabc1bac"
        const val yodaUUID = "0049d4f0-122f-49a9-a588-d7b9296b4b9f"
    }

    private val allJedi = mapOf(
        lukeUUID to Jedi(id = lukeUUID, name = "Luke", age = 23),
        yodaUUID to Jedi(id = yodaUUID, name = "Yoda", age = 900)
    )

    override fun getAll() = IO { allJedi.map { it.value }.right() }

    override fun getByUUID(uuid: UUID) = IO { allJedi[uuid.toString()].toOption().right() }

    override fun save(jedi: Jedi) = IO {
        if (allJedi.contains(jedi.id)) Conflict(UUID.fromString(jedi.id)).left()
        else jedi.right()
    }

    override fun deleteByUUID(uuid: UUID) = getByUUID(uuid)
        .map { either -> either.flatMap { option -> option.fold({ NotFound(uuid).left() }, { it.right() }) } }
}
