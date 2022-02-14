package community.flock.kmonad.core.common.define

interface HasLogger {
    val logger: Logger
}

interface Logger : Dependency {

    fun log(string: String)

    fun error(string: String)

    fun warn(string: String)

}
