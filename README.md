# Potato application

### What is this?
This is a Spring Boot based application, which represents a  "Potato Market".

It handles suppliers and their products "a bag of potato".

### How to use?
* Clone the repository
* Execute `mvn clean install` to build the application
* Execute `java -jar target/potato-dropwizard-application-1.0-SNAPSHOT.jar db migrate config.yml` to create H2 database tables
* Execute `java -jar target/potato-dropwizard-application-1.0-SNAPSHOT.jar server config.yml` to start the application

You can interact with the application via Postman or other REST tools.
* http://localhost:8081/tasks/dummyPopulator
  * POST: Populate dummy data (It can run successfully only once!)

* http://localhost:8080/api/suppliers
  * GET: Retrieve all of the suppliers
  * POST: Requires a valid `Supplier` as RequestBody

* http://localhost:8080/api/bags\[?count=Integer\]
  * GET: Retrieve `count` amount of potato bags.
  As requested this parameter is optional, the default value is 3.
  To retrieve all of the potato bags, use negative number, like `-1`.

* http://localhost:8080/api/bags
  * POST: Requires a valid `PotatoBag` as RequestBody
