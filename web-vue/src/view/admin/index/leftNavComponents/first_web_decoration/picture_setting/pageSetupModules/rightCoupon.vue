<template>
  <div class="rightCoupon">
    <div class="rightCouponMain">
      {{modulesTest.name}}
      <span>{{modulesTest.test}}</span>
      <el-button @click='handleToSelectCuopon()'>会员模块测试</el-button>
    </div>

  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object,
    sortIndex: Number
  },
  data () {
    return {
      modulesTest: {
        name: '优惠卷模块',
        test: '测试'
      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: {
      handler (newData) {
        console.log(newData, this.modulesData)
      },
      immediate: true
    }
  },
  methods: {
    // 测试点击数据回传
    handleToSelectCuopon () {
      let obj = {
        name: '帅飞优惠卷',
        backgroundColor: '#111'
      }
      this.$emit('handleToBackData', obj)
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
