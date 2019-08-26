<template>
  <div class="addingBusClassDialog">
    <div class="addingBusClassDialogMain">
      <el-dialog
        title="添加商家分类"
        :visible.sync="dialogVisible"
        width="30%"
      >

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

  </div>
</template>
<script>
import { initGrandgetRequest } from '@/api/admin/brandManagement.js'
export default {
  data () {
    return {
      dialogVisible: false
    }
  },
  mounted () {
    this.$http.$on('addingBusClassDialog', res => {
      this.dialogVisible = true
    })
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      // 弹窗数据获取
      initGrandgetRequest().then((res) => {
        console.log(res.goodsSorts)
      })
    }
  }
}
</script>>
<style lang="scss" scoped>
.addingBusClassDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background: #f3f3f3;
  }
  /deep/ .el-dialog__body {
    padding: 20px 20px;
  }
}
</style>
