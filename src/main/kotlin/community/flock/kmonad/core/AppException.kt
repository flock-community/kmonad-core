package community.flock.kmonad.core

import community.flock.kmonad.core.common.monads.Either
import community.flock.kmonad.core.common.monads.Either.Companion.left
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.Reader.Factory.just
import java.util.UUID

sealed class AppException(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    class Conflict(uuid: UUID?, cause: Throwable? = null) : AppException("${uuid ?: "Entity"} already exists", cause)
    class NotFound(uuid: UUID, cause: Throwable? = null) : AppException("$uuid Not found", cause)
    class BadRequest(cause: Throwable? = null) : AppException("Bad Request", cause)
    class InternalServerError(cause: Throwable? = null) : AppException("Internal Server Error", cause)
}

fun <R> AppException.toReader() = just<R, IO<Either<AppException, Nothing>>>(IO { left() })
