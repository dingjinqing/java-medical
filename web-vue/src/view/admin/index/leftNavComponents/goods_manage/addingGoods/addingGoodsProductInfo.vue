<template>
  <div class="addingGoodsProductInfo">
    <!-- 基本信息 -->
    <section class="title">
      <span>基本信息</span>
    </section>
    <basicInfo ref="basicInfo" />
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
    <!-- 底部按钮组 -->
    <section class="addingGoodsFooter">
      <el-button
        class="btn"
        type="primary"
        size="small"
        @click.native.prevent="handleToList"
      >保存后返回列表</el-button>
      <el-button
        class="btn"
        size="small"
        @click.native.prevent="handleNextStep"
      >下一步</el-button>
    </section>

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
      formData: ''
    }
  },
  methods: {
    // 底部按钮跳转
    handleToList () {
      console.log('tolist')
    },
    handleNextStep () {
      console.log(this.$refs.basicInfo.getFormData)
      this.formData = this.$refs.basicInfo.getFormData
      console.log(this.formData)
      this.$router.push(
        { name: 'details', query: this.formData }
      )
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
.addingGoodsProductInfo {
  position: relative;
}
.addingGoodsFooter {
  border-top: 1px solid #f2f2f2;
  background-color: pink;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.btn {
  margin: 0 10px;
}
</style>
