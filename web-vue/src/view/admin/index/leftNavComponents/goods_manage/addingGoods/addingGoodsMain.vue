<template >
  <div class="addingGoodsMain">
    <!-- 第一部分编辑商品信息 -->
    <addingGoodsProductInfo v-show="active===1" />
    <!-- 第二部分编辑商品详情 -->
    <addingGoodsDetails v-show="active===2" />
    <!-- 第三部分编辑分销信息 -->
    <addingGoodsDistributionInfo v-show="active===3" />
  </div>
</template>
<script>
import addingGoodsProductInfo from './addingGoodsProductInfo'
import addingGoodsDetails from './addingGoodsDetails'
import addingGoodsDistributionInfo from './addingGoodsDistributionInfo'
export default {
  name: 'addingGoodsMain',
  components: { addingGoodsProductInfo, addingGoodsDetails, addingGoodsDistributionInfo },
  data () {
    return {
      active: 1
    }
  },
  // 钩子
  created () {
    this.receiveEventBus()
  },
  methods: {
    // 监听派发来的事件
    receiveEventBus () {
      this.$http.$on('first', val => { this.active = val })
      this.$http.$on('second', val => { this.active = val })
      this.$http.$on('third', val => { this.active = val })
    }
  }
}
</script>
<style lang="css" scoped>
</style>
