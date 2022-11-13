package web

import java.io.BufferedReader

class HttpRequest(val method: String,
                  val resource: String,
                  val version: String) {
  override def toString: String = s"HttpRequest[method=$method, resource=$resource, version=$version]"
}

def readRequest(input: BufferedReader): HttpRequest =
  val content = LazyList.continually(input.readLine())
                        .takeWhile(_.nonEmpty)
  val firstLine = content.head.split(" ")
  HttpRequest(firstLine(0), firstLine(1), firstLine(2))
