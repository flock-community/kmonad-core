package community.flock.kmonad.core.common.typeclasses

@Suppress("unused")
interface Producible<T, R> {
    suspend fun T.produce(): R
}
