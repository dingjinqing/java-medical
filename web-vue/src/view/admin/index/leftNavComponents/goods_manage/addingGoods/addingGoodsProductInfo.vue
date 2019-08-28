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
      <el-form ref="stockAndPriceInfoForm" :model="goodsProductInfo" :rules="stockAndPriceRules" label-width="120px">
        <!--商品规格按钮-->
        <el-form-item label="商品规格：" v-if="!specInfoSwitch">
          <el-button size="small" @click="addSpecClick">添加规格</el-button>
        </el-form-item>
        <el-form-item label="商品规格：" v-if="specInfoSwitch">
          <div class="specInfoWrap">
            <template v-for="(specInfoModel,kIndex) in goodsProductInfo.goodsSpecs">
              <div class="specInfoItem speInfoItemK" :key="'k'+kIndex">
                <div class="specInfoItemTitle">规格名：</div>
                <div class="specInfoItemContent">
                  <div class="specInfoItemInputWrap">
                    <input type="text" :value="specInfoModel.specName"
                           @change="specInfoChange(specInfoModel,kIndex,$event.target.value)"/>
                    <div @click="deleteSpecInfo(specInfoModel,kIndex)" class="deleteIcon">×</div>
                  </div>
                </div>
              </div>
              <div class="specInfoItem speInfoItemV" :key="'v'+kIndex">
                <div class="specInfoItemTitle">规格值：</div>
                <div class="specInfoItemContent">
                  <div class="specInfoItemInputWrap" v-for="(specInfoVModel,vIndex) in specInfoModel.goodsSpecVals"
                       :key="vIndex">
                    <input type="text" :value="specInfoVModel.specValName"
                           @change="specValChange(specInfoModel,vIndex,$event.target.value)"/>
                    <div @click="deleteSpecVal(specInfoModel,vIndex)" class="deleteIcon">×</div>
                  </div>
                  <el-link size="small" :underline="false" @click="addSpecValClick(specInfoModel)"
                           style="margin-left: 5px;">添加规格值
                  </el-link>
                </div>
              </div>
            </template>
            <el-button style="float: right;" size="small" @click="addSpecInfoClick">添加规格选项</el-button>
          </div>
        </el-form-item>
        <el-form-item label="规格价格：" v-if="specInfoSwitch">
          <div class="specInfoWrap">
            <table>
              <tr>
                <th></th>
                <th>价格(元)</th>
                <th>成本价格(元)</th>
                <th>库存</th>
                <th>规格编码</th>
                <th>规格图片</th>
              </tr>
              <tr v-for="(item,index) in goodsProductInfo.goodsSpecProducts" :key="index">
                <td>{{item.prdDescTemp}}</td>
                <td><input :id="'prdPrice_'+item.prdDesc" v-model.number="item.prdPrice"/></td>
                <td><input v-model.number="item.prdCostPrice"/></td>
                <td><input :id="'prdNumber_'+item.prdDesc" v-model.number="item.prdNumber"/></td>
                <td><input v-model.number="item.prdSn"/></td>
                <td><img src="" alt="">src</td>
              </tr>
            </table>
            <div style="text-align: center;">
              <span>批量设置：</span>
              <el-link size="small" :underline="false" @click="unifyPrdSpecAttr('prdPrice')" style="margin-right: 5px;">价格</el-link>
              <el-link size="small" :underline="false" @click="unifyPrdSpecAttr('prdCostPrice')" style="margin-right: 5px;">成本价格</el-link>
              <el-link size="small" :underline="false" @click="unifyPrdSpecAttr('prdNumber')" style="margin-right: 5px;">库存</el-link>
              <el-link size="small" :underline="false" @click="unifyPrdSpecAttr('prdImg')" style="margin-right: 5px;">规格图片</el-link>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="商品库存：" prop="prdNumber">
          <el-input-number ref="prdNumberInput" v-model="goodsProductInfo.prdNumber" step-strictly size="small" controls-position="right" :min="0" :disabled="specInfoSwitch" style="width:170px;"/>
          <span class="inputTip">设置了规格库存商品库存将失效，不在前端展示</span>
        </el-form-item>
        <el-form-item label="商品价格：" prop="prdPrice">
          <el-input-number ref="prdPriceInput" v-model="goodsProductInfo.prdPrice"  size="small" controls-position="right" :min="0" :disabled="specInfoSwitch" style="width:170px;"/>
          <span class="inputTip">设置了规格价格商品价格将失效，不在前端展示</span>
        </el-form-item>
        <el-form-item label="市场价格：" prop="marketPrice">
          <el-input-number ref="marketPriceInput" v-model="goodsProductInfo.marketPrice" size="small" controls-position="right" :min="0" style="width:170px;"/>
        </el-form-item>
        <el-form-item label="会员价：">
          <el-checkbox v-for="(item,index) in memberCards" v-model="item.checked" :key="index" @change="memberCardCheckedChange(item,$event)">{{item.cardName}}</el-checkbox>
          <br/>
          <span class="inputTip" style="margin-left: 0px;">会员价仅针对等级会员卡设定，非等级会员卡不可设置会员价。若等级会员卡也包含会员折扣，则会员价和会员折扣可同时享受，优先计算会员价</span>
        </el-form-item>
        <el-form-item label="会员价设置：" v-if="memberCardPrdShow">
            <div class="specInfoWrap">
              <table v-if="specInfoSwitch">
                <tr>
                  <th>未计算</th>
                  <th>规格价格(元)</th>
                  <th v-for="item in memberCards" :key="item.id" v-if="item.checked">{{item.cardName}}</th>
                  <th v-if="unifyCardsPriceShow"></th>
                </tr>
                <tr v-for="(item,index) in goodsProductInfo.goodsSpecProducts" :key="index">
                  <td>{{item.prdDescTemp}}</td>
                  <td>{{item.prdPrice}}</td>
                  <td v-for="(cardWrap,cardWrapIndex) in item.memberCards" v-if="cardWrap.card.checked" :key="cardWrapIndex">
                    <input :id="item.prdDesc+cardWrap.card.cardName" type="text" v-model.number="cardWrap.cardPrice" @change="memberCardPriceChange(item.prdPrice,cardWrap.cardPrice,item.prdDesc+cardWrap.card.cardName)"/>
                  </td>
                  <td v-if="unifyCardsPriceShow">
                    <el-link size="small" :underline="false" @click="unifyMemberCardsPrice(item)">统一会员价</el-link>
                  </td>
                </tr>
              </table>
              <table v-else>
                <tr>
                  <th>商品价格(元)</th>
                  <th v-for="item in memberCards" :key="item.id" v-if="item.checked">{{item.cardName}}</th>
                  <th v-if="unifyCardsPriceShow"></th>
                </tr>
                <tr>
                  <td>{{goodsProductInfo.prdPrice}}</td>
                  <td v-for="item in memberCards" v-if="item.checked" :key="item.id">
                    <input :id="item.cardName" type="text"  v-model.number="item.cardPrice" @change="memberCardPriceChange(goodsProductInfo.prdPrice,item.cardPrice,item.cardName)"/>
                  </td>
                  <td v-if="unifyCardsPriceShow">
                    <el-link size="small" :underline="false" @click="unifyMemberCardsPrice()">统一会员价</el-link>
                  </td>
                </tr>
              </table>
            </div>
        </el-form-item>
      </el-form>
      <!-- 展开更多配置 -->
      <el-collapse accordion>
        <el-collapse-item title="展开/收起更多配置" name="1">
          <el-form :model="goodsProductInfo" :rules="stockAndPriceRules" ref="stockAndPriceInfoOtherForm" label-width="120px">

            <el-form-item label="最小限购数量：" prop="limitBuyNum">
              <el-input-number ref="limitBuyNumInput" v-model="goodsProductInfo.limitBuyNum" step-strictly size="small" controls-position="right" :min="0" style="width:170px;"/>
              <span class="inputTip">0或不填表示不限制购买数量</span>
            </el-form-item>
            <el-form-item label="最大限购数量：" prop="limitMaxNum">
              <el-input-number ref="limitMaxNumInput" v-model="goodsProductInfo.limitMaxNum" step-strictly size="small" controls-position="right" :min="0" style="width:170px;"/>
              <span class="inputTip">0或不填表示不限制购买数量</span>
            </el-form-item>
            <el-form-item label="成本价格：" prop="prdCost">
              <el-input-number ref="prdCostInput" v-model="goodsProductInfo.prdCost" step-strictly size="small" controls-position="right" :min="0" :disabled="specInfoSwitch" style="width:170px;"/>
              <span class="inputTip">0或不填表示不限制购买数量</span>
            </el-form-item>
            <el-form-item label="初始销量：" prop="addSaleNum">
              <el-input-number v-model="goodsProductInfo.addSaleNum" step-strictly size="small" controls-position="right" :min="0" style="width:170px;"/>
              <span class="inputTip">设置后，您的用户看到的销量=初始销量+下单量，初始销量不计入统计。</span>
            </el-form-item>
            <el-form-item label="商品规格编码：" v-if="!specInfoSwitch">
              <el-input v-model="goodsProductInfo.prdSn" size="small"  style="width:170px;"/>
            </el-form-item>

          </el-form>
        </el-collapse-item>
      </el-collapse>
      <el-button @click="validateFormData">验证</el-button>
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
// TODO: 1.格笛卡尔积中规格图片样式未实现
// TODO: 2.格笛卡尔积中规格input框样式未实现
// TODO: 3.会员价格table表格样式未实现

// 接口函数引入
import {getLevelCardList} from '@/api/admin/goodsManage/addingGoods/addingGoods'
// 组件导入
import basicInfo from './basicInfo'

export default {
  components: {basicInfo},
  data () {
    return {
      goodsProductInfo: {
        // 库存、价格信息
        marketPrice: null,
        goodsSpecProducts: [],
        goodsSpecs: [],
        goodsGradePrds: [],
        limitBuyNum: null,
        limitMaxNum: null,
        addSaleNum: null,
        deliverTemplateId: null,
        goodsWeight: null,
        deliverPlace: null,
        isCardExclusive: null,
        memberCardIds: null,
        isOnSale: null,
        saleTime: null,
        /* 以下为辅助数据，不传到后台 */
        prdNumber: null,
        prdPrice: null,
        prdCost: null,
        prdSn: null
      },
      stockAndPriceRules: {
        prdNumber: [
          {required: true, message: '请输入商品库存', trigger: 'change'}
        ],
        prdPrice: [
          {required: true, message: '请输入商品价格', trigger: 'change'}
        ],
        prdCost: [
          {required: true, message: '请输入商品价格', trigger: 'change'}
        ]
      },
      /* 自定义商品规格 */
      specInfoSwitch: false,
      /* 会员价辅助数据 */
      memberCards: [],
      memberCardPrdShow: false,
      unifyCardsPriceShow: false
    }
  },
  watch: {
    'goodsProductInfo.goodsSpecProducts': function () {
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
    }
  },
  methods: {
    /** 商品规格交互函数结束**/
    addSpecClick () {
      this.specInfoSwitch = !this.specInfoSwitch
      this.goodsProductInfo.goodsSpecs.push({specName: null, goodsSpecVals: [{specValName: null}]})

      this._recalculateSpec()
    },
    /* 添加规格选项按钮 */
    addSpecInfoClick () {
      let specInfoItem = {specName: null, goodsSpecVals: [{specValName: null}]}
      this.goodsProductInfo.goodsSpecs.push(specInfoItem)
    },
    /* 添加规格值按钮 */
    addSpecValClick (specInfoModel) {
      specInfoModel.goodsSpecVals.push({specValName: null})
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
    /* 规格项名称改变 */
    specInfoChange (specInfoModel, kIndex, newVal) {
      newVal = newVal === '' || newVal === null ? null : newVal

      let oldSpecName = specInfoModel.specName
      specInfoModel.specName = newVal

      // 如果规格名称被修改为null则认为是删除了该规格
      if (newVal === null) {
        // 删除了规格但是规格下没有有效的规格值
        if (!this._isSpecInfoHasSpecVal(specInfoModel)) {
          // TODO:仅仅触发specInfoLabel值遍历计算
        } else {
          // 删除了规格但是规格下有有效的规格值，则触发重计算，视为删除了该规格
          this._recalculateSpec()
        }
      } else {
        // 名字由null修改为非nulll,但是存在可用的规格值，则认为是新增了规格，则触发重新计算
        if (this._isBlank(oldSpecName) && this._isSpecInfoHasSpecVal(specInfoModel)) {
          this._recalculateSpec()
        } else {
          // 修改了规格名字而且有有效的规格值，则进行遍历修改specInfoLabel和specDesc
          if (this._isSpecInfoHasSpecVal(specInfoModel)) {
            this._calculateChangeSpecInfo(newVal, oldSpecName)
          }
        }
      }
    },
    /* 规格值名称改变 */
    specValChange (specInfoModel, vIndex, newVal) {
      newVal = this._isBlank(newVal) ? null : newVal

      let tempSpecVal = {
        specValName: specInfoModel.goodsSpecVals[vIndex].specValName
      }
      specInfoModel.goodsSpecVals[vIndex].specValName = newVal

      // 如果规格名为空则只更新一下goodsSpec即可
      if (this._isBlank(specInfoModel.specName)) {
        return null
      }

      if (newVal === null) {
        // 看做删除操作,遍历删除对应项
        this._calculateDeleteSpecVal(specInfoModel, tempSpecVal)
      } else {
        // 旧值为null,而新值不是null则视为新增项
        if (this._isBlank(tempSpecVal.specValName)) {
          // 新增项

          this._calculateAddSpecVal(specInfoModel, specInfoModel.goodsSpecVals[vIndex])
        } else {
          // 修改规格值名字，遍历修改对应项
          this._calculateChangeSpecVal(newVal, tempSpecVal.specValName)
        }
      }
    },
    /* 统一规格属性函数 */
    unifyPrdSpecAttr (attrName) {
      if (this.goodsProductInfo.goodsSpecProducts.length === 0) {
        return
      }
      let val = this.goodsProductInfo.goodsSpecProducts[0][attrName]
      this.goodsProductInfo.goodsSpecProducts.forEach(item => {
        item[attrName] = val
      })
      console.log(this.goodsProductInfo.goodsSpecProducts)
    },
    /* 判断规格是否存在有效的规格值，只有在所有规格值中有名字非空的情况下返回true,否则返回false */
    _isSpecInfoHasSpecVal (specInfoModel) {
      let hasValue = false
      specInfoModel.goodsSpecVals.forEach(item => {
        if (item.specValName !== '' && item.specValName !== null) {
          hasValue = true
        }
      })
      return hasValue
    },
    /* 判断传入的参数是否为空，当参数为null,undefined,''时都视为空 */
    _isBlank (val) {
      if (val === null || val === undefined || val === '') {
        return true
      } else {
        return false
      }
    },
    /* 规格名称改变，当变为null和从null变为非空都会触发重计算，否则就是进行遍历修改 */
    _calculateChangeSpecInfo (newVal, oldVal) {
      let prdDescReg = new RegExp(`^${oldVal}:|!!${oldVal}:`)
      for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
        let item = this.goodsProductInfo.goodsSpecProducts[i]
        // replacement 匹配到的字符串，用返回值替换该字符串
        item.prdDesc = item.prdDesc.replace(prdDescReg, replacement => replacement.replace(oldVal, newVal))
      }
    },
    /* 当添加新的规格值的时候或者当规格值由空变为有值的时候,需要判断是否是新增规格的情况 */
    _calculateAddSpecVal (specInfoModel, specVal) {
      let okCount = 0
      specInfoModel.goodsSpecVals.forEach(item => {
        if (!this._isBlank(item.specValName)) {
          okCount++
        }
      })
      if (okCount === 1) {
        // 表明当前规格只有一个规格值，而且是新增加的，则这时候要触发重新计算，视为新添加规格
        this._recalculateSpec()
        return
      }

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

      let goodsSpecProducts = [{
        prdPrice: 0,
        prdCostPrice: 0,
        prdNumber: 0,
        prdSn: null,
        prdImg: null,
        prdDesc: '',
        prdDescTemp: ''
      }]

      goodsSpecProducts = this._calculateCartesian(0, goodsSpecs, goodsSpecProducts)

      for (let item of goodsSpecProducts) {
        this.goodsProductInfo.goodsSpecProducts.push(item)
      }
    },
    _calculateChangeSpecVal (newVal, oldVal) {
      let prdDescReg = new RegExp(`:${oldVal}$|:${oldVal}!!`)
      let prdDescTemp = new RegExp(`^${oldVal}|${oldVal}$|\\s${oldVal}\\s`)

      for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
        let item = this.goodsProductInfo.goodsSpecProducts[i]
        // replacement 匹配到的字符串，用返回值替换该字符串
        item.prdDesc = item.prdDesc.replace(prdDescReg, replacement => replacement.replace(oldVal, newVal))

        item.prdDescTemp = item.prdDescTemp.replace(prdDescTemp, replacement => replacement.replace(oldVal, newVal))
      }
    },
    /* 当删除某规格值的时候直接遍历已有笛卡尔结果，并删除包含当前规格值的所有项 */
    _calculateDeleteSpecVal (specInfoModel, specVal) {
      if (this._isSpecInfoHasSpecVal(specInfoModel)) {
        // 通过正则表达式判断需要剔除的项
        let regStr = `:${specVal.specValName}$|:${specVal.specValName}!!`
        let regExp = new RegExp(regStr)
        let tempArr = this.goodsProductInfo.goodsSpecProducts.filter(item => !regExp.test(item.prdDesc))
        this.goodsProductInfo.goodsSpecProducts = tempArr
      } else {
        this._recalculateSpec()
      }
    },
    /* 计算规格笛卡尔积 */
    _recalculateSpec () {
      // 商品规格是否有效
      let specOk = false
      this.goodsProductInfo.goodsSpecs.forEach(specInfoModel => {
        if (this._isSpecInfoHasSpecVal(specInfoModel)) {
          specOk = true
        }
      })

      if (!specOk) {
        // 商品规格不存在有效数据则直接返回
        this.goodsProductInfo.goodsSpecProducts = []
        return
      }

      // 笛卡尔计算初始化操作
      let goodsSpecProducts = [{
        prdPrice: 0,
        prdCostPrice: 0,
        prdNumber: 0,
        prdSn: null,
        prdImg: null,
        prdDesc: '',
        prdDescTemp: ''
      }]

      this.goodsProductInfo.goodsSpecProducts = this._calculateCartesian(0, this.goodsProductInfo.goodsSpecs, goodsSpecProducts)
    },
    /* 递归计算笛卡尔积 */
    _calculateCartesian (curIndex, goodsSpecs, goodsSpecProducts) {
      // 规格名和值之间的分隔符
      let PRD_VAL_DELIMITER = ':'
      // 不同规格之间的分隔符
      let PRD_SPEC_DELIMITER = '!!'
      // 递归出口，已遍历完所有规格
      if (curIndex >= goodsSpecs.length) {
        return goodsSpecProducts
      }
      // 当前将要进行计算的规格
      let item = goodsSpecs[curIndex]

      // 规格名称为null或者规格没有有效的规格值则视为无效项，则直接跳过
      if (this._isBlank(item.specName) || !this._isSpecInfoHasSpecVal(item)) {
        return this._calculateCartesian(curIndex + 1, goodsSpecs, goodsSpecProducts)
      }
      // 用来缓存通过当前规格计算出来的笛卡尔积
      let tempArr = []

      // 遍历当前规格的所有规格值，依次进行遍历计算
      for (let i = 0; i < item.goodsSpecVals.length; i++) {
        // 当前规格值
        let specVal = item.goodsSpecVals[i]
        // 判断是否规格值的名字是否为空
        if (this._isBlank(specVal.specValName)) {
          continue
        }

        // 遍历已有笛卡尔积，并和当前规格值依次拼接求值，模拟乘法操作
        for (let j = 0; j < goodsSpecProducts.length; j++) {
          let goodsSpec = goodsSpecProducts[j]

          let tempSpec = {
            prdPrice: 0,
            prdCostPrice: 0,
            prdNumber: 0,
            prdSn: null,
            prdImg: null,
            prdDesc: '',
            prdDescTemp: ''
          }
          tempSpec.prdDesc = item.specName + PRD_VAL_DELIMITER + specVal.specValName
          tempSpec.prdDescTemp = specVal.specValName

          // 以下是按预定义规则拼接字符串
          if (!this._isBlank(goodsSpec.prdDesc)) {
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
    /** 会员卡部分交互函数 **/
    /* 等级会员卡数据初始化 */
    memberCardsInit () {
      getLevelCardList().then(rep => {
        let {content: {dataList}} = rep
        for (let item of dataList) {
          this.memberCards.push({id: item.id, cardName: item.cardName, checked: false, cardPrice: null})
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
        for (let j = 0; j < this.goodsProductInfo.goodsSpecProducts.length; j++) {
          let a = this.goodsProductInfo.goodsSpecProducts[j]
          let mcs = a.memberCards
          for (let i = 0; i < mcs.length; i++) {
            let cardWrap = mcs[i]
            if (cardWrap.card.checked) {
              cardWrap.cardPrice = 250
              console.log(cardWrap.cardPrice)
            }
          }
        }
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
    memberCardPriceChange (prdPrice, cardPrice, inputId) {
      if (cardPrice === undefined || cardPrice === '') {
        this.$message('会员价格不可以为空')
        document.getElementById(inputId).focus()
        return
      }

      if (cardPrice > prdPrice) {
        this.$message('会员价格不可高于原价格')
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
        this.$message('请填写商品价格')
        this.$refs.prdPriceInput.focus()
        memberCard.checked = false
        return false
      }
      // 填写规格数据校验正确性
      if (this.specInfoSwitch) {
        for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
          let item = this.goodsProductInfo.goodsSpecProducts[i]
          if (item.prdPrice === undefined || item.prdPrice === null || item.prdPrice === '') {
            this.$message('请填写规格价格')
            document.getElementById('prdPrice_' + item.prdDesc).focus()
            memberCard.checked = false
            return false
          }
        }
      }
      return true
    },
    /** 此函数由父组件主动调用 **/
    /* 验证数据是否全部合法 */
    validateFormData () {
      // 自定义情况验证
      if (this.specInfoSwitch) {
        // 验证商品规格信息
        for (let i = 0; i < this.goodsProductInfo.goodsSpecProducts.length; i++) {
          let item = this.goodsProductInfo.goodsSpecProducts[i]
          if (!this._numberValidate(item.prdPrice)) {
            this.$message('规格:' + item.prdDescTemp + ' 价格填写错误')
            document.getElementById('prdPrice_' + item.prdDesc).focus()
            return false
          }

          if (!this._numberValidate(item.prdNumber)) {
            this.$message('规格：' + item.prdDescTemp + '库存写错误')
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
            if (!this._numberValidate(cardWrap.cardPrice)) {
              this.$message('会员价格不可为空')
              document.getElementById(specProduct.prdDesc + cardWrap.card.cardName).focus()
              return false
            }

            if (cardWrap.cardPrice > specProduct.prdPrice) {
              this.$message('会员价格不可高于商品价格')
              document.getElementById(specProduct.prdDesc + cardWrap.card.cardName).focus()
              return false
            }
          }
        }
      } else {
        // 商品库存检查
        if (!this._numberValidate(this.goodsProductInfo.prdNumber)) {
          this.$message('商品库存填写错误')
          this.$refs.prdNumberInput.focus()
          return false
        }
        // 商品价格验证
        if (!this._numberValidate(this.goodsProductInfo.prdPrice)) {
          this.$message('商品价格填写错误')
          this.$refs.prdPriceInput.focus()
          return false
        }
        // 成本价格验证
        if (!this._numberValidate(this.goodsProductInfo.prdCost)) {
          this.$message('成本价格填写错误')
          this.$refs.prdPriceInput.focus()
          return false
        }
        // 会员价格验证
        for (let i = 0; i < this.memberCards.length; i++) {
          let item = this.memberCards[i]
          if (!item.checked) {
            continue
          }
          if (!this._numberValidate(item.cardPrice)) {
            this.$message('会员价格不可为空')
            document.getElementById(item.cardName).focus()
            return false
          }
          if (item.cardPrice > this.goodsProductInfo.prdPrice) {
            this.$message('会员价格不可高于商品价格')
            document.getElementById(item.cardName).focus()
            return false
          }
        }
      }

      // 验证限购数量
      if (this._numberValidate(this.goodsProductInfo.limitBuyNum) && this._numberValidate(this.goodsProductInfo.limitMaxNum)) {
        if (this.goodsProductInfo.limitBuyNum > this.goodsProductInfo.limitMaxNum) {
          this.$message('最小限购数量不可大于最大限购数量')
          this.$refs.limitBuyNumInput.focus()
          return false
        }
      }
      return true
    },
    _numberValidate (val) {
      if (val === undefined || val === null || val === '' || val < 0) {
        return false
      }
      return true
    }
  },
  mounted () {
    this.memberCardsInit()
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
    content: '';
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
    border: 1px solid #CCCCCC;
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
  }

  /* 以下临时使用，可删除 */
  table {
    border-collapse: collapse;
    margin: 0 auto;
    text-align: center;
    width: 100%;
  }

  table td, table th {
    border: 1px solid #cad9ea;
    color: #666;
    height: 30px;
  }

  table thead th {
    background-color: #CCE8EB;
    width: 100px;
  }

  table tr:nth-child(odd) {
    background: #fff;
  }

  table tr:nth-child(even) {
    background: #F5FAFA;
  }
</style>
