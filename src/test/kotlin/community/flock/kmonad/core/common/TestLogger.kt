package community.flock.kmonad.core.common

import org.junit.jupiter.api.Assertions

object TestLogger : Logger {

    override fun log(string: String) {
        Assertions.assertTrue(string.isNotBlank())
    }

    override fun error(string: String) {
        Assertions.assertTrue(string.isNotBlank())
    }

    override fun warn(string: String) {
        Assertions.assertTrue(string.isNotBlank())
    }

}
