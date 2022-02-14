package community.flock.kmonad.core.droids.pipe

import arrow.core.computations.either
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.droids.data.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID


suspend fun <R> R.getAll() where R : HasDroidRepository = either<AppException, Flow<Droid>> {
    droidRepository.getAll().bind()
}

suspend fun <R> R.getByUUID(uuid: UUID) where R : HasDroidRepository = either<AppException, Droid> {
    droidRepository.getByUUID(uuid).bind()
}

suspend fun <R> R.save(droid: Droid) where R : HasDroidRepository = either<AppException, Droid> {
    droidRepository.save(droid).bind()
}

suspend fun <R> R.deleteByUUID(uuid: UUID) where R : HasDroidRepository = either<AppException, Droid> {
    droidRepository.deleteByUUID(uuid).bind()
}
