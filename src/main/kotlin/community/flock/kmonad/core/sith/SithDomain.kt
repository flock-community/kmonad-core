package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID

context(HasSithRepository, HasLogger)
suspend fun getAllSith() = sithRepository.getAll()
    .also { logger.log(it.toString()) }

context(HasSithRepository)
suspend fun getSithByUUID(uuid: UUID) = sithRepository.getByUUID(uuid)

suspend fun HasSithRepository.saveSith(sith: Sith) = sithRepository.save(sith)

suspend fun HasSithRepository.deleteSithByUUID(uuid: UUID) = sithRepository.deleteByUUID(uuid)
