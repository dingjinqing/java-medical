<template>
  <el-dialog
    :title="$t('allGoods.allGoodsData.tip')"
    :visible.sync="showNodes"
    custom-class="custom"
    width="30%"
    v-loading="loading"
  >
    <span>{{$t('order.orderExportConfirmTip_1')}}{{totalRows}}{{$t('order.orderExportConfirmTip_2')}}</span>
    <div>
      {{$t('order.filterCondition')}}
    </div>
    <div v-if="Object.keys(paramString).length!=0">
      <div
        v-for="(item,key,index) in paramString"
        :key="index"
      >
        <div v-if="ok(key,item)">
          <div>{{$t('allGoods.allGoodsHeaderInputLabel.'+key)}}:{{item}}</div>
        </div>
      </div>
    </div>
    <div v-else>
      {{$t('allGoods.allGoodsData.no')}}
    </div>
    <div v-if="totalRows > 0">
      <div>{{$t('order.orderExportLimitTip')}}</div>
      <el-input
        v-model="param.exportRowStart"
        placeholder=""
        size="small"
        width="30%"
      ></el-input>
      {{$t('orderCommon.to')}}
      <el-input
        v-model="param.exportRowEnd"
        placeholder=""
        size="small"
        width="30%"
      ></el-input>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="showNodes = false">{{$t('orderCommon.cancel')}}</el-button>
      <el-button
        type="primary"
        @click="confirm"
        v-if="totalRows > 0"
      >{{$t('orderCommon.ok')}}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { goodsExport, getExportTotalRows } from '@/api/admin/goodsManage/allGoods/allGoods.js'
export default {
  data () {
    return {
      totalRows: 0,
      showNodes: false,
      loading: false,
      orderStatusMap: new Map(this.$t('order.orderStatusList'))
    }
  },
  props: {
    show: Boolean,
    param: Object,
    paramString: Object // 用于展示已选择条件
  },
  methods: {
    initData () {
      this.param.exportRowStart = 1
      this.param.exportRowEnd = 5000
      getExportTotalRows(this.param).then(res => {
        if (res.error === 0) {
          this.totalRows = res.content
          if (this.totalRows < 5000) {
            this.param.exportRowEnd = this.totalRows
          }
        }
      }).catch(() => {
      })

      Object.keys(this.paramString).forEach((item, index) => {
        if (!this.paramString[item]) {
          delete this.paramString[item]
        }
      })
    },
    closeDialog () {
      this.$emit('update:show', false)
    },
    confirm () {
      this.loading = true
      goodsExport(this.param).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        this.loading = false
        download(res, decodeURIComponent(fileName))
      })
    },
    ok (key, item) {
      if (Array.isArray(item)) {
        if (item.length !== 0) return true
      } else {
        if (key === 'currentPage' || key === 'pageRows' || key === 'exportRowStart' || key === 'exportRowEnd') {
          return false
        }
        if (item) return true
      }
      return false
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
