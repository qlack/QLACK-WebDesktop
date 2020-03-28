FROM openjdk:8-jdk-alpine

ARG DATABASE_ROOT_PASSWORD

ARG SPRING_PROFILES_ACTIVE

ARG SYSTEM_DEFAULT_LANGUAGE

ARG APPS_URL

ENV DATABASE_ROOT_PASSWORD ${DATABASE_ROOT_PASSWORD}

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE}

ENV SYSTEM_DEFAULT_LANGUAGE ${SYSTEM_DEFAULT_LANGUAGE}

ENV APPS_URL ${APPS_URL}

COPY target/qlack-webdesktop-app.jar /opt/app/qlack-webdesktop-app.jar

COPY docker_deploy_webdesktop.sh /scripts/docker_deploy_webdesktop.sh

RUN dos2unix /scripts/docker_deploy_webdesktop.sh

RUN chmod +x /scripts/docker_deploy_webdesktop.sh

ENTRYPOINT ["/scripts/docker_deploy_webdesktop.sh"]