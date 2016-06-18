package features

import test.application.PlayApplication
import cucumber.api.scala.{EN, ScalaDsl}

object ServerPerScenarioHooks extends ScalaDsl with EN {
  Before { scenario =>
    PlayApplication.startTestServer()
  }

  After { scenario =>
    PlayApplication.stopTestServer()
  }
}
