@echo off
if "%1" == "" ( 
  echo usage: one_table_main.cmd table_name
) else (
  mvnw install -Dschema="mini_main" -DpackageName="main" -DskipTests -Dincludes=%1%
)

