package community.flock.kmonad.core.common.define

interface HasLogger {
    val logger: Logger
}

interface Logger {

    fun log(string: String = "This is a log line")

    fun error(string: String = "This is an error")

    fun warn(string: String = "This is a warning")

}
