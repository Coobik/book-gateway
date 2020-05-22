#!/bin/sh

echo set java 11.0
export JAVA_HOME=`/usr/libexec/java_home -v 11.0`

echo building books...
cd books
mvn clean package
cd ..

echo building authors...
cd authors
mvn clean package
cd ..

echo building web-sockets...
cd web-sockets
mvn clean package
cd ..

echo building gateway...
cd gateway
./gradlew clean build
cd ..

echo building frontend...
cd frontend
./gradlew clean build
cd ..

echo building brief-aggregator...
cd brief-aggregator
./gradlew clean build
cd ..

echo jars build complete
