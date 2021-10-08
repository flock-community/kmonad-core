package community.flock.kmonad.core.common.define

import org.litote.kmongo.coroutine.CoroutineClient

interface HasDatabaseClient {
    val databaseClient: CoroutineClient
}
