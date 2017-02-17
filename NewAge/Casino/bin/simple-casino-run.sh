#!/bin/bash
if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ]; then 
  echo "You have to set 3 ports numbers for services (one for Wallet Service and two for Game Service)"
else
  ./run-wallet.sh $1
  ./run-game.sh $2 1
  ./run-game.sh $3 2
fi
