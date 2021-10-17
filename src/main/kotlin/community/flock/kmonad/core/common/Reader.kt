package community.flock.kmonad.core.common

class Reader<D, out A>(val provide: (D) -> A) {

    inline fun <B> map(crossinline fa: (A) -> B): Reader<D, B> = Reader { fa(provide(it)) }

    inline fun <B> flatMap(crossinline fa: (A) -> Reader<D, B>): Reader<D, B> = Reader { fa(provide(it)).provide(it) }

    companion object Factory {
        fun <D, A> just(a: A): Reader<D, A> = Reader { a }

        fun <D> ask(): Reader<D, D> = Reader { it }
    }

}
