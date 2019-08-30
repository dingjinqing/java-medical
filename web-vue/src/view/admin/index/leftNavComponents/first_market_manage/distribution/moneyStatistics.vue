<template>
  <div class="statistics">
    <div class="infoContent">
      <ul>
        <li>
          <span>手机号</span>
          <el-input></el-input>
        </li>
        <li>
          <span>微信昵称</span>
          <el-input></el-input>
        </li>
        <li>
          <span>微信昵称</span>
          <el-input></el-input>
        </li>
      </ul>
    </div>
    <div class="tableContent"></div>
  </div>
</template>

<script>

export default {
  data () {
    return {

    }
  }

}

</script>
<style lang="scss" scoped>
.statistics {
  width: 100%;
  background: #fff;
  .infoContent {
    height: 100px;
  }
  .tableContent {
    height: 100px;
  }
}
</style>
