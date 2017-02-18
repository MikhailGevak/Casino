#!/bin/bash    
cd ../game-service

if
 ! [ -z "$1" ]; then 
    serverPort="-Drest.server.port=$1"
fi

if
 ! [ -z "$2" ]; then 
    walletHost="-Dwallet.rest.server=$2"
fi

echo "Running wallet service with params: $serverPort $walletHost"
nohup mvn compile exec:java -Dexec.args="src/main/resources/game.properties" $serverPort $walletHost> ../logs/game$3.log 0</dev/null 2>&1&
echo $! > ../pids/game_pid$3.txt