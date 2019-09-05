<template>
  <div>
    <wrapper>
      <el-row>
        <el-col :span="6">
          <el-form label-width="100px">
            <el-form-item label="商品名称">
              <el-input
                v-model="param.goodsName"
                placeholder="请输入商品名称"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-form label-width="100px">
            <el-form-item label="订单号">
              <el-input
                v-model="param.orderSn"
                placeholder="请输入订单号"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-form label-width="100px">
            <el-form-item label="订单状态">
              <template>
                <el-select
                  v-model="param.orderStatus"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-form label-width="120px">
            <el-form-item label="收货人姓名">
              <el-input
                v-model="param.receiverName"
                placeholder="请输入收货人姓名"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-form label-width="120px">
            <el-form-item label="收货人手机号">
              <el-input
                v-model="param.receiverPhone"
                placeholder="请输入收货人手机号"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="10">
          <el-form
            label-width="100px"
            :inline="false"
          >
            <el-form-item label="收货地址">
              <template>
                <div>
                  <areaLinkage @areaData="handleAreaData" />
                </div>
              </template>
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
            prop="orderSn"
            label="订单号"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="主商品"
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
            label="换购商品"
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
            label="下单时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="收货人信息"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.consignee}}<br>{{scope.row.mobile}}
            </template>
          </el-table-column>
          <el-table-column
            prop="orderStatusName"
            label="订单状态"
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
import { orderList } from '@/api/admin/marketManage/increasePurchase.js'
// import { getList, changeActivity, add, update, getDetail, share, orderList, detailList, orderExport, detailExport } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
export default {
  components: {
    pagination,
    wrapper,
    areaLinkage
  },
  mounted () {
    this.langDefault()
  },
  created () {
    this.initDateList()
  },
  data () {
    return {
      activityName: '加价购',
      tableData: [],
      pageParams: {},
      options: [{
        value: 0,
        // 待付款 可进行操作：关闭订单
        label: '待付款'
      }, {
        value: 1,
        // 客户已取消 可进行操作：无
        label: '订单已取消'
      }, {
        value: 2,
        // 卖家关闭
        label: '订单已关闭'
      }, {
        value: 3,
        // 待发货 可进行操作：发货，卖家关闭（关闭原因）
        label: '待发货'
      }, {
        value: 4,
        // 已发货 可进行操作：已收货（需检查已超时未收货时才可操作）
        label: '已发货'
      }, {
        value: 5,
        // 已收货 可进行操作：完成
        label: '已收货'
      }, {
        value: 6,
        // 已完成 可进行操作：无
        label: '已完成'
      }, {
        value: 7,
        // 目前没用-退货中 可进行操作：完成退货 已完成
        label: '退货中'
      }, {
        value: 8,
        // 完成退货 可进行操作：无
        label: '退货完成'
      }, {
        value: 9,
        // 目前没用-退款中 可进行操作：无
        label: '退款中'
      }, {
        value: 10,
        label: '退款成功'
      }, {
        value: 11,
        // 拼团已支付拼团中状态,这时需等待拼团是否成功.拼团成功后,如果为定金订单则变为待补款,否则为待发货;拼团失败则变为退款
        label: '拼团中'
      }, {
        value: 12,
        label: '已成团'
      }, {
        value: 13,
        // 礼单(主订单)环节已完成
        label: '送礼完成'
      }],
      param: {
        activityId: this.$route.params.id,
        goodsName: '',
        orderSn: '',
        orderStatus: null,
        receiverName: '',
        receiverPhone: '',
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
    initDateList () {
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
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
