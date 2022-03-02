package community.flock.kmonad.core.droids.model

import community.flock.kmonad.core.common.define.Data
import java.util.UUID

data class Droid(
    override val id: String = UUID.randomUUID().toString(),
    val designation: String,
    val type: Type
) : Data {

    enum class Type {
        Protocol, Astromech
    }

}
