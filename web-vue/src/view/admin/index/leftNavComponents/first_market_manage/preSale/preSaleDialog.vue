<template>
  <el-dialog
    :visible.sync="productShow"
    title="多规格定金膨胀"
    custom-class="product_set"
    center
  >
    <el-table
      :data="secKillProduct"
      border
      :header-cell-style="{
        'background-color':'#f5f5f5',
        'text-align':'center',
        'border':'none'
      }"
      :cell-style="{
        'text-align':'center'
      }"
    >
      <el-table-column
        prop="prdDesc"
        label="规格名称"
      ></el-table-column>

      <el-table-column
        prop="prdPrice"
        label="原价(元)"
      ></el-table-column>

      <el-table-column
        prop="prdNumber"
        label="规格库存"
      >
      </el-table-column>

      <el-table-column label="活动价格">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.presalePrice"
            :disabled="isEdit"
            size="small"
            class="small_input"
          ></el-input>
        </template>
      </el-table-column>

      <el-table-column label="活动库存">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.presaleNumber"
            :disabled="isEdit"
            size="small"
            class="small_input"
          ></el-input>
        </template>
      </el-table-column>

      <el-table-column
        label="定金"
        v-if="isShowPreMoneyAct"
      >
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.presaleMoney"
            :disabled="isEdit"
            size="small"
            class="small_input"
          ></el-input>
        </template>
      </el-table-column>

      <el-table-column
        label="1阶段定金可抵扣金额"
        v-if="isShowPreMoneyAct"
      >
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.preDiscountMoney1"
            :disabled="isEdit"
            size="small"
            class="small_input"
          ></el-input>
        </template>
      </el-table-column>

      <el-table-column
        label="2阶段定金可抵扣金额"
        v-if="isShowTwoStageMoney&&isShowPreMoneyAct"
      >
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.preDiscountMoney2"
            :disabled="isEdit"
            size="small"
            class="small_input"
          ></el-input>
        </template>
      </el-table-column>

    </el-table>
    <div
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="closeDialog"
        size="small"
      >取 消</el-button>
      <el-button
        type="primary"
        size="small"
        @click="confrim"
      >确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      productShow: false,
      secKillProduct: null
    }
  },
  props: {
    productDialog: {
      type: Boolean,
      default: false
    },
    productInfo: {
      type: Object,
      default: null
    },
    isEdit: {
      type: Boolean,
      default: false
    },
    isShowTwoStageMoney: {
      type: Boolean,
      default: false
    },
    isShowPreMoneyAct: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    productShow (val) {
      this.$emit('update:productDialog', val)
      if (val === true) {
        this.secKillProduct = JSON.parse(JSON.stringify(this.productInfo.goodsSpecProducts))
      }
    },
    productDialog (val) {
      this.productShow = val
      this.$emit('update:productDialog', val)
    }
  },
  methods: {
    closeDialog () {
      this.productShow = false
    },
    confrim () {
      if (!this.checkInput()) return
      if (!this.isEdit) {
        this.$emit('confrim', { goodsId: this.productInfo.goodsId, prdInfo: this.secKillProduct })
      }
      this.productShow = false
    },
    checkInput () {
      console.log(this.secKillProduct)
      for (let i = 0; i < this.secKillProduct.length; i++) {
        if (!this.secKillProduct[i].presalePrice) {
          this.$message.error({
            message: '请输入活动价格'
          })
          return false
        }
        if (this.secKillProduct[i].prdPrice < this.secKillProduct[i].presalePrice) {
          this.$message.error({
            message: '有规格价格大于原价，请修改'
          })
          return false
        }
        if (!this.secKillProduct[i].stock) {
          this.$message.error({
            message: '请输入活动库存'
          })
          return false
        }
        if (this.secKillProduct[i].prdNumber < this.secKillProduct[i].presaleNumber) {
          this.$message.error({
            message: '有规格定金膨胀库存大于原库存，请修改'
          })
          return false
        }
        if (!this.secKillProduct[i].presaleMoney) {
          this.$message.error({
            message: '请输入定金'
          })
          return false
        }
        if (this.secKillProduct[i].prdPrice < this.secKillProduct.presalePrice) {
          this.$message.error({
            message: '定金价格大于商品原价，请修改'
          })
          return false
        }
        if (!this.secKillProduct[i].preDiscountMoney1) {
          this.$message.error({
            message: '请输入一阶段可抵扣金额'
          })
          return false
        }
        if (this.secKillProduct[i].prdPrice < this.secKillProduct.preDiscountMoney1) {
          this.$message.error({
            message: '一阶段可抵扣金额大于商品原价，请修改'
          })
          return false
        }
        if (!this.secKillProduct[i].preDiscountMoney2) {
          this.$message.error({
            message: '请输入二阶段可抵扣金额'
          })
          return false
        }
        if (this.secKillProduct[i].prdPrice < this.secKillProduct.preDiscountMoney2) {
          this.$message.error({
            message: '二阶段可抵扣金额大于商品原价，请修改'
          })
          return false
        }
      }
      return true
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .product_set {
  width: 800px;
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
.small_input {
  width: 80px;
}
</style>
