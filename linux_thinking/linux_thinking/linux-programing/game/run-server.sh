#!/bin/bash
sudo docker build -t server .
sudo docker run -it --rm --net=host --name game server