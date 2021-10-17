package community.flock.kmonad.core.wielders.pipe

import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.wielders.data.ForceWielder
import community.flock.kmonad.core.wielders.data.toForceWielder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.util.UUID
import community.flock.kmonad.core.jedi.pipe.getAll as getAllJedi
import community.flock.kmonad.core.jedi.pipe.getByUUID as getJediByUUID
import community.flock.kmonad.core.sith.pipe.getAll as getAllSith
import community.flock.kmonad.core.sith.pipe.getByUUID as getSithByUUID

@ExperimentalCoroutinesApi
suspend fun <D> D.getAll() where D : Has.JediRepository, D : Has.SithRepository, D : Has.Logger = getAllJedi<D>()
    .provide(this)
    .runUnsafe()
    .getOrHandle { throw it }
    .map { it.toForceWielder() } + getAllSith()
    .map { it.toForceWielder() }

suspend fun <D> D.getByUUID(uuid: UUID): ForceWielder where D : Has.JediRepository, D : Has.SithRepository, D : Has.Logger {
    val jedi = getJediByUUID<D>(uuid)
        .provide(this)
        .runUnsafe()
        .map { it.toForceWielder() }
        .orNull()
    val sith = runCatching { getSithByUUID(uuid) }
        .map { it.toForceWielder() }
        .getOrNull()
    val forceWielder = with(jedi to sith) {
        when {
            bothAreNull() -> throw AppException.NotFound(uuid)
            bothAreNotNull() -> throw AppException.InternalServerError()
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
