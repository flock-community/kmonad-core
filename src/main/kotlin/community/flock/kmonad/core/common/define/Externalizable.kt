package community.flock.kmonad.core.common.define

interface Externalizable<T> {
    fun externalize(): T
}
