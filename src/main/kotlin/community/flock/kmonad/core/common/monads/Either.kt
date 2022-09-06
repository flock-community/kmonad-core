package community.flock.kmonad.core.common.monads

import community.flock.kmonad.core.common.monads.Either.Left
import community.flock.kmonad.core.common.monads.Either.Right

sealed class Either<out A, out B> private constructor() {

    data class Left<out A>(val value: A) : Either<A, Nothing>()

    data class Right<out B>(val value: B) : Either<Nothing, B>()

    fun isLeft() = this is Left
    fun isRight() = this is Right

    companion object {
        fun <A> A.left() = Left(this)
        fun <B> B.right() = Right(this)
    }

    inline fun <C> map(f: (B) -> C): Either<A, C> = flatMap { Right(f(it)) }

    inline fun <C> mapLeft(f: (A) -> C): Either<C, B> = fold({ Left(f(it)) }, ::Right)

    inline fun <C> fold(ifLeft: (A) -> C, ifRight: (B) -> C): C = when (this) {
        is Right -> ifRight(value)
        is Left -> ifLeft(value)
    }

    fun getOrNull() = when (this) {
        is Left -> null
        is Right -> value
    }

    fun swap(): Either<B, A> = fold({ Right(it) }, { Left(it) })

}

inline fun <A, B, C> Either<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> = when (this) {
    is Right -> f(value)
    is Left -> this
}

fun <A, B> Either<A, B>.getOrHandle(default: (A) -> B) = when (this) {
    is Left -> default(value)
    is Right -> value
}
