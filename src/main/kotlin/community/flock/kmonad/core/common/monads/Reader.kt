package community.flock.kmonad.core.common.monads

class Reader<R, out A>(val provide: (R) -> A) {

    inline fun <B> map(crossinline fa: (A) -> B): Reader<R, B> = Reader { fa(provide(it)) }

    inline fun <B> flatMap(crossinline fa: (A) -> Reader<R, B>): Reader<R, B> = Reader { fa(provide(it)).provide(it) }

    companion object Factory {
        fun <R, A> just(a: A): Reader<R, A> = Reader { a }

        fun <R> ask(): Reader<R, R> = Reader { it }
    }

}
