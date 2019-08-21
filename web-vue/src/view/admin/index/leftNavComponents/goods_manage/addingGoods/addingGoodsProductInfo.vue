<template>
  <div class="addingGoodsProductInfo">
    <!-- 基本信息 -->
    <section class="title">
      <span>基本信息</span>
    </section>
    <basicInfo ref="basicInfo" />
    <!--库存/价格信息-->
    <section class="title">
      <span>库存/价格信息</span>
    </section>
    <priceInfo ref="priceInfo" />
    <!-- <stockAndPriceInfo /> -->
    <!-- 配送信息 -->
    <section class="title">
      <span>配送信息</span>
    </section>
    <deliveryInfo />
    <!-- 其他信息 -->
    <section class="title">
      <span>其他信息</span>
    </section>
    <otherInfo />
    <!-- 底部按钮组 -->
    <section class="addingGoodsFooter">
      <el-button
        class="btn"
        type="primary"
        size="small"
        @click.native.prevent="handleToList"
      >保存后返回列表</el-button>
      <el-button
        class="btn"
        size="small"
        @click.native.prevent="handleNextStep"
      >下一步</el-button>
    </section>

  </div>
</template>
<script>
import { goodsList, getGoodsList } from '@/api/admin/goods_manage/addingGoods/addingGoods'
import basicInfo from './basicInfo'
import otherInfo from './otherInfo'
import deliveryInfo from './deliveryInfo'
import priceInfo from './priceInfo'
export default {
  components: { basicInfo, priceInfo, otherInfo, deliveryInfo },
  props: {
    active: Number
  },
  mounted () {
    // 获取子组件们的数据
    this.getChildData()
  },
  methods: {
    // 获取子组件的数据
    getChildData () {

    },
    // 保存后返回列表
    handleToList () {
      getGoodsList({

        'goodsName': '苹果手机',
        'orderField': 'shopPrice'

      }).then(res => console.log(res)).catch(err => console.log(err))
      // console.log(this.$refs.basicInfo.getFormData)
      // 基本信息表单数据
      let basicInfo = this.$refs.basicInfo.getFormData
      // console.log(basicInfo)
      // 价格信息
      let stockPriceInfo = this.$refs.priceInfo.getFormData
      // console.log(stockPriceInfo)
      Object.assign(basicInfo, stockPriceInfo)

      console.log(basicInfo)
      // let params = {
      //   catId: 233,
      //   goodsAd: '',
      //   goodsImg: 'http://jmpdevimg.weipubao.cn/upload/245547/image/20190820/451MdJcYVgSCF63e8ZZp.png',
      //   goodsName: '苹果手机',
      //   goodsNumber: '',
      //   prdMarketPrice: '',
      //   prdNumber: 22,
      //   prdPrice: '5000'
      // }
      goodsList({
        'goodsName': '苹果手机',
        'goodsAd': '手机还行',
        'catId': 233,
        'goodsImg': 'http://jmpdevimg.weipubao.cn/upload/245547/image/20190820/451MdJcYVgSCF63e8ZZp.png',
        'unit': '个',
        'sortId': 23,

        'marketPrice': 5000,

        'limitBuyNum': 1,
        'limitMaxNum': 5,
        'costPrice': 2000,
        'deliverTemplateId': 9,
        'goodsWeight': 0.5,
        'isCardExclusive': 0,
        'canRebate': 1,
        'isOnSale': 1,
        'isPageUp': 1,
        'goodsPageId': 1,
        'goodsSpecs': [],
        'goodsSpecProducts': [
          {
            'prdPrice': 5000,
            'prdCostPrice': 2000,
            'prdMarketPrice': 5200,
            'prdNumber': 22

          }
        ]
      }).then(res => console.log(res)).catch(err => console.log(err))
    },
    // 下一步（去到编辑商品详情）
    handleNextStep () {
      // let obj1 = this.$refs.priceInfo.getPrice
      // let obj2 = this.$refs.basicInfo.getFormData
      // Object.assign(obj1, obj2)
      // console.log(obj1)
      this.$router.push(
        { name: 'details', query: {} }
      )
    }
  }

}
</script>
<style scoped>
.title {
  font-weight: bold;
  height: 40px;
  background: #f8f8f8;
  line-height: 40px;
  width: 100%;
  padding-left: 10px;
  margin-top: 20px;
}
.addingGoodsProductInfo {
  position: relative;
}
.addingGoodsFooter {
  border-top: 1px solid #f2f2f2;
  background-color: pink;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.btn {
  margin: 0 10px;
}
</style>
