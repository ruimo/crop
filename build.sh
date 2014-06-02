#!/bin/sh

if test "$JAVA_HOME" = ""; then
    echo Set JAVA_HOME.
    exit 1
fi

sbt -java-home $JAVA_HOME
