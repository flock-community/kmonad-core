package community.flock.kmonad.core.forcewielder

import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.monads.Either.Companion.left
import community.flock.kmonad.core.common.monads.Either.Companion.right
import community.flock.kmonad.core.common.monads.flatMap
import community.flock.kmonad.core.common.monads.getOrHandle
import community.flock.kmonad.core.forcewielder.model.ForceWielder
import community.flock.kmonad.core.forcewielder.model.toForceWielder
import community.flock.kmonad.core.jedi.HasJediRepository
import community.flock.kmonad.core.jedi.getAllJedi
import community.flock.kmonad.core.jedi.getJediByUUID
import community.flock.kmonad.core.sith.HasSithRepository
import community.flock.kmonad.core.sith.getAllSith
import community.flock.kmonad.core.sith.getSithByUUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import java.util.UUID


@ExperimentalCoroutinesApi
suspend fun <R> R.getAllForceWielders() where R : HasJediRepository, R : HasSithRepository, R : HasLogger = getAllJedi<R>()
    .provide(this)
    .runUnsafe()
    .getOrHandle { throw it }
    .map { it.toForceWielder() } + getAllSith()
    .getOrThrow().map { it.toForceWielder() }
    .also { logger.log() }

suspend fun <R> R.getForceWielderByUUID(uuid: UUID): ForceWielder where R : HasJediRepository, R : HasSithRepository, R : HasLogger {
    val jedi = getJediByUUID<R>(uuid)
        .provide(this)
        .runUnsafe()
        .map { maybeJedi -> maybeJedi.map { it.toForceWielder() } }
        .flatMap { option -> option.fold({ NotFound(uuid).left() }, { it.right() }) }
        .getOrNull()
    val sith = getSithByUUID(uuid)
        .map { it?.toForceWielder() }
        .getOrNull()
    val forceWielder = with(jedi to sith) {
        when {
            bothAreNull() -> throw NotFound(uuid).also { logger.warn() }
            bothAreNotNull() -> throw InternalServerError().also { logger.error() }
            else -> pick()
        }
    }
    return forceWielder
}


@ExperimentalCoroutinesApi
private operator fun Flow<ForceWielder>.plus(flow: Flow<ForceWielder>) = merge(this, flow)

private fun <A, B> Pair<A, B>.bothAreNull(): Boolean = (first === null && second === null)
private fun <A, B> Pair<A, B>.bothAreNotNull(): Boolean = (first !== null && second !== null)
private fun <A : Any> Pair<A?, A?>.pick(): A = first ?: second!!
