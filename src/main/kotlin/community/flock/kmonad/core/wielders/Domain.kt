package community.flock.kmonad.core.wielders

import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.HasLogger
import community.flock.kmonad.core.common.monads.Either.Companion.left
import community.flock.kmonad.core.common.monads.Either.Companion.right
import community.flock.kmonad.core.common.monads.flatMap
import community.flock.kmonad.core.common.monads.getOrHandle
import community.flock.kmonad.core.common.monads.orNull
import community.flock.kmonad.core.jedi.HasJediRepository
import community.flock.kmonad.core.sith.HasSithRepository
import community.flock.kmonad.core.wielders.model.ForceWielder
import community.flock.kmonad.core.wielders.model.toForceWielder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.util.UUID
import community.flock.kmonad.core.jedi.getAll as getAllJedi
import community.flock.kmonad.core.jedi.getByUUID as getJediByUUID
import community.flock.kmonad.core.sith.getAll as getAllSith
import community.flock.kmonad.core.sith.getByUUID as getSithByUUID


interface Domain : HasJediRepository, HasSithRepository, HasLogger


@ExperimentalCoroutinesApi
suspend fun Domain.getAll() = getAllJedi<Domain>()
    .provide(this)
    .runUnsafe()
    .getOrHandle { throw it }
    .map { it.toForceWielder() } + getAllSith()
    .map { it.toForceWielder() }
    .also { logger.log() }

suspend fun Domain.getByUUID(uuid: UUID): ForceWielder {
    val jedi = getJediByUUID<Domain>(uuid)
        .provide(this)
        .runUnsafe()
        .map { maybeJedi -> maybeJedi.map { it.toForceWielder() } }
        .flatMap { option -> option.fold({ NotFound(uuid).left() }, { it.right() }) }
        .orNull()
    val sith = runCatching { getSithByUUID(uuid) }
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
