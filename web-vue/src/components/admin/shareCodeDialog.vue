<template>
  <div>
    <div class="shareC">
      <el-dialog
        title="扫一扫，分享给好友吧~"
        :visible.sync="dialogVisibleShare"
        width="25%"
        :modal='false'
      >
        <div class="shareDialog_">
          <div class="shareDialog_content_"><img
              style="height:160px"
              :src="shareImg"
            ></div>
          <div
            class="shareDialog_bottom_"
            @click="downs()"
          >下载二维码</div>
        </div>

        <div class="d_footer">
          <div
            slot="footer"
            class="dialogFooter"
          >
            <el-input
              v-model="pathInput"
              placeholder="请输入内容"
              size="mini"
              ref="copy"
            ></el-input>
            <span
              style="cursor:pointer"
              @click="clickCopy()"
            >复制</span>
          </div>
        </div>

      </el-dialog>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      dialogVisibleShare: false,
      shareImg: 'http://mpdev.weipubao.cn/upload/4748160/qrcode/33/T33P307bfc9947d3756c206033bd06eb13b0_20190614100251.jpg',
      pathInput: ''
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$http.$on('shareCodeDialog', res => {
        console.log(res)
        this.dialogVisibleShare = true
      })
    },
    // 下载图片
    downs () {
      // var alink = document.createElement('a')
      // alink.href = this.shareImg
      // alink.download = name || 'pic' // 图片名
      // alink.click()
    },
    // 复制
    clickCopy () {
      this.$refs.copy.select() // 选择对象
      document.execCommand('Copy') // 执行浏览器复制命令
    }
  }
}
</script>
<style lang="scss" scoped>
.shareC {
  .shareDialog_ {
    .shareDialog_content_ {
      display: flex;
      justify-content: center;
      img {
        width: 160px;
      }
    }
    .shareDialog_bottom_ {
      text-align: center;
      margin-top: 18px;
      cursor: pointer;
    }
  }
  .d_footer {
    margin-top: 10px;
    .dialogFooter {
      width: 100%;
      display: flex;
      /deep/ .el-input {
        width: 80%;
      }

      span {
        margin-left: 20px !important;
        color: #5a8bff;
        height: 28px;
        line-height: 28px;
      }
    }
  }
}
</style>
