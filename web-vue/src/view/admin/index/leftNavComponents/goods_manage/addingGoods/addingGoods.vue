<template>
  <div class="addGoods">
    <el-card class="container">
      <!-- 头部组件 -->
      <addingGoodsHeader />
      <!-- 主要内容区 -->
      <addingGoodsMain />
      <!-- 底部按钮组件 -->
      <addingGoodsFooter />
    </el-card>
  </div>
</template>
<script>
import addingGoodsHeader from './addingGoodsHeader'
import addingGoodsMain from './addingGoodsMain'
import addingGoodsFooter from './addingGoodsFooter'
export default {
  name: 'addingGoods',
  // 组件注册
  components: { addingGoodsHeader, addingGoodsMain, addingGoodsFooter },
  data () {
    return {

    }
  },
  mounted () {

  },
  methods: {

  }
}
</script>
<style scoped>
.addGoods {
  overflow-y: auto;
  padding: 10px;
}
</style>
