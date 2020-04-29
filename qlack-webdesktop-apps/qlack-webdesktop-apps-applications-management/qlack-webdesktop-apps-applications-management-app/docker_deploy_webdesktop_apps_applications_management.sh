#!/bin/sh

until nc -z -v -w30 qlack-webdesktop-db 3306
do
	echo "Waiting for MySQL Server"
	sleep 5
done

if [ "$SPRING_PROFILES_ACTIVE" = "sso" ]
then
	java -jar -Dspring.datasource.password=${DATABASE_ROOT_PASSWORD} -Dspring.profiles.active=sso -Dwebdesktop.scheme=${WEBDESKTOP_SCHEME} -Doauth2.provider.url=${WEBDESKTOP_URL} -Dspring.security.oauth2.client.registration.master.client-id=${KEYCLOAK_CLIENT} -Dspring.security.oauth2.client.registration.master.client-name=${KEYCLOAK_CLIENT} -Dspring.security.oauth2.client.registration.master.client-secret=${KEYCLOAK_CLIENT_SECRET} /opt/app/qlack-webdesktop-apps-applications-management-app.jar
else
	java -jar -Dspring.datasource.password=${DATABASE_ROOT_PASSWORD} /opt/app/qlack-webdesktop-apps-applications-management-app.jar
fi