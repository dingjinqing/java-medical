<template>
  <div class="pointsExchangeOrder">
    <div class="main">
      <div class="mainTop">
        <div class="mainTopList">
          <div class="filters_item">
            <span>商品名称：</span>
            <el-input
              size="small"
              v-model="goodsNameInput"
              placeholder="商品名称"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>订单号：</span>
            <el-input
              size="small"
              v-model="goodsNameInput"
              placeholder="订单号"
            ></el-input>
          </div>
          <div class="filters_item select">
            <span>订单状态：</span>
            <el-select
              v-model="orderStatus"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in orderStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="mainTopList">
          <div class="filters_item">
            <span>收货人姓名：</span>
            <el-input
              size="small"
              v-model="consigneeName"
              placeholder="收货人姓名"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>收货人手机号：</span>
            <el-input
              size="small"
              v-model="consigneePhone"
              placeholder="收货人手机号"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>下单时间：</span>
            <el-input
              size="small"
              v-model="orderTime"
              placeholder="下单时间"
            ></el-input>
          </div>
        </div>
        <div class="bottom">
          <span>收获地址：</span>
          <areaLinkage
            :areaCode="areaLinkage"
            @areaData="handleAreaData"
            style="width:365px"
          />
          <div style="margin-left:20px">
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
            <el-button
              type="info"
              size="small"
              plain
              @click="handleToExport()"
            >导出</el-button>
          </div>
        </div>
      </div>
      <div class="mainBottom">
        <div class="table_list">
          <el-table
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
          >
            <el-table-column
              prop="orderSn"
              label="订单号"
              align="center"
            >
              <template slot-scope="scope">
                <div
                  @click="handleToClickOrderSn(scope.row)"
                  style="color:#5a8bff;cursor:pointer"
                >
                  {{scope.row.orderSn}}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="积分兑换商品"
              align="center"
              prop="goodsName"
              width="250"
            >
              <template slot-scope="scope">
                <div class="opt">
                  <div class="left">
                    <img :src="scope.row.goodsImg">
                  </div>
                  <div class="right">
                    <p>{{scope.row.goodsName}}</p>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="goodsPrice"
              label="商品原价"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="goodsNumber"
              label="商品数量"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="money"
              label="兑换现金(元)"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="score"
              label="兑换积分数量"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="下单人信息"
              align="center"
              prop="xdrName"
            >
              <template slot-scope="scope">
                <div
                  @click="handleToClickSinglePerson(scope.row)"
                  style="color:#5a8bff;cursor:pointer"
                >
                  <div>
                    {{scope.row.xdrName}}
                  </div>
                  <div>
                    {{scope.row.xdrMobile}}
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="收货人信息"
              align="center"
              prop="consignee"
            >
              <template slot-scope="scope">

                <div>
                  <div>
                    {{scope.row.consignee}}
                  </div>
                  <div>
                    {{scope.row.mobile}}
                  </div>
                  <div style="color:#5a8bff;cursor:pointer">
                    <el-tooltip
                      content="复制收件人信息"
                      placement="top"
                    >
                      <span
                        @click="handleToCopy(scope.row)"
                        class="iconfont iconfuzhi iconSpan"
                      ></span>
                    </el-tooltip>
                  </div>
                </div>

              </template>
            </el-table-column>
            <el-table-column
              prop="orderStatus"
              label="订单人状态"
              align="center"
            >
              <template slot-scope="scope">
                {{scope.row.orderStatus === 1?'待发货':''}}
              </template>
            </el-table-column>
          </el-table>
          <div class="footer">
          </div>
          <pagination
            :page-params.sync="pageParams"
            @pagination="initDataList"
          />
        </div>
      </div>
    </div>
    <!--导出弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="export_title ">
        <p><img :src="`${$imageHost}/image/admin/notice_img.png`"><span>&nbsp;&nbsp;根据以下条件筛选出37条数据,是否确认导出？</span></p>
      </div>
      <div class="export_title ">
        <p>筛选条件：无</p>
      </div>
      <div class="export_title ">
        <p style="font-weight: bold;">导出数据</p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToClickSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--点击复制出现的提示部分-->
    <div
      class="model"
      v-if="modelFlag"
    >
      <div class="modelTop">已复制到剪切板</div>
      <div class="modelContent">
        {{modelData}}
      </div>
    </div>
  </div>
</template>
<script>
import { integralOrder } from '@/api/admin/marketManage/integralExchange'
export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'), // 省市区弹窗
    pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页组件
  },
  data () {
    return {
      goodsNameInput: '', // 商品名称input值
      orderStatus: -1, // 订单select选中值
      orderStatusOptions: [{
        value: -1,
        label: '全部订单'
      }, {
        value: 0,
        label: '待付款'
      }, {
        value: 1,
        label: '订单取消'
      }, {
        value: 2,
        label: '订单关闭'
      }, {
        value: 3,
        label: '待发货/待核销'
      }, {
        value: 4,
        label: '已发货'
      }, {
        value: 5,
        label: '已发货/已自提'
      }, {
        value: 6,
        label: '订单完成'
      }, {
        value: 7,
        label: '售后中'
      }, {
        value: 6,
        label: '售后完成'
      }, {
        value: 8,
        label: '送礼完成'
      }, {
        value: 9,
        label: '待接单'
      }, {
        value: 10,
        label: '待接单-取件中'
      }, {
        value: 11,
        label: '已取件-配送中'
      }],
      consigneeName: '', // 收货人姓名
      consigneePhone: '', // 收货人手机号
      orderTime: '', // 下单时间
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      tableData: [
        {
          orderSn: 'P201909031128006589',
          goodsImg: 'http://mpdevimg2.weipubao.cn/upload/7893594/image/20190812/73XTie739D31ubXW.jpeg',
          goodsName: '大号saddle马鞍包系列2019新款D字手拎时尚洋气帆啊啊啊',
          goodsPrice: '198.00',
          goodsNumber: '1',
          money: '0.1',
          score: '100',
          xdrName: '腾飞',
          xdrMobile: '13167356120',
          consignee: '小张',
          mobile: '15535865178',
          orderStatus: 1
        },
        {
          orderSn: 'P201909031128006589',
          goodsImg: 'http://mpdevimg2.weipubao.cn/upload/7893594/image/20190812/73XTie739D31ubXW.jpeg',
          goodsName: '大号saddle马鞍包系列2019新款D字手拎时尚洋气帆啊啊啊',
          goodsPrice: '198.00',
          goodsNumber: '1',
          money: '0.1',
          score: '100',
          xdrName: '腾飞2',
          xdrMobile: '13167356120',
          consignee: '小张2',
          mobile: '15535865178',
          orderStatus: 1
        }
      ], // 列表表格数据
      pageParams: {}, // 分页
      dialogVisible: false, // 筛选导出弹窗
      modelFlag: false, // 遮罩弹窗
      modelData: '' // 遮罩弹窗数据
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化请求数据
    this.initDataList()
  },
  methods: {
    // 省市区选中回传
    handleAreaData (data) {
      console.log(data)
    },
    // 初始化请求数据
    initDataList () {
      let params = {
        activityId: '14',
        page: {
          'currentPage': this.pageParams.currentPage,
          'pageRows': this.pageParams.pageRows
        }
      }
      integralOrder(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          // 模拟
          res.content.page.pageCount = 1
          res.content.page.totalRows = 2
          this.pageParams = res.content.page
        }
        // 模拟数据
        // let arr = [
        //   {
        //     'orderSn': 'P201908030105372897',
        //     'goods': [
        //       {
        //         'recId': null,
        //         'orderId': 17,
        //         'orderSn': 'P201908030105372897',
        //         'goodsId': 2,
        //         'goodsSn': '',
        //         'goodsName': '连衣裙',
        //         'goodsNumber': 1,
        //         'goodsPrice': 888.00,
        //         'goodsAttr': '黑色，XL',
        //         'productId': 1,
        //         'goodsImg': '/image/admin/head_icon.png',
        //         'sendNumber': null
        //       },
        //       {
        //         'recId': null,
        //         'orderId': 17,
        //         'orderSn': 'P201908030105372897',
        //         'goodsId': 2,
        //         'goodsSn': '',
        //         'goodsName': '连衣裙',
        //         'goodsNumber': 1,
        //         'goodsPrice': 688.00,
        //         'goodsAttr': '黑色，XXL',
        //         'productId': 2,
        //         'goodsImg': '/image/admin/head_icon.png',
        //         'sendNumber': null
        //       }
        //     ],
        //     'number': 2,
        //     'money': 108000.00,
        //     'score': 7500,
        //     'consignee': '小张',
        //     'mobile': '15535865178',
        //     'orderStatus': 5
        //   }
        // ]
      })
    },
    // 点击复制收件人信息
    handleToCopy (row) {
      // this.$refs.qrCodePageUrlInput.select()
      //       document.execCommand('Copy')
      let str = row.consignee + row.mobile
      this.modelData = str
      let oInput = document.createElement('input')
      oInput.value = str
      document.body.appendChild(oInput)
      oInput.select() // 选择对象
      document.execCommand('Copy') // 执行浏览器复制命令
      oInput.className = 'oInput'
      oInput.style.display = 'none'
      // 处理model
      this.modelFlag = true
      setTimeout(() => {
        this.modelFlag = false
      }, 1000)
    },
    // 点击导出
    handleToExport () {
      this.dialogVisible = true
    },
    // 导出弹窗确定事件
    handleToClickSure () {

    },
    // 点击表格订单号
    handleToClickOrderSn (row) {
      this.$router.push({
        path: '/admin/home/main/orders/info',
        query: {
          orderSn: row.orderSn
        }
      })
    },
    // 点击下单人信息  缺userId字段
    handleToClickSinglePerson (row) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: row.userId
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.pointsExchangeOrder {
  padding: 10px;
  font-size: 14px;
  .main {
    .mainTop {
      background-color: #fff;
      padding: 20px 0 20px 30px;
      .mainTopList {
        display: flex;
        .filters_item {
          display: flex;
          min-width: 320px;
          margin-bottom: 20px;
          max-width: 328px;
          /deep/ .el-input__inner {
            width: auto;
          }
          /deep/ .el-input {
            width: 188px;
          }
          span {
            white-space: nowrap;
            width: 140px;
            display: inline-block;
            height: 32px;
            line-height: 32px;
            text-align: right;
          }
        }
        .select {
          // padding-right: 43px;
          /deep/ .el-input__inner {
            width: 100%;
          }
        }
      }
      .bottom {
        display: flex;
        span {
          width: 140px;
          line-height: 32px;
          height: 32px;
          text-align: right;
        }
        /deep/ .el-select {
          margin-right: 5px;
        }
      }
    }
    .mainBottom {
      margin-top: 10px;
      background-color: #fff;
      padding: 10px;
      .opt {
        display: flex;
        .left {
          img {
            width: 48px;
            height: 48px;
            display: inline-block;
          }
        }
        .right {
          height: 50px;
          width: 150px;
          position: relative;
          margin-left: 12px;
          font-size: 12px;
          text-align: left;
          p {
            width: 150px;
            display: -webkit-box;
            text-overflow: ellipsis;
            overflow: hidden;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            margin: 0;
          }
        }
      }
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .export_title {
    margin-bottom: 10px;
  }
  .model {
    transition: all 1s;
    position: absolute;
    top: 50%;
    left: 50%;
    width: 360px;
    margin-left: -180px;
    margin-top: -96px;
    opacity: 0.7;
    background: #000;
    z-index: 10000;
    color: #fff;
    border-radius: 5px;
    .modelTop {
      text-align: center;
      height: 40px;
      line-height: 40px;
    }
    .modelContent {
      padding-left: 20px;
      line-height: 20px;
      padding-bottom: 20px;
    }
  }
}
</style>
