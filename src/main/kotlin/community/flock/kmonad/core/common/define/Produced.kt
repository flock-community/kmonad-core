package community.flock.kmonad.core.common.define

interface Produced<T> {
    fun produce(): T
}
