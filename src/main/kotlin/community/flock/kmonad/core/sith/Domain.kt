package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID

context(HasSithRepository, HasLogger)
suspend fun getAll() = sithRepository.getAll()
    .also { logger.log(it.toString()) }

context(HasSithRepository)
suspend fun getByUUID(uuid: UUID) = sithRepository.getByUUID(uuid)

suspend fun HasSithRepository.save(sith: Sith) = sithRepository.save(sith)

suspend fun HasSithRepository.deleteByUUID(uuid: UUID) = sithRepository.deleteByUUID(uuid)
