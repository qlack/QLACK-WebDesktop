#!/bin/sh

until nc -z -v -w30 qlack-webdesktop-applications-management 8090
do
	echo "Waiting for qlack-webdesktop-applications-management Server"
	sleep 5
done

until nc -z -v -w30 qlack-webdesktop-translations-management 8091
do
	echo "Waiting for qlack-webdesktop-translations-management Server"
	sleep 5
done

until nc -z -v -w30 qlack-webdesktop-user-profile-management 8092
do
	echo "Waiting for qlack-webdesktop-user-profile-management Server"
	sleep 5
done

if [ "$SPRING_PROFILES_ACTIVE" = "sso" ]
then
	java -jar -Dserver.port=${WEBDESKTOP_PORT} -Dspring.datasource.password=${DATABASE_ROOT_PASSWORD} -Dsystem.default.language=${SYSTEM_DEFAULT_LANGUAGE} -Dspring.profiles.active=sso -Dwebdesktop.host=${WEBDESKTOP_URL} -Dwebdesktop.scheme=${WEBDESKTOP_SCHEME} -Doauth2.provider.url=${KEYCLOAK_AUTH_URL} -Dspring.security.oauth2.client.registration.master.client-id=${KEYCLOAK_CLIENT} -Dspring.security.oauth2.client.registration.master.client-name=${KEYCLOAK_CLIENT} -Dspring.security.oauth2.client.registration.master.client-secret=${KEYCLOAK_CLIENT_SECRET} -Dwebdesktop.administrator.username=${WEBDESKTOP_ADMIN} /opt/app/qlack-webdesktop-app.jar --apps.url=${APPS_URL}
else
	java -jar -Dserver.port=${WEBDESKTOP_PORT} -Dspring.datasource.password=${DATABASE_ROOT_PASSWORD} -Dsystem.default.language=${SYSTEM_DEFAULT_LANGUAGE} /opt/app/qlack-webdesktop-app.jar --apps.url=${APPS_URL}
fi

