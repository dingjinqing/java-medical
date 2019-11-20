<template>
  <!-- 头部搜索输入框组件 -->
  <div class="allGoodsHeader">
    <div class="allGoodsFilter">
      <el-form
        ref="goodsFilterForm"
        :inline="true"
        :model="goodsFilterFormData"
        label-width="120px"
      >
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
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.category')+'：'"
          prop="catId"
        >
          <el-select
            v-model="goodsFilterFormData.catId"
            :style="goodsFilterInputStyle"
            size="small"
          >
            <el-option
              :label="$t('allGoods.allGoodsHeaderData.chooseCategory')"
              :value="null"
            />
            <el-option
              v-for="(item,index) in goodsCatOptions"
              :label="item.catName+' ('+item.goodsNumberSum+')'"
              :value="item.catId"
              :key="index"
              :style="{paddingLeft: (item.level+1)*20+'px'}"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          :label="$t('allGoods.allGoodsHeaderData.sort')+'：'"
          prop="sortId"
        >
          <el-select
            v-model="goodsFilterFormData.sortId"
            :style="goodsFilterInputStyle"
            size="small"
          >
            <el-option
              :label="$t('allGoods.allGoodsHeaderData.chooseSort')"
              :value="null"
            />
            <el-option
              v-for="(item,index) in goodsSortOptions"
              :label="item.sortName+' ('+item.goodsNumberSum+')'"
              :value="item.sortId"
              :key="index"
              :style="{paddingLeft: (item.level+1)*20+'px'}"
            />
          </el-select>
        </el-form-item>
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
        <el-form-item :label="$t('allGoods.allGoodsHeaderData.shopPrice')+'：'">
          <el-input
            v-model.number="goodsFilterFormData.lowShopPrice"
            @change="shopPriceChange(true)"
            :placeholder="$t('allGoods.allGoodsHeaderData.inputShopPrice')"
            :style="goodsFilterInputStyle"
            size="small"
          />
          至
          <el-input
            v-model.number="goodsFilterFormData.highShopPrice"
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
import { getAllGoodsInitValue } from '@/api/admin/goodsManage/allGoods/allGoods'
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
  computed: {
    // 查询过滤对象的字符串格式化
    goodsFilterFormDataString () {
      return {
        goodsName: this.goodsFilterFormData.goodsName,
        catName: this.getCatNameById(this.goodsFilterFormData.catId),
        sortName: this.getSortNameById(this.goodsFilterFormData.sortId),
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
        goodsName: null,
        catId: null,
        sortId: null,
        labelId: null,
        brandId: null,
        // 商品来源
        source: null,
        // 活动类型
        goodsType: null,
        saleTimeStart: null,
        saleTimeEnd: null,
        lowShopPrice: null,
        highShopPrice: null,
        isOnSale: null,
        goodsNumber: null,
        saleType: null
      },
      goodsFilterInputStyle: { width: '170px' },
      goodsCatOptions: [],
      goodsSortOptions: [],
      goodsBrandOptions: [],
      goodsLabelOptions: []
    }
  },

  methods: {
    /* 初始化form表单下拉框数据 */
    initFilterData () {
      getAllGoodsInitValue(this.initSortCatParams).then(res => {
        let { content } = res
        this.goodsBrandOptions = content.goodsBrands
        this.goodsLabelOptions = content.goodsLabels
        this.goodsSortOptions = this._disposeGoodsSortAndCatData(content.goodsSorts, 'sortId')
        this.goodsCatOptions = this._disposeGoodsSortAndCatData(content.sysCates, 'catId')
      })
    },
    /* 处理后台传入的商家分类数据 */
    _disposeGoodsSortAndCatData (data, idName) {
      let retObj = {}

      for (let i = 0; i < data.length; i++) {
        let item = data[i]

        // 是否自身节点被创建过（子节点先遍历到了）
        let selfItem = retObj[item[idName]]
        if (selfItem === undefined) {
          // 未遍历到则初始化自己
          retObj[item[idName]] = { 'item': item, children: [] }
          selfItem = retObj[item[idName]]
        } else {
          // 已创建过，（因提前遍历了子节点而创建）
          selfItem.item = item
        }

        let parentItem = retObj[item.parentId]
        // 有父亲直接插入
        if (parentItem !== undefined) {
          parentItem.children.push(selfItem)
        } else {
          // 没有则创建临时父亲
          retObj[item.parentId] = { 'item': null, children: [selfItem] }
        }
      }

      let retArr = []

      if (data.length === 0) {
        return retArr
      }
      let rootArr = retObj['0'].children
      // 处理结果将对象变为数组
      for (let i = 0; i < rootArr.length; i++) {
        let retItem = rootArr[i]
        retArr.push(retItem.item)
        if (retItem.children.length > 0) {
          rootArr.splice(i + 1, 0, ...(retItem.children))
        }
      }
      return retArr
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
      if (typeof this.goodsFilterFormData.lowShopPrice !== 'number') {
        this.goodsFilterFormData.lowShopPrice = null
      }
      if (typeof this.goodsFilterFormData.highShopPrice !== 'number') {
        this.goodsFilterFormData.highShopPrice = null
      }

      if (this.goodsFilterFormData.lowShopPrice === null || this.goodsFilterFormData.highShopPrice === null) {
        return
      }
      if (this.goodsFilterFormData.lowShopPrice <= this.goodsFilterFormData.highShopPrice) {
        return
      }
      if (isStart) {
        this.goodsFilterFormData.lowShopPrice = null
      } else {
        this.goodsFilterFormData.highShopPrice = null
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
      this.goodsFilterFormData.lowShopPrice = null
      this.goodsFilterFormData.highShopPrice = null
      this.goodsFilterFormData.saleTimeStart = null
      this.goodsFilterFormData.saleTimeEnd = null
    },

    getCatNameById (catId) {
      let catName = null
      this.goodsCatOptions.forEach((item, index) => {
        if (item.catId === catId) {
          catName = item.catName
        }
      })
      return catName
    },
    getSortNameById (sortId) {
      let sortName = null
      this.goodsSortOptions.forEach((item, index) => {
        if (item.sortId === sortId) {
          sortName = item.sortName
        }
      })
      return sortName
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
  width: 50px;
  line-height: 50px;
  margin: 0px 5px;
  cursor: pointer;
}
.tabItemActive {
  border-bottom: 2px solid #5a8bff;
}
.allGoodsFilter {
  padding: 15px;
  padding-bottom: 0;
  /* padding: 20px 0px 0px 0px; */
}
</style>
