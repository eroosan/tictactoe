FROM openjdk:8-jre
ARG VERSION=unspecified

ARG GIT_COMMIT=unspecified
label git_commit=$GIT_COMMIT

ARG COMMIT_DATE=unspecified
label commit_date=$COMMIT_DATE

COPY target/*.jar /usr/app/

WORKDIR /usr/app

RUN echo "#!/bin/bash \n java -jar $VERSION" > ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
