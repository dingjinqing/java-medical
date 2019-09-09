<template>
  <div class="backgroundTaskList">
    <div class="backgroundTaskListMain">
      <div class="TaskListTop">
        <span>执行状态：</span>
        <el-select
          v-model="value"
          size="small"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div>
      <div class="footer">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            align="center"
            label="任务名称"
            prop="pageName"
          >
          </el-table-column>
          <el-table-column
            prop="params"
            label="参数"
            align="center"
            width="300"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="reason"
            label="失败原因"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="进度"
            align="center"
          >
            <template slot-scope="scope">
              <el-progress
                :text-inside="true"
                :stroke-width="24"
                :percentage="scope.row.speedOfProgress"
                status="success"
              ></el-progress>

            </template>
          </el-table-column>

          <el-table-column
            prop="speedOfProgressText"
            label="进度信息"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="remarks"
            label="备注"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="creatTime"
            label="创建时间"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="endTime"
            label="结束时间"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div
                class="operation"
                @click="handleToStop(scope.row)"
              >强行终止</div>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totle"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      options: [{
        value: '0',
        label: '--请选择--'
      }, {
        value: '1',
        label: '初始'
      }, {
        value: '2',
        label: '执行中'
      }, {
        value: '3',
        label: '执行完成'
      }, {
        value: '4',
        label: '执行失败'
      },
      {
        value: '5',
        label: '执行停止'
      }],
      value: '0',
      tableData: [
        {
          pageName: '批量提交小程序审核',
          params: ' "{\u0022template_id\u0022:229,\u0022user_version\u0022:\u00221.31.2\u0022,\u0022user_desc\u0022:\u00229.6\\u6d4b\\u8bd5\\u7248\\u672c1\u0022,\u0022create_time\u0022:\u00222019-09-06 14:30:58\u0022,\u0022del_flag\u0022:0,\u0022current_in_use\u0022:1,\u0022source_miniprogram_appid\u0022:\u0022wxeaeb5c37a376f415\u0022,\u0022source_miniprogram\u0022:\u0022\\u5fae\\u94fa\\u5b9db2c\\u5546\\u57ce\u0022,\u0022developer\u0022:\u0022\\u65b0\\u623f\\u5ba2\\u4e36\u0022,\u0022package_version\u0022:2}" ',
          status: '初始',
          reason: '进程终止',
          speedOfProgress: '40',
          speedOfProgressText: '处理完成',
          remarks: '我爱你',
          creatTime: '2018-05-14 13:22:07',
          endTime: '2018-06-14 13:22:07'
        },
        {
          pageName: '批量提交小程序审核',
          params: ' "{\u0022template_id\u0022:229,\u0022user_version\u0022:\u00221.31.2\u0022,\u0022user_desc\u0022:\u00229.6\\u6d4b\\u8bd5\\u7248\\u672c1\u0022,\u0022create_time\u0022:\u00222019-09-06 14:30:58\u0022,\u0022del_flag\u0022:0,\u0022current_in_use\u0022:1,\u0022source_miniprogram_appid\u0022:\u0022wxeaeb5c37a376f415\u0022,\u0022source_miniprogram\u0022:\u0022\\u5fae\\u94fa\\u5b9db2c\\u5546\\u57ce\u0022,\u0022developer\u0022:\u0022\\u65b0\\u623f\\u5ba2\\u4e36\u0022,\u0022package_version\u0022:2}" ',
          status: '初始',
          reason: '进程终止',
          speedOfProgress: '25',
          speedOfProgressText: '处理完成',
          remarks: '我爱你',
          creatTime: '2018-05-14 13:22:07',
          endTime: '2018-06-14 13:22:07'
        },
        {
          pageName: '批量提交小程序审核',
          params: ' "{\u0022template_id\u0022:229,\u0022user_version\u0022:\u00221.31.2\u0022,\u0022user_desc\u0022:\u00229.6\\u6d4b\\u8bd5\\u7248\\u672c1\u0022,\u0022create_time\u0022:\u00222019-09-06 14:30:58\u0022,\u0022del_flag\u0022:0,\u0022current_in_use\u0022:1,\u0022source_miniprogram_appid\u0022:\u0022wxeaeb5c37a376f415\u0022,\u0022source_miniprogram\u0022:\u0022\\u5fae\\u94fa\\u5b9db2c\\u5546\\u57ce\u0022,\u0022developer\u0022:\u0022\\u65b0\\u623f\\u5ba2\\u4e36\u0022,\u0022package_version\u0022:2}" ',
          status: '初始',
          reason: '进程终止',
          speedOfProgress: '56',
          speedOfProgressText: '处理完成',
          remarks: '我爱你',
          creatTime: '2018-05-14 13:22:07',
          endTime: '2018-06-14 13:22:07'
        },
        {
          pageName: '批量提交小程序审核',
          params: ' "{\u0022template_id\u0022:229,\u0022user_version\u0022:\u00221.31.2\u0022,\u0022user_desc\u0022:\u00229.6\\u6d4b\\u8bd5\\u7248\\u672c1\u0022,\u0022create_time\u0022:\u00222019-09-06 14:30:58\u0022,\u0022del_flag\u0022:0,\u0022current_in_use\u0022:1,\u0022source_miniprogram_appid\u0022:\u0022wxeaeb5c37a376f415\u0022,\u0022source_miniprogram\u0022:\u0022\\u5fae\\u94fa\\u5b9db2c\\u5546\\u57ce\u0022,\u0022developer\u0022:\u0022\\u65b0\\u623f\\u5ba2\\u4e36\u0022,\u0022package_version\u0022:2}" ',
          status: '初始',
          reason: '进程终止',
          speedOfProgress: '34',
          speedOfProgressText: '处理完成',
          remarks: '我爱你',
          creatTime: '2018-05-14 13:22:07',
          endTime: '2018-06-14 13:22:07'
        },
        {
          pageName: '批量提交小程序审核',
          params: ' "{\u0022template_id\u0022:229,\u0022user_version\u0022:\u00221.31.2\u0022,\u0022user_desc\u0022:\u00229.6\\u6d4b\\u8bd5\\u7248\\u672c1\u0022,\u0022create_time\u0022:\u00222019-09-06 14:30:58\u0022,\u0022del_flag\u0022:0,\u0022current_in_use\u0022:1,\u0022source_miniprogram_appid\u0022:\u0022wxeaeb5c37a376f415\u0022,\u0022source_miniprogram\u0022:\u0022\\u5fae\\u94fa\\u5b9db2c\\u5546\\u57ce\u0022,\u0022developer\u0022:\u0022\\u65b0\\u623f\\u5ba2\\u4e36\u0022,\u0022package_version\u0022:2}" ',
          status: '初始',
          reason: '进程终止',
          speedOfProgress: '67',
          speedOfProgressText: '处理完成',
          remarks: '我爱你',
          creatTime: '2018-05-14 13:22:07',
          endTime: '2018-06-14 13:22:07'
        },
        {
          pageName: '批量提交小程序审核',
          params: ' "{\u0022template_id\u0022:229,\u0022user_version\u0022:\u00221.31.2\u0022,\u0022user_desc\u0022:\u00229.6\\u6d4b\\u8bd5\\u7248\\u672c1\u0022,\u0022create_time\u0022:\u00222019-09-06 14:30:58\u0022,\u0022del_flag\u0022:0,\u0022current_in_use\u0022:1,\u0022source_miniprogram_appid\u0022:\u0022wxeaeb5c37a376f415\u0022,\u0022source_miniprogram\u0022:\u0022\\u5fae\\u94fa\\u5b9db2c\\u5546\\u57ce\u0022,\u0022developer\u0022:\u0022\\u65b0\\u623f\\u5ba2\\u4e36\u0022,\u0022package_version\u0022:2}" ',
          status: '初始',
          reason: '进程终止',
          speedOfProgress: '89',
          speedOfProgressText: '处理完成',
          remarks: '我爱你',
          creatTime: '2018-05-14 13:22:07',
          endTime: '2018-06-14 13:22:07'
        }
      ],
      currentPage: 1,
      totle: 1,
      pageCount: 1
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {

    },
    // 当前页改变
    handleCurrentChange () {

    },
    // 每页显示条数变化
    handleSizeChange () {

    },
    // 强行终止
    handleToStop (row) {
      console.log(row)
    }
  }
}
</script>
<style lang="scss" scoped>
.backgroundTaskList {
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .backgroundTaskListMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .TaskListTop {
      height: 80px;
      display: flex;
      align-items: center;
    }
  }
  .footer {
    margin-top: 10px;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      .el-checkbox {
        margin-left: -4px;
      }
    }
    .operation {
      cursor: pointer;
      &:hover {
        color: #333;
        text-decoration: underline;
      }
    }
  }
  .pagination {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
