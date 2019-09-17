<template>
  <el-dialog
    title="添加备注"
    :visible.sync="showNodes"
    custom-class="custom"
    width="30%"
  >
    <el-input
      type="textarea"
      v-model="textarea"
      placeholder="请输入内容"
      resize="none"
      rows="5"
    ></el-input>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="showNodes = false">取 消</el-button>
      <el-button
        type="primary"
        @click="showNodes = fasle"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      textarea: null,
      showNodes: false
    }
  },
  props: {
    show: Boolean,
    handleClose: Function
  },
  methods: {
    closeDialog () {
      this.$emit('update:show', false)
    }
  },
  watch: {
    showNodes (newval) {
      this.closeDialog()
    },
    show (newVal) {
      if (newVal === true) {
        this.textarea = null
        this.showNodes = true
      }
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .custom {
  .el-dialog__header {
    background: #f3f3f3;
    padding-top: 10px;
    .el-dialog__title {
      font-size: 14px;
    }
    .el-dialog__headerbtn {
      top: 10px;
    }
  }
  .el-checkbox-button.is-disabled .el-checkbox-button__inner {
    background-color: #f5f7fa;
  }
}
</style>
