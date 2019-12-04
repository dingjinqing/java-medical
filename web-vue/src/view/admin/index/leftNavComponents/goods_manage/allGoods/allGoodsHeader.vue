<template>
  <!-- 头部搜索输入框组件 -->
  <div class="allGoodsHeader">
    <div class="allGoodsFilter">
      <el-form
        ref="goodsFilterForm"
        :inline="true"
        :model="goodsFilterFormData"
        label-width="100px"
      >
        <!--商品名称-->
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsName')+'：'"
          prop="goodsName"
        >
          <el-input
            v-model="goodsFilterFormData.goodsName"
            suffix-icon="el-icon-search"
            :placeholder="$t('allGoods.allGoodsHeaderData.searchGoods')"
            :style="goodsFilterInputStyle"
            size="small"
            clearable
          />
        </el-form-item>
        <!--平台分类树-->
        <sortCatTreeSelect
          ref="catTree"
          :filterGoodsInfo="initSortCatParams"
          treeType="cat"
          :selectedId.sync="goodsFilterFormData.catId"
        />
        <!--商家分类树-->
        <sortCatTreeSelect
          ref="sortTree"
          :filterGoodsInfo="initSortCatParams"
          treeType="sort"
          :selectedId.sync="goodsFilterFormData.sortId"
        />
        <!--商品标签-->
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsLabel')+'：'"
          prop="labelId"
        >
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
        </el-form-item>
        <!--上架开始时间和结束时间-->
        <el-form-item :label="$t('allGoods.allGoodsHeaderData.saleTime')+'：'">
          <el-date-picker
            v-model="goodsFilterFormData.saleTimeStart"
            @change="datePickerChange(true)"
            :placeholder="$t('allGoods.allGoodsHeaderData.chooseSaleTime')"
            :style="goodsFilterInputStyle"
            size="small"
          />
          至
          <el-date-picker
            v-model="goodsFilterFormData.saleTimeEnd"
            @change="datePickerChange(false)"
            :placeholder="$t('allGoods.allGoodsHeaderData.chooseSaleTime')"
            :picker-options="endTime"
            :style="goodsFilterInputStyle"
            size="small"
          />
        </el-form-item>
        <!--商品品牌-->
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsBrand')+'：'"
          prop="brandId"
        >
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
        </el-form-item>
        <!--商品来源-->
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsSource')+'：'"
          prop="source"
        >
          <el-select
            v-model="goodsFilterFormData.source"
            :style="goodsFilterInputStyle"
            size="small"
          >
            <el-option
              v-for="(item,index) in goodsSourceOptions"
              :label="item.label"
              :value="item.value"
              :key="index"
            />
          </el-select>
        </el-form-item>
        <!--活动类型-->
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.goodsType')+'：'"
          prop="goodsType"
        >
          <el-select
            v-model="goodsFilterFormData.goodsType"
            :style="goodsFilterInputStyle"
            size="small"
          >
            <el-option
              v-for="(item,index) in goodsTypeOptions"
              :label="item.label"
              :value="item.value"
              :key="index"
            />
          </el-select>
        </el-form-item>
        <!--商品价格区间-->
        <el-form-item :label="$t('allGoods.allGoodsHeaderData.shopPrice')+'：'">
          <el-input
            v-model="goodsFilterFormData.lowShopPrice"
            @change="shopPriceChange(true)"
            :placeholder="$t('allGoods.allGoodsHeaderData.inputShopPrice')"
            :style="goodsFilterInputStyle"
            size="small"
          />
          至
          <el-input
            v-model="goodsFilterFormData.highShopPrice"
            @change="shopPriceChange(false)"
            :placeholder="$t('allGoods.allGoodsHeaderData.inputShopPrice')"
            :style="goodsFilterInputStyle"
            size="small"
          />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
// 组件导入
import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'
// api导入
import { getGoodsFilterItem } from '@/api/admin/goodsManage/allGoods/allGoods'
// 工具导入
import { format } from '@/util/date'
export default {
  name: 'allGoodsHeader',
  props: ['initSortCatParams'],
  watch: {
    lang () {
      this.goodsSourceOptions = [
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[0],
          value: null
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[1],
          value: 0
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[2],
          value: 1
        }
      ]
      this.goodsTypeOptions = [
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[0],
          value: null
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[1],
          value: 1
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[2],
          value: 3
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[3],
          value: 5
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[4],
          value: 6
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[5],
          value: 98
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[6],
          value: 99
        },
        {
          label: this.$t('allGoods.allGoodsHeaderData.goodsTypeOptions')[7],
          value: 10
        }
      ]
      console.log(this.goodsBrandOptions)
      console.log(this.goodsLabelOptions)
    }
  },
  components: { sortCatTreeSelect },
  computed: {
    // 查询过滤对象的字符串格式化
    goodsFilterFormDataString () {
      return {
        goodsName: this.goodsFilterFormData.goodsName,
        catName: this.getCatName(),
        sortName: this.getSortName(),
        labelName: this.getLabelNameById(this.goodsFilterFormData.labelId),
        brandName: this.getBrandNameById(this.goodsFilterFormData.brandId),
        sourceName: this.getSourceNameById(this.goodsFilterFormData.source),
        typeName: this.getTypeNameById(this.goodsFilterFormData.goodsType),
        saleTimeStart: format(this.goodsFilterFormData.saleTimeStart),
        saleTimeEnd: format(this.goodsFilterFormData.saleTimeEnd),
        lowShopPrice: this.goodsFilterFormData.lowShopPrice,
        highShopPrice: this.goodsFilterFormData.highShopPrice
      }
    }
  },
  data () {
    return {
      // 结束时间校验
      endTime: {
        disabledDate: time => {
          return time.getTime() < this.goodsFilterFormData.saleTimeStart
        }
      },
      // 国际化使用
      goodsSourceOptions: [],
      goodsTypeOptions: [],
      // 查询过滤对象
      goodsFilterFormData: {
        // 商品名称
        goodsName: null,
        // 平台分类
        catId: null,
        // 商家分类
        sortId: null,
        // 标签id
        labelId: null,
        // 品牌id
        brandId: null,
        // 商品来源
        source: null,
        // 活动类型
        goodsType: null,
        // 上架时间
        saleTimeStart: null,
        // 下架时间
        saleTimeEnd: null,
        // 商品价格开始
        lowShopPrice: null,
        // 商品价格结束
        highShopPrice: null
      },
      goodsFilterInputStyle: { width: '170px' },
      goodsBrandOptions: [],
      goodsLabelOptions: []
    }
  },

  methods: {
    /* 初始化form表单下拉框数据 */
    initFilterData () {
      getGoodsFilterItem({ needGoodsLabel: true, needGoodsBrand: true }).then(res => {
        let { content } = res
        this.goodsBrandOptions = content.goodsBrands
        this.goodsLabelOptions = content.goodsLabels
      })
    },
    /* 验证输入的时间范围是否合法 */
    datePickerChange (isStart) {
      if (this.goodsFilterFormData.saleTimeStart === null || this.goodsFilterFormData.saleTimeEnd === null) {
        return
      }
      if (this.goodsFilterFormData.saleTimeStart.getTime() <= this.goodsFilterFormData.saleTimeEnd.getTime()) {
        return
      }
      if (isStart) {
        this.goodsFilterFormData.saleTimeStart = null
      } else {
        this.goodsFilterFormData.saleTimeEnd = null
      }
    },
    /* 商品价格输入范围是否合法 */
    shopPriceChange (isStart) {
      this.goodsFilterFormData.lowShopPrice = parseFloat(this.goodsFilterFormData.lowShopPrice)
      this.goodsFilterFormData.highShopPrice = parseFloat(this.goodsFilterFormData.highShopPrice)

      let startTypeIsOk = !isNaN(this.goodsFilterFormData.lowShopPrice)
      let endTypeIsOk = !isNaN(this.goodsFilterFormData.highShopPrice)

      if (startTypeIsOk && endTypeIsOk) {
        if (this.goodsFilterFormData.lowShopPrice < 0) {
          this.goodsFilterFormData.lowShopPrice = 0
        }
        if (this.goodsFilterFormData.highShopPrice < 0) {
          this.goodsFilterFormData.highShopPrice = 0
        }
        this.goodsFilterFormData.lowShopPrice = parseFloat(this.goodsFilterFormData.lowShopPrice.toFixed(2))
        this.goodsFilterFormData.highShopPrice = parseFloat(this.goodsFilterFormData.highShopPrice.toFixed(2))
        if (this.goodsFilterFormData.lowShopPrice <= this.goodsFilterFormData.highShopPrice) {
          return
        }
        if (isStart) {
          this.goodsFilterFormData.lowShopPrice = null
        } else {
          this.goodsFilterFormData.highShopPrice = null
        }
      } else {
        if (!startTypeIsOk) {
          this.goodsFilterFormData.lowShopPrice = null
        }
        if (!endTypeIsOk) {
          this.goodsFilterFormData.highShopPrice = null
        }
      }
    },
    /* 获取过滤条件数据 */
    getFormData () {
      let retData = {
        ...this.goodsFilterFormData
      }
      retData.saleTimeStart = format(this.goodsFilterFormData.saleTimeStart)
      retData.saleTimeEnd = format(this.goodsFilterFormData.saleTimeEnd)

      return retData
    },
    /* 获取过滤条件格式化字符串 */
    getFormDataString () {
      return this.goodsFilterFormDataString
    },
    /* 清空过滤条件 */
    resetFormData () {
      this.$refs['goodsFilterForm'].resetFields()
      this.$refs['catTree'].clearData()
      this.$refs['sortTree'].clearData()
      this.goodsFilterFormData.lowShopPrice = null
      this.goodsFilterFormData.highShopPrice = null
      this.goodsFilterFormData.saleTimeStart = null
      this.goodsFilterFormData.saleTimeEnd = null
    },
    getCatName () {
      return this.$refs['catTree'].getSelectedText()
    },
    getSortName () {
      return this.$refs['sortTree'].getSelectedText()
    },
    getLabelNameById (labelId) {
      let labelName = null
      this.goodsLabelOptions.forEach((item, index) => {
        if (item.id === labelId) {
          labelName = item.name
        }
      })
      return labelName
    },
    getBrandNameById (brandId) {
      let brandName = null
      this.goodsBrandOptions.forEach((item, index) => {
        if (item.id === brandId) {
          brandName = item.brandName
        }
      })
      return brandName
    },
    getSourceNameById (sourceId) {
      let sourceName = null
      this.goodsSourceOptions.forEach((item, index) => {
        if (item.value === sourceId && item.value !== null) {
          sourceName = item.label
        }
      })
      return sourceName
    },
    getTypeNameById (typeId) {
      let typeName = null
      this.goodsTypeOptions.forEach((item, index) => {
        if (item.value === typeId && item.value !== null) {
          typeName = item.label
        }
      })
      return typeName
    }
  },
  mounted () {
    // 初始化form表单下拉框数据
    this.initFilterData()
    // 初始化国际语言
    this.langDefault()
  }
}
</script>

<style scoped>
.headerTab {
  border-bottom: 1px solid #eeeeee;
  color: #666;
  display: flex;
}
.tabItem {
  font-size: 14px;
  min-width: 50px;
  line-height: 40px;
  /* margin: 0px 5px; */
  margin-right: 20px;
  cursor: pointer;
}
.tabItemActive {
  border-bottom: 1.5px solid #5a8bff;
}
.allGoodsFilter {
  padding: 10px;
  padding-bottom: 0;
  /* padding: 20px 0px 0px 0px; */
}
</style>
