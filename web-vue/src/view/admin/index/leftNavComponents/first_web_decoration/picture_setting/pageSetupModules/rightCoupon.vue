<template>
  <div class="rightCoupon">
    <div class="rightCouponMain">
      {{modulesData.name}}
      <span>{{modulesData.test}}</span>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      modulesData: {
        name: '优惠卷模块',
        test: '测试'
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.rightCoupon {
  .rightCouponMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
  }
}
</style>
