package community.flock.kmonad.core.jedi

import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.monads.Reader
import community.flock.kmonad.core.jedi.model.Jedi
import java.util.UUID

interface JediDependencies : HasJediRepository, HasLogger
interface JediContext : JediDependencies

fun getAllJedi() = Reader { ctx: JediContext ->
    ctx.jediRepository.getAll().also { it.map { list -> ctx.logger.log(list.toString()) } }
}

fun getJediByUUID(uuid: UUID) = Reader { ctx: JediContext ->
    ctx.jediRepository.getByUUID(uuid)
}

fun saveJedi(jedi: Jedi) = Reader { ctx: JediContext ->
    ctx.jediRepository.save(jedi)
}

fun deleteJediByUUID(uuid: UUID) = Reader { ctx: JediContext ->
    ctx.jediRepository.deleteByUUID(uuid)
}
