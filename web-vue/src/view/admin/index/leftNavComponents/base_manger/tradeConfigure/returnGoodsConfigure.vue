<template>
  <div class="returnGoodsConfigure">
    <!-- 退货配置 -->
    <section class="returnGoods">
      <div class='title'>退货配置</div>
      <div class="content">
        <el-radio-group
          v-model="returnGoodsRequirement"
          class="requirement"
        >
          <el-radio :label="1">除不可退换货商品外，其他均可退换 </el-radio>
          <el-radio :label="2">除可退换货商品外，其他均不可退换</el-radio>
        </el-radio-group>
        <div
          v-if="returnGoodsRequirement === 1"
          class="chooseInfo"
        >选择不可退换货商品</div>
        <div
          v-if="returnGoodsRequirement === 2"
          class="chooseInfo"
        >选择可退换货商品</div>

        <div class="chooseBtn">
          <img :src="src">
          <span>选择商品</span>
        </div>
        <div class="goodsTable">
          <table style="width: 100%">
            <thead>
              <tr>
                <td>商品名称</td>
                <td>价格</td>
                <td>库存</td>
                <td>操作</td>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="item in goodsDialog"
                :key="item.name"
              >
                <td>{{item.name}}</td>
                <td>{{item.price}}</td>
                <td>{{item.number}}</td>
                <td>{{item.operate}}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="chooseBtn">
          <img :src="src">
          <span>选择平台分类</span>
        </div>
        <div class="chooseBtn">
          <img :src="src">
          <span>选择商家分类</span>
        </div>
        <div class="chooseBtn">
          <img :src="src">
          <span>选择商品标签</span>
        </div>
        <div class="chooseBtn">
          <img :src="src">
          <span>选择商品品牌</span>
        </div>
      </div>

    </section>

    <!-- 退款退券 -->
    <section class="configureWrapper">
      <div class="title">
        <span></span>
        下单必填信息设置
      </div>
      <div class="configureContent baseInfo">
        <el-radio-group v-model="returnCoupon">
          <el-radio :label="1">开启</el-radio>
          <el-radio :label="2">关闭</el-radio>
        </el-radio-group>
        <span class="onText">开启后，未完成的订单全额退款时会将优惠券退还给买家</span>
      </div>
    </section>

    <!-- 自动退款/退货设置 -->
    <section class="configureWrapper">
      <div class="title">
        <span></span>
        自动退款/退货设置
      </div>
      <div class="configureContent">
        <el-radio-group
          v-model="returnMoney"
          class="radio"
        >
          <el-radio :label="1">开启</el-radio>
          <el-radio :label="2">关闭</el-radio>
        </el-radio-group>
        <!-- 开启时的显示内容 -->
        <div
          class="returnMoneySetting"
          v-if="returnMoney === 1"
        >
          <span class="tips">注：默认自动退款/退货处理时间为7日，填写0表示不设置</span>
          <div>
            <span>1、买家发起仅退款申请后，商家在</span>
            <el-input
              size="mini"
              class="inputWidth"
            ></el-input>
            <span>日内未处理，系统将自动退款。</span>
          </div>
          <div>
            <span>2、商家已发货，买家发起退款退货申请，商家在</span>
            <el-input
              size="mini"
              class="inputWidth"
            ></el-input>
            <span>日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收货地址。</span>
          </div>
          <div>
            <span>3、买家已提交物流信息，商家在</span>
            <el-input
              size="mini"
              class="inputWidth"
            ></el-input>
            <span>日内未处理，系统将默认同意退款退货，并自动退款给买家。</span>
          </div>
          <div>
            4、商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。
          </div>
        </div>
        <!-- 关闭时的显示内容 -->
        <div
          class="returnMoneySetting"
          v-if="returnMoney === 2"
        >
          <div>商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。</div>
        </div>
      </div>
    </section>

    <!-- 商家默认收货地址 -->
    <section class="configureWrapper">
      <div class="title">
        <span></span>
        商家默认收货地址
      </div>
      <div class="configureContent">
        <div class="receiveInfo">
          <span>收件人：</span>
          <el-input
            size="small"
            style="width:245px"
          ></el-input>
        </div>
        <div class="receiveInfo">
          <span>收件电话：</span>
          <el-input
            size="small"
            style="width:245px"
          ></el-input>
        </div>
        <div class="receiveInfo">
          <span>邮编：</span>
          <el-input
            size="small"
            style="width:245px"
          ></el-input>
        </div>
        <div class="receiveInfo">
          <span>退货地址：</span>
          <el-input
            size="small"
            style="width:245px"
          ></el-input>
        </div>
      </div>
    </section>

    <div class="btn">
      <el-button type="primary">保存</el-button>
    </div>

  </div>
</template>

<script>

export default {
  data () {
    return {
      returnGoodsRequirement: 1,
      returnCoupon: 1,
      returnMoney: 1,
      src: `${this.$imageHost}/image/admin/icon_jia.png`,
      goodsDialog: [
        { name: '百世快递', price: '67', number: '334', operate: '删除' },
        { name: '顺丰', price: '33', number: '533', operate: '删除' },
        { name: '德邦', price: '22', number: '342', operate: '删除' },
        { name: '圆通', price: '45', number: '332', operate: '删除' },
        { name: '中通', price: '32', number: '323', operate: '删除' },
        { name: '中国邮政', price: '32', number: '434', operate: '删除' }
      ]
    }
  }
}

</script>
<style lang="scss" scoped>
.returnGoodsConfigure {
  padding-bottom: 20px;
  .returnGoods {
    display: flex;
    .title {
      height: 60px;
      line-height: 60px;
      margin-right: 35px;
    }
    .content {
      .requirement {
        height: 60px;
        line-height: 70px;
      }
      .chooseInfo {
        margin: 0 0 10px;
      }
      .chooseBtn {
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
      .goodsTable {
        width: 500px;
        line-height: 35px;
        table {
          width: 100%;
          text-align: center;
          thead {
            background: #f8f8f8;
            font-weight: bold;
            color: #333;
          }
          tbody td {
            border: 1px solid #ddd;
          }
        }
      }
    }
  }
  .configureWrapper {
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
    .configureContent {
      padding-left: 10px;
      .onText {
        margin-left: 20px;
        color: #999;
      }
      .radio {
        height: 50px;
        line-height: 70px;
      }
      .returnMoneySetting {
        .tips {
          color: #999;
          margin-bottom: 10px;
          display: block;
        }
        div {
          color: #333;
          height: 40px;
          line-height: 40px;
          .inputWidth {
            width: 65px;
            margin: 0 5px;
          }
        }
      }
      // 商家默认收货地址样式
      .receiveInfo {
        display: flex;
        height: 60px;
        line-height: 60px;
        span {
          display: block;
          width: 70px;
          text-align: right;
        }
      }
    }
    .baseInfo {
      height: 50px;
      line-height: 50px;
    }
  }
  .btn {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
}
</style>
