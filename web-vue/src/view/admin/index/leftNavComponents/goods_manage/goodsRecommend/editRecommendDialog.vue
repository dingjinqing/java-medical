<template>
  <el-dialog
    title="填入快递信息发货"
    :visible.sync="showDialog"
    custom-class="custom"
    width="500px"
  >
    <el-checkbox-group v-model="checkedId">
      <table class="recommend_table">
        <thead>
          <tr>
            <th>应用页面</th>
            <th>说明</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="item in tableData">
            <tr :key="item.id">
              <td width="200">
                <el-checkbox :label="item.id">{{item.pageName}}</el-checkbox>
              </td>
              <td>{{item.tips}}</td>
            </tr>
          </template>
        </tbody>
      </table>
    </el-checkbox-group>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="showDialog = false">取 消</el-button>
      <el-button
        type="primary"
        @click="showDialog = false"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      showDialog: false,
      checkedId: [],
      tableData: [
        { id: 0, pageName: '购物车页', tips: '展示在购物车页底部，用于商品推荐' },
        { id: 1, pageName: '订单列表页', tips: '展示在订单列表页底部，用于商品推荐' },
        { id: 2, pageName: '砍价活动页', tips: '展示在砍价活动页底部，用于商品推荐' },
        { id: 3, pageName: '参团活动页', tips: '展示在参团活动页底部，用于商品推荐' },
        { id: 4, pageName: '商品列表页', tips: '展示在商品列表页底部，用于商品推荐' },
        { id: 5, pageName: '支付成功页', tips: '展示在支付成功页底部，用于商品推荐' },
        { id: 6, pageName: '订单完成页', tips: '展示在订单完成页底部，用于商品推荐' },
        { id: 7, pageName: '商品搜索页', tips: '展示在商品搜索页底部，用于商品推荐' },
        { id: 8, pageName: '商品详情页', tips: '展示在商品详情页底部，用于商品推荐' }
      ]
    }
  },
  props: {
    show: Boolean,
    editData: Object
  },
  methods: {
  },
  watch: {
    showDialog (val) {
      this.$emit('update:show', val)
    },
    show (val) {
      this.showDialog = val
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
}
.recommend_table {
  border: 1px solid #cccc;
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  td,
  th {
    border: 1px solid #eee;
    padding: 10px;
    text-align: center;
    font-size: 14px;
  }
}
</style>
