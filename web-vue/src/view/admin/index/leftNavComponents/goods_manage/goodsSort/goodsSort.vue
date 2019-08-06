<template>
  <div class="goodsSort">
    <el-card class="card_container">
      <goodsSortContent />
    </el-card>

  </div>
</template>
<script>
import goodsSortContent from '@/view/admin/index/leftNavComponents/goods_manage/goodsSort/goodsSortContent'
export default {
  components: { goodsSortContent }
}
</script>
<style scoped>
.goodsSort {
  padding: 10px;
}
.card_container {
  margin-bottom: 40px;
}
</style>
