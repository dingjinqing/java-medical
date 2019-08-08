<template>
  <div class="deliverHeader">
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
    >
      <el-tab-pane
        label="运费模板列表"
        name="first"
      >

      </el-tab-pane>
      <el-tab-pane
        label="重量运费模板列表"
        name="second"
      >

      </el-tab-pane>
      <el-tab-pane
        label="添加运费模板"
        name="third"
      >

      </el-tab-pane>
      <el-tab-pane
        label="添加重量运费模板"
        name="fourth"
      >

      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  data () {
    return {
      activeName: 'first'
    }
  },
  methods: {
    handleClick (tab, event) {
      console.log(tab.name)
      switch (tab.name) {
        case 'first': this.$router.push('/admin/home/main/deliver/template/list'); break
        case 'second': this.$router.push({ path: '/admin/home/main/deliver/template/weight/list' }); break
        case 'third': this.$router.push('/admin/home/main/deliver/template/add'); break
        case 'fourth': this.$router.push({ path: '/admin/home/main/deliver/template/weight/add' }); break
        default: break
      }
    }
  }
}
</script>
<style scoped>
</style>
