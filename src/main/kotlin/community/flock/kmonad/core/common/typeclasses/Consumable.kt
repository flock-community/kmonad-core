package community.flock.kmonad.core.common.typeclasses

@Suppress("unused")
interface Consumable<T, R> {
    suspend fun T.consume(): R
}
