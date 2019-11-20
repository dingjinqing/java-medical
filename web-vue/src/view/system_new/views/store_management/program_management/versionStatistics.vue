<template>
  <div class="version-statistics">
    <div class="filter-menu pd-10 bg-white clearfixed">
      <div class="clearfixed">
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.versionNum"
          :placeholder="$t('programVersion.selectVersionNumber')"
          size="small"
          @change="handleSelectId()"
        >
          <el-option
            v-for="(item, index) in selectIdTemId"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isAuth"
          :placeholder="$t('programVersion.whetherToorNot')"
          @change="handleSelectId()"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectIsAuthorization"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isWxPay"
          :placeholder="$t('programVersion.supportWechatPayment')"
          @change="handleSelectId()"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectIsPay"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.auditStatus"
          :placeholder="$t('programVersion.SelectAuditStatus')"
          @change="handleSelectId()"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectExamineStatus"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isRelease"
          :placeholder="$t('programVersion.publicStatus')"
          size="small"
          @change="handleSelectId()"
        >
          <el-option
            v-for="(item, index) in selectReleaseStatus"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
      </div>
      <div class="row clearfixed">
        <span class="title fll">{{$t('programVersion.displayColumn')}}: </span>
        <el-checkbox-group
          v-model="checkList"
          class="fll"
        >
          <el-checkbox :label="0">{{$t('programVersion.versionNumber')}}</el-checkbox>
          <el-checkbox :label="1">{{$t('programVersion.WhetherToAuthorize')}}</el-checkbox>
          <el-checkbox :label="2">{{$t('programVersion.WechatPayment')}}</el-checkbox>
          <el-checkbox :label="3">{{$t('programVersion.auditStatus')}}</el-checkbox>
          <el-checkbox :label="4">{{$t('programVersion.publiStatus')}}</el-checkbox>
        </el-checkbox-group>
      </div>
    </div>

    <el-table
      class="auth-list mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="userVersion"
        :label="$t('programVersion.versionNumber')"
        align="center"
        v-if="holdHiddenArr[0]"
      >
      </el-table-column>
      <el-table-column
        prop="isAuthOk"
        :label="$t('programVersion.WhetherToAuthorize')"
        align="center"
        v-if="holdHiddenArr[1]"
      >
      </el-table-column>
      <el-table-column
        prop="openPay"
        align="center"
        :label="$t('programVersion.WechatPayment')"
        v-if="holdHiddenArr[2]"
      >
      </el-table-column>
      <el-table-column
        prop="auditState"
        align="center"
        :label="$t('programVersion.auditStatus')"
        v-if="holdHiddenArr[3]"
      >
      </el-table-column>
      <el-table-column
        prop="publishState"
        align="center"
        :label="$t('programVersion.publiStatus')"
        v-if="holdHiddenArr[4]"
      >
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.NumberStores')"
      >
        <template slot-scope="scope">
          <div
            class="num"
            @click="handleToAuthListPpage(scope.row)"
          >{{scope.row.number}}</div>
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
  </div>
</template>

<script>
import { versionStatRequest, SpinnerListRequest } from '@/api/system/programManage'
export default {
  name: 'versionStatistics',
  data () {
    return {
      currentPage: 1,
      pageCount: 1,
      totle: 0,
      queryData: {
        versionNum: null, // 版本号
        isAuth: null, // 是否授权
        isWxPay: null, // 是否微信支付
        auditStatus: null, // 审核状态
        isRelease: null // 发布状态
      },
      checkList: [0, 3, 4], // 复选框选项
      tableData: [
        // {
        //   versionNum: '1.28.3',
        //   isAuth: '已授权',
        //   ispay: '支持微信支付',
        //   status1: '审核中',
        //   status2: '已发布',
        //   num: '2'
        // },
        // {
        //   versionNum: '1.28.3',
        //   isAuth: '已授权',
        //   ispay: '支持微信支付',
        //   status1: '审核中',
        //   status2: '已发布',
        //   num: '2'
        // },
        // {
        //   versionNum: '1.28.3',
        //   isAuth: '已授权',
        //   ispay: '支持微信支付',
        //   status1: '审核中',
        //   status2: '已发布',
        //   num: '2'
        // }
      ],
      holdHiddenArr: [false, false, false, false, false],
      selectIdTemId: [],
      selectIsAuthorization: this.$t('programVersion.selectIsAuthorization'),
      selectIsPay: this.$t('programVersion.selectIsPay'),
      selectExamineStatus: this.$t('programVersion.selectExamineStatus'),
      selectReleaseStatus: this.$t('programVersion.selectReleaseStatus')
    }
  },
  watch: {
    checkList: {
      handler (newData) {
        console.log(newData)
        let arr = [0, 1, 2, 3, 4, 5]

        arr.map((item, index) => {
          if (newData.indexOf(item) !== -1) {
            this.holdHiddenArr[item] = true
          } else {
            this.holdHiddenArr[item] = false
          }
        })
      },
      immediate: true
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    async defaultData () {
      let spinnerList = await SpinnerListRequest()
      console.log(spinnerList)
      if (spinnerList.error === 0) {
        let arr = []
        let defaultObj = {}
        defaultObj.value = ''
        defaultObj.label = this.$t('programVersion.selectVersionNumber')
        arr.push(defaultObj)
        spinnerList.content.map((item, index) => {
          let obj = {}
          obj.value = item.templateId
          obj.label = item.userVersion
          arr.push(obj)
        })
        this.selectIdTemId = arr
        this.handleQueryTableData()
      }
    },
    handleQueryTableData () {
      let obj = {
        'templateId': this.queryData.versionNum,
        'userVserion': '1.0.1',
        'isAuthOk': this.queryData.isAuth,
        'openPay': this.queryData.isWxPay,
        'auditState': this.queryData.auditStatus,
        'publishState': this.queryData.isRelease,
        'currentPage': this.currentPage,
        'pageRows': 0
      }
      versionStatRequest(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          res.content.dataList.map((item, index) => {
            if (item.isAuthOk === 1) {
              item.isAuthOk = this.$t('programVersion.beRevoked')
            } else {
              item.isAuthOk = this.$t('programVersion.Unauthorized')
            }
            if (item.openPay === 1) {
              item.openPay = this.$t('programVersion.WechatPayment')
            } else {
              item.openPay = this.$t('programVersion.selectIsPay[1].label')
            }
            switch (item.auditState) {
              case 0:
                item.auditState = this.$t('programVersion.notSubmitted')
                break
              case 1:
                item.auditState = this.$t('programVersion.auditProgress')
                break
              case 2:
                item.auditState = this.$t('programVersion.AuditSuccess')
                break
              case 3:
                item.auditState = this.$t('programVersion.auditFailure')
                break
            }
            if (item.publishState === 0) {
              item.publishState = this.$t('programVersion.unpublished')
            } else {
              item.publishState = this.$t('programVersion.published')
            }
          })
          this.tableData = res.content.dataList
        }
        console.log(res)
      })
    },
    // 选择模板下拉框选中
    handleSelectId () {
      this.handleQueryTableData()
    },
    // 当前页改变
    handleCurrentChange (val) {
      this.handleQueryTableData()
    },
    // 跳转到小程序授权列表页
    handleToAuthListPpage (row) {
      console.log(row)
      this.$http.$emit('formStatics', row)
      this.$router.push({
        name: 'programManage',
        params: {
          page: 'authList',
          appId: -1
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.version-statistics {
  .select-width {
    width: 140px;
  }

  .row {
    .title {
      margin-right: 20px;
      line-height: 19px;
      font-size: 14px;
    }
  }

  .num {
    border: 1px solid #dedede;
    padding: 0px 8px;
    height: 30px;
    line-height: 30px;
    width: 30px;
    margin: 0 auto;
    cursor: pointer;
    &:hover {
      background: #fff !important;
      color: #5a8bff;
      border: 1px solid #5a8bff;
    }
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
