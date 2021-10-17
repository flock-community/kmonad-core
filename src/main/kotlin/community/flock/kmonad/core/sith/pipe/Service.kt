package community.flock.kmonad.core.sith.pipe

import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.sith.data.Sith
import java.util.UUID


suspend fun <D> D.getAll() where D : Has.SithRepository, D : Has.Logger = sithRepository.getAll()
    .also { logger.log(it.toString()) }

suspend fun <D> D.getByUUID(uuid: UUID) where D : Has.SithRepository = sithRepository.getByUUID(uuid)

suspend fun <D> D.save(sith: Sith) where D : Has.SithRepository = sithRepository.save(sith)

suspend fun <D> D.deleteByUUID(uuid: UUID) where D : Has.SithRepository = sithRepository.deleteByUUID(uuid)
