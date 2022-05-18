package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.monads.Either
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.Option
import community.flock.kmonad.core.jedi.model.Jedi
import java.util.UUID

interface HasJediRepository {
    val jediRepository: JediRepository
}

interface JediRepository {

    fun getAll(): IO<Either<AppException, List<Jedi>>>

    fun getByUUID(uuid: UUID): IO<Either<AppException, Option<Jedi>>>

    fun save(jedi: Jedi): IO<Either<AppException, Jedi>>

    fun deleteByUUID(uuid: UUID): IO<Either<AppException, Jedi>>

}
