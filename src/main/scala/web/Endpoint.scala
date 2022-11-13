package web

import scala.util.matching.Regex

val varPattern: Regex = "\\{[a-zA-Z]+\\}".r
val varPatternAtEnd: Regex = "^.*\\{[a-zA-Z]+\\}$".r

enum Method:
  case GET, POST, PUT, PATCH, DELETE

private def resource2ResourcePattern(resource: String): ResourcePattern = {
  if (varPatternAtEnd.matches(resource)) {
    ResourcePattern((varPattern.split(resource)
                               .map(Regex.quote(_))
                               .mkString(".+") + ".+").r)
  } else {
    ResourcePattern(varPattern.split(resource)
                              .map(Regex.quote(_))
                              .mkString(".+").r)
  }
}

class ResourcePattern(resource: Regex) {
  def satisfy(resource: String): Boolean = this.resource.matches(resource)
}

class Endpoint(method: Method, resource: ResourcePattern, processor: HttpRequest => HttpResponse) {
  def this(method: Method, resource: String, processor: HttpRequest => HttpResponse) = this(method, resource2ResourcePattern(resource), processor)

  def satisfy(request: HttpRequest): Boolean = method.toString.equals(request.method) && resource.satisfy(request.resource)

  def process(request: HttpRequest): HttpResponse = processor.apply(request)
}
