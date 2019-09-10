<template>
  <div class="addGoodsWrap" >
    <div
      v-if="reload"
      id="addGoods"
      class="addGoodsContent"
    >
      <!-- 头部导航 headerSteps-->
      <el-steps
        :active="stepData.currentStep"
        finish-status="finish"
        simple
      >
        <el-step
          :title="$t('goodsAddEditInfo.editGoodsBasicInfo')"
          icon="el-icon-edit"
          @click.native="headerStepsClick(1)"
          style="cursor: pointer"
        />
        <el-step
          :title="$t('goodsAddEditInfo.editGoodsDetailInfo')"
          icon="el-icon-edit"
          @click.native="headerStepsClick(2)"
          style="cursor: pointer"
        />
        <el-step
          :title="$t('goodsAddEditInfo.editGoodsDistributionInfo')"
          icon="el-icon-edit"
          @click.native="headerStepsClick(3)"
          style="cursor: pointer"
        />
      </el-steps>

      <!-- 主要内容区 -->
      <!--商品信息-->
        <addGoodsProductInfo
          ref="goodsProductInfoCmp"
          v-show="stepData.currentStep===1"
        />
      <!--商品详情-->
        <addGoodsDetails
          :goodsProductInfoData="goodsProductInfoDataForDetails"
          ref="goodsDetailsCmp"
          v-show="stepData.currentStep===2"
        />
      <!--商品分销信息-->
        <addGoodsDistributionInfo
          :goodsProductInfoData="goodsProductInfoDataForDistribution"
          ref="goodsDistributionInfoCmp"
          v-show="stepData.currentStep===3"
        />

      <!-- 底部按钮组件 -->
      <div class="addingGoodsFooter">
        <el-button
          class="btn"
          type="primary"
          size="small"
          @click="saveGoodsReturnList"
        >{{$t('goodsAddEditInfo.saveAndReturnList')}}
        </el-button>
        <el-button
          class="btn"
          size="small"
          @click="footerStepsClick(-1)"
          v-show="stepData.currentStep!==1"
        >{{$t('goodsAddEditInfo.previewStep')}}
        </el-button>
        <el-button
          class="btn"
          size="small"
          @click="footerStepsClick(1)"
          v-show="stepData.currentStep!==3"
        >{{$t('goodsAddEditInfo.nextStep')}}
        </el-button>
        <el-button
          class="btn"
          size="small"
          type="primary"
          @click="saveGoodsContinueAdd"
          v-show="stepData.currentStep!==1"
        >{{$t('goodsAddEditInfo.saveAndAdd')}}
        </el-button>
        <el-button
          class="btn"
          size="small"
          type="primary"
          @click="saveGoodsView"
          v-show="stepData.currentStep!==1"
        >{{$t('goodsAddEditInfo.saveAndReview')}}
        </el-button>
      </div>

      <!--预览商品太阳码-->
      <el-dialog :visible.sync="qrCodeData.isShow" :title="$t('goodsAddEditInfo.goodsAppView')" width="350px">
        <div style="text-align: center;">
          <el-image
            fit="scale-down"
            :src="qrCodeData.imgFullUrl"
            style="width: 250px; height: 230px;"
          />
        </div>
        <div slot="footer" style="text-align: center;">
          <el-button @click="reloadCmp" type="primary">{{$t('goodsAddEditInfo.continueAdd')}}</el-button>
          <el-button @click="returnGoodsList">{{$t('goodsAddEditInfo.returnToList')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
/* 导入组件 */
import addGoodsProductInfo from './addGoodsProductInfo'
import addGoodsDetails from './addGoodsDetails'
import addGoodsDistributionInfo from './addGoodsDistributionInfo'

/* 导入js组件 */
import {addGoodsApi, getGoodsQrCode} from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'

export default {
  name: 'addingGoods',
  components: {addGoodsProductInfo, addGoodsDetails, addGoodsDistributionInfo},
  computed: {
    goodsProductInfoDataForDetails: function () {
      let retData = {}
      retData.goodsName = this.goodsProductInfoData.goodsName || ''
      retData.goodsImg = this.goodsProductInfoData.goodsImg || ''
      retData.unit = this.goodsProductInfoData.unit || ''
      retData.goodsSpecProducts = this.goodsProductInfoData.goodsSpecProducts || []
      return retData
    },
    goodsProductInfoDataForDistribution () {
      let retData = {}
      retData.goodsSpecProducts = this.goodsProductInfoData.goodsSpecProducts || []
      retData.specInfoSwitch = this.goodsProductInfoData.specInfoSwitch || false
      return retData
    }
  },
  provide () {
    return {
      stepData: this.stepData
    }
  },
  data () {
    return {
      reload: true,
      /* 为了能在子组件内部通过inject察觉到变化,默认情况inject不具有响应式 */
      stepData: {currentStep: 1},
      goodsProductInfoData: {},
      goodsDetailsData: {},
      goodsDistributionInfoData: {},
      qrCodeData: {
        imgFullUrl: null,
        isShow: false
      }
    }
  },
  methods: {
    reloadCmp () {
      this.reload = false
      this.$nextTick(() => {
        this.reload = true
        this.stepData.currentStep = 1
        this.goodsProductInfoData = {}
        this.goodsDetailsData = {}
        this.goodsDistributionInfoData = {}
        this.qrCodeData.imgFullUrl = null
        this.qrCodeData.isShow = false
      })
    },
    /* 顶部导航点击事件 */
    headerStepsClick (nextStep) {
      /* 隐藏商品主图选择弹框，这主要是这个弹框在使用上有点小bug(但是改起来非常困难) */
      this.$http.$emit('dtVisible', false)

      // 如果是从商品基本信息跳转验证基础信息正确性
      if (this.stepData.currentStep === 1) {
        // 数据验证失败，或者直接从第一步跳到第三步
        if (!this.$refs.goodsProductInfoCmp.validateFormData() || nextStep === 3) {
          return
        } else {
          // 验证成功，缓存对应的数据
          this.goodsProductInfoData = this.$refs.goodsProductInfoCmp.getFormData()
        }
      }
      // 从第三步跳转
      if (this.stepData.currentStep === 3) {
        if (!this.$refs.goodsDistributionInfoCmp.validateFormData()) {
          return
        }
      }

      this.stepData.currentStep = nextStep
    },
    /* 底部下一步,上一步点击事件 */
    footerStepsClick (step) {
      /* 隐藏商品主图选择弹框，这主要是这个弹框在使用上有点小bug(但是改起来非常困难) */
      this.$http.$emit('dtVisible', false)

      // 如果是从商品基本信息跳转验证基础信息正确性
      if (this.stepData.currentStep === 1) {
        if (!this.$refs.goodsProductInfoCmp.validateFormData()) {
          return
        } else {
          // 验证成功，缓存对应的数据
          this.goodsProductInfoData = this.$refs.goodsProductInfoCmp.getFormData()
        }
      }
      // 从第三步跳转
      if (this.stepData.currentStep === 3) {
        if (!this.$refs.goodsDistributionInfoCmp.validateFormData()) {
          return
        }
      }

      let nextStep = this.stepData.currentStep + step

      this.stepData.currentStep = nextStep < 1 ? 1 : nextStep > 3 ? 3 : nextStep
    },
    validateFormData () {
      if (!this.$refs.goodsProductInfoCmp.validateFormData()) {
        return false
      }
      if (!this.$refs.goodsDetailsCmp.validateFormData()) {
        return false
      }
      if (!this.$refs.goodsDistributionInfoCmp.validateFormData()) {
        return false
      }
      return true
    },
    _saveGoods () {
      let isOk = this.validateFormData()
      if (!isOk) {
        return null
      }
      let productInfoData = this.$refs.goodsProductInfoCmp.getFormData()
      debugger
      let goodsDetailsData = this.$refs.goodsDetailsCmp.getFormData()
      let distributionInfoData = this.$refs.goodsDistributionInfoCmp.getFormData()

      let retData = {
        ...productInfoData,
        ...goodsDetailsData,
        ...distributionInfoData
      }
      return retData
    },
    saveGoodsReturnList () {
      let goodsData = this._saveGoods()
      if (goodsData === null) {
        return
      }
      addGoodsApi(goodsData).then(res => {
        if (res.error !== 0) {
          this.$message({
            message: res.message,
            type: 'error'
          })
        } else {
          this.$router.push({name: 'soldOutGoods'})
        }
      })
    },
    saveGoodsContinueAdd () {
      let goodsData = this._saveGoods()
      if (goodsData === null) {
        return
      }
      addGoodsApi(goodsData).then(res => {
        if (res.error !== 0) {
          this.$message({
            message: res.message,
            type: 'error'
          })
        } else {
          this.reloadCmp()
        }
      })
    },
    saveGoodsView () {
      let goodsData = this._saveGoods()
      if (goodsData === null) {
        return
      }

      addGoodsApi(goodsData).then(res => {
        if (res.error !== 0) {
          this.$message({
            message: res.message,
            type: 'error'
          })
          throw new Error(res.message)
        } else {
          return res.content
        }
      }).then(goodsId => {
        return getGoodsQrCode(goodsId)
      }).then(res => {
        this.qrCodeData.imgFullUrl = res.content.imgFullUrl
        this.qrCodeData.isShow = true
      })
    },
    returnGoodsList () {
      this.$router.push({name: 'soldOutGoods'})
    }
  }
}
</script>
<style scoped>
  .addGoodsWrap {
    padding: 10px 10px;
    overflow-y: auto;
  }

  .addGoodsContent {
    background-color: white;
    padding: 10px 10px 100px 10px;
    position: relative;
  }

  .addingGoodsFooter {
    background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 50px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
  }
</style>
