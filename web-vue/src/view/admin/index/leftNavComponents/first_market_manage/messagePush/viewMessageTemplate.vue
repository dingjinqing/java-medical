
<template>
  <div class="addMessagePush">
    <!-- 添加消息推送的卡片 -->
    <el-card>
      <div class="mainContent">
        <div class="mainContentHeader">
          <div class="mainContentTop">
            <el-image
              style="margin-right:10px"
              :src="urls.url1"
              fit="fill"
            ></el-image>
            <span>每人每天最多发送一条自定义模板消息，请勿发送骚扰信息，违者将受到微信官方相应处罚，严重者将被封禁小程序</span>
          </div>
        </div>

        <div class="mainContentMiddle">
          <div class="mainContentLeft">
            <div class="leftTitle">
            </div>
            <div class="leftMainCon">
              <div class="leftMainConName">
                <el-image
                  style="width:20px;height:20px"
                  :src="urls.url2"
                  fit="fill"
                ></el-image>
                <span>此处为小程序的名称</span>
                <span class="fr">...</span>
              </div>
              <div class="leftMainConCenter">
                <div class="leftMainConCenterTips">商家活动通知</div>
                <div class="leftMainConCenterTime">2018年6月11日</div>
                <div class="leftMainConCenterTitle">
                  <div class="title">业务标题</div>
                  <div class="h1title">{{formData.title===``?`业务标题`:formData.title}}</div>
                </div>
                <div class="leftMainConCenterCon"><span>业务内容</span><span class="xxx">{{formData.content===``?`xxx`:formData.content}}</span></div>
                <div class="leftMainConCenterComeIn"><span>进入小程序查看</span></div>
                <div class="leftMainConCenterReject"><span>拒收通知</span></div>

              </div>
            </div>
          </div>
          <div class="mainContentRight">
            <div class="mainContentRightForm">
              <el-form
                :rules="rules"
                :model="formData"
                label-width="128px"
                size="small"
              >

                <!-- 消息名称 -->
                <el-form-item :label="labels.label1">
                  <el-input
                    maxlength="20"
                    show-word-limit
                    size="small"
                    :disabled="true"
                    v-model="detailData.name"
                    style="width:245px"
                  >
                  </el-input>
                  <div class="mainContentRightFormText">只作为商家记录使用，用户不会看到这个名称</div>
                </el-form-item>

                <!-- 消息类型 -->
                <el-form-item :label="labels.label2">
                  <!-- <el-input v-model="detailData."></el-input> -->
                </el-form-item>
                <!-- 业务标题 -->
                <el-form-item :label="labels.label3">
                  <el-input
                    maxlength="7"
                    show-word-limit
                    size="small"
                    v-model="formData.title"
                    style="width:245px"
                  ></el-input>
                </el-form-item>
                <!-- 业务内容 -->
                <el-form-item
                  :label="labels.label4"
                  class="addTemplateBtnWrap"
                >
                  <div>

                  </div>
                  <div>
                    <el-input
                      style="width:250px;"
                      :rows="7"
                      type="textarea"
                      placeholder="请输入小程序推送内容"
                      v-model="formData.content"
                      maxlength="50"
                      show-word-limit
                    >
                    </el-input>
                  </div>
                  <div
                    class="addTemplateBtn"
                    v-if="isShowBtn"
                  >
                    <el-button
                      size="small"
                      type="primary"
                      @click="handleAddTemplate"
                    >添加为模板</el-button>
                  </div>
                </el-form-item>
                <el-form-item :label="labels.label4">
                  <div>
                    <el-button size="small"> {{this.pageLink === ``?`添加链接`:this.pageLink}}</el-button>
                  </div>
                </el-form-item>

              </el-form>
            </div>

          </div>
        </div>
      </div>

    </el-card>
  </div>
</template>
<script>
//
import dialogTable from './chooseDialogTable'
// 引入选择链接弹窗
import selectLinks from '@/components/admin/selectLinks'
// 选择商品弹窗
import choosingGoods from '@/components/admin/choosingGoods'
// 选择内容模板弹窗
import chooseTemplateDialog from './chooseTemplateDialog'
import memberListDialog from './memberListDialog'
import getUserDialog from './getUserDialog'
import chooseSelect from '@/components/admin/chooseSelect/chooseSelect'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import { getDetailApi } from '@/api/admin/marketManage/messagePush.js'
export default {
  name: 'addMessagePush',
  /**
   * 接收查看组件的值
   */
  props: {

  },
  components: {
    'dgTable': dialogTable,
    chooseTemplateDialog,
    selectLinks,
    dateTimePicker,
    chooseSelect,
    memberListDialog,
    choosingGoods,
    getUserDialog },
  data () {
    return {
      /**
       *
       */
      id: this.$route.query.id,
      detailData: {},
      /**
       *
       */
      checkedData: [], // 初始化弹窗选中的行
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`,
        url4: `${this.$imageHost}/image/admin/icon_delete.png`
      },
      /**
       * form表单的数据
       */
      formData: {
        name: ``,
        title: ``,
        content: ``
      },
      templateId: null,
      labels: {
        label1: `消息名称：`,
        label2: `消息类型：`,
        label3: `业务标题：`,
        label4: `业务内容：`,
        label5: `进入小程序查看：`,
        label6: `参与活动人群：`,
        label7: `发送时间：`
      },
      cardList: [

      ],
      cardValue: `请选择会员卡`,
      /**
      * 动态获取人数的参数集合
      */
      params: {
        userKey: null,
        onClickNoPay: false,
        onClickGoods: false,
        // 商品列表idList
        onClickCard: false,
        cardIdsList: [],
        onClickTag: false,
        tagIdList: [],
        onClickUser: false,
        userIdList: [],
        onClickCustomRule: false,
        customRuleInfo: {
          payedDay: ``,
          noPayDay: ``,
          buyTimesMore: ``,
          buyTimesLess: ``,
          moneyAvgMore: ``,
          moneyAvgLess: ``,
          loginStart: ``,
          loginEnd: ``
        }
      },
      /**
       * params
       */
      senAction: 1,
      startTime: ``,
      endTime: ``,
      startTime1: ``,
      onClickNoPay: false, // 勾选加购人群
      onClickGoods: false, // 勾选购买指定商品人群
      goodsIdList: [], // 商品ID集合
      onClickCard: false,
      cardIdsList: [],
      onClickTag: false,
      tagIdList: [],
      onClickUser: false, // 勾选指定会员
      userIdList: [],
      onClickCustomRule: false,
      disabledOnClickCustomRule: false,
      pageLink: ``,
      time: {},
      /**
       * 表单检验
       */
      rules: {
        name: [
          { required: true, message: '请填写消息名称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ]
      },
      /**
       * 传给组件chooseSelect的数据
       */

      dialogOff: false,
      whetherShowDialog: false,
      dialogVisible: false,
      memberNum: 0, // 已选择的会员人数
      /**
       * 自定义实体
       */
      customRuleInfoVal: `请选择`,
      loginStart: ``,
      loginEnd: ``,
      customRuleInfoOptions: [
        {
          label: `N天内有交易记录`,
          value: `N天内有交易记录`,
          key: `payedDay`,
          ipt: ``
        },
        {
          label: `N天内没有交易记录`,
          value: `N天内没有交易记录`,
          key: `noPayDay`,
          ipt: ``

        },
        {
          label: `累计购买次数小于N次`,
          value: `累计购买次数小于N次`,
          key: `buyTimesLess`,
          ipt: ``

        },
        {
          label: `累计购买次数大于N次`,
          value: `累计购买次数大于N次`,
          key: `buyTimesMore`,
          ipt: ``

        },
        {
          label: `购买商品均价大于N元`,
          value: `购买商品均价大于N元`,
          key: `moneyAvgMore`,
          ipt: ``

        },
        {
          label: `购买商品均价小于N元`,
          value: `购买商品均价小于N元`,
          key: `moneyAvgLess`,
          ipt: ``

        },
        {
          label: `指定时间内有登录记录`,
          value: `指定时间内有登录记录`,
          key: `time`

        }
      ],
      optionsList: [

      ],
      showTime: false,
      isShowBtn: false,
      arrList: [],
      imgsList: [],
      userNumber: 0,
      tuneUpChooseGoods: false,
      tuneUpSelectLink: false

    }
  },
  watch: {
    senAction (newVal, oldVal) {
      switch (newVal) {
        case 1:
          this.startTime = this.moment().format('YYYY-MM-DD HH:mm:ss')
          break
        case 2:
          this.startTime = this.time.startTime
          this.endTime = this.time.endTime
          break
        case 4:
          this.startTime = this.time.startTime
          break
        default:
          break
      }
    }
  },
  created () {
    this.fetchData()
  },
  mounted () {

  },
  filters: {
    filterA: function (val) {
      // console.log(val.search('N'))
      if (val.search('N') !== -1) {
        return val.substr(val.search('N') + 1, 1)
      }
    }
  },
  // 方法
  methods: {
    fetchData () {
      getDetailApi(this.id).then(res => {
        const { error, content } = res
        if (error === 0) {
          this.detailData = content
          console.log(this.detailData)
        }
      }).catch(err => console.log(err))
    }
  }

}
</script>
<style lang="scss" scoped>
.saveAndSend {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
.addMessagePush {
  padding: 10px;
  .mainContent {
    .mainContentHeader {
      display: flex;
      justify-content: center;
    }
    .mainContentTop {
      display: flex;
      align-items: center;
      justify-content: center;
      border: 1px solid #f2e1c8;
      background: #fff7ec;
      color: #666;
      margin-bottom: 12px;
      height: 40px;
      width: 870px;
    }
    .mainContentMiddle {
      display: flex;
      justify-content: center;
      .mainContentLeft {
        width: 320px;
        height: 510px;
        background-color: #eee;
        margin-right: 20px;
        .leftTitle {
          height: 55px;
          width: 100%;
          background: url(../../../../../../assets/adminImg/phone_tops.png)
            no-repeat;
        }
        .leftMainCon {
          width: 285px;
          min-height: 300px;
          background-color: #fff;
          margin-top: 30px;
          margin-left: 15px;
          .leftMainConName {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            width: 100%;
            height: 45px;
            line-height: 45px;
            border-bottom: 1px solid #eee;
            .fr {
              font-size: 34px;
              // line-height: 25px;
              color: #999;
              margin-left: 70px;
              margin-bottom: 20px;
            }
          }
          .leftMainConCenter {
            width: 100%;
            height: 172px;
            padding: 15px 12px 0;
            .leftMainConCenterTips {
              padding: 10px 12px;
            }
            .xxx {
              color: #000;
              font-size: 12px;
              margin-left: 5px;
            }
            .leftMainConCenterTime {
              padding: 10px 12px;
            }
            .leftMainConCenterTitle {
              padding: 10px 12px;
              height: 50px;
              .title {
                color: #999;
                font-size: 12px;
                display: flex;
                justify-content: center;
                align-items: center;
              }
              .h1title {
                color: #000;
                font-size: 22px;
                display: flex;
                justify-content: center;
                padding: 5px;
              }
            }
            .leftMainConCenterCon {
              border-top: 1px solid #eee;
              border-bottom: 1px solid #eee;
              min-height: 36px;
              padding: 10px 12px;
            }
            .leftMainConCenterReject {
              padding: 10px 12px;
              border-top: 1px solid #eee;
              background: url(../../../../../../../static/image/wxapp/click_look.png)
                no-repeat 95%;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
            .leftMainConCenterComeIn {
              padding: 10px 12px;
              border-top: 1px solid #eee;
              background: url(../../../../../../../static/image/wxapp/click_look.png)
                no-repeat 95%;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
          }
        }
      }
      .mainContentRight {
        margin-bottom: 100px;
        width: 520px;
        min-height: 600px;
        background-color: #f8f8f8;
        margin-left: 20px;
        padding-top: 15px;
        .mainContentRightForm {
          .mainContentRightFormText {
            color: #999;
          }
          .addTemplateBtnWrap {
            position: relative;
            .addTemplateBtn {
              position: absolute;
              bottom: 10px;
              left: 10px;
            }
          }
        }
        .chooseGoods {
          display: flex;
          margin: 20px 0;
          .chooseGoodsLeft {
            margin-left: 40px;
            margin-right: 20px;
          }
          .imgList {
            display: flex;
            li {
              margin-right: 10px;
              position: relative;
              .delImg {
                position: absolute;
                top: -6px;
                right: -6px;
              }
            }
          }
          .imageWraper {
            width: 80px;
            height: 80px;
            cursor: pointer;
            border: 1px solid #ccc;
            background: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
        .selectedCard {
          border: 1px solid #eee;
          width: 382px;
          min-height: 56px;
          margin: 10px 0;
          display: flex;
          flex-wrap: wrap;
          .oneCardWraper {
            position: relative;
            margin: 2px 6px;
            .oneCard {
              padding: 0 10px;
              min-width: 70px;
              // margin: 10px;
              // display: flex;
              text-align: center;
              line-height: 24px;
              background-color: #fff;
              height: 24px;
              border: 1px solid #ccc;
              display: inline-block;
            }
            .oneCardDel {
              position: absolute;
              right: -5px;
              top: -5px;
              cursor: pointer;
            }
          }
        }
        .ulList {
          width: 100%;
          li {
            margin: 5px 0;
            span {
              margin: 0 5px;
            }
            .img_span {
              position: relative;
              .img {
                position: absolute;
                right: 25px;
                top: -22px;
                cursor: pointer;
              }
            }
          }
        }
      }
    }
  }
}
</style>
