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
            :src="this.$imageHost + '/image/admin/shop_beautify/phone_tops.png'"
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
import { setDocument, getDocument, setRebateDocument, getRebateDocument } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor'), // 富文本编辑器
    ShareDialog: () => import('@/components/admin/shareDialog') // 分享弹窗
  },
  data () {
    return {
      type: 1, // 类型 (1推广文案, 2返利规则)
      pageText: '',
      form: {
        title: '',
        document: '' // 文本内容
      },
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',

      templateFlag: false, // 默认适用模板
      contentTip1: '',
      contentTip2: '',
      contentText: '',

      // 返利规则模板
      rebateTemplate: `
        <div class="rebateContent">
          <p>1. 返利说明</p>
          <p>(1) 买家购买返利商品,下单支付成功,则邀请该用户注册的分销员可获得佣金返利。</p>
          <p>(2) 邀请新用户注册,或者分享给已经注册的但没有邀请人的用户都算作该分销员的用户。</p>
          <p>(3) 订单支付成功则返利佣金为待返利状态,交易完成则该佣金返利完成,自动提现到分销员的用户余额中。交易完成前发生退款的订单,相应的分销员返利佣金为已退款返利失败状态。</p>
          <p>(4) 仅在线支付的订单算作业绩,即微信支付、余额支付(会员卡余额、用户余额)、货到付款的订单计算返利佣金。例如买家购买返利商品A价格为100元,该买家使用了20元的优惠券,其余金额使用微信支付,返利比例为10%,则邀请该买家的分销员可获得返利佣金为(100-20)*10%=8元。</p>
          <p>(5) 买家仅购买返利商品,该买家的邀请人可获得返利佣金,购买普通商品不返利。</p>
          <p>2. 结算说明</p>
          <p>(1) 按照返佣比例进行返利。</p>
          <p>(2) 返利有效期在用户被分销员邀请注册开始计算，在指定天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利。</p>
          <p>3. 其他说明</p>
          <p>(1) 分享前,请确定商品页面有【...】按钮。</p>
          <p>(2) 销售过程中有任何疑问,请直接联系商家。</p>
          <p>(3) 已售出商品的任何售后问题,由本商城处理。</p>
          <p>(4) 不传播或者扩散有关政治、色情等任何违法的信息,一经发现,则立即封号,如果触犯任何法律相关问题,商城不负任何责任。</p>
          <p>(5) 以上内容解释权归本商城所有。</p>
        </div>
      `
    }
  },
  watch: {
    lang () {
      this.contentTip1 = this.$t('distribution.contentTip1')
      this.contentTip2 = this.$t('distribution.contentTip2')
      this.contentText = this.$t('distribution.contentText')
      this.getInitData()
    }
  },
  mounted () {
    this.langDefault()

    if (this.$route.query.type) {
      this.type = this.$route.query.type
      if (this.type === 1) {
        this.pageText = 'pages/distributionspread/distributionspread'
        this.form.title = '分销员推广测试'
      } else {
        this.pageText = 'pages/distribution/distribution'
        this.form.title = '返利规则说明'
      }
      this.getInitData()
    }
  },
  methods: {
    // 初始化数据
    getInitData () {
      if (this.type === 1) {
        // 获取推广文案
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
          }
        })
      } else {
        // 获取返利规则
        getRebateDocument().then(res => {
          if (res.error === 0) {
            if (res.content) {
              this.form = res.content
            } else {
              // this.form.document = this.rebateTemplate
            }
          }
        })
      }
    },

    // 保存
    saveClickHandler () {
      if (this.type === 1) {
        // 保存推广文案
        console.log(this.form)
        setDocument(this.form).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('distribution.rebateSaveSuccess'))
            // setTimeout(() => {
            //   this.$router.push({ name: 'distribution_info' })
            // }, 1000)
          }
        })
      } else {
        // 保存返利规则
        setRebateDocument(this.form).then(res => {
          if (res.error === 0) {
            this.$message.success(this.$t('distribution.rebateSaveSuccess'))
            // setTimeout(() => {
            //   this.$router.push({ name: 'distribution_info' })
            // }, 1000)
          }
        })
      }
    },

    // 使用模板文案
    templateCopyHandler () {
      this.templateFlag = true
      if (this.type === 1) {
        this.form.document = this.contentTip1 + this.contentText
      } else {
        this.form.document = this.rebateTemplate
      }
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

.leftContent .leftPass >>> .rebateContent {
  font-size: 14px;
  line-height: 28px;
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
