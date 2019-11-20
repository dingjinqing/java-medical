<template>
  <div class="program_auth_details">
    <el-card>
      <div
        slot="header"
        class="__el-card-header"
      >
        {{$t('ShopConfiguration.SmallProgramAuthorizationPage.BindedApplet')}}
      </div>
      <div class="__el-card-content">
        <ul class="program-details">
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.WidgetName')}}:
            </span>
            <span class="item-title ml-20">
              {{this.data.nickName}}
            </span>
            <el-button
              size="mini"
              type="text"
              class="ml-20"
              style="font-size: 14px;"
              @click="refAuthSubmit()"
            >
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.Reauthorization')}}
            </el-button>
          </li>

          <li
            class="details-item"
            v-show="isEmpty(this.data.linkOfficialAppId)"
          >
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.chooseOffice')}}
            </span>
            <span class="item-title ml-20">
              <el-select
                size="mini"
                style="width: 140px;"
                v-model="appIdValue"
                :placeholder="$t('ShopConfiguration.SmallProgramAuthorizationPage.please')"
              >
                <el-option
                  v-for="(item, index) in this.officialList"
                  :key="index"
                  :value="item.value"
                  :label="item.label"
                >
                </el-option>

              </el-select>
            </span>
            <span class="info-text ml-20">
              <el-button
                type="text"
                @click="bind()"
              > {{$t('ShopConfiguration.SmallProgramAuthorizationPage.sure')}}</el-button>
            </span>
            <span
              class="ml-20"
              v-if="isEmpty(this.data.principalName)"
            >
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.firest')}}<el-button
                size="mini"
                type="text"
                class="ml-20"
                style="font-size: 14px;margin-left: 0px"
                @click="refAuthSubmit()"
              >
                {{$t('ShopConfiguration.SmallProgramAuthorizationPage.Reauthorization')}}
              </el-button>{{$t('ShopConfiguration.SmallProgramAuthorizationPage.firest2')}}
            </span>

            <span
              class="ml-20"
              v-if="this.official"
            >
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.noOffice')}}
              <router-link
                target="_blank"
                style="text-decoration:none;color:#66b1ff"
                :to="{path:'/admin/home/shopMain',query:{ change_components: '3'}}"
              > {{$t('ShopConfiguration.SmallProgramAuthorizationPage.noOffice2')}}</router-link>
            </span>

          </li>
          <li
            class="auth_p"
            v-show="isEmpty(this.data.linkOfficialAppId)"
          >
            <span>
              <p>{{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip7')}}</p>
            </span>
          </li>

          <li
            class="details-item"
            v-if="(!isEmpty(this.data.linkOfficialAppId))&&this.isAuthOk2===1"
          >
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.BindedPublicNumber')}}:
            </span>
            <span class="item-title ml-20">
              {{this.nickName}}
            </span>
          </li>

          <li
            class="details-item"
            v-if="(!isEmpty(this.data.linkOfficialAppId))&&this.isAuthOk2!==1"
          >
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.BindedPublicNumber')}}:
            </span>
            <span class="item-title ml-20">
              {{this.nickName}}
            </span>
            <span class="info-text ml-20">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip1')}}
            </span>
          </li>
          <li class="details-item moreHight">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.SmallProgramVersion')}}:
            </span>
            <span class="item-title ml-20">
              {{this.data.bindUserVersion}}
            </span>
            <span
              class="info-text ml-20"
              v-show="bindshow"
            >
              ({{$t('ShopConfiguration.SmallProgramAuthorizationPage.CurrentBindUserVersion')}} {{data.currentUserVersion}})
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.AuditStatus')}}:
            </span>
            <span class="item-title ml-20">
              {{this.data.auditStateTrans}}
            </span>
            <span
              class="item-title ml-20"
              v-if="this.reUpload"
            >
              <el-button
                type="text"
                @click="uploadAudit()"
              > {{$t('ShopConfiguration.SmallProgramAuthorizationPage.uploadAudit')}}</el-button>
            </span>
          </li>
          <li
            class="details-item"
            style="height: auto;"
            v-show="this.auditState===3"
          >
            <div style="position: absolute; ">
              <span class="item-label">
                {{$t('ShopConfiguration.SmallProgramAuthorizationPage.failReason')}}:
              </span>
            </div>

            <div
              class="auoDiv"
              style="color: red; width: auto;position: relative;"
            >
              <span>
                {{this.data.auditFailReason}}
              </span>
            </div>
          </li>

          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.AuthorizationStatus')}}:
            </span>
            <span class="item-title ml-20">
              {{this.data.isAuthOkTrans}}
            </span>
          </li>
          <li class="details-item img-row">
            <span class="item-label fll">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.SmallProgramAvatar')}}:
            </span>
            <img
              style="width:100px;margin-left:25px"
              :src="data.headImg"
            >
          </li>
          <li
            class="details-item img-row"
            style="height: 150px;"
          >
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.SmallProgramCode')}}:
            </span>
            <img
              style="width:100px;margin-left:25px"
              :src="data.qrcodeUrl"
            >
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.WechatAuthentication')}}:
            </span>
            <span class="item-title ml-20">
              {{this.data.verifyTypeInfoTrans}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.OriginalID')}}:
            </span>
            <span class="item-title ml-20">
              {{this.data.userName}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              AppID:
            </span>
            <span class="item-title ml-20">
              {{this.data.appId}}
            </span>
          </li>
        </ul>
      </div>
    </el-card>
    <el-card>
      <div
        slot="header"
        class="__el-card-header"
      >
        {{$t('ShopConfiguration.SmallProgramAuthorizationPage.WechatCircleSettings')}}
      </div>
      <div class="__el-card-content fun-opt">
        <el-switch
          style="height: 30px;"
          v-model="queryData.switch"
          active-color="#f38019"
        >
        </el-switch>
        <ul class="program-details ml-20">
          <li class="details-item flex-wrap">
            <span class="item-text c-999">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip2')}}
            </span>
            <el-button
              size="mini"
              class="item-text"
              style="font-size: 14px;"
              type="text"
            >
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.IntroductiontoFunctions')}}
            </el-button>
          </li>
          <li class="details-item">
            <strong>
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.Note')}}
            </strong>
            <span class="item-text c-999">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip3')}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-text">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip4')}}
            </span>
          </li>
          <li class="details-item flex-wrap">
            <div class="check-item">
              <el-checkbox
                v-model="queryData.isShowDetails"
                @change="handleChange"
              >
                {{$t('ShopConfiguration.SmallProgramAuthorizationPage.OrderDetailsPage')}}
              </el-checkbox>
              <el-popover
                placement="right"
                trigger="hover"
              >
                <img
                  :src="$imageHost + '/image/admin/new_preview_image/order_thing.jpg'"
                  class="thum-img"
                  alt=""
                >
                <el-button
                  slot="reference"
                  type="text"
                  class="fz-14 ml-10"
                  size="mini"
                >
                  {{$t('ShopConfiguration.SmallProgramAuthorizationPage.ViewExamples')}}
                </el-button>
              </el-popover>
            </div>

            <div class="check-item">
              <el-checkbox
                v-model="queryData.isShowGoodsDetails"
                @change="handleChange"
              >
                {{$t('ShopConfiguration.SmallProgramAuthorizationPage.CommodityDetailsPage')}}
              </el-checkbox>
              <el-popover
                placement="right"
                trigger="hover"
              >
                <img
                  src="http://mpdevimg2.weipubao.cn/image/admin/new_preview_image/goods_thing.jpg"
                  class="thum-img"
                  alt=""
                >
                <el-button
                  slot="reference"
                  type="text"
                  class="fz-14 ml-10"
                  size="mini"
                >
                  {{$t('ShopConfiguration.SmallProgramAuthorizationPage.ViewExamples')}}
                </el-button>
              </el-popover>
            </div>
          </li>
          <li class="details-item">
            <span class="item-text">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip5')}}
            </span>
            <div style="color: red;">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip6')}}
            </div>
          </li>
          <li style="margin-top: 50px;">
            <el-button
              size="small"
              type="primary"
              @click="handleToSave()"
            >{{$t('ShopConfiguration.SmallProgramAuthorizationPage.Preservation')}}</el-button>
          </li>
        </ul>
      </div>
    </el-card>
    <el-dialog
      :title="$t('ShopConfiguration.SmallProgramAuthorizationPage.prompt')"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <span>{{$t('ShopConfiguration.SmallProgramAuthorizationPage.ReauthorizationInfo')}}</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <a
          class="link"
          :href="hrefDataOne"
          target="_blank"
        >
          <el-button
            @click="centerDialogVisible = false"
            type="primary"
          >{{$t('ShopConfiguration.SmallProgramAuthorizationPage.Reauthorization')}}</el-button>
        </a>
        <el-button @click="centerDialogVisible = false">{{$t('ShopConfiguration.SmallProgramAuthorizationPage.cancel')}}</el-button>
      </span>

    </el-dialog>
  </div>
</template>

<script>
import { checkGoodThingRequest, setGoodThingRequest, grantAuthorizationRequest, bindOfficial, queryAuthdritionRequest, publishSetRequest } from '@/api/admin/basicConfiguration/shopConfig'
export default {
  name: 'program_auth_details',
  data () {
    return {
      bindshow: false,
      queryData: {
        switch: false,
        isShowDetails: false, // 订单详情页是否展示
        isShowGoodsDetails: false // 商品详情页是否展示
      },
      data: {},
      hrefDataOne: null,
      centerDialogVisible: false,
      appIdValue: null,
      isAuthOk2: null,
      nickName: null,
      reUpload: false,
      auditState1: [0, 3],
      auditState2: [0, 2, 3],
      auditState: null,
      official: false,
      officialList: [],
      data2: {},
      length: null,
      length2: null
    }
  },
  watch: {
    lang () {
      if (!this.isEmpty(this.data2.appId)) {
        console.log(333333 + this.lang)
        console.log(this.data2)
        this.handleData(this.data2)
        this.addOfficialList()
      }
      this.changeCss()
    }
  },
  mounted () {
    this.langDefault()
    this.defaultData()
    this.changeCss()
    // 初始化数据
  },
  methods: {
    defaultData () {
      this.$http.$on('handleToAuthData', res => {
        this.data2 = res
        if (!this.isEmpty(this.data2.appId)) {
          this.handleData(this.data2)
          this.addOfficialList()
        }
      })
      checkGoodThingRequest().then(res => {
        if (res.error === 0) {
          if (res.content.enabeldWxShoppingList === '0') {
            this.queryData.switch = false
          } else {
            this.queryData.switch = true
          }
          console.log('res.content.wxShoppingRecommend', res.content.wxShoppingRecommend)
          switch (res.content.wxShoppingRecommend) {
            case '':
              this.queryData.isShowDetails = false
              this.queryData.isShowGoodsDetails = false
              break
            case '1':
              this.queryData.isShowDetails = true
              break
            case '2':
              this.queryData.isShowGoodsDetails = true
              break
            case '1,2':
              this.queryData.isShowDetails = true
              this.queryData.isShowGoodsDetails = true
              break
          }
        }
      })
    },
    // 初始化数据处理
    handleData (res) {
      if (res.isAuthOk === 1 && (((res.currentTemplateId === res.bindTemplateId) && this.auditState1.includes(res.auditState)) || ((res.currentTemplateId !== res.bindTemplateId) && this.auditState2.includes(res.auditState)))) {
        this.reUpload = true
      }
      this.auditState = res.auditState
      console.log('handleData', this.lang)
      console.log('前', res.auditStateTrans)
      switch (res.auditState) {
        case 0:
          res.auditStateTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.auditState0')
          break
        case 1:
          res.auditStateTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.auditState1')
          break
        case 2:
          res.auditStateTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.auditState2')
          break
        case 3:
          res.auditStateTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.auditState3')
          break
      }
      console.log('后', res.auditStateTrans)
      if (res.officialAccount === null) {
        this.isAuthOk2 = null
        this.nickName = null
      } else {
        this.isAuthOk2 = res.officialAccount.isAuthOk
        this.nickName = res.officialAccount.nickName
      }
      if (this.isEmpty(res.officialList)) {
        this.official = true
      }
      switch (res.isAuthOk) {
        case 0:
          res.isAuthOkTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.isAuthOk0')
          break
        case 1:
          res.isAuthOkTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.isAuthOk1')
          break
      }
      switch (res.verifyTypeInfo) {
        case '-1':
          res.verifyTypeInfoTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.verifyType1')
          break
        case '0':
          res.verifyTypeInfoTrans = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.verifyType0')
          break
      }
      if (res.bindTemplateId === res.currentTemplateId) {
        this.bindshow = false
      } else {
        this.bindshow = true
      }
      if (!this.isEmpty(res.auditFailReason)) {
        res.auditFailReason = res.auditFailReason.replace(/<br>/g, '')
      }
      this.data = res
      console.log('真假', this.isEmpty(this.data.linkOfficialAppId))
      console.log('吼吼吼', this.data)
    },
    handleChange (val) {
      console.log(val)
    },
    // 保存点击
    handleToSave () {
      let params1 = ''
      let params2 = ''
      if (this.queryData.switch === false) {
        params1 = '0'
      } else {
        params1 = '1'
      }
      if (this.queryData.isShowDetails === false && this.queryData.isShowGoodsDetails === false) {
        params2 = ''
      } else if (this.queryData.isShowDetails === true && this.queryData.isShowGoodsDetails === false) {
        params2 = '1'
      } else if (this.queryData.isShowDetails === false && this.queryData.isShowGoodsDetails === true) {
        params2 = '2'
      } else if (this.queryData.isShowDetails === true && this.queryData.isShowGoodsDetails === true) {
        params2 = '1,2'
      }

      let obj = {
        'enabeldWxShoppingList': params1,
        'wxShoppingRecommend': params2
      }
      console.log(params1, params2)
      setGoodThingRequest(obj).then(res => {
        if (res.error === 0) {
          this.$message({
            message: this.$t('ShopConfiguration.SmallProgramAuthorizationPage.success'),
            type: 'success'
          })
        }
      })
    },
    refAuthSubmit () {
      this.centerDialogVisible = true
      grantAuthorizationRequest().then((res) => {
        if (res.error === 0) {
          console.log(res.content)
          this.hrefDataOne = res.content
        }
      })
    },
    bind () {
      if (this.isEmpty(this.appIdValue) && (this.isEmpty(this.data.linkOfficialAppId))) {
        this.$message.error(this.$t('ShopConfiguration.SmallProgramAuthorizationPage.needOffice'))
      }
      bindOfficial(this.appIdValue).then((res) => {
        if (res.error === 0) {
          this.$message.success(res.message)
          this.reflushData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    uploadAudit () {
      let data2 = {
        appId: this.data.appId,
        templateId: this.data.currentTemplateId
      }
      publishSetRequest(data2).then((res) => {
        if (res.error === 0) {
          console.log(res.content)
          this.$message.success(res.message)
          this.reflushData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    reflushData () {
      queryAuthdritionRequest().then(res => {
        if (res.error === 170016) {
          this.flag = false
        } else {
          this.flag = true
          if (res.error === 0) {
            if (window.location.href.split('/').pop() === 'authok') {
              this.activeName = 'third'
            }

            this.$http.$emit('handleToAuthData', res.content)
          }
          console.log(res)
        }
      })
    },
    addOfficialList () {
      let obj = this.data.officialList
      let arr = []
      let defaultObj = {}
      defaultObj.value = ''
      defaultObj.label = this.$t('ShopConfiguration.SmallProgramAuthorizationPage.please')
      arr.push(defaultObj)
      obj.map((item, index) => {
        let obj = {}
        obj.value = item.appId
        obj.label = item.nickName
        arr.push(obj)
      })
      this.officialList = arr
    },
    changeCss () {
      var className = document.getElementsByClassName('item-label')
      var className2 = document.getElementsByClassName('auth_p')
      var className3 = document.getElementsByClassName('auoDiv')
      if (this.lang === 'en_US') {
        this.length = '270px'
        this.length2 = '292px'
      } else {
        this.length = '170px'
        this.length2 = '192px'
      }

      className2[0].style.marginLeft = this.length2
      className3[0].style.marginLeft = this.length2
      for (var i = 0; i < className.length; i++) {
        className[i].style.width = this.length
      }
    }

  }
}
</script>

<style scoped lang="scss">
.program_auth_details {
  /deep/ .el-card__header {
    border-left: 2px solid #5a8bff;
    font-size: 14px;
    background: #eaedf4;
  }

  .program-details {
    font-size: 14px;
    .details-item {
      line-height: 30px;
      height: 30px;
      margin-bottom: 10px;

      strong {
        color: red;
      }

      .check-item {
        margin-right: 100px;
      }
    }

    .item-label {
      display: inline-block;
      width: 170px;
      text-align: right;
    }

    .info-text {
      color: #b94a48;
    }

    .img-row {
      height: 100px;
      display: flex;
      align-items: center;
    }
  }

  .fun-opt {
    display: flex;
    align-items: flex-start;
  }

  .c-999 {
    color: #999;
  }

  .flex-wrap {
    display: flex;
  }
}

.thum-img {
  display: block;
  width: 200px;
  height: 356px;
}
.auth_p {
  color: #999;
  margin-left: 192px;
  margin-bottom: 10px;
  margin-top: 10px;
}
.moreHight {
  margin-top: 10px;
}
</style>
