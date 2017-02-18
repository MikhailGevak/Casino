#!/bin/bash
if [ -z "$1" ] || [ -z "$2" ]; then 
  echo "You have to set 2 hosts name for services (one for Wallet Service and one for Game Service)"
else
	walletHost="-Dwallet.rest.server=$1"
	gameHost="-Dgame.rest.server=$2"
	
	cd ../tests
	echo "Run tests with params: $walletHost $gameHost"
	mvn compile exec:java $walletHost $gameHost
fi