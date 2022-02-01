package community.flock.kmonad.core.jedi.pipe

import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.common.Reader
import community.flock.kmonad.core.jedi.data.Jedi
import java.util.UUID


fun <R> getAll() where R : Has.JediRepository, R : Has.Logger = Reader { r: R ->
    r.jediRepository.getAll()
        .also { it.map { flow -> r.logger.log(flow.toString()) } }
}

fun <R> getByUUID(uuid: UUID) where R : Has.JediRepository = Reader { r: R ->
    r.jediRepository.getByUUID(uuid)
}

fun <R> save(jedi: Jedi) where R : Has.JediRepository = Reader { r: R ->
    r.jediRepository.save(jedi)
}

fun <R> deleteByUUID(uuid: UUID) where R : Has.JediRepository = Reader { r: R ->
    r.jediRepository.deleteByUUID(uuid)
}
