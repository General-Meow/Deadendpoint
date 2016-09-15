# Deadendpoint
Tiny project that simulates a slow running or dead endpoint

To build the project use:
./gradlew run

Send requests to http://localhost:4567/api
Default is to wait for 5 seconds before responding
Respond after 1 second http://localhost:4567/api?sleep=1000
Dont respond http://localhost:4567/api?die
