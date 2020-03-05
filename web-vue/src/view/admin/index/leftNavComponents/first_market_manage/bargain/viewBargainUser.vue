<!--
* 砍价 - 砍价用户页面
*
* @author:赵鑫
-->
<template>
  <div>
    <!-- 上半部分：筛选条件查询部分 -->
    <div class="filter-conditions">
      <section class="bargainUserMain">
        <div class="bargainUserInfo">
          <span>{{$t('marketCommon.username')+"："}}</span>
          <el-input
            v-model="requestParams.username"
            size="small"
            class="inputWidth"
            clearable
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>{{$t('marketCommon.mobile')+"："}}</span>
          <el-input
            v-model="requestParams.mobile"
            size="small"
            class="inputWidth"
            clearable
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>{{$t('bargainList.bargainStatus')+"："}}</span>
          <el-select
            v-model="requestParams.status"
            :placeholder="$t('marketCommon.selectPlaceholder')"
            size="small"
            class="inputWidth"
            clearable
          >
            <el-option
              :value="1"
              :label="$t('bargainList.bargainStatusSuccessful')"
            ></el-option>
            <el-option
              :value="2"
              :label="$t('bargainList.bargainStatusFailed')"
            ></el-option>
            <el-option
              :value="0"
              :label="$t('bargainList.bargainStatusProcessing')"
            ></el-option>

          </el-select>
        </div>
      </section>
      <section class="bargainUserMain infoBottom">
        <div style="display:flex">
          <span style="height:32px;line-height:32px;margin-right:5px">{{$t('bargainList.initiatedTime')+"："}}</span>
          <el-date-picker
            v-model="createDate"
            type="datetimerange"
            :range-separator="$t('marketCommon.to')"
            :start-placeholder="$t('marketCommon.startTime')"
            :end-placeholder="$t('marketCommon.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
        </div>
        <div style="margin:0 20px">
          <el-button
            type="primary"
            size="small"
            @click="initDataList"
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
        <div>
          <el-button
            size="small"
            @click="exportData"
          >{{$t('marketCommon.export')}}</el-button>
        </div>
        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          custom-class="custom"
          width="30%"
          style="font-size: 20px;"
          center
        >
          <!-- <div class="tips-content1">根据以下条件筛选出{{totalRows}}条数据,是否确认导出？</div> -->
          <!-- <div class="tips-content2">筛选条件：无</div> -->
          <div>筛选条件：</div>
          <div
            v-for="(item, key, index) in param"
            :key="index"
          >
            <div v-if="ok(key,item)">
              <div v-if="key === 'orderStatus'">
                {{$t('orderSearch.'+key)}}:
                <span
                  v-for="status in item"
                  :key="status"
                >
                  {{orderStatusMap.get(status)}}
                </span>
              </div>
              <div
                v-else
                style="margin-top: 10px;"
              >{{$t('orderSearch.'+key)}}:{{item}}</div>
            </div>
          </div>

          <span
            slot="footer"
            class="dialog-footer"
          >
            <el-button
              size="small"
              @click="dialogVisible = false"
            >取 消</el-button>
            <el-button
              type="primary"
              size="small"
              @click="handelConfirm"
            >确 定</el-button>
          </span>
        </el-dialog>
      </section>
    </div>

    <!-- 下半部分：表格数据部分 -->
    <div class="table">
      <div class="table_list">
        <el-table
          v-loading="loading"
          header-row-class-name="tableClss"
          border
          :data="tableData"
          style="width: 100%"
        >
          <el-table-column
            prop="goodsName"
            :label="$t('bargainList.goodsName')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="username"
            :label="$t('bargainList.bargainUsername')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="mobile"
            :label="$t('marketCommon.mobile')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            :label="$t('bargainList.initiatedTime')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="bargainMoney"
            :label="$t('bargainList.bargainMoney')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="surplusMoney"
            :label="$t('bargainList.surplusMoney')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="userNumber"
            :label="$t('bargainList.participationInTheBargaining')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="status"
            :label="$t('bargainList.bargainStatus')"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            :label="$t('marketCommon.operate')"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <a
                  @click="goBargainUserPageList(scope.row.id)"
                  href="##"
                >{{$t('bargainList.getBargainUser')}}</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>

  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
import { getRecordPageList, exportBargainUserData } from '@/api/admin/marketManage/bargain.js'
import { download } from '@/util/excelUtil.js'

export default {
  components: { pagination },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      console.log(this.$route, 'get id')
      this.actId = this.$route.query.id
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      requestParams: {},
      pageParams: {},
      tableData: [],
      createDate: '',
      actId: '',

      // 表格原始数据
      originalData: [],

      // 导出数据接口参数
      dialogVisible: false,
      param: Object
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.bargainId = this.actId
      this.requestParams.startTime = this.createDate[0]
      this.requestParams.endTime = this.createDate[1]
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getRecordPageList(this.requestParams).then((res) => {
        console.log(res, 'get res')
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        switch (item.status) {
          case 0:
            item.status = this.$t('bargainList.bargainStatusProcessing')
            break
          case 1:
            item.status = this.$t('bargainList.bargainStatusSuccessful')
            break
          case 2:
            item.status = this.$t('bargainList.bargainStatusFailed')
            break
        }
      })
      this.tableData = data
    },

    // 跳转帮忙砍价用户列表
    goBargainUserPageList (id) {
      this.$router.push({
        path: '/admin/home/main/bargain/record/detail',
        query: {
          recordId: id
        }
      })
    },

    // 表格导出
    exportData () {
      this.dialogVisible = true
    },
    handelConfirm () {
      console.log(this.tableData, 'get tableData')
      exportBargainUserData({
        // 'bargainId': this.actId,
        // 'status': this.tableData.status[1],
        // 'username': this.tableData.username[1]
      }).then(res => {
        if (res.error === 0) {
          console.log(res, 'excle-res')
          let fileName = localStorage.getItem('V-content-disposition')
          fileName = fileName.split(';')[1].split('=')[1]
          download(res, decodeURIComponent(fileName))
        }
      }).catch(err => console.log(err))
    }
  },
  watch: {
    // data内变量国际化
    lang () {
      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  }

}

</script>
<style lang="scss" scoped>
* {
  font-size: 14px;
}
.filter-conditions {
  margin: 10px;
  padding: 20px 30px;
  background: #fff;
}
.table {
  margin: 0 10px 10px;
  padding: 15px;
  background: #fff;
}
.tips-content1 {
  margin: 0 0 20px;
}
/deep/ .custom {
  .el-dialog__header {
    background: #f3f3f3;
    padding-top: 10px;
    .el-dialog__title {
      font-size: 14px;
    }
    .el-dialog__headerbtn {
      top: 10px;
    }
  }
  .el-checkbox-button.is-disabled .el-checkbox-button__inner {
    background-color: #f5f7fa;
  }
}
.bargainUserMain {
  display: flex;
  .bargainUserInfo {
    margin-right: 50px;
    span {
      margin-right: 5px;
    }
    .inputWidth {
      width: 150px;
    }
  }
}
.infoBottom {
  margin-top: 20px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
</style>
