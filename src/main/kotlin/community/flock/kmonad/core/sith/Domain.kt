package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID

context(HasSithRepository, HasLogger)
suspend fun getAll() = sithRepository.getAll()
    .also { logger.log(it.toString()) }

context(HasSithRepository)
suspend fun getByUUID(uuid: UUID) = sithRepository.getByUUID(uuid)

context(HasSithRepository)
suspend fun save(sith: Sith) = sithRepository.save(sith)

context(HasSithRepository)
suspend fun deleteByUUID(uuid: UUID) = sithRepository.deleteByUUID(uuid)
