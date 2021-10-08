package community.flock.kmonad.core.wielders.data

import community.flock.kmonad.core.common.define.Data
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.sith.data.Sith
import java.util.UUID

data class ForceWielder(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int,
    val forceType: Type,
) : Data {

    constructor(jedi: Jedi) : this(jedi.id, jedi.name, jedi.age, Type.LIGHT)

    constructor(sith: Sith) : this(sith.id, sith.name, sith.age, Type.DARK)

    enum class Type {
        DARK, LIGHT
    }

}

fun Jedi.toForceWielder() = ForceWielder(this)
fun Sith.toForceWielder() = ForceWielder(this)
