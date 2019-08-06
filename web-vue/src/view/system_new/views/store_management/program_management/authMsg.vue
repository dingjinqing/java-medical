<template>
  <div class="authMsg">
    <div class="bg-white pd-10">
      <div class="handle-status">
        操作成功
      </div>
    </div>

    <div class="details-wrap bg-white">
      <el-row :gutter="10">
        <el-col :lg="12" :sm="24">
          <div class="details-item">
            <div class="item-header">
                <span class="title">
                  授权基本信息
                </span>
              <el-button size="small" type="primary">
                更新授权信息
              </el-button>
            </div>
            <div class="item-content">
              <table class="item-table">
                <tbody>
                <tr>
                  <td>店铺ID</td>
                  <td>
                    7893594
                  </td>
                </tr>
                <tr>
                  <td>appid</td>
                  <td>wxeaeb5c37a376f415</td>
                </tr>
                <tr>
                  <td>
                    主体名称
                  </td>
                  <td>
                    北京微铺宝网络科技有限公司
                  </td>
                </tr>
                <tr>
                  <td>昵称</td>
                  <td>微铺宝b2c商城</td>
                </tr>
                <tr>
                  <td>用户名</td>
                  <td>gh_b48e11dd4e99</td>
                </tr>
                <tr>
                  <td>别名</td>
                  <td></td>
                </tr>
                <tr>
                  <td>头像</td>
                  <td>
                    <el-avatar shape="square"
                               src="http://wx.qlogo.cn/mmopen/ibkKkoaQFco4LkwgLSc80z6O24h245qPMLL6znSwbPhEs5eLBcT07CXcXC0CzvohMts3N47SuUQsNmbMOPzbNsFrSGoj1Lfd8/0"></el-avatar>
                  </td>
                </tr>
                <tr>
                  <td>小程序二维码</td>
                  <td>
                    <el-avatar shape="square"
                               src="http://wx.qlogo.cn/mmopen/ibkKkoaQFco4LkwgLSc80z6O24h245qPMLL6znSwbPhEs5eLBcT07CXcXC0CzvohMts3N47SuUQsNmbMOPzbNsFrSGoj1Lfd8/0"></el-avatar>
                  </td>
                </tr>
                <tr>
                  <td>
                    小程序授权权限
                  </td>
                  <td>
                    <div class="td-item"
                         v-for="(item, index) in 3"
                         :key="index">
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark">
                        已授权
                      </el-tag>
                      <span>
                            账号管理权限（小程序）
                          </span>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td>是否已认证</td>
                  <td>
                    <el-tag
                      type="primary"
                      size="small"
                      effect="dark">
                      已认证
                    </el-tag>
                  </td>
                </tr>
                <tr>
                  <td>是否支持微信支付</td>
                  <td>
                    <el-tag
                      type="primary"
                      size="small"
                      effect="dark">
                      是
                    </el-tag>
                  </td>
                </tr>
                <tr>
                  <td>是否支持卡券</td>
                  <td>
                    <el-tag
                      type="warning"
                      size="small"
                      effect="dark">
                      否
                    </el-tag>
                  </td>
                </tr>
                <tr>
                  <td>是否授权</td>
                  <td>
                    <el-tag
                      type="warning"
                      size="small"
                      effect="dark">
                      是
                    </el-tag>
                  </td>
                </tr>
                <tr>
                  <td>最后授权时间</td>
                  <td>
                    2019-08-01 11:52:22
                  </td>
                </tr>
                <tr>
                  <td>最后取消授权时间</td>
                  <td>
                    2018-05-23 09:44:17
                  </td>
                </tr>
                <tr>
                  <td>记录时间</td>
                  <td>
                    2018-04-08 13:58:10
                  </td>
                </tr>
                <tr>
                  <td>商户号</td>
                  <td>
                    1467791702
                  </td>
                </tr>
                <tr>
                  <td>商户密钥</td>
                  <td>
                    c3d9ecd13945a8932d5b0ed2bdd0872a
                  </td>
                </tr>
                <tr>
                  <td>
                    证书内容
                  </td>
                  <td class="elippse">
                    -----BEGIN CERTIFICATE----- MIIEbDCCA9WgAwIBAgIEAQEYWDANBgkqhkiG9w0BAQUFADCBijELMAkGA1UEBhMC
                    Q04xEjAQBgNVBAgTCUd1YW5nZG9uZzERMA8GA1UEBxMIU2hlbnpoZW4xEDAOBgNV
                    BAoTB1RlbmNlbnQxDDAKBgNVBAsTA1dYRzETMBEGA1UEAxMKTW1wYXltY2hDQTEf
                    MB0GCSqGSIb3DQEJARYQbW1wYXltY2hAdGVuY2VudDAeFw0xNzA1MTExMTAwMjFa
                    Fw0yNzA1MDkxMTAwMjFaMIGbMQswCQYDVQQGEwJDTjESMBAGA1UECBMJR3Vhbmdk
                    b25nMREwDwYDVQQHEwhTaGVuemhlbjEQMA4GA1UEChMHVGVuY2VudDEOMAwGA1UE
                    CxMFTU1QYXkxMDAuBgNVBAMUJ+WMl+S6rOW+rumTuuWunee9kee7nOenkeaKgOac
                    iemZkOWFrOWPuDERMA8GA1UEBBMIMjU3NzU5MjMwggEiMA0GCSqGSIb3DQEBAQUA
                    A4IBDwAwggEKAoIBAQC5bic0MQToG4WTx8RREsl9cVJ3WUHsXd5Fiv0VlCvUKIH7
                    xh5crMGdBBDL//QyUZ9LptVzlDY18RO7HoMWQWF/0XXaQS3kpN5C/mhOBuMIE9uR
                    80dHy7d1/WDD/OhuIedkwlBipCd9+IOI8OFrynNzEWMJd1Jbj1U7fckvLxS2sOth
                    NZFGXzKHABXvoxAdjFS4n8JGHXrdwKoTXwpnpdV4KaIuktL8JKqLNvEPHneUlQXX
                    V4c1SemLdWuzx+zixFVNL5gUOY+1VCfJfxBIAOx4TDwlcg5+dJbAItbb7+Cp2CkL
                    BkNKdDm4+VNNCnoV+CEH7CBj8//Bhc74CQCsdwlzAgMBAAGjggFGMIIBQjAJBgNV
                    HRMEAjAAMCwGCWCGSAGG+EIBDQQfFh0iQ0VTLUNBIEdlbmVyYXRlIENlcnRpZmlj
                    YXRlIjAdBgNVHQ4EFgQUxHMjaujMdlZie9+gDTuQBVUE8EQwgb8GA1UdIwSBtzCB
                    tIAUPgUm9iJitBVbiM1kfrDUYqflhnShgZCkgY0wgYoxCzAJBgNVBAYTAkNOMRIw
                    EAYDVQQIEwlHdWFuZ2RvbmcxETAPBgNVBAcTCFNoZW56aGVuMRAwDgYDVQQKEwdU
                    ZW5jZW50MQwwCgYDVQQLEwNXWEcxEzARBgNVBAMTCk1tcGF5bWNoQ0ExHzAdBgkq
                    hkiG9w0BCQEWEG1tcGF5bWNoQHRlbmNlbnSCCQC7VJcrvADoVzAOBgNVHQ8BAf8E
                    BAMCBsAwFgYDVR0lAQH/BAwwCgYIKwYBBQUHAwIwDQYJKoZIhvcNAQEFBQADgYEA
                    LVGi2PxeWl9XqTQwARvzcOAvHYNIBaG32uoArN5MStI211zvCuTyOuI00S8b5uIN
                    Q2AkLaLNaNQGqmtI8skadCcyCyPbx2uD5L26L+IqD0FP4+uLo0S/eULD5PcC6wyo
                    2eElkNwWtffwcyi9CRqGaHrZnzA87Y7GWU2v+QANas0= -----END CERTIFICATE-----

                  </td>
                </tr>
                <tr>
                  <td>
                    私钥内容
                  </td>
                  <td class="elippse">
                    -----BEGIN CERTIFICATE----- MIIEbDCCA9WgAwIBAgIEAQEYWDANBgkqhkiG9w0BAQUFADCBijELMAkGA1UEBhMC
                    Q04xEjAQBgNVBAgTCUd1YW5nZG9uZzERMA8GA1UEBxMIU2hlbnpoZW4xEDAOBgNV
                    BAoTB1RlbmNlbnQxDDAKBgNVBAsTA1dYRzETMBEGA1UEAxMKTW1wYXltY2hDQTEf
                    MB0GCSqGSIb3DQEJARYQbW1wYXltY2hAdGVuY2VudDAeFw0xNzA1MTExMTAwMjFa
                    Fw0yNzA1MDkxMTAwMjFaMIGbMQswCQYDVQQGEwJDTjESMBAGA1UECBMJR3Vhbmdk
                    b25nMREwDwYDVQQHEwhTaGVuemhlbjEQMA4GA1UEChMHVGVuY2VudDEOMAwGA1UE
                    CxMFTU1QYXkxMDAuBgNVBAMUJ+WMl+S6rOW+rumTuuWunee9kee7nOenkeaKgOac
                    iemZkOWFrOWPuDERMA8GA1UEBBMIMjU3NzU5MjMwggEiMA0GCSqGSIb3DQEBAQUA
                    A4IBDwAwggEKAoIBAQC5bic0MQToG4WTx8RREsl9cVJ3WUHsXd5Fiv0VlCvUKIH7
                    xh5crMGdBBDL//QyUZ9LptVzlDY18RO7HoMWQWF/0XXaQS3kpN5C/mhOBuMIE9uR
                    80dHy7d1/WDD/OhuIedkwlBipCd9+IOI8OFrynNzEWMJd1Jbj1U7fckvLxS2sOth
                    NZFGXzKHABXvoxAdjFS4n8JGHXrdwKoTXwpnpdV4KaIuktL8JKqLNvEPHneUlQXX
                    V4c1SemLdWuzx+zixFVNL5gUOY+1VCfJfxBIAOx4TDwlcg5+dJbAItbb7+Cp2CkL
                    BkNKdDm4+VNNCnoV+CEH7CBj8//Bhc74CQCsdwlzAgMBAAGjggFGMIIBQjAJBgNV
                    HRMEAjAAMCwGCWCGSAGG+EIBDQQfFh0iQ0VTLUNBIEdlbmVyYXRlIENlcnRpZmlj
                    YXRlIjAdBgNVHQ4EFgQUxHMjaujMdlZie9+gDTuQBVUE8EQwgb8GA1UdIwSBtzCB
                    tIAUPgUm9iJitBVbiM1kfrDUYqflhnShgZCkgY0wgYoxCzAJBgNVBAYTAkNOMRIw
                    EAYDVQQIEwlHdWFuZ2RvbmcxETAPBgNVBAcTCFNoZW56aGVuMRAwDgYDVQQKEwdU
                    ZW5jZW50MQwwCgYDVQQLEwNXWEcxEzARBgNVBAMTCk1tcGF5bWNoQ0ExHzAdBgkq
                    hkiG9w0BCQEWEG1tcGF5bWNoQHRlbmNlbnSCCQC7VJcrvADoVzAOBgNVHQ8BAf8E
                    BAMCBsAwFgYDVR0lAQH/BAwwCgYIKwYBBQUHAwIwDQYJKoZIhvcNAQEFBQADgYEA
                    LVGi2PxeWl9XqTQwARvzcOAvHYNIBaG32uoArN5MStI211zvCuTyOuI00S8b5uIN
                    Q2AkLaLNaNQGqmtI8skadCcyCyPbx2uD5L26L+IqD0FP4+uLo0S/eULD5PcC6wyo
                    2eElkNwWtffwcyi9CRqGaHrZnzA87Y7GWU2v+QANas0= -----END CERTIFICATE-----

                  </td>
                </tr>
                <tr>
                  <td>
                    私钥内容
                  </td>
                  <td class="pd-10">
                    <div class="warning-text">
                      谨慎设置支付方式，设置之前，请确认商户支付已经申请成功。
                    </div>
                    <table class="min-table">
                      <tr>
                        <td>
                          支付方式:
                        </td>
                        <td>
                          微信直连支付
                        </td>
                      </tr>
                      <tr>
                        <td>
                          支付配置:
                        </td>
                        <td>
                          无额外配置
                        </td>
                      </tr>
                    </table>
                    <div class="btn-wrap">
                      <el-button type="primary" size="small">设置支付方式</el-button>
                    </div>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </el-col>
        <!--授权基本信息结束-->

        <!--上传审核发布信息 开始-->
        <el-col :lg="12" :sm="24">
          <div class="details-item">
            <div class="item-header">
                <span class="title">
                  上传审核发布信息
                </span>
              <router-link to="#" class="td-inner-link">查看版本操作日志</router-link>
              <el-button size="small" type="primary">
                一键提交审核
              </el-button>
            </div>
            <div class="item-content">
              <table class="item-table">
                <tbody>
                <tr>
                  <td>设置服务器域名</td>
                  <td>
                    1
                    <el-button size="small" type="primary">修改域名</el-button>
                  </td>
                </tr>
                <tr>
                  <td>绑定代码模板ID</td>
                  <td>
                    214
                    <el-button
                      size="small"
                      type="primary">
                      上传代码 （模板ID: 215）
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>
                    上传代码状态
                  </td>
                  <td>
                    已上传
                  </td>
                </tr>
                <tr>
                  <td>最后上传代码时间</td>
                  <td>
                    2019-08-01 10:50:04
                  </td>
                </tr>
                <tr>
                  <td>小程序体验者</td>
                  <td style="padding-top: 5px;">
                    <el-tag
                      v-for="(tag,index) in tagOpt"
                      :key="index"
                      type="warning"
                      size="small"
                      closable
                      @close="handleClose(index)"
                      class="tag-item"
                    >
                      {{tag['name']}}
                    </el-tag>
                    <el-button
                      size="small"
                      type="primary"
                    >
                      添加微信体验者
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>体验二维码</td>
                  <td>
                    <el-avatar
                      style="vertical-align: middle"
                      shape="square"
                      :size="150"
                      fit="fit"
                      src="http://mpdevimg2.weipubao.cn/upload/saas/mp/qr/wxeaeb5c37a376f415.jpg"/>
                    <el-button
                      style="vertical-align: middle"
                      size="small"
                      type="primary"
                    >
                      获取二维码
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>可选类目</td>
                  <td>
                    Array ( [0] => Array ( [first_class] => IT科技 [second_class] => 硬件与设备 [first_id] => 210 [second_id]
                    => 211 ) )
                    <el-button
                      size="small"
                      type="primary">
                      获取可选类目
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>页面配置</td>
                  <td>
                    Array ( [0] => pages/bottom/bottom [1] => pages/agreement/agreement [2] => pages/account/account [3]
                    => pages/appointcomment/appointcomment [4] => pages/appointinfo/appointinfo [5] =>
                    pages/appointlist/appointlist [6] => pages/appointment/appointment [7] =>
                    pages/appointorder/appointorder [8] => pages/cart/cart [9] => pages/cardpay/cardpay [10] =>
                    pages/checkout/checkout [11] => pages/collect/collect [12] => pages/comment/comment [13] =>
                    pages/couponlist/couponlist [14] => pages/express/express [15] => pages/getcoupon/getcoupon [16] =>
                    pages/goodscomment/goodscomment [17] => pages/groupbuyitem/groupbuyitem [18] =>
                    pages/groupbuyinfo/groupbuyinfo [19] => pages/groupbuyrule/groupbuyrule [20] => pages/index/index
                    [21] => pages/integral/integral [22] => pages/item/item [23] => pages/orderinfo/orderinfo [24] =>
                    pages/orderlist/orderlist [25] => pages/return_order_list/return_order_list [26] =>
                    pages/returnorder/returnorder [27] => pages/searchs/search [28] =>
                    pages/servicecomment/servicecomment [29] => pages/splitcoupon/splitcoupon [30] =>
                    pages/splitinfo/splitinfo [31] => pages/storelist/storelist [32] => pages/storeinfo/storeinfo [33]
                    => pages/shopcheckout/shopcheckout [34] => pages/shoporderinfo/shoporderinfo [35] =>
                    pages/usercenter/usercenter [36] => pages/usercardlist/usercardlist [37] =>
                    pages/usercardinfo/usercardinfo [38] => pages/userinfo/userinfo )
                    <el-button
                      size="small"
                      type="primary">
                      获取页面配置
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>提交审核ID</td>
                  <td>
                    500454342
                    <el-button
                      size="small"
                      type="primary">
                      提交审核
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>
                    提交审核状态
                  </td>
                  <td>
                    审核成功
                  </td>
                </tr>
                <tr>
                  <td>提交审核时间</td>
                  <td>
                    2019-08-01 14:45:07
                  </td>
                </tr>
                <tr>
                  <td>审批成功时间</td>
                  <td>
                    2019-08-02 09:45:34
                  </td>
                </tr>
                <tr>
                  <td>审批失败原因</td>
                  <td v-html="'审批失败原因\t1:小程序内容不符合规则:<br>(1):你好，小程序【分类】页面无具体运营内容，请上架正式内容或商品（非测试）后重新提交审核。<br>'">

                  </td>
                </tr>
                <tr>
                  <td>发布代码状态</td>
                  <td>
                    <span>1</span>
                    <el-button
                      size="small"
                      type="primary"
                    >
                      发布代码
                    </el-button>
                  </td>
                </tr>
                <tr>
                  <td>发布时间</td>
                  <td>
                    2019-08-01 11:52:22
                  </td>
                </tr>
                <tr>
                  <td>最后取消授权时间</td>
                  <td>
                    2018-05-23 09:44:17
                  </td>
                </tr>

                </tbody>
              </table>
            </div>
          </div>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
export default {
  name: 'authMsg',
  data () {
    return {
      tagOpt: [ // 小程序微信体验者数据
        {
          name: 'gaoyuanyuan9554'
        },
        {
          name: 'wangyiboangel'
        },
        {
          name: 'chenxue_420'
        }
      ]
    }
  },
  methods: {
    handleClose (idx) {
      console.log(`删除的tag标签为${this.tagOpt[idx].name}`)
      this.tagOpt.splice(idx, 1)
    }
  }
}
</script>

<style scoped lang="scss">
  .authMsg {
    background: #e0e3ec;

    .handle-status {
      margin-bottom: 20px;
      padding: 10px;
      background: #efe1b3;
      border-left: 5px solid #dfb56c;
      color: #826430;
      font-size: 14px;
    }

    .details-wrap {
      margin-top: 10px;
      padding: 10px 10px 30px 10px;
    }

    .details-item {
      .item-header {
        padding: 10px 15px;
        border: 1px solid #ddd;
        background: #f5f5f5;

        .title {
          margin-right: 10px;
        }

        .td-inner-link {
          color: #333;
          text-decoration: none;

          &:hover {
            text-decoration: underline;
          }
        }
      }

      .item-content {
        border: 1px solid #ddd;
        border-top: none;
        padding: 15px;
        padding-bottom: 30px;

        .item-table {
          display: table;
          width: 100%;
          box-sizing: border-box;
          background: #fff;
          border-collapse: collapse;
          font-size: 14px;

          td {
            padding: 10px;
            border: 1px solid #ddd;
            vertical-align: middle;

            .td-item {
              margin-bottom: 2px;
            }
          }

          & > tbody > tr > td:first-child {
            min-width: 170px;
          }

          td.elippse {
            max-width: 31em;
            word-break: keep-all;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
          }

          .tag-item {
            margin-top: 5px;
            margin-right: 5px;
          }

          .warning-text {
            color: #b94a48;
          }

          .min-table {
            margin-top: 10px;
            width: 100%;

            tr {
              border-top: 1px solid #ddd;
            }

            td {
              border: none;
            }
          }

          .btn-wrap {
            margin-top: 30px;
          }
        }
      }
    }
  }
</style>
