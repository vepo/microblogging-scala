package microblogging

import web.Method.GET
import web.{Endpoint, HttpResponse, Server}

@main
def startMicroBlogging() =
  val server = Server(Endpoint(GET, "/post", request => {
    println("Processing: " + request)
    HttpResponse()
  }))
  server.run()