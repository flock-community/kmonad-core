package community.flock.kmonad.core.common.define

interface Externalized<T> {
    fun externalize(): T
}
