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
            >
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.Reauthorization')}}
            </el-button>
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.BindedPublicNumber')}}:
            </span>
            <span class="item-title ml-20">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.MicroshopEnterpriseService')}}
            </span>
            <span class="info-text ml-20">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.tip1')}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.SmallProgramVersion')}}:
            </span>
            <span class="item-title ml-20">
              {{data.bindUserVersion}}
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
              {{data.auditState}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.AuthorizationStatus')}}:
            </span>
            <span class="item-title ml-20">
              {{data.isAuthOk}}
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
              {{data.verifyTypeInfo}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              {{$t('ShopConfiguration.SmallProgramAuthorizationPage.OriginalID')}}:
            </span>
            <span class="item-title ml-20">
              {{data.userName}}
            </span>
          </li>
          <li class="details-item">
            <span class="item-label">
              AppID:
            </span>
            <span class="item-title ml-20">
              {{data.appId}}
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
                v-model="isShowOrderDetails"
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
                v-model="isShowGoodsDetails"
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
  </div>
</template>

<script>
import { checkGoodThingRequest, setGoodThingRequest } from '@/api/admin/basicConfiguration/shopConfig'
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
      isShowOrderDetails: false, // 是否展示订单详情页缩略图
      isShowGoodsDetails: false, // 是否显示商品详情页缩略图,
      data: {}
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$http.$on('handleToAuthData', res => {
        console.log(res)
        this.handleData(res)
      })
      checkGoodThingRequest().then(res => {
        if (res.error === 0) {
          if (res.content.enabeldWxShoppingList === '0') {
            this.queryData.switch = false
          } else {
            this.queryData.switch = true
          }
          switch (res.content.wxShoppingRecommend) {
            case '':
              this.isShowDetails = false
              this.isShowGoodsDetails = false
              break
            case '1':
              this.isShowDetails = true
              break
            case '2':
              this.isShowGoodsDetails = true
              break
            case '1,2':
              this.isShowDetails = true
              this.isShowGoodsDetails = true
          }
        }
        console.log(res)
      })
    },
    // 初始化数据处理
    handleData (res) {
      switch (res.auditState) {
        case 0:
          res.auditState = '未提交'
          break
        case 1:
          res.auditState = '审核中'
          break
        case 2:
          res.auditState = '审核中'
          break
        case 3:
          res.auditState = '审核失败'
          break
      }
      switch (res.isAuthOk) {
        case 0:
          res.isAuthOk = '未授权'
          break
        case 1:
          res.isAuthOk = '已授权'
      }
      switch (res.verifyTypeInfo) {
        case '-1':
          res.verifyTypeInfo = '未认证'
          break
        case '0':
          res.verifyTypeInfo = '微信认证'
      }
      if (res.isAuthOk === res.currentTemplateId) {
        this.bindshow = false
      } else {
        this.bindshow = true
      }
      this.data = res
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
            message: '设置成功',
            type: 'success'
          })
        }
      })
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
</style>
