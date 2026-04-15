FROM maven:3.9-amazoncorretto-23
WORKDIR /usr/src/app
RUN yum install -y pango fontconfig freetype
COPY . .
EXPOSE 8080
CMD ["mvn", "jpro:run"]