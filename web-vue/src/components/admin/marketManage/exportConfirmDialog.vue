
<template>
  <el-dialog
    :title="$t('orderCommon.tip')"
    :visible.sync="showNodes"
    custom-class="custom"
    width="30%"
    v-loading="loading"
    center
  >
    <el-alert
      type="warning"
      show-icon
      :closable='false'
    >
      <template slot='title'>
        <p>{{$t('order.orderExportConfirmTip_1')}}{{totalRows}}{{$t('order.orderExportConfirmTip_2')}}</p>
      </template>
    </el-alert>
    <div style="margin-top: 10px;">
      {{$t('order.filterCondition')}}
      <span v-if="selectFlag">{{$t('allGoods.allGoodsData.no')}}</span>
    </div>
    <div
      v-for="(item,key,index) in param"
      :key="index"
      class="selectItem"
    >
      <div v-if="ok(key,item)">
        <div
          class="selectItem"
          v-if="key === 'selectedOrderStatus'"
        >
          订单状态：{{orderStatusArr[param[key]]}}
        </div>
        <div
          class="selectItem"
          v-else
        >
          {{$t('orderSearch.'+key)}}：{{item}}
        </div>
      </div>
    </div>

    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="showNodes = false"
        size="small"
      >{{$t('orderCommon.cancel')}}</el-button>
      <el-button
        type="primary"
        @click="confirm"
        size="small"
      >{{$t('orderCommon.ok')}}</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      selectFlag: false, // 筛选条件
      showNodes: false,
      loading: false,
      exportRowStart: 1,
      exportRowEnd: 5000,
      // 订单状态
      orderStatusArr: {
        null: '全部订单',
        0: '待付款',
        1: '订单取消',
        2: '订单关闭',
        3: '待发货/待核销',
        4: '已发货',
        5: '已收货/已自提',
        6: '订单完成',
        7: '退货中',
        8: '退货完成',
        9: '退款中',
        10: '退款完成',
        11: '拼团中',
        12: '已成团',
        13: '送礼完成'
      }
    }
  },
  props: {
    show: Boolean,
    param: Object, // 用于展示已经选择的条件
    totalRows: Number // 筛选个数
  },
  methods: {
    initData () {
      console.log(this.param, 'get params')
      // 判断筛选条件
      this.selectFlag = false
      var data = []
      for (var key in this.param) {
        data.push(this.ok(key, this.param[key]))
      }
      if (data.indexOf(true) === -1) {
        this.selectFlag = true
      }
    },
    closeDialog () {
      this.$emit('update:show', false)
    },
    // 导出数据
    confirm () {
      this.$emit('export', this.param)
      this.showNodes = false
    },
    ok (key, item) {
      if (Array.isArray(item)) {
        if (item.length !== 0) return true
      } else {
        if (key === 'currentPage' || key === 'pageRows' || key === 'exportRowStart' || key === 'exportRowEnd' || key === 'orderStatus2' || key === 'activityId') {
          return false
        }
        if (key === 'selectedOrderStatus') {
          if (item || item === 0 || item === null) return true
        } else {
          if (item) return true
        }
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
.selectItem {
  margin-top: 10px;
}
</style>
