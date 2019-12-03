<template>
  <div class="templateMessage">
    <el-card>
      <div class="header">
        <span>{{$t('templateMessage.title')}}</span>
        <span>
          {{$t('templateMessage.tips')}}
        </span>
      </div>
      <el-divider></el-divider>
      <div class="title">
        {{$t('templateMessage.openMpNum')}}{{openMpNum}}{{$t('templateMessage.tiao')}}, {{$t('templateMessage.openMaNum')}}{{openMaNum}}{{$t('templateMessage.tiao')}}
      </div>
      <!-- 交易物流提醒|营销信息提醒 -->
      <div class="main">
        <el-collapse v-model="activeNames">
          <el-collapse-item
            :title="$t('templateMessage.tradingRemind')"
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
                  :label="$t('templateMessage.templateMessage')"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="sendingCondition"
                  :label="$t('templateMessage.sendConditions')"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop=""
                  :label="$t('templateMessage.openMpContent')"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.openMp"></el-checkbox>
                    <span>{{$t('templateMessage.sendMessagePublice')}}</span>
                    <el-popover
                      placement="right"
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
                  :label="$t('templateMessage.openMaMessage')"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.openMa"></el-checkbox>
                    <span>{{$t('templateMessage.sendMessageApplets')}}</span>
                    <el-popover
                      placement="right"
                      width="200"
                      trigger="hover"
                    >
                      <el-image :src="scope.row.xcxSrc"></el-image>
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
            :title="$t('templateMessage.marketInformationRemind')"
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
                  :label="$t('templateMessage.templateMessage')"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="sendingCondition"
                  :label="$t('templateMessage.sendConditions')"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  prop="publicNumberMessage"
                  :label="$t('templateMessage.openMpContent')"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.openMp"></el-checkbox>
                    <span>{{$t('templateMessage.sendMessagePublice')}}</span>
                    <el-popover
                      placement="right"
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
                  :label="$t('templateMessage.openMaMessage')"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.openMa"></el-checkbox>
                    <span>{{$t('templateMessage.sendMessageApplets')}}</span>
                    <el-popover
                      placement="right"
                      width="200"
                      trigger="hover"
                    >
                      <el-image :src="scope.row.xcxSrc"></el-image>
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
        >{{$t('templateMessage.save')}}</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
import { templateQueryApi, templateUpdateApi } from '@/api/admin/basicConfiguration/templateMessage'

export default {
  name: `templateMessage`,
  props: {

  },
  components: {

  },
  data () {
    return {
      list: [],
      activeNames: ['1', '2'],
      //  交易物流提醒
      tableData: [],
      //  营销信息提醒
      tableData1: []
    }
  },
  computed: {
    openMpNum () {
      let openMpNum = this.tableData.filter(item => item.openMp === true).length + this.tableData1.filter(item => item.openMp === true).length
      return openMpNum
    },
    openMaNum () {
      let openMaNum = this.tableData.filter(item => item.openMa === true).length + this.tableData1.filter(item => item.openMa === true).length
      return openMaNum
    }
  },
  mounted () {
    this.langDefault()
    this.fetchData()
  },
  watch: {
    lang () {
      this.tableData = [
        {
          templateMessage: this.$t('templateMessage.cancellationNotice'),
          sendingCondition: this.$t('templateMessage.sendOnCanellationNotice'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/qx_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/qx_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.appointSuccessRemind'),
          sendingCondition: this.$t('templateMessage.sendOnAppointSuccessRemind'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/cg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/cg_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.appointExpiredRemind'),
          sendingCondition: this.$t('templateMessage.sendOnAppointExpiredRemind'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/yydq_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/yydq_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.accountChangeRemind'),
          sendingCondition: this.$t('templateMessage.sendOnAccountChangeRemind'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/zhye_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/zhye_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.orderDeliveryRemind'),
          sendingCondition: this.$t('templateMessage.sendOnOrderDeliveryRemind'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ddfh_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ddfh_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.oredrNoPayNotice'),
          sendingCondition: this.$t('templateMessage.sendOnOredrNoPayNotice'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublice'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ddwzf_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ddwzf_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.orderPaySuccessNotice'),
          sendingCondition: this.$t('templateMessage.sendOnOrderPaySuccessNotice'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ddzfcg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ddzfcg_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.confirmReceiveGoods'),
          sendingCondition: this.$t('templateMessage.sendOnConfirmReceiveGoods'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/qrsh_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/qrsh_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.refundFailed'),
          sendingCondition: this.$t('templateMessage.sendOnRefundFailed'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/tksb_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/tksb_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.refundState'),
          sendingCondition: this.$t('templateMessage.sendOnRefundState'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/tktz_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/tktz_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.storeRemind'),
          sendingCondition: this.$t('templateMessage.sendOnStoreRemind'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/mdzt_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/mdzt_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.pickUpGoodsSucess'),
          sendingCondition: this.$t('templateMessage.sendOnPickUpGoodsSucess'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/qhcg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/qhcg_xcx.jpg`
        }
      ]
      this.tableData1 = [
        {
          templateMessage: this.$t('templateMessage.vipAccountChange'),
          sendingCondition: this.$t('templateMessage.sendOnVipAccountChange'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/viptx_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/viptx_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.vipReceiveSucess'),
          sendingCondition: this.$t('templateMessage.sendOnVipReceiveSucess'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/vipcg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/vipcg_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.cardTimesReduce'),
          sendingCondition: this.$t('templateMessage.sendOnCardTimesReduce'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/xckj_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/xckj_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.couponExpiration'),
          sendingCondition: this.$t('templateMessage.sendOnCouponExpiration'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/kqdq_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/kqdq_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.couponReceiveSucess'),
          sendingCondition: this.$t('templateMessage.sendOnCouponReceiveSucess'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/kqcg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/kqcg_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.spellGroupFail'),
          sendingCondition: this.$t('templateMessage.sendOnSpellGroupFail'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ptsb_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ptsb_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.spellGroupSucess'),
          sendingCondition: this.$t('templateMessage.sendOnSpellGroupSucess'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ptcg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ptcg_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.customMessage'),
          sendingCondition: this.$t('templateMessage.sendOnCustomMessage'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ywcl_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ywcl_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.bargainSucess'),
          sendingCondition: this.$t('templateMessage.sendOnBargainSucess'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/kjcg_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/kjcg_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.bargainProcess'),
          sendingCondition: this.$t('templateMessage.sendOnBargainProcess'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/kjjd_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/kjjd_xcx.jpg`
        }, {
          templateMessage: this.$t('templateMessage.checkPass'),
          sendingCondition: this.$t('templateMessage.sendOnCheckPass'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/official_audit_success.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/mp_audit_success.jpg`
        }, {
          templateMessage: this.$t('templateMessage.checkFail'),
          sendingCondition: this.$t('templateMessage.sendOnCheckFail'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/official_audit_fail.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/mp_audit_fail.jpg`
        }, {
          templateMessage: this.$t('templateMessage.vipLevelUpgrade'),
          sendingCondition: this.$t('templateMessage.sendOnVipLevelUpgrade'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/fxdj_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/fxdj_xcx.jpg`
        },
        {
          templateMessage: this.$t('templateMessage.spellGroupResults'),
          sendingCondition: this.$t('templateMessage.sendOnSpellGroupResults'),
          publicNumberMessage: this.$t('templateMessage.sendMessagePublic'),
          appletMessage: this.$t('templateMessage.sendMessageApplets'),
          preview: this.$t('templateMessage.preview'),
          src: `${this.$imageHost}/image/admin/template_message/ptcj_gzh.jpg`,
          xcxSrc: `${this.$imageHost}/image/admin/template_message/ptcj_xcx.jpg`
        }
      ]
    }
  },
  methods: {
    // 模板消息查询数据初始化
    fetchData () {
      templateQueryApi().then(res => {
        // console.log(res, 'templateQueryApi data---')
        // console.log(res.content)
        // 合并定义的数据和返回的数据
        let list = [...res.content]
        let list1 = [...res.content]
        // console.log(list, '----', list1, 'list data')

        let resultData = list.slice(0, 12).map((item, index) => {
          return { ...item, ...this.tableData[index], openMp: !!item.openMp, openMa: !!item.openMa }
        })
        this.tableData = resultData
        console.log(this.tableData, 'this.tableData')

        let resultData1 = list1.splice(12).map((item, index) => {
          return { ...item, ...this.tableData1[index], openMp: !!item.openMp, openMa: !!item.openMa }
        })
        this.tableData1 = resultData1
        console.log(this.tableData1, 'this.tableData1')
      }).catch(err => console.log(err))
    },

    // 保存-更新数据
    handleSave () {
      let configs = this.tableData.concat(this.tableData1).map(item => {
        return { ...item, openMp: Number(item.openMp), openMa: Number(item.openMa) }
      })
      let params = { configs }

      if (this.openMaNum > 25) {
        this.$message.warning('小程序消息不能超过25条!')
        return
      }
      if (this.openMpNum > 25) {
        this.$message.warning('公众号消息不能超过25条!')
        return
      }
      templateUpdateApi(JSON.stringify(params)).then(res => {
        if (res.error === 0) {
          this.$message.success('更新成功')
        }
      }).catch(err => console.log(err))
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
