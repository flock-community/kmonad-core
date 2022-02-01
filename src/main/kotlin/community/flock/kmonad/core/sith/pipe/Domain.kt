package community.flock.kmonad.core.sith.pipe

import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.sith.data.Sith
import java.util.UUID


suspend fun <R> R.getAll() where R : Has.SithRepository, R : Has.Logger = sithRepository.getAll()
    .also { logger.log(it.toString()) }

suspend fun <R> R.getByUUID(uuid: UUID) where R : Has.SithRepository = sithRepository.getByUUID(uuid)

suspend fun <R> R.save(sith: Sith) where R : Has.SithRepository = sithRepository.save(sith)

suspend fun <R> R.deleteByUUID(uuid: UUID) where R : Has.SithRepository = sithRepository.deleteByUUID(uuid)
