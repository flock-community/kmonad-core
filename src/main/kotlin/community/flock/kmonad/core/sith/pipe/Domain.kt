package community.flock.kmonad.core.sith.pipe

import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.sith.data.Sith
import java.util.UUID


suspend fun <R> R.getAll() where R : HasSithRepository, R : HasLogger = sithRepository.getAll()
    .also { logger.log(it.toString()) }

suspend fun <R> R.getByUUID(uuid: UUID) where R : HasSithRepository = sithRepository.getByUUID(uuid)

suspend fun <R> R.save(sith: Sith) where R : HasSithRepository = sithRepository.save(sith)

suspend fun <R> R.deleteByUUID(uuid: UUID) where R : HasSithRepository = sithRepository.deleteByUUID(uuid)