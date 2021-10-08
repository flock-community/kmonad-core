package community.flock.kmonad.core.jedi.pipe

import community.flock.kmonad.core.common.Reader
import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.jedi.data.Jedi
import java.util.UUID

fun <D> getAll() where D : HasRepository, D : HasLogger = Reader { d: D ->
    d.jediRepository.getAll()
        .also { it.map { flow -> d.logger.log(flow.toString()) } }
}

fun <D> getByUUID(uuid: UUID) where D : HasRepository = Reader { d: D ->
    d.jediRepository.getByUUID(uuid)
}

fun <D> save(jedi: Jedi) where D : HasRepository = Reader { d: D ->
    d.jediRepository.save(jedi)
}

fun <D> deleteByUUID(uuid: UUID) where D : HasRepository = Reader { d: D ->
    d.jediRepository.deleteByUUID(uuid)
}
