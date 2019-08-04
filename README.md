# ecommerce-auditlog

Custom audit log for Rest-apis.


Basic spring authentication is implemented in the program.
Also a custom implementation of audit-log in REST api. 

UserNames and passwords are hard-coded in the program.

Two Users will be there

1.admin - user which is having access CRUD access for all controllers.
credentials:
username:admin
password:password
2.user - a normal user which is having CRUD access for purchase controller and Read access for all other controllers.
no read access given for audit log controller.
username:user
password:password

#For running application 

1.clone the project.
2.Create a database named users_database in my-sql db using below SQL statement
   CREATE DATABASE `users_database` /*!40100 DEFAULT CHARACTER SET utf8 */;
   Note:database tables will be created automatically.
3.right click on the project and 
4.select Run As-->mvn buid
5.Set goals as spring-boot:run in run configuration.

#Unit test

Test the application using POSTMAN app,by giving respective URL.

#API-DOCUMENTATION

For Api documentation please go through below swagger URL

http://localhost:8085/swagger-ui.html
