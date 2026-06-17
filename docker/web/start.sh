#!/bin/bash

service ssh start
service nginx start

echo "Waiting for MySQL..."
until mysqladmin ping -h mysql -u root -pHello@123 --silent; do
  sleep 2
done

echo "MySQL is ready. Starting Spring Boot..."

java -jar /app/target/*.jar \
  --server.port=8081 \
  --spring.datasource.url=jdbc:mysql://mysql:3306/A-Heng_oulong-db \
  --spring.datasource.username=root \
  --spring.datasource.password=Hello@123