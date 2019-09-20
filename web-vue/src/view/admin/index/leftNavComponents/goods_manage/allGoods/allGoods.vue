<template>
  <!-- 全部商品组件 -->
  <div class="contentWrap">
      <div class="content">
        <!-- 在售、售罄、仓库中分别为一个组件，存在部分代码重复，但是相对结构清晰 -->
        <router-view></router-view>
      </div>
  </div>
</template>
<script>
export default {
  name: 'allGoods'
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
