<template>
<div class="program_auth_details">
  <el-card>
    <div slot="header" class="__el-card-header">
      已绑定小程序
    </div>
    <div class="__el-card-content">
      <ul class="program-details">
        <li class="details-item">
          <span class="item-label">
            小程序名称:
          </span>
          <span class="item-title ml-20">
             微铺宝b2c商城
          </span>
          <el-button size="mini" type="text" class="ml-20" style="font-size: 14px;">
            重新授权
          </el-button>
        </li>
        <li class="details-item">
          <span class="item-label">
            已绑定的公众号:
          </span>
          <span class="item-title ml-20">
             微铺宝企业服务
          </span>
          <span class="info-text ml-20">
            此公众号已取消授权，无法给用户发送公众号消息、分销员返利佣金不能提现，如有需要，请重新授权
          </span>
        </li>
        <li class="details-item">
          <span class="item-label">
            小程序版本:
          </span>
          <span class="item-title ml-20">
             1.28.2
          </span>
        </li>
        <li class="details-item">
          <span class="item-label">
            审核状态:
          </span>
          <span class="item-title ml-20">
             审核成功
          </span>
        </li>
        <li class="details-item">
          <span class="item-label">
            授权状态:
          </span>
          <span class="item-title ml-20">
             已授权
          </span>
        </li>
        <li class="details-item img-row">
          <span class="item-label fll">
            小程序头像:
          </span>
          <el-avatar
            shape="square"
            :size="100"
            fit="cover"
            class="fll ml-20"
            src="http://wx.qlogo.cn/mmopen/ibkKkoaQFco4LkwgLSc80z6O24h245qPMLL6znSwbPhEs5eLBcT07CXcXC0CzvohMts3N47SuUQsNmbMOPzbNsFrSGoj1Lfd8/0" />
        </li>
        <li class="details-item img-row" style="height: 150px;">
          <span class="item-label">
            小程序码:
          </span>
          <el-avatar
            class="item-label ml-20"
            :size="150"
            shape="square"
            fit="cover"
            src="http://mpdevimg2.weipubao.cn/upload/7893594/qrcode/1/T1P0_20190806150150.jpg" />
        </li>
        <li class="details-item">
          <span class="item-label">
            微信认证:
          </span>
          <span class="item-title ml-20">
             已认证
          </span>
        </li>
        <li class="details-item">
          <span class="item-label">
            原始ID:
          </span>
          <span class="item-title ml-20">
             gh_b48e11dd4e99
          </span>
        </li>
        <li class="details-item">
          <span class="item-label">
            AppID:
          </span>
          <span class="item-title ml-20">
             wxeaeb5c37a376f415
          </span>
        </li>
      </ul>
    </div>
  </el-card>
  <el-card>
    <div slot="header" class="__el-card-header">
      微信好物圈功能设置
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
            开关开启，小程序前端可同步购物车商品及支付完成订单信息到微信好物圈，助力享有小程序搜索能力。
          </span>
          <el-button
            size="mini"
            class="item-text"
            style="font-size: 14px;"
            type="text">
            功能介绍
          </el-button>
        </li>
        <li class="details-item">
          <strong>
            注：
          </strong>
          <span class="item-text c-999">
            开启或关闭【好物圈】，需重新授权小程序并勾选/取消勾选好物圈权限，才能生效
          </span>
        </li>
        <li class="details-item">
          <span class="item-text">
            【好物推荐】在小程序端展示位置：
          </span>
        </li>
        <li class="details-item flex-wrap">
          <div class="check-item" >
            <el-checkbox v-model="queryData.isShowDetails" @change="handleChange">
              订单详情页
            </el-checkbox>
            <el-popover
              placement="right"
              trigger="hover"
              v-model="isShowOrderDetails">
              <img src="http://mpdevimg2.weipubao.cn/image/admin/new_preview_image/order_thing.jpg" class="thum-img" alt="">
              <el-button
                slot="reference"
                type="text"
                class="fz-14 ml-10"
                size="mini">
                查看示例
              </el-button>
            </el-popover>
          </div>

          <div class="check-item">
            <el-checkbox v-model="queryData.isShowGoodsDetails" @change="handleChange">
              商品详情页
            </el-checkbox>
            <el-popover
              placement="right"
              trigger="hover"
              v-model="isShowGoodsDetails">
              <img src="http://mpdevimg2.weipubao.cn/image/admin/new_preview_image/goods_thing.jpg" class="thum-img" alt="">
              <el-button
                slot="reference"
                type="text"
                class="fz-14 ml-10"
                size="mini">
                查看示例
              </el-button>
            </el-popover>
          </div>
        </li>
        <li class="details-item">
          <span class="item-text">
            当前【好物推荐】申请通过
          </span>
          <div style="color: red;">
            注：好物圈插件申请后，小程序公众平台后台不要关闭或者删除此插件，负责小程序将无法正常使用。若想关闭此功能，请联系客服、运营人员，协助操作。
          </div>
        </li>
        <li style="margin-top: 50px;">
          <el-button size="small" type="primary">保存</el-button>
        </li>
      </ul>
    </div>
  </el-card>
  <el-card>
    <div slot="header" class="__el-card-header">短信设置</div>
    <div class="flex-wrap">
      <el-input v-model="queryData.rechargeAmount" style="width: 200px;" size="small" />
      <el-button size="mini" type="text" class="fz-14">创建充值账号</el-button>
    </div>
  </el-card>
</div>
</template>

<script>
export default {
  name: 'program_auth_details',
  data () {
    return {
      queryData: {
        switch: false,
        isShowDetails: false, // 订单详情页是否展示
        isShowGoodsDetails: false, // 商品详情页是否展示
        rechargeAmount: '' // 充值金额
      },
      isShowOrderDetails: false, // 是否展示订单详情页缩略图
      isShowGoodsDetails: false // 是否显示商品详情页缩略图
    }
  },
  methods: {
    handleChange (val) {
      console.log(val)
    }
  }
}
</script>

<style scoped lang="scss">
  .program_auth_details {
    padding: 10px;
    background: #e0e3ec;

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
