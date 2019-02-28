#!/bin/bash

set -e

PACKAGER=${1}
INSTALLER_TYPE=${2}
MODULE_PATH=${3}
INPUT=${4}
OUTPUT=${5}
JAR=${6}
VERSION=${7}
FILE_ASSOCIATIONS=${8}
APP_ICON=${9}
EXTRA_BUNDLER_ARGUMENTS=${10}

${PACKAGER} \
  create-installer ${INSTALLER_TYPE} \
  --module-path ${MODULE_PATH} \
  --verbose \
  --echo-mode \
  --add-modules java.base,java.datatransfer,java.desktop,java.instrument,java.logging,java.management,java.scripting,java.sql,java.xml,jdk.attach,jdk.jdi,jdk.jsobject,jdk.unsupported,jdk.unsupported.desktop,jdk.xml.dom,javafx.controls,javafx.graphics,javafx.fxml,java.naming,jdk.charsets \
  --input "${INPUT}" \
  --output "${OUTPUT}" \
  --name VDM \
  --main-jar ${JAR} \
  --version ${VERSION} \
  --jvm-args '-Djdk.tls.client.protocols=TLSv1.2 --add-opens javafx.base/com.sun.javafx.reflect=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED' \
  --file-associations ${FILE_ASSOCIATIONS} \
  --icon $APP_ICON \
  $EXTRA_BUNDLER_ARGUMENTS \
  --class vdm.Main
