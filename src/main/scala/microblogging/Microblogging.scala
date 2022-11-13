package microblogging

import web.{Endpoint, Server, HttpResponse}

@main
def startMicroBlogging() =
  val server = Server(Endpoint("/post", request => {
    println("Processing: " + request)
    HttpResponse()
  }))
  server.run()