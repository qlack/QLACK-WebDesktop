# QLACK WebDesktop Installation Manual

This manual contains instructions on how to deploy and use the QLACK WebDesktop application.

## Manual Deployment

1) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8082) <br/>
**spring.datasource.url**: the url connection of the database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

2) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.host**: the url under which QLACK WebDesktop will be served (ex. http://localhost:8082) <br/>
**webdesktop.scheme**: the http protocol (http or https) <br/>
**oauth2.provider.url**: the url of the SSO provider home page (ex. http://localhost:8080/auth) <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>
**webdesktop.administrator.username**: the user id of the user who will be the QLACK WebDesktop system administrator <br/>

3) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-applications-management\qlack
-webdesktop-apps-applications-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8090) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /applicationsManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

4) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

5) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-applications
-management\qlack-webdesktop-apps-applications-management-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.scheme**: the http protocol (http or https) <br/>
**oauth2.provider.url**: the url under which QLACK WebDesktop will be served <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>

6) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-translations-management\qlack
-webdesktop-apps-translations-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8091) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /translationsManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

7) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

8) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-translations
-management\qlack-webdesktop-apps-translations-management-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.scheme**: the http protocol (http or https) <br/>
**oauth2.provider.url**: the url under which QLACK WebDesktop will be served <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>

9) Navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-user-profile-management\qlack
-webdesktop-apps-user-profile-management-app\src\main\resources_ folder and edit the following properties in the_application.properties_ file: <br/>
**server.port**: the desired port which will serve the application on your machine (default 8092) <br/>
**server.servlet.context-path**: the context path under which the application will be accessible (default /userProfileManagement) <br/>
**spring.datasource.url**: the url connection of the QLACK WebDesktop database (ex. jdbc:mysql://localhost:3306/QlackWebDesktop?useUnicode=yes&characterEncoding=UTF-8) <br/>
**spring.datasource.username**: the username the connect to the database <br/>
**spring.datasource.password**: the password the connect to the database <br/>

10) On the same folder update the following properties of the _configuration.yaml_ file: <br/>
**appUrl**: the url under which the application is served (hostname:port) <br/>
**appPath**: the context path of the application (same value as _server.servlet.context-path_) <br/>
**proxyAppPath**: the reversed path under which QLACK WebDesktop will use the application (if SSO is used, the value should be equal to the _appPath_ value) <br/>

11) If SSO is enabled, navigate to the _QLACK-WebDesktop\qlack-webdesktop-apps\qlack-webdesktop-apps-user-profile
-management\qlack-webdesktop-apps-user-profile-management-app\src\main\resources_ folder and edit the following properties in the_application-sso.properties_ file: <br/>
**webdesktop.scheme**: the http protocol (http or https) <br/>
**oauth2.provider.url**: the url under which QLACK WebDesktop will be served <br/>
**spring.security.oauth2.client.registration.master.client-id**: the id of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-name**: the name of the SSO client <br/>
**spring.security.oauth2.client.registration.master.client-secret**: the key for accessing the restricted SSO client <br/>

12) Make a clean installation of the application by executing the following command on the root folder of the
 project (Java 8 must be installed and set as JAVA_HOME): <br/>
`mvnw.cmd clean install -Dmaven.test.skip=true` (for Windows OS) <br/>
`./mvnw clean install -Dmaven.test.skip=true` (for Linux OS) <br/>

13) Create the database schema by executing the command: <br/>
`CREATE DATABASE QlackWebDesktop CHARACTER SET utf8 COLLATE utf8_general_ci;`

14) Deploy the system QLACK WebDesktop applications by executing the commands: <br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/target/qlack-webdesktop-apps-applications-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-translations-management/qlack-webdesktop-apps-translations-management-app/target/qlack-webdesktop-apps-translations-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar qlack-webdesktop-apps/qlack-webdesktop-apps-user-profile-management/qlack-webdesktop-apps-user-profile-management-app/target/qlack-webdesktop-apps-user-profile-management.jar &>/dev/null &` <br/><br/>
If SSO is enabled, deploy the applications with the following command:
`nohup java -jar -Dspring.profiles.active=sso qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/target/qlack-webdesktop-apps-applications-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar -Dspring.profiles.active=sso qlack-webdesktop-apps/qlack-webdesktop-apps-translations-management/qlack-webdesktop-apps-translations-management-app/target/qlack-webdesktop-apps-translations-management.jar &>/dev/null &` <br/><br/>
`nohup java -jar -Dspring.profiles.active=sso qlack-webdesktop-apps/qlack-webdesktop-apps-user-profile-management/qlack-webdesktop-apps-user-profile-management-app/target/qlack-webdesktop-apps-user-profile-management.jar &>/dev/null &` <br/><br/>

15) Deploy the QLACK Web Desktop application by executing the command: <br/>
`nohup java -jar -Dsystem.default.language=en qlack-webdesktop-app/target/qlack-webdesktop-app.jar --apps.url=http://127.0.0.1:8090/applicationsManagement/configuration,http://127.0.0.1:8091/translationsManagement/configuration,http://127.0.0.1:8092/userProfileManagement/configuration &>/dev/null &` <br/> <br/>
If SSO is enabled, deploy QLACK WebDesktop with the following command:
`nohup java -jar -Dsystem.default.language=en -Dspring.profiles.active=sso qlack-webdesktop-app/target/qlack-webdesktop-app.jar --apps.url=http://127.0.0.1:8090/applicationsManagement/configuration,http://127.0.0.1:8091/translationsManagement/configuration,http://127.0.0.1:8092/userProfileManagement/configuration &>/dev/null &` <br/> <br/>
The argument _system.default.language_ is used in order to define the default language of the system and its possible values are 'en', 'el', 'fr' and 'de'.
Using the argument _apps.url_ you can specify the url of the .yaml configuration file of an application which will be registered in the system. The .yaml file should be accessible by performing a GET HTTP request on the provided url and multiple configuration files can be given during the deployment with the comma separator.

## Docker Deployment

QLACK WebDesktop can also be deployed using Docker by executing the command `docker-compose up`.
This command will the following five containers:
1) qlack-webdesktop-db
This container contains a MySQL 8 installation with the schema 'QlackWebDesktop' in which all the other containers will be connected. <br/>
2) qlack-webdesktop-applications-management
This container runs a deployment of the Applications/Users Management application and prevents any external communication to the application by not publishing any ports. <br/>
3) qlack-webdesktop-translations-management
This container runs a deployment of the Translations Management application and prevents any external communication to the application by not publishing any ports. <br/>
4) qlack-webdesktop-user-profile-management
This container runs a deployment of the User Profile Management application and prevents any external communication to the application by not publishing any ports. <br/>
5) qlack-webdesktop-app
This container runs a deployment of the QLACK Web Desktop application, in which the three custom applications are already integrated. The application is accessible on port 80, which is published by the container.

Before running the containers, the following variables of the _.env_ file should be edited: <br/>
**WEBDESKTOP_PORT=80**: the port under which QLACK WebDesktop will be accessible on your system <br/>
**DATABASE_ROOT_PASSWORD**: the password of the ROOT user of the MySQL application <br/>
**SPRING_PROFILES_ACTIVE**: the security profile of the system ('sso' if SSO will be enabled, or 'default' if no security will be used) <br/>
**SYSTEM_DEFAULT_LANGUAGE**: the default language for all the users (possible values are 'en', 'el', 'fr' and 'de') <br/>
**APPS_URL**: the urls of the .yaml configuration files of the applications to be integrated (sepated by comma)

If SSO will be enabled, the following variables should be also edited: <br/>
**WEBDESKTOP_URL**: the url under which QLACK WebDesktop will be served (ex. http://qlack-webdesktop:8082) <br/>
**WEBDESKTOP_SCHEME**: the http protocol (http or https) <br/>
**KEYCLOAK_AUTH_URL**: the url of the SSO provider home page (ex. http://keycloak-provider:8080/auth) <br/>
**KEYCLOAK_CLIENT**: the id of the SSO client <br/>
**KEYCLOAK_CLIENT_SECRET**: the key for accessing the restricted SSO client <br/>
**WEBDESKTOP_ADMIN**: the user id of the user who will be the QLACK WebDesktop system administrator <br/>

The Docker Deployment can also start a Keycloak provider on a separate container, along with the other QLACK WebDesktop containers, by running the command: `docker-compose docker-compose-sso.yml up`.
When using the Keycloak container, the following .env variables must be also set: <br/>
**KEYCLOAK_AUTH_URL**: http://qlack-webdesktop-keycloak:8080/auth <br/>
**KEYCLOAK_ADMIN_USERNAME**: the username of the Keycloak admin user <br/>
**KEYCLOAK_ADMIN_PASSWORD**: the password of the Keycloak admin user <br/>
**KEYCLOAK_PORT**: the port under which Keycloak will be accessible on your system <br/>

Also, after the **qlack-webdesktop-keycloak** container is started, navigate to its _/auth_ page in order to create the client to be used by QLACK WebDesktop and update the .env variables **KEYCLOAK_CLIENT** **KEYCLOAK_CLIENT_SECRET** accordingly. 
Restart the applications container, by executing: `docker-compose docker-compose-sso.yml down` and `docker-compose docker-compose-sso.yml up`


## Applications Integration

A QLACK WebDesktop application is identified based on the following information: <br/>
**applicationName**: the identifier name of the application <br/>
**groupName**: the group in which the application is registered <br/>
**appUrl**: the url where the application is deployed <br/>
**appPath**: the context path of the application home page  <br/>
**proxyAppPath**: the reverse proxy path under which QLACK WebDesktop will serve the application <br/>
**icon**: the url of the application icon
**iconSmall**: the url of the small application icon
**width**: the default width of the application window <br/>
**minWidth**: the maximum width of the application window <br/>
**height**: the default height of the application window <br/>
**minHeight**: the maximum height of the application window <br/>
**active**: defines if the application is active or not <br/>
**multipleInstances**: defines if the application window can be opened multiple times or not <br/>
**resizable**: defines if the application window can be resised or not <br/>
**draggable**: defines if the application window can be dragged or not <br/>
**closable**: defines if the application window can be closed or not <br/>
**minimizable**: defines if the application window can be minimized or not <br/>
**maximizable**: defines if the application window can be maximized or not <br/>
**showTitle**: defines if the application title will be shown or not <br/>
**system**: defines if the application is accessible only by the system administrator or not <br/>
**restrictAccess**: defines if the application will be accessible based on user/user group rules or not <br/>
**version**: the current version of the application <br/>

QLACK WebDekstop provides three ways of integrating new applications:

### Form
1) Open the Applications/Users Management application and select 'Create'
2) Fill in the fields
3) Click on Save

### Yaml file
1) Open the Applications/Users Management application and select 'Upload'
2) Select a .yaml configuration file
3) Click on Submit

### Deployment
While deploying QLACK WebDesktop, you can provide the url of the .yaml configuration file on the _apps_url_ argument.

An example of a configuration file can be found here: https://github.com/qlack/QLACK-WebDesktop/blob/master/qlack-webdesktop-apps/qlack-webdesktop-apps-applications-management/qlack-webdesktop-apps-applications-management-app/src/main/resources/configuration.yaml

## Translations

QLACK WebDesktop is deployed with multiple ready-to-read translations for multiple languages, which are defined at the _QLACK-WebDesktop/qlack-webdesktop-app/src/main/resources/qlack-lexicon-config.yaml_ file. 
The values of these translations can be updated before each QLACK WebDesktop re-deploy by changing the value and adding the pair key:value `forceUpdate: true` below each updated key. For example:
```
- group: webdesktop-ui
  locale: en
  keys:
    - groupOthers: Others
      forceUpdate: true
```

Alternatively, the Translations Management application provides the ability of viewing and updating all the system translations, as well as creating new languages and copying the already existing translations of another language.

When creating new applications or/and new application groups, their translations must be set up via the Translations Management application.

## Applications Messages

QLACK WebDesktop also provides the ability of exchanging messages between the integrated applications and/or the main application, by using the [QLACK QPubSub Javascript library](https://www.npmjs.com/package/@qlack/qpubsub) or the [QLACK QPubSub Angular Wrapper](https://www.npmjs.com/package/@qlack/qng-pubsub).

Each integrated application, which will be making use of the QLACK QPubSub library, must be registered as a _client_ before being able to _publish_ messages or _subscribe_ to message topics.

QLACK WebDesktop provides the following ready-to-use topics:
1) QNotifications: display notifications on the main application window
```
this.qPubSubService.publish('QNotifications', 'Notifications Message');
```
2) QUserInformation: retrieve the information of the logged user
```
this.qPubSubService.subscribe('QUserInformationRequest', () => {
    this.qPubSubService.subscribe('QUserInformationRequest', (message: QPubSub.Message) => {
		console.log(message);
	});
});
```
3) QDefaultLanguage: retrieve the system language locale
```
this.qPubSubService.subscribe('QDefaultLanguageRequest', () => {
    this.qPubSubService.subscribe('QDefaultLanguageResponse', (message: QPubSub.Message) => {
		console.log(message);
	});
});
```