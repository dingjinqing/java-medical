echo off
if "%1" == "" (
  echo usage: one_table_shop.cmd table_name
) else (
  mvnw install -Dschema="mini_shop_471752" -DpackageName="shop" -DskipTests  -Dincludes=%1%
)
