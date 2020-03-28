FROM openjdk:8-jdk-alpine

ARG DATABASE_ROOT_PASSWORD

ARG SPRING_PROFILES_ACTIVE

ENV DATABASE_ROOT_PASSWORD ${DATABASE_ROOT_PASSWORD}

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE}

COPY target/qlack-webdesktop-apps-user-profile-management-app.jar /opt/app/qlack-webdesktop-apps-user-profile-management-app.jar

COPY docker_deploy_webdesktop_apps_user_profile_management.sh /scripts/docker_deploy_webdesktop_apps_user_profile_management.sh

RUN dos2unix /scripts/docker_deploy_webdesktop_apps_user_profile_management.sh

RUN chmod +x /scripts/docker_deploy_webdesktop_apps_user_profile_management.sh

ENTRYPOINT ["/scripts/docker_deploy_webdesktop_apps_user_profile_management.sh"]