package community.flock.kmonad.core.wielders.data

import community.flock.kmonad.core.common.define.Data
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.core.wielders.data.ForceWielder.Type.DARK
import community.flock.kmonad.core.wielders.data.ForceWielder.Type.LIGHT
import java.util.UUID

data class ForceWielder(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val age: Int,
    val forceType: Type,
) : Data {

    enum class Type {
        DARK, LIGHT
    }

}

fun Jedi.toForceWielder() = ForceWielder(id, name, age, LIGHT)
fun Sith.toForceWielder() = ForceWielder(id, name, age, DARK)
