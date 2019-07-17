<template>
  <div>
    <el-button
      type="info"
      @click="handleDialog()"
    >信息按钮</el-button>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <span>2</span>
      <vue-cropper></vue-cropper>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  data () {
    return {
      dialogVisible: false,
      options: {
        img: 'https://qn-qn-kibey-static-cdn.app-echo.com/goodboy-weixin.PNG',
        size: 1,
        full: false,
        outputType: 'png',
        canMove: true,
        fixedBox: false,
        original: false,
        canMoveBox: true,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 200,
        autoCropHeight: 150,
        centerBox: false,
        high: true
      }
    }
  },
  methods: {
    handleClose (done) {
      this.dialogVisible = false
    },
    // 弹出弹框
    handleDialog () {
      this.dialogVisible = true
    }
  }
}
</script>
<style scoped>
</style>
