<template>
  <div class="riName">
    <div class="riNameMain">
      <!--模块私有区域-->
      <div class="main">
        <div class="list">
          <span>展示形式：</span>
          <el-radio
            v-model="modulesSaveData.show_types"
            label="0"
          >多行</el-radio>
          <el-radio
            v-model="modulesSaveData.show_types"
            label="1"
          >单行</el-radio>
          <el-radio
            v-model="modulesSaveData.show_types"
            label="2"
          >单行纵向</el-radio>
        </div>
        <div class="list">
          <span>标题文字：</span>
          <el-input
            v-model="modulesSaveData.form_title"
            size="small"
          ></el-input>
        </div>
        <div class="list">
          <span></span>
          <div class="tips">最多可输入20个字</div>
        </div>
        <div class="list">
          <span>提示语：</span>
          <el-input
            v-model="modulesSaveData.placeholder"
            size="small"
            placeholder="请输入提示语"
          ></el-input>
        </div>
        <div class="list">
          <span></span>
          <div class="tips">最多可输入20个字</div>
        </div>
        <div class="list">
          <span>条件验证：</span>
          <el-checkbox v-model="modulesSaveData.confirm">必填</el-checkbox>
        </div>
        <div class="list lastInput">
          <span></span>
          至少输入<el-input
            v-model="modulesSaveData.least_number"
            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
            size="small"
            placeholder="1"
          ></el-input>字
        </div>
        <div class="list lastInput">
          <span></span>
          至多输入<el-input
            v-model="modulesSaveData.least_number"
            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
            size="small"
            placeholder="500"
          ></el-input>字不可超过500字
        </div>
        <!--模块私有end-->
        <div class="sure">
          <el-button
            type="primary"
            size="small"
            @click="handleToClickSure()"
          >确定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
      modulesSaveData: {
        'show_types': '0',
        'form_title': '输入框',
        'placeholder': '',
        'confirm': 0,
        'least_number': 1,
        'most_number': 500,
        'ok_ajax': 1
      } // 模块保存数据
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (this.modulesData !== -1) {
          this.modulesSaveData = this.modulesData
        }
      },
      immediate: true
    },
    // 监听数据变换
    modulesSaveData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },
  methods: {
    // 点击确定按钮
    handleToClickSure () {
      this.modulesSaveData.ok_ajax = 1
      this.$message.success({
        message: '模块保存成功',
        showClose: true
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.riName {
  .riNameMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 20px 2%;
    .list {
      margin-bottom: 20px;
      span {
        display: inline-block;
        width: 100px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
      }
      display: flex;
      /deep/ .el-input {
        width: 220px;
      }
      .tips {
        color: #a7a7a7;
        font-size: 12px;
      }
      .iconContainer {
        background-size: 45% !important;
        background-position: center !important;
        width: 70px;
        height: 70px;
        border: 1px solid #e5e5e5;
        position: relative;
        .click_to_change {
          position: absolute;
          cursor: pointer;
          bottom: 0;
          width: 100%;
          text-align: center;
          color: #fff;
          background: rgba(0, 0, 0, 0.5);
          padding: 3px 0;
          font-size: 11px;
        }
        img {
          width: 100%;
        }
      }
      .iconTips {
        color: #a7a7a7;
        font-size: 12px;
        margin-top: 20px;
      }
      .iconSpan {
        align-items: flex-start;
      }
    }
    .lastInput {
      /deep/ .el-input {
        width: 80px;
      }
      display: flex;
      align-items: center;
    }
    .sure {
      display: flex;
      justify-content: center;
    }
    //end
  }
}
</style>
