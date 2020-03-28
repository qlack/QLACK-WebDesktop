#!/bin/sh

until nc -z -v -w30 qlack-webdesktop-db 3306
do
  echo "Waiting for MySQL Server"
  sleep 5
done

java -jar -Dspring.datasource.password=${DATABASE_ROOT_PASSWORD} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} /opt/app/qlack-webdesktop-apps-translations-management-app.jar