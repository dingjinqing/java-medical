echo off
if "%1" == "" (
  echo usage: one_table_shop.cmd table_name
) else (
  mvn install -Dschema="mini_shop_471752" -DpackageName="shop" -DskipTests  -Dincludes=b2c_virtual_order
)
