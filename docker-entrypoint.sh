#!/bin/sh

if [ -n "${APP_CONFIG_BPM_HOST}" ];then
  sed -i -e "s@\"bpmHost\": \".*\"@\"bpmHost\": \"${APP_CONFIG_BPM_HOST}\"@g" ./static/app.config.json
fi

java $JAVA_OPTS -jar app.jar --isUseExternalStaticResourceLocation=true --externalStaticResourceLocation="file:/opt/static"