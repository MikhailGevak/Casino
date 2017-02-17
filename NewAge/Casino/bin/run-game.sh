#!/bin/bash    
cd ../game-service

if ! [ -z "$1" ]; then 
    args="-Drest.server.port=$1"
fi
echo "Running wallet service with params: $args"
nohup mvn compile exec:java -Dexec.args="src/main/resources/game.properties" $args> ../logs/game$2.log 0</dev/null 2>&1&
echo $! > ../bin/game_pid$2.txt