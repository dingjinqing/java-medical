<!--营销活动分享弹框 -->
<!--
使用样例：
 <shareDialog
      :imgPath="shareImgPath"
      :pagePath="sharePagePath"
      :show="shareDialogShow"
      @close="shareDialogShow=false"
    />
  字段说明：
  shareImgPath：二维码图片路径
  sharePagePath: 分享页面url
  shareDialogShow:控制弹窗是否显示
 -->
<template>
  <el-dialog
    :title="$t('seckill.shareTitle')"
    :visible="show"
    width="350px"
    center
    :close-on-click-modal="false"
    @close="close"
  >
    <div class="shareBox">
      <div>
        <img
          :src="imgPath"
          alt=""
          style="width:160px;height:160px;"
        >
      </div>
      <div style="margin: 20px; 0">
        <a
          v-if="imgPath !== null"
          :href="imgPath"
          download
          style="color: #999;text-decoration: none;"
        >{{ $t('seckill.downLoad') }}</a>
        <a
          v-if="imgPath === null"
          href="javaScript:void(0);"
          style="color: #999;text-decoration: none;"
        >{{ $t('seckill.downLoadFail') }}</a>
      </div>
    </div>
    <div>
      <el-input v-model="pagePath">
        <el-button
          slot="append"
          v-clipboard:copy="pagePath"
          v-clipboard:success="copyHandler"
        >{{ $t('seckill.copy') }}</el-button>
      </el-input>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="close"
        size="small"
        style="margin-right: 10px"
      >{{ $t('seckill.cancel') }}</el-button>
      <el-button
        type="primary"
        size="small"
        @click="close"
      >{{ $t('seckill.sure') }}</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  props: {
    show: {
      type: Boolean,
      default: () => false
    },
    imgPath: {
      type: String
    },
    pagePath: {
      type: String
    }
  },
  methods: {
    // 复制
    copyHandler (e) {
      this.$message.success({ message: this.$t('seckill.copySuccess') })
    },
    // 关闭弹窗
    close () {
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss" scoped>
.shareBox {
  width: 100%;
  text-align: center;
  margin-bottom: 15px;
  border-bottom: 1px solid #ccc;
}
</style>
