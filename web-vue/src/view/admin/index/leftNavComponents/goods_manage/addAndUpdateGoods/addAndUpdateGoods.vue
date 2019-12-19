<template>
  <div class="goodsWrap">
    <div
      v-if="reload"
      id="goodsDiv"
      class="goodsContent"
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
      <addAndUpdateGoodsProductInfo
        ref="goodsProductInfoCmp"
        v-show="stepData.currentStep===1"
      />
      <!--商品详情-->
      <addAndUpdateGoodsDetails
        :goodsProductInfoData="goodsProductInfoDataForDetails"
        ref="goodsDetailsCmp"
        v-show="stepData.currentStep===2"
      />
      <!--商品分销信息-->
      <addAndUpdateGoodsDistributionInfo
        :goodsProductInfoData="goodsProductInfoDataForDistribution"
        ref="goodsDistributionInfoCmp"
        v-show="stepData.currentStep===3"
      />

      <!-- 底部按钮组件 -->
      <div class="goodsFooter">
        <el-button
          class="btn"
          type="primary"
          size="small"
          @click="saveOrUpdateGoodsReturnList"
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
          @click="saveOrUpdateGoodsContinueAdd"
          v-show="stepData.currentStep!==1"
        >{{$t('goodsAddEditInfo.saveAndAdd')}}
        </el-button>
        <el-button
          class="btn"
          size="small"
          type="primary"
          @click="saveOrUpdateGoodsView"
          v-show="stepData.currentStep!==1"
        >{{$t('goodsAddEditInfo.saveAndReview')}}
        </el-button>
      </div>

      <!--预览商品太阳码-->
      <el-dialog
        :visible.sync="qrCodeData.isShow"
        :title="$t('goodsAddEditInfo.goodsAppView')"
        width="350px"
      >
        <div style="text-align: center;">
          <el-image
            fit="scale-down"
            :src="qrCodeData.imgFullUrl"
            style="width: 250px; height: 230px;"
          />
        </div>
        <div
          slot="footer"
          style="text-align: center;"
        >
          <el-button
            @click="reloadCmp"
            type="primary"
          >{{$t('goodsAddEditInfo.continueAdd')}}</el-button>
          <el-button @click="returnGoodsList">{{$t('goodsAddEditInfo.returnToList')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
/* 导入组件 */
import addAndUpdateGoodsProductInfo from './addAndUpdateGoodsProductInfo'
import addAndUpdateGoodsDetails from './addAndUpdateGoodsDetails'
import addAndUpdateGoodsDistributionInfo from './addAndUpdateGoodsDistributionInfo'

/* 导入js组件 */
import { addGoodsApi, updateGoodsApi, selectGoodsApi, getGoodsQrCode } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'

export default {
  name: 'addAndUpdateGoods',
  components: { addAndUpdateGoodsProductInfo, addAndUpdateGoodsDetails, addAndUpdateGoodsDistributionInfo },
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
      isUpdateWrap: this.isUpdateWrap
    }
  },
  beforeRouteEnter (to, from, next) {
    next(vm => {
      // 组件实例被复用的情况
      if ((from.name === 'goods_add' || from.name === 'goods_update') && (to.name === 'goods_add' || to.name === 'goods_update')) {
        vm.reloadCmp()
      }
    })
  },
  data () {
    return {
      reload: true,
      /* 为了能在子组件内部通过inject察觉到变化,默认情况inject不具有响应式 */
      stepData: { currentStep: 1 },
      isUpdateWrap: { isUpdate: false, updateGoodsId: null, isCopy: 0 },
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
    /* 手动卸载组件并重新装载 */
    reloadCmp () {
      this.reload = false
      this.$nextTick(() => {
        this.reload = true
        this.stepData.currentStep = 1
        this.isUpdateWrap.isUpdate = false
        this.isUpdateWrap.updateGoodsId = null
        this.isUpdateWrap.isCopy = 0
        this.goodsProductInfoData = {}
        this.goodsDetailsData = {}
        this.goodsDistributionInfoData = {}
        this.qrCodeData.imgFullUrl = null
        this.qrCodeData.isShow = false
        this.$nextTick(() => {
          /* 触发重新装载 */
          this._mounted()
        })
      })
    },
    /* 顶部导航点击事件 */
    headerStepsClick (nextStep) {
      /* 隐藏商品主图选择弹框，这主要是这个弹框在使用上有点小bug(但是改起来非常困难) */
      this.$http.$emit('dtVisible', false)

      // 如果是从商品基本信息跳转验证基础信息正确性
      if (this.stepData.currentStep === 1) {
        // 数据验证失败，或者直接从第一步跳到第三步
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
    _validateFormData () {
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
    _getGoodsData () {
      let isOk = this._validateFormData()
      if (!isOk) {
        return null
      }
      if (this.isUpdateWrap.isCopy === 1) {
        this._disposeDataForCopy()
      }
      let productInfoData = this.$refs.goodsProductInfoCmp.getFormData()
      let goodsDetailsData = this.$refs.goodsDetailsCmp.getFormData()
      let distributionInfoData = this.$refs.goodsDistributionInfoCmp.getFormData()

      let retData = {
        ...productInfoData,
        ...goodsDetailsData,
        ...distributionInfoData
      }
      retData.goodsId = this.isUpdateWrap.updateGoodsId

      return retData
    },
    /* 处理复制操作的数据 */
    _disposeDataForCopy () {
      this.isUpdateWrap.isUpdate = false
      this.$refs.goodsProductInfoCmp.disposeDataForCopy()
      this.$refs.goodsDetailsCmp.disposeDataForCopy()
      this.$refs.goodsDistributionInfoCmp.disposeDataForCopy()
    },
    /* 保存或修改并返回列表 */
    saveOrUpdateGoodsReturnList () {
      let goodsData = this._getGoodsData()
      if (goodsData === null) {
        return
      }
      let executeFunc = this.isUpdateWrap.isUpdate ? updateGoodsApi : addGoodsApi

      executeFunc(goodsData).then(res => {
        if (res.error !== 0) {
          this.$message.error({
            message: res.message,
            type: 'error'
          })
        } else {
          this.$router.push({ name: 'goodsForSale' })
        }
      })
    },
    /* 保存或修改并继续添加 */
    saveOrUpdateGoodsContinueAdd () {
      let goodsData = this._getGoodsData()
      if (goodsData === null) {
        return
      }
      let executeFunc = this.isUpdateWrap.isUpdate ? updateGoodsApi : addGoodsApi

      executeFunc(goodsData).then(res => {
        if (res.error !== 0) {
          this.$message.error({
            message: res.message,
            type: 'error'
          })
        } else {
          this.$router.push({ name: 'goods_add' })
          this.reloadCmp()
        }
      })
    },
    /* 保存或修改并查看商品预览 */
    saveOrUpdateGoodsView () {
      let goodsData = this._getGoodsData()
      if (goodsData === null) {
        return
      }
      let executeFunc = this.isUpdateWrap.isUpdate ? updateGoodsApi : addGoodsApi

      executeFunc(goodsData).then(res => {
        if (res.error !== 0) {
          this.$message.error({
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
      this.$router.push({ name: 'soldOutGoods' })
    },
    /* 初始化待修改商品数据 */
    _initDataForUpdate (goodsId) {
      selectGoodsApi({ goodsId: goodsId }).then(res => {
        if (res.error !== 0) {
          this.$message.error({
            type: 'error',
            message: this.$t('goodsAddEditInfo.warningInfo.serverWrong') + res.error + this.$t('goodsAddEditInfo.warningInfo.pleaseConnectUs')
          })
        } else {
          let goodsData = res.content
          if (this.isUpdateWrap.isCopy === 1) {
            this._disposeDataForCopyInit(goodsData)
          }
          this.isUpdateWrap.updateGoodsId = goodsData.goodsId
          this.$refs.goodsProductInfoCmp.initDataForUpdate(goodsData)
          this.$refs.goodsDetailsCmp.initDataForUpdate(goodsData)
          this.$refs.goodsDistributionInfoCmp.initDataForUpdate(goodsData)
        }
      })
    },
    /* 初始化复制数据时处理数据 */
    _disposeDataForCopyInit (goodsData) {
      goodsData.goodsId = null
      goodsData.goodsName = goodsData.goodsName + '-'
      goodsData.goodsSn = null
    },
    /* 新增数据时数据初始化 */
    _initDataForInsert () {
      this.$refs.goodsProductInfoCmp.initDataForInsert()
      this.$refs.goodsDetailsCmp.initDataForInsert()
      this.$refs.goodsDistributionInfoCmp.initDataForInsert()
    },
    /* 页面装载执行函数 */
    _mounted () {
      let goodsId = this.$route.params.goodsId

      let isCopy = this.$route.params.isCopy
      if (isCopy === 1) {
        this.isUpdateWrap.isUpdate = true
        this.isUpdateWrap.isCopy = 1
        this._initDataForUpdate(goodsId)
        return
      }

      if (goodsId === undefined || goodsId === null) {
        this.isUpdateWrap.isUpdate = false
        this._initDataForInsert()
      } else {
        this.isUpdateWrap.isUpdate = true
        this._initDataForUpdate(goodsId)
      }
    }
  },
  mounted () {
    // 页面有其他页面跳转过来
    this._mounted()
  }
}
</script>
<style scoped>
.goodsWrap {
  padding: 10px 10px;
  overflow-y: auto;
}

.goodsContent {
  background-color: white;
  padding: 10px 10px 100px 10px;
  position: relative;
}

.goodsFooter {
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
