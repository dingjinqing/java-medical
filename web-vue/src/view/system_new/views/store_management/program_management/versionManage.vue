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
            @change='handleClickSelect(scope.row,scope.row.versionVal)'
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
    <div class="footer">
      <div>每页20行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数微：{{this.totle}}</div>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="20"
        layout="prev, pager, next, jumper"
        :total="totle"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { synchronizationRequest, smallProManRequest, setNowVersionRequest, setCurrentVersionRequest } from '@/api/system/programManage'
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
      operationData: ['批量上传并提交审核', '查看当前版本操作日志', '设置为当前使用版本'],
      currentPage: 1,
      totle: null,
      pageCount: null
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
        'pageRows': 20,
        'currentPage': this.currentPage
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
          this.totle = res.content.page.totalRows
          this.pageCount = res.content.page.pageCount
          console.log(this.pageCount)
        }
        console.log(res)
      })
    },
    // 当前版本下拉框选中
    handleClickSelect (data, val) {
      console.log(data, val)
      let obj = {
        templateId: data.templateId,
        packageVersion: val
      }
      setCurrentVersionRequest(obj).then((res) => {
        if (res.error === 0) {
          this.defaluteData()
        }
        console.log(res)
      })
    },
    // 操作点击处理
    handleOperationClick (row, index) {
      console.log(row, index)
      switch (index) {
        case 0:
          break
        case 1:
          this.$router.push({
            name: 'programManage',
            params: {
              page: 'versionLog',
              userVersion: row.templateId
            }
          })
          break
        case 2:
          setNowVersionRequest(row.templateId).then((res) => {
            if (res.error === 0) {
              this.defaluteData()
            }
            console.log(res)
          })
          break
      }
    },
    // 同步微信开放平台小程序
    handleBtn () {
      synchronizationRequest().then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.defaluteData()
        }
      })
    },
    // 当前页发生变化
    handleCurrentChange () {
      this.defaluteData()
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

  .footer {
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
