#! /bin/bash

selfDir="$( cd "$(dirname "$0")" ; pwd -P )"
self="$selfDir/$( basename $0 )"
java=java

if [[ -z "$JAVA_HOME" ]]; then
  java="$JAVA_HOME/bin/java"
fi

if [[ ! -z "$JVM_ARGS" ]]; then
  echo "Running with custom JVM arguments"
fi
if [[ ! -z "$TAGS" ]]; then
  echo "Running with custom Cucumber tags"
fi
if [[ ! -z "$@" ]]; then
  echo "Running custom test set"
fi

exec "$java" -cp "$selfDir" $JVM_ARGS -jar "$self" --tags="${TAGS:-~@ignore}" "${@:-tests}"
exit 1
