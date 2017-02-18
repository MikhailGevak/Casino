#!/bin/bash
echo "Killing process Wallet Service $1"
kill -9 `cat ../pids/wallet_pid$1.txt`
rm ../pids/wallet_pid$1.txt