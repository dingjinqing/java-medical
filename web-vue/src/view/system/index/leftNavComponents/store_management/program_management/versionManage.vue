<template>
  <div class="version-manage">
    <div class="btn-wrap">
      <el-button
        type="primary"
        size="small"
        @click="handleBtn()"
      >
        {{this.$t('programVersion.synchronization')}}
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
        :label="$t('programVersion.templateID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="userVersion"
        :label="$t('programVersion.templateVersionNumber')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.currentPackageVersion')"
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
        :label="$t('programVersion.templateDescription')"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        align="center"
        :label="$t('programVersion.templateAdditionTime')"
        width="200"
      >
      </el-table-column>
      <!-- <el-table-column
        align="center"
        :label="$t('programVersion.developingAppid')"
        width="200"
      >
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.developAppName')"
      >
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.developer')"
      >
      </el-table-column> -->
      <el-table-column
        align="center"
        :label="$t('programVersion.isDel')"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.delFlag ===0">{{$t('programVersion.normal')}}</span>
          <span v-if="scope.row.delFlag ===1">{{$t('programVersion.del')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.iscueerenuse')"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.currentInUse ===1"
          >{{$t('programVersion.currentUse')}}</span>
          <span
            class="nuuseSpan"
            v-if="scope.row.currentInUse ===0"
          >{{$t('programVersion.no')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.operation')"
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
      <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="20"
        layout="prev, pager, next, jumper"
        :total="totle"
      >
      </el-pagination>
    </div>
    <!--批量提交弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>此操作将批量伟所有小程序客户上传模板版本{{this.upTemplateId}}并提交审核，是否确认此操作</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="handleToBatchSubmit(0)">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToBatchSubmit(1)"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { synchronizationRequest, smallProManRequest, setNowVersionRequest, setCurrentVersionRequest, batchUpload } from '@/api/system/programManage'
export default {
  name: 'versionManage',
  data () {
    return {
      tableData: [],
      version: [{
        value: '1',
        label: this.$t('programVersion.ordinaryVersion')
      }, {
        value: '2',
        label: this.$t('programVersion.goodVersion')
      }],
      operationData: this.$t('programVersion.operationData'),
      currentPage: 1,
      totle: null,
      pageCount: null,
      dialogVisible: false,
      upTemplateId: null
    }
  },
  mounted () {
    // 初始化数据
    this.defaluteData()
    // 初始化语言
    this.langDefault()
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
              item.versionVal = this.$t('programVersion.ordinaryVersion')
            } else if (item.packageVersion === 2) {
              item.versionVal = this.$t('programVersion.goodVersion')
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
          this.dialogVisible = true
          this.upTemplateId = row.templateId
          break
        case 1:
          this.$router.push({
            name: 'programManage',
            params: {
              page: 'versionLog',
              userVersion: row.userVersion,
              id: row.templateId
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
    },
    // 批量提交弹窗确定按钮
    handleToBatchSubmit (flag) {
      if (flag === 1) {
        // 确定时调用接口
        batchUpload(this.upTemplateId).then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.defaluteData()
          }
        })
      }
      this.$router.push({
        name: 'backgroundTaskList'
      })
      this.dialogVisible = false
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
  /deep/ .el-dialog__header {
    text-align: center;
    background-color: #f3f3f3;
  }
}
</style>
