package community.flock.kmonad.core.common.define

sealed interface Has {

    interface Logger {
        val logger: community.flock.kmonad.core.common.define.Logger
    }

    interface JediRepository {
        val jediRepository: community.flock.kmonad.core.jedi.pipe.Repository
    }

    interface SithRepository {
        val sithRepository: community.flock.kmonad.core.sith.pipe.Repository
    }

    interface DroidRepository {
        val droidRepository: community.flock.kmonad.core.droids.pipe.Repository
    }

}
