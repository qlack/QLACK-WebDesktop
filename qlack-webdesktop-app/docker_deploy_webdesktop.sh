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

java -jar -Dspring.datasource.password=${DATABASE_ROOT_PASSWORD} -Dsystem.default.language=${SYSTEM_DEFAULT_LANGUAGE} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} /opt/app/qlack-webdesktop-app.jar --apps.url=${APPS_URL}