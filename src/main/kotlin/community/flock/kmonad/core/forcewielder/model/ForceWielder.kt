package community.flock.kmonad.core.forcewielder.model

import community.flock.kmonad.core.common.Data
import community.flock.kmonad.core.jedi.model.Jedi
import community.flock.kmonad.core.sith.model.Sith
import community.flock.kmonad.core.forcewielder.model.ForceWielder.Type.DARK
import community.flock.kmonad.core.forcewielder.model.ForceWielder.Type.LIGHT
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
