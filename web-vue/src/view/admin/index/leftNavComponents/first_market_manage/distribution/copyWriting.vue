<template>
  <div class="container">
    <div class="main">
      <div class="titleTip">
        <i
          class="el-icon-warning"
          style="color: #E6A23C;"
        ></i>
        <span>若系统开启分销员审核功能，则该文案展示在用户申请页面。或分销员可在分销中心邀请其他用户注册为分销员，此时普通用户通过邀请链接点击也是查看该推广文案。</span>
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
            v-html="form.content"
          ></div>
        </div>
        <div class="rightContent">
          <el-form
            ref="form"
            :model="form"
            label-width="100px"
          >
            <el-form-item label="分享地址：">
              <span>{{ form.pageText }}</span>
              <span
                class="text"
                v-clipboard:copy="form.pageText"
                v-clipboard:success="copyHandler"
              >复制</span>
              <span
                class="text"
                @click="shareHandler"
              >立即分享</span>
            </el-form-item>
            <el-form-item label="页面标题：">
              <el-input
                placeholder="请填写页面标题"
                maxlength="20"
                show-word-limit
                size="small"
                v-model="form.title"
                style="width: 170px;"
              ></el-input>
            </el-form-item>
            <el-form-item label="页面内容：">
              <p
                class="template"
                @click="templateCopyHandler"
              >使用模板文案</p>
              <TinymceEditor v-model="form.content" />
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
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor'), // 富文本编辑器
    ShareDialog: () => import('@/components/admin/shareDialog') // 分享弹窗
  },
  data () {
    return {
      form: {
        pageText: 'pages/distributionspread/distributionspread',
        title: '分销员推广测试',
        content: '' // 文本内容
      },
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',

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
      this.form.content = this.contentTip2 + this.contentText
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

    },

    // 保存
    saveClickHandler () {
    },

    // 使用模板文案
    templateCopyHandler () {
      this.form.content = this.contentTip1 + this.contentText
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
