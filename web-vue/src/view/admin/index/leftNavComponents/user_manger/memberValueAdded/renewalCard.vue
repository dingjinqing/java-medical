<template>
  <div>
    <!--会员卡购买表头-->
    <div v-if="showHeader === 'first'">
      会员卡购买表头
    </div>
    <!--会员卡续费表头-->
    <div v-if="showHeader === 'second'">
      <div class="list">
        <div class="li">
          <div class="phoneClass">续费单号</div>
          <el-input
            v-model="headerDataSecond.renewalNo"
            placeholder="请输入续费单号"
            size="small"
          ></el-input>
        </div>
      </div>
    </div>
    <!--会员卡充值表头-->
    <div v-if="showHeader === 'third'">
      会员卡充值表头
    </div>
    <!--优惠卷礼包购买表头-->
    <div v-if="showHeader === 'fourth'">
      优惠卷礼包购买表头
    </div>
  </div>
</template>
<script>
export default {
  props: {
    showHeader: {
      Type: String,
      default: ''
    }
  },
  data () {
    return {
      headerDataFirst: {
      },
      headerDataSecond: {
        renewalNo: ''
      },
      headerDataThird: {
      },
      headerDataFourth: {
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.li {
  width: 280px;
  display: flex;
  .phoneClass {
    width: 70px;
    line-height: 30px;
    height: 30px;
    text-align: left;
    color: #333;
  }
}
</style>
