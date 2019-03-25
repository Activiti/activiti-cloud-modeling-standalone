if [[ -n "${APP_CONFIG_BPM_HOST}" ]]
then
  replace="\/"
  encoded=${APP_CONFIG_BPM_HOST//\//$replace}
  sed -e "s/\"bpmHost\": \".*\"/\"bpmHost\": \"${encoded}\"/g" \
    -i ./static/app.config.json
fi

java $JAVA_OPTS -DisUseExternalStaticResourceLocation=true -jar app.jar