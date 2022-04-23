package community.flock.kmonad.core.droids

import arrow.core.continuations.EffectScope
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.log
import community.flock.kmonad.core.droids.model.Droid
import java.util.UUID


context(EffectScope<AppException>, HasDroidRepository)
suspend fun getAll() = droidRepository.getAll()

context(EffectScope<AppException>, HasDroidRepository)
suspend fun getByUUID(uuid: UUID) = droidRepository.getByUUID(uuid)

context(EffectScope<AppException>, HasDroidRepository)
suspend fun save(droid: Droid) = droidRepository.save(droid)

context(EffectScope<AppException>, HasDroidRepository, HasLogger)
suspend fun deleteByUUID(uuid: UUID): Droid {
    val droid = droidRepository.deleteByUUID(uuid)
    droid.id.log()
    return droid
}
