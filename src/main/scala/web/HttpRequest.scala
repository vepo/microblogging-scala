package web

import web.Method.GET

import java.io.BufferedReader

class HttpRequest(val method: Method,
                  val resource: String,
                  val version: String) {

  def this(method: String,
           resource: String,
           version: String) = this(Method.values.find(_.toString().equals(method)).get, resource, version)

  override def toString: String = s"HttpRequest[method=$method, resource=$resource, version=$version]"
}

def readRequest(input: BufferedReader): HttpRequest =
  val content = LazyList.continually(input.readLine())
                        .takeWhile(_.nonEmpty)
  val firstLine = content.head.split(" ")
  HttpRequest(firstLine(0), firstLine(1), firstLine(2))
