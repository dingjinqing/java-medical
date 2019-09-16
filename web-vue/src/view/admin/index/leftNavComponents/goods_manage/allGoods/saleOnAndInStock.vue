<template>
  <!-- 出售中仓库中商品组件 -->
  <div class="saleOnAndInStock">
    <el-input v-model="a"/>
  </div>
</template>
<script>
export default {
  name: 'saleOnAndInStock',
  beforeRouteEnter  (to, from, next) {
    if (to.name === 'goodsForSale') {

    } else {

    }
    next()
  },
  data () {
    return {
      a: 0
    }
  },
  methods: {

  },
  mounted () {
  }
}
</script>

<style lang="css" scoped>
</style>
