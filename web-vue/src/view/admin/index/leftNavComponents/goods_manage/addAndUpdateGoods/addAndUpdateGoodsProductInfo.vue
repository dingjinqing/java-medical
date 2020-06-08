<template>
  <div>
    <!--基本信息配置模块-->
    <addAndUpdateBasicInfo ref="basicInfo" />
    <!--库存/价格信息-->
    <addAndUpdateStockAndPriceInfo ref="stockAndPriceInfo" v-bind="transferData" v-on:default_prd_change="defaultPrdChange"/>
    <!--配送信息-->
    <addAndUpdateDeliverAndOtherInfo ref="deliverAndOtherInfo" v-bind="transferData"/>

    <el-dialog :title="$t('goodsAddEditInfo.goodsInfoMissing')"
               :visible.sync="goodsWeightDialogShow"
               width="610px">
      <div class="infoContent" v-html="$t('goodsAddEditInfo.goodsWeightConfigInfo')">
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="goodsWeightDialogConfirm">{{$t('goodsAddEditInfo.confirmBtn')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {selectGoodsCommonConfig, openGoodsWeightCfg} from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// 组件导入
import addAndUpdateBasicInfo from './addAndUpdateBasicInfo'
import addAndUpdateStockAndPriceInfo from './addAndUpdateStockAndPriceInfo'
import addAndUpdateDeliverAndOtherInfo from './addAndUpdateDeliverAndOtherInfo'
// 导入js工具
import {isNumberBlank} from '@/util/typeUtil'
export default {
  components: { addAndUpdateBasicInfo, addAndUpdateStockAndPriceInfo, addAndUpdateDeliverAndOtherInfo },
  data () {
    return {
      goodsWeightDialogShow: false,
      transferData: {
        isDefaultPrd: true,
        needPrdCodes: 0,
        goodsWeightCfg: 0
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
      let deliverAndOtherInfoData = this.$refs.deliverAndOtherInfo.getFormData()

      let deliverType = deliverAndOtherInfoData.deliverTemplateType
      // 选择重量模板
      if (deliverType === 1) {
        // 但是没开启重量填写配置
        if (this.transferData.goodsWeightCfg === 0) {
          this.goodsWeightDialogShow = true
          return false
        }
      }

      // 需要校验商品重量逻辑
      if (this.transferData.goodsWeightCfg === 1) {
        // 选择了重量模板则需要校验填入的数据是否正确
        if (this.transferData.isDefaultPrd) {
          // 填了数据
          if (!isNumberBlank(deliverAndOtherInfoData.goodsWeight)) {
            // 数据不合法
            if (deliverAndOtherInfoData.goodsWeight <= 0) {
              this.$message.warning(this.$t('goodsAddEditInfo.warningInfo.goodsWeightIsNull'))
              this.$refs.deliverAndOtherInfo.$refs.goodsWeightInput.focus()
              return false
            }
          } else {
            // 没填数据，但是选的是运费模板
            if (deliverType === 1) {
              this.$message.warning(this.$t('goodsAddEditInfo.warningInfo.goodsWeightIsNull'))
              this.$refs.deliverAndOtherInfo.$refs.goodsWeightInput.focus()
              return false
            }
          }
        } else {
          let stockAndPriceInfoData = this.$refs.stockAndPriceInfo.getFormData()
          if (deliverType === 1) {
            for (let i = 0; i < stockAndPriceInfoData.goodsSpecProducts.length; i++) {
              let item = stockAndPriceInfoData.goodsSpecProducts[i]
              // 没填数据，或者数据错误
              if (isNumberBlank(item.prdWeight) || item.prdWeight <= 0) {
                this.$message.warning(this.$t('goodsAddEditInfo.warningInfo.goodsPrdWeightIsNull'))
                document.getElementById('prdWeight_' + item.prdDesc).focus()
                return false
              }
            }
          } else {
            for (let i = 0; i < stockAndPriceInfoData.goodsSpecProducts.length; i++) {
              let item = stockAndPriceInfoData.goodsSpecProducts[i]
              // 填数据但是数据错误
              if (!isNumberBlank(item.prdWeight) && item.prdWeight <= 0) {
                this.$message.warning(this.$t('goodsAddEditInfo.warningInfo.goodsPrdWeightIsNull'))
                document.getElementById('prdWeight_' + item.prdDesc).focus()
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
      let stockAndPriceInfoData = this.$refs.stockAndPriceInfo.getFormData()
      let deliverAndOtherInfoData = this.$refs.deliverAndOtherInfo.getFormData()
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
    // 国际化
    this.langDefault()
  }
}
</script>
<style scoped>
  .infoContent{
    line-height: 24px;
  }
</style>
