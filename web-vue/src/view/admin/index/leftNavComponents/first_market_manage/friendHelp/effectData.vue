<template>
  <div class="effectData">
    <div class="top">
      哈哈哈
    </div>
    <div class="mid">

    </div>
    <div class="bottom">

      <!--    第二个折线图-->
<!--      <div id="secondCharts" v-show="this.controlShow"></div>-->
<!--      <div v-show="!this.controlShow"><span>暂无数据</span></div>-->
    </div>

    <div></div>
  </div>
</template>

<script>
export default {
  name: 'effectData.vue'
}
</script>

<style lang="scss" scoped>
  .effectData {
    padding: 10px;
    background: #a4a4a4;
  .top{
    height: 50px;
  }
  .mid{
    height: 550px;
  .table{
    margin: 40px;
    border: 1px solid#45c3ec;
    border-spacing: 0px;
    border-collapse: collapse;
    width: 1240px;
    height: 180px;
    table-layout: fixed;
  }
  .table td{
    border: 1px gray solid;
    padding-left: 80px;
  }
  }
  .bottom{
    height: 550px;

  }
  }
</style>
