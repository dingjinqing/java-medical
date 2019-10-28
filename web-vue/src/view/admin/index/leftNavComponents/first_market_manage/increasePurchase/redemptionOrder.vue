<template>
  <div>
    <wrapper>
      <marketOrderSearchTab
        :requestParams="param"
        @filter="initDataList"
        @export="exportDataList"
      />
    </wrapper>
    <wrapper>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableHeader"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="orderSn"
          :label="$t('purchase.orderSn')"
          align="center"
        >

        </el-table-column>
        <el-table-column
          :label="$t('purchase.majorgoods')"
          align="left"
        >
          <template slot-scope="scope">
            <ul>
              <li
                v-for="(item,index) in scope.row.mainGoods"
                :key="index"
              >
                <el-form :inline="true">
                  <el-form-item>
                    <el-image
                      style="width: 50px; height: 50px"
                      :src="item.goodsImg"
                      :fit="fit"
                    ></el-image>
                  </el-form-item>
                  <el-form-item style="width: 100px">
                    {{item.goodsName}}
                  </el-form-item>
                  <el-form-item>
                    ×{{item.goodsNumber}}
                  </el-form-item>
                </el-form>
              </li>
            </ul>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('purchase.redemptiongoods')"
          align="left"
        >
          <template slot-scope="scope">
            <ul>
              <li
                v-for="(item,index) in scope.row.redemptionGoods"
                :key="index"
              >
                <el-form :inline="true">
                  <el-form-item>
                    <el-image
                      style="width: 50px; height: 50px"
                      :src="item.goodsImg"
                      :fit="fit"
                    ></el-image>
                  </el-form-item>
                  <el-form-item style="width: 100px">
                    {{item.goodsName}}
                  </el-form-item>
                  <el-form-item>
                    ×{{item.goodsNumber}}
                  </el-form-item>
                </el-form>
              </li>
            </ul>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('purchase.ordertime')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('purchase.consigneemobile')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.consignee}}<br>{{scope.row.mobile}}
          </template>
        </el-table-column>
        <el-table-column
          prop="orderStatusName"
          :label="$t('purchase.orderStatus')"
          align="center"
        >

        </el-table-column>
      </el-table>
      <div>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </wrapper>
  </div>
</template>
<script>
import { orderList } from '@/api/admin/marketManage/increasePurchase.js'
// import { getList, changeActivity, add, update, getDetail, share, orderList, detailList, orderExport, detailExport } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
export default {
  components: {
    pagination,
    wrapper,
    marketOrderSearchTab
  },
  mounted () {
    this.langDefault()
    this.initDataList()
    if (this.$route.params.id > 0) {
      this.activityId = this.param.activityId = this.$route.params.id
    }
  },
  data () {
    return {
      activityId: 0,
      activityName: '加价购',
      tableData: [],
      pageParams: {},
      param: {
        activityId: this.activityId,
        goodsName: '',
        orderSn: '',
        orderStatus: null,
        provinceCode: null,
        cityCode: null,
        districtCode: null,
        currentPage: 0,
        pageRows: 20
      }
    }
  },
  methods: {
    // 分模块查询数据列表
    initDataList () {
      this.param.category = this.param.status
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      console.log(this.param)
      orderList(this.param).then((res) => {
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
      // data.dataList.map((item, index) => {
      //   console.log(item.purchaseInfo)
      // })
      this.tableData = data.dataList
    },
    // 省市区三级联动
    handleAreaData (val) {
      this.param.provinceCode = val['province']
      this.param.cityCode = val['city']
      this.param.districtCode = val['district']
    },
    exportDataList () {
      alert(11)
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
