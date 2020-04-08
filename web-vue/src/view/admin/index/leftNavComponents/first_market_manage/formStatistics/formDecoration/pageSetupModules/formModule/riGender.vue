<template>
  <div class="riName">
    <div class="riNameMain">
      <!--模块私有区域-->
      <div class="main">
        <div class="list">
          <span>{{$t('formDecorationModel.presentation')}}</span>
          <el-radio
            v-model="modulesSaveData.show_types"
            :label="0"
          >{{$t('formDecorationModel.transverse')}}</el-radio>
          <el-radio
            v-model="modulesSaveData.show_types"
            :label="1"
          >{{$t('formDecorationModel.portrait')}}</el-radio>
        </div>
        <div class="list">
          <span>{{$t('formDecorationModel.titleText')}}</span>
          <el-input
            v-model="modulesSaveData.form_title"
            size="small"
          ></el-input>
        </div>
        <div class="list">
          <span></span>
          <div class="tips">{{$t('formDecorationModel.titleTextTip')}}</div>
        </div>
        <div class="list">
          <span>{{$t('formDecorationModel.conditionValidation')}}</span>
          <el-checkbox v-model="modulesSaveData.confirm">{{$t('formDecorationModel.mustFill')}}</el-checkbox>
        </div>
        <!--模块私有end-->
        <div class="sure">
          <el-button
            type="primary"
            size="small"
            @click="handleToClickSure()"
          >{{$t('formDecorationModel.determine')}}</el-button>
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
        'form_title': '性别',
        'show_types': 0,
        'confirm': 0,
        'ok_ajax': 0
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
        message: this.$t('formDecorationModel.savedSuccessfully'),
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
    .sure {
      display: flex;
      justify-content: center;
    }
    //end
  }
}
</style>
