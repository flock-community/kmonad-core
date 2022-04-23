package community.flock.kmonad.core.common.typeclasses

@Suppress("unused")
interface Externalizable<T, R> {
    suspend fun T.externalize(): R
}
