
# url d'un service

http://localhost:8080/services/customerservice/customers/20/

* services : vient du servlet mapping dans web.xml
* customerservice : @Path("/customerservice/") sur la classe CustomerService
* customers/20/ : @Path("/customers/{id}/") sur la méthode getCustomer

