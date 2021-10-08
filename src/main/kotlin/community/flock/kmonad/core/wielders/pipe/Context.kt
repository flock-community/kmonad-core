package community.flock.kmonad.core.wielders.pipe

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.core.sith.pipe.Context as SithContext
import community.flock.kmonad.core.sith.pipe.Repository as SithRepository

interface Context : JediContext, SithContext {
    override val jediRepository: JediRepository
    override val sithRepository: SithRepository
    override val logger: Logger
}
