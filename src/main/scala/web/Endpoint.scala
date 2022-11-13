package web

import scala.util.matching.Regex

val varPattern: Regex = "\\{[a-zA-Z]+\\}".r
val varPatternAtEnd: Regex = "^.*\\{[a-zA-Z]+\\}$".r

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

class Endpoint(resource: ResourcePattern, processor: HttpRequest => HttpResponse) {
  def this(resource: String, processor: HttpRequest => HttpResponse) = this(resource2ResourcePattern(resource), processor)

  def satisfy(request: HttpRequest): Boolean = resource.satisfy(request.resource)

  def process(request: HttpRequest): HttpResponse = processor.apply(request)
}
