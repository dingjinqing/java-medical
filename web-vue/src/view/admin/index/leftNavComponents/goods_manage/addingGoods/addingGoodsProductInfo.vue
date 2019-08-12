<template>
  <div class="addingGoodsProductInfo">
    <!-- 基本信息 -->
    <section class="title">
      <span>基本信息</span>
    </section>
    <basicInfo />
    <!--库存/价格信息-->
    <section class="title">
      <span>库存/价格信息</span>
    </section>
    <priceInfo />
    <!-- 配送信息 -->
    <section class="title">
      <span>配送信息</span>
    </section>
    <deliveryInfo />
    <!-- 其他信息 -->
    <section class="title">
      <span>其他信息</span>
    </section>
    <otherInfo />
    <!-- 底部 -->
    <div class="addingGoodsFooter">
      <section class="addingGoodsFooter">
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handleToList"
        >保存后返回列表</el-button>
        <el-button
          size="small"
          @click.native.prevent="handleNextStep"
        >下一步</el-button>
      </section>
    </div>
  </div>
</template>
<script>
import basicInfo from './basicInfo'
import pictureTemplate from '@/components/admin/pictureTemplate/pictureTemplateContent'
import otherInfo from './otherInfo'
import deliveryInfo from './deliveryInfo'
import priceInfo from './priceInfo'
export default {
  components: { basicInfo, priceInfo, pictureTemplate, otherInfo, deliveryInfo },
  props: {
    active: Number
  },
  data () {
    return {
    }
  },
  methods: {
    // 底部按钮跳转
    handleToList () {
      console.log('tolist')
    },
    handleNextStep () {
      this.$emit('toSecondPage')
    }
  }
}
</script>
<style scoped>
.title {
  font-weight: bold;
  height: 40px;
  background: #f8f8f8;
  line-height: 40px;
  width: 100%;
  padding-left: 10px;
  margin-top: 20px;
}
.addingGoodsFooter {
  position: fixed;
  bottom: 0;
  width: 1093px;
  padding: 10px;
  margin-left: -16px;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  z-index: 2;
  background: #f8f8fa;
  display: block;
}
</style>
