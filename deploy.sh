#!/usr/bin/env bash

docker build -t questionbot:latest -f Dockerfile --no-cache .

cmd="docker run -e DISCORD_TOKEN='$1' questionbot:latest"

eval $cmd
