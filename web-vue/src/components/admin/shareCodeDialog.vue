<template>
  <div>
    <div class="shareC">
      <el-dialog
        :title="$t('memberCard.scanCode')"
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
          >{{$t('memberCard.downQrcode')}}</div>
        </div>

        <div class="d_footer">
          <div
            slot="footer"
            class="dialogFooter"
          >
            <el-input
              v-model="pathInput"
              :placeholder="$t('memberCard.pleaseInput')"
              size="mini"
              ref="copy"
            ></el-input>
            <span
              style="cursor:pointer"
              @click="clickCopy()"
            >{{$t('memberCard.copy')}}</span>
          </div>
        </div>

      </el-dialog>
    </div>
  </div>
</template>
<script>
import { getShareCode } from '@/api/admin/memberManage/memberCard.js'
export default {
  data () {
    return {
      dialogVisibleShare: false,
      shareImg: null,
      pathInput: null
    }
  },
  mounted () {
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  watch: {
    lang () {

    }
  },
  methods: {
    defaultData () {
      this.$http.$on('shareCodeDialog', res => {
        console.log(res.id)
        this.downs(res.id)
      })
    },
    // 下载图片
    downs (cardId) {
      getShareCode(cardId).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.warning(this.$t('memberCard.shareCodeSuccess'))
          this.shareImg = res.content.imageUrl
          this.pathInput = res.content.pagePath
          this.dialogVisibleShare = true
        } else {
          this.$message.warning(this.$t('memberCard.shareCodeFail'))
        }
      })
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
