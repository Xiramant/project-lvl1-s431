.DEFAULT_GOAL := update-build-run

update-build-run: update build run

update:
	./mvnw versions:update-properties
	./mvnw versions:display-plugin-updates

run:
	java -jar ./target/Casino-1.0-SNAPSHOT-jar-with-dependencies.jar

build:
	./mvnw clean package
