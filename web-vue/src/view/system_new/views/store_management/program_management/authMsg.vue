<template>
  <div class="authMsg">
    <div class="bg-white pd-10">
      <div class="handle-status">
        {{this.opStatus}}
      </div>
    </div>

    <div class="details-wrap bg-white">
      <el-row :gutter="10">
        <el-col
          :lg="12"
          :sm="24"
        >
          <div class="details-item">
            <div class="item-header">
              <span class="title">
                授权基本信息
              </span>
              <el-button
                size="small"
                type="primary"
              >
                更新授权信息
              </el-button>
            </div>
            <div class="item-content">
              <table class="item-table">
                <tbody>
                  <tr>
                    <td>店铺ID</td>
                    <td>
                      {{this.dataList.shopId}}
                    </td>
                  </tr>
                  <tr>
                    <td>appid</td>
                    <td>{{this.dataList.appId}}</td>
                  </tr>
                  <tr>
                    <td>
                      主体名称
                    </td>
                    <td>
                      {{this.dataList.principalName}}
                    </td>
                  </tr>
                  <tr>
                    <td>昵称</td>
                    <td>{{this.dataList.nickName}}</td>
                  </tr>
                  <tr>
                    <td>用户名</td>
                    <td>{{this.dataList.userName}}</td>
                  </tr>
                  <tr>
                    <td>别名</td>
                    <td>{{this.dataList.alias}}</td>
                  </tr>
                  <tr>
                    <td>头像</td>
                    <td>
                      <img
                        class="imgClass"
                        :src="this.dataList.headImg"
                      >
                      <!-- <el-avatar
                        shape="square"
                        src="http://wx.qlogo.cn/mmopen/ibkKkoaQFco4LkwgLSc80z6O24h245qPMLL6znSwbPhEs5eLBcT07CXcXC0CzvohMts3N47SuUQsNmbMOPzbNsFrSGoj1Lfd8/0"
                      ></el-avatar> -->
                    </td>
                  </tr>
                  <tr>
                    <td>小程序二维码</td>
                    <td>
                      <img
                        style="width:52px"
                        :src="this.dataList.qrcodeUrl"
                      >
                      <!-- <el-avatar
                        shape="square"
                        src="http://wx.qlogo.cn/mmopen/ibkKkoaQFco4LkwgLSc80z6O24h245qPMLL6znSwbPhEs5eLBcT07CXcXC0CzvohMts3N47SuUQsNmbMOPzbNsFrSGoj1Lfd8/0"
                      ></el-avatar> -->
                    </td>
                  </tr>
                  <tr>
                    <td>
                      小程序授权权限
                    </td>
                    <td>
                      <div
                        class="td-item"
                        v-for="(item, index) in dataList.funcInfo"
                        :key="index"
                      >
                        <el-tag
                          type="primary"
                          size="small"
                          effect="dark"
                        >
                          已授权
                        </el-tag>
                        <span>
                          {{item}}
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
                        effect="dark"
                        v-if="dataList.verifyTypeInfo==='0'"
                      >
                        已认证
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.verifyTypeInfo==='1'"
                      >
                        未认证
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>是否支持微信支付</td>
                    <td>
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.openPay===1"
                      >
                        是
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.openPay===0"
                      >
                        否
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>是否支持卡券</td>
                    <td>
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.openCard===1"
                      >
                        是
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.openCard===0"
                      >
                        否
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>是否授权</td>
                    <td>
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.isAuthOk===1"
                      >
                        是
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.isAuthOk===0"
                      >
                        否
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>最后授权时间</td>
                    <td>
                      {{this.dataList.lastAuthTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>最后取消授权时间</td>
                    <td>
                      {{this.dataList.lastCancelAuthTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>记录时间</td>
                    <td>
                      {{this.dataList.createTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>商户号</td>
                    <td>
                      {{this.dataList.payMchId}}
                    </td>
                  </tr>
                  <tr>
                    <td>商户密钥</td>
                    <td>
                      {{this.dataList.payKey}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      证书内容
                    </td>
                    <td class="elippse">
                      {{this.dataList.payCertContent}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      私钥内容
                    </td>
                    <td class="elippse">
                      {{this.dataList.payKeyContent}}
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
                            <span v-if="dataList.isSubMerchant===0">子商户模式</span>
                            <span v-if="dataList.isSubMerchant===1">非子商户</span>
                            <span v-if="dataList.isSubMerchant===2">微铺宝子商户</span>
                            <span v-if="dataList.isSubMerchant===3">通联支付子商户</span>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            支付配置:
                          </td>
                          <td>
                            <span v-if="dataList.isSubMerchant===0 || dataList.isSubMerchant===1">无额外配置</span>
                            <div v-if="dataList.isSubMerchant===2">
                              <div>通联支付子商户APPID：{{this.dataList.unionPayAppId}}</div>
                              <div>通联支付子商户商户号：{{this.dataList.unionPayCusId}}</div>
                              <div>通联支付子商户密钥：{{this.dataList.unionPayAppKey}}</div>
                            </div>
                            <div v-if="dataList.isSubMerchant===3">
                              <div>MCC码：{{this.dataList.merchantCategoryCode}}</div>
                              <div>标价币种：{{this.dataList.feeType}}</div>
                            </div>
                          </td>
                        </tr>
                      </table>
                      <div class="btn-wrap">
                        <el-button
                          type="primary"
                          size="small"
                          @click="handleSetPay(dataList)"
                        >设置支付方式</el-button>
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
        <el-col
          :lg="12"
          :sm="24"
        >
          <div class="details-item">
            <div class="item-header">
              <span class="title">
                上传审核发布信息
              </span>
              <router-link
                to="#"
                class="td-inner-link"
              >查看版本操作日志</router-link>
              <el-button
                size="small"
                type="primary"
              >
                一键提交审核
              </el-button>
            </div>
            <div class="item-content">
              <table class="item-table">
                <tbody>
                  <tr>
                    <td>设置服务器域名</td>
                    <td>
                      {{this.dataList.isModifyDomain}}
                      <el-button
                        size="small"
                        type="primary"
                      >修改域名</el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>绑定代码模板ID</td>
                    <td>
                      {{this.dataList.bindTemplateId}}
                      <el-button
                        size="small"
                        type="primary"
                      >
                        上传代码 （模板ID: {{this.tem_id}}）
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      上传代码状态
                    </td>
                    <td>
                      <span v-if="dataList.uploadState===0">未上传</span>
                      <span v-if="dataList.uploadState===1">已上传</span>
                    </td>
                  </tr>
                  <tr>
                    <td>最后上传代码时间</td>
                    <td>
                      {{this.dataList.lastUploadTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>小程序体验者</td>
                    <td style="padding-top: 5px;">
                      <el-tag
                        v-for="(tag,index) in dataList.tester"
                        :key="index"
                        type="warning"
                        size="small"
                        closable
                        @close="handleClose(index)"
                        class="tag-item"
                      >
                        {{tag}}
                      </el-tag>
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleClickty()"
                      >
                        添加微信体验者
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>体验二维码</td>
                    <td>
                      <img
                        style="wdith:52px"
                        :src="dataList.testQrPath"
                      >
                      <!-- <el-avatar
                        style="vertical-align: middle"
                        shape="square"
                        :size="150"
                        fit="fit"
                        src="http://mpdevimg2.weipubao.cn/upload/saas/mp/qr/wxeaeb5c37a376f415.jpg"
                      /> -->

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
                        type="primary"
                      >
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
                        type="primary"
                      >
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
                        type="primary"
                      >
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
    <!--设置支付方式弹窗-->
    <div class="payDialog">
      <el-dialog
        title="设置支付方式"
        :visible.sync="dialogVisible"
        width="20%"
      >
        <div class="payDialogDiv first">
          <span>支付方式设置</span>
          <el-select
            v-model="value"
            placeholder="请选择"
            size="small"
            @change="handleSelect()"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div v-if="value === '2'">
          <div class="payDialogDiv">
            <span>商户APPID： </span>
            <el-input
              v-model="appidinput"
              size="small"
              placeholder="请输入内容"
            ></el-input>
          </div>
          <div class="payDialogDiv">
            <span>商户商户号： </span>
            <el-input
              v-model="shnuminput"
              size="small"
              placeholder="请输入内容"
            ></el-input>
          </div>
          <div class="payDialogDiv sp">
            <span>商户密钥： </span>
            <el-input
              v-model="myinput"
              size="small"
              placeholder="请输入内容"
            ></el-input>
          </div>
        </div>
        <div v-if="value === '3'">
          <div class="payDialogDiv mcc">
            <span>MCC码： </span>
            <el-input
              v-model="mccinput"
              size="small"
              placeholder="请输入内容"
            ></el-input>
          </div>
          <div class="payDialogDiv bz">
            <span>标价币种： </span>
            <el-input
              v-model="bzinput"
              size="small"
              placeholder="请输入内容"
            ></el-input>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="handleSureSetPay()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <!--添加小程序体验者弹窗-->
    <div class="smallProgramDialog">
      <el-dialog
        title="提示"
        :visible.sync="smallDialogVisible"
        width="25%"
      >
        <div style="margin-bottom:10px">请输入体验微信号</div>
        <el-input
          v-model="tyinput"
          placeholder="请输入内容"
        ></el-input>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="smallDialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="handleClickSureTy()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { authInformationRequest, nowIdRequest, publishSetRequest } from '@/api/system/programManage'
export default {
  name: 'authMsg',
  data () {
    return {
      smallDialogVisible: false,
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
      ],
      dataList: {

      },
      dialogVisible: false,
      options: [{
        value: '0',
        label: '微信直连支付'
      }, {
        value: '1',
        label: '微铺宝子商户支付'
      },
      {
        value: '2',
        label: '通联子商户支付'
      },
      {
        value: '3',
        label: '微信国际融合钱包支付'
      }],
      value: '微信直连支付',
      appidinput: '',
      shnuminput: '',
      myinput: '',
      mccinput: '',
      bzinput: '',
      tyinput: '',
      opStatus: '操作成功',
      tem_id: ''
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      console.log(this.$route)
      // let appId = this.$route.params.appId
      console.log(typeof appId)
      authInformationRequest('wx3a6cbd7a7735b683').then((res) => {
        if (res.error === 0) {
          let arr = []
          JSON.parse(res.content.funcInfo).map((item, index) => {
            let str = ''
            switch (item) {
              case 17:
                str = '帐号管理权限（小程序）'
                arr.push(str)
                break
              case 18:
                str = '开发管理与数据分析权限（小程序）'
                arr.push(str)
                break
              case 19:
                str = '客服消息管理权限（小程序）'
                arr.push(str)
                break
              case 25:
                str = '开放平台帐号管理权限（小程序）'
                arr.push(str)
                break
              case 30:
                str = '小程序基本信息设置权限'
                arr.push(str)
                break
              case 31:
                str = '小程序认证权限'
                arr.push(str)
                break
            }
          })
          console.log(arr)
          nowIdRequest(res.content.appId).then(res => {
            if (res.error === 0) {
              console.log(res.content.currentUseTemplateId)
              this.tem_id = res.content.currentUseTemplateId
            }
            console.log(res)
          })
          res.content.funcInfo = arr
          this.dataList = res.content
        }

        console.log(res)
      })
    },
    handleClose (idx) {
      console.log(`删除的tag标签为${this.tagOpt[idx].name}`)
      this.tagOpt.splice(idx, 1)
    },
    // 设置支付方式下拉框支付
    handleSelect () {
      console.log(this.value)
    },
    // 点击支付按钮
    handleSetPay (data) {
      console.log(this.unionPayAppId, this.unionPayCusId, this.unionPayAppKey, this.merchantCategoryCode, this.feeType)
      this.appidinput = this.unionPayAppId
      this.shnuminput = this.unionPayCusId
      this.myinput = this.unionPayAppKey
      this.mccinput = this.merchantCategoryCode
      this.bzinput = this.feeType
      this.dialogVisible = true
    },
    // 设置支付方式弹窗确定事件
    handleSureSetPay (index) {
      let obj = {}
      switch (index) {
        case 0:
          obj.act = 'setting-sub-merchant'
      }

      publishSetRequest(obj).then(res => {
        console.log(res)
      })
    },
    // 点击添加微信体验者
    handleClickty () {
      this.smallDialogVisible = true
    },
    // 添加微信体验者确定按钮点击
    handleClickSureTy () {
      let obj = {
        name: this.tyinput
      }
      this.tagOpt.push(obj)
      this.smallDialogVisible = false
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
        .imgClass {
          width: 50px;
          height: 50px;
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
  .payDialog {
    /deep/ .el-dialog__header {
      text-align: center;
      background-color: #f3f3f3;
    }
    /deep/ .el-dialog__title {
      font-size: 14px;
    }
    .payDialogDiv {
      border-top: 2px solid #ddd;
      height: 50px;
      display: flex;
      justify-content: flex-end;
      align-items: center;
      padding: 0 20px;
      span {
        margin-right: 10px;
        white-space: nowrap;
      }
      /deep/ .el-input__inner {
        width: 210px;
      }
    }
    .sp {
      padding-left: 50px;
    }
    .first {
      padding-left: 10px;
      justify-content: space-between;
    }
    .mcc {
      padding-left: 50px;
    }
    .bz {
      padding-left: 40px;
    }
  }
}
</style>
