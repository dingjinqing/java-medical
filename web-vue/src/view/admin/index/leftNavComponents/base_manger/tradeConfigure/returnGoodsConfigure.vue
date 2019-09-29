<template>
  <div class="returnGoodsConfigure">
    <!-- 退货配置 -->
    <section class="returnGoods">
      <div class='title'>{{$t('returnconfiguration.returnconfig')}}</div>
      <div class="content">
        <el-radio-group
          v-model="returnParam.return_change_goods_status"
          class="requirement"
        >
          <el-radio :label="1">{{$t('returnconfiguration.cannotreturngoods')}} </el-radio>
          <el-radio :label="0">{{$t('returnconfiguration.canreturngoods')}}</el-radio>
        </el-radio-group>
        <div
          v-if="returnParam.return_change_goods_status === 1"
          class="chooseInfo"
        >{{$t('returnconfiguration.choosecannotreturn')}}</div>
        <div
          v-if="returnParam.return_change_goods_status === 0"
          class="chooseInfo"
        >{{$t('returnconfiguration.choosecanreturn')}}</div>

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
        {{$t('returnconfiguration.ordernecessaryinfo')}}
      </div>
      <div class="configureContent baseInfo">
        <el-radio-group v-model="returnParam.is_refund_coupon">
          <el-radio :label="1">{{$t('returnconfiguration.activated')}}</el-radio>
          <el-radio :label="0">{{$t('returnconfiguration.inactived')}}</el-radio>
        </el-radio-group>
        <span class="onText">{{$t('returnconfiguration.ordernecessarydesc')}}</span>
      </div>
    </section>

    <!-- 自动退款/退货设置 -->
    <section class="configureWrapper">
      <div class="title">
        <span></span>
        {{$t('returnconfiguration.autoreturn')}}
      </div>
      <div class="configureContent">
        <el-radio-group
          v-model="returnParam.auto_return"
          class="radio"
        >
          <el-radio :label="1">{{$t('returnconfiguration.activated')}}</el-radio>
          <el-radio :label="0">{{$t('returnconfiguration.inactived')}}</el-radio>
        </el-radio-group>
        <!-- 开启时的显示内容 -->
        <div
          class="returnMoneySetting"
          v-if="returnParam.auto_return === 1"
        >
          <span class="tips">{{$t('returnconfiguration.note')}}</span>
          <div>
            <span>{{$t('returnconfiguration.note1')}}</span>
            <el-input
              size="mini"
              class="inputWidth"
              v-model="returnParam.return_money_days"
            ></el-input>
            <span>{{$t('returnconfiguration.note11')}}</span>
          </div>
          <div>
            <span>{{$t('returnconfiguration.note2')}}</span>
            <el-input
              size="mini"
              class="inputWidth"
              v-model="returnParam.return_address_days"
            ></el-input>
            <span>{{$t('returnconfiguration.note22')}}</span>
          </div>
          <div>
            <span>{{$t('returnconfiguration.note3')}}</span>
            <el-input
              size="mini"
              class="inputWidth"
              v-model="returnParam.return_shopping_days"
            ></el-input>
            <span>{{$t('returnconfiguration.note33')}}</span>
          </div>
          <div>
            <span>{{$t('returnconfiguration.note4')}}</span>
            <el-input
              size="mini"
              class="inputWidth"
              v-model="returnParam.return_pass_days"
            ></el-input>
            <span>{{$t('returnconfiguration.note44')}}</span>
          </div>
        </div>
        <!-- 关闭时的显示内容 -->
        <div
          class="returnMoneySetting"
          v-if="returnParam.auto_return === 2"
        >
          <div>
            {{$t('returnconfiguration.note5')}}</div>
        </div>
      </div>
    </section>

    <!-- 商家默认收货地址 -->
    <section class="configureWrapper">
      <div class="title">
        <span></span>
        {{$t('returnconfiguration.defaultaddress')}}
      </div>
      <div class="configureContent">
        <div class="receiveInfo">
          <span>{{$t('returnconfiguration.consignee')}}</span>
          <el-input
            size="small"
            style="width:245px"
            v-model="returnParam.business_address.consignee"
          ></el-input>
        </div>
        <div class="receiveInfo">
          <span>{{$t('returnconfiguration.merchant_telephone')}}</span>
          <el-input
            size="small"
            style="width:245px"
            v-model="returnParam.business_address.merchant_telephone"
          ></el-input>
        </div>
        <div class="receiveInfo">
          <span>{{$t('returnconfiguration.zip_code')}}</span>
          <el-input
            size="small"
            style="width:245px"
            v-model="returnParam.business_address.zip_code"
          ></el-input>
        </div>
        <div class="receiveInfo">
          <span>{{$t('returnconfiguration.return_address')}}</span>
          <el-input
            size="small"
            style="width:245px"
            v-model="returnParam.business_address.return_address"
          ></el-input>
        </div>
      </div>
    </section>

    <div class="btn">
      <el-button
        type="primary"
        @click="updateConfig"
      >{{$t('returnconfiguration.save')}}</el-button>
    </div>

  </div>
</template>

<script>
import { returnSelect, retrunUpdate } from '@/api/admin/basicConfiguration/tradeConfiguration.js'
export default {
  created () {
    this.initData()
  },
  data () {
    return {
      returnParam: {
        return_change_goods_status: 0,
        is_refund_coupon: 0,
        auto_return: 0,
        return_money_days: 7,
        return_address_days: 7,
        return_shopping_days: 7,
        return_pass_days: 7,
        business_address: {
          consignee: '',
          merchant_telephone: '',
          zip_code: '',
          return_address: ''
        }
      },
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
  },
  methods: {
    initData () {
      returnSelect().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.returnParam = res.content
        } else {
          this.$message.error('操作失败，请稍后重试！')
        }
      })
    },
    // 更新配置项
    updateConfig () {
      console.log(JSON.parse(JSON.stringify(this.returnParam)))
      retrunUpdate(this.returnParam).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('更新成功！')
          this.initData()
        } else {
          this.$message.error('更新失败！')
        }
      })
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
        width: 20px;
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
