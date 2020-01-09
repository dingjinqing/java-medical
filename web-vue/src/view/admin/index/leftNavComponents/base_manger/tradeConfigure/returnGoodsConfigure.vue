<template>
  <div class="returnGoodsConfigure">
    <!--    售后配置-->
    <section class="configureWrapper">
      <div class="title">
        <span></span>{{$t('returnconfiguration.afterconfig')}}
        <el-switch
          v-model="afterSalesConfiguration"
          active-color="#13ce66"
          inactive-color="#f7931e"
          style="margin: 0 10px;"
        ></el-switch>
        {{afterSalesConfiguration?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}
        <label class="onText">{{$t('returnconfiguration.activeafterconfig')}}</label>
      </div>
    </section>
    <!-- 退货配置 -->
    <section
      class="returnGoods"
      v-if="afterSalesConfiguration"
    >
      <div class='title'>{{$t('returnconfiguration.returnconfig')}}：</div>
      <div class="content">
        <div style="margin-top: 20px">
          <template>
            <!-- `checked` 为 true 或 false -->
            <el-checkbox v-model="orderCanExchange"><label>{{$t('returnconfiguration.supportreturn')}}</label></el-checkbox>
          </template>
        </div>
        <el-radio-group
          v-model="returnParam.return_change_goods_status"
          class="requirement"
        >
          <el-radio :label="2">{{$t('returnconfiguration.allgoods')}}</el-radio>
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

        <div
          class="noneBlockList"
          @click="showChoosingGoods"
        >
          <div class="noneBlockLeft">
            <img :src="src">
            <span>{{$t('tradeConfiguration.selectgoods')}}</span>
          </div>
          <div
            class="noneBlockRight"
            v-if="goodsN"
          >已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="goodsN"
            ></el-input> 件 商品
          </div>
        </div>
        <div
          class="noneBlockList"
          @click="showBusClassDialog(2)"
          style="margin: 10px 0"
        >
          <div class="noneBlockLeft">
            <img :src="src">
            <span>{{$t('tradeConfiguration.selectplant')}}</span>
          </div>
          <div
            class="noneBlockRight"
            v-if="platN"
          >已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="platN"
            ></el-input> 个 平台分类
          </div>
        </div>
        <div
          class="noneBlockList"
          @click="showBusClassDialog(1)"
          style="margin: 10px 0"
        >
          <div class="noneBlockLeft">
            <img :src="src">
            <span>{{$t('tradeConfiguration.selectshop')}}</span>
          </div>
          <div
            class="noneBlockRight"
            v-if="busClassN"
          >已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="busClassN"
            ></el-input> 个 商家分类
          </div>
        </div>
        <div
          class="noneBlockList"
          @click="showProductLabel"
          style="margin: 10px 0"
        >
          <div class="noneBlockLeft">
            <img :src="src">
            <span>{{$t('tradeConfiguration.selectlabel')}}</span>
          </div>
          <div
            class="noneBlockRight"
            v-if="labelN"
          >已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="labelN"
            ></el-input> 个 商品标签
          </div>
        </div>
        <div
          class="noneBlockList"
          @click="showBrandDialog"
          style="margin: 10px 0"
        >
          <div class="noneBlockLeft">
            <img :src="src">
            <span>{{$t('tradeConfiguration.selectbrand')}}</span>
          </div>
          <div
            class="noneBlockRight"
            v-if="brandN"
          >已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="brandN"
            ></el-input> 个 商品品牌
          </div>
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
              v-model="returnParam.return_shipping_days"
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
          v-if="returnParam.auto_return === 0"
        >
          <div>
            {{$t('returnconfiguration.note5')}}</div>
        </div>
      </div>
    </section>
    <!--    售后商品库存配置-->
    <section class="configureWrapper">
      <div class="title">
        <span></span>
        {{$t('returnconfiguration.afterstockconfig')}}
      </div>
      <div class="configureContent baseInfo">
        <el-radio-group v-model="returnParam.auto_return_goods_stock">
          <el-radio :label="1">{{$t('returnconfiguration.addgoodstock')}}</el-radio>
          <el-radio :label="0">{{$t('returnconfiguration.notaddgoodstock')}}</el-radio>
        </el-radio-group>
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
        size="small"
        @click="updateConfig"
      >{{$t('returnconfiguration.save')}}</el-button>
    </div>

    <!--选择商品弹窗-->
    <ChoosingGoods
      :tuneUpChooseGoods="tuneUpChooseGoods"
      @resultGoodsDatas="choosingGoodsResult"
      :chooseGoodsBack="goodsInfo"
    />
    <!-- 选择 1商家分类;2平台分类弹窗 -->
    <BusClassDialog
      :dialogVisible.sync="tuneUpBusClassDialog"
      :classFlag="classFlag"
      @BusClassTrueDetailData="busClassDialogResult"
      :backDataArr="commInfo"
    />
    <!-- 选择商品标签弹窗 -->
    <ProductLabel
      :callAddProductLabel.sync="tuneUpProductLabel"
      @handleToGetBackData="busProductLabelResult"
      :brandBackData="labelInfo"
    />
    <!-- 选择商品牌弹窗 -->
    <BrandDialog
      :callAddBrand.sync="tuneUpBrandDialog"
      @handleToGetBackData="busBrandDialogResult"
      :brandBackData="brand"
    />

  </div>
</template>

<script>
import { retrunUpdate, returnSelect } from '@/api/admin/basicConfiguration/tradeConfiguration.js'
import ChoosingGoods from '@/components/admin/choosingGoods'
import ProductLabel from '@/components/admin/addProductLabel'
import BrandDialog from '@/components/admin/addBrandDialog'
import BusClassDialog from '@/components/admin/addingBusClassDialog'

export default {
  components: {
    ChoosingGoods,
    ProductLabel,
    BrandDialog,
    BusClassDialog
  },
  created () {
    this.initData()
  },
  data () {
    return {
      afterSalesConfiguration: true,
      orderCanExchange: true,
      // 商品弹窗回调数据
      goodsInfo: [],
      goodsInfoRow: [],
      goodsN: 0,
      // 标签弹窗回调数据
      labelInfo: [],
      labelInfoRow: [],
      labelN: 0,
      // 商品品牌弹窗回调数据
      brand: [],
      brandRow: [],
      brandN: 0,
      // 商家分类弹窗回调数据
      busClass: [],
      busClassRow: [],
      busClassN: 0,
      // 平台分类弹窗回调数据
      platClass: [],
      platClassRow: [],
      platN: 0,
      // 平台分类/商家分类共享变量
      commInfo: [],
      // 弹窗结果区分标识 1商家分类;2平台分类
      flag: 0,
      tuneUpChooseGoods: false,
      tuneUpBusClassDialog: false,
      tuneUpBrandDialog: false,
      tuneUpProductLabel: false,
      classFlag: 0,
      returnParam: {
        post_sale_status: 1,
        order_can_exchange: 1,
        return_change_goods_status: 0,
        is_refund_coupon: 0,
        auto_return_goods_stock: 1,
        auto_return: 0,
        return_money_days: 7,
        return_address_days: 7,
        return_shipping_days: 7,
        return_pass_days: 7,
        order_return_goods_package: {
          add_goods: [],
          add_cate: [],
          add_sort: [],
          add_label: [],
          add_brand: {}
        },
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
    // 选择商品弹窗调起
    showChoosingGoods () {
      this.tuneUpChooseGoods = !this.tuneUpChooseGoods
    },
    // 选择商品弹窗回调显示
    choosingGoodsResult (row) {
      console.log('选择商品弹窗回调显示:', row)
      this.goodsInfoRow = row
      this.goodsInfo = []
      this.goodsInfoRow.map((item, index) => {
        this.goodsInfo.push(item.goodsId)
      })
      this.goodsN = this.goodsInfo.length
    },
    // 选择商家分类/平台分类弹窗调起
    showBusClassDialog (classFlag) {
      this.tuneUpBusClassDialog = !this.tuneUpBusClassDialog
      this.classFlag = classFlag
      this.flag = classFlag
      // 弹窗结果区分标识 1商家分类;2平台分类
      if (this.flag === 1) {
        this.commInfo = this.busClass
      } else if (this.flag === 2) {
        this.commInfo = this.platClass
      }
    },
    // 选择商家分类/平台分类弹窗回调显示
    busClassDialogResult (row) {
      console.log('选择商家分类/平台分类弹窗回调显示:', row)
      if (this.flag === 1) {
        // 商家分类
        this.busClassRow = row
        this.busClass = []
        this.busClassRow.map((item, index) => {
          this.busClass.push(item.sortId)
        })
        this.busClassN = this.busClass.length
      } else {
        // 平台分类
        this.platClassRow = row
        this.platClass = []
        this.platClassRow.map((item, index) => {
          this.platClass.push(item.catId)
        })
        this.platN = this.platClass.length
      }
    },
    // 选择商品标签弹窗调起
    showProductLabel () {
      this.tuneUpProductLabel = !this.tuneUpProductLabel
    },
    // 选择商品标签弹窗回调显示
    busProductLabelResult (row) {
      console.log('选择商品标签弹窗回调显示:', row)
      this.labelInfoRow = row
      this.labelInfo = []
      this.labelInfoRow.map((item, index) => {
        this.labelInfo.push(item.id)
      })
      this.labelN = this.labelInfo.length
    },
    // 选择商品品牌弹窗调起
    showBrandDialog () {
      this.tuneUpBrandDialog = !this.tuneUpBrandDialog
    },
    // 选择商品品牌弹窗回调显示
    busBrandDialogResult (row) {
      console.log('选择商品品牌弹窗回调显示:', row)
      this.brandRow = row
      this.brand = []
      this.brandRow.map((item, index) => {
        this.brand.push(item.id)
      })
      this.brandN = this.brand.length
    },
    initData () {
      returnSelect().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.returnParam = res.content
          this.goodsInfo = this.returnParam.order_return_goods_package.add_goods
          this.goodsN = this.goodsInfo.length
          this.labelInfo = this.returnParam.order_return_goods_package.add_label
          this.labelN = this.labelInfo.length
          this.brand = this.returnParam.order_return_goods_package.add_brand
          this.brandN = this.brand.length
          this.busClass = this.returnParam.order_return_goods_package.add_sort
          this.busClassN = this.busClass.length
          this.platClass = this.returnParam.order_return_goods_package.add_cate
          this.platN = this.platClass.length
          this.afterSalesConfiguration = this.number2boolean(this.returnParam.post_sale_status)
          this.orderCanExchange = this.number2boolean(this.returnParam.order_can_exchange)
        } else {
          this.$message.error('操作失败，请稍后重试！')
        }
      })
    },
    number2boolean (configValue) {
      if (configValue === 1) {
        return true
      } else if (configValue === 0) {
        return false
      }
    },
    boolean2number (booleanValue) {
      if (booleanValue === true) {
        return 1
      } else if (booleanValue === false) {
        return 0
      }
    },
    // 更新配置项
    updateConfig () {
      // 设置那几个弹窗的值
      this.returnParam.order_return_goods_package.add_goods = this.goodsInfo
      this.returnParam.order_return_goods_package.add_label = this.labelInfo
      this.returnParam.order_return_goods_package.add_brand = this.brand
      this.returnParam.order_return_goods_package.add_sort = this.busClass
      this.returnParam.order_return_goods_package.add_cate = this.platClass
      this.returnParam.post_sale_status = this.boolean2number(this.afterSalesConfiguration)
      this.returnParam.order_can_exchange = this.boolean2number(this.orderCanExchange)
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
      .noneBlockList {
        margin-bottom: 10px;
        display: flex;
        .noneBlockLeft {
          line-height: 30px;
          height: 30px;
          width: 120px;
          text-align: left;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
          padding-left: 5px;
          margin-right: 20px;
        }
        .noneBlockRight {
          color: #5a8bff;
          cursor: pointer;
          height: 30px;
          line-height: 30px;
        }
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
    .onText {
      margin-left: 20px;
      color: #999;
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
