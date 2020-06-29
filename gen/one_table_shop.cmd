echo off
if "%1" == "" (
  echo usage: one_table_shop.cmd table_name
) else (
  mvn install -Dschema="jmd_shop" -DpackageName="shop" -DskipTests  -Dincludes=%1%
)
