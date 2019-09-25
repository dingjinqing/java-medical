<template>
  <div class="tradeProcessConfigure">
    <!-- 配送方式卡片区域   -->
    <el-card class="deliverMethods">
      <div
        v-for="item in deliverMethods"
        :key="item.name"
        class="deliverContent"
      >
        <span>{{item.name}}</span>
        <el-switch
          v-model="item.value"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span>{{item.value?'已开启':'已关闭'}}</span>
        <span>{{item.title}}</span>
        <span
          v-if="item.name==='自提'"
          class="takeByself"
          @click="handleTake"
        >设置自提门店</span>
      </div>
    </el-card>

    <!-- 待付款订单取消时间设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        待付款订单取消时间设置
      </div>
      <div class="settingContent">
        <span>拍下未付款订单</span>
        <el-input
          size="mini"
          class="inputWidth"
        ></el-input>
        <span>小时</span>
        <el-input
          size="mini"
          class="inputWidth"
        ></el-input>
        <span>分钟内未付款，自动取消订单</span>
      </div>
    </section>

    <!-- 发货后自动确认收货时间设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        发货后自动确认收货时间设置
      </div>
      <div class="settingContent">
        发货后
        <el-input
          size="mini"
          class="inputWidth"
        ></el-input>
        天，自动确认收货
      </div>
    </section>

    <!-- 确认收货后自动订单完成时间设置(订单完成则不可退换货) -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        确认收货后自动订单完成时间设置(订单完成则不可退换货)
      </div>
      <div class="settingContent">
        确认收货后
        <el-input
          size="mini"
          class="inputWidth"
        ></el-input>
        天，订单完成
      </div>
    </section>

    <!-- 申请延长收货配置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        申请延长收货配置
      </div>
      <div class="settingContent delay top">
        <el-switch
          v-model="value"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.value?'已开启':'已关闭'}}</span>
        <span style="color:#999;margin-left: 15px">开关开启，用户可在前端申请延长收货时间</span>
      </div>
      <div class="settingContent delay bottom">
        用户对单笔订单可申请一次延长收货时间，申请后可延长
        <el-input
          size="mini"
          class="inputWidth"
        ></el-input>
        天
      </div>
    </section>

    <!-- 发票展示设置、服务条款设置 -->
    <section
      v-for="item in options"
      :key="item.name"
      class="settingWrapper"
    >
      <div class="title">
        <span></span>
        {{item.title}}
      </div>
      <div class="settingContent">
        <span>{{item.title}}</span>
        <el-switch
          v-model="item.value"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{item.value?'已开启':'已关闭'}}</span>
        <span style="color:#999;margin-left: 15px">开关开启，{{item.content}}</span>
      </div>
    </section>

    <!-- 下单必填信息设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        下单必填信息设置
      </div>
      <div
        v-for="item in isRequiredInfo"
        :key="item.info"
        class="settingContent"
        style="display:flex"
      >
        <span style="display: block;width: 130px;">{{item.info}}</span>
        <el-switch
          v-model="item.value"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin:0 10px;height:60px;line-height:60px"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{item.value?'已开启':'已关闭'}}</span>
        <span
          style="color:#999;margin-left: 15px"
          v-if="item.info !=='自定义信息'"
        >开关开启，用户下单时须填写{{item.content}}</span>
        <span
          v-else
          style="color:#999;margin-left: 15px"
        >
          标题：
          <el-input
            size="mini"
            style="width:100px; margin: 0 5px"
          ></el-input>
          限制输入不超过6个字
        </span>
      </div>
    </section>

    <!-- 选择下单需要填写必填信息的商品： -->
    <section class="requiredInfo">
      <div class="necessaryGoodsInfo">选择下单需要填写必填信息的商品：</div>
      <!-- <div class="boxList"> -->
      <div class="goodsWrapper">
        <div class="addGoods">
          <img :src="src">
          <span>选择商品</span>
        </div>
        <div
          class="addGoods"
          style="margin: 10px 0"
        >
          <img :src="src">
          <span>选择平台分类</span>
        </div>
        <div
          class="addGoods"
          style="margin: 10px 0"
        >
          <img :src="src">
          <span>选择商家分类</span>
        </div>
        <div
          class="addGoods"
          style="margin: 10px 0"
        >
          <img :src="src">
          <span>选择商品标签</span>
        </div>
        <div
          class="addGoods"
          style="margin: 10px 0"
        >
          <img :src="src">
          <span>选择商品品牌</span>
        </div>
      </div>
    </section>

    <!-- 微信物流助手对接配置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        微信物流助手对接配置
      </div>
      <div
        class="WeChatExpress"
        style="display:flex;"
      >
        <el-switch
          v-model="value1"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 17px 10px 0;"
        ></el-switch>
        <div class="switchText">{{value1?'已开启':'已关闭'}}</div>
        <!-- 右侧第三部分 - 已开启、已关闭后边的内容 -->
        <div class="expressInfo">
          <div class="grayText">开关打开，已发货商品物流信息将展示在小程序前端订单页面，用户可直接查看物流信息。</div>
          <div class="grayText">开关关闭，用户在小程序端查看物流信息时将自动跳转至“快递100”小程序。</div>
          <div style="display:flex;line-height:25px">
            <span style="color:red;">注：开关开启时，若选用微信物流助手不支持的物流公司发货，将无法在小程序端查看物流信息 </span>
            <span style="color: #5A8BFF;margin-left: 20px;">查看支持的物流公司</span>
          </div>
          <!-- 发货地址部分 -->
          <div class="addressContent">
            <span class="address">请选择发货地址</span>
            <areaLinkage @areaData="handleAreaData" />
            <el-input
              size="small"
              style="width:180px"
            ></el-input>
          </div>
          <!-- 快递表格数据部分 -->
          <div class="expressTable">
            <table>
              <thead>
                <tr>
                  <td>物流公司</td>
                  <td>电子面单账号</td>
                  <td>状态</td>
                  <td>操作</td>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="item in expressCompany"
                  :key="item.name"
                >
                  <td style="width:190px">{{item.name}}</td>
                  <td style="width:150px">{{item.account}}</td>
                  <td style="width:90px">{{item.state}}</td>
                  <td style="color:5A8BFF;cursor:pointer;width:70px">{{item.operate}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>

    <div class="btn">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div>

  </div>
</template>

<script>
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'

export default {
  components: { areaLinkage },
  data () {
    return {
      deliverMethods: [
        { name: '快递', title: '启用后，卖家下单可以选择快递发货，由你安排快递送货上门 ', value: '' },
        { name: '自提', title: '启用上门自提功能后，买家可以就近选择你预设的自提门店进行提货。默认所有门店均可自提', value: '' }
      ],
      options: [
        // { title: '申请延长收货配置', content: '用户可在前端申请延长收货时间', value: '' },
        { title: '发票展示设置', content: '用户在购买时可以使用发票功能', value: '' },
        { title: '服务条款设置', content: '结算页会展示服务条款，用户需勾选“同意”才可继续下单', value: '' }
      ],
      isRequiredInfo: [
        { info: '下单人真实姓名', content: '下单人真实姓名', value: '' },
        { info: '下单人身份证号码', content: '下单人身份证号码', value: '' },
        { info: '收货人真实姓名', content: '收货人真实姓名', value: '' },
        { info: '收货人身份证号码', content: '收货人身份证号码', value: '' },
        { info: '自定义信息', value: '' }
      ],
      value: '',
      value1: '',
      src: `${this.$imageHost}/image/admin/icon_jia.png`,
      province: ``,
      district: ``,
      city: ``,
      expressCompany: [
        { name: '百世快递', account: '', state: '未签约', operate: '签约' },
        { name: '顺丰', account: '', state: '未签约', operate: '签约' },
        { name: '德邦', account: '', state: '未签约', operate: '签约' },
        { name: '圆通', account: '', state: '未签约', operate: '签约' },
        { name: '中通', account: '', state: '未签约', operate: '签约' },
        { name: '中国邮政', account: '', state: '未签约', operate: '签约' }
      ]
    }
  },
  methods: {
    handleTake () {
      console.log('自提')
    },
    handleAreaData (val) {
      this.province = val['province']
    }
  }
}

</script>
<style lang="scss" scoped>
.tradeProcessConfigure {
  padding-bottom: 20px;
  .deliverMethods {
    position: relative;
    width: 890px;
    padding-top: 0 !important;
    .deliverContent {
      height: 50px;
      line-height: 50px;
      border-bottom: 1px solid #eee;
      :nth-of-type(3) {
        margin-left: 15px;
        color: #999;
        font-size: 12px;
      }
      .takeByself {
        position: absolute;
        color: #5a8bff;
        right: 90px;
        cursor: pointer;
      }
    }
  }
  .settingWrapper {
    font-size: 13px;
    .title {
      height: 40px;
      line-height: 40px;
      background: #eef1f6;
      padding-left: 16px;
      span {
        display: inline-block;
        border-left: 2px solid #5a8bff;
        height: 14px;
        width: 8px;
        margin-bottom: -1px;
      }
    }
    .settingContent {
      height: 60px;
      line-height: 60px;
      padding-left: 10px;
      color: #666;
      .inputWidth {
        width: 65px;
        margin: 0 5px;
      }
    }
    .delay {
      height: 35px;
      line-height: 35px;
    }
    .top {
      margin-top: 15px;
    }
    .bottom {
      margin: 0 0 15px 10px;
    }
    .WeChatExpress {
      font-size: 14px;
      div {
        margin-bottom: 10px;
      }
      .switchText {
        line-height: 25px;
        color: #333;
        width: 55px;
        margin: 15px 20px 0 10px;
      }
      .expressInfo {
        margin-top: 15px;
        .grayText {
          color: #999;
          line-height: 25px;
        }
        .addressContent {
          color: #333;
          display: flex;
          .address {
            line-height: 25px;
            margin-right: 20px;
          }
        }
        .expressTable {
          width: 500px;
          line-height: 35px;
          table {
            width: 100%;
            text-align: center;
            thead {
              background: #f5f5f5;
            }
            tbody td {
              border: 1px solid #eee;
            }
          }
        }
      }
    }
  }
  .settingWrapper:nth-of-type(1) {
    margin-top: 20px;
  }
  .requiredInfo {
    .necessaryGoodsInfo {
      height: 60px;
      line-height: 60px;
      color: #666;
    }
    .goodsWrapper {
      margin: 10px 0;
      .addGoods {
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5a8bff;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin: 10px 0;
      }
    }
  }
  .btn {
    display: flex;
    justify-content: center;
    width: 100%;
    margin-top: 30px;
  }
}
</style>
