package community.flock.kmonad.core.sith

import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID

interface HasSithRepository {
    val sithRepository: SithRepository
}

interface SithRepository {

    suspend fun getAll(): Result<List<Sith>>

    suspend fun getByUUID(uuid: UUID): Result<Sith?>

    suspend fun save(sith: Sith): Result<Sith>

    suspend fun deleteByUUID(uuid: UUID): Result<Sith>

}
