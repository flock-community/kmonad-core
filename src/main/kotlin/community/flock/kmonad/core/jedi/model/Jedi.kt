package community.flock.kmonad.core.jedi.model

import community.flock.kmonad.core.common.Data
import java.util.UUID

data class Jedi(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int
) : Data
