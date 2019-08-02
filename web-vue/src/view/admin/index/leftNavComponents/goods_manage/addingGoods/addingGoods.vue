<template>
  <div class="addGoods">
    <!-- 头部面包屑导航 -->
    <Crumbs :mTitle="M_title" />
    <!-- 主题内容部分 -->
    <addingGoodsContent />
  </div>
</template>
<script>

import Crumbs from '@/components/admin/crumbs'
import addingGoodsContent from '@/view/admin/index/leftNavComponents/goods_manage/addingGoods/addingGoodsContent'

export default {
  components: { Crumbs, addingGoodsContent },
  data () {
    return {
      M_title: [
        '商品管理', '添加商品'
      ],
      componentsFlag: 1,
      turnIndex: null
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
}
</style>
