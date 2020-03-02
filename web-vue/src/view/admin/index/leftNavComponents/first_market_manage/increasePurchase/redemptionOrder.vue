<template>
  <div>
    <wrapper>
      <!-- 查询条件 -->
      <el-form
        label-width="100px"
        :inline="true"
      >
        <el-form-item :label="$t('marketCommon.goodsName')">
          <el-input
            v-model="param.goodsName"
            :placeholder="$t('marketCommon.goodsName')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('marketCommon.orderSn')">
          <el-input
            v-model="param.orderSn"
            :placeholder="$t('marketCommon.orderSn')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('marketCommon.orderStatus')">
          <el-select
            v-model="param.orderStatus[0]"
            :placeholder="$t('marketCommon.selectPlaceholder')"
            size="small"
          >
            <el-option
              v-for="item in $t('order.orderStatusList')"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('marketCommon.consigneeName')">
          <el-input
            v-model="param.consignee"
            :placeholder="$t('marketCommon.consigneeName')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('marketCommon.consigneeMobile')">
          <el-input
            v-model="param.mobile"
            :placeholder="$t('marketCommon.consigneeMobile')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('marketCommon.orderTime')">
          <el-date-picker
            v-model="param.orderTime"
            type="datetimerange"
            :placeholder="$t('marketCommon.orderTime')"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('marketCommon.shippingAddress')">
          <template>
            <areaLinkage
              @areaData="handleAreaData"
              style="width:365px;"
            />
          </template>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="initDataList"
          >{{$t('marketCommon.filter')}}</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            style="float:right;"
            @click="exportDataList"
          >
            {{$t('marketCommon.export')}}
          </el-button>
        </el-form-item>
      </el-form>
    </wrapper>
    <wrapper>
      <!-- 表格数据 -->
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
import { download } from '@/util/excelUtil.js'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
export default {
  components: {
    pagination,
    wrapper,
    download,
    areaLinkage
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
        orderStatus: [],
        consignee: '',
        mobile: '',
        createTimeStart: '',
        createTimeEnd: '',
        orderTime: [],
        provinceCode: '',
        cityCode: '',
        districtCode: '',
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
      this.param.createTimeStart = this.param.orderTime[0]
      this.param.createTimeEnd = this.param.orderTime[1]
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
    },
    // 省市区下拉处理
    handleAreaData (data) {
      this.param.provinceCode = data.province
      this.param.cityCode = data.city
      this.param.districtCode = data.district
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
