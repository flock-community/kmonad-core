package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID

interface SithDependencies : HasSithRepository, HasLogger
interface SithContext : SithDependencies

suspend fun SithContext.getAllSith() = sithRepository.getAll()
    .also { logger.log(it.toString()) }

suspend fun SithContext.getSithByUUID(uuid: UUID) = sithRepository.getByUUID(uuid)

suspend fun SithContext.saveSith(sith: Sith) = sithRepository.save(sith)

suspend fun SithContext.deleteSithByUUID(uuid: UUID) = sithRepository.deleteByUUID(uuid)
