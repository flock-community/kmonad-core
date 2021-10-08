package community.flock.kmonad.core.jedi.data

import community.flock.kmonad.core.common.define.Data
import java.util.UUID

data class Jedi(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int
) : Data