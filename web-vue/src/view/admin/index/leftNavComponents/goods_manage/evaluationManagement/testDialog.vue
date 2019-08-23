<template>
  <div class="evaluationManagement">
    <el-dialog
      title="提示"
      width="30%"
      :visible.sync="isShow"
    >
      <el-button @click="handleTest">测试按钮</el-button>
      <span></span>
    </el-dialog>
  </div>
</template>
<script>

export default {
  props: {
    isShow: {
      type: Boolean
    }
  },
  data () {
    return {
      children: `123`
    }
  },
  mounted () {

  },
  methods: {
    handleTest () {
      this.$emit('test', this.children)
    }
  }
}
</script>
<style scoped>
</style>
