# simple-wallet-microservice
simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players


This application fulfils the functional requirements(while honouring the constraints) provided by the chanllenge and
persist data across after restart is possible because of the h2 database used. But to be honest applicaiton is not yet product ready. 

Reasons are the following:
-Tests are not well constructed yet(the code are tested using postman during development, even though the mvcTest does test whether if the 
 api fulfils functional requirements and constrains it is not well constructed and to be honest looks dirty)
-Api in the /test path should not exist in the application, this way of testing can cause sercurity problem. Need to try other method
-The application is not sercure, no security measure is taken at all.
-Poor exception handling, has a created custom Exception Handler but no real content

In order to run the applicaiton, clone it from repository and run the demoApplication class in ide.
