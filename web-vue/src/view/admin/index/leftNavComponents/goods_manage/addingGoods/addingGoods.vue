<template>
  <div class="addGoodsWrap">
    <div
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
          title="编辑商品信息"
          icon="el-icon-edit"
          @click.native="headerStepsClick(1)"
          style="cursor: pointer"
        />
        <el-step
          title="编辑商品详情"
          icon="el-icon-edit"
          @click.native="headerStepsClick(2)"
          style="cursor: pointer"
        />
        <el-step
          title="编辑分销信息"
          icon="el-icon-edit"
          @click.native="headerStepsClick(3)"
          style="cursor: pointer"
        />
      </el-steps>

      <!-- 主要内容区 -->
      <!--商品信息-->
        <addingGoodsProductInfo
          ref="goodsProductInfoCmp"
          v-show="stepData.currentStep===1"
        />
      <!--商品详情-->
        <addingGoodsDetails
          :goodsProductInfoData="goodsProductInfoDataForDetails"
          ref="goodsDetailsCmp"
          v-show="stepData.currentStep===2"
        />
      <!--商品分销信息-->
        <addingGoodsDistributionInfo
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
          @click="saveGoods"
        >保存后返回列表
        </el-button>
        <el-button
          class="btn"
          size="small"
          @click="footerStepsClick(-1)"
          v-show="stepData.currentStep!==1"
        >上一步
        </el-button>
        <el-button
          class="btn"
          size="small"
          @click="footerStepsClick(1)"
          v-show="stepData.currentStep!==3"
        >下一步
        </el-button>
        <el-button
          class="btn"
          size="small"
          type="primary"
          v-show="stepData.currentStep!==1"
        >保存后继续添加
        </el-button>
        <el-button
          class="btn"
          size="small"
          type="primary"
          v-show="stepData.currentStep!==1"
        >保存后预览商品
        </el-button>
      </div>
    </div>
  </div>
</template>
<script>
/* 导入组件 */
import addingGoodsProductInfo from './addingGoodsProductInfo'
import addingGoodsDetails from './addingGoodsDetails'
import addingGoodsDistributionInfo from './addingGoodsDistributionInfo'

/* 导入js组件 */
import {addGoodsApi} from '@/api/admin/goodsManage/addingGoods/addingGoods'

export default {
  name: 'addingGoods',
  components: {addingGoodsProductInfo, addingGoodsDetails, addingGoodsDistributionInfo},
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
      /* 为了能在子组件内部通过inject察觉到变化,默认情况inject不具有响应式 */
      stepData: {currentStep: 1},
      goodsProductInfoData: {},
      goodsDetailsData: {},
      goodsDistributionInfoData: {}
    }
  },
  methods: {
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
    saveGoods () {
      let isOk = this.validateFormData()
      if (!isOk) {
        return
      }

      let productInfoData = this.$refs.goodsProductInfoCmp.getFormData()
      let goodsDetailsData = this.$refs.goodsDetailsCmp.getFormData()
      let distributionInfoData = this.$refs.goodsDistributionInfoCmp.getFormData()

      let retData = {
        ...productInfoData,
        ...goodsDetailsData,
        ...distributionInfoData
      }

      addGoodsApi(retData).then(res => {
        if (res.error !== 0) {
          this.$message({
            message: res.message,
            type: 'error'
          })
        } else {
          this.$router.push({path: '/admin/home/main/goodsManage/goodsForSale'})
        }
      })
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
