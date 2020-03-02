<template>
  <div>
    <wrapper>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.nickName')">
              <el-input
                v-model="param.nickName"
                :placeholder="$t('purchase.inputnickName')"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.phone')">
              <el-input
                v-model="param.phoneNumber"
                :placeholder="$t('purchase.inputphone')"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.redemptionNum')">
              <el-input
                v-model="param.redemptionNum"
                :placeholder="$t('purchase.inputredemptionNum')"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="2"
          :offset=1
        >
          <el-button
            type="primary"
            @click="initDateList"
          >{{$t('purchase.serach')}}</el-button>
        </el-col>
        <el-col
          :span="2"
          :offset="1"
        >
          <el-button
            type="info"
            style="float:right;"
            @click="dataExport"
          >
            {{$t('purchase.export')}}
          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-table
          class="version-manage-table"
          header-row-class-name="tableHeader"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="userId"
            :label="$t('purchase.userid')"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="username"
            :label="$t('purchase.nickName')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('purchase.phone')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="orderSn"
            :label="$t('purchase.orderSn')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('purchase.redemptiontime')"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="mainGoodsTotalMoney"
            :label="$t('purchase.mainGoodsTotalMoney')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="redemptionNum"
            :label="$t('purchase.redemptionNum')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="redemptionTotalMoney"
            :label="$t('purchase.redemptionTotalMoney')"
            align="center"
          >
          </el-table-column>
        </el-table>
      </el-row>
      <div>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDateList"
        />
      </div>
    </wrapper>
  </div>
</template>
<script>
import { detailList, detailExport } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import { download } from '@/util/excelUtil.js'
export default {
  components: {
    pagination,
    wrapper,
    download
  },
  mounted () {
    this.langDefault()
  },
  created () {
    this.initDateList()
  },
  data () {
    return {
      tableData: [],
      pageParams: {},
      param: {
        activityId: this.$route.params.id,
        nickName: '',
        phoneNumber: '',
        redemptionNum: null,
        // 分页
        currentPage: 0,
        pageRows: 20
      }
    }
  },
  methods: {
    // 分模块查询数据列表
    initDateList () {
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      detailList(this.param).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content)
          this.pageParams = res.content.page
          this.param.currentPage = res.content.page.currentPage
          this.param.pageRows = res.content.page.pageRows
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      this.tableData = data.dataList
    },
    // 换购明细导出
    dataExport () {
      let params = Object.assign({}, this.param)
      detailExport(params).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
