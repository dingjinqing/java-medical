<!--
* 砍价 - 用户信息列表页面
*
* @author:赵鑫
-->
<template>
  <div>
    <!-- 上半部分：筛选条件查询部分 -->
    <wrapper style="padding: 0 30px">
      <section class="bargainUserMain">
        <div class="bargainUserInfo">
          <span>{{$t('marketCommon.username')+"："}}</span>
          <el-input
            v-model="requestParams.username"
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>{{$t('marketCommon.mobile')+"："}}</span>
          <el-input
            v-model="requestParams.mobile"
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>{{$t('bargainList.bargainStatus')+"："}}</span>
          <el-select
            v-model="requestParams.status"
            :placeholder="$t('marketCommon.selectPlaceholder')"
            size="small"
            class="inputWidth"
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
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
        <div>
          <el-button size="small">{{$t('marketCommon.export')}}</el-button>
        </div>
      </section>
    </wrapper>

    <!-- 下半部分：表格数据部分 -->
    <wrapper>
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
    </wrapper>

  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import pagination from '@/components/admin/pagination/pagination'
import { getRecordPageList } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { wrapper, dateTimePicker, pagination },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
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

      // 表格原始数据
      originalData: []
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
