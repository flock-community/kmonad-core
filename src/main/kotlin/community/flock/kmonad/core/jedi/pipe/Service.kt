package community.flock.kmonad.core.jedi.pipe

import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.common.Reader
import community.flock.kmonad.core.jedi.data.Jedi
import java.util.UUID


fun <D> getAll() where D : Has.JediRepository, D : Has.Logger = Reader { d: D ->
    d.jediRepository.getAll()
        .also { it.map { flow -> d.logger.log(flow.toString()) } }
}

fun <D> getByUUID(uuid: UUID) where D : Has.JediRepository = Reader { d: D ->
    d.jediRepository.getByUUID(uuid)
}

fun <D> save(jedi: Jedi) where D : Has.JediRepository = Reader { d: D ->
    d.jediRepository.save(jedi)
}

fun <D> deleteByUUID(uuid: UUID) where D : Has.JediRepository = Reader { d: D ->
    d.jediRepository.deleteByUUID(uuid)
}
