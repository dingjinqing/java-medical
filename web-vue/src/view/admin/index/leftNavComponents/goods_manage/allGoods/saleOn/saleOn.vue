<template>
  <!-- 出售中商品组件 -->
  <div class="saleOn">
    <!-- 测试按钮 -->
    <el-button @click="handleTest">测试按钮</el-button>
    <!-- header -->
    <headOperation />
    <!-- 表格 -->
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      header-align="center"
      :header-cell-style="tableHeaderStyle"
      :span-method="arraySpanMethod"
    >
      <!-- 用来实现多选 -->
      <el-table-column
        type="selection"
        width="30"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="goodsName"
        label="名称"
        align="center"
      >
        <template slot-scope="scope">
          <slot
            :row="scope.row"
            :$index="scope.$index"
          >
            <section class="goods_name">
              <section>
                <!-- 商品的图片 -->
                <el-image
                  style="width: 60px; height: 60px"
                  :src="scope.row.goodsImg"
                  fit="fill"
                ></el-image>
              </section>
              <section>
                <!-- tag信息 -->
                <span>
                  <el-tag
                    size="mini"
                    type="danger"
                    effect="dark"
                  >
                    自营
                  </el-tag>
                </span>
                <span>{{scope.row.name}}</span>
              </section>
            </section>
          </slot>
        </template>

      </el-table-column>
      <el-table-column
        prop="price"
        label="价格"
        align="center"
      >
        <!-- 价格 -->
        <template slot-scope="scope">
          <slot
            :row="scope.row"
            :$index="scope.$index"
          >
            <section v-if="scope.row.status === 1">
              <span>{{scope.row.price}}</span>
              <i
                class="el-icon-edit-outline"
                style="color:#739DFF;fontSize:20px"
                @click="handleChangePrice(scope.$index)"
              ></i>
            </section>
            <section v-else>
              <el-input
                @keyup.enter.native="handleSaveChange(scope.$index)"
                @blur="handleSaveChange"
                autofocus
                style="width:60px"
                v-model="scope.row.price"
                size="mini"
              ></el-input>

            </section>

          </slot>
        </template>
      </el-table-column>
      <el-table-column
        prop="code"
        label="商品货号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="catName"
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
        prop="brandName"
        label="品牌"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="库存"
        align="center"
      >
        <!-- 库存 -->
        <template slot-scope="scope">
          <slot
            :row="scope.row"
            :$index="scope.$index"
          >
            <section v-if="true">
              <span>{{scope.row.stock}}</span>
              <i
                class="el-icon-edit-outline"
                style="color:#739DFF;fontSize:20px"
                @click="handleChangePrice(scope.$index)"
              ></i>
            </section>
            <section v-else>
              <el-input
                @keyup.enter.native="handleSaveChange(scope.$index)"
                @blur="handleSaveChange"
                autofocus
                style="width:60px"
                v-model="scope.row.stock"
                size="mini"
              ></el-input>

            </section>

          </slot>
        </template>
      </el-table-column>
      <el-table-column
        prop="sales"
        label="销量"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="商品标签"
        align="center"
      >
        <template slot-scope="scope">
          <slot
            :row="scope.row"
            :$index="scope.$index"
          >
            <span>官方正品</span>
            <el-button type="text">设置</el-button>
          </slot>
        </template>
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
    <!-- 底部 -->
    <allGoodsFooter />
  </div>
</template>
<script>
import headOperation from '../headOperation'
import allGoodsFooter from '../allGoodsFooter'
// import { getGoodsList } from '@/api/admin/goods_manage/allGoods/allGoods'
export default {
  name: 'saleOn',
  components: { headOperation, allGoodsFooter },
  // 数据data
  data () {
    return {
      // 表格数据
      tableData: [{
        name: `华为手机`,
        price: `6000`,
        code: `G10001`,
        sort1: `手机`,
        sort2: `电子`,
        brand: `HUAWEI`,
        stock: `10`,
        sales: `2222`,
        label: `正品行货`,
        status: 1
      }, {
        name: `苹果手机`,
        price: `5000`,
        code: `G10000`,
        sort1: `手机`,
        sort2: `电子`,
        brand: `IPhone`,
        stock: `200`,
        sales: `12`,
        label: `正品行货`,
        status: 1
      }]
      // showPrice: true // 显示价格
    }
  },
  // Vue生命周期钩子函数
  mounted () {
    this.fetchTableData()
  },
  // methods 方法
  methods: {
    // 测试按钮
    handleTest () {
      //       {
      // brandName: "测试1"
      // catId: 2
      // catName: "图书，音像"
      // goodsId: 5
      // goodsImg: "imgimgimgigm"
      // goodsLabels: null
      // goodsName: "SK-II 大红瓶 冻龄护肤套装（微肌因修护精华霜50g）（面霜女 补水保湿 紧致 面部精华 淡化细纹 细致毛孔）"
      // goodsNumber: 5
      // goodsSaleNum: 0
      // goodsSn: "9d8e571b-47b6-40e5-a532-aeda9dcbe8ab"
      // shopPrice: 15
      // sortId: 3
      // sortName: null
      //       }

    },
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

    },
    // 合并列行
    arraySpanMethod ({ row, column, rowIndex, columnIndex }) {

    },
    // 每行的改变价格
    handleChangePrice (index) {
      this.tableData.forEach((item, i) => {
        if (i === index) item['status'] = !item['status']
      })
    },
    // 保存修改
    handleSaveChange (index) {

    },
    // 初始化表格的数据
    fetchTableData () {
      // getGoodsList({}).then(res => {
      //   const { error, content } = res
      //   if (error === 0) {
      //     const { dataList } = content
      //     console.log(dataList)
      //     this.tableData = dataList
      //   }
      // }).catch(err => console.log(err))
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

.goods_name {
  display: flex;
}
</style>
