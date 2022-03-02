package community.flock.kmonad.core.droids

import arrow.core.computations.either
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.droids.model.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID


suspend fun HasDroidRepository.getAll() = either<AppException, Flow<Droid>> {
    droidRepository.getAll().bind()
}

suspend fun HasDroidRepository.getByUUID(uuid: UUID) = either<AppException, Droid> {
    droidRepository.getByUUID(uuid).bind()
}

suspend fun HasDroidRepository.save(droid: Droid) = either<AppException, Droid> {
    droidRepository.save(droid).bind()
}

suspend fun HasDroidRepository.deleteByUUID(uuid: UUID) = either<AppException, Droid> {
    droidRepository.deleteByUUID(uuid).bind()
}
