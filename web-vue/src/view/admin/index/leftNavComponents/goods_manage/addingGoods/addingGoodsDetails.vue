<template>
  <div style="display: flex;justify-content: center;margin-top: 40px;">
    <div class="phoneWrap">
      <div class="phoneTop">
        <span style="font-weight:800;color:#000;font-size:16px;margin-top:33px;display: inline-block;">商品详情</span>
      </div>
      <div style="height:550px;overflow-y:auto;overflow-x: auto;background-color:#F5F5F5;">
        <!--商品主图-->
        <div style="height:380px;">
          <img :src="goodsProductInfo.goodsImg" style="width:100%;height:100%;"/>
        </div>
        <!--商品信息-->
        <div class="goodsItemWrap">
          <div style="color:#333;font-size:16px;">{{goodsProductInfo.goodsName}}</div>
          <div style="color:#FF6666;font-size:18px;margin:10px 0;">
            <template v-if="goodsNumAndPriceComputed.goodsPriceMin === goodsNumAndPriceComputed.goodsPriceMax">
              ￥ {{goodsNumAndPriceComputed.goodsPriceMin}}
            </template>
            <template v-else>
              ￥ {{goodsNumAndPriceComputed.goodsPriceMin}} - {{goodsNumAndPriceComputed.goodsPriceMax}}
            </template>
          </div>
          <div style="color:#999;font-size:14px;">库存：{{goodsNumAndPriceComputed.goodsNumSum}} {{goodsProductInfo.unit}}</div>
        </div>
        <!--商品描述-->
        <div class="goodsItemWrap goodsDrapItem">
          <div style="width: 70px;height: 20px;border-bottom: 1px solid #ccc;margin-bottom: 5px;">商品描述</div>
          <!--<div style="word-break:break-all;"></div>-->
          <div class="contentTip">
            <p>可在右侧编辑商品详情</p>
            <p>未添加内容时,不显示此模块内容</p>
          </div>
        </div>
        <!--自定义内容区域-->
        <div class="goodsItemWrap goodsDrapItem">
          <div class="contentTip">
            <p style="font-size: 15px;color:#333;">自定义内容区域</p>
            <p>可在右侧选择商品页模板</p>
            <p>未添加内容时,不显示此模块内容</p>
          </div>
        </div>
      </div>
    </div>
    <div class="goodsDetailWrap">
      <div class="goodsDetailItem"><p class="message"><span class="messageTitle">商品基本信息</span><span style="color: #ccc">商品信息为固定样式仅供参考，请以实际效果为准</span></p></div>
      <div class="goodsDetailItem">
        <p class="message">
          <span class="messageTitle">模块位置</span>
          <span>
            <el-radio-group v-model="goodsDetailInfo.isPageUp">
              <el-radio :label="0">自定义内容在上</el-radio>
              <el-radio :label="1">商品详情在上</el-radio>
            </el-radio-group>
          </span>
        </p>
      </div>
      <div class="goodsDetailItem">
        <p class="message"><span class="messageTitle">自定义内容</span></p>
        <p class="message"><span class="messageTitle">商品页模板</span></p>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'addingGoodsDetails',
  props: ['goodsProductInfo'],
  data () {
    return {
      goodsDetailInfo: {
        isPageUp: 1,
        goodsDesc: '<div style="font-size: 14px;">sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨​sdfas第三方第三方的范德萨发的发的是第三方的范德萨sdfas第三方第三方的范德萨发的发的是第三方的范德萨</div>'
      }
    }
  },
  computed: {
    goodsNumAndPriceComputed () {
      let goodsNumSum = 0
      let goodsPriceMin = Number.MAX_VALUE
      let goodsPriceMax = Number.MIN_VALUE

      this.goodsProductInfo.goodsSpecProducts.forEach(item => {
        goodsNumSum += item.prdNumber
        goodsPriceMin = goodsPriceMin > item.prdPrice ? item.prdPrice : goodsPriceMin
        goodsPriceMax = goodsPriceMax < item.prdPrice ? item.prdPrice : goodsPriceMax
      })
      return {
        goodsNumSum,
        goodsPriceMin,
        goodsPriceMax
      }
    }
  }
}
</script>
<style scoped>
.phoneWrap{
  width: 385px;
  border:1px solid #ccc;
  box-sizing: content-box;
}
.phoneTop{
  width: 375px;
  height: 64px;
  margin: 0 auto;
  background: url(../../../../../../assets/adminImg/page_name.png) no-repeat;
  text-align: center;
}
.goodsItemWrap{
  font-size: 14px;
  min-height: 90px;
  background-color: #fff;
  padding:5px 0px 5px 10px;
}
.goodsItemWrap .contentTip{
  color: #ccc;
  height: 150px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.goodsItemWrap .contentTip p{
  margin-top: 5px;
}
.goodsDrapItem{
  margin-top: 10px;
  min-height: 200px;
}
.goodsDrapItem:hover{
  cursor: pointer;
  border: 1px dashed #2057ff;
}
.goodsDetailWrap{
  border:1px solid #ccc;
  border-radius: 5px;
  background-color: #F8F8F8;
  padding: 10px;
  margin-left: 30px;
}
.goodsDetailItem{
  width: 652px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  padding-left: 15px;
  background-color: #fff;
  margin-bottom: 20px;
}
.goodsDetailItem .message{
  padding: 10px 0px;
}
.goodsDetailItem .message .messageTitle{
  display: inline-block;
  width: 120px;
  color: #333;
}
/* 控制整个滚动条 */
::-webkit-scrollbar {
  background-color: #fff;
  width: 5px;
  background-clip: border-box;
}
/* 滚动条中间滑动部分 */
::-webkit-scrollbar-thumb {
  background-color:#ccc;
  border-radius: 5px;
}
::-webkit-scrollbar-thumb:hover{
  background-color: #555d63;
}
</style>
