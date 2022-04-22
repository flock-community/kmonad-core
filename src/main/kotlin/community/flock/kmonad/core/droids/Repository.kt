package community.flock.kmonad.core.droids

import arrow.core.continuations.EffectScope
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.droids.model.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface HasDroidRepository {
    val droidRepository: Repository
}

interface Repository {

    context(EffectScope<AppException>)
    suspend fun getAll(): Flow<Droid>

    context(EffectScope<AppException>)
    suspend fun getByUUID(uuid: UUID): Droid

    context(EffectScope<AppException>)
    suspend fun save(droid: Droid): Droid

    context(EffectScope<AppException>)
    suspend fun deleteByUUID(uuid: UUID): Droid

}
