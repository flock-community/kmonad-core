package community.flock.kmonad.core.droids

import arrow.core.computations.either
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.droids.model.Droid
import kotlinx.coroutines.flow.Flow
import java.util.UUID


context(HasDroidRepository)
suspend fun getAll() = either<AppException, Flow<Droid>> {
    droidRepository.getAll().bind()
}

context(HasDroidRepository)
suspend fun getByUUID(uuid: UUID) = either<AppException, Droid> {
    droidRepository.getByUUID(uuid).bind()
}

context(HasDroidRepository)
suspend fun save(droid: Droid) = either<AppException, Droid> {
    droidRepository.save(droid).bind()
}

context(HasDroidRepository)
suspend fun deleteByUUID(uuid: UUID) = either<AppException, Droid> {
    droidRepository.deleteByUUID(uuid).bind()
}
