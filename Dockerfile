FROM ubuntu:latest
LABEL authors="slippery"

ENTRYPOINT ["top", "-b"]