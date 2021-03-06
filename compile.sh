#!/bin/bash
cd "$(dirname "$0")"

if ! dpkg -s libmaven3-core-java > /dev/null || ! dpkg -s maven > /dev/null; then
	echo "[ERROR] maven3 is not installed, please install packages : maven and libmaven3-core-java"
	exit
fi

if ! dpkg -s mysql-server > /dev/null; then
	echo "[ERROR] mysql-server is not installed"
	exit
fi

if ! dpkg -s mysql-client > /dev/null; then
	echo "[ERROR] mysql-client is not installed"
	exit
fi

echo -n "Copying dependencies..."
mvn dependency:copy-dependencies > /dev/null
echo "Done"

echo "Executing Database creation script"
echo -n "MySQL user : "
read user
mysql -u $user -p < camping-db-creation.sql
echo "DB created !"

mvn package -Dmaven.test.skip=true
cp target/campingsimulator2017-1.0-SNAPSHOT.jar .
cp -r target/dependency-jars .
mvn clean

