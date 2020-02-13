<template>
  <div class="batchSetup">
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>这是一段信息</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="$emit('update:dialogVisible',false)">取 消</el-button>
        <el-button
          type="primary"
          @click="$emit('update:dialogVisible',false)"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {

    }
  },
  watch: {
    dialogVisible (newData) {
      console.log(newData)
      if (!newData) this.$emit('update:dialogVisible', false)
    }
  },
  mounted () {
    console.log(this.dialogVisible)
  },
  method: {

  }
}
</script>
<style scoped lang="scss">
.batchSetup {
  /deep/ .el-dialog__header {
    text-align: left;
  }
}
</style>
