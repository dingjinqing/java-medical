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
        @click="confirm"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { notes } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      textarea: null,
      showNodes: false,
      type: 0
    }
  },
  props: {
    show: Boolean,
    orderSn: String
  },
  methods: {
    closeDialog () {
      this.$emit('update:show', false)
    },
    initData () {
      let obj = {
        orderSn: this.orderSn,
        type: this.type,
        remark: this.textarea
      }
      notes(obj).then(res => {
        if (res.error === 0) {
          if (this.type === 0) {
            this.textarea = res.content.sellerRemark
          }
          if (this.type === 1) {
            this.$message.success('添加备注成功！')
            this.showNodes = false
            this.$emit('handlerResetData', this.orderSn)
          }
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
      })
    },
    confirm () {
      this.type = 1
      this.initData()
    }
  },
  watch: {
    showNodes (newval) {
      this.closeDialog()
    },
    show (newVal) {
      if (newVal === true) {
        this.showNodes = true
        this.type = 0
        this.textarea = null
        this.initData()
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
