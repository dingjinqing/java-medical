<template>
  <!-- 全部商品组件 -->
  <div class="contentWrap">
      <div class="content">
        <allGoodsHeader ref="goodsHeaderCmp"/>
        <div class="btnWrap">
          <el-button type="primary" @click="searchGoods">查询</el-button>
          <el-button type="primary" @click="resetFormData">重置</el-button>
          <el-button type="primary">导出商品</el-button>
          <el-button type="primary">添加商品</el-button>
        </div>
        <!-- 出售中|已售罄|仓库中路由出口 -->
        <router-view ref="tableCmp"></router-view>
      </div>
  </div>
</template>
<script>
import allGoodsHeader from './allGoodsHeader'

export default {
  name: 'allGoods',
  components: { allGoodsHeader },

  // 数据data
  data () {
    return {
      tabItemActiveIndex: 0
    }
  },
  // methods 方法
  methods: {
    resetFormData () {
      this.$refs.goodsHeaderCmp.resetFormData()
      let filterData = this.$refs.goodsHeaderCmp.getFormData()
      this.$refs.tableCmp.setFilterData(filterData)
    },
    searchGoods () {
      let filterData = this.$refs.goodsHeaderCmp.getFormData()
      this.$refs.tableCmp.fetchGoodsData(filterData)
    }
  },
  // Vue生命周期钩子函数
  mounted () {

  }
}
</script>

<style scoped>
  .contentWrap {
    padding: 10px 10px;
  }
  .content {
    background-color: white;
    padding: 10px 10px 20px 10px;
  }
</style>
