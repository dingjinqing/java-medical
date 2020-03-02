<template>
  <div class="riName">
    <div class="riNameMain">
      <!--模块私有区域-->
      <div class="main">
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
          <span>展现形式：</span>
          <el-radio
            v-model="modulesSaveData.image_type"
            :label="1"
          >有图标</el-radio>
          <el-radio
            v-model="modulesSaveData.image_type"
            :label="0"
          >无图标</el-radio>
        </div>
        <!--展现形式选择有图标时显示的隐藏模块-->
        <div
          class="list"
          v-if="modulesSaveData.image_type===1"
        >
          <span class="iconSpan">图标：</span>
          <div class="icon">
            <div
              class="iconContainer"
              :style="`background:url(${$imageHost}/image/admin/shop_deco/name_change.png) no-repeat`"
            >
              <div
                class="click_to_change"
                @click="handleToImageDialog()"
              >更换图标</div>
              <img
                v-if="modulesSaveData.name_url"
                :src="modulesSaveData.name_url"
              >
            </div>
            <div class="iconTips">建议尺寸：36X36</div>
          </div>

        </div>
        <div class="list">
          <span>条件验证：</span>
          <el-checkbox v-model="modulesSaveData.confirm">必填</el-checkbox>
        </div>
        <!--模块私有end-->
        <div class="sure">
          <el-button
            type="primary"
            size="small"
          >确定</el-button>
        </div>
      </div>
    </div>
    <!--选择图片弹窗-->
    <ImageDalog
      pageIndex="pictureSpace"
      :tuneUp="imageTuneUp"
      :imageSize="[36,36]"
      @handleSelectImg="handleSelectImg"
    />
  </div>
</template>
<script>
import decMixins from '@/mixins/decorationModulesMixins/decorationModulesMixins'
export default {
  components: {
    ImageDalog: () => import('@/components/admin/imageDalog') // 选择图片弹窗
  },
  mixins: [decMixins],
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      imageTuneUp: false, // 图片选择弹窗调起
      modulesSaveData: {
        'form_title': '姓名',
        'image_type': 0,
        'name_url': '',
        'confirm': 0,
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
    // 选择弹窗调起
    handleToImageDialog () {
      this.imageTuneUp = !this.imageTuneUp
    },
    // 选择图片弹窗选中数据回传
    handleSelectImg (res) {
      console.log(res)
      this.modulesSaveData.name_url = res.imgUrl
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
