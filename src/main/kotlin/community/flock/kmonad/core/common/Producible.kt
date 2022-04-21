package community.flock.kmonad.core.common

@Suppress("unused")
interface Producible<T, R> {
    suspend fun T.produce(): R
}
