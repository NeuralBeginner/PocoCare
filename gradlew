#!/usr/bin/env bash

# Basic gradle wrapper script
APP_BASE_NAME=${0##*/}
APP_HOME="$(dirname "$(readlink -f "$0")")"

# Java command
if [ -n "$JAVA_HOME" ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

# Default JVM options
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

# Execute Gradle
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS -Dorg.gradle.appname="$APP_BASE_NAME" -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"