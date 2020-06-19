<template>
  <div class="memberValueAddedHome">
    <div class="memberValueAddedHomeMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="会员卡购买"
          name="first"
        >
          <memberCardOrder v-if="activeName ==='first'&!isShowTransactionData"/>
        </el-tab-pane>
        <el-tab-pane
          label="会员卡续费"
          name="second"
        >
          <renewalCard
            :showHeader="activeName"
            @getRenewalHeaderData="handleToGetHeader"
            v-if="!isShowTransactionData"
          />
        </el-tab-pane>
        <el-tab-pane
          label="会员卡充值"
          name="third"
        >
          <renewalCard
            :showHeader="activeName"
            @getRenewalHeaderData="handleToGetHeader"
            v-if="!isShowTransactionData"
          />
        </el-tab-pane>
        <el-tab-pane
          label="优惠卷礼包购买"
          name="fourth"
        >
          <couponPackageOrder v-if="activeName ==='fourth'&!isShowTransactionData" />
        </el-tab-pane>
      </el-tabs>
      <el-button
        class="toCheck"
        size="small"
        type="primary"
        @click="handleToShowTransactionData()"
      >{{isShowTransactionData?'查看明细':'查看交易数据'}}</el-button>
    </div>
    <!--会员卡续费和会员卡充值底部表格-->
    <memberForm
      :activeName="activeName"
      :bottomFormData="bottomFormData"
      :userId="userId"
      v-if="(activeName==='second' || activeName==='third')&!isShowTransactionData"
    />
    <!--查看交易数据组件-->
    <transactionData
      :activeName="activeName"
      v-if="isShowTransactionData"
    />
  </div>
</template>
<script>
export default {
  components: {
    renewalCard: () => import('./renewalCard'), // 会员卡续费和会员卡充值表头
    memberForm: () => import('./memberForm'), // 会员卡续费和会员卡充值表格
    transactionData: () => import('./transactionData'), // 查看交易数据
    memberCardOrder: () => import('./memberCardOrder'), // 会员卡购买
    couponPackageOrder: () => import('./couponPackageOrder') // 优惠卷礼包购买
  },
  data () {
    return {
      userId: null,
      activeName: 'first',
      bottomFormData: {},
      isShowTransactionData: false, // 是否显示交易数据页面
      rightText: '查看交数据' // 右上角动态文案
    }
  },
  mounted () {
    if (this.$route.query.activeName) {
      this.activeName = this.$route.query.activeName
    }
    if (this.$route.query.userId) {
      this.userId = this.$route.query.userId
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      this.isShowTransactionData = false
    },
    // 表头点击筛选回传表头信息数据
    handleToGetHeader (res) {
      this.bottomFormData = res
      console.log(res, this.activeName)
    },
    // 点击查看交易数据
    handleToShowTransactionData () {
      this.isShowTransactionData = !this.isShowTransactionData
    }
  }
}
</script>
<style lang="scss" scoped>
.memberValueAddedHome {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .memberValueAddedHomeMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .toCheck {
      position: absolute;
      top: 12px;
      right: 26px;
    }
  }
}
</style>
