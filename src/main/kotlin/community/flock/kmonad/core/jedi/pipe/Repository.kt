package community.flock.kmonad.core.jedi.pipe

import arrow.core.Either
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.IO
import community.flock.kmonad.core.common.define.Dependency
import community.flock.kmonad.core.jedi.data.Jedi
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HasJediRepository {
    val jediRepository: Repository
}

interface Repository : Dependency {

    fun getAll(): IO<Either<AppException, Flow<Jedi>>>

    fun getByUUID(uuid: UUID): IO<Either<AppException, Jedi>>

    fun save(jedi: Jedi): IO<Either<AppException, Jedi>>

    fun deleteByUUID(uuid: UUID): IO<Either<AppException, Jedi>>

}
