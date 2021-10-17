package community.flock.kmonad.core.common

class IO<out A>(val runUnsafe: () -> A) {

    inline fun <B> map(crossinline f: (A) -> B): IO<B> = IO { f(runUnsafe()) }

    inline fun <B> flatMap(crossinline f: (A) -> IO<B>): IO<B> = IO { f(runUnsafe()).runUnsafe() }

}
