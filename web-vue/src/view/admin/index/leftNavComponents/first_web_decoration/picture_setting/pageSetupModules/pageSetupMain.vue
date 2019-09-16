<template>
  <div class="pageSetupMain">
    <div>
      <div>
        <span>页面名称</span>
        <el-input
          v-model="pageName"
          placeholder="请输入内容"
        ></el-input>
      </div>
      <div>
        <span>页面分类</span>

      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      pageName: ''
    }
  }
}
</script>
<style lang="scss" scoped>
.pageSetupMain {
}
</style>
