package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.define.Dependency
import community.flock.kmonad.core.sith.model.Sith
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HasSithRepository {
    val sithRepository: Repository
}

interface Repository : Dependency {

    suspend fun getAll(): Flow<Sith>

    suspend fun getByUUID(uuid: UUID): Sith

    suspend fun save(sith: Sith): Sith

    suspend fun deleteByUUID(uuid: UUID): Sith

}
