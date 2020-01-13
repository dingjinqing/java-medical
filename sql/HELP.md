# Getting Started
支持对现有的sql文件的**读取**、**检查**、**执行**的功能
## 检查SQL

`java -jar -Dcheck -DcheckPath=通用sql文件夹的路径 *****.jat`

* -Dcheck 　　　开启sql校验的标识
* -DcheckPath　&nbsp;指定sql的通用的路径；
例如：全路径为`/Users/luguangyao/idea_work/java-mp/jmp/mp-service/src/main/resources/db/main/***.sql` ，通用路径就是`/Users/luguangyao/idea_work/java-mp/jmp/mp-service/src/main/resources/db` 

## 执行SQL
`java -jar -Dmain|-Dshop [-DshopId=123,456] -DsqlPath=精确的绝对路径 [-Dversion=**]  ***.jar`

* -Dmain|-Dshop　指定是执行的数据库是**main库**还是**shop库**
* -DshopId　　　&nbsp;&nbsp;(可选项)如果是只对部分shop库执行sql，那么可以通过这个参数指定相关的shop库。(多个库之间用英文逗号隔开) 
* -DsqlPath　　　&nbsp;&nbsp;指定要执行的sql文件的绝对路径
* -Dversion　　　&nbsp;&nbsp;是上线执行时,当前上线版本的版本号(由于包括历史sql在内的所有sql都在一个sql文件内，需要通过版本号来区分真正要执行的文件)，如果不填默认全部执行