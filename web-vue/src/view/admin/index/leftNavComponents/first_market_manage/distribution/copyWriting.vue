<template>
  <div class="container">
    <div class="main">
      <div class="titleTip">
        <i
          class="el-icon-warning"
          style="color: #E6A23C;"
        ></i>
        <span>{{ $t('distribution.pageTip') }}</span>
      </div>
      <div class="content">
        <div class="leftContent">
          <div class="leftName">{{ form.title }}</div>
          <img
            class="leftTitle"
            src="http://mpdevimg2.weipubao.cn/image/admin/shop_beautify/phone_tops.png"
            alt=""
          >
          <div
            class="leftPass"
            v-html="form.document"
          ></div>
        </div>
        <div class="rightContent">
          <el-form
            ref="form"
            :model="form"
            label-width="100px"
          >
            <el-form-item :label="$t('distribution.shareAddress') + '：'">
              <span>{{ pageText }}</span>
              <span
                class="text"
                v-clipboard:copy="pageText"
                v-clipboard:success="copyHandler"
              >{{ $t('distribution.writingCopy') }}</span>
              <span
                class="text"
                @click="shareHandler"
              >{{ $t('distribution.writingShare') }}</span>
            </el-form-item>
            <el-form-item :label="$t('distribution.writingTitle') + '：'">
              <el-input
                :placeholder="$t('distribution.writingTitleTip')"
                maxlength="20"
                show-word-limit
                size="small"
                v-model="form.title"
                style="width: 170px;"
              ></el-input>
            </el-form-item>
            <el-form-item :label="$t('distribution.writingContent') + '：'">
              <span
                class="template"
                @click="templateCopyHandler"
              >{{ $t('distribution.writingTemplate') }}</span>
              <TinymceEditor v-model="form.document" />
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>

    <!-- 底部 -->
    <div class="footer">
      <el-button
        size="small"
        type="primary"
        @click="saveClickHandler"
      >{{ $t('seckill.save') }}</el-button>
    </div>

    <!-- 分享弹窗 -->
    <ShareDialog
      :show="shareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="shareDialog=false"
    />
  </div>
</template>

<script>
import { setDocument, getDocument } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor'), // 富文本编辑器
    ShareDialog: () => import('@/components/admin/shareDialog') // 分享弹窗
  },
  data () {
    return {
      pageText: 'pages/distributionspread/distributionspread',
      form: {
        title: '分销员推广测试',
        document: '' // 文本内容
      },
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',

      templateFlag: false,
      contentTip1: '',
      contentTip2: '',
      contentText: ''
    }
  },
  watch: {
    lang () {
      this.contentTip1 = this.$t('distribution.contentTip1')
      this.contentTip2 = this.$t('distribution.contentTip2')
      this.contentText = this.$t('distribution.contentText')
      this.getClickHandler()
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.getClickHandler()
  },
  methods: {
    // 获取
    getClickHandler () {
      getDocument().then((res) => {
        if (res.error === 0) {
          if (res.content) {
            this.form = res.content
          } else {
            if (this.templateFlag === true) {
              this.form.document = this.contentTip1 + this.contentText
            } else {
              this.form.document = this.contentTip2 + this.contentText
            }
          }
          this.$message.success('获取推广文案')
        }
      })
    },

    // 保存
    saveClickHandler () {
      console.log(this.form)
      setDocument(this.form).then((res) => {
        if (res.error === 0) {
          this.$message.success(this.$t('distribution.rebateSaveSuccess'))
        }
      })
    },

    // 使用模板文案
    templateCopyHandler () {
      this.templateFlag = true
      this.form.document = this.contentTip1 + this.contentText
    },

    // 复制
    copyHandler (e) {
      this.$message.success({ message: this.$t('seckill.copySuccess') })
    },

    // 分享
    shareHandler () {
      this.shareDialog = !this.shareDialog
      // shareSeckillList(id).then((res) => {
      //   if (res.error === 0) {
      //     this.shareImg = res.content.imageUrl
      //     this.sharePath = res.content.pagePath
      //   }
      // })
    }
  }

}

</script>
<style scoped>
.container {
  width: 100%;
  padding: 10px;
}

.main {
  background: #fff;
  padding: 10px;
  overflow: hidden;
  margin-bottom: 80px;
}

.titleTip {
  padding: 10px 0;
  width: 950px;
  margin: 0 auto;
  text-align: center;
  color: #666;
  font-size: 12px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
}

.content {
  width: 950px;
  margin: 0 auto;
  background: #fff;
  padding: 10px 0;
  padding-bottom: 0;
}

.leftContent {
  position: relative;
  float: left;
  width: 330px;
  height: 600px;
  border: 1px solid #ededed;
}

.leftContent .leftName {
  width: 100%;
  position: absolute;
  top: 0;
  height: 55px;
  color: #fff;
  text-align: center;
  line-height: 70px;
}

.leftContent .leftTitle {
  width: 330px;
  height: 55px;
}

.leftContent .leftPass {
  width: 330px;
  height: 540px;
  overflow-y: auto;
  padding: 0 8px;
}

.rightContent {
  float: right;
  width: 600px;
  padding: 10px 20px;
  background: #f8f8f8;
  border: 1px solid #ededed;
}

span {
  margin-right: 10px;
}

.text {
  color: #5a8bff;
  cursor: pointer;
}

.template {
  color: #5a8bff;
  margin-bottom: 5px;
  cursor: pointer;
}

.footer {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
</style>
