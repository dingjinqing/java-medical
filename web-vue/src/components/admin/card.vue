<template>
<div class="card">
  <div class="card-header">{{title}}</div>
  <div class="card-content">
    <slot></slot>
  </div>
</div>
</template>

<script>
export default {
  name: 'card',
  props: {
    title: String
  },
  data () {
    return {

    }
  }
}
</script>

<style scoped lang="scss">
.card {
  float: left;
  width: 310px;
  height: 260px;
  margin-right: 10px;
  box-sizing: border-box;
  overflow: hidden;

  .card-header {
    line-height: 50px;
    height: 50px;
    background: #f9f9f9;
    text-align: center;
    color: #333;
    font-size: 14px;
  }

  .card-content {
    padding: 0 45px 45px 45px;
    background: #fff;
  }
}
</style>
