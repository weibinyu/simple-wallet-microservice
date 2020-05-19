# simple-wallet-microservice
simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players.
This application fulfills the functional requirements(while honoring the constraints) provided by the challenge and
persist data across after restart is possible because of the h2 database used. But to be honest, the application is not yet production-ready. 

Reasons are the following:
-Tests are not well constructed yet(the code are tested using postman during development, even though the mvcTest does test whether if the 
 API fulfills functional requirements and constrains it is not well constructed and, to be honest looks dirty)
-API in the /test path should not exist in the application, this way of testing can cause security problems. Need to try other methods
-The application is not secure, no security measure is taken at all.
-Poor exception handling. A custom Exception Handler is created but no real content.

In order to run the applicaiton, clone it from repository and run demoApplication class under src/main/java/com/example/demo/ in ide. Mvc test cases can be find at src/test/java/com/example/demo/

Postman is recommended for sending HTTP requests to the application. Following URLs are supported:
-localhost:8080/api/balance?username=userA       (get request)
-localhost:8080/api/transaction_history?username=userA           (get request)
-localhost:8080/api/credit?username=userA         (put request)
-localhost:8080/api/withdraw?username=userA       (put request)
Note that you have to send username as param in the URL else request will be blocked by pre handler, since there are only three preset
users(userA with balance 1000, userB with balance 5000 and userTest with balance 0. userTest should not be used in this case since it is created for mvcTesting purpose) in the database, one of them need to be provided. For put requests a JSON object has to be provided with transactionID and amount as parameter i.e.{ "transactionID":"3","amount":5000}

Also URL localhost:8080/h2 can give access to the h2 database. The user name is root and the password is password.
