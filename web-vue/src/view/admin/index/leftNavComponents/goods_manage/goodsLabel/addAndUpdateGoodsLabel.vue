<template>
  <div class="contentWrap">
    <div class="content">
    </div>
  </div>
</template>

<script>
export default {
  name: 'addAndUpdateGoodsLabel',
  mounted () {
    console.log(this.$route)
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
