<template>
  <div class="term_main">
    <div class="term_content">
      <div class="left_content">
        <div class="title">
          <img
            :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'"
            alt=""
          >
        </div>
        <div
          class="showMsg"
          v-html="editMsg"
        >
        </div>
      </div>
      <div class="right_content">
        <el-form
          ref="form"
          label-width="100px"
          label-position="left"
        >
          <el-form-item label="服务条款配置"></el-form-item>
          <el-form-item label="条款内容：">
          </el-form-item>
        </el-form>
        <div class="innerRight">
          <editor
            v-model="editMsg"
            :disabled="disabled"
            ref="editor"
          />
        </div>
      </div>
    </div>
    <div class="save">
      <el-button
        size="samll"
        type="primary"
        @click="handleSave"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import editor from '@/components/admin/tinymceEditor/tinymceEditor'
import { termUpdate } from '@/api/admin/basicConfiguration/tradeConfiguration.js'

export default {
  components: {
    editor
  },
  data () {
    return {
      editMsg: null,
      disabled: false
    }
  },
  methods: {
    handleSave () {
      // termSelect().then(res => {
      //   // console.log(res)
      // })
      termUpdate().then(res => {

      })
    }
  }
}

</script>
<style lang="scss" scoped>
.term_main {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .term_content {
    padding: 40px 0 96px;
    display: flex;
    justify-content: center;
    background-color: #fff;
    .left_content {
      width: 330px;
      height: 600px;
      border: 1px solid #ededed;
    }
    .right_content {
      margin-left: 20px;
      border: 1px solid #e5e5e5;
      background: #f8f8f8;
      width: 550px;
      padding: 0 10px;
      border-radius: 4px;
      margin-bottom: 10px;
      .el-form-item {
        margin-bottom: 0;
      }
    }
  }
  .save {
    border-top: 1px solid #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 0;
    z-index: 2;
    right: 20px;
    left: 160px;
    height: 50px;
    background: #f8f8fa;
  }
}
</style>
