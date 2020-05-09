<template>
  <el-dialog
    title="选择应用位置"
    :visible.sync="showDialog"
    custom-class="custom"
    width="500px"
  >
    <el-checkbox-group v-model="recommendUsePage">
      <table class="recommend_table">
        <thead>
          <tr>
            <th>应用页面</th>
            <th>说明</th>
          </tr>
        </thead>
        <tbody>
          <template v-for="item in tableData">
            <tr :key="item.mark">
              <td width="200">
                <el-checkbox :label="item.mark">{{item.pageName}}</el-checkbox>
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
        @click="submit()"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { updateRecommend } from '@/api/admin/goodsManage/goodsRecommend/goodsRecommend'
export default {
  data () {
    return {
      showDialog: false,
      recommendUsePage: [],
      tableData: [
        { mark: 'cart', pageName: '购物车页', tips: '展示在购物车页底部，用于商品推荐' },
        { mark: 'orderlist', pageName: '订单列表页', tips: '展示在订单列表页底部，用于商品推荐' },
        { mark: 'bargainitem', pageName: '砍价活动页', tips: '展示在砍价活动页底部，用于商品推荐' },
        { mark: 'groupbuyitem', pageName: '参团活动页', tips: '展示在参团活动页底部，用于商品推荐' },
        { mark: 'search', pageName: '商品列表页', tips: '展示在商品列表页底部，用于商品推荐' },
        { mark: 'payment', pageName: '支付成功页', tips: '展示在支付成功页底部，用于商品推荐' },
        { mark: 'order_complete', pageName: '订单完成页', tips: '展示在订单完成页底部，用于商品推荐' },
        { mark: 'new_search', pageName: '商品搜索页', tips: '展示在商品搜索页底部，用于商品推荐' },
        { mark: 'item', pageName: '商品详情页', tips: '展示在商品详情页底部，用于商品推荐' }
      ]
    }
  },
  props: {
    show: Boolean,
    editData: Object
  },
  methods: {
    submit () {
      let editData = this.editData
      console.log(editData)
      if (editData.recommendGoods && editData.recommendGoods.length > 0) {
        editData.recommendGoods = editData.recommendGoods.map(item => {
          if (typeof item === 'object') {
            return item.goodsId
          } else {
            return item
          }
        })
      }

      let obj = {
        ...editData,
        recommendUsePage: this.recommendUsePage
      }
      updateRecommend(obj).then(res => {
        if (res.error === 0) {
          this.$emit('update:show', false)
          this.$emit('refresh')
        }
      })
    }
  },
  watch: {
    showDialog (val) {
      this.$emit('update:show', val)
    },
    show (val) {
      this.showDialog = val
      this.recommendUsePage = this.editData.recommendUsePage
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
