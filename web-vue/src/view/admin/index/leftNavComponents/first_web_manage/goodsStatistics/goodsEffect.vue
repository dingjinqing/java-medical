<template>
  <div class="label">
    <div class="labelItem">商品效果</div>
    <el-select
      v-model="timeSelect"
      size="small"
      clearable
      @change="dateChangeHandler"
      class="timeSelect"
    >
      <el-option
        v-for="item in timeRange"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>
    <!--商家分类树-->
    <sortCatTreeSelect
      ref="sortTree"
      :filterGoodsInfo="initSortCatParams"
      treeType="sort"
      :selectedId.sync="goodsFilterFormData.sortId"
    />
    <!--商品标签-->
    <el-select
      v-model="goodsFilterFormData.labelId"
      :style="goodsFilterInputStyle"
      size="small"
    >
      <el-option
        :label="$t('allGoods.allGoodsHeaderData.chooseGoodsLabel')"
        :value="null"
      />
      <el-option
        v-for="(item,index) in goodsLabelOptions"
        :label="item.name"
        :value="item.id"
        :key="index"
      />
    </el-select>
    <!--商品品牌-->
    <el-select
      v-model="goodsFilterFormData.brandId"
      :style="goodsFilterInputStyle"
      size="small"
    >
      <el-option
        :label="$t('allGoods.allGoodsHeaderData.chooseGoodsBrand')"
        :value="null"
      />
      <el-option
        v-for="(item, index) in goodsBrandOptions"
        :label="item.brandName"
        :value="item.id"
        :key="index"
      />
    </el-select>
    <el-button
      size="small"
      type="primary"
    >导出商品</el-button>
    <!-- 表格数据部分 -->
    <div style="width:95%">
      <el-table
        :data="tableData"
        style="width: 100%"
      >
        <el-table-column
          prop="date"
          label="日期"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="180"
        >
        </el-table-column>
        <el-table-column
          prop="address"
          label="地址"
        >
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'
export default {
  components: { sortCatTreeSelect },
  data () {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }],
      search: '',
      timeSelect: 1,
      timeRange: this.$t('userStatistics.timeRange'),
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      },
      // 商品相关下拉框
      // 查询过滤对象
      goodsFilterFormData: {
        // 商家分类
        sortId: null,
        // 标签id
        labelId: null,
        // 品牌id
        brandId: null
      },
      goodsBrandOptions: [],
      goodsLabelOptions: []
    }
  },
  methods: {
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    }
  }
}

</script>
<style lang="scss" scoped>
.label {
  padding: 10px;
  background: #fff;
  margin-top: 5px;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
    margin-left: 10px;
    .tc_left {
      flex: 1;
      display: flex;
      align-items: center;
    }
    .tc_right {
      flex: 1;
      text-align: right;
      margin-right: 15px;
    }
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 10px 10px;
  }
}
</style>
