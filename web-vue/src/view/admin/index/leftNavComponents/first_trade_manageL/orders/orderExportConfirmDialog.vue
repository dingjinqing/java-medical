<template>
  <el-dialog
    title="提示"
    :visible.sync="showNodes"
    custom-class="custom"
    width="30%"
  >
    <span>筛选出数据条数：{{totalRows}}</span>
    <el-input
      v-model="param.exportRowStart"
      placeholder=""
    ></el-input>
    至
    <el-input
      v-model="param.exportRowEnd"
      placeholder=""
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
import { download } from '@/util/excelUtil.js'
import { orderExport, getExportTotalRows } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      totalRows: 0,
      showNodes: false
    }
  },
  props: {
    show: Boolean,
    param: Object
  },
  methods: {
    initData () {
      getExportTotalRows(this.param).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          this.totalRows = res.content
        }
      }).catch(() => {
      })
      this.param.exportRowStart = 1
      this.param.exportRowEnd = 5000
    },
    closeDialog () {
      this.$emit('update:show', false)
    },
    confirm () {
      orderExport(this.param).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      })
    }
  },
  watch: {
    showNodes (newval) {
      this.closeDialog()
    },
    show (newVal) {
      if (newVal === true) {
        this.showNodes = true
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
