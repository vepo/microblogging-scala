package web

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.Assertions.assert
import web.Method.GET

class EndpointTest extends AnyFlatSpec {

  "An static endpoint" should "not satisfy if the resources is different" in {
    val endpoint = Endpoint(GET, "/post", _ => HttpResponse())
    val request = HttpRequest("GET", "/post-2", "HTTP/1.1")
    assert(endpoint.satisfy(request) == false)
  }

  "An dynamic endpoint" should "satisfy if the resources has the same pattern" in {
    val endpoint = Endpoint(GET, "/post/{id}", _ => HttpResponse())
    val request = HttpRequest("GET", "/post/123", "HTTP/1.1")
    assert(endpoint.satisfy(request))
  }

  "An dynamic endpoint" should "satisfy if the resources has the same pattern in the middle" in {
    val endpoint = Endpoint(GET, "/post/{id}/meta", _ => HttpResponse())
    val request = HttpRequest("GET", "/post/123/meta", "HTTP/1.1")
    assert(endpoint.satisfy(request))
  }


  "An dynamic endpoint" should "satisfy if many resources" in {
    val endpoint = Endpoint(GET, "/user/{name}/post/{id}/meta", _ => HttpResponse())
    val request = HttpRequest("GET", "/user/vepo/post/123/meta", "HTTP/1.1")
    assert(endpoint.satisfy(request))
  }

  "An static endpoint" should "satisfy if the resources and the method are the same" in {
    val endpoint = Endpoint(GET, "/post", _ => HttpResponse())
    val request = HttpRequest("GET", "/post", "HTTP/1.1")
    assert(endpoint.satisfy(request))
  }

  "An static endpoint" should "not satisfy if the resources is the same but the method is different" in {
    val endpoint = Endpoint(GET, "/post", _ => HttpResponse())
    val request = HttpRequest("POST", "/post", "HTTP/1.1")
    assert(endpoint.satisfy(request) == false)
  }

}
