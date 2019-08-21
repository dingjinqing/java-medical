<template>
  <!-- 出售中商品组件 -->
  <div class="saleOn">
    <!-- 表格 -->
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      header-align="center"
      :header-cell-style="tableHeaderStyle"
    >
      <!-- 用来实现多选 -->
      <el-table-column
        type="selection"
        width="50"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="code"
        label="商品货号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="sort1"
        label="平台分类"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="sort2"
        label="商家分类"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="brand"
        label="品牌"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="stock"
        label="库存"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="sales"
        label="销量"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="label"
        label="商品标签"
        align="center"
      >
      </el-table-column>
      <!-- 操作列 -->
      <el-table-column
        prop=""
        label="操作"
        align="center"
      >
        <template>
          <el-tooltip
            content="编辑"
            placement="top"
          >
            <i
              @click="handleEdit"
              class="el-icon-edit-outline"
              style="color:#409EFF;fontSize:16px"
            ></i>
          </el-tooltip>
          <el-tooltip
            content="复制"
            placement="top"
          >
            <i
              @click="handleCopy"
              class="el-icon-document-copy"
              style="color:#409EFF;fontSize:16px"
            ></i>
          </el-tooltip>
          <el-tooltip
            content="分享"
            placement="top"
          >
            <i
              @click="handleShare"
              class="el-icon-share"
              style="color:#409EFF;fontSize:16px"
            ></i>
          </el-tooltip>
          <el-tooltip
            content="下架"
            placement="top"
          >
            <i
              @click="handleXia"
              class="el-icon-alixiajia"
              style="color:#409EFF;fontSize:16px"
            ></i>
          </el-tooltip>
          <el-tooltip
            content="删除"
            placement="top"
          >
            <i
              @click="handleDel"
              class="el-icon-delete"
              style="color:#409EFF;fontSize:16px"
            ></i>
          </el-tooltip>

        </template>
      </el-table-column>

    </el-table>
  </div>
</template>
<script>
export default {
  name: 'saleOn',
  // 数据data
  data () {
    return {
      tableData: [{
        name: `华为手机`,
        price: `6000`,
        code: `G10001`,
        sort1: `手机`,
        sort2: `电子`,
        brand: `HUAWEI`,
        stock: `10`,
        sales: `2222`,
        label: `正品行货`
      }, {
        name: `苹果手机`,
        price: `5000`,
        code: `G10000`,
        sort1: `手机`,
        sort2: `电子`,
        brand: `IPhone`,
        stock: `200`,
        sales: `12`,
        label: `正品行货`
      }]
    }
  },
  // Vue生命周期钩子函数
  mounted () {

  },
  // methods 方法
  methods: {
    //  修改table header的样式
    tableHeaderStyle ({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return 'background-color: #f5f5f5;color: #333;font-weight: 500;'
      }
    },
    // 编辑
    handleEdit () {

    },
    // 复制
    handleCopy () {

    },
    // 分享
    handleShare () {

    },
    // 下架
    handleXia () {

    },
    // 删除
    handleDel () {

    }
  }

}
</script>

<style lang="css" scoped>
/* 样式文件 要加scoped作用于当前的组件，方式样式污染 */
[class^="el-icon-ali"],
[class*="el-icon-ali"] {
  font-family: "iconfont" !important;

  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
