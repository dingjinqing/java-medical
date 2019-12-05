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
                {{$t('programVersion.BasicInformation')}}
              </span>
              <el-button
                size="small"
                type="primary"
                @click="handleSureSetPay(9)"
              >
                {{$t('programVersion.UpdateInformation')}}
              </el-button>
            </div>
            <div class="item-content">
              <table
                class="item-table"
                width="100%"
              >
                <tbody>
                  <tr>
                    <td>{{$t('programVersion.ShopID')}}</td>
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
                      {{$t('programVersion.SubjectName')}}
                    </td>
                    <td>
                      {{this.dataList.principalName}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.nickName')}}</td>
                    <td>{{this.dataList.nickName}}</td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.UserName')}}</td>
                    <td>{{this.dataList.userName}}</td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.alias')}}</td>
                    <td>{{this.dataList.alias}}</td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.head')}}</td>
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
                    <td>{{$t('programVersion.ToProgramCode')}}</td>
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
                      {{$t('programVersion.WidgetRights')}}
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
                          {{$t('programVersion.beRevoked')}}
                        </el-tag>
                        <span>
                          {{item}}
                        </span>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.iscertified')}}</td>
                    <td>

                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.verifyTypeInfo==='0'"
                      >
                        {{$t('programVersion.certified')}}
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.verifyTypeInfo==='1'"
                      >
                        {{$t('programVersion.uncertified')}}
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.SupportWechatPayment')}}</td>
                    <td>
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.openPay===1"
                      >
                        {{$t('programVersion.yes')}}
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.openPay===0"
                      >
                        {{$t('programVersion.no')}}
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.supportVouchers')}}</td>
                    <td>
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.openCard===1"
                      >
                        {{$t('programVersion.yes')}}
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.openCard===0"
                      >
                        {{$t('programVersion.no')}}
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.ToAuthorize')}}</td>
                    <td>
                      <el-tag
                        type="primary"
                        size="small"
                        effect="dark"
                        v-if="dataList.isAuthOk===1"
                      >
                        {{$t('programVersion.yes')}}
                      </el-tag>
                      <el-tag
                        type="danger"
                        size="small"
                        effect="dark"
                        v-if="dataList.isAuthOk===0"
                      >
                        {{$t('programVersion.no')}}
                      </el-tag>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.authorizationTime')}}</td>
                    <td>
                      {{this.dataList.lastAuthTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.FauthorizationTime')}}</td>
                    <td>
                      {{this.dataList.lastCancelAuthTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.RecordingTime')}}</td>
                    <td>
                      {{this.dataList.createTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.BusinessName')}}</td>
                    <td>
                      {{this.dataList.payMchId}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.BusinessKey')}}</td>
                    <td>
                      {{this.dataList.payKey}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      {{$t('programVersion.CertificateContent')}}
                    </td>
                    <td class="elippse">
                      {{this.dataList.payCertContent}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      {{$t('programVersion.PrivateKeyContent')}}
                    </td>
                    <td class="elippse">
                      {{this.dataList.payKeyContent}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      {{$t('programVersion.PrivateKeyContent')}}
                    </td>
                    <td class="pd-10">
                      <div class="warning-text">
                        {{$t('programVersion.keyTips')}}
                      </div>
                      <table class="min-table">
                        <tr>
                          <td>
                            {{$t('programVersion.PaymentMethod')}}:
                          </td>
                          <td>
                            <span v-if="dataList.isSubMerchant===0">{{$t('programVersion.SubMerchantModel')}}</span>
                            <span v-if="dataList.isSubMerchant===1">{{$t('programVersion.NonSubMerchants')}}</span>
                            <span v-if="dataList.isSubMerchant===2">{{$t('programVersion.WeipuBaoziMerchants')}}</span>
                            <span v-if="dataList.isSubMerchant===3">{{$t('programVersion.UnicomPaymentSubmerchants')}}</span>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            {{$t('programVersion.PaymentConfiguration')}}:
                          </td>
                          <td>
                            <span v-if="dataList.isSubMerchant===0 || dataList.isSubMerchant===1">{{$t('programVersion.NoConfiguration')}}</span>
                            <div v-if="dataList.isSubMerchant===2">
                              <div>{{$t('programVersion.UnicomPaymentSubmerchant')}}{{this.dataList.unionPayAppId}}</div>
                              <div>{{$t('programVersion.UnicomPaymentNumber')}}{{this.dataList.unionPayCusId}}</div>
                              <div>{{$t('programVersion.UnicomSubmerchantKey')}}{{this.dataList.unionPayAppKey}}</div>
                            </div>
                            <div v-if="dataList.isSubMerchant===3">
                              <div>{{$t('programVersion.MCCCode')}}{{this.dataList.merchantCategoryCode}}</div>
                              <div>{{$t('programVersion.PricedCurrencies')}}{{this.dataList.feeType}}</div>
                            </div>
                          </td>
                        </tr>
                      </table>
                      <div class="btn-wrap">
                        <el-button
                          type="primary"
                          size="small"
                          @click="handleSetPay(dataList)"
                        >{{$t('programVersion.SettingPaymentMethod')}}</el-button>
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
                {{$t('programVersion.UploadReleaseInformation')}}
              </span>
              <router-link
                to="#"
                class="td-inner-link"
              >{{$t('programVersion.ViewVersionOperationsLog')}}</router-link>
              <el-button
                size="small"
                type="primary"
                @click="handleSureSetPay(7)"
              >
                {{$t('programVersion.clickSubmissionAudit')}}
              </el-button>
            </div>
            <div class="item-content">
              <table
                class="item-table"
                width="100%"
              >
                <tbody>
                  <tr>
                    <td>{{$t('programVersion.SettingServerDomainName')}}</td>
                    <td>
                      {{this.dataList.isModifyDomain}}
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(3)"
                      >{{$t('programVersion.ModifyDomaiName')}}</el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.BindingCodeTemplateID')}}</td>
                    <td>
                      {{this.dataList.bindTemplateId}}
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(10)"
                      >
                        {{$t('programVersion.UploadCode')}} （{{$t('programVersion.TemplateID')}}: {{this.tem_id}}）
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      {{$t('programVersion.UploadCodeStatus')}}
                    </td>
                    <td>
                      <span v-if="dataList.uploadState===0">{{$t('programVersion.NotUploaded')}}</span>
                      <span v-if="dataList.uploadState===1">{{$t('programVersion.Uploaded')}}</span>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.LastUploadCodeTime')}}</td>
                    <td>
                      {{this.dataList.lastUploadTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.SmallProgramExperiencer')}}</td>
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
                        {{$t('programVersion.AddingWechatExperiencer')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.ExperienceCode')}}</td>
                    <td>
                      <img
                        style="wdith:150px;height:150px"
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
                        @click="handleSureSetPay(4)"
                      >
                        {{$t('programVersion.AcquisitionCodes')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.OptionalCategory')}}</td>
                    <td style="max-width: 28em">
                      <span class="warp">{{this.dataList.category}} </span>
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(5)"
                      >
                        {{$t('programVersion.GetOptionalCategories')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.PageConfiguration')}}</td>
                    <td style="max-width: 28em">
                      <span class="warp">{{this.dataList.pageCfg}}</span>

                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(6)"
                      >
                        {{$t('programVersion.GetPageConfiguration')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.SubmitAuditID')}}</td>
                    <td>
                      {{this.dataList.auditId}}
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(7)"
                      >
                        {{$t('programVersion.SubmiAudit')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      {{$t('programVersion.SubmitAuditstatus')}}
                    </td>
                    <td>
                      {{this.dataList.auditState}}
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(11)"
                      >
                        {{$t('programVersion.RefreshAuditStatus')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.AuditSubmisSiontime')}}</td>
                    <td>
                      {{this.dataList.submitAuditTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.ApprovalSuccessTime')}}</td>
                    <td>
                      {{this.dataList.auditOkTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.ReasonsFailureApproval')}}</td>
                    <td v-html="dataList.auditFailReason">

                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.PublishCodeStatus')}}</td>
                    <td>
                      <span>{{this.dataList.auditOkTime}}</span>
                      <el-button
                        size="small"
                        type="primary"
                        @click="handleSureSetPay(8)"
                      >
                        {{$t('programVersion.PublishCode')}}
                      </el-button>
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.ReleaseTime')}}</td>
                    <td>
                      {{dataList.publishTime}}
                    </td>
                  </tr>
                  <tr>
                    <td>{{$t('programVersion.FauthorizationTime')}}</td>
                    <td>
                      {{dataList.lastCancelAuthTime}}
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
        :title="$t('programVersion.SettingPaymentMethod')"
        :visible.sync="dialogVisible"
        width="25%"
      >
        <div
          class="payDialogDiv first"
          :class="payDialogDivEn"
        >
          <span style="margin-right:22px">{{$t('programVersion.PaymentSettings')}}</span>
          <el-select
            v-model="value"
            :placeholder="$t('programVersion.placeChoise')"
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
          <div
            class="payDialogDiv"
            :class="payDialogDivEn"
          >
            <span>{{$t('programVersion.MerchantAPPID')}} </span>
            <el-input
              v-model="appidinput"
              size="small"
              :placeholder="$t('programVersion.PleaseContent')"
            ></el-input>
          </div>
          <div
            class="payDialogDiv sp"
            :class="payDialogDivEn"
          >
            <span>{{$t('programVersion.BusinessName')}}： </span>
            <el-input
              v-model="shnuminput"
              size="small"
              :placeholder="$t('programVersion.PleaseContent')"
            ></el-input>
          </div>
          <div
            class="payDialogDiv sp"
            :class="payDialogDivEnLast"
          >
            <span>{{$t('programVersion.BusinessKey')}}： </span>
            <el-input
              v-model="myinput"
              size="small"
              :placeholder="$t('programVersion.PleaseContent')"
            ></el-input>
          </div>
        </div>
        <div v-if="value === '3'">
          <div
            class="payDialogDiv mcc"
            :class="payDialogDivEnhiddenTwo"
          >
            <span>{{$t('programVersion.MCCCode')}} </span>
            <el-input
              v-model="mccinput"
              size="small"
              :placeholder="$t('programVersion.PleaseContent')"
            ></el-input>
          </div>
          <div
            class="payDialogDiv bz"
            :class="payDialogDivEn"
          >
            <span>{{$t('programVersion.PricedCurrencies')}} </span>
            <el-input
              v-model="bzinput"
              size="small"
              :placeholder="$t('programVersion.PleaseContent')"
            ></el-input>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">{{$t('programVersion.cancel')}}</el-button>
          <el-button
            type="primary"
            @click="handleSureSetPay(0)"
          >{{$t('programVersion.Sure')}}</el-button>
        </span>
      </el-dialog>
    </div>
    <!--添加小程序体验者弹窗-->
    <div class="smallProgramDialog">
      <el-dialog
        :title="$t('programVersion.Tips')"
        :visible.sync="smallDialogVisible"
        width="25%"
      >
        <div style="margin-bottom:10px">{{$t('programVersion.PleaseEnterMicroSignal')}}</div>
        <el-input
          v-model="tyinput"
          :placeholder="$t('programVersion.PleaseContent')"
        ></el-input>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="smallDialogVisible = false">{{$t('programVersion.cancel')}}</el-button>
          <el-button
            type="primary"
            @click="handleClickSureTy()"
          >{{$t('programVersion.Sure')}}</el-button>
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
      options: this.$t('programVersion.options'),
      value: '',
      appidinput: '',
      shnuminput: '',
      myinput: '',
      mccinput: '',
      bzinput: '',
      tyinput: '',
      opStatus: this.$t('programVersion.SuccessfulOperation'),
      tem_id: '',
      templateId: '',
      payDialogDivEn: '',
      payDialogDivEnLast: '',
      payDialogDivEnhiddenTwo: ''
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      console.log(this.$route)
      let appId = this.$route.params.appId
      console.log(typeof appId)
      authInformationRequest(appId).then((res) => {
        if (res.error === 0) {
          let arr = []
          JSON.parse(res.content.funcInfo).map((item, index) => {
            let str = ''
            switch (item) {
              case 17:
                str = this.$t('programVersion.AccountManagementRights')
                arr.push(str)
                break
              case 18:
                str = this.$t('programVersion.DevelopmentAnalysisAuthority')
                arr.push(str)
                break
              case 19:
                str = this.$t('programVersion.CustomerManagementAuthority')
                arr.push(str)
                break
              case 25:
                str = this.$t('programVersion.ManagementAuthority')
                arr.push(str)
                break
              case 30:
                str = this.$t('programVersion.BasicSettingSmallPrograms')
                arr.push(str)
                break
              case 31:
                str = this.$t('programVersion.WidgetAuthenticationAuthority')
                arr.push(str)
                break
            }
          })
          switch (res.content.auditState) {
            case 0:
              res.content.auditState = this.$t('programVersion.notSubmitted')
              break
            case 1:
              res.content.auditState = this.$t('programVersion.auditProgress')
              break
            case 2:
              res.content.auditState = this.$t('programVersion.AuditSuccess')
              break
            case 3:
              res.content.auditState = this.$t('programVersion.auditFailure')
              break
          }

          console.log(arr)
          nowIdRequest(res.content.appId).then(res => {
            if (res.error === 0) {
              console.log(res.content.currentUseTemplateId)
              this.tem_id = res.content.currentUseTemplateId
            }
            console.log(res)
          })
          res.content.funcInfo = arr
          console.log(res.content)
          res.content.tester = JSON.parse(res.content.tester)
          this.dataList = res.content
        }

        console.log(res)
      })
    },
    handleClose (idx) {
      console.log(this.tagOpt)
      console.log(`删除的tag标签为${this.dataList.tester[idx]}`)
      this.tagOpt.splice(idx, 1)
      this.handleSureSetPay(2, this.dataList.tester[idx])
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
    handleSureSetPay (index, wechatid) {
      let obj = {}
      obj.appId = this.dataList.appId
      switch (index) {
        case 0:
          obj.act = 'setting-sub-merchant'
          console.log(this.value)
          obj.isSubMerchant = this.value
          if (this.value === '2') {
            obj.union_pay_app_id = this.appidinput
            obj.union_pay_cus_id = this.shnuminput
            obj.union_pay_app_key = this.myinput
          } else if (this.value === '3') {
            obj.merchant_category_code = this.mccinput
            if (this.bzinput) {
              obj.fee_type = this.bzinput
            } else {
              obj.fee_type = 'CNY'
            }
          }
          break
        case 1:
          obj.act = 'add-tester'
          obj.wechatId = this.tyinput
          break
        case 2:
          obj.act = 'remove-tester'
          obj.wechatId = wechatid
          break
        case 3:
          obj.act = 'modify-domain'
          break
        case 4:
          obj.act = 'get-test-qr'
          break
        case 5:
          obj.act = 'get-category'
          break
        case 6:
          obj.act = 'get-page'
          break
        case 7:
          obj.act = 'submit-audit'
          obj.templateId = this.tem_id
          break
        case 8:
          obj.act = 'publish-code'
          break
        case 9:
          obj.act = 'update-mp'
          break
        case 10:
          obj.act = 'upload-code'
          obj.appId = this.dataList.appId
          obj.templateId = this.tem_id
          break
        case 11:
          obj.act = 'refresh-audit-state'
          obj.appId = this.dataList.appId
          obj.wechatId = wechatid
      }
      publishSetRequest(obj).then(res => {
        console.log(res)
        if (res.error === 0 || res.error === -1) {
          this.tyinput = ''
          this.opStatus = res.content
          this.defaultData()
          this.dialogVisible = false
        }
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
      this.handleSureSetPay(1)
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
          text-align: left;
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
          max-width: 28em;
          word-break: keep-all;
          white-space: nowrap;
          text-overflow: ellipsis;
          overflow: hidden;
        }
        .warp {
          max-width: 27em;
          word-wrap: break-word;
          display: inline-block;
          overflow: hidden;
          overflow-wrap: break-word;
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
      /deep/ .el-input {
        width: auto;
      }
    }

    .sp {
      padding-left: 50px;
    }
    .first {
      padding-left: 10px;
      justify-content: flex-end;
    }
    .mcc {
      padding-left: 50px;
    }
    .bz {
      padding-left: 40px;
    }
    .payDialogDivEn {
      padding: 0 0 0 16px !important;
    }
    .payDialogDivEnLast {
      padding-left: 28px !important;
      display: flex;
      justify-content: flex-start;
    }
    .payDialogDivEnhiddenTwo {
      padding-left: 55px !important;
      display: flex;
      justify-content: flex-start;
    }
  }
}
</style>
