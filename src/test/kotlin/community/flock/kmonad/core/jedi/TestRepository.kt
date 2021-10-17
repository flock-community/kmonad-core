package community.flock.kmonad.core.jedi

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.IO
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.jedi.pipe.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.util.UUID

object TestRepository : Repository {

    const val lukeUUID = "220ae1b9-6300-4082-a2f0-5884aabc1bac"
    const val yodaUUID = "0049d4f0-122f-49a9-a588-d7b9296b4b9f"

    private val allJedi = mutableMapOf(
        lukeUUID to Jedi(id = lukeUUID, name = "Luke", age = 23),
        yodaUUID to Jedi(id = yodaUUID, name = "Yoda", age = 900)
    )

    override fun getAll(): IO<Either<AppException, Flow<Jedi>>> =
        IO { allJedi.map { (_, jedi) -> jedi }.asFlow().right() }

    override fun getByUUID(uuid: UUID): IO<Either<AppException, Jedi>> = IO {
        allJedi[uuid.toString()]?.right() ?: NotFound(uuid).left()
    }

    override fun save(jedi: Jedi): IO<Either<AppException, Jedi>> = IO {
        if (allJedi.contains(jedi.id)) Conflict(UUID.fromString(jedi.id)).left()
        else jedi.right()
    }

    override fun deleteByUUID(uuid: UUID) = getByUUID(uuid)

}
