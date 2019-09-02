<template>
  <div class="integralDescription">
    <div class="integralDescriptionMain">
      <div class="container">
        <div class="leftDiv">
          <div class="leftTitle"></div>
          <img :src="$imageHost+'/image/admin/shop_beautify/phone_tops.png'">
          <div class="leftContent">
            <div class="show_pass">
              <p class="headline">
                <strong>积分是什么</strong>
              </p>
              <p>
                积分是微铺宝电商运营小程序商城（以下简称“商城”）回馈给广大会员的服务，会员在商城通过购物消费、签到及活动参与将获得相应的积分奖励。在不同情况下，积分可用于购物时抵扣订单支付金额、参与商城活动等，100积分抵扣1元人民币。积分收支规则以商城实际运营情况为准
              </p>
              <p class="headline">
                如何获取积分
              </p>
              <p>
                1.会员在商城成功消费，当订单状态为“已完成”后赠送的积分到账且可以正常使用。购买商品可获得的积分数量，具体请以订单完成后实际到账数量为准。
              </p>
              <p>
                2.会员在商城开通会员卡，可获得相应数量的积分。
              </p>
              <p>
                3.会员在商城参与活动，可获得相应数量的积分。
              </p>
              <p>
                4.会员在商城签到可领取签到积分奖励。
              </p>
              <p>
                5.会员具体可获得的积分数量以最终实际获得为准。
              </p>
              <p class="headline">
                <strong>注意</strong>
              </p>
              <p>
                A.订单发生部分退款，则实际赠送的积分数量会扣减退货商品对应的积分；
              </p>
              <p>
                B.购买会员卡不赠送积分。
              </p>
              <p class="headline">
                <strong>如何使用积分</strong>
              </p>
              <p>
                1.购物抵现金，在商城购物时，积分可抵现金支付，可抵扣金额显示请以订单结算页面显示可用数目为准。
              </p>
              <p>
                2.使用积分兑换商品、优惠券及会员卡，在商城“积分兑换”模块中，可选择兑换的商品、优惠券或会员卡，以相应数量的积分进行兑换，兑换所需积分数量请以最终结算页面提示信息为准。
              </p>
              <p>
                3.参与活动消耗积分。
              </p>
              <p class="headline">
                <strong>注意</strong>：
              </p>
              <p>
                A.商城不同阶段运营策略不同，结算时会出现积分抵扣不能使用的情况；
              </p>
              <p>
                B.积分无法抵扣运费金额；
              </p>
              <p>
                C.如果会员以不正当方式（包括但不限于利用系统漏洞作弊、规避商城管理规则等）获取积分，商城有权取消会员以不正当方式获得、使用的积分。情节严重的，商城有权冻结会员账户资金、禁止会员登录、终止服务。
              </p>
              <p class="headline">
                <strong>积分有效期</strong>
              </p>
              <p>
                在商城中获得的积分存在有效期限，具体有效期限由商家设置。
              </p>
              <p class="headline">
                <strong>服务声明</strong>
              </p>
              <p>
                商城可根据不同时段的运营策略修改积分领取及使用等规则，使用积分时请以商城相关页面实际结果为准。此《积分说明》最终解释权归微铺宝电商运营小程序商城所有。
              </p>
            </div>
          </div>
        </div>
        <div class="rightDiv">
          <div class="rightTop">
            <span>页面内容：</span>
            <span style="color:#5a8bff;cursor:pointer">使用模板文案</span>
          </div>
          <div class="rightContent">
            <div>
              <!--编辑器-->
              <TinymceEditor
                v-model="editMsg"
                :disabled="disabled"
                @onClick="onClick"
                @input="input"
                ref="editor"
              />
            </div>

          </div>
        </div>
      </div>

    </div>

    <!--保存-->
    <SaveComponent />
  </div>
</template>
<script>
export default {
  components: {
    SaveComponent: () => import('./saveComponent'),
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor')
  },
  data () {
    return {
      editMsg: '',
      disabled: false
    }
  },
  methods: {
    // 鼠标单击的事件
    onClick (e, editor) {
      console.log(e, editor)
    },
    // 清空内容
    clear () {
      this.$refs.editor.clear()
    },
    // 编辑器输入内容获取
    input (val) {
      console.log(val)
    }
  }
}
</script>
<style lang="scss" scoped>
.integralDescription {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  min-height: 600px;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;

  .integralDescriptionMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    height: 100%;
    display: flex;
    justify-content: center;
    .container {
      display: flex;
      .leftDiv {
        position: relative;
        width: 330px;
        height: 600px;
        border: 1px solid #ededed;
        margin-right: 10px;
        img {
          display: inline-block;
          width: 330px;
          height: 55px;
        }
        .leftTitle {
          width: 100%;
          position: absolute;
          top: 0;
          height: 55px;
          color: #fff;
          text-align: center;
          line-height: 70px;
        }
        .leftContent {
          .show_pass {
            p {
              line-height: 20px;
            }
            padding: 0 8px;
            overflow-y: auto;
            height: 540px;
            .headline {
              font-weight: bold;
            }
          }
        }
      }
      .rightDiv {
        width: 600px;
        padding: 20px 25px;
        height: 630px;
        background: #f8f8f8;
        border: 1px solid #ededed;
        .rightTop {
          margin-bottom: 20px;
        }
        .rightContent {
          width: 100%;
          display: flex;
          justify-content: flex-end;
          div {
            width: 472px;
          }
        }
      }
    }
  }
}
</style>
