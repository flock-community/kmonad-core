package community.flock.kmonad.core.sith.pipe

import community.flock.kmonad.core.common.define.Dependency
import community.flock.kmonad.core.sith.data.Sith
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface Repository : Dependency {

    suspend fun getAll(): Flow<Sith>

    suspend fun getByUUID(uuid: UUID): Sith

    suspend fun save(sith: Sith): Sith

    suspend fun deleteByUUID(uuid: UUID): Sith

}
