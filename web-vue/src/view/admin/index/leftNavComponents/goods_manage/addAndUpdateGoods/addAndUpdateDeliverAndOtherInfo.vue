<template>
  <div>
    <div class="title">{{$t("goodsAddEditInfo.deliverAndOtherInfo.title")}}</div>
    <el-form
      ref="deliveryInfoForm"
      :model="goodsProductInfo"
      :label-width="labelWidth+'px'"
    >
      <el-form-item
        :label="$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplate')"
        prop="deliverTemplateId"
      >
        <el-select
          v-model="goodsProductInfo.deliverTemplateId"
          size="small"
          @change="deliverTemplateChange"
          style="width:170px;"
        >
          <el-option
            v-for="(item,index) in deliverTemplateData"
            :value="item.deliverTemplateId"
            :label="item.templateName"
            :key="index"
          />
        </el-select>
        <el-link
          type="primary"
          :underline="false"
          @click.native="deliverTemplateSelectRefresh"
          href="#"
          style="margin:0 5px;"
        >{{$t("goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateRefresh")}}
        </el-link>
        |
        <el-link
          type="primary"
          :underline="false"
          @click.native="$router.push({name: 'deliverTemplateAdd'})"
          href="#"
          style="margin:0 5px;"
        >{{$t("goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateNew")}}</el-link>
        |
        <el-link
          type="primary"
          :underline="false"
          @click.native="$router.push({name: 'deliverTemplateList'})"
          href="#"
          style="margin:0 5px;"
        >{{$t("goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateManage")}}</el-link>
        <div class="deliverTemplateContentWrap">
          <div class="deliverTemplateContentTitle">
            <div>
              {{deliverTemplateCurrentData.deliverTemplateTitleDesc}}
            </div>
            <el-link
              style="position:absolute;right: 10px;top: 0px;"
              type="primary"
              :underline="false"
              href="#"
            >{{$t("goodsAddEditInfo.linkDetail")}}</el-link>
          </div>
          <div
            v-if="deliverTemplateCurrentData.deliverTemplateAreasDesc.length>0"
            class="deliverTemplateContent"
          >
            <p>{{$t("goodsAddEditInfo.deliverAndOtherInfo.pointAreaForFreeDeliverTemplate")}}</p>
            <p
              v-for="(item,index) in deliverTemplateCurrentData.deliverTemplateAreasDesc"
              :key="index"
            >{{item}}</p>
          </div>
          <div
            v-if="deliverTemplateCurrentData.freeDeliverTemplateAreasDesc.length>0"
            class="deliverTemplateContent"
          >
            <p>{{$t("goodsAddEditInfo.deliverAndOtherInfo.pointConditionForFreeDeliverTemplate")}}</p>
            <p
              v-for="(item,index) in deliverTemplateCurrentData.freeDeliverTemplateAreasDesc"
              :key="index"
            >{{item}}</p>
          </div>
        </div>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.deliverAndOtherInfo.goodsWeight')"
        prop="goodsWeight"
      >
        <el-input-number
          v-model="goodsProductInfo.goodsWeight"
          size="small"
          controls-position="right"
          :min="0"
          :precision="1"
          style="width:170px;"
        />
        <span>&nbsp;&nbsp;kg</span>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.deliverAndOtherInfo.deliverPlace')"
        prop="deliverPlace"
      >
        <el-input
          ref="deliverPlaceInput"
          v-model="goodsProductInfo.deliverPlace"
          size="small"
          style="width:170px;"
        />
        <span class="inputTip">{{$t('goodsAddEditInfo.deliverAndOtherInfo.deliverPlaceTip')}}</span>
      </el-form-item>
    </el-form>
    <div class="title">{{$t('goodsAddEditInfo.deliverAndOtherInfo.otherTitle')}}</div>
    <el-form
      ref="otherInfoForm"
      :model="goodsProductInfo"
      :label-width="labelWidth+'px'"
    >
      <el-form-item :label="$t('goodsAddEditInfo.deliverAndOtherInfo.memberCard')">
        <el-checkbox v-model="goodsProductInfo.isCardExclusive">{{$t('goodsAddEditInfo.deliverAndOtherInfo.memberCardTip')}}</el-checkbox>
        <div v-if="goodsProductInfo.isCardExclusive">
          <el-select
            v-model="cardSelectedTempVal"
            size="small"
            @change="cardSelectChange"
            :placeholder="$t('goodsAddEditInfo.deliverAndOtherInfo.chooseMemberCard')"
            style="width:170px;"
          >
            <el-option
              v-for="(item,index) in cardsSelectOptions"
              :value="item.id"
              :label="item.cardName"
              :key="index"
            />
          </el-select>
          <el-link
            type="primary"
            :underline="false"
            @click.native="cardSelectRefresh"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.linkRefresh')}}
          </el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            @click.native="$router.push({name: 'normalCardDetail'})"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.deliverAndOtherInfo.memberCardNew')}}</el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            @click.native="$router.push({name: 'user_card'})"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.deliverAndOtherInfo.memberCardManage')}}</el-link>
          <div
            v-if="cardSelectedItems.length>0"
            style="display: flex;flex-wrap: wrap;align-items:center;background-color: #f8f8f8;"
          >
            <div>{{$t('goodsAddEditInfo.deliverAndOtherInfo.memberCardSelected')}}</div>
            <div
              class="selectedWrap"
              v-for="(item,index) in cardSelectedItems"
              :key="index"
            >
              {{item.cardName}}
              <span
                @click="deleteCard(item,index)"
                class="deleteIcon"
              >×</span>
            </div>
          </div>
        </div>
      </el-form-item>
      <el-form-item
        :label="$t('goodsAddEditInfo.deliverAndOtherInfo.saleType') + '：'"
        prop="isOnSale"
      >
        <el-radio-group v-model="goodsProductInfo.saleType">
          <el-radio :label="0">{{$t('goodsAddEditInfo.deliverAndOtherInfo.saleNow')}}</el-radio><br />
          <el-radio :label="1">{{$t('goodsAddEditInfo.deliverAndOtherInfo.saleOnTime')}}</el-radio>
          <el-date-picker
            ref="saleTimeInput"
            v-model="goodsProductInfo.saleTime"
            :disabled="goodsProductInfo.saleType!==1"
            type="datetime"
            @change="saleTimeChange"
            size="small"
            :placeholder="$t('goodsAddEditInfo.deliverAndOtherInfo.chooseMemberCard')"
            style="margin-left: 10px;"
          /><br />
          <el-radio :label="2">{{$t('goodsAddEditInfo.deliverAndOtherInfo.notSale')}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// api导入
import { getExclusiveCardList } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
import { deliverTemplateNameListApi, getDeliverTemplateApi, getDeliverTemplateConfigApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
// js工具函数导入
import { isStrBlank, isNumberBlank } from '@/util/typeUtil'
import { format, parseDate } from '@/util/date'
export default {
  data () {
    return {
      lang: '',
      labelWidth: 120,
      goodsProductInfo: {
        deliverTemplateId: 0,
        goodsWeight: null,
        deliverPlace: null,
        isCardExclusive: false,
        saleType: 0,
        saleTime: new Date()
      },
      /* 运费模板辅助数据 */
      // 当前选中的运费模板，用来展示模板的详细信息使用,此处初始化用的undefined,当没有运费模板的时候在进行大括号插值显示的时候判断使用
      deliverTemplateCurrentData: {
        deliverTemplateId: 0,
        deliverTemplateTitleDesc: null,
        deliverTemplateAreasDesc: [],
        freeDeliverTemplateAreasDesc: []

      },
      /* 运费模板数据 */
      deliverTemplateData: [],
      /* 会员专享卡数据 */
      cardsSelectOptions: [],
      cardSelectedTempVal: null,
      cardSelectedItems: []
    }
  },
  methods: {
    /* loading开始加载遮罩预留函数 */
    beginLoading () {

    },
    /* loading关闭加载遮罩预留函数 */
    closeLoading () {

    },
    /* 解析运费模板数据 */
    parseDeliverTemplateData (data) {
      // 定义国际化变量
      // 全国其他区域运费：
      let pintAreaOtherDeliverFee = this.$t('goodsAddEditInfo.deliverAndOtherInfo.pintAreaOtherDeliverFee')
      // 件
      let deliverTemplateUnit1 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateUnit1')
      // 公斤
      let deliverTemplateUnit2 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateUnit2')
      // 除可配送区域外，不可配送
      let deliverTemplateTitleDesc1 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc1')
      // 内
      let deliverTemplateTitleDesc2 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc2')
      // 元,每增加'
      let deliverTemplateTitleDesc3 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc3')
      // ,加
      let deliverTemplateTitleDesc4 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc4')
      // 元
      let deliverTemplateTitleDesc9 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc9')
      // 内
      let deliverTemplateAreasDesc1 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateAreasDesc1')
      // 元,每增加
      let deliverTemplateAreasDesc2 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateAreasDesc2')
      // ，加
      let deliverTemplateAreasDesc3 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateAreasDesc3')
      // 元
      let deliverTemplateAreasDesc4 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateAreasDesc4')
      // 内包邮
      let freeDeliverTemplateAreasDesc1 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.freeDeliverTemplateAreasDesc1')
      // 满
      let freeDeliverTemplateAreasDesc2 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.freeDeliverTemplateAreasDesc2')
      // 元包邮
      let freeDeliverTemplateAreasDesc3 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.freeDeliverTemplateAreasDesc3')
      // 内
      let freeDeliverTemplateAreasDesc4 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.freeDeliverTemplateAreasDesc4')
      // 元包邮
      let freeDeliverTemplateAreasDesc5 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.freeDeliverTemplateAreasDesc5')

      // let unit = data.flag === 0 ? '件' : '公斤'
      let unit = data.flag === 0 ? deliverTemplateUnit1 : deliverTemplateUnit2

      let retData = {}
      retData.deliverTemplateId = data.deliverTemplateId
      let templateContent = data.content
      // 运费模板:'除可配送区域外，其他不可配送' 部分信息
      retData.deliverTemplateTitleDesc = null

      let limitArea = templateContent.limitParam
      if (limitArea.limit_deliver_area === 1) {
        // retData.deliverTemplateTitleDesc = '除可配送区域外，不可配送'
        retData.deliverTemplateTitleDesc = deliverTemplateTitleDesc1
      } else { // 全国其他区域运费：1件内5元,没增加1件，加10元
        retData.deliverTemplateTitleDesc = `${pintAreaOtherDeliverFee}${limitArea.first_num}${unit}${deliverTemplateTitleDesc2}${limitArea.first_fee}${deliverTemplateTitleDesc3}${limitArea.continue_num}${unit}${deliverTemplateTitleDesc4}${limitArea.continue_fee}${deliverTemplateTitleDesc9}`
      }

      // 搜索指定可配送区域运费
      retData.deliverTemplateAreasDesc = []
      if (templateContent.areaParam !== null && templateContent.areaParam.length > 0) {
        for (let i = 0; i < templateContent.areaParam.length; i++) {
          let temp = templateContent.areaParam[i]
          // 北京：1件内10元，每增加5件，加10元
          retData.deliverTemplateAreasDesc.push(`${temp.area_text.join(',')}：${temp.first_num}${unit}${deliverTemplateAreasDesc1}${temp.first_fee}${deliverTemplateAreasDesc2}${temp.continue_num}${unit}${deliverTemplateAreasDesc3}${temp.continue_fee}${deliverTemplateAreasDesc4}`)
        }
      }

      // 指定条件包邮可配送区域运费
      retData.freeDeliverTemplateAreasDesc = []
      if (templateContent.has_fee_0_condition === 0) {
        return retData
      }
      for (let i = 0; i < templateContent.feeConditionParam.length; i++) {
        let temp = templateContent.feeConditionParam[i]
        // 指定件数包邮
        if (temp.fee_0_condition === '1') {
          retData.freeDeliverTemplateAreasDesc.push(`${temp.area_text.join(',')}：${temp.fee_0_con1_num}${unit}${freeDeliverTemplateAreasDesc1}`)
        } else if (temp.fee_0_condition === '2') {
          retData.freeDeliverTemplateAreasDesc.push(`${temp.area_text.join(',')}：${freeDeliverTemplateAreasDesc2}${temp.fee_0_con2_fee}${freeDeliverTemplateAreasDesc3}`)
        } else {
          retData.freeDeliverTemplateAreasDesc.push(`${temp.area_text.join(',')}：${temp.fee_0_con3_num}${unit}${freeDeliverTemplateAreasDesc4}${temp.fee_0_con3_fee}${freeDeliverTemplateAreasDesc5}`)
        }
      }
      return retData
    },
    /* 解析默认运费模板 */
    parseDeliverTemplateDefaultData (data) {
      let content = JSON.parse(data.content)
      let retData = {}
      retData.deliverTemplateId = 0

      let deliverTemplateTitleDesc5 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc5') // "订单满"
      let deliverTemplateTitleDesc6 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc6') // "包邮，否则运费为"
      let deliverTemplateTitleDesc7 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc7') // "元"
      let deliverTemplateTitleDesc8 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc8') // "店铺统一运费："
      let deliverTemplateTitleDesc9 = this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateTitleDesc9')// "元"

      if (content.templateName === 1) {
        //
        retData.deliverTemplateTitleDesc = `${deliverTemplateTitleDesc5}${content.feeLimit}${deliverTemplateTitleDesc6}${content.price}${deliverTemplateTitleDesc7}`
      } else {
        // 订单统一运费：8元
        retData.deliverTemplateTitleDesc = `${deliverTemplateTitleDesc8}${content.price}${deliverTemplateTitleDesc9}`
      }
      retData.deliverTemplateAreasDesc = []
      retData.freeDeliverTemplateAreasDesc = []
      return retData
    },
    /* 运费模板下拉框change处理函数 */
    deliverTemplateChange (deliverTemplateId) {
      // 加载遮罩
      this.beginLoading()

      if (deliverTemplateId === null || deliverTemplateId === 0) {
        // 查找默认配置
        getDeliverTemplateConfigApi().then(res => {
          this.deliverTemplateCurrentData = this.parseDeliverTemplateDefaultData(res)
          this.closeLoading()
        })
      } else {
        // 找到对应模板信息
        getDeliverTemplateApi({ 'deliverTemplateId': deliverTemplateId }).then(res => {
          this.closeLoading()
          let content = res.content
          // 模板信息被删除，则展示默认模板信息
          if (content === null) {
            this.goodsProductInfo.deliverTemplateId = 0
            this.deliverTemplateChange(0)
          } else {
            this.deliverTemplateCurrentData = this.parseDeliverTemplateData(content)
          }
        })
      }
    },
    /* 刷新运费模板 */
    deliverTemplateSelectRefresh () {
      this.deliverTemplateDataInit()
    },
    /* 初始化运费模板下拉框数据 */
    deliverTemplateDataInit () {
      return deliverTemplateNameListApi().then(res => {
        let content = res.content || []
        this.deliverTemplateData = []

        content.forEach(item => {
          this.deliverTemplateData.push({
            deliverTemplateId: item.deliverTemplateId,
            templateName: item.flag === '0' ? '普通--' + item.templateName : '重量--' + item.templateName
          })
        })
        this.deliverTemplateData.unshift({
          deliverTemplateId: 0,
          templateName: this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverTemplateDefault')
        })
        // 刷新时回显使用
        this.goodsProductInfo.deliverTemplateId = this.deliverTemplateCurrentData.deliverTemplateId
        this.deliverTemplateChange(this.goodsProductInfo.deliverTemplateId)
      })
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
      getExclusiveCardList().then(res => {
        let tempArr = res.content || []
        this.cardsSelectOptions = tempArr.filter(item => !this.cardSelectedItems.some(innerItem => innerItem.id === item.id))
      })
    },
    /* 删除商品已选会员卡,并将删除的项添加到下拉框内 */
    deleteCard (item, index) {
      this.cardSelectedItems.splice(index, 1)
      this.cardsSelectOptions.push(item)
    },
    /* 初始化会员专享卡 */
    cardDataInit () {
      return getExclusiveCardList().then(res => {
        this.cardsSelectOptions = res.content || []
      })
    },
    /* 选择上架售卖时间change */
    saleTimeChange () {
      // 点击了时间控件的清空按钮或者上架时间原本就是空（比如第一次点击）
      if (this.goodsProductInfo.saleTime === null) {
        return
      }
      // 只能选择当前时间以后的时间，否者为当前时间值
      if (new Date().getTime() > this.goodsProductInfo.saleTime.getTime()) {
        this.goodsProductInfo.saleTime = new Date()
      }
    },

    /* 回显运费模板 */
    _initDeliverTemplateId (goodsData) {
      this.goodsProductInfo.deliverTemplateId = goodsData.deliverTemplateId
      this.deliverTemplateChange(goodsData.deliverTemplateId)
    },
    /* 初始化专享会员卡 */
    _initExclusiveCard (goodsData) {
      this.goodsProductInfo.isCardExclusive = goodsData.isCardExclusive === 1
      this.cardsSelectOptions = this.cardsSelectOptions.filter(item => {
        let has = goodsData.memberCardIds.some(cardId => cardId === item.id)
        if (has) {
          this.cardSelectedItems.push(item)
        }
        return !has
      })
    },
    /* 页面数据初始化链，避免页面数据未加载完成的时候就初始化待修改商品数据，返回一个Promise */
    initPageDataLink () {
      let p1 = this.deliverTemplateDataInit()
      let p2 = this.cardDataInit()
      return Promise.all([p1, p2])
    },
    /* 修改商品数据初始化 */
    initDataForUpdate (goodsData) {
      return this.initPageDataLink().then(() => {
        // 初始化运费模板
        this._initDeliverTemplateId(goodsData)
        // 初始化商品重量
        this.goodsProductInfo.goodsWeight = goodsData.goodsWeight
        // 初始化发货地
        this.goodsProductInfo.deliverPlace = goodsData.deliverPlace
        // 初始化专享会员卡
        this._initExclusiveCard(goodsData)
        // 初始化上架时间
        this.goodsProductInfo.saleType = goodsData.saleType
        this.goodsProductInfo.saleTime = parseDate(goodsData.saleTime)
      })
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {

    },
    /* 新增商品数据初始化 */
    initDataForInsert () {
      this.cardDataInit()
      this.deliverTemplateDataInit()
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      if (!isStrBlank(this.goodsProductInfo.deliverPlace) && this.goodsProductInfo.deliverPlace.length > 15) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.deliverAndOtherInfo.deliverPlaceTip'), type: 'warning' })
        this.$refs.deliverPlaceInput.focus()
        return false
      }

      if (this.goodsProductInfo.saleType === 1) {
        if (this.goodsProductInfo.saleTime === null) {
          this.$message.warning({ message: this.$t('goodsAddEditInfo.deliverAndOtherInfo.saleTimeNotNll'), type: 'warning' })
          this.$refs.saleTimeInput.focus()
          return false
        }
        if (this.goodsProductInfo.saleTime.getTime() <= new Date().getTime()) {
          this.$message.warning({ message: this.$t('goodsAddEditInfo.deliverAndOtherInfo.saleTimeCanNotBeBefore'), type: 'warning' })
          this.$refs.saleTimeInput.focus()
          return false
        }
      }
      return true
    },
    /* 获取传给后台的表单数据 */
    getFormData () {
      let retData = {
        deliverTemplateId: this.goodsProductInfo.deliverTemplateId,
        isCardExclusive: this.goodsProductInfo.isCardExclusive ? 1 : 0,
        memberCardIds: null,
        saleType: this.goodsProductInfo.saleType,
        saleTime: null,
        isOnSale: this.goodsProductInfo.saleType === 0 ? 1 : 0
      }

      retData.goodsWeight = isNumberBlank(this.goodsProductInfo.goodsWeight) ? null : this.goodsProductInfo.goodsWeight
      retData.deliverPlace = this.goodsProductInfo.deliverPlace

      if (this.cardSelectedItems.length > 0) {
        retData.memberCardIds = []
        this.cardSelectedItems.forEach(item => retData.memberCardIds.push(item.id))
      }

      if (retData.saleType === 1) {
        retData.saleTime = format(this.goodsProductInfo.saleTime)
      }
      return retData
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
.deliverTemplateContentWrap {
  position: relative;
  background-color: #f8f8f8;
  padding: 10px;
  box-sizing: border-box;
}
.deliverTemplateContentTitle {
  width: calc(100% - 30px);
}
.deliverTemplateContent {
  border-top: 1px solid #ccc;
}
.selectedWrap {
  min-width: 70px;
  height: 22px;
  border: 1px solid #ccc;
  line-height: 22px;
  text-align: center;
  padding: 0px 5px;
  margin: 0px 5px;
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
