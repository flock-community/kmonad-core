package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.monads.Reader
import community.flock.kmonad.core.jedi.model.Jedi
import java.util.UUID


fun <R> getAllJedi() where R : HasJediRepository, R : HasLogger = Reader { r: R ->
    r.jediRepository.getAll().also { it.map { list -> r.logger.log(list.toString()) } }
}

fun <R> getJediByUUID(uuid: UUID) where R : HasJediRepository = Reader { r: R ->
    r.jediRepository.getByUUID(uuid)
}

fun <R> saveJedi(jedi: Jedi) where R : HasJediRepository = Reader { r: R ->
    r.jediRepository.save(jedi)
}

fun <R> deleteJediByUUID(uuid: UUID) where R : HasJediRepository = Reader { r: R ->
    r.jediRepository.deleteByUUID(uuid)
}
