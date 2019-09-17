<template>
  <!-- 头部组件 -->
  <div class="allGoodsHeader">
    <div class="headerTab">
      <div class="tabItem" :class="{tabItemActive:tabItemActiveIndex === 0}" @click="tabItemClicked('goodsForSale')">出售中</div>
      <div class="tabItem" :class="{tabItemActive:tabItemActiveIndex === 1}" @click="tabItemClicked('goodsForSaleOut')">已售罄</div>
      <div class="tabItem" :class="{tabItemActive:tabItemActiveIndex === 2}" @click="tabItemClicked('goodsForInStock')">仓库中</div>
    </div>
    <div class="allGoodsFilter">
      <el-form ref="goodsFilterForm" :inline="true" :model="goodsFilterFormData" label-width="120px">
        <el-form-item label="商品名称:" prop="goodsName">
          <el-input v-model="goodsFilterFormData.goodsName" suffix-icon="el-icon-search"
                     placeholder="搜索商品" :style="goodsFilterInputStyle"/>
        </el-form-item>
        <el-form-item label="平台分类:" prop="catId">
          <el-select v-model="goodsFilterFormData.catId" :style="goodsFilterInputStyle">
            <el-option label="请选择平台分类" :value="null"/>
            <el-option v-for="(item,index) in goodsCatOptions" :label="item.catName" :value="item.catId" :key="index"
                       :style="{paddingLeft: (item.level+1)*20+'px'}"/>
          </el-select>
        </el-form-item>
        <el-form-item label="商家分类:" prop="sortId">
          <el-select v-model="goodsFilterFormData.sortId"  :style="goodsFilterInputStyle">
            <el-option label="请选择商家分类" :value="null"/>
            <el-option v-for="(item,index) in goodsSortOptions" :label="item.sortName" :value="item.sortId" :key="index"
                       :style="{paddingLeft: (item.level+1)*20+'px'}"/>
          </el-select>
        </el-form-item>
        <el-form-item label="商品标签:" prop="labelId">
          <el-select v-model="goodsFilterFormData.labelId"  :style="goodsFilterInputStyle">
            <el-option label="请选择商品标签" :value="null"/>
            <el-option v-for="(item,index) in goodsLabelOptions" :label="item.name" :value="item.id" :key="index" />
          </el-select>
        </el-form-item>
        <el-form-item label="上架时间:">
          <el-date-picker v-model="goodsFilterFormData.saleTimeStart" @change="datePickerChange(true)"
                          placeholder="请选择时间" :style="goodsFilterInputStyle"/>
          —
          <el-date-picker v-model="goodsFilterFormData.saleTimeEnd" @change="datePickerChange(false)"
                          placeholder="请选择时间" :style="goodsFilterInputStyle"/>
        </el-form-item>
        <el-form-item label="商品品牌:" prop="brandId">
          <el-select v-model="goodsFilterFormData.brandId" :style="goodsFilterInputStyle">
            <el-option label="请选择品牌" :value="null"/>
            <el-option v-for="(item, index) in goodsBrandOptions" :label="item.brandName" :value="item.id" :key="index"/>
          </el-select>
        </el-form-item>
        <el-form-item label="商品来源:" prop="source">
          <el-select v-model="goodsFilterFormData.source" :style="goodsFilterInputStyle">
            <el-option label="请选择商品来源" :value="null"/>
            <el-option label="自营商品" :value="0"/>
            <el-option label="非自营商品" :value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="活动类型:" prop="goodsType">
          <el-select v-model="goodsFilterFormData.goodsType" :style="goodsFilterInputStyle">
            <el-option label="请选择活动类型" :value="null"/>
            <el-option label="拼团" :value="1"/>
            <el-option label="砍价" :value="2"/>
            <el-option label="秒杀" :value="3"/>
            <el-option label="限时降价" :value="4"/>
            <el-option label="加价购" :value="5"/>
            <el-option label="打包一口价" :value="6"/>
            <el-option label="定金膨胀" :value="7"/>
          </el-select>
        </el-form-item>
        <el-form-item label="商品价格:">
          <el-input v-model.number="goodsFilterFormData.lowShopPrice" @change="shopPriceChange(true)"
                    placeholder="请输入价格" :style="goodsFilterInputStyle"/>
          —
          <el-input v-model.number="goodsFilterFormData.highShopPrice" @change="shopPriceChange(false)"
                    placeholder="请输入价格" :style="goodsFilterInputStyle"/>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {getAllGoodsInitValue} from '@/api/admin/goodsManage/allGoods/allGoods'
import {format} from '@/util/date'
export default {
  name: 'allGoodsHeader',
  watch: {
    /* 用来单独处理在 */
    $route (to) {
      if (to.name === 'goodsForSaleOut') {
        this.tabItemActiveIndex = 1
      } else if (to.name === 'goodsForInStock') {
        this.tabItemActiveIndex = 2
      } else {
        // goodsForSale
        this.tabItemActiveIndex = 0
      }
    }
  },
  // 数据data
  data () {
    return {
      // 当前商品页面状态 0出售中 1已售罄 2仓库中
      tabItemActiveIndex: 0,
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
      goodsFilterInputStyle: {width: '170px'},
      goodsCatOptions: [],
      goodsSortOptions: [],
      goodsBrandOptions: [],
      goodsLabelOptions: []
    }
  },

  methods: {
    /* 顶部导航按钮点击事件 */
    tabItemClicked (routerName) {
      this.$router.push({name: routerName})
    },
    /* 初始化form表单下拉框数据 */
    initFilterData () {
      getAllGoodsInitValue().then(res => {
        let { content } = res
        console.log(content)
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
          retObj[item[idName]] = {'item': item, children: []}
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
          retObj[item.parentId] = {'item': null, children: [selfItem]}
        }
      }

      let retArr = []
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
    /* 获取数据 */
    getFormData () {
      let retData = {
        ...this.goodsFilterFormData
      }
      retData.saleTimeStart = format(this.goodsFilterFormData.saleTimeStart)
      retData.saleTimeEnd = format(this.goodsFilterFormData.saleTimeEnd)

      return retData
    },
    /* 清空过滤条件 */
    resetFormData () {
      this.$refs['goodsFilterForm'].resetFields()
      this.goodsFilterFormData.lowShopPrice = null
      this.goodsFilterFormData.highShopPrice = null
      this.goodsFilterFormData.saleTimeStart = null
      this.goodsFilterFormData.saleTimeEnd = null
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
  .headerTab{
    border-bottom: 1px solid #EEEEEE;
    color: #666;
    display: flex;
  }
  .tabItem{
    width: 50px;
    line-height: 50px;
    margin:0px 5px;
    cursor: pointer;
  }
  .tabItemActive{
    border-bottom: 2px solid #5a8bff;
  }
  .allGoodsFilter{
    padding:20px 0px 0px 0px;
  }
</style>
