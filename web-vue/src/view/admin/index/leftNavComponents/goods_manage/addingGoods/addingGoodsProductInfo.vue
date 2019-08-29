<template>
  <div>
    <!--基本信息配置模块-->
    <div>
      <div class="title">基本信息</div>
      <basicInfo ref="basicInfo"/>
    </div>

    <!--库存/价格信息-->
    <div>
      <div class="title">库存/价格信息</div>
      <stockAndPriceInfo ref="stockAndPriceInfo"/>
    </div>
    <!--配送信息-->
    <div>
      <div class="title">配送信息</div>
      <el-form ref="deliveryInfoForm" :model="goodsProductInfo" label-width="120px">
        <el-form-item label="运费模板：" prop="deliverTemplateId">
          <el-select v-model="goodsProductInfo.deliverTemplateId" size="small" @change="deliverTemplateChange" style="width:170px;">
            <el-option v-for="(item,index) in deliverTemplateData" :value="item.deliverTemplateId" :label="item.templateName" :key="index"/>
          </el-select>
          <el-link type="primary" :underline="false" @click="deliverTemplateSelectRefresh" href="#" style="margin:0 5px;">刷新
          </el-link>
          |
          <el-link type="primary" :underline="false" href="#" style="margin:0 5px;">新建标签</el-link>
          |
          <el-link type="primary" :underline="false" href="#" style="margin:0 5px;">管理标签</el-link>
          <div class="deliverTemplateContentWrap">
            <div class="deliverTemplateContentTitle">
              <div>
                {{deliverTemplateCurrentData.templateContent}} 此处是运费模板头部信息，主要展示运费模板其他区域运费信息
              </div>
              <el-link style="position:absolute;right: 10px;top: 0px;" type="primary" :underline="false" href="#" >查看详情</el-link>
            </div>
            <div class="deliverTemplateContent">
              {{deliverTemplateCurrentData.templateContent}} 运费模板详情，这个div在的显示需要通过后台的数据进行动态判断，具体如何判断需要和运费模板后端交流，可以咨询良辰
            </div>
          </div>
        </el-form-item>
        <el-form-item label="商品重量：" prop="goodsWeight">
          <el-input-number v-model="goodsProductInfo.goodsWeight" step-strictly size="small" controls-position="right" :min="0" style="width:170px;"/>
          <span>&nbsp;&nbsp;Kg</span>
        </el-form-item>
        <el-form-item label="发货地：" prop="deliverPlace">
          <el-input ref="deliverPlaceInput" v-model="goodsProductInfo.deliverPlace" size="small"  style="width:230px;"/>
          <span class="inputTip">最多15个字</span>
        </el-form-item>
      </el-form>
      <div class="title">其他信息</div>
      <el-form ref="otherInfoForm" :model="goodsProductInfo" label-width="120px">
        <el-form-item label="会员专享商品：" prop="deliverTemplateId">
          <el-checkbox v-model="goodsProductInfo.isCardExclusive">用户持有会员卡才可以购买此商品</el-checkbox>
          <div v-if="goodsProductInfo.isCardExclusive">
            <el-select v-model="cardSelectedTempVal" size="small" @change="cardSelectChange" placeholder="请选择会员卡" style="width:170px;">
              <el-option v-for="(item,index) in cardsSelectOptions" :value="item.id" :label="item.cardName" :key="index"/>
            </el-select>
            <el-link type="primary" :underline="false" @click="cardSelectRefresh" href="#" style="margin:0 5px;">刷新
            </el-link>
            |
            <el-link type="primary" :underline="false" href="#" style="margin:0 5px;">新建会员卡</el-link>
            |
            <el-link type="primary" :underline="false" href="#" style="margin:0 5px;">管理会员卡</el-link>
            <div v-if="cardSelectedItems.length>0" style="display: flex;flex-wrap: wrap;align-items:center;background-color: #f8f8f8;">
              <div>已选：</div>
              <div class="selectedWrap" v-for="(item,index) in cardSelectedItems" :key="index">
                {{item.cardName}}
                <span @click="deleteCard(item,index)" class="deleteIcon">×</span>
              </div>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <!--<stockAndPriceInfo ref="priceInfo" />-->
    <!--&lt;!&ndash; 配送信息 &ndash;&gt;-->
    <!--<div class="blockWrap">-->
    <!--<div class="title">配送信息</div>-->
    <!--</div>-->
    <!--<deliveryInfo />-->
    <!--&lt;!&ndash; 其他信息 &ndash;&gt;-->
    <!--<div class="blockWrap">-->
    <!--<div class="title">其他信息</div>-->
    <!--</div>-->
    <!--<otherInfo />-->
  </div>
</template>
<script>
// 组件导入
import basicInfo from './basicInfo'
import stockAndPriceInfo from './stockAndPriceInfo'
export default {
  components: {basicInfo, stockAndPriceInfo},
  data () {
    return {
      goodsProductInfo: {
        deliverTemplateId: null,
        goodsWeight: null,
        deliverPlace: null,
        isCardExclusive: false,
        isOnSale: null,
        saleTime: null
      },
      /* 运费模板辅助数据 */
      // 当前选中的运费模板，用来展示模板的详细信息使用
      deliverTemplateCurrentData: null,
      /* 运费模板数据 */
      deliverTemplateData: [],
      /* 会员专享卡数据 */
      cardsSelectOptions: [{id: 1, cardName: 'cardName'}],
      cardSelectedTempVal: null,
      cardSelectedItems: []
    }
  },
  methods: {
    /* 运费模板下拉框change处理函数 */
    deliverTemplateChange (deliverTemplateId) {
      for (let i = 0; i < this.deliverTemplateCurrentData.length; i++) {
        if (this.deliverTemplateCurrentData[i].deliverTemplateId === deliverTemplateId) {
          this.deliverTemplateCurrentData = this.deliverTemplateCurrentData[i]
          break
        }
      }
    },
    /* 刷新运费模板 */
    deliverTemplateSelectRefresh () {
      this.deliverTemplateDataInit()
    },
    /* 初始化运费模板数据 */
    // TODO: 从后台获取去运费模板数据，包括了默认模板数据，将默认模板项的id设置为null，并且放在deliverTemplateData的第一项
    deliverTemplateDataInit () {
      this.deliverTemplateData.push({
        deliverTemplateId: null,
        templateName: '店铺默认运费模板',
        templateContent: 'content'
      })
      this.deliverTemplateData.push({
        deliverTemplateId: 1,
        templateName: '运费模板1',
        templateContent: 'content1'
      })
      this.deliverTemplateData.push({
        deliverTemplateId: 2,
        templateName: '运费模板2',
        templateContent: 'content2'
      })

      this.deliverTemplateCurrentData = this.deliverTemplateData[0]
    },
    /* 会员专享商品下拉框change */
    cardSelectChange () {
      this.cardsSelectOptions = this.cardsSelectOptions.filter(item => {
        if (item.id === this.cardSelectedTempVal) {
          this.cardSelectedItems.push(item)
          return false
        }
        return true
      })
      this.cardSelectedTempVal = null
    },
    /* 刷新会员卡下拉列表，要将已选的项剔除 */
    cardSelectRefresh () {

    },
    /* 删除商品已选会员卡,并将删除的项添加到下拉框内 */
    deleteCard (item, index) {
      this.cardSelectedItems.splice(index, 1)
      this.cardsSelectOptions.push(item)
    }
  },
  mounted () {
    // 初始化运费模板
    this.deliverTemplateDataInit()
  }
}

</script>
<style scoped>
  .inputTip {
    color: #999;
    margin-left: 15px;
  }
  .title {
    font-weight: bold;
    height: 40px;
    background: #f8f8f8;
    line-height: 40px;
    width: 100%;
    padding-left: 10px;
    margin: 20px 0;
  }
  .deliverTemplateContentWrap{
    position: relative;
    background-color: #f8f8f8;
    padding: 10px;
    box-sizing: border-box;
  }
  .deliverTemplateContentTitle{
    width: calc(100% - 30px);
  }
  .deliverTemplateContent{
    border-top: 1px solid #ccc;
  }
  .selectedWrap{
    min-width: 70px;
    height: 22px;
    border: 1px solid #ccc;
    line-height: 22px;
    text-align: center;
    padding: 0px 5px;
    margin:0px 5px;
    background-color: #fff;
    position: relative;
  }
  .selectedWrap .deleteIcon {
    width: 17px;
    height: 17px;
    color: #fff;
    background: #ccc;
    border: 1px solid #ccc;
    border-radius: 50%;
    line-height: 17px;
    text-align: center;
    position: absolute;
    top: -8px;
    right: -8px;
    cursor: pointer;
    opacity: 0.8;
  }
</style>
