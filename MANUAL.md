## QLACK WebDesktop Installation Manual

This manual contains instructions on how to deploy and use the QLACK WebDesktop application.

### Deployment

1) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file:
**server.port**: the desired port which will serve the application on your machine (default 8082)
**spring.datasource.url**: the url connection of the database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8)
**spring.datasource.username**: the username the connect to the database
**spring.datasource.password**: the password the connect to the database

2) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-applications-management\qlack-webdesktop-apps-applications-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file:
**server.port**: the desired port which will serve the application on your machine (default 8090)
**server.servlet.context-path**: the context path under which the application will be accessible (default /applicationsManagement)
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8)
**spring.datasource.username**: the username the connect to the database
**spring.datasource.password**: the password the connect to the database

3) On the same folder update the following properties of the _configuration.yaml_ file:
**appUrl**: the url under which the application is served (hostname:port)
**appPath**: the context path of the application (same value as _server.servlet.context-path_)
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value)

5) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-translations-management\qlack-webdesktop-apps-translations-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file:
**server.port**: the desired port which will serve the application on your machine (default 8091)
**server.servlet.context-path**: the context path under which the application will be accessible (default /translationsManagement)
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8)
**spring.datasource.username**: the username the connect to the database
**spring.datasource.password**: the password the connect to the database

6) On the same folder update the following properties of the _configuration.yaml_ file:
**appUrl**: the url under which the application is served (hostname:port)
**appPath**: the context path of the application (same value as _server.servlet.context-path_)
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value)

7) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-user-profile-management\qlack-webdesktop-apps-user-profile-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file:
**server.port**: the desired port which will serve the application on your machine (default 8092)
**server.servlet.context-path**: the context path under which the application will be accessible (default /userProfileManagement)
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8)
**spring.datasource.username**: the username the connect to the database
**spring.datasource.password**: the password the connect to the database

8) On the same folder update the following properties of the _configuration.yaml_ file:
**appUrl**: the url under which the application is served (hostname:port)
**appPath**: the context path of the application (same value as _server.servlet.context-path_)
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value)

9) Make a clean installation of the application by executing the following command on the root folder of the project (Java 8 must be installed and set as JAVA_HOME):
`mvnw.cmd clean install -Dmaven.test.skip=true` (for Windows OS)
`./mvnw clean install -Dmaven.test.skip=true` (for Linux OS)

10) Create the database schema by executing the command: 
`CREATE DATABASE QlackWebDesktop CHARACTER SET utf8 COLLATE utf8_general_ci;`

11) Deploy the system QLACK WebDesktop applications by executing the commands:
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/target/qlack-webdesktop-apps-applications-management.jar &>/dev/null &`
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-translations-management/qlack-webdesktop-apps-translations-management-app/target/qlack-webdesktop-apps-translations-management.jar &>/dev/null &`
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-user-profile-management/qlack-webdesktop-apps-user-profile-management-app/target/qlack-webdesktop-apps-user-profile-management.jar &>/dev/null &`

12) Deploy the QLACK Web Desktop application by executing the command:
`nohup java -jar -Dsystem.default.language=en qlack-webdesktop-app/target/qlack-webdesktop-app.jar --apps.url=http://127.0.0.1:8090/applicationsManagement/configuration,http://127.0.0.1:8091/translationsManagement/configuration,http://127.0.0.1:8092/userProfileManagement/configuration &>/dev/null &` 
The argument _system.default.language_ is used in order to define the default language of the system and its possible values are 'en', 'el', 'fr' and 'de'.
Using the argument _apps.url_ you can specify the url of the .yaml configuration file of an application which will be registered in the system. The .yaml file should be accessible by performing a GET HTTP request on the provided url and multiple configuration files can be given during the deployment with the comma separator.

### Deployment using Docker

QLACK WebDesktop can also be deployed using Docker by executing the command `docker-compose up`.
This command will the following five containers:
1) qlack-webdesktop-db
This container contains 
2) qlack-webdesktop-app
3) qlack-webdesktop-applications-management
4) qlack-webdesktop-translations-management
5) qlack-webdesktop-user-profile-management 