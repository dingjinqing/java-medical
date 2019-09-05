<template>
  <div>
    <div class="title">配送信息</div>
    <el-form
      ref="deliveryInfoForm"
      :model="goodsProductInfo"
      label-width="120px"
    >
      <el-form-item
        label="运费模板："
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
        >刷新模板
        </el-link>
        |
        <el-link
          type="primary"
          :underline="false"
          href="#"
          style="margin:0 5px;"
        >新建模板</el-link>
        |
        <el-link
          type="primary"
          :underline="false"
          href="#"
          style="margin:0 5px;"
        >管理模板</el-link>
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
            >查看详情</el-link>
          </div>
          <div v-if="deliverTemplateCurrentData.deliverTemplateAreasDesc.length>0" class="deliverTemplateContent">
            <p>指定可配送区域运费:</p>
            <p v-for="(item,index) in deliverTemplateCurrentData.deliverTemplateAreasDesc" :key="index">{{item}}</p>
          </div>
          <div v-if="deliverTemplateCurrentData.freeDeliverTemplateAreasDesc.length>0" class="deliverTemplateContent">
            <p>指定条件包邮可配送区域运费:</p>
            <p v-for="(item,index) in deliverTemplateCurrentData.freeDeliverTemplateAreasDesc" :key="index">{{item}}</p>
          </div>
        </div>
      </el-form-item>
      <el-form-item
        label="商品重量："
        prop="goodsWeight"
      >
        <el-input-number
          v-model="goodsProductInfo.goodsWeight"
          step-strictly
          size="small"
          controls-position="right"
          :min="0"
          style="width:170px;"
        />
        <span>&nbsp;&nbsp;Kg</span>
      </el-form-item>
      <el-form-item
        label="发货地："
        prop="deliverPlace"
      >
        <el-input
          ref="deliverPlaceInput"
          v-model="goodsProductInfo.deliverPlace"
          size="small"
          style="width:230px;"
        />
        <span class="inputTip">最多15个字</span>
      </el-form-item>
    </el-form>
    <div class="title">其他信息</div>
    <el-form
      ref="otherInfoForm"
      :model="goodsProductInfo"
      label-width="120px"
    >
      <el-form-item
        label="会员专享商品："
        prop="deliverTemplateId"
      >
        <el-checkbox v-model="goodsProductInfo.isCardExclusive">用户持有会员卡才可以购买此商品</el-checkbox>
        <div v-if="goodsProductInfo.isCardExclusive">
          <el-select
            v-model="cardSelectedTempVal"
            size="small"
            @change="cardSelectChange"
            placeholder="请选择会员卡"
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
            @click="cardSelectRefresh"
            href="#"
            style="margin:0 5px;"
          >刷新
          </el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            href="#"
            style="margin:0 5px;"
          >新建会员卡</el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            href="#"
            style="margin:0 5px;"
          >管理会员卡</el-link>
          <div
            v-if="cardSelectedItems.length>0"
            style="display: flex;flex-wrap: wrap;align-items:center;background-color: #f8f8f8;"
          >
            <div>已选：</div>
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
        label="上下架："
        prop="isOnSale"
      >
        <el-radio-group v-model="goodsProductInfo.saleType">
          <el-radio :label="0">立即上架售卖</el-radio><br />
          <el-radio :label="1">自定义上架时间</el-radio>
          <el-date-picker
            ref="saleTimeInput"
            v-model="goodsProductInfo.saleTime"
            :disabled="goodsProductInfo.saleType!==1"
            type="datetime"
            @change="saleTimeChange"
            size="small"
            placeholder="选择上架售卖时间"
            style="margin-left: 10px;"
          /><br />
          <el-radio :label="2">暂不售卖放入仓库</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// api导入
import {getExclusiveCardList} from '@/api/admin/goodsManage/addingGoods/addingGoods'
import {deliverTemplateNameListApi, getDeliverTemplateApi, getDeliverTemplateConfigApi} from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
// js工具函数导入
import { isStrBlank, isNumberBlank } from '@/util/goodsUtil'
import { format } from '@/util/date'
export default {
  data () {
    return {
      goodsProductInfo: {
        goodsWeight: null,
        deliverTemplateId: null,
        deliverPlace: null,
        isCardExclusive: false,
        saleType: 0,
        saleTime: new Date()
      },
      /* 运费模板辅助数据 */
      // 当前选中的运费模板，用来展示模板的详细信息使用,此处初始化用的undefined,当没有运费模板的时候在进行大括号插值显示的时候判断使用
      deliverTemplateCurrentData: {
        deliverTemplateId: null,
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
      let unit = data.flag === '0' ? '件' : '公斤'

      let retData = {}
      let temp = null
      retData.deliverTemplateId = data.deliverTemplateId
      let templateContent = JSON.parse(data.templateContent)
      // 运费模板:'除可配送区域外，其他不可配送' 部分信息
      retData.deliverTemplateTitleDesc = null
      // 搜索area_list为'0'的表示配送基础信心配置项
      for (let i = 0; i < templateContent.data_list.length; i++) {
        if (templateContent.data_list[i].area_list === '0') {
          temp = templateContent.data_list[i]
          break
        }
      }
      if (temp.limit_deliver_area === 1) {
        retData.deliverTemplateTitleDesc = '除可配送区域外，不可配送'
      } else { // 1件内5元,
        retData.deliverTemplateTitleDesc = `${temp.first_num} ${unit}内${temp.first_fee}元,每增加${temp.continue_num}${unit},加${temp.continue_fee}元`
      }

      // 搜索指定可配送区域运费
      retData.deliverTemplateAreasDesc = []
      for (let i = 0; i < templateContent.data_list.length; i++) {
        if (templateContent.data_list[i].area_list === '0') {
          continue
        }
        temp = templateContent.data_list[i]
        retData.deliverTemplateAreasDesc.push(`${temp.area_text}：${temp.first_num} ${unit}内${temp.first_fee}元,每增加${temp.continue_num}${unit},加${temp.continue_fee}元`)
      }
      // 指定条件包邮可配送区域运费
      retData.freeDeliverTemplateAreasDesc = []
      if (templateContent.has_fee_0_condition === 0) {
        return retData
      }
      for (let i = 0; i < templateContent.fee_0_data_list.length; i++) {
        temp = templateContent.fee_0_data_list[i]
        // 指定件数包邮
        if (temp.fee_0_condition === '1') {
          retData.freeDeliverTemplateAreasDesc.push(`${temp.area_text}：${temp.fee_0_con1_num}${unit}内包邮`)
        } else if (temp.fee_0_condition === '2') {
          retData.freeDeliverTemplateAreasDesc.push(`${temp.area_text}：满${temp.fee_0_con2_fee}元包邮`)
        } else {
          retData.freeDeliverTemplateAreasDesc.push(`${temp.area_text}：${temp.fee_0_con3_num}${unit}内，${temp.fee_0_con3_fee}元包邮`)
        }
      }
      return retData
    },
    /* 解析默认运费模板 */
    parseDeliverTemplateDefaultData (data) {
      let content = JSON.parse(data.content)
      let retData = {}
      retData.deliverTemplateId = null
      if (content.templateName === 1) {
        retData.deliverTemplateTitleDesc = `订单满${content.feeLimit}包邮，否则运费为${content.price}元`
      } else {
        retData.deliverTemplateTitleDesc = `店铺统一运费：${content.price}元`
      }
      retData.deliverTemplateAreasDesc = []
      retData.freeDeliverTemplateAreasDesc = []
      return retData
    },
    /* 运费模板下拉框change处理函数 */
    deliverTemplateChange (deliverTemplateId) {
      // 加载遮罩
      this.beginLoading()

      if (deliverTemplateId === null) {
        // 查找默认配置
        getDeliverTemplateConfigApi().then(res => {
          this.deliverTemplateCurrentData = this.parseDeliverTemplateDefaultData(res)
          this.closeLoading()
        })
      } else {
        // 找到对应模板信息
        getDeliverTemplateApi({'deliverTemplateId': deliverTemplateId}).then(res => {
          this.closeLoading()
          let content = res.content || []
          // 模板信息被删除，则展示默认模板信息
          if (content.length === 0) {
            this.goodsProductInfo.deliverTemplateId = null
            this.deliverTemplateChange(null)
          } else {
            this.deliverTemplateCurrentData = this.parseDeliverTemplateData(content[0])
          }
        })
      }
    },
    /* 刷新运费模板 */
    deliverTemplateSelectRefresh () {
      this.deliverTemplateDataInit()
    },
    /* 初始化运费模板数据 */
    deliverTemplateDataInit () {
      deliverTemplateNameListApi().then(res => {
        let content = res.content || []
        this.deliverTemplateData = []

        content.forEach(item => {
          this.deliverTemplateData.push({
            deliverTemplateId: item.deliverTemplateId,
            templateName: item.flag === '0' ? '普通--' + item.templateName : '重量--' + item.templateName
          })
        })
        this.deliverTemplateData.unshift({
          deliverTemplateId: null,
          templateName: '店铺默认运费模板'
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
      getExclusiveCardList().then(res => {
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
    /** 此函数由父组件主动调用 **/
    /* 验证数据是否全部合法 */
    validateFormData () {
      if (!isStrBlank(this.goodsProductInfo.deliverPlace) && this.goodsProductInfo.deliverPlace.length > 15) {
        this.$message({message: '发货地址最多15个字', type: 'warning'})
        this.$refs.deliverPlaceInput.focus()
        return false
      }

      if (this.goodsProductInfo.saleType === 1) {
        if (this.goodsProductInfo.saleTime === null) {
          this.$message({message: '自定义上架时间不可为空', type: 'warning'})
          this.$refs.saleTimeInput.focus()
          return false
        }
        if (this.goodsProductInfo.saleTime.getTime() <= new Date().getTime()) {
          this.$message({message: '自定义上架时间不可早于当前时间', type: 'warning'})
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
        saleTime: null
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
    /* 运费模板初始化 */
    this.deliverTemplateDataInit()
    /* 会员专享卡初始化 */
    this.cardDataInit()
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
