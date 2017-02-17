#!/bin/bash    
cd wallet-service

if ! [ -z "$1" ]; then 
    args="-Drest.server.port=$1"
fi
echo "Running wallet service with params: $args"
nohup mvn compile exec:java -Dexec.args="src/main/resources/wallet.properties" $args> ../wallet.log 0</dev/null 2>&1&
echo $! > ../wallet_pid.txt