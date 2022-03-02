package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.common.Reader
import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.jedi.model.Jedi
import java.util.UUID


fun <R> getAll() where R : HasJediRepository, R : HasLogger = Reader { r: R ->
    r.jediRepository.getAll()
        .also { it.map { flow -> r.logger.log(flow.toString()) } }
}

fun <R> getByUUID(uuid: UUID) where R : HasJediRepository = Reader { r: R ->
    r.jediRepository.getByUUID(uuid)
}

fun <R> save(jedi: Jedi) where R : HasJediRepository = Reader { r: R ->
    r.jediRepository.save(jedi)
}

fun <R> deleteByUUID(uuid: UUID) where R : HasJediRepository = Reader { r: R ->
    r.jediRepository.deleteByUUID(uuid)
}
