<template>
  <div>
    <div class="title">{{$t("goodsAddEditInfo.stockAndPriceInfo.title")}}</div>
    <!--库存和价格-->
    <el-form
      ref="stockAndPriceInfoForm"
      :model="goodsProductInfo"
      :rules="stockAndPriceRules"
      label-width="120px"
    >
      <!--商品规格按钮-->
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsPrd')"
        v-if="!specInfoSwitch"
      >
        <el-button
          size="small"
          @click="addSpecClick"
          style="width: 170px;"
        >{{$t("goodsAddEditInfo.stockAndPriceInfo.goodsPrdAdd")}}</el-button>
      </el-form-item>
      <!--商品规格元素-->
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsPrd')"
        v-if="specInfoSwitch"
      >
        <div class="specInfoWrap">
          <template v-for="(specInfoModel,kIndex) in goodsProductInfo.goodsSpecs">
            <div
              class="specInfoItem speInfoItemK"
              :key="'k'+kIndex"
            >
              <div class="specInfoItemTitle">{{$t("goodsAddEditInfo.stockAndPriceInfo.goodsSpecName")}}</div>
              <div class="specInfoItemContent">
                <div class="specInfoItemInputWrap">
                  <input
                    type="text"
                    :value="specInfoModel.specName"
                    @change="specInfoChange(specInfoModel,kIndex,$event.target.value,$event)"
                  />
                  <span
                    @click="deleteSpecInfo(specInfoModel,kIndex)"
                    class="deleteIcon"
                  >×</span>
                </div>
              </div>
            </div>
            <div
              class="specInfoItem speInfoItemV"
              :key="'v'+kIndex"
            >
              <div class="specInfoItemTitle">{{$t("goodsAddEditInfo.stockAndPriceInfo.goodsSpecVal")}}</div>
              <div class="specInfoItemContent">
                <div
                  class="specInfoItemInputWrap"
                  v-for="(specInfoVModel,vIndex) in specInfoModel.goodsSpecVals"
                  :key="vIndex"
                >
                  <input
                    type="text"
                    :value="specInfoVModel.specValName"
                    @change="specValChange(specInfoModel,vIndex,$event.target.value,$event)"
                  />
                  <span
                    @click="deleteSpecVal(specInfoModel,vIndex)"
                    class="deleteIcon"
                  >×</span>
                </div>
                <el-link
                  size="small"
                  :underline="false"
                  @click="addSpecValClick(specInfoModel)"
                  style="margin-left: 5px;"
                >{{$t("goodsAddEditInfo.stockAndPriceInfo.goodsSpecValAdd")}}
                </el-link>
              </div>
            </div>
          </template>
          <el-button
            style="float: right;"
            size="small"
            @click="addSpecInfoClick"
          >{{$t("goodsAddEditInfo.stockAndPriceInfo.goodsSpecAdd")}}</el-button>
        </div>
      </el-form-item>
      <!--商品规格价格元素-->
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsSpecPrice')"
        v-if="specInfoSwitch"
      >
        <div class="specInfoWrap">
          <table>
            <tr>
              <th></th>
              <th>{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsSpecShopPrice')}}</th>
              <th>{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsSpecShopCost')}}</th>
              <th>{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsSpecGoodsNum')}}</th>
              <th>{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsSpecGoodsPrdSn')}}</th>
              <th>{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsSpecGoodsImg')}}</th>
            </tr>
            <tr
              v-for="(item,index) in goodsProductInfo.goodsSpecProducts"
              :key="index"
            >
              <td>{{item.prdDescTemp}}</td>
              <td><input
                  :id="'prdPrice_'+item.prdDesc"
                  v-model.number="item.prdPrice"
                  @change="specPrdInputChange(item.prdPrice,'prdPrice_'+item.prdDesc,item)"
                /></td>
              <td><input
                  :id="'prdCostPrice_'+item.prdCostPrice"
                  v-model.number="item.prdCostPrice"
                  @change="specPrdInputChange(item.prdCostPrice,'prdCostPrice_'+item.prdCostPrice,item)"
                /></td>
              <td><input
                  :id="'prdNumber_'+item.prdNumber"
                  v-model.number="item.prdNumber"
                  @change="specPrdInputChange(item.prdNumber,'prdNumber_'+item.prdNumber,item)"
                /></td>
              <td><input
                  :id="'prdSn_'+item.prdDesc"
                  v-model="item.prdSn"
                  @change="specPrdSnChange(item,index,$event.target.value,$event)"
                /></td>
              <td>
                <div
                  style="margin: 0 auto;width: 30px;height: 30px;border: 1px solid #ccc;cursor: pointer;"
                  @click="prdImgClick(item)"
                >
                  <img
                    v-if="item.prdImg.imgUrl === null"
                    style="width: 30px;height: 30px;"
                    :src="$imageHost+'/image/admin/add_img.png'"
                  >
                  <img
                    v-else
                    style="width: 30px;height: 30px;"
                    :src="item.prdImg.imgUrl"
                  >
                </div>
              </td>
            </tr>
          </table>
          <div style="text-align: center;">
            <span class="batchSpan ">{{$t('goodsAddEditInfo.stockAndPriceInfo.batchUpdate')}}</span>
            <span class="batchSpan linkSpan" @click="unifyPrdSpecAttr('prdPrice')">{{$t('goodsAddEditInfo.stockAndPriceInfo.batchPrice')}}</span>
            <span class="batchSpan linkSpan" @click="unifyPrdSpecAttr('prdCostPrice')">{{$t('goodsAddEditInfo.stockAndPriceInfo.batchCost')}}</span>
            <span class="batchSpan linkSpan" @click="unifyPrdSpecAttr('prdNumber')">{{$t('goodsAddEditInfo.stockAndPriceInfo.batchNum')}}</span>
            <span class="batchSpan linkSpan" @click="unifyPrdSpecAttr('prdImg')">{{$t('goodsAddEditInfo.stockAndPriceInfo.batchImgSrc')}}</span>
          </div>
        </div>
      </el-form-item>
      <!--库存等-->
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsNumber')"
        prop="prdNumber"
      >
        <el-input-number
          ref="prdNumberInput"
          v-model="goodsProductInfo.prdNumber"
          step-strictly
          size="small"
          controls-position="right"
          :min="0"
          :disabled="specInfoSwitch"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsNumberTip')}}</span>
      </el-form-item>
      <!--商品价格-->
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsShopPrice')"
        prop="prdPrice"
      >
        <el-input-number
          ref="prdPriceInput"
          v-model="goodsProductInfo.prdPrice"
          size="small"
          controls-position="right"
          :min="0"
          :precision="2"
          :disabled="specInfoSwitch"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsShopPriceTip')}}</span>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsMarketPrice')"
        prop="marketPrice"
      >
        <el-input-number
          ref="marketPriceInput"
          v-model="goodsProductInfo.marketPrice"
          size="small"
          controls-position="right"
          :min="0"
          :precision="2"
          style="width:170px;"
        />
      </el-form-item>
      <!--商品会员价格复选框-->
      <el-form-item v-if="memberCards.length>0" :label="$t('goodsAddEditInfo.stockAndPriceInfo.goodsGradeMember')">
        <el-checkbox
          v-for="(item,index) in memberCards"
          v-model="item.checked"
          :key="index"
          @change="memberCardCheckedChange(item,$event)"
        >{{item.cardName}}</el-checkbox>
        <br />
        <span
          class="inputTip"
          style="margin-left: 0px;"
        >{{$t('goodsAddEditInfo.stockAndPriceInfo.goodsGradeMemberTip')}}</span>
      </el-form-item>
      <!--商品会员价格设置table-->
      <el-form-item
        label="会员价设置："
        v-if="memberCardPrdShow"
      >
        <div class="specInfoWrap">
          <table v-if="specInfoSwitch">
            <tr>
              <th>未计算</th>
              <th>规格价格(元)</th>
              <template v-for="item in memberCards">
                <th
                  :key="item.id"
                  v-if="item.checked"
                >{{item.cardName}}</th>
              </template>
              <th v-if="unifyCardsPriceShow"></th>
            </tr>
            <tr
              v-for="(item,index) in goodsProductInfo.goodsSpecProducts"
              :key="index"
            >
              <td>{{item.prdDescTemp}}</td>
              <td>{{item.prdPrice}}</td>
              <template v-for="(cardWrap,cardWrapIndex) in item.memberCards">
                <td
                  v-if="cardWrap.card.checked"
                  :key="cardWrapIndex"
                >
                  <input
                    :id="item.prdDesc+cardWrap.card.cardName"
                    type="text"
                    v-model.number="cardWrap.cardPrice"
                    @change="memberCardPriceChange(item.prdPrice,cardWrap.cardPrice,item.prdDesc+cardWrap.card.cardName,cardWrap)"
                  />
                </td>
              </template>
              <td v-if="unifyCardsPriceShow">
                <el-link
                  size="small"
                  :underline="false"
                  @click="unifyMemberCardsPrice(item)"
                >统一会员价</el-link>
              </td>
            </tr>
          </table>
          <table v-else>
            <tr>
              <th>商品价格(元)</th>
              <template v-for="item in memberCards">
                <th
                  :key="item.id"
                  v-if="item.checked"
                >{{item.cardName}}</th>
              </template>
              <th v-if="unifyCardsPriceShow"></th>
            </tr>
            <tr>
              <td>{{goodsProductInfo.prdPrice}}</td>
              <template v-for="item in memberCards">
                <td
                  v-if="item.checked"
                  :key="item.id"
                >
                  <input
                    :id="item.cardName"
                    type="text"
                    v-model.number="item.cardPrice"
                    @change="memberCardPriceChange(goodsProductInfo.prdPrice,item.cardPrice,item.cardName,item)"
                  />
                </td>
              </template>
              <td v-if="unifyCardsPriceShow">
                <el-link
                  size="small"
                  :underline="false"
                  @click="unifyMemberCardsPrice()"
                >统一会员价</el-link>
              </td>
            </tr>
          </table>
        </div>
      </el-form-item>
    </el-form>
    <!-- 展开更多配置 -->
    <div
      @click="handleToChangeArror"
      style="padding: 0 0 30px 50px;"
    >
      <div
        v-if="arrorFlag"
        style="color:rgb(90, 139, 255);cursor:pointer;width: 200px;"
      >
        {{ $t('seckill.openConfigure') }}&nbsp;<img :src="ArrowArr[0].img_1">
      </div>
      <div
        v-if="!arrorFlag"
        style="color:rgb(90, 139, 255);cursor:pointer;width: 200px;"
      >
        {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="ArrowArr[1].img_2">
      </div>
    </div>

    <el-form
      :model="goodsProductInfo"
      :rules="stockAndPriceRules"
      ref="stockAndPriceInfoOtherForm"
      label-width="120px"
      v-show="!arrorFlag"
    >

      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfoOther.limitBuyNum')"
        prop="limitBuyNum"
      >
        <el-input-number
          ref="limitBuyNumInput"
          v-model="goodsProductInfo.limitBuyNum"
          step-strictly
          size="small"
          controls-position="right"
          :min="0"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.stockAndPriceInfoOther.limitBuyNumTip')}}</span>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfoOther.maxBuyNum')"
        prop="limitMaxNum"
      >
        <el-input-number
          ref="limitMaxNumInput"
          v-model="goodsProductInfo.limitMaxNum"
          step-strictly
          size="small"
          controls-position="right"
          :min="0"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.stockAndPriceInfoOther.maxBuyNumTip')}}</span>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfoOther.costPrice')"
        prop="prdCost"
      >
        <el-input-number
          ref="prdCostInput"
          v-model="goodsProductInfo.prdCost"
          step-strictly
          size="small"
          controls-position="right"
          :min="0"
          :disabled="specInfoSwitch"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.stockAndPriceInfoOther.costPriceTip')}}</span>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfoOther.baseSale')"
        prop="baseSale"
      >
        <el-input-number
          v-model="goodsProductInfo.baseSale"
          step-strictly
          size="small"
          controls-position="right"
          :min="0"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.stockAndPriceInfoOther.baseSaleSetting')}}</span>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.stockAndPriceInfoOther.goodsPrdSn')"
        v-if="!specInfoSwitch"
      >
        <el-input
          v-model="goodsProductInfo.prdSn"
          size="small"
          style="width:170px;"
          @change="defaultSpecPrdChangeRepeatCheck"
        />
      </el-form-item>

    </el-form>
    <!--图片dialog-->
    <ImageDalog
      :tuneUp="imgDialogShow"
      pageIndex='pictureSpace'
      :imageSize="[800,800]"
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </div>
</template>
<script>
// TODO: 1.格笛卡尔积中规格图片样式未实现
// TODO: 2.格笛卡尔积中规格input框样式未实现
// TODO: 3.会员价格table表格样式未实现

// 接口函数引入
import {getLevelCardList, isGoodsColumnValueExist} from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// js工具函数导入
import {isNumberBlank, isStrBlank} from '@/util/typeUtil'
import ImageDalog from '@/components/admin/imageDalog'

export default {
  inject: ['isUpdateWrap'],
  components: {
    ImageDalog
  },
  data () {
    return {
      lang: '',
      imgDialogShow: false,
      /* 临时存放和后台交互的数据 */
      goodsProductInfo: {
        // 库存、价格信息
        marketPrice: null,
        // 根据用户填写的goodsSpecs计算出来的笛卡尔集合：[{tempId:1,prdDesc:'color:red;size:x',prdPrice:0,prdCostPrice:0,prdSn:null,prdImg:null}]
        // 其中tempId是在新增的时候由页面内一个全局变量产生的递增值，仅用于前端页面辅助操作，比如传递给商品分销详情页面
        goodsSpecProducts: [],
        currentImgClickedSpecPrdItem: null,
        // 修改商品时保存回显的默认规格值
        updateGoodsSpecProduct: null,
        // 存放sku名和值{ specId: null, specName: null, goodsSpecVals: [{ specValId: null, specValName: null }] }，新增时没有各种id值
        goodsSpecs: [],
        limitBuyNum: 0,
        limitMaxNum: 0,
        baseSale: 0,
        /* 以下为使用默认规格时的辅助数据，最终会被整合到goodsSpecProducts传到后台 */
        prdNumber: 0,
        prdPrice: 0,
        prdCost: 0,
        prdSn: null,
        prdSnBak: null
      },
      stockAndPriceRules: {
        prdNumber: [
          { required: true, message: '请输入商品库存', trigger: 'change' }
        ],
        prdPrice: [
          { required: true, message: '请输入商品价格', trigger: 'change' }
        ],
        prdCost: [
          { required: true, message: '请输入商品成本价格', trigger: 'change' }
        ]
      },
      /* 是否自定义商品规格 */
      specInfoSwitch: false,
      // 前端使用的规格自增id
      goodsSpecProductsIndex: 0,
      /* 更多配置部分 */
      collapseActiveName: 'stockMore',
      /* 会员价辅助数据 */
      memberCards: [],
      memberCardPrdShow: false,
      unifyCardsPriceShow: false,
      arrorFlag: true, // 展开更多配置
      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }]
    }
  },
  watch: {
    lang () {
      this.stockAndPriceRules.prdNumber[0].message = this.$t('goodsAddEditInfo.warningInfo.requireGoodsNumber')
      this.stockAndPriceRules.prdPrice[0].message = this.$t('goodsAddEditInfo.warningInfo.requireGoodsPrice')
      this.stockAndPriceRules.prdCost[0].message = this.$t('goodsAddEditInfo.warningInfo.requireGoodsCostPrice')
    }
  },
  methods: {
    /* 注册所有监听器 */
    _watch () {
      this.$watch('goodsProductInfo.goodsSpecProducts', this._watchGoodsSpecProducts)
    },
    /* 监听GoodsSpecProducts */
    _watchGoodsSpecProducts () {
      if (this.memberCards === undefined || this.memberCards === null) {
        return
      }

      // 当商品规格信息变化时将会员卡信息注入每一个规格项内，用来生成会员价的列表
      this.goodsProductInfo.goodsSpecProducts.forEach(specPrd => {
        if (specPrd.memberCards === undefined) {
          this.$set(specPrd, 'memberCards', [])
          this.memberCards.forEach(item => {
            specPrd.memberCards.push({
              card: item,
              cardPrice: null
            })
          })
        }
      })
    },
    /** 商品规格交互函数结束**/
    prdImgClick (specPrd) {
      this.goodsProductInfo.currentImgClickedSpecPrdItem = specPrd
      this.imgDialogShow = !this.imgDialogShow
    },
    imgDialogSelectedCallback (imgObj) {
      this.goodsProductInfo.currentImgClickedSpecPrdItem.prdImg.imgUrl = imgObj.imgUrl
      this.goodsProductInfo.currentImgClickedSpecPrdItem.prdImg.imgPath = imgObj.imgPath
    },
    /* 统一规格属性函数（填写规格价格的时候，下面那一排批量设置按钮） */
    unifyPrdSpecAttr (attrName) {
      if (this.goodsProductInfo.goodsSpecProducts.length === 0) {
        return
      }
      // 取数组第一个数据的值作为其他值的内容
      let val = this.goodsProductInfo.goodsSpecProducts[0][attrName]
      this.goodsProductInfo.goodsSpecProducts.forEach(item => {
        if (attrName === 'prdImg') {
          item.prdImg = {
            imgUrl: val.imgUrl,
            imgPath: val.imgPath
          }
        } else {
          item[attrName] = val
        }
      })
    },
    /* 商品规格价格、成本价格、库存发生变化变化 */
    specPrdInputChange (val, inputId, item) {
      if (typeof val !== 'number') {
        if (inputId.indexOf('prdPrice_') > -1) {
          item.prdPrice = 0
        }
        if (inputId.indexOf('prdCostPrice_') > -1) {
          item.prdCostPrice = 0
        }
        if (inputId.indexOf('prdNumber_') > -1) {
          item.prdCostPrice = 0
        }
        document.getElementById(inputId).focus()
      }
    },
    /* 规格编码改变 */
    specPrdSnChange (item, index, newVal, event) {
      if (isStrBlank(newVal)) {
        item.prdSn = null
        item.prdSnBak = null
        return
      }

      if (this._isSpecPrdSnRepeated(index, newVal)) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsSkuSnRepeat'), type: 'warning' })
        item.prdSn = item.prdSnBak
        event.target.focus()
        return
      }

      let data = {
        columnCheckFor: 1,
        prdId: item.prdId,
        prdSn: newVal
      }
      isGoodsColumnValueExist(data).then(res => {
        if (res.error === 0) {
          this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsSkuSnRepeat'), type: 'warning' })
          item.prdSn = item.prdSnBak
          event.target.focus()
        } else {
          item.prdSnBak = item.prdSn
        }
      })
    },
    addSpecClick () {
      this.specInfoSwitch = !this.specInfoSwitch
      this.goodsProductInfo.goodsSpecs.push({ specId: null, specName: null, goodsSpecVals: [{ specValId: null, specValName: null }] })
      // 商品sku数据初始化
      this._recalculateSpec()
    },
    /* 添加规格选项按钮 */
    addSpecInfoClick () {
      let specInfoItem = { specId: null, specName: null, goodsSpecVals: [{ specValId: null, specValName: null }] }
      this.goodsProductInfo.goodsSpecs.push(specInfoItem)
    },
    /* 添加规格值按钮 */
    addSpecValClick (specInfoModel) {
      specInfoModel.goodsSpecVals.push({ specValId: null, specValName: null })
    },
    /* 右上角删除规格项按钮处理 */
    deleteSpecInfo (specInfoModel, kIndex) {
      this.goodsProductInfo.goodsSpecs.splice(kIndex, 1)
      if (this.goodsProductInfo.goodsSpecs.length === 0) {
        this.specInfoSwitch = false
      }
      // 触发重计算
      this._recalculateSpec()
    },
    /* 右上角删除规格值按钮处理 */
    deleteSpecVal (specInfoModel, vIndex) {
      let specVal = specInfoModel.goodsSpecVals[vIndex]
      specInfoModel.goodsSpecVals.splice(vIndex, 1)

      this._calculateDeleteSpecVal(specInfoModel, specVal)
    },
    /* 规格项名称改变,specInfoModel:对应规格项，kIndex:规格值在规格项内的下标，newVal:新值 */
    specInfoChange (specInfoModel, kIndex, newVal, event) {
      // 规格名称重复则将input恢复原值，并返回
      if (this._isSpecInfoNameRepeated(kIndex, newVal)) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsSpecNameRepeat'), type: 'warning' })
        event.target.value = specInfoModel.specName
        event.target.focus()
        return
      }

      let oldSpecName = specInfoModel.specName
      specInfoModel.specName = newVal

      // 如果规格名称被修改为null则认为是删除了该规格
      if (isStrBlank(newVal)) {
        // 删除了规格项但是规格下没有有效的规格值
        if (!this._isSpecInfoHasSpecVal(specInfoModel)) {
          // TODO:仅仅触发specInfoLabel值遍历计算
        } else {
          // 删除了规格项但是规格下有有效的规格值，则触发重计算，视为删除了该规格
          this._recalculateSpec()
        }
      } else {
        // 如果原值为null，新值不为null，则认为是新增了规格项
        if (isStrBlank(oldSpecName)) {
          // 规格项有可用规格值则重计算
          if (this._isSpecInfoHasSpecVal(specInfoModel)) {
            this._recalculateSpec()
          } else {
            // 没有有效规格值，则啥也不干
          }
        } else {
          // 修改了规格名字而且有有效的规格值，则进行遍历修改specInfoLabel和spec
          if (this._isSpecInfoHasSpecVal(specInfoModel)) {
            this._calculateChangeSpecInfo(newVal, oldSpecName)
          } else {
            // 没有有效规格值，则啥也不干
          }
        }
      }
    },
    /*
     * 规格值名称改变
     */
    specValChange (specInfoModel, vIndex, newVal, event) {
      // 商品规格值名称有重复的话则将input恢复原值,并返回
      if (this._isSpecValNameRepeated(specInfoModel, vIndex, newVal)) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsSpecValKRepeat'), type: 'warning' })
        event.target.value = specInfoModel.goodsSpecVals[vIndex].specValName
        event.target.focus()
        return
      }

      // 旧名称
      let tempSpecVal = {
        specValName: specInfoModel.goodsSpecVals[vIndex].specValName
      }

      // 设置新名称
      specInfoModel.goodsSpecVals[vIndex].specValName = newVal

      // 如果规格项名为空则只更新一下goodsSpec即可
      if (isStrBlank(specInfoModel.specName)) {
        return null
      }
      if (isStrBlank(newVal)) {
        // 如果规格值被修改为了null,看做删除操作,遍历删除对应项
        this._calculateDeleteSpecVal(specInfoModel, tempSpecVal)
      } else {
        // 旧值为null,而新值不是null则视为新增项
        if (isStrBlank(tempSpecVal.specValName)) {
          // 新增项,此处代码内部可能会触发_recalculateSpec重计算
          this._calculateAddSpecVal(specInfoModel, specInfoModel.goodsSpecVals[vIndex])
        } else {
          // 修改规格值名字，遍历修改对应项
          this._calculateChangeSpecVal(newVal, tempSpecVal.specValName)
        }
      }
    },
    /* 判断规格名称是否存在重复 */
    _isSpecInfoNameRepeated (kIndex, newVal) {
      if (isStrBlank(newVal)) {
        return false
      }
      for (let i = 0; i < this.goodsProductInfo.goodsSpecs.length; i++) {
        if (i === kIndex) {
          continue
        }
        if (this.goodsProductInfo.goodsSpecs[i].specName === newVal) {
          return true
        }
      }
      return false
    },
    /*
     * 判断规格值名称在所有规格项内是否重复
     * specInfoModel: 代表规格项
     * vIndex: 规格值在规格项的
     * */
    _isSpecValNameRepeated (specInfoModel, vIndex, newVal) {
      if (isStrBlank(newVal)) {
        return false
      }
      for (let i = 0; i < this.goodsProductInfo.goodsSpecs.length; i++) {
        let modelItem = this.goodsProductInfo.goodsSpecs[i]

        let isSameFlag = false
        // 同一个规格项
        if (modelItem.specName === specInfoModel.specName) {
          isSameFlag = true
        }
        // 判断重复性
        for (let j = 0; j < modelItem.goodsSpecVals.length; j++) {
          if (j === vIndex && isSameFlag) {
            continue
          }
          if (modelItem.goodsSpecVals[j].specValName === newVal) {
            return true
          }
        }
      }

      return false
    },
    /*
     * 规格编码是否重复
     * index: newVal在goodsSpecProducts数组的索引
     * newVal: 待比较的规格编码值
     * */
    _isSpecPrdSnRepeated (index, newVal) {
      if (isStrBlank(newVal)) {
        return false
      }
      for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
        // 自己不和自己比较
        if (i === index) {
          continue
        }
        if (this.goodsProductInfo.goodsSpecProducts[i].prdSn === newVal) {
          return true
        }
      }
      return false
    },
    /*
     * 判断规格是否存在有效的规格值，只要在所有规格值中存在任意一个规格值非空的项时就返回true,否则返回false
     * specInfoModel: { specId: null, specName: null, goodsSpecVals: [{ specValId: null, specValName: null }] }
     * */
    _isSpecInfoHasSpecVal (specInfoModel) {
      return specInfoModel.goodsSpecVals.some(item => !isStrBlank(item.specValName))
    },
    /* 规格名称改变，当变为null或从null变为非空都会触发重计算，否则就是进行遍历修改 */
    _calculateChangeSpecInfo (newVal, oldVal) {
      let prdDescReg = new RegExp(`^${oldVal}:|;${oldVal}:`)
      for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
        let item = this.goodsProductInfo.goodsSpecProducts[i]
        // replacement 匹配到的字符串，用返回值替换该字符串
        item.prdDesc = item.prdDesc.replace(prdDescReg, replacement => replacement.replace(oldVal, newVal))
      }
    },
    /*
     * 当添加新的规格值的时候或者当规格值由空变为有值的时候都需要手动触发此方法
     * 需要判断是否是新增规格的情况，如果是则需要通过_recalculateSpec重新计算所有的sku
     * 如果仅仅是新增加了一个规格值，则可以先计算出该规格值和其他规格项的产生的sku，然后将这个新的sku添加到已有的sku集合中
     * specInfoModel: 改变后的规格项
     * specVal: 新改变的规格值
     * */
    _calculateAddSpecVal (specInfoModel, specVal) {
      let okCount = 0
      specInfoModel.goodsSpecVals.forEach(item => {
        if (!isStrBlank(item.specValName)) {
          okCount++
        }
      })
      if (okCount === 1) {
        // 表明当前规格只有一个有效的规格值，而且是新增加的，则这时候要触发重新计算，视为新添加规格项
        this._recalculateSpec()
        return
      }
      // goodsSpecs代表新规格值和其他规格项的集合，为计算新增的规格值和其他规格项产生的新的sku做准备
      let goodsSpecs = this.goodsProductInfo.goodsSpecs.map(item => {
        if (item.specName !== specInfoModel.specName) {
          return item
        } else {
          return {
            specName: item.specName,
            goodsSpecVals: [specVal]
          }
        }
      })

      let goodsSpecProducts = [this._getGoodsSpecProductObj()]
      // 新增的sku
      goodsSpecProducts = this._calculateCartesian(0, goodsSpecs, goodsSpecProducts)
      // 将新增的sku添加到已有的sku集合中
      for (let item of goodsSpecProducts) {
        this.goodsProductInfo.goodsSpecProducts.push(item)
      }
    },
    /*
     * 计算规格项值名称改变
     */
    _calculateChangeSpecVal (newVal, oldVal) {
      let prdDescReg = new RegExp(`:${oldVal}$|:${oldVal};`)
      let prdDescTemp = new RegExp(`^${oldVal}|${oldVal}$|\\s${oldVal}\\s`)

      for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
        let item = this.goodsProductInfo.goodsSpecProducts[i]
        // replacement 匹配到的字符串，用返回值替换该字符串
        item.prdDesc = item.prdDesc.replace(prdDescReg, replacement => replacement.replace(oldVal, newVal))

        item.prdDescTemp = item.prdDescTemp.replace(prdDescTemp, replacement => replacement.replace(oldVal, newVal))
      }
    },
    /*
     * 当删除某规格值的时候直接遍历已有笛卡尔结果，并删除包含当前规格值的所有项
     * specInfoModel:已删除对应的specVal项
     * specVal:被删除的specVal项 {specValName:'xx'}
     **/
    _calculateDeleteSpecVal (specInfoModel, specVal) {
      // 如果被删除规格值的规格项还有可以使用的其他有效规格值
      if (this._isSpecInfoHasSpecVal(specInfoModel)) {
        // 通过正则表达式判断需要剔除的项
        let regStr = `:${specVal.specValName}$|:${specVal.specValName};`
        let regExp = new RegExp(regStr)
        let tempArr = this.goodsProductInfo.goodsSpecProducts.filter(item => !regExp.test(item.prdDesc))
        this.goodsProductInfo.goodsSpecProducts = tempArr
      } else {
        // 该规格项没有可以使用的有效规格值
        this._recalculateSpec()
      }
    },
    /* 重新计算规格笛卡尔积 */
    _recalculateSpec () {
      // 商品规格是否存在可以使用的规格项和值
      let specOk = false
      this.goodsProductInfo.goodsSpecs.forEach(specInfoModel => {
        if (!isStrBlank(specInfoModel.specName) && this._isSpecInfoHasSpecVal(specInfoModel)) {
          specOk = true
        }
      })

      if (!specOk) {
        // 商品规格不存在有效数据则直接返回
        this.goodsProductInfo.goodsSpecProducts = []
        return
      }

      // 笛卡尔计算初始化操作
      let goodsSpecProducts = [this._getGoodsSpecProductObj()]

      this.goodsProductInfo.goodsSpecProducts = this._calculateCartesian(0, this.goodsProductInfo.goodsSpecs, goodsSpecProducts)
    },
    /* 递归计算笛卡尔积
     * curIndex:当前计算到的规格项
     * goodsSpecs: 规格项数组 eg:[{ specName:'颜色',goodsSpecVals:[{specValName:'红色'},{specValName:'白色'}]}
     *                            { specName:'尺寸',goodsSpecVals:[{specValName:'X'},{specValName:'L'}]}]
     * goodsSpecProducts:已计算出来的sku项 和返回内容格式相同
     * 返回内容是sku数组，eg:（仅列出了prdDesc和prdDescTemp字段内容，其它字段是_getGoodsSpecProductObj函数的返回内容）
     * [{prdDesc:'颜色:红色;尺寸:X',prdDescTemp:'红色 X'},{prdDesc:'颜色:白色;尺寸:X',prdDescTemp:'白色 X'},
     *  {prdDesc:'颜色:红色;尺寸:L',prdDescTemp:'红色 L'},{prdDesc:'颜色:白色;尺寸:L',prdDescTemp:'白色 L'}]
     * */
    _calculateCartesian (curIndex, goodsSpecs, goodsSpecProducts) {
      // 规格项名和规格值之间的分隔符
      let PRD_VAL_DELIMITER = ':'
      // 不同规格项之间的分隔符
      let PRD_SPEC_DELIMITER = ';'
      // 递归出口，已遍历完所有规格
      if (curIndex >= goodsSpecs.length) {
        return goodsSpecProducts
      }
      // 当前将要进行计算的规格项
      // item : { specId: null, specName: null, goodsSpecVals: [{ specValId: null, specValName: null }] }
      let item = goodsSpecs[curIndex]

      // 规格项名称为空或者规格没有有效的规格值则视为无效项，则直接跳过
      if (isStrBlank(item.specName) || !this._isSpecInfoHasSpecVal(item)) {
        return this._calculateCartesian(curIndex + 1, goodsSpecs, goodsSpecProducts)
      }
      // 用来缓存通过当前规格项计算出来的笛卡尔积
      let tempArr = []

      // 遍历当前规格的所有规格值，依次进行遍历计算
      for (let i = 0; i < item.goodsSpecVals.length; i++) {
        // 当前规格值
        let specVal = item.goodsSpecVals[i]
        // 判断是否规格值的名字是否为空
        if (isStrBlank(specVal.specValName)) {
          continue
        }

        // 遍历已有笛卡尔积，并和当前规格值依次拼接求值，模拟乘法操作
        for (let j = 0; j < goodsSpecProducts.length; j++) {
          let goodsSpec = goodsSpecProducts[j]

          let tempSpec = this._getGoodsSpecProductObj()

          tempSpec.prdDesc = item.specName + PRD_VAL_DELIMITER + specVal.specValName
          tempSpec.prdDescTemp = specVal.specValName

          // 以下是按预定义规则拼接字符串
          if (!isStrBlank(goodsSpec.prdDesc)) {
            tempSpec.prdDesc = PRD_SPEC_DELIMITER + tempSpec.prdDesc
            tempSpec.prdDescTemp = ' ' + tempSpec.prdDescTemp
          }
          tempSpec.prdDesc = goodsSpec.prdDesc + tempSpec.prdDesc
          tempSpec.prdDescTemp = goodsSpec.prdDescTemp + tempSpec.prdDescTemp

          // 保存计算出来的笛卡尔项
          tempArr.push(tempSpec)
        }
      }

      goodsSpecProducts = tempArr
      // 递归计算下一项规格
      return this._calculateCartesian(curIndex + 1, goodsSpecs, goodsSpecProducts)
    },
    // 获取商品规格项对象模板
    _getGoodsSpecProductObj () {
      return {
        tempId: this.goodsSpecProductsIndex++,
        prdId: null, // 更新接口需要数据
        prdPrice: 0, // sku价格 接口需要数据
        prdCostPrice: 0, // sku成本价 接口需要数据
        prdNumber: 0, // sku数量 接口需要数据
        prdSn: null, // sku sn码 用户输入项
        prdSnBak: null, // sku sn码 接口需要数据
        prdImg: {
          imgUrl: null, // 图片全路径 接口需要数据
          imgPath: null // 图片相对路径 接口需要数据
        },
        prdDesc: '', // sku 内容 颜色:红色;尺寸:x 接口需要数据
        prdDescTemp: '' // 规格价格表格第一列显示内容 sku 内容 红色 x
      }
    },
    /** 会员卡部分交互函数 **/
    /* 等级会员卡数据初始化 */
    _memberCardsInit () {
      return getLevelCardList().then(rep => {
        let { content: { dataList } } = rep
        for (let item of dataList) {
          this.memberCards.push({ id: item.id, cardName: item.cardName, checked: false, cardPrice: null, grade: item.grade })
        }
      })
    },
    /* 等级会员卡选择框改变处理函数 */
    memberCardCheckedChange (memberCard, checked) {
      let isOk = this._memberCardCanCheck(memberCard, checked)

      if (!isOk) {
        return
      }

      // 是否要显示会员设置
      let memberCardPrdShow = false
      let checkedNum = 0
      this.memberCards.forEach(item => {
        if (item.checked) {
          memberCardPrdShow = true
          checkedNum++
        }
      })
      this.memberCardPrdShow = memberCardPrdShow
      this.unifyCardsPriceShow = checkedNum > 1
    },
    unifyMemberCardsPrice (specPrdInfo) {
      let price = null
      // 使用自定义规格
      if (specPrdInfo !== undefined) {
        specPrdInfo.memberCards.forEach(cardWrap => {
          if (!cardWrap.card.checked) {
            return
          }
          price !== null ? cardWrap.cardPrice = price : price = cardWrap.cardPrice
        })
      } else {
        this.memberCards.forEach(item => {
          if (!item.checked) {
            return
          }
          price !== null ? item.cardPrice = price : price = item.cardPrice
        })
      }
    },
    /* 会员价格change处理函数 */
    memberCardPriceChange (prdPrice, cardPrice, inputId, item) {
      if (cardPrice === undefined || cardPrice === '') {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.gradPrdPriceIsNull'), type: 'warning' })
        document.getElementById(inputId).focus()
        return
      }

      if (typeof cardPrice !== 'number') {
        item.cardPrice = null
        document.getElementById(inputId).focus()
        return
      }

      if (cardPrice > prdPrice) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.gradePrdPriceHigherThanGoodsPrice'), type: 'warning' })
        document.getElementById(inputId).focus()
      }
    },
    /* 校验是否可以进行会员卡的选择 */
    _memberCardCanCheck (memberCard, checked) {
      if (!checked) {
        return true
      }
      // 商品价格填写校验正确性
      if (!this.specInfoSwitch && this.goodsProductInfo.prdPrice === undefined) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsPriceIsNull'), type: 'warning' })
        this.$refs.prdPriceInput.focus()
        memberCard.checked = false
        return false
      }
      // 填写规格数据校验正确性
      if (this.specInfoSwitch) {
        for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
          let item = this.goodsProductInfo.goodsSpecProducts[i]
          if (item.prdPrice === undefined || item.prdPrice === null || item.prdPrice === '') {
            this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.prdPriceIsNull'), type: 'warning' })
            document.getElementById('prdPrice_' + item.prdDesc).focus()
            memberCard.checked = false
            return false
          }
        }
      }
      return true
    },
    defaultSpecPrdChangeRepeatCheck () {
      if (isStrBlank(this.goodsProductInfo.prdSn)) {
        this.goodsProductInfo.prdSnBak = this.goodsProductInfo.prdSn
        return
      }

      let data = {
        columnCheckFor: 0,
        goodsId: this.goodsProductInfo.goodsId,
        goodsSn: this.goodsProductInfo.prdSn
      }
      isGoodsColumnValueExist(data).then(res => {
        if (res.error === 0) {
          this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsPrdSnRepeat'), type: 'warning' })
          this.goodsProductInfo.prdSn = this.goodsProductInfo.prdSnBak
        } else {
          this.goodsProductInfo.prdSnBak = this.goodsProductInfo.prdSn
        }
      })
    },
    /** 以下是页面数据初始化辅助函数 **/
    /* 初始化会员价复选框 */
    _initMemberCards (goodsData) {
      let count = 0
      let goodsGradePrds = goodsData.goodsGradePrds
      this.memberCards.forEach(card => {
        let has = goodsGradePrds.some(item => item.grade === card.grade)
        card.checked = has
        if (has) {
          count++
        }
      })
      this.unifyCardsPriceShow = count > 1
    },
    /* 初始化商品规格名值 */
    _initGoodsSpecs (goodsData, isUseDefaultPrd) {
      if (isUseDefaultPrd) {
        return
      }
      this.specInfoSwitch = true
      goodsData.goodsSpecs.forEach(spec => {
        let specTemp = { specId: spec.specId, specName: spec.specName, goodsSpecVals: [] }
        spec.goodsSpecVals.forEach(specVal => {
          specTemp.goodsSpecVals.push({ specValId: specVal.specValId, specValName: specVal.specValName })
        })
        this.goodsProductInfo.goodsSpecs.push(specTemp)
      })
    },
    /* 初始化商品规格项（sku） */
    _initGoodsSpecProducts (goodsData, isUseDefaultPrd) {
      if (isUseDefaultPrd) {
        let specPrd = goodsData.goodsSpecProducts[0]
        this.goodsProductInfo.updateGoodsSpecProduct = {
          prdId: specPrd.prdId,
          prdPrice: specPrd.prdPrice,
          prdCostPrice: specPrd.prdCostPrice,
          prdNumber: specPrd.prdNumber,
          prdSn: specPrd.prdSn,
          prdSnBak: specPrd.prdSn,
          prdImg: {
            imgUrl: specPrd.prdImg,
            imgPath: specPrd.prdImgPath
          },
          prdDesc: specPrd.prdDesc,
          prdSpecs: specPrd.prdSpecs
        }
      }
      // 一次性赋值，避免监听器重复执行
      let specProducts = []
      let reg = new RegExp('^[\\S\\s]+?:|;[\\S\\s]+?:', 'g')
      goodsData.goodsSpecProducts.forEach(specPrd => {
        let temp = {
          tempId: this.goodsSpecProductsIndex++,
          prdId: specPrd.prdId,
          prdPrice: specPrd.prdPrice,
          prdCostPrice: specPrd.prdCostPrice,
          prdNumber: specPrd.prdNumber,
          prdSn: specPrd.prdSn,
          prdSnBak: specPrd.prdSn,
          prdImg: {
            imgUrl: specPrd.prdImgUrl,
            imgPath: specPrd.prdImg
          },
          prdDesc: specPrd.prdDesc,
          prdSpecs: specPrd.prdSpecs,
          prdDescTemp: specPrd.prdDesc.replace(reg, ' ').trim(),
          memberCards: []
        }
        this.memberCards.forEach(card => {
          temp.memberCards.push({ card: card, cardPrice: null })
        })
        specProducts.push(temp)
      })
      this.goodsProductInfo.goodsSpecProducts = specProducts
    },
    /* 初始化商品规格会员卡价格 */
    _initMemberCardPrice (goodsData, isUseDefaultPrd) {
      if (isUseDefaultPrd) {
        this.memberCards.forEach(card => {
          let gradeData = this._searchGradeData(goodsData, this.goodsProductInfo.updateGoodsSpecProduct.prdId, card.grade)
          if (gradeData !== null) {
            card.cardPrice = gradeData.gradePrice
          }
        })
      } else {
        this.goodsProductInfo.goodsSpecProducts.forEach(specPrd => {
          specPrd.memberCards.forEach(cardWrap => {
            let gradeData = this._searchGradeData(goodsData, specPrd.prdId, cardWrap.card.grade)
            if (gradeData !== null) {
              cardWrap.cardPrice = gradeData.gradePrice
            }
          })
        })
      }
    },
    /* 根据规格项id和等级查询对应的会员价对象 */
    _searchGradeData (goodsData, prdId, grade) {
      let goodsGradePrds = goodsData.goodsGradePrds
      let retData = goodsGradePrds.filter(gradePrd => gradePrd.prdId === prdId && gradePrd.grade === grade)
      if (retData.length > 0) {
        return retData[0]
      } else {
        return null
      }
    },
    /* 初始化商品库存，价格，成本等数据 */
    _initOtherData (goodsData, isUseDefaultPrd) {
      this.goodsProductInfo.marketPrice = goodsData.marketPrice
      this.goodsProductInfo.limitBuyNum = goodsData.limitBuyNum
      this.goodsProductInfo.limitMaxNum = goodsData.limitMaxNum
      this.goodsProductInfo.baseSale = goodsData.baseSale
      if (isUseDefaultPrd) {
        this.goodsProductInfo.prdNumber = goodsData.goodsNumber
        this.goodsProductInfo.prdPrice = goodsData.shopPrice
        this.goodsProductInfo.prdCost = goodsData.costPrice
        this.goodsProductInfo.prdSn = goodsData.goodsSpecProducts[0].prdSn
      }
    },
    /* 页面数据初始化链，避免页面数据未加载完成的时候就初始化待修改商品数据，返回一个Promise */
    initPageDataLink () {
      return this._memberCardsInit()
    },
    /* 初始化待修改商品数据 */
    initDataForUpdate (goodsData) {
      // 打开basicForm，否则display = none时会因数据尚未渲染报错
      this.arrorFlag = false
      return this.initPageDataLink().then(() => {
        let isUseDefaultPrd = false

        // 判断是否使用默认的规格项
        let prdDesc = goodsData.goodsSpecProducts[0].prdDesc
        if (goodsData.goodsSpecProducts.length === 1 && isStrBlank(prdDesc)) {
          isUseDefaultPrd = true
        }
        this._initOtherData(goodsData, isUseDefaultPrd)
        // 初始化商品规格名值
        this._initGoodsSpecs(goodsData, isUseDefaultPrd)
        // 初始化商品规格项（sku）
        this._initGoodsSpecProducts(goodsData, isUseDefaultPrd)

        // 初始化会员卡信息
        this._initMemberCards(goodsData)
        // 会员价设置是否显示设置
        this.memberCardPrdShow = goodsData.goodsGradePrds.length > 0
        // 初始化商品规格会员卡价格
        this._initMemberCardPrice(goodsData, isUseDefaultPrd)
        // 开启监听
        this._watch()
      })
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {
      this.goodsProductInfo.goodsSpecs.forEach(item => {
        item.specId = null
        item.goodsId = null
        item.goodsSpecVals.forEach(innerItem => {
          innerItem.specId = null
          innerItem.specValId = null
          innerItem.goodsId = null
        })
      })
      this.goodsProductInfo.updateGoodsSpecProduct = null
      this.goodsProductInfo.goodsSpecProducts.forEach(item => {
        item.prdId = null
        item.prdSnBak = null
      })
    },
    /* 新增商品数据初始化 */
    initDataForInsert () {
      this._memberCardsInit()
      // 开启监听
      this._watch()
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      // 自定义情况验证
      if (this.specInfoSwitch) {
        // 验证商品规格信息
        for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
          let item = this.goodsProductInfo.goodsSpecProducts[i]
          if (isNumberBlank(item.prdPrice) || item.prdPrice < 0) {
            this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsSpec') + item.prdDescTemp + this.$t('goodsAddEditInfo.warningInfo.priceIsWrong'), type: 'warning' })
            document.getElementById('prdPrice_' + item.prdDesc).focus()
            return false
          }

          if (isNumberBlank(item.prdNumber) || item.prdNumber < 0) {
            this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.goodsSpec') + item.prdDescTemp + this.$t('goodsAddEditInfo.warningInfo.goodsNumIsWrong'), type: 'warning' })
            document.getElementById('prdNumber_' + item.prdDesc).focus()
            return false
          }
        }

        // 验证会员价格
        for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
          let specProduct = this.goodsProductInfo.goodsSpecProducts[i]
          let memberCards = specProduct.memberCards
          for (let j = 0; j < memberCards.length; j++) {
            let cardWrap = memberCards[j]
            if (!cardWrap.card.checked) {
              continue
            }
            if (isNumberBlank(cardWrap.cardPrice) || cardWrap < 0) {
              this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.gradPrdPriceIsNull'), type: 'warning' })
              document.getElementById(specProduct.prdDesc + cardWrap.card.cardName).focus()
              return false
            }

            if (cardWrap.cardPrice > specProduct.prdPrice) {
              this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.gradePrdPriceHigherThanGoodsPrice'), type: 'warning' })
              document.getElementById(specProduct.prdDesc + cardWrap.card.cardName).focus()
              return false
            }
          }
        }
      } else {
        // 商品库存检查
        if (isNumberBlank(this.goodsProductInfo.prdNumber) || this.goodsProductInfo.prdNumber < 0) {
          this.$message.warning({ message: '商品库存填写错误', type: 'warning' })
          this.$refs.prdNumberInput.focus()
          return false
        }
        // 商品价格验证
        if (isNumberBlank(this.goodsProductInfo.prdPrice) || this.goodsProductInfo.prdPrice < 0) {
          this.$message.warning({ message: '商品价格填写错误', type: 'warning' })
          this.$refs.prdPriceInput.focus()
          return false
        }
        // 成本价格验证
        if (isNumberBlank(this.goodsProductInfo.prdCost) || this.goodsProductInfo.prdCost < 0) {
          this.$message.warning({ message: '成本价格填写错误', type: 'warning' })
          this.$refs.prdPriceInput.focus()
          return false
        }
        // 会员价格验证
        for (let i = 0; i < this.memberCards.length; i++) {
          let item = this.memberCards[i]
          if (!item.checked) {
            continue
          }
          if (isNumberBlank(item.cardPrice) || item.cardPrice < 0) {
            this.$message.warning({ message: '会员价格不可为空', type: 'warning' })
            document.getElementById(item.cardName).focus()
            return false
          }
          if (item.cardPrice > this.goodsProductInfo.prdPrice) {
            this.$message.warning({ message: '会员价格不可高于商品价格', type: 'warning' })
            document.getElementById(item.cardName).focus()
            return false
          }
        }
      }

      // 验证限购数量
      if ((isNumberBlank(this.goodsProductInfo.limitBuyNum) || this.goodsProductInfo.limitBuyNum < 0) &&
        (isNumberBlank(this.goodsProductInfo.limitMaxNum) || this.goodsProductInfo.limitMaxNum < 0)) {
        if (this.goodsProductInfo.limitBuyNum > this.goodsProductInfo.limitMaxNum) {
          this.$message.warning({ message: '最小限购数量不可大于最大限购数量', type: 'warning' })
          this.$refs.limitBuyNumInput.focus()
          return false
        }
      }
      return true
    },
    /* 获取传给后台的表单数据 */
    getFormData () {
      let retData = {}
      // 自定义规格和默认规格公共数据
      retData.marketPrice = this.goodsProductInfo.marketPrice
      retData.baseSale = this.goodsProductInfo.baseSale
      retData.limitBuyNum = this.goodsProductInfo.limitBuyNum
      retData.limitMaxNum = this.goodsProductInfo.limitMaxNum
      retData.specInfoSwitch = this.specInfoSwitch

      // 剔除无效规格
      this.goodsProductInfo.goodsSpecs = this.goodsProductInfo.goodsSpecs.filter(goodsSpec => {
        // 规格名称为空
        if (isStrBlank(goodsSpec.specName)) {
          return false
        }
        // 没有有效规格值
        if (!this._isSpecInfoHasSpecVal(goodsSpec)) {
          return false
        }
        // 剔除无效规格值
        let goodsSpecVals = goodsSpec.goodsSpecVals.filter(specVal => !isStrBlank(specVal.specValName))
        // 重新设置值
        goodsSpec.goodsSpecVals = goodsSpecVals
        return true
      })

      retData.goodsSpecs = this.goodsProductInfo.goodsSpecs

      // 默认规格数据
      if (!this.specInfoSwitch) {
        retData.goodsGradePrds = []
        // 收集有效会员价
        this.memberCards.forEach(card => {
          if (!card.checked) {
            return
          }
          retData.goodsGradePrds.push({
            prdDesc: null,
            gradePrice: card.cardPrice,
            grade: card.grade
          })
        })

        // 填充默认规格
        retData.goodsSpecProducts = [{
          prdId: this.goodsProductInfo.updateGoodsSpecProduct !== null ? this.goodsProductInfo.updateGoodsSpecProduct.prdId : null,
          tempId: this.goodsSpecProductsIndex++,
          prdDesc: '',
          prdSpecs: '',
          prdSn: this.goodsProductInfo.prdSn,
          prdNumber: this.goodsProductInfo.prdNumber,
          prdPrice: this.goodsProductInfo.prdPrice,
          prdCostPrice: this.goodsProductInfo.prdCost,
          prdMarketPrice: this.goodsProductInfo.marketPrice
        }]
      } else {
        // 自定义规格数据
        retData.goodsSpecProducts = []
        retData.goodsGradePrds = []

        this.goodsProductInfo.goodsSpecProducts.forEach(specProduct => {
          // 插入规格属性
          retData.goodsSpecProducts.push({
            prdId: specProduct.prdId,
            tempId: specProduct.tempId,
            prdDesc: specProduct.prdDesc,
            prdSpecs: specProduct.prdSpecs,
            prdDescTemp: specProduct.prdDescTemp,
            prdPrice: specProduct.prdPrice,
            prdCostPrice: specProduct.prdCostPrice,
            prdNumber: specProduct.prdNumber,
            prdMarketPrice: this.goodsProductInfo.marketPrice,
            prdSn: specProduct.prdSn,
            prdImg: specProduct.prdImg.imgPath
          })

          // 填充对应规格的会员价格
          specProduct.memberCards.forEach(cardWrap => {
            if (!cardWrap.card.checked) {
              return
            }
            retData.goodsGradePrds.push({
              prdDesc: specProduct.prdDesc,
              gradePrice: cardWrap.cardPrice,
              grade: cardWrap.card.grade
            })
          })
        })
      }
      return retData
    },
    /* 展开更多配置 */
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    }
  },
  mounted () {
    // 国际化
    this.langDefault()
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
.specInfoWrap {
  border: 1px solid #ccc;
  color: #333;
  padding: 10px;
}

.specInfoWrap::after {
  display: block;
  content: "";
  clear: both;
}

.specInfoItem {
  display: flex;
  align-items: center;
}

.speInfoItemK {
  background: #f8f8f8;
}

.specInfoItemTitle {
  width: 80px;
}

.specInfoItemContent {
  width: calc(100% - 100px);
}

.specInfoItemInputWrap {
  width: 120px;
  height: 30px;
  border: 1px solid #cccccc;
  float: left;
  margin: 2px 2px;
  position: relative;
}

.specInfoItemInputWrap input {
  width: 100%;
  height: 100%;
  outline: none;
  padding: 0 0;
  border: none;
  float: left;
}

.specInfoItemInputWrap .deleteIcon {
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

/* 以下临时使用，可删除 */
table {
  border-collapse: collapse;
  margin: 0 auto;
  text-align: center;
  width: 100%;
}

table td,
table th {
  border: 1px solid #cad9ea;
  color: #666;
  height: 30px;
  vertical-align: middle !important;
  text-align: center;
}

table thead th {
  background-color: #cce8eb;
  width: 100px;
}

table tr:nth-child(odd) {
  background: #fff;
}

table tr:nth-child(even) {
  background: #f5fafa;
}

.batchSpan{
  display: inline-block;
  margin:0px 2px;
}
.linkSpan{
  color: #555;
  cursor: pointer;
}
.linkSpan:hover {
  color: #66b1ff;
}
</style>
