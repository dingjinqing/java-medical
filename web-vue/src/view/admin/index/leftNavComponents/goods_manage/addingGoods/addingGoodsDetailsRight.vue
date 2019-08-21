<template>
  <div class="addingGoodsDetailsRight">
    <section class="container">
      <!-- form 表单布局 -->
      <el-form
        ref="form"
        :model="formData"
        label-width="110px"
      >
        <!-- 商品基本信息  -->
        <el-form-item
          label="商品基本信息"
          class="basic_info"
        >
          <span style="color:#999;">商品信息为固定样式仅供参考，请以实际效果为准</span>
        </el-form-item>
        <!-- 模块位置 -->
        <el-form-item
          label="模块位置"
          class="module_location"
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
        <section class="conAndTem">
          <!-- 自定义内容 -->
          <el-form-item label="自定义内容">
          </el-form-item>
          <!-- 商品页模板 -->
          <el-form-item label="商品页模板">
            <el-button
              size="small"
              @click="handleSelectTemplate"
            >选择模板</el-button>
            <el-button type="text">刷新</el-button>
            <el-button type="text">添加模板</el-button>
          </el-form-item>
        </section>
        <section class="goods_detail">
          <!-- 商品详情 -->
          <el-form-item label="商品详情">
          </el-form-item>
          <!-- 富文本编辑器 -->
          <section class="tinymceWrap">
            <tinymceEditor
              v-model="msg"
              :disabled="disabled"
              @onClick="onClick"
              ref="editor"
            />
          </section>
        </section>
      </el-form>
      <goodsPageTemplate
        :visible="visible"
        @close="handleClose"
      />
    </section>

  </div>
</template>
<script>
// import { shopDecorateList } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
import goodsPageTemplate from './goodsPageTemplate'
import tinymceEditor from '@/components/admin/tinymceEditor/tinymceEditor'
export default {
  name: 'addingGoodsDetailsRight',
  components: { goodsPageTemplate, tinymceEditor },
  data () {
    return {
      formData: {

      },
      radio: '1',
      visible: false,
      msg: '请输入商品详情……',
      disabled: false

    }
  },
  methods: {
    handleClose () {
      this.visible = false
    },
    // 鼠标单击的事件
    onClick (e, editor) {

    },
    // 清空内容
    clear () {
      this.$refs.editor.clear()
    },
    // 选择商品页模板
    handleSelectTemplate () {
      this.visible = true
    }
  }
}
</script>
<style scoped>
.addingGoodsDetailsRight {
  width: 682px;
  min-height: 790px;
  background: #f8f8f8;
  padding: 15px;
  border: 1px solid #e5e5e5;
  color: #333;
}
.basic_info {
  border: 1px solid #dddddd;
  background-color: #fff;
}
.module_location {
  border: 1px solid #dddddd;
  background-color: #fff;
}
.conAndTem {
  border: 1px solid #dddddd;
  background-color: #fff;
}
.goods_detail {
  border: 1px solid #dddddd;
  background-color: #fff;
}
</style>
