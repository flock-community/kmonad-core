package community.flock.kmonad.core.common.monads

import community.flock.kmonad.core.common.monads.Option.Companion.fromNullable
import community.flock.kmonad.core.common.monads.Option.None
import community.flock.kmonad.core.common.monads.Option.Some

sealed class Option<out A> {

    data class Some<A>(val value: A) : Option<A>()
    object None : Option<Nothing>()

    inline fun <B> map(f: (A) -> B): Option<B> = flatMap { a -> Some(f(a)) }

    inline fun <B> flatMap(f: (A) -> Option<B>): Option<B> = when (this) {
        is None -> this
        is Some -> f(value)
    }

    inline fun <R> fold(ifEmpty: () -> R, ifSome: (A) -> R): R = when (this) {
        is None -> ifEmpty()
        is Some<A> -> ifSome(value)
    }

    companion object {
        fun <A> fromNullable(a: A?): Option<A> = if (a != null) Some(a) else None
    }

}

fun <A> Option<A>.getOrNull(): A? = when (this) {
    is None -> null
    is Some<A> -> this.value
}

fun <A> A?.toOption() = fromNullable(this)
