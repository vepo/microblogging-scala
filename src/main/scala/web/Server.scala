package web

import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.{ServerSocket, Socket}
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}


class Server(val port: Int, val endpoints: Endpoint*) {

  def this(endpoints: Endpoint*) = this(8080, endpoints: _*)

  private def process(socket: Socket, context: ExecutionContext) = Future {
    println("Doing " + Thread.currentThread().getName)
    val in = BufferedReader(InputStreamReader(socket.getInputStream))
    val out = PrintWriter(socket.getOutputStream)
    val request = readRequest(in)
    val endpoint = endpoints.find(_.satisfy(request))
    endpoint.map(_.process(request))
    println(request)
    // this works, but i don’t know if it 100% meets the standard.
    // might need a `\r` at the end of every line.
    out.print(httpResponse(request))
    out.flush()

    out.close
    in.close
    socket.close
  }(context)

  def run() =
    println(s"Hello World $port")
    val currentThreadExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))
    val server = ServerSocket(port)
    while true do
      process(server.accept(), currentThreadExecutionContext)

  def httpResponse(request: HttpRequest) = "OK"
}