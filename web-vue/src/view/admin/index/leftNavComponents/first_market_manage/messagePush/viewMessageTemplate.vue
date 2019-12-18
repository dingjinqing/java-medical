
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
                <div class="leftMainConCenterTime">2019年9月24日</div>
                <div class="leftMainConCenterTitle">
                  <div class="title">业务标题</div>
                  <div class="h1title">{{detailData.title}}</div>
                </div>
                <div class="leftMainConCenterCon"><span>业务内容</span><span class="xxx">{{detailData.content}}</span></div>
                <div
                  class="leftMainConCenterComeIn"
                  :style="'background:url('+$imageHost+'/image/wxapp/click_look.png) no-repeat 95%'"
                ><span>进入小程序查看</span></div>
                <div
                  class="leftMainConCenterReject"
                  :style="'background:url('+$imageHost+'/image/wxapp/click_look.png) no-repeat 95%'"
                ><span>拒收通知</span></div>

              </div>
            </div>
          </div>
          <div class="mainContentRight">
            <div class="mainContentRightForm">
              <el-form
                :rules="rules"
                :model="formData"
                label-width="140px"
                size="small"
              >
                <!-- 后台返回的数据 -->
                <div>
                  <!-- {{detailData}} -->
                </div>
                <!-- 消息名称 -->
                <el-form-item
                  :label="labels.label1"
                  :rules="[{required:true}]"
                >
                  <div class="detailDataName">
                    {{detailData.name}}
                  </div>
                  <div class="mainContentRightFormText">只作为商家记录使用，用户不会看到这个名称</div>
                </el-form-item>

                <!-- 消息类型 -->
                <el-form-item
                  :label="labels.label2"
                  :rules="[{required:true}]"
                >
                  <div class="detailDataName">
                    {{detailData.action===7?`商家活动通知`:`其他`}}
                  </div>
                </el-form-item>
                <!-- 业务标题 -->
                <el-form-item
                  :label="labels.label3"
                  :rules="[{required:true}]"
                >
                  <div class="detailDataName">
                    {{detailData.title}}
                  </div>
                </el-form-item>
                <!-- 业务内容 -->
                <el-form-item
                  :label="labels.label4"
                  class="addTemplateBtnWrap"
                  :rules="[{required:true}]"
                >
                  <div class="detailDataContent">
                    {{detailData.content}}
                  </div>
                </el-form-item>
                <!-- 进入小程序查看 -->
                <el-form-item
                  :label="labels.label5"
                  :rules="[{required:true}]"
                >
                  <div class="detailDataName">
                    {{detailData.pageLink}}
                  </div>
                </el-form-item>
                <!-- 参与活动人群 -->
                <el-form-item
                  :label="labels.label6"
                  :rules="[{required:true}]"
                >
                  <div>
                    <span style="color:#999;fontSize:12px">以下筛选条件为“或”关系</span>
                  </div>
                  <ul class="ulList">
                    <li>12 天内有交易记录</li>
                    <li>30天内在本店内有加入购物车行为，但没有支付的用户</li>
                    <li>指定的会员</li>
                  </ul>

                </el-form-item>
                <!-- 发送时间 -->
                <el-form-item
                  :label="labels.label7"
                  :rules="[{required:true}]"
                >
                  <!-- 立即发送 -->
                  <div v-if="detailData.sendAction ===1">
                    <el-radio
                      :label=1
                      v-model="detailData.sendAction"
                    >立即发送</el-radio>
                    <div class="sendAction1">
                      <el-date-picker
                        v-model="detailData.startTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        readonly
                      >
                      </el-date-picker>
                    </div>
                  </div>
                  <!-- 持续发送 -->
                  <div v-else-if="detailData.sendAction ===2">
                    <div>
                      <el-radio
                        :label=2
                        v-model="detailData.sendAction"
                      >持续发送</el-radio>
                    </div>
                    <div class="sendAction2">
                      <el-date-picker
                        v-model="sendAction2"
                        type="datetimerange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        readonly
                      >
                      </el-date-picker>
                    </div>
                    <div style="color:#999;fontSize:12px;margin:5px 0">
                      所有可送达的用户均会第一时间收到一次此消息
                    </div>
                  </div>
                  <!-- 定时发送 -->
                  <div v-else>
                    <div>
                      <el-radio
                        :label=4
                        v-model="detailData.sendAction"
                      >定时发送</el-radio>
                    </div>
                    <div>
                      <el-date-picker
                        v-model="detailData.startTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        readonly
                      >
                      </el-date-picker>
                    </div>
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
import RulesMixins from '@/mixins/RulesMixins'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import { getDetailApi } from '@/api/admin/marketManage/messagePush.js'
export default {
  name: 'addMessagePush',
  /**
   * 接收查看组件的值
   */
  mixins: [RulesMixins],
  components: {
    dateTimePicker
  },
  data () {
    return {
      /**
       *
       */
      id: this.$route.query.id,
      detailData: {},
      sendAction2: [],
      // sendAction2: [this.detailData.startTime, this.detailData.endTime],

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
      rules: {
        // 消息名称
        name: [
          { validator: this.checkMessageName, trigger: 'blur', required: true }
        ],
        // 业务标题
        title: [
          { validator: this.checkMessageTitle, trigger: 'blur', required: true }
        ],
        // 业务内容
        content: [
          { validator: this.checkMessageContent, trigger: 'blur', required: true }

        ]
      }

    }
  },
  watch: {
    detailData: {
      handler: function (newVal, oldVal) {
        this.sendAction2 = [newVal.startTime, newVal.endTime]
      },
      deep: true
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
              -webkit-background-size: 6px;
              background-size: 6px;
            }
            .leftMainConCenterComeIn {
              padding: 10px 12px;
              border-top: 1px solid #eee;
              -webkit-background-size: 6px;
              background-size: 6px;
            }
          }
        }
      }
      .mainContentRight {
        margin-bottom: 100px;
        min-width: 520px;
        min-height: 600px;
        background-color: #f8f8f8;
        margin-left: 20px;
        padding-top: 15px;
        .mainContentRightForm {
          .detailDataName {
            height: 30px;
            width: 245px;
            border: 1px solid #dbdbdb;
            background: #fff;
            line-height: 30px;
            padding-left: 12px;
          }
          .detailDataContent {
            padding: 10px 0 0 12px;
            height: 120px;
            width: 330px;
            border: 1px solid #dbdbdb;
          }
          .ulList {
            border: 1px solid #eee;
            background: #fff;
            padding: 0px 10px 10px 30px;
            margin: 10px 0;
            list-style-type: disc;
          }
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
