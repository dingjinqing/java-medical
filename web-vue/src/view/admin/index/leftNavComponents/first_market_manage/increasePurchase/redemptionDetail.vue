<template>
  <div>
    <wrapper>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="昵称">
              <el-input
                v-model="param.nickName"
                placeholder="请输入昵称"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="手机号">
              <el-input
                v-model="param.phoneNumber"
                placeholder="请输入手机号"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="换购数量">
              <el-input
                v-model="param.redemptionNum"
                placeholder="请输入换购数量"
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
          >查询</el-button>
        </el-col>
        <el-col
          :span="2"
          :offset="1"
        >
          <el-button
            type="info"
            style="float:right;"
          >
            导出excel
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
            label="用户id"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="username"
            label="昵称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="orderSn"
            label="订单号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="换购时间"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="mainGoodsTotalMoney"
            label="主商品总金额"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="redemptionNum"
            label="换购数量"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="redemptionTotalMoney"
            label="换购总金额"
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
import { detailList } from '@/api/admin/marketManage/increasePurchase.js'
// import { getList, changeActivity, add, update, getDetail, share, orderList, detailList, orderExport, detailExport } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
export default {
  components: {
    pagination,
    wrapper
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
      console.log(this.param)
      detailList(this.param).then((res) => {
        console.log(res)
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
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
