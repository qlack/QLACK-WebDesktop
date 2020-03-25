## QLACK WebDesktop Installation Manual

This manual contains instructions on how to deploy and use the QLACK WebDesktop application.

### Deployment

1) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file:
**server.port**: the desired port which will serve the application on your machine (default 8082)
**spring.datasource.url**: the url connection of the database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8)
**spring.datasource.username**: the username the connect to the database
**spring.datasource.password**: the password the connect to the database
**system.default.language**: the locale of the default system language (i.e 'en', 'el', 'fr' or 'de')

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

9) Make a clean installation of the application by executing the following command on the root folder of the project:
`mvnw.cmd clean install -Dmaven.test.skip=true` (for Windows OS)
`./mvnw clean install -Dmaven.test.skip=true` (for Linux OS)

10) Create the database schema by executing the command: 
`CREATE DATABASE QlackWebDesktop CHARACTER SET utf8 COLLATE utf8_general_ci;`

11) 


