#!/bin/bash
echo "Killing process Game Service $1"
kill -9 `cat ../pids/game_pid$1.txt`
rm ../pids/game_pid$1.txt