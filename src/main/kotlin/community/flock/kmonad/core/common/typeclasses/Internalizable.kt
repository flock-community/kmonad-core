package community.flock.kmonad.core.common.typeclasses

@Suppress("unused")
interface Internalizable<T, R> {
    suspend fun T.internalize(): R
}
