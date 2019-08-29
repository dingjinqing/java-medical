<template>
  <div class="addGoodsWrap">
    <div id="addGoods" class="addGoodsContent">
      <!-- 头部导航 headerSteps-->
      <el-steps :active="currentStep" finish-status="finish" simple>
        <el-step title="编辑商品信息" icon="el-icon-edit" @click.native="headerStepsClick(1)" style="cursor: pointer"/>
        <el-step title="编辑商品详情" icon="el-icon-edit" @click.native="headerStepsClick(2)" style="cursor: pointer"/>
        <el-step title="编辑分销信息" icon="el-icon-edit" @click.native="headerStepsClick(3)" style="cursor: pointer"/>
      </el-steps>

      <!-- 主要内容区 -->
      <addingGoodsProductInfo ref="goodsProductInfoCmp" v-show="currentStep===1"/>
      <addingGoodsDetails ref="goodsDetailsCmp" v-show="currentStep===2"/>
      <addingGoodsDistributionInfo ref="goodsDistributionInfoCmp" v-show="currentStep==3"/>

      <!-- 底部按钮组件 -->
      <div class="addingGoodsFooter">
        <el-button class="btn" type="primary" size="small">保存后返回列表</el-button>
        <el-button class="btn" size="small" @click="footerStepsClick(-1)" v-show="currentStep!==1">上一步</el-button>
        <el-button class="btn" size="small" @click="footerStepsClick(1)" v-show="currentStep!==3">下一步</el-button>
        <el-button class="btn"  size="small" type="primary" v-show="currentStep!==1">保存后继续添加</el-button>
        <el-button class="btn"  size="small" type="primary" v-show="currentStep!==1">保存后预览商品</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import addingGoodsProductInfo from './addingGoodsProductInfo'
import addingGoodsDetails from './addingGoodsDetails'
import addingGoodsDistributionInfo from './addingGoodsDistributionInfo'

export default {
  name: 'addingGoods',
  components: { addingGoodsProductInfo, addingGoodsDetails, addingGoodsDistributionInfo },
  data () {
    return {
      currentStep: 1,
      goodsProductInfoData: {},
      goodsDetailsData: {},
      goodsDistributionInfoData: {}
    }
  },
  methods: {
    headerStepsClick (curStep) {
      // TODO:数据正确性验证
      this.currentStep = curStep
    },
    footerStepsClick (step) {
      let offsetStep = this.currentStep + step

      if (offsetStep === 2) {
        // TODO:验证1号数据
      }

      if (offsetStep === 3) {
        // TODO:验证2号数据
      }

      this.currentStep = offsetStep < 1 ? 1 : offsetStep > 3 ? 3 : offsetStep
    }
  },
  mounted () {
  }
}
</script>
<style scoped>
.addGoodsWrap {
  padding: 10px 10px;
  overflow-y: auto;
}
.addGoodsContent{
  background-color: white;
  padding: 10px 10px 100px 10px;
  position: relative;
}
.addingGoodsFooter{
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
