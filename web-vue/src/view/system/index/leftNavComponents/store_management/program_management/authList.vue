<template>
  <div class="auth-list">
    <div
      class="menu-list pd-10 bg-white mt-10"
      style="padding-bottom: 0;"
    >
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedId"
        :placeholder="$t('programVersion.selectTemplateID')"
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
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedAuth"
        :placeholder="$t('programVersion.whetherToAuthorize')"
        size="small"
        @change="handleSelectId()"
      >
        <el-option
          v-for="(item, index) in selectIsAuthorization"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedWxPay"
        :placeholder="$t('programVersion.whetherToPay')"
        size="small"
        @change="handleSelectId()"
      >
        <el-option
          v-for="(item, index) in selectIsPay"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedAuditStatus"
        :placeholder="$t('programVersion.selectAuditStatus')"
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
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedReleaseStatus"
        :placeholder="$t('programVersion.publicationStatus')"
        @change="handleSelectId()"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectReleaseStatus"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedShopStatus"
        :placeholder="$t('programVersion.storeStatus')"
        @change="handleSelectId()"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectShopStatus"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-input
        class="fll mr-6 input-width mb-10"
        v-model="queryData.appid"
        :placeholder="$t('programVersion.enterAppid')"
        size="small"
      />
      <el-input
        class="fll mr-6 input-width mb-10"
        v-model="queryData.shop_id"
        :placeholder="$t('programVersion.enterShopid')"
        size="small"
      />
      <el-input
        class="fll mr-6 input-width mb-10"
        v-model="queryData.programName"
        :placeholder="$t('programVersion.enterNameApplet')"
        size="small"
      />
      <el-button
        size="small"
        type="primary"
        class="mr-6 mb-10"
        @click="handleQuery()"
      >{{$t('programVersion.search')}}</el-button>
    </div>

    <el-table
      class="auth-list mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="appId"
        label="appid"
        align="center"
        width="170"
      >
      </el-table-column>
      <el-table-column
        prop="shopId"
        :label="$t('programVersion.shopID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="nickName"
        align="center"
        :label="$t('programVersion.nickName')"
      >
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.head')"
      >
        <template slot-scope="scope">
          <img :src="scope.row.headImg">
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.ToAuthorize')"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.isAuthOk === 0"
          >{{$t('programVersion.cancelRevoked')}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.isAuthOk === 1"
          >{{$t('programVersion.beRevoked')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.certificationOrNot')"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.verifyTypeInfo === '0'"
          >{{$t('programVersion.uncertified')}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.verifyTypeInfo === '1'"
          >{{$t('programVersion.certified')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('programVersion.SupportWechatPayment')"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.openPay === 0"
          >{{$t('programVersion.noSupport')}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.openPay === 1"
          >{{$t('programVersion.support')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="lastAuthTime"
        align="center"
        :label="$t('programVersion.authorizationTime')"
        width="160"
      >
      </el-table-column>
      <el-table-column
        prop="bindTemplateId"
        align="center"
        :label="$t('programVersion.bindingTemplateID')"
        width="90"
      >
      </el-table-column>
      <el-table-column
        prop="status1"
        align="center"
        :label="$t('programVersion.auditStatus')"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.auditState === 0">{{$t('programVersion.notSubmitted')}}</span>
          <span v-if="scope.row.auditState === 1">{{$t('programVersion.auditProgress')}}</span>
          <span v-if="scope.row.auditState === 2">{{$t('programVersion.AuditSuccess')}}</span>
          <span v-if="scope.row.auditState === 3">{{$t('programVersion.auditFailure')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status2"
        align="center"
        :label="$t('programVersion.publiStatus')"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.publishState === 0"
          >{{$t('programVersion.unpublished')}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.publishState === 1"
          >{{$t('programVersion.published')}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status3"
        align="center"
        :label="$t('programVersion.shopStatus')"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.shopState === 0">{{$t('programVersion.expired')}}</span>
          <span v-if="scope.row.shopState === 1">{{$t('programVersion.inUse')}}</span>

        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        :label="$t('programVersion.operation')"
        width="110"
      >
        <template slot-scope="scope">
          <div class='lastDiv'>
            <span
              v-for="(item,index) in operationData"
              :key="index"
              @click="handleToClick(scope.row,index)"
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
  </div>
</template>

<script>
import { authListRequest, SpinnerListRequest } from '@/api/system/programManage'
export default {
  name: 'authList',
  data () {
    return {
      pageCount: 1,
      queryData: {
        selectedId: null, // 选择模板id
        selectedAuth: null, // 选择是否授权
        selectedWxPay: null, // 选择是否微信支付
        selectedAuditStatus: null, // 审核状态
        selectedReleaseStatus: null, // 发布状态
        selectedShopStatus: null, // 选择店铺状态
        appid: null, // appid输入框数据
        shop_id: null, // shop——id输入框数据
        programName: null // 小程序名称输入框数据

      },
      selectIsAuthorization: this.$t('programVersion.selectIsAuthorization'),
      page: 1,
      templateId: '',
      selectIdOpt: [

        {
          value: '1',
          label: '1.0.0'
        },
        {
          value: '2',
          label: '2.0.0'
        }
      ],
      selectIsPay: this.$t('programVersion.selectIsPay'),
      selectExamineStatus: this.$t('programVersion.selectExamineStatus'),
      selectReleaseStatus: this.$t('programVersion.selectReleaseStatus'),
      selectShopStatus: this.$t('programVersion.selectShopStatus'),
      tableData: [

      ],
      operationData: this.$t('programVersion.operationAuLishData'),
      selectIdTemId: [],
      currentPage: 1,
      totle: 0
    }
  },
  mounted () {
    // 初始化数据
    this.$http.$on('formStatics', (row) => {
      console.log(row)
      this.templateId = row.userVersion
      this.queryData.selectedId = row.templateId
      if (row.auditState === '未提交') {
        this.queryData.selectedAuditStatus = 0
      } else if (row.auditState === '审核中') {
        this.queryData.selectedAuditStatus = 1
      } else if (row.auditState === '审核成功') {
        this.queryData.selectedAuditStatus = 2
      } else if (row.auditState === '审核失败') {
        this.queryData.selectedAuditStatus = 3
      }
      // this.defaultData()
    })
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
        defaultObj.label = this.$t('programVersion.selectTemplateID')
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
    // 小程序列表分页查询
    handleQueryTableData () {
      console.log(this.queryData.selectedId)
      let obj = {
        'templateId': this.queryData.selectedId,
        'isAuthOk': this.queryData.selectedAuth,
        'shopState': this.queryData.selectedShopStatus,
        'openPay': this.queryData.selectedWxPay,
        'auditState': this.queryData.selectedAuditStatus,
        'publishState': this.queryData.selectedReleaseStatus,
        'appId': this.queryData.appid,
        'shopId': this.queryData.shop_id,
        'nickName': this.queryData.programName,
        'currentPage': this.currentPage,
        'pageRows': 0
      }
      authListRequest(obj).then((res) => {
        if (res.error === 0) {
          console.log(res)

          this.tableData = res.content.dataList
          this.pageCount = res.content.page.pageCount
          this.totle = res.content.page.totalRows
        }
        console.log(res)
      })
    },
    // 选择模板下拉框选中
    handleSelectId () {
      console.log(this.queryData.selectedId)
      this.handleQueryTableData()
    },
    // 当前页改变
    handleCurrentChange (val) {
      this.handleQueryTableData()
    },
    // 操作点击处理
    handleToClick (row, index) {
      console.log(row, index)
      if (index === 0) {
        this.$router.push({
          name: 'programManage',
          params: {
            page: 'authMsg',
            appId: row.appId
          }
        })
      } else {
        this.$router.push({
          name: 'programManage',
          params: {
            page: 'versionLog',
            appId: row.appId
          }
        })
      }
    },
    // 点击搜索
    handleQuery () {
      this.handleQueryTableData()
    }
  },
  watch: {
    queryData: {
      deep: true,
      handler (val) {

      }
    }
  }
}
</script>

<style scoped lang="scss">
.auth-list {
  .selected-id {
    width: 140px;
  }
  .input-width {
    width: 140px;
  }
  .useSpan {
    font-weight: 700;
    color: #fff;
    padding: 4px;
    border-radius: 5px;
    background-color: #a90329;
  }
  .nuSpan {
    background-color: #739e73;
  }
  .lastDiv {
    span {
      display: inline-block;
      cursor: pointer;
    }
    span:nth-of-type(1) {
      margin-right: 4px;
    }
  }
  img {
    width: 50px;
    height: 50px;
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
