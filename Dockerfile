FROM tomcat:9.0.83-jdk11-corretto-al2

COPY target/StudentManager-1.0-SNAPSHOT.war /usr/local/tomcat/webapps

