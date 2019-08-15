<template>
  <div class="addingGoodsDetailsRight">
    <section class="detail-edit">
      <el-form
        ref="form"
        :model="formData"
        label-width="100px"
      >
        <!-- 商品基本信息 -->
        <el-form-item label="商品基本信息">
          <span style="color:#999">商品信息为固定样式仅供参考，请以实际效果为准</span>
        </el-form-item>
        <!-- 模块位置 -->
        <el-form-item label="模块位置">
          <el-radio
            v-model="radio"
            label="1"
          >自定义内容在上</el-radio>
          <el-radio
            v-model="radio"
            label="2"
          >商品详情在上</el-radio>
        </el-form-item>
        <!-- 自定义内容 -->
        <el-form-item label="自定义内容">
          <label for="">商品页模板</label>
          <el-button
            size="small "
            class="choose"
          >选择模板</el-button>
          <el-link
            class="refresh"
            type="primary"
            :underline="false"
          > 刷新</el-link>
          <el-link
            type="primary"
            :underline="false"
          >添加模板</el-link>
        </el-form-item>
        <!-- 商品详情 -->
        <section class="goodsDetails">
          <p class="goodsDetails_top">商品详情</p>

        </section>

      </el-form>
    </section>
  </div>
</template>
<script>
export default {
  name: 'addingGoodsDetailsRight',
  data () {
    return {
      formData: {

      },
      radio: '1'
    }
  }
}
</script>
<style scoped>
</style>
