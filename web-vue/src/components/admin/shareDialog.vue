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
    width="480px"
    center
    :close-on-click-modal="false"
    @close="close"
  >
    <div style="text-align: center;">
        <img
          :src="imgPath"
          alt=""
          style="width: 180px;height: 180px;border: none;"
          />
        <a
          v-if="imgPath !== null"
          :href="imgPath"
          download
         class="downLoadQrImg"
        >{{ $t('seckill.downLoad') }}</a>
        <a
          v-if="imgPath === null"
          href="javaScript:void(0);"
          class="downLoadQrImg"
        >{{ $t('seckill.downLoadFail') }}</a>
          <div style="text-align: left;padding: 5px 5px 5px 50px;">
            <el-input
              ref="qrCodePageUrlInput"
              v-model="pagePath"
              size="small"
              style="width:230px;"
            />
            <span
              @click="copyPageUrlClick"
              class="copyQrCodeUrl"
            >{{this.$t('allGoods.allGoodsData.copy')}}</span>
          </div>
      </div>
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
    },
    copyPageUrlClick () {
      this.$refs.qrCodePageUrlInput.select()
      document.execCommand('Copy')
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
.downLoadQrImg {
  color: #999;
  font-size: 16px;
  display: inline-block;
  height: 40px;
  line-height: 40px;
  width: 100%;
  text-align: center;
  margin-left: 0;
  border-bottom: 1px solid #eee;
  text-decoration: none;
}
.copyQrCodeUrl {
  display: inline-block;
  margin-left: 5px;
  color: #5a8bff;
  background: #fff;
  border: none;
  height: 35px;
  line-height: 35px;
  font-size: 16px;
  cursor: pointer;
}
</style>
