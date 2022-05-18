package community.flock.kmonad.core.sith

import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.sith.model.Sith
import java.util.UUID
import kotlin.Result.Companion.success

class TestSithRepository : SithRepository {

    companion object {
        const val sidiousUUID = "13c51af7-c5ff-4846-ab4e-a12b29772609"
        const val vaderUUID = "8221980d-4245-4dcd-b4ab-899a165de5e1"
    }

    private val allSith = mapOf(
        sidiousUUID to Sith(id = sidiousUUID, name = "Darth Sidious", age = 86),
        vaderUUID to Sith(id = vaderUUID, name = "Darth Vader", age = 45)
    )

    override suspend fun getAll() = success(allSith.map { it.value })

    override suspend fun getByUUID(uuid: UUID) = runCatching { allSith[uuid.toString()] ?: throw NotFound(uuid) }

    override suspend fun save(sith: Sith) = runCatching {
        if (allSith.contains(sith.id)) throw Conflict(UUID.fromString(sith.id))
        else sith
    }

    override suspend fun deleteByUUID(uuid: UUID) = getByUUID(uuid)

}
