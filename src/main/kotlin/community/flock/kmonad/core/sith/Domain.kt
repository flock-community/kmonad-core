package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID


suspend fun <R> R.getAll() where R : HasSithRepository, R : HasLogger = sithRepository.getAll()
    .also { logger.log(it.toString()) }

suspend fun <R : HasSithRepository> R.getByUUID(uuid: UUID) = sithRepository.getByUUID(uuid)

suspend fun <R : HasSithRepository> R.save(sith: Sith) = sithRepository.save(sith)

suspend fun <R : HasSithRepository> R.deleteByUUID(uuid: UUID) = sithRepository.deleteByUUID(uuid)
