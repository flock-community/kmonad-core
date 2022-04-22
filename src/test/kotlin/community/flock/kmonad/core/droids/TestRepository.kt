package community.flock.kmonad.core.droids

import arrow.core.continuations.EffectScope
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.droids.model.Droid
import kotlinx.coroutines.flow.asFlow
import java.util.UUID

object TestRepository : Repository {

    const val c3poUUID = "4db46c64-d164-4efe-a2f2-7b2c7c689ded"
    const val r2d2UUID = "3dfd4324-0805-455e-a749-9fea2f9dc18a"

    private val allDroids = mapOf(
        c3poUUID to Droid(id = c3poUUID, designation = "C3PO", type = Droid.Type.Protocol),
        r2d2UUID to Droid(id = r2d2UUID, designation = "R2D2", type = Droid.Type.Astromech)
    )

    context(EffectScope<AppException>)
    override suspend fun getAll() = allDroids.map { it.value }.asFlow()

    context(EffectScope<AppException>)
    override suspend fun getByUUID(uuid: UUID) = allDroids[uuid.toString()] ?: shift(NotFound(uuid))

    context(EffectScope<AppException>)
    override suspend fun save(droid: Droid) =
        if (allDroids.contains(droid.id)) shift(Conflict(UUID.fromString(droid.id)))
        else droid

    context(EffectScope<AppException>)
    override suspend fun deleteByUUID(uuid: UUID) = getByUUID(uuid)

}
