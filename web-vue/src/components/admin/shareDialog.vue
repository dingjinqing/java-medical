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
    title="扫一扫，分享给好友吧~"
    width="30%"
    center
    :visible="show"
    @close="close"
  >
    <span>
      <div class="demo-image__error">
        <div class="block">
          <el-image :src="imgPath">
            <div
              slot="error"
              class="image-slot"
            >
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </div>
      </div>
    </span>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-row>
        <el-col :span="18">
          <el-input v-model="pagePath"></el-input>
        </el-col>
        <el-col :span="6">
          <span>复制</span>
        </el-col>
      </el-row>
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
    imgPath: {
      type: String
    },
    pagePath: {
      type: String
    },
    show: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    close () {
      this.$emit('close')
    }
  }
}
</script>
