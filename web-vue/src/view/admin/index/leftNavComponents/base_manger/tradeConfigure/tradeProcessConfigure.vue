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
          v-model.number="tradeProcessConfig.cancelHour"
        ></el-input>
        <span>小时</span>
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="tradeProcessConfig.cancelMinute"
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
          v-model.number="tradeProcessConfig.drawback_days"
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
          v-model.number="tradeProcessConfig.order_timeout_days"
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
          v-model="tradeProcessConfig.extend_receive_goods"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.tradeProcessConfig.extend_receive_goods?'已开启':'已关闭'}}</span>
        <span style="color:#999;margin-left: 15px">开关开启，用户可在前端申请延长收货时间</span>
      </div>
      <div class="settingContent delay bottom">
        用户对单笔订单可申请一次延长收货时间，申请后可延长
        <el-input
          size="mini"
          class="inputWidth"
          v-model.number="tradeProcessConfig.extend_receive_days"
        ></el-input>
        天
      </div>
    </section>

    <!-- 发票展示设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        发票展示设置
      </div>
      <div class="settingContent">
        <el-switch
          v-model="switch1"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.value?'已开启':'已关闭'}}</span>
        <span style="color:#999;margin-left: 15px">开关开启，用户在购买时可以使用发票功能</span>
      </div>
    </section>

    <!--服务条款设置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        服务条款设置
      </div>
      <div class="settingContent defaultSelect">
        <el-switch
          v-model="switch2"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 0 10px;"
        ></el-switch>
        <span style="font-size: 14px; color:#333;">{{this.value3?'已开启':'已关闭'}}</span>
        <span style="color:#999;margin-left: 15px">开关开启，结算页会展示服务条款，用户需勾选“同意”才可继续下单</span>
      </div>
      <div
        class="serviceTerms settingContent"
        v-if="this.value3===true"
      >
        <div class="termsName">
          <span>条款名称：</span>
          <el-input
            size="small"
            style="width:165px"
          ></el-input>
          <span>展示在结算页的服务条款名称 </span>
          <span>编辑条款</span>
          <span>查看示例</span>
        </div>
      </div>
      <div class="defaultOption settingContent">
        <span>首次下单是否默认勾选：</span>
        <el-radio-group v-model="firstOrder">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="2">否</el-radio>
        </el-radio-group>
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
            v-model.number="tradeProcessConfig.custom_title"
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
          v-model="tradeProcessConfig.shipping_express"
          active-color="#13ce66"
          inactive-color="#ff4949"
          style="margin: 17px 10px 0;"
        ></el-switch>
        <div class="switchText">{{this.tradeProcessConfig.shipping_express?'已开启':'已关闭'}}</div>
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
              v-model="addresssConf.address"
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
      >保存</el-button>
    </div>

    <!-- 设置自提门店弹窗 -->
    <el-dialog
      title="设置自提门店"
      :visible.sync="showStoreDialog"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop=""
            label="门店名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="门店地址"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="负责人"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="联系电话"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="营业时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="营业状态"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop=""
            label="是否自提"
            align="center"
          >
          </el-table-column>
        </el-table>
        <div class="table_footer">
          <pagination
            :page-params.sync="pageParams"
            @pagination="initDataList"
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
        >保 存</el-button>
        <el-button @click="cancle">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import pagination from '@/components/admin/pagination/pagination'
import { tradeSelect, tradeUpdate } from '@/api/admin/basicConfiguration/tradeConfiguration.js'
export default {
  components: { areaLinkage, pagination },
  created () {
    this.initData()
  },
  data () {
    return {
      deliverMethods: [
        { code: 'express', name: '快递', title: '启用后，卖家下单可以选择快递发货，由你安排快递送货上门 ', value: false },
        { code: 'fetch', name: '自提', title: '启用上门自提功能后，买家可以就近选择你预设的自提门店进行提货。默认所有门店均可自提', value: false }
      ],
      options: [
        { code: 'invoice', title: '发票展示设置', content: '用户在购买时可以使用发票功能', value: false },
        { code: 'service_terms', title: '服务条款设置', content: '结算页会展示服务条款，用户需勾选“同意”才可继续下单', value: false }
      ],
      isRequiredInfo: [
        { code: 'order_real_name', info: '下单人真实姓名', content: '下单人真实姓名', value: false },
        { code: 'order_cid', info: '下单人身份证号码', content: '下单人身份证号码', value: false },
        { code: 'consignee_real_name', info: '收货人真实姓名', content: '收货人真实姓名', value: false },
        { code: 'consignee_cid', info: '收货人身份证号码', content: '收货人身份证号码', value: false },
        { code: 'custom', info: '自定义信息', value: false }
      ],
      value3: '',
      switch1: '',
      switch2: '',
      src: `${this.$imageHost}/image/admin/icon_jia.png`,
      province: ``,
      district: ``,
      city: ``,
      firstOrder: '',
      showStoreDialog: false,
      pageParams: {},
      expressCompany: [
        { delivery_name: '百世快递', biz_id: '', status_code: '未签约', operate: '签约' }
      ],
      tradeProcessConfig: {
        cancel_time: null,
        cancelHour: null,
        cancelMinute: null,
        drawback_days: null,
        order_timeout_days: null,
        extend_receive_goods: null,
        extend_receive_days: null,
        shipping_express: null,
        shop_address: null,
        express: null,
        fetch: null,
        invoice: null,
        service_terms: null,
        order_real_name: null,
        order_cid: null,
        consignee_real_name: null,
        consignee_cid: null,
        custom: null
      },
      addresssConf: {
        province_code: '',
        city_code: '',
        district_code: '',
        address: ''
      }
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
          this.tradeProcessConfig = res.content.trade_process_config
          console.log('***************************')
          console.log(this.tradeProcessConfig)
          this.tradeProcessConfig.cancelHour = Math.floor(this.tradeProcessConfig.cancel_time / 60)
          this.tradeProcessConfig.cancelMinute = this.tradeProcessConfig.cancel_time % 60
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
          this.options.map((item, index) => {
            switch (item.code) {
              case 'invoice':
                item.value = this.number2boolean(this.tradeProcessConfig.invoice)
                break
              case 'service_terms':
                item.value = this.number2boolean(this.tradeProcessConfig.service_terms)
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
            this.tradeProcessConfig.shipping_express = this.number2boolean(this.tradeProcessConfig.shipping_express)
            this.tradeProcessConfig.extend_receive_goods = this.number2boolean(this.tradeProcessConfig.extend_receive_goods)
            this.addresssConf = JSON.parse(this.tradeProcessConfig.shop_address)
          })
          console.log(this.tradeProcessConfig)
          console.log(this.addresssConf)
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
      this.tradeProcessConfig.cancel_time = this.tradeProcessConfig.cancelHour * 60 + this.tradeProcessConfig.cancelMinute
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
      this.options.map((item, index) => {
        switch (item.code) {
          case 'invoice':
            this.tradeProcessConfig.invoice = this.boolean2number(item.value)
            break
          case 'service_terms':
            this.tradeProcessConfig.service_terms = this.boolean2number(item.value)
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
      this.tradeProcessConfig.extend_receive_goods = this.boolean2number(this.tradeProcessConfig.extend_receive_goods)
      this.tradeProcessConfig.shipping_express = this.boolean2number(this.tradeProcessConfig.shipping_express)
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
    handleTake () {
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
}
</style>
