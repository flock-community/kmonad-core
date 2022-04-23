package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.monads.Either
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.Option
import community.flock.kmonad.core.jedi.model.Jedi
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HasJediRepository {
    val jediRepository: Repository
}

interface Repository {

    fun getAll(): IO<Either<AppException, Flow<Jedi>>>

    fun getByUUID(uuid: UUID): IO<Either<AppException, Option<Jedi>>>

    fun save(jedi: Jedi): IO<Either<AppException, Jedi>>

    fun deleteByUUID(uuid: UUID): IO<Either<AppException, Jedi>>

}
