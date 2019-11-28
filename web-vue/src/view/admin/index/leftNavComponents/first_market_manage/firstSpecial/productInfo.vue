<template>
  <el-dialog
    :visible.sync="productShow"
    title="多规格折后价"
    custom-class="product_set"
    center
  >
    <el-table
      :data="reducePriceProduct"
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
      <template v-for="item in tableLabel">
        <el-table-column
          :prop="item.prop"
          :label="item.label"
          :key="item.index"
          v-if="item.index === 3"
        >
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.prdPrice"
              size="small"
              class="small_input"
            ></el-input>
            元
          </template>
        </el-table-column>
        <el-table-column
          :prop="item.prop"
          :label="item.label"
          :key="item.index"
          v-else
        ></el-table-column>
      </template>
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
      tableLabel: [
        { index: 1, prop: 'prdDesc', label: '规格名称' },
        { index: 2, prop: 'originalPrice', label: '原价' },
        { index: 3, prop: 'prdPrice', label: '首单价' }
      ],
      reducePriceProduct: null
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
    }
  },
  watch: {
    productShow (val) {
      this.$emit('update:productDialog', val)
      if (val === true) {
        this.reducePriceProduct = JSON.parse(JSON.stringify(this.productInfo.reducePriceProduct))
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
      this.$emit('confrim', this.productInfo.goodsId, this.reducePriceProduct)
      this.productShow = false
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .product_set {
  width: 500px;
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
