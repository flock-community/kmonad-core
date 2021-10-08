package community.flock.kmonad.core.common.define

interface Logger : Dependency {
    fun log(s: String)
    fun error(s: String)
    fun warn(s: String)
}
