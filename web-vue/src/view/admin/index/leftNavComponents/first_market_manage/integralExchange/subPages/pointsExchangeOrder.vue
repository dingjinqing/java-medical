<template>
  <div class="pointsExchangeOrder">
    <div class="main">
      <div class="mainTop">
        <div class="mainTopList">
          <div class="filters_item">
            <span>商品名称：</span>
            <el-input
              size="small"
              v-model="goodsNameInput"
              placeholder="商品名称"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>订单号：</span>
            <el-input
              size="small"
              v-model="goodsNameInput"
              placeholder="订单号"
            ></el-input>
          </div>
          <div class="filters_item select">
            <span>订单状态：</span>
            <el-select
              v-model="orderStatus"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in orderStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="mainTopList">
          <div class="filters_item">
            <span>收货人姓名：</span>
            <el-input
              size="small"
              v-model="consigneeName"
              placeholder="收货人姓名"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>收货人手机号：</span>
            <el-input
              size="small"
              v-model="consigneePhone"
              placeholder="收货人手机号"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>下单时间：</span>
            <el-input
              size="small"
              v-model="orderTime"
              placeholder="下单时间"
            ></el-input>
          </div>
        </div>
        <div class="bottom">
          <span>收获地址：</span>
          <areaLinkage
            :areaCode="areaLinkage"
            @areaData="handleAreaData"
            style="width:365px"
          />
          <div style="margin-left:20px">
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
            <el-button
              type="info"
              size="small"
              plain
            >导出</el-button>
          </div>
        </div>
      </div>
      <div class="mainBottom">
        ssss
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue') // 省市区弹窗
  },
  data () {
    return {
      goodsNameInput: '', // 商品名称input值
      orderStatus: -1, // 订单select选中值
      orderStatusOptions: [{
        value: -1,
        label: '全部订单'
      }, {
        value: 0,
        label: '待付款'
      }, {
        value: 1,
        label: '订单取消'
      }, {
        value: 2,
        label: '订单关闭'
      }, {
        value: 3,
        label: '待发货/待核销'
      }, {
        value: 4,
        label: '已发货'
      }, {
        value: 5,
        label: '已发货/已自提'
      }, {
        value: 6,
        label: '订单完成'
      }, {
        value: 7,
        label: '售后中'
      }, {
        value: 6,
        label: '售后完成'
      }, {
        value: 8,
        label: '送礼完成'
      }, {
        value: 9,
        label: '待接单'
      }, {
        value: 10,
        label: '待接单-取件中'
      }, {
        value: 11,
        label: '已取件-配送中'
      }],
      consigneeName: '', // 收货人姓名
      consigneePhone: '', // 收货人手机号
      orderTime: '', // 下单时间
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      }
    }
  },
  mounted () {
    console.log(this.$route)
  },
  methods: {
    // 省市区选中回传
    handleAreaData (data) {
      console.log(data)
    }
  }
}
</script>
<style lang="scss" scoped>
.pointsExchangeOrder {
  padding: 10px;
  font-size: 14px;
  .main {
    .mainTop {
      background-color: #fff;
      padding: 20px 0 20px 30px;
      .mainTopList {
        display: flex;
        .filters_item {
          display: flex;
          min-width: 320px;
          margin-bottom: 20px;
          max-width: 328px;
          /deep/ .el-input__inner {
            width: auto;
          }
          /deep/ .el-input {
            width: 188px;
          }
          span {
            white-space: nowrap;
            width: 140px;
            display: inline-block;
            height: 32px;
            line-height: 32px;
            text-align: right;
          }
        }
        .select {
          // padding-right: 43px;
          /deep/ .el-input__inner {
            width: 100%;
          }
        }
      }
      .bottom {
        display: flex;
        span {
          width: 140px;
          line-height: 32px;
          height: 32px;
          text-align: right;
        }
        /deep/ .el-select {
          margin-right: 5px;
        }
      }
    }
    .mainBottom {
      margin-top: 10px;
      background-color: #fff;
      padding: 10px;
    }
  }
}
</style>
