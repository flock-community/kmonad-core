package community.flock.kmonad.core.common

import community.flock.kmonad.core.common.define.Logger
import org.junit.jupiter.api.Assertions

object TestLogger : Logger {

    override fun log(s: String) {
        Assertions.assertTrue(s.isNotBlank())
    }

    override fun error(s: String) {
        Assertions.assertTrue(s.isNotBlank())
    }

    override fun warn(s: String) {
        Assertions.assertTrue(s.isNotBlank())
    }

}
