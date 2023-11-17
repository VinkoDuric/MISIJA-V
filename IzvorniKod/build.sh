#!/bin/bash

cat << EOF > backend/src/main/resources/application.properties
spring.datasource.url=$POSTGRES_URL
spring.datasource.username=$POSTGRES_USERNAME
spring.datasource.password=$POSTGRES_PASSWORD
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

flipmemo.token.expire.after.minutes=60

spring.mail.host=$MAIL_HOST
spring.mail.port=$MAIL_PORT
spring.mail.username=$MAIL_USERNAME
spring.mail.password=$MAIL_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties..mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
EOF