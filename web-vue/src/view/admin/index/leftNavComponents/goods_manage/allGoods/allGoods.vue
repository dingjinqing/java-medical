<template>
  <!-- 全部商品组件 -->
  <div class="contentWrap">
    <!-- <div class="content"> -->
    <!-- 在售、售罄、仓库中分别为一个组件，存在部分代码重复，但是相对结构清晰 -->
    <router-view></router-view>
    <!-- </div> -->
  </div>
</template>
<script>
// TODO: 全部商品批量处理和导出接口未调用调试，先做主体功能和小程序相关接口，后期返回过来添加，
export default {
  name: 'allGoods'
}
</script>

<style scoped>
.contentWrap {
  padding: 10px 10px;
}
/* .content {
  background-color: #fff;
  padding: 10px 10px 20px 10px;
} */
</style>
