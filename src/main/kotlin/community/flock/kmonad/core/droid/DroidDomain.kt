package community.flock.kmonad.core.droid

import arrow.core.continuations.EffectScope
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.log
import community.flock.kmonad.core.droid.model.Droid
import java.util.UUID

interface DroidDependencies : HasDroidRepository, HasLogger
interface DroidContext : DroidDependencies

context(EffectScope<AppException>, DroidContext)
suspend fun getAll() = droidRepository.getAll()

context(EffectScope<AppException>, DroidContext)
suspend fun getByUUID(uuid: UUID) = droidRepository.getByUUID(uuid)

context(EffectScope<AppException>, DroidContext)
suspend fun save(droid: Droid) = droidRepository.save(droid)

context(EffectScope<AppException>, DroidContext)
suspend fun deleteByUUID(uuid: UUID) = droidRepository.deleteByUUID(uuid).also { it.id.log() }
