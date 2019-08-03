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
2.right click on the project and 
3.select Run As-->mvn buid
4.Set goals as spring-boot:run in run configuration.

#Unit test

Test the application using POSTMAN app,by giving respective URL.

#API-DOCUMENTATION

For Api documentation please go through below swagger URL

http://<server-name>:<port address>/swagger-ui.html#/
