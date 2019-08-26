<template>
  <div class="content">
    <div class='main'>
      <span>分销员分组：</span>
      <el-input
        class="groupName"
        v-model="input"
        placeholder="请输入内容"
      ></el-input>
      <el-button type="primary">主要按钮</el-button>
      <span class="showCfg">分组是否展示在小程序端：</span>
    </div>
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
.main {
  display: flex;
  .groupName {
    width: 12%;
    margin-right: 10px;
  }
  span {
    height: 40px;
    line-height: 40px;
  }
  .showCfg {
    margin-left: 10px;
  }
}
</style>
