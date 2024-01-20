FROM node:17-alpine as node_build
COPY ./frontend /frontend
WORKDIR /frontend
RUN npm install --force
RUN npm run build

FROM ubuntu:latest AS spring_build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY ./backend /backend
COPY ./build.sh /
RUN --mount=type=secret,id=_env,dst=/etc/secrets/.env ./build.sh
COPY --from=node_build /frontend/build/ /backend/src/main/resources/public
RUN cd backend && ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=spring_build /backend/build/libs/flipmemo-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
