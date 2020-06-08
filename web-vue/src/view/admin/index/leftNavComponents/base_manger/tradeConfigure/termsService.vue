<!--
* 交易配置 - 服务条款页面
* @author: zhaoxin
-->
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
          <!-- 编辑器 -->
          <TinymceEditor
            v-model="editMsg"
            :disabled="disabled"
          />
        </div>
      </div>
    </div>
    <div class="save">
      <el-button
        size="small"
        type="primary"
        @click="handleSave"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import TinymceEditor from '@/components/admin/tinymceEditor/tinymceEditor'
import { termUpdate, termSelect } from '@/api/admin/basicConfiguration/tradeConfiguration.js'

export default {
  components: {
    TinymceEditor
  },
  mounted () {
    this.initData()
  },
  data () {
    return {
      editMsg: '',
      disabled: false
    }
  },
  methods: {
    handleSave () {
      termUpdate({ service_document: this.editMsg }).then(res => {
        if (res.error === 0) {
          this.$message.success('添加成功')
        }
      })
    },
    initData () {
      termSelect().then(res => {
        console.log(res)
        this.editMsg = res.content
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
      .title > img {
        width: 100%;
      }
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
