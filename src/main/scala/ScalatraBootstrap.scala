import com.saltsecurity.ars.server.APIServlet
import org.scalatra._

import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(
      urlPattern = "/api/*",
      handler = new APIServlet
    )
  }
}
