echo off
if "%1" == "" (
  echo usage: one_table_shop.cmd table_name
) else (
  mvn install -Dschema="mini_shop_245547" -DpackageName="shop" -DskipTests  -Dincludes=b2c_group_buy_define
)
