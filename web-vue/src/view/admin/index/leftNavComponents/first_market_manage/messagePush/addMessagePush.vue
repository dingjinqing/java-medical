
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
                  <div class="h1title">业务标题</div>
                </div>
                <div class="leftMainConCenterCon"><span>业务内容</span><span class="xxx">xxx</span></div>
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
                <el-form-item :label="labels.label1">
                  <el-input
                    maxlength="20"
                    show-word-limit
                    size="small"
                    v-model="formData.name"
                    style="width:245px"
                  ></el-input>
                  <div class="mainContentRightFormText">只作为商家记录使用，用户不会看到这个名称</div>
                </el-form-item>
                <el-form-item :label="labels.label2">
                  <span>商家活动通知</span>
                </el-form-item>
                <el-form-item :label="labels.label3">
                  <el-input
                    maxlength="7"
                    show-word-limit
                    size="small"
                    v-model="formData.name"
                    style="width:245px"
                  ></el-input>
                </el-form-item>
                <el-form-item :label="labels.label4">
                  <div>
                    <el-button
                      @click="choosTemplate"
                      type="text"
                    >选择模板</el-button>
                  </div>
                  <div>
                    <el-input
                      style="width:250px"
                      type="textarea"
                      placeholder="请输入小程序推送内容"
                      v-model="textarea"
                      maxlength="50"
                      show-word-limit
                    >
                    </el-input>
                  </div>
                </el-form-item>
                <el-form-item :label="labels.label4">
                  <div>
                    <el-button
                      size="small"
                      @click="handleChooseLink"
                    > 选择链接</el-button>
                  </div>
                </el-form-item>
                <el-form-item :label="labels.label5">
                  <div>
                    <span>以下筛选条件为“或”关系</span>
                  </div>
                  <div>
                    <el-checkbox v-model="checked">加购人群</el-checkbox>
                    <span>30天内在本店内有加入购物车行为，但没有支付的用户</span>
                  </div>
                  <div>

                    <el-checkbox v-model="checked">指定购买商品人群 </el-checkbox>
                    <span>最多可选择3件商品</span>
                  </div>
                  <div class="chooseGoods">
                    <div class="chooseGoodsLeft">选择商品</div>
                    <div class="imageWraper">
                      <el-image :src="urls.url3"></el-image>
                    </div>
                  </div>
                  <div>
                    <el-checkbox v-model="checked">持有 </el-checkbox>
                    <el-select
                      v-model="value"
                      placeholder="请选择"
                    >
                      <el-option
                        label="请选择会员卡"
                        value="请选择会员卡"
                      ></el-option>
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                    <span>会员卡人群</span>
                  </div>
                  <div>
                    <el-checkbox v-model="checked">选择指定的会员 </el-checkbox>
                    <el-button
                      @click="handleAddMember"
                      type="text"
                    >+ 添加会员</el-button>
                    <span>已选择会员0人</span>
                  </div>
                  <div>
                    <el-checkbox v-model="checked">自定义 </el-checkbox>
                    <el-select
                      v-model="value"
                      placeholder="请选择"
                    >
                      <el-option
                        label="请选择"
                        value="请选择"
                      ></el-option>
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      >
                      </el-option>
                    </el-select>
                  </div>
                </el-form-item>
                <el-form-item :label="labels.label7">
                  <div>
                    <el-radio
                      v-model="radio"
                      label="1"
                    >立即发送</el-radio>
                  </div>
                  <div>
                    <span>预计送达人数：0 </span>
                    <el-button type="text">查看</el-button>
                  </div>
                  <div>
                    <el-radio
                      v-model="radio"
                      label="1"
                    >持续发送</el-radio>
                  </div>
                  <div class="timePicker">

                  </div>
                  <div>
                    所有可送达的用户均会第一时间收到一次此消息
                  </div>
                  <div>
                    <el-radio
                      v-model="radio"
                      label="1"
                    >定时发送</el-radio>
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
export default {
  name: 'addMessagePush',
  data () {
    return {
      urls: {
        url1: `${this.$imageHost}/image/admin/notice_img.png`,
        url2: `${this.$imageHost}/image/admin/shop_logo_default.png`,
        url3: `${this.$imageHost}/image/admin/shop_beautify/add_decorete.png`
      },
      /**
       * form表单的数据
       */
      formData: {
        name: ``
      },
      labels: {
        label1: `消息名称：`,
        label2: `消息类型：`,
        label3: `业务标题：`,
        label4: `业务内容：`,
        label5: `进入小程序查看：`,
        label6: `参与活动人群：`,
        label7: `发送时间：`
      },
      /**
       * 表单检验
       */
      rules: {
        name: [
          { required: true, message: '请填写消息名称', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ]
      }

    }
  }
}
</script>
<style lang="scss" scoped>
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
          height: 300px;
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
              height: 36px;
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
        width: 520px;
        height: 1010px;
        background-color: #f8f8f8;
        margin-left: 20px;
        padding-top: 15px;
        .mainContentRightForm {
          .mainContentRightFormText {
            color: #999;
          }
        }
        .chooseGoods {
          display: flex;
          .chooseGoodsLeft {
            margin-left: 50px;
            margin-right: 30px;
          }
          .imageWraper {
            width: 80px;
            height: 80px;
            border: 1px solid #ccc;
            background: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
      }
    }
  }
}
</style>
