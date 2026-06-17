FROM ubuntu:24.04

RUN apt update && apt install -y \
    openjdk-25-jdk \
    maven \
    nginx \
    git \
    openssh-server \
    php-cli \
    mysql-client \
    curl \
    && apt clean

RUN mkdir -p /run/sshd
RUN echo 'root:Hello@123' | chpasswd
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

COPY docker/web/nginx.conf /etc/nginx/sites-available/default
COPY docker/web/start.sh /start.sh

RUN chmod +x /start.sh

EXPOSE 8080 22

CMD ["/start.sh"]
