package community.flock.kmonad.core.sith.data

import community.flock.kmonad.core.common.define.Data
import java.util.UUID

data class Sith(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int
) : Data
