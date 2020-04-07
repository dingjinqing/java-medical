<template>
  <div>
    <!--基本信息配置模块-->
    <addAndUpdateBasicInfo ref="basicInfo" />
    <!--库存/价格信息-->
    <addAndUpdateStockAndPriceInfo ref="stockAndPriceInfo" v-bind="transferData" v-on:default_prd_change="defaultPrdChange"/>
    <!--配送信息-->
    <addAndUpdateDeliverAndOtherInfo ref="deliverAndOtherInfo" v-bind="transferData"/>

    <el-dialog title="商品信息缺失"
               :visible.sync="goodsWeightDialogShow"
               width="30%">
      <p class="infoContent">当前商品运费设置已选【重量运费模板】，必须填写商品重量后保存。</p>
      <p class="infoContent">如页面未展示商品提示信息，请前往"基础配置-店铺基础配置-店铺通用配置"页面，开启"商品重量配置项设置"开关后,重新编辑商品信息。</p>
      <p class="infoContent">点击确定按钮，可自动开启</p>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="goodsWeightDialogConfirm">确 定</el-button>
      </span>
    </el-dialog>
    <el-button @click="goodsWeightDialogShow=true">点击</el-button>
  </div>
</template>
<script>
// js工具函数导入
import {isNumberBlank} from '@/util/typeUtil'
import {selectGoodsCommonConfig, openGoodsWeightCfg} from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// 组件导入
import addAndUpdateBasicInfo from './addAndUpdateBasicInfo'
import addAndUpdateStockAndPriceInfo from './addAndUpdateStockAndPriceInfo'
import addAndUpdateDeliverAndOtherInfo from './addAndUpdateDeliverAndOtherInfo'
export default {
  components: { addAndUpdateBasicInfo, addAndUpdateStockAndPriceInfo, addAndUpdateDeliverAndOtherInfo },
  data () {
    return {
      goodsWeightDialogShow: false,
      transferData: {
        isDefaultPrd: true,
        needPrdCodes: 0,
        goodsWeightCfg: 0
      },
      childCmpData: {
        basicInfoData: null,
        stockAndPriceInfoData: null,
        deliverAndOtherInfoData: null
      }
    }
  },
  methods: {
    /* 商品重量提示框点击确认 */
    goodsWeightDialogConfirm () {
      openGoodsWeightCfg().then(() => {
        this.getGoodsCommonConfigData()
        this.goodsWeightDialogShow = false
      })
    },
    /* 是否使用默认规格监听 */
    defaultPrdChange (newVal) {
      this.transferData.isDefaultPrd = newVal
    },
    /* 初始化商品编辑时需要的店铺配置新 */
    getGoodsCommonConfigData () {
      selectGoodsCommonConfig().then(res => {
        this.transferData.needPrdCodes = res.content.needPrdCodes
        this.transferData.goodsWeightCfg = res.content.goodsWeightCfg
      })
    },
    /* 修改商品数据初始化 */
    initDataForUpdate (goodsData) {
      this.$refs.basicInfo.initDataForUpdate(goodsData)
      this.$refs.stockAndPriceInfo.initDataForUpdate(goodsData)
      this.$refs.deliverAndOtherInfo.initDataForUpdate(goodsData)
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {
      this.$refs.basicInfo.disposeDataForCopy()
      this.$refs.stockAndPriceInfo.disposeDataForCopy()
      this.$refs.deliverAndOtherInfo.disposeDataForCopy()
    },
    /* 新增商品数据初始化 */
    initDataForInsert () {
      this.$refs.basicInfo.initDataForInsert()
      this.$refs.stockAndPriceInfo.initDataForInsert()
      this.$refs.deliverAndOtherInfo.initDataForInsert()
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      let validateVal = this.$refs.basicInfo.validateFormData() && this.$refs.stockAndPriceInfo.validateFormData() && this.$refs.deliverAndOtherInfo.validateFormData()
      if (!validateVal) {
        return false
      }
      this.childCmpData.stockAndPriceInfoData = this.$refs.stockAndPriceInfo.getFormData()
      this.childCmpData.deliverAndOtherInfoData = this.$refs.deliverAndOtherInfo.getFormData()

      let deliverType = this.childCmpData.deliverAndOtherInfoData.deliverTemplateType

      // 选择重量模板
      if (deliverType === 1) {
        // 但是没开启重量填写配置
        if ((this.transferData.goodsWeightCfg === 0)) {
          this.goodsWeightDialogShow = true
          return false
        } else {
          // 校验是否重量都写了
          // 默认规格
          if (this.transferData.isDefaultPrd) {
            if (isNumberBlank(this.childCmpData.deliverAndOtherInfoData.goodsWeight)) {
              this.$message.warning('请填写商品重量!')
              return false
            }
          } else {
            let specPrds = this.childCmpData.stockAndPriceInfoData.goodsSpecProducts
            for (let i = 0; i < specPrds.length; i++) {
              if (isNumberBlank(specPrds[i].prdWeight)) {
                this.$message.warning('请填写商品规格重量!')
                return false
              }
            }
          }
        }
      }

      return true
    },
    /* 获取数据 */
    getFormData () {
      let basicInfoData = this.$refs.basicInfo.getFormData()
      // 校验重量时已经拿到数据
      let stockAndPriceInfoData = this.childCmpData.stockAndPriceInfoData
      let deliverAndOtherInfoData = this.childCmpData.deliverAndOtherInfoData
      // 默认规格
      if (this.transferData.isDefaultPrd) {
        stockAndPriceInfoData.goodsSpecProducts[0].prdImg = basicInfoData.goodsImg
        stockAndPriceInfoData.goodsSpecProducts[0].prdWeight = deliverAndOtherInfoData.goodsWeight
      }

      return {
        ...basicInfoData,
        ...stockAndPriceInfoData,
        ...deliverAndOtherInfoData
      }
    }
  },
  mounted () {
    this.getGoodsCommonConfigData()
  }
}
</script>
<style scoped>
  .infoContent{
    line-height: 24px;
  }
</style>
