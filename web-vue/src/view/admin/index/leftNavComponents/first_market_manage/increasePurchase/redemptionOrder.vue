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
          align="center"
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
          align="center"
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
import { orderList, orderExport } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import marketOrderSearchTab from '@/components/admin/marketManage/marketOrderSearchTab.vue'
import { download } from '@/util/excelUtil.js'
export default {
  components: {
    pagination,
    wrapper,
    marketOrderSearchTab,
    download
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
      },
      imgHost: `${this.$imageHost}`,
      orderStatusArr: this.$t('groupBuy.orderStatusArr')
    }
  },
  watch: {
    lang () {
      this.orderStatusArr = this.$t('groupBuy.orderStatusArr')
    }
  },
  methods: {
    // 分模块查询数据列表
    initDataList () {
      this.param.category = this.param.status
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      orderList(this.param).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.tableData.map((item, index) => {
            this.setDomainImg(item.mainGoods)
            this.setDomainImg(item.redemptionGoods)
            item.orderStatusName = this.orderStatusArr[item.orderStatus]
          })
          this.pageParams = res.content.page
          this.param.currentPage = res.content.page.currentPage
          this.param.pageRows = res.content.page.pageRows
        }
      })
    },
    // 省市区三级联动
    handleAreaData (val) {
      this.param.provinceCode = val['province']
      this.param.cityCode = val['city']
      this.param.districtCode = val['district']
    },
    exportDataList () {
      this.param.category = this.param.status
      let params = Object.assign({}, this.param)
      orderExport(params).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      })
    },
    // 图片加域名
    setDomainImg (data) {
      data.map((item, index) => {
        item.goodsImg = this.imgHost + '/' + item.goodsImg
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
