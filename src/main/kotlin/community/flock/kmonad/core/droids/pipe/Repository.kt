package community.flock.kmonad.core.droids.pipe

import arrow.core.Either
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.define.Dependency
import community.flock.kmonad.core.droids.data.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface Repository : Dependency {

    suspend fun getAll(): Either<AppException, Flow<Droid>>

    suspend fun getByUUID(uuid: UUID): Either<AppException, Droid>

    suspend fun save(droid: Droid): Either<AppException, Droid>

    suspend fun deleteByUUID(uuid: UUID): Either<AppException, Droid>

}
