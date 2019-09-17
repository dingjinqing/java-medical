<template>
  <div class="content">
    <div class="main">
      <el-tabs v-model="activeName">
        <el-tab-pane
          label="会员卡订单"
          name="card"
        >
          <memberCardOrder v-if="activeName ==='card'" />
        </el-tab-pane>
        <el-tab-pane
          label="优惠券礼包订单"
          name="couponPackage"
        >
          <couponPackageOrder v-if="activeName ==='couponPackage'" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    memberCardOrder: () => import('./memberCardOrder'),
    couponPackageOrder: () => import('./couponPackageOrder')
  },
  data () {
    return {
      activeName: 'card'
    }
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    padding: 10px 10px 0;
    background-color: #fff;
    /deep/ .search_box {
      background-color: #fff;
      .filters {
        display: flex;
        line-height: 32px;
        flex-wrap: wrap;
        margin-left: -15px;
        // max-width: 1226px;
        .filters_item {
          display: flex;
          max-width: 480px;
          min-width: 280px;
          margin-left: 15px;
          margin-bottom: 10px;
          > span {
            min-width: 100px;
            font-size: 14px;
            text-align: right;
          }
        }
      }
      .default_input {
        width: 180px;
      }
    }
  }
}
</style>
