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
          inactive-color="#f7931e"
          style="margin: 0 10px;"
        ></el-switch>
        <span>{{item.value?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}</span>
        <span>{{item.title}}</span>
        <span
          v-if="item.name==='自提'"
          class="takeByself"
          @click="handleTake"
        >{{$t('tradeConfiguration.setAutoPack')}}</span>
      </div>
    </el-card>

    <!-- 待付款订单取消时间设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.wait2payconf')}}
      </div>
      <div class="settingContent">
        <span>{{$t('tradeConfiguration.takenotpay')}}</span>
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="cancelHour"
        ></el-input>
        <span>{{$t('tradeConfiguration.hour')}}</span>
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="cancelMinute"
        ></el-input>
        <span>{{$t('tradeConfiguration.mintuesnotpayautocancel')}}</span>
      </div>
    </section>

    <!-- 发货后自动确认收货时间设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.deliveryautoconfirm')}}
      </div>
      <div class="settingContent">
        {{$t('tradeConfiguration.alreadydeliver')}}
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="tradeProcessConfig.drawback_days"
        ></el-input>
        {{$t('tradeConfiguration.autoconfirmgoods')}}
      </div>
    </section>

    <!-- 确认收货后自动订单完成时间设置(订单完成则不可退换货) -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.alreadyconfirmgoods')}}
      </div>
      <div class="settingContent">
        {{$t('tradeConfiguration.confirmgoodslater')}}
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="tradeProcessConfig.order_timeout_days"
        ></el-input>
        {{$t('tradeConfiguration.orderautodone')}}
      </div>
    </section>

    <!-- 申请延长收货配置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.applicationextend')}}
      </div>
      <div class="settingContent delay top">
        <el-switch
          v-model="extenReceiveGoods"
          active-color="#13ce66"
          inactive-color="#f7931e"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.extenReceiveGoods?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}</span>
        <span style="color:#999;margin-left: 15px">{{$t('tradeConfiguration.openapplicationextend')}}</span>
      </div>
      <div class="settingContent delay bottom">
        {{$t('tradeConfiguration.applicatoinextenddays')}}
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="tradeProcessConfig.extend_receive_days"
        ></el-input>
        {{$t('tradeConfiguration.days')}}
      </div>
    </section>

    <!-- 发票展示设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.invoiceshow')}}
      </div>
      <div class="settingContent">
        <el-switch
          v-model="invoice"
          active-color="#13ce66"
          inactive-color="#f7931e"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.invoice?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}</span>
        <span style="color:#999;margin-left: 15px">{{$t('tradeConfiguration.useinvoice')}}</span>
      </div>
    </section>

    <!--服务条款设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.servicetermconf')}}
      </div>
      <div class="settingContent defaultSelect">
        <el-switch
          v-model="serviceTerms"
          active-color="#13ce66"
          inactive-color="#f7931e"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.serviceTerms?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}</span>
        <span style="color:#999;margin-left: 15px">{{$t('tradeConfiguration.showserviceterm')}}</span>
      </div>
      <div
        class="serviceTerms settingContent"
        v-if="this.serviceTerms===true"
      >
        <div class="termsName">
          <span>{{$t('tradeConfiguration.servicename')}}</span>
          <el-input
            size="small"
            style="width:165px"
            v-model="tradeProcessConfig.service_name"
          ></el-input>
          <span>{{$t('tradeConfiguration.servicenameshow')}} </span>
          <span>{{$t('tradeConfiguration.udpateterm')}}</span>
          <span>{{$t('tradeConfiguration.showexample')}}</span>
        </div>
      </div>
      <div
        class="defaultOption settingContent"
        v-if="this.serviceTerms===true"
      >
        <span>{{$t('tradeConfiguration.firstorderdafauleselected')}}</span>
        <el-radio-group v-model="tradeProcessConfig.service_choose">
          <el-radio :label="1">{{$t('tradeConfiguration.yes')}}</el-radio>
          <el-radio :label="0">{{$t('tradeConfiguration.no')}}</el-radio>
        </el-radio-group>
      </div>
    </section>

    <!-- 下单必填信息设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.ordernecessaryinfo')}}
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
          inactive-color="#f7931e"
          style="margin:0 10px;height:60px;line-height:60px"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{item.value?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}</span>
        <span
          style="color:#999;margin-left: 15px"
          v-if="item.info !=='自定义信息'"
        >{{$t('tradeConfiguration.openordernecessary')}}{{item.content}}</span>
        <span
          v-else
          style="color:#999;margin-left: 15px"
        >
          {{$t('tradeConfiguration.title')}}
          <el-input
            size="mini"
            style="width:100px; margin: 0 5px"
            v-model.number="tradeProcessConfig.custom_title"
          ></el-input>
          {{$t('tradeConfiguration.limitinput')}}
        </span>
      </div>
    </section>

    <!-- 选择下单需要填写必填信息的商品： -->
    <section class="requiredInfo">
      <div class="necessaryGoodsInfo">{{$t('tradeConfiguration.ordernecessarygoodsinfo')}}</div>
      <!-- <div class="boxList"> -->
      <div class="goodsWrapper">
        <div
          class="noneBlockList"
          @click="showChoosingGoods"
        >
          <div class="noneBlockLeft">
            <img :src="src">
            <span>{{$t('tradeConfiguration.selectgoods')}}</span>
          </div>
          <div class="noneBlockRight" v-if="goodsN">已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="goodsN"
            ></el-input>  件 商品
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
          <div class="noneBlockRight" v-if="platN">已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="platN"
            ></el-input>  个 平台分类
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
          <div class="noneBlockRight" v-if="busClassN">已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="busClassN"
            ></el-input>  个 商家分类
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
          <div class="noneBlockRight" v-if="labelN">已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="labelN"
            ></el-input>  个 商品标签
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
          <div class="noneBlockRight" v-if="brandN">已选择：
            <el-input
              size="mini"
              style="width:50px"
              :disabled="true"
              placeholder="0"
              v-model.number="brandN"
            ></el-input>  个 商品品牌
          </div>
        </div>
      </div>
    </section>

    <!-- 微信物流助手对接配置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('tradeConfiguration.logisconf')}}
      </div>
      <div
        class="WeChatExpress"
        style="display:flex;"
      >
        <el-switch
          v-model="shippingExpress"
          active-color="#13ce66"
          inactive-color="#f7931e"
          style="margin: 17px 10px 0;"
        ></el-switch>
        <div class="switchText">
          {{this.shippingExpress?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}
        </div>
        <!-- 右侧第三部分 - 已开启、已关闭后边的内容 -->
        <div class="expressInfo">
          <div class="grayText">{{$t('tradeConfiguration.logiscaption1')}}</div>
          <div class="grayText">{{$t('tradeConfiguration.logiscaption2')}}</div>
          <div style="display:flex;line-height:25px">
            <span style="color:red;">{{$t('tradeConfiguration.logiscaption3')}} </span>
            <span style="color: #5A8BFF;margin-left: 20px;">{{$t('tradeConfiguration.showsupportcompany')}}</span>
          </div>
          <!-- 发货地址部分 -->
          <div class="addressContent">
            <span class="address">{{$t('tradeConfiguration.selectaddress')}}</span>
            <areaLinkage @areaData="handleAreaData"/>
            <el-input
              size="small"
              style="width:180px"
              v-model="addresssConf.address"
            ></el-input>
          </div>
          <!-- 快递表格数据部分 -->
          <div class="expressTable">
            <table>
              <thead>
              <tr>
                <td>{{$t('tradeConfiguration.logiscompany')}}</td>
                <td>{{$t('tradeConfiguration.account')}}</td>
                <td>{{$t('tradeConfiguration.status')}}</td>
                <td>{{$t('tradeConfiguration.operation')}}</td>
              </tr>
              </thead>
              <tbody>
              <tr
                v-for="item in expressCompany"
                :key="item.delivery_name"
              >
                <td style="width:190px">{{item.delivery_name}}</td>
                <td style="width:150px">{{item.biz_id}}</td>
                <td style="width:90px">{{item.status_code}}</td>
                <td style="color:#5A8BFF;cursor:pointer;width:70px">{{item.operate}}</td>
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
        @click="updateConfig"
      >{{$t('tradeConfiguration.save')}}
      </el-button>
    </div>

    <!-- 设置自提门店弹窗 -->
    <el-dialog
      :title="$t('tradeConfiguration.setAutoPack')"
      :visible.sync="showStoreDialog"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="storeParamList"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="storeName"
            :label="$t('tradeConfiguration.storename')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            :label="$t('tradeConfiguration.storeaddress')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="manager"
            :label="$t('tradeConfiguration.storeperson')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('tradeConfiguration.storephone')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="businessTime"
            :label="$t('tradeConfiguration.storebusinesstime')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="businessState"
            :label="$t('tradeConfiguration.storebusinessstatus')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('tradeConfiguration.storeautopack')"
            align="center"
          >
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.autoPick"
                active-color="#13ce66"
                inactive-color="#f7931e"
                style="margin: 0 10px;"
              ></el-switch>
              <span style="font-size: 14px; color:#333;">{{scope.row.autoPick?$t('tradeConfiguration.activated'):$t('tradeConfiguration.inactived')}}</span>
            </template>
          </el-table-column>
        </el-table>
        <div class="table_footer">
          <pagination
            :page-params.sync="pageParams"
            @pagination="handleTake"
          />
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="initDataList"
        >{{$t('tradeConfiguration.save')}}</el-button>
        <el-button @click="cancle">{{$t('tradeConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>

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
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import pagination from '@/components/admin/pagination/pagination'
import {tradeSelect, tradeUpdate} from '@/api/admin/basicConfiguration/tradeConfiguration.js'
import ChoosingGoods from '@/components/admin/choosingGoods'
import ProductLabel from '@/components/admin/addProductLabel'
import BrandDialog from '@/components/admin/addBrandDialog'
import BusClassDialog from '@/components/admin/addingBusClassDialog'
import {storeList, batchUpdateStore} from '@/api/admin/storeManage/store'

export default {
  components: {
    areaLinkage,
    pagination,
    ChoosingGoods,
    ProductLabel,
    BrandDialog,
    BusClassDialog
  },
  mounted () {
    this.langDefault()
  },
  watch: {
    lang () {
      this.deliverMethods = [
        {code: 'express', name: '快递', title: this.$t('tradeConfiguration.opendelivery'), value: false},
        {code: 'fetch', name: '自提', title: this.$t('tradeConfiguration.openpack'), value: false}
      ]
      this.isRequiredInfo = [
        {
          code: 'order_real_name',
          info: this.$t('tradeConfiguration.orderrealname'),
          content: this.$t('tradeConfiguration.orderrealname'),
          value: false
        },
        {
          code: 'order_cid',
          info: this.$t('tradeConfiguration.ordercreadid'),
          content: this.$t('tradeConfiguration.ordercreadid'),
          value: false
        },
        {
          code: 'consignee_real_name',
          info: this.$t('tradeConfiguration.realname'),
          content: this.$t('tradeConfiguration.realname'),
          value: false
        },
        {
          code: 'consignee_cid',
          info: this.$t('tradeConfiguration.creadid'),
          content: this.$t('tradeConfiguration.creadid'),
          value: false
        },
        {code: 'custom', info: this.$t('tradeConfiguration.custominfo'), value: false}
      ]
    },
    allChecked (newData) {
      console.log(newData)
      switch (newData) {
        case true:
          this.brandData.map((item, index) => {
            item.isChecked = true
          })
          break
        case false:
          console.log(1111)
          if (this.allCheckFlag === false) {
            this.brandData.map((item, index) => {
              item.isChecked = false
            })
          }
      }
    },
    'brandData': {
      handler (newData) {
        console.log(newData)
        let arr = newData.filter((item, index) => {
          return item.isChecked === false
        })
        if (!arr.length) {
          this.allChecked = true
        } else {
          this.allCheckFlag = true
          this.allChecked = false
        }
      },
      deep: true
    },
    currency (newData) {
      console.log(newData)
    }
  },
  created () {
    this.initData()
  },
  data () {
    return {
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
      deliverMethods: [
        {code: 'express', name: '快递', title: this.$t('tradeConfiguration.opendelivery'), value: false},
        {code: 'fetch', name: '自提', title: this.$t('tradeConfiguration.openpack'), value: false}
      ],
      isRequiredInfo: [
        {
          code: 'order_real_name',
          info: this.$t('tradeConfiguration.orderrealname'),
          content: this.$t('tradeConfiguration.orderrealname'),
          value: false
        },
        {
          code: 'order_cid',
          info: this.$t('tradeConfiguration.ordercreadid'),
          content: this.$t('tradeConfiguration.ordercreadid'),
          value: false
        },
        {
          code: 'consignee_real_name',
          info: this.$t('tradeConfiguration.realname'),
          content: this.$t('tradeConfiguration.realname'),
          value: false
        },
        {
          code: 'consignee_cid',
          info: this.$t('tradeConfiguration.creadid'),
          content: this.$t('tradeConfiguration.creadid'),
          value: false
        },
        {code: 'custom', info: this.$t('tradeConfiguration.custominfo'), value: false}
      ],
      invoice: false,
      serviceTerms: false,
      serviceChoose: null,
      src: `${this.$imageHost}/image/admin/icon_jia.png`,
      province: ``,
      district: ``,
      city: ``,
      showStoreDialog: false,
      pageParams: {},
      expressCompany: [
        {delivery_name: '百世快递', biz_id: '', status_code: '未签约', operate: '签约'}
      ],
      cancelHour: 0,
      cancelMinute: 0,
      shippingExpress: false,
      extenReceiveGoods: false,
      tradeProcessConfig: {
        cancel_time: null,
        drawback_days: null,
        order_timeout_days: null,
        extend_receive_goods: false,
        extend_receive_days: null,
        shipping_express: false,
        shop_address: null,
        express: null,
        fetch: null,
        invoice: null,
        service_terms: null,
        service_name: null,
        service_choose: 0,
        order_real_name: null,
        order_cid: null,
        consignee_real_name: null,
        consignee_cid: null,
        custom: null,
        order_require_goods_package: {
          add_goods: [],
          add_cate: [],
          add_sort: [],
          add_label: [],
          add_brand: {}
        }
      },
      addresssConf: {
        province_code: '',
        city_code: '',
        district_code: '',
        address: ''
      },
      // 自提门店列表
      storeParamList: [
        {
          storeId: null,
          storeName: null,
          provinceCod: null,
          cityCode: null,
          districtCod: null,
          address: null,
          manager: null,
          mobile: null,
          businessTime: null,
          openingTime: null,
          closeTime: null,
          businessState: null,
          autoPick: null
        }
      ],
      brandClassify: '',
      classifyList: [
        {value: 1, label: '运动品牌'},
        {value: 2, label: '奢侈品'},
        {value: 3, label: '电子产品'},
        {value: 4, label: 'SONY'},
        {value: 5, label: '商品测试'}
      ],
      allChecked: false,
      allCheckFlag: false,
      tuneUpChooseGoods: false,
      tuneUpBusClassDialog: false,
      tuneUpBrandDialog: false,
      tuneUpProductLabel: false,
      classFlag: 0
    }
  },
  methods: {
    initData () {
      tradeSelect().then(res => {
        console.log(res)
        if (res.error === 0) {
          // 物流助手列表
          this.expressCompany = res.content.delivery_list
          this.expressCompany.map((item, index1) => {
            switch (item.status_code) {
              case 0:
                item.status_code = '已绑定'
                item.operate = '解约'
                break
              case 1:
                item.status_code = '审核中'
                item.operate = '签约'
                break
              case 2:
                item.status_code = '绑定失败'
                item.operate = '签约'
                break
              case -1:
                item.status_code = '未绑定'
                item.operate = '签约'
                break
            }
          })
          console.log(res.content)
          this.tradeProcessConfig = res.content.trade_process_config
          this.cancelHour = Math.floor(this.tradeProcessConfig.cancel_time / 60)
          this.cancelMinute = this.tradeProcessConfig.cancel_time % 60
          this.deliverMethods.map((item, index) => {
            switch (item.code) {
              case 'express':
                item.value = this.number2boolean(this.tradeProcessConfig.express)
                break
              case 'fetch':
                item.value = this.number2boolean(this.tradeProcessConfig.fetch)
                break
            }
          })
          this.isRequiredInfo.map((item, index) => {
            switch (item.code) {
              case 'order_real_name':
                item.value = this.number2boolean(this.tradeProcessConfig.order_real_name)
                break
              case 'order_cid':
                item.value = this.number2boolean(this.tradeProcessConfig.order_cid)
                break
              case 'consignee_real_name':
                item.value = this.number2boolean(this.tradeProcessConfig.consignee_real_name)
                break
              case 'consignee_cid':
                item.value = this.number2boolean(this.tradeProcessConfig.consignee_cid)
                break
              case 'custom':
                item.value = this.number2boolean(this.tradeProcessConfig.custom)
                break
            }
            this.shippingExpress = this.number2boolean(this.tradeProcessConfig.shipping_express)
            this.extenReceiveGoods = this.number2boolean(this.tradeProcessConfig.extend_receive_goods)
            this.invoice = this.number2boolean(this.tradeProcessConfig.invoice)
            this.serviceTerms = this.number2boolean(this.tradeProcessConfig.service_terms)
            this.addresssConf = JSON.parse(this.tradeProcessConfig.shop_address)
            this.goodsInfo = this.tradeProcessConfig.order_require_goods_package.add_goods
            this.goodsN = this.goodsInfo.length
            this.labelInfo = this.tradeProcessConfig.order_require_goods_package.add_label
            this.labelN = this.labelInfo.length
            this.brand = this.tradeProcessConfig.order_require_goods_package.add_brand
            this.brandN = this.brand.length
            this.busClass = this.tradeProcessConfig.order_require_goods_package.add_sort
            this.busClassN = this.busClass.length
            this.platClass = this.tradeProcessConfig.order_require_goods_package.add_cate
            this.platN = this.platClass.length
          })
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
      this.tradeProcessConfig.cancel_time = this.cancelHour * 60 + this.cancelMinute
      this.tradeProcessConfig.shop_address = JSON.stringify(this.addresssConf)
      this.deliverMethods.map((item, index) => {
        switch (item.code) {
          case 'express':
            this.tradeProcessConfig.express = this.boolean2number(item.value)
            break
          case 'fetch':
            this.tradeProcessConfig.fetch = this.boolean2number(item.value)
            break
        }
      })
      this.isRequiredInfo.map((item, index) => {
        switch (item.code) {
          case 'order_real_name':
            this.tradeProcessConfig.order_real_name = this.boolean2number(item.value)
            break
          case 'order_cid':
            this.tradeProcessConfig.order_cid = this.boolean2number(item.value)
            break
          case 'consignee_real_name':
            this.tradeProcessConfig.consignee_real_name = this.boolean2number(item.value)
            break
          case 'consignee_cid':
            this.tradeProcessConfig.consignee_cid = this.boolean2number(item.value)
            break
          case 'custom':
            this.tradeProcessConfig.custom = this.boolean2number(item.value)
            break
        }
      })
      this.tradeProcessConfig.extend_receive_goods = this.boolean2number(this.extenReceiveGoods)
      this.tradeProcessConfig.shipping_express = this.boolean2number(this.shippingExpress)
      this.tradeProcessConfig.invoice = this.boolean2number(this.invoice)
      this.tradeProcessConfig.service_terms = this.boolean2number(this.serviceTerms)
      // 设置那几个弹窗的值
      this.tradeProcessConfig.order_require_goods_package.add_goods = this.goodsInfo
      this.tradeProcessConfig.order_require_goods_package.add_label = this.labelInfo
      this.tradeProcessConfig.order_require_goods_package.add_brand = this.brand
      this.tradeProcessConfig.order_require_goods_package.add_sort = this.busClass
      this.tradeProcessConfig.order_require_goods_package.add_cate = this.platClass
      console.log(JSON.parse(JSON.stringify(this.tradeProcessConfig)))
      tradeUpdate(this.tradeProcessConfig).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('更新成功！')
          this.initData()
        } else {
          this.$message.error('更新失败！')
        }
      })
    },
    // 全选本页 - 全部checkbox选中
    handleAllcheck () {
      this.allCheckFlag = false
    },
    // 获取门店列表
    getStoreList () {
      let storPageParam = {
        currentPage: 0,
        pageRows: 20
      }
      if (this.pageParams != null) {
        storPageParam.currentPage = this.pageParams.currentPage
        storPageParam.pageRows = this.pageParams.pageRows
      }
      storeList(storPageParam).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.storeParamList = res.content.dataList
          this.storeParamList.map((item, index) => {
            if (item.businessState === 0) {
              item.businessState = '关店'
            } else if (item.businessState === 1) {
              item.businessState = '营业'
            }
            item.autoPick = this.number2boolean(item.autoPick)
            item.businessTime = item.openingTime + '-' + item.closeTime
          })
        } else {
          this.$message.error('获取门店列表失败！')
        }
      })
    },
    // 更新门店自提状态
    updateSetPick () {
      var updateParam = []
      const stores = this.storeParamList.map(({storeId, autoPick}) => ({storeId, autoPick}))
      updateParam = stores
      updateParam.map((item, index) => {
        item.autoPick = this.boolean2number(item.autoPick)
      })
      console.log(updateParam)
      batchUpdateStore(updateParam).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('更新成功！')
        } else {
          this.$message.error('更新失败！')
        }
      })
    },
    handleTake () {
      this.getStoreList()
      this.showStoreDialog = true
    },
    handleAreaData (val) {
      this.province = val['province']
    },
    // 配置弹出取消按钮点击
    cancle () {
      this.showStoreDialog = false
    },
    // 配置弹出按钮确认点击
    initDataList () {
      this.showStoreDialog = false
      this.updateSetPick()
    },
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
    }
  }
}

</script>
<style lang="scss" scoped>
  .tradeProcessConfigure {
    padding-bottom: 20px;

    .deliverMethods {
      position: relative;
      width: 80%;
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
          width: 85px;
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
      }
    }

    .btn {
      display: flex;
      justify-content: center;
      width: 100%;
      margin-top: 30px;
    }

    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      padding: 8px 10px;
      color: #333;
    }

    .table_list {
      position: relative;

      .table_footer {
        background: #666;
      }
    }

    .chooseGoodsBrand {
      .selectCondition {
        display: flex;
        margin-bottom: 15px;

        .brandClassify {
          margin: 0 30px 0 20px;
        }
      }
    }
  }
</style>
