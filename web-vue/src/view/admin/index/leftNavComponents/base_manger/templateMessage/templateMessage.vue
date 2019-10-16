<template>
  <div class="templateMessage">
    <el-card>
      <div class="header">
        <span> 微信消息模板</span>
        <span>
          因微信平台限制，公众号消息最多可发送25类，小程序消息最多可发送25类，请谨慎选择
        </span>
      </div>
      <el-divider></el-divider>
      <div class="title">
        公众号消息已选24条, 小程序消息已选25条
      </div>
      <!-- 交易物流提醒|营销信息提醒 -->
      <div class="main">
        <el-collapse
          v-model="activeNames"
          @change="handleChange"
        >
          <el-collapse-item
            style="color:red"
            title="交易物流提醒"
            name="1"
          >
            <div>
              <el-table
                :data="tableData"
                style="width: 100%"
                border
              >
                <el-table-column
                  prop="templateMessage"
                  label="模板消息"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="sendingCondition"
                  label="发送条件"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop=""
                  label="公众号消息"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.check"></el-checkbox>
                    <span>发送公众号消息</span>
                    <el-popover
                      placement="top-right"
                      width="200"
                      trigger="hover"
                    >
                      <el-image :src="scope.row.src"></el-image>
                      <el-button
                        slot="reference"
                        type="text"
                      >{{scope.row.preview}}</el-button>
                    </el-popover>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="appletMessage"
                  label="小程序消息"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.checked"></el-checkbox>
                    <span>发送小程序消息</span>
                    <el-popover
                      placement="top-right"
                      width="200"
                      trigger="hover"
                    >
                      <el-image :src="srcList.src1"></el-image>
                      <el-button
                        slot="reference"
                        type="text"
                      >{{scope.row.preview}}</el-button>
                    </el-popover>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-collapse-item>
          <el-collapse-item
            title="营销信息提醒"
            name="2"
          >
            <div>
              <el-table
                :data="tableData1"
                style="width: 100%"
                border
              >
                <el-table-column
                  prop="templateMessage"
                  label="模板消息"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="sendingCondition"
                  label="发送条件"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="publicNumberMessage"
                  label="公众号消息"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.check"></el-checkbox>
                    <span>发送公众号消息</span>
                    <el-popover
                      placement="top-right"
                      width="200"
                      trigger="hover"
                    >
                      <el-image :src="scope.row.src"></el-image>
                      <el-button
                        slot="reference"
                        type="text"
                      >{{scope.row.preview}}</el-button>
                    </el-popover>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="appletMessage"
                  label="小程序消息"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.checked"></el-checkbox>
                    <span>发送小程序消息</span>
                    <el-popover
                      placement="top-right"
                      width="200"
                      trigger="hover"
                    >
                      <el-image :src="srcList.src1"></el-image>
                      <el-button
                        slot="reference"
                        type="text"
                      >{{scope.row.preview}}</el-button>
                    </el-popover>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <!-- 保存 -->
      <div class="save">
        <el-button
          @click="handleSave()"
          size="small"
          type="primary"
        >保存</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
import { templateQueryApi } from '@/api/admin/basicConfiguration/templateMessage'
export default {
  name: `templateMessage`,
  props: {

  },
  components: {

  },
  data () {
    return {
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`
      },
      activeNames: ['1'],
      /**
       * 交易物流提醒
       */
      tableData: [
        {
          templateMessage: `预约取消通知`,
          sendingCondition: `预约订单取消时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/qx_gzh.jpg`
        },
        {
          templateMessage: `预约成功提醒`,
          sendingCondition: `服务预约成功时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/cg_gzh.jpg`
        },
        {
          templateMessage: `预约到期提醒`,
          sendingCondition: `距预约服务开始前1小时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/yydq_gzh.jpg`
        },
        {
          templateMessage: `账户余额变动提醒`,
          sendingCondition: `账户余额发生变动时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/zhye_gzh.jpg`
        },
        {
          templateMessage: `订单发货提醒`,
          sendingCondition: `订单发货时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ddfh_gzh.jpg`
        },
        {
          templateMessage: `订单未支付通知`,
          sendingCondition: `下单后未支付时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ddwzf_gzh.jpg`
        },
        {
          templateMessage: `订单支付成功通知`,
          sendingCondition: `订单支付成功时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ddzfcg_gzh.jpg`
        },
        {
          templateMessage: `确认收货通知`,
          sendingCondition: `确认收货时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/qrsh_gzh.jpg`
        },
        {
          templateMessage: `退款失败通知`,
          sendingCondition: `退款失败时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/tksb_gzh.jpg`
        },
        {
          templateMessage: `退款状态通知`,
          sendingCondition: `退款时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/tktz_gzh.jpg`
        },
        {
          templateMessage: `门店自提到期提醒`,
          sendingCondition: `门店自提时间到期前半小时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/mdzt_gzh.jpg`
        },
        {
          templateMessage: `取货成功通知`,
          sendingCondition: `取货完成时发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/qhcg_gzh.jpg`
        }
      ],
      /**
       * 营销信息提醒
       */
      tableData1: [
        {
          templateMessage: `会员卡余额变动提醒`,
          sendingCondition: `会员卡余额发生变动时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/viptx_gzh.jpg`
        },
        {
          templateMessage: `会员卡领取成功通知`,
          sendingCondition: `会员卡领取成功通知`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/vipcg_gzh.jpg`
        },
        {
          templateMessage: `限次卡扣减通知`,
          sendingCondition: `限次卡扣减使用次数后立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/xckj_gzh.jpg`
        }, {
          templateMessage: `卡券到期提醒`,
          sendingCondition: `会员卡/优惠券过期前一天发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/kqdq_gzh_gzh.jpg`
        },
        {
          templateMessage: `卡券领取成功通知`,
          sendingCondition: `领取优惠券后立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/kqcg_gzh.jpg`
        }, {
          templateMessage: `拼团失败通知`,
          sendingCondition: `拼团时间截止后如未拼团成功则立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ptsb_gzh.jpg`
        }, {
          templateMessage: `拼团成功通知`,
          sendingCondition: `拼团成功时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ptcg_gzh.jpg`
        },
        {
          templateMessage: `自定义消息模板推送`,
          sendingCondition: `自定义消息模板到发送时间时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ywcl_gzh.jpg`
        }, {
          templateMessage: `砍价成功提醒`,
          sendingCondition: `砍价成功时立即发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/kjcg_gzh.jpg`
        },
        {
          templateMessage: `砍价进度通知`,
          sendingCondition: `砍价结束前3小时如未砍价成功则发送`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/kjjd_gzh.jpg`
        }, {
          templateMessage: `审核通过提醒`,
          sendingCondition: `审核通过提醒`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/official_audit_success.jpg`
        }, {
          templateMessage: `审核不通过提醒`,
          sendingCondition: `审核不通过提醒`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/official_audit_fail.jpg`
        }, {
          templateMessage: `分销员等级升级提醒`,
          sendingCondition: `分销员等级升级提醒`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/fxdj_gzh.jpg`
        },
        {
          templateMessage: `拼团抽奖结果通知`,
          sendingCondition: `拼团抽奖结果通知`,
          publicNumberMessage: `发送公众号消息`,
          appletMessage: `发送小程序消息`,
          preview: '预览',
          check: '',
          checked: '',
          src: `${this.$imageHost}/image/admin/template_message/ptcj_gzh.jpg`
        }
      ]
    }
  },
  computed: {

  },
  created () {
    this.fetchData()
  },
  mounted () {

  },
  watch: {

  },
  methods: {
    // 模板消息查询数据初始化
    // async fetchData () {
    //   const response = await templateQueryApi()
    //   console.log(response)
    // },
    fetchData () {
      templateQueryApi().then(res => {
        console.log(res)
      }).catch(err => console.log(err))
    },
    // 当前激活面板改变时触发
    handleChange (val) {
      console.log(val)
    },
    // 保存
    handleSave () {

    }
  }
}
</script>

<style scoped lang="scss">
.templateMessage {
  padding: 10px;
  .header {
    span {
      &:nth-of-type(2) {
        color: #999;
        font-size: 12px;
      }
    }
  }
  /deep/ .el-divider {
    margin: 16px 0;
  }
  /deep/ .el-checkbox {
    margin-right: 10px;
  }
  .title {
    margin-bottom: 20px;
    font-size: 14px;
  }
  .main {
    margin-bottom: 20px;
  }
  .save {
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
}
</style>
