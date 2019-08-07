<template>
  <div class="version-manage">
    <div class="btn-wrap">
      <el-button
        type="primary"
        size="small"
        @click="handleBtn()"
      >
        同步微信开放平台小程序代码模板
      </el-button>
    </div>

    <el-table
      class="version-manage-table"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="templateId"
        label="模板ID"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="userVersion"
        label="模板版本号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="当前包版本"
      >
        <template slot-scope="scope">
          <el-select
            v-model="scope.row.versionVal"
            placeholder="请选择"
            @change='handleClickSelect(scope.row.packageVersion)'
            size="mini"
          >
            <el-option
              v-for="item in scope.row.version"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column
        prop="userDesc"
        align="center"
        label="模板描述"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        align="center"
        label="模板添加时间"
        width="200"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="开发appid"
        width="200"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="开发app名称"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="开发者"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="是否删除"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.delFlag ===0">正常</span>
          <span v-if="scope.row.delFlag ===1">删除</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="是否当前使用"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.currentInUse ===1"
          >当前使用</span>
          <span
            class="nuuseSpan"
            v-if="scope.row.currentInUse ===0"
          >否</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <div class="lastDiv">
            <span
              style="cursor:pointer"
              v-for="(item,index) in operationData"
              :key="index"
              @click="handleOperationClick(scope.row,index)"
            >{{item}}</span>

          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { synchronizationRequest, smallProManRequest, setNowVersionRequest } from '@/api/system/programManage'
export default {
  name: 'versionManage',
  data () {
    return {
      tableData: [],
      version: [{
        value: '1',
        label: '普通版本'
      }, {
        value: '2',
        label: '好物版本'
      }],
      // tableData: [{
      //   id: '216',
      //   num: '1.28.2',
      //   version: [{
      //     value: '选项1',
      //     label: '普通版本'
      //   }, {
      //     value: '选项2',
      //     label: '好物版本'
      //   }],
      //   versionDescribe: '8.6测试版本1',
      //   time: '2019-08-06 09：16：33',
      //   appid: 'wxeaeb5c37a376f415',
      //   appName: '微铺宝b2c商城',
      //   developer: '帅飞',
      //   isDelete: '正常',
      //   isUse: '当前使用',
      //   operation: '',
      //   selectVal: ''
      // }, {
      //   id: '216',
      //   num: '1.28.2',
      //   version: [{
      //     value: '选项1',
      //     label: '普通版本'
      //   }, {
      //     value: '选项2',
      //     label: '好物版本'
      //   }],
      //   versionDescribe: '8.6测试版本1',
      //   time: '2019-08-06 09：16：33',
      //   appid: 'wxeaeb5c37a376f415',
      //   appName: '微铺宝b2c商城',
      //   developer: '帅飞',
      //   isDelete: '正常',
      //   isUse: '否',
      //   operation: '',
      //   selectVal: ''
      // }, {
      //   id: '216',
      //   num: '1.28.2',
      //   version: [{
      //     value: '选项1',
      //     label: '普通版本'
      //   }, {
      //     value: '选项2',
      //     label: '好物版本'
      //   }],
      //   versionDescribe: '8.6测试版本1',
      //   time: '2019-08-06 09：16：33',
      //   appid: 'wxeaeb5c37a376f415',
      //   appName: '微铺宝b2c商城',
      //   developer: '帅飞',
      //   isDelete: '正常',
      //   isUse: '当前使用',
      //   operation: ''
      // }, {
      //   id: '216',
      //   num: '1.28.2',
      //   version: [{
      //     value: '选项1',
      //     label: '普通版本'
      //   }, {
      //     value: '选项2',
      //     label: '好物版本'
      //   }],
      //   versionDescribe: '8.6测试版本1',
      //   time: '2019-08-06 09：16：33',
      //   appid: 'wxeaeb5c37a376f415',
      //   appName: '微铺宝b2c商城',
      //   developer: '帅飞',
      //   isDelete: '正常',
      //   isUse: '当前使用',
      //   operation: '',
      //   selectVal: ''
      // }],
      operationData: ['批量上传并提交审核', '查看当前版本操作日志', '设置为当前使用版本']
    }
  },
  mounted () {
    // 初始化数据
    this.defaluteData()
  },
  methods: {
    // 初始化数据
    defaluteData () {
      let obj = {
        'page': 1
      }
      smallProManRequest(obj).then((res) => {
        if (res.error === 0) {
          res.content.dataList.map((item, index) => {
            item.version = this.version
            if (item.packageVersion === 1) {
              item.versionVal = '普通版本'
            } else if (item.packageVersion === 2) {
              item.versionVal = '好物版本'
            }
          })

          this.tableData = res.content.dataList
        }
        console.log(res)
      })
    },
    // 当前版本下拉框选中
    handleClickSelect (data) {
      console.log(data)
    },
    // 操作点击处理
    handleOperationClick (row, index) {
      console.log(row, index)
      setNowVersionRequest(row.templateId).then((res) => {
        if (res.error === 0) {
          this.defaluteData()
        }
        console.log(res)
      })
    },
    // 同步微信开放平台小程序
    handleBtn () {
      synchronizationRequest().then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.defaluteData()
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.version-manage {
  .btn-wrap {
    background: #fff;
    padding: 15px;

    .el-button--primary {
      background: #7495bf;
      border-color: #7495bf;

      &:active {
        background: #6f97c2;
        border-color: #6f97c2;
      }
    }
  }
  .useSpan {
    font-weight: 700;
    color: #fff;
    padding: 4px;
    border-radius: 5px;
    background-color: #739e73;
  }
  .lastDiv {
    height: 100%;
    display: flex;
    flex-direction: column;
    span {
      &:hover {
        color: #999;
      }
    }
  }
}

.version-manage {
  .version-manage-table {
    margin-top: 10px;
  }
}
</style>
