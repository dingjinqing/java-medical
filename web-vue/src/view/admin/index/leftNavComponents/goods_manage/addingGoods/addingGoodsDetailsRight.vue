<template>
  <div class="addingGoodsDetailsRight">
    <section class="container">
      <el-form
        ref="form"
        :model="formData"
        label-width="110px"
      >
        <el-form-item
          label="商品基本信息"
          class="one"
        >
          <span style="color:#999;">商品信息为固定样式仅供参考，请以实际效果为准</span>
        </el-form-item>
        <el-form-item
          label="模块位置"
          class="one"
        >
          <el-radio
            v-model="radio"
            label="1"
          >自定义内容在上</el-radio>
          <el-radio
            v-model="radio"
            label="2"
          >商品详情在上</el-radio>
        </el-form-item>
        <section class="three one">
          <span class="text1"> 自定义内容</span>
          <el-divider></el-divider>
          <span class="text2">商品页模板</span>
          <el-button>选择模板</el-button>
          <el-button type="text">刷新</el-button>
          <el-button type="text">添加模板</el-button>
        </section>
        <section class="four one">
          <span class="text1"> 商品详情</span>
          <el-divider></el-divider>
          <tinymce
            id="d1"
            v-model="content"
          ></tinymce>
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
      radio: '1',
      content: ``

    }
  }
}
</script>
<style scoped>
.addingGoodsDetailsRight {
  width: 682px;
  height: 790px;
  background: #f8f8f8;
  padding: 15px;
  border: 1px solid #e5e5e5;
  color: #333;
}
.one {
  border: 1px solid #dddddd;
  background-color: #fff;
}
.three {
  margin-top: 30px;
  padding: 20px 0;
}
.four {
  padding: 20px 0;
  margin-top: 30px;
}
.text1 {
  padding-left: 30px;
  font-size: 14px;
  color: #606266;
}
.text2 {
  font-size: 14px;
  color: #606266;
  padding-left: 30px;
}
</style>
