<template>
  <div class="pointsExchangeOrder">
    <div class="main">
      <div class="mainTop">
        <div class="mainTopList">
          <div class="filters_item">
            <span>{{$t('mintegralExchange.commodityName')}}：</span>
            <el-input
              size="small"
              v-model="goodsNameInput"
              :placeholder="$t('mintegralExchange.commodityName')"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('mintegralExchange.orderNumber')}}：</span>
            <el-input
              size="small"
              v-model="goodsNameInput"
              :placeholder="$t('mintegralExchange.orderNumber')"
            ></el-input>
          </div>
          <div class="filters_item select">
            <span>{{$t('mintegralExchange.orderNumber')}}</span>
            <el-select
              v-model="orderStatus"
              :placeholder="$t('mintegralExchange.pleaseChoose')"
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
            <span>{{$t('mintegralExchange.nameOfConsignee')}}：</span>
            <el-input
              size="small"
              v-model="consigneeName"
              :placeholder="$t('mintegralExchange.nameOfConsignee')"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('mintegralExchange.mobileNumber')}}：</span>
            <el-input
              size="small"
              v-model="consigneePhone"
              :placeholder="$t('mintegralExchange.mobileNumber')"
            ></el-input>
          </div>
          <div class="filters_item">
            <span>{{$t('mintegralExchange.orderTime')}}：</span>
            <el-date-picker
              size="small"
              v-model="orderTime"
              type="date"
              :placeholder="$t('mintegralExchange.orderTime')"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </div>
        </div>
        <div class="bottom">
          <span>{{$t('mintegralExchange.harvestAddress')}}</span>
          <areaLinkage
            :areaCode="areaLinkage"
            @areaData="handleAreaData"
            @areaChange="handleAreaDataDetail"
            style="width:365px"
          />
          <div style="margin-left:20px">
            <el-button
              type="primary"
              size="small"
              @click="initDataList()"
            >{{$t('mintegralExchange.screen')}}</el-button>
            <el-button
              type="info"
              size="small"
              plain
              @click="handleToExport()"
            >{{$t('mintegralExchange.export')}}</el-button>
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
              :label="$t('mintegralExchange.orderNumber')"
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
              :label="$t('mintegralExchange.pointsExchangeProducts')"
              align="center"
              prop="goodsName"
              width="250"
            >
              <template slot-scope="scope">
                <div class="opt">
                  <div class="left">
                    <img :src="$imageHost+'/'+scope.row.goodsImg">
                  </div>
                  <div class="right">
                    <p>{{scope.row.goodsName}}</p>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="goodsPrice"
              :label="$t('mintegralExchange.orderOriginalPrice')"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="number"
              :label="$t('mintegralExchange.quantityOfCommodities')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="money"
              :label="$t('mintegralExchange.cashExchange')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="score"
              :label="$t('mintegralExchange.numberOfPoints')"
              align="center"
            >
            </el-table-column>
            <el-table-column
              :label="$t('mintegralExchange.nextPersonInformation')"
              align="center"
              prop="xdrName"
            >
              <template slot-scope="scope">
                <div
                  @click="handleToClickSinglePerson(scope.row)"
                  style="color:#5a8bff;cursor:pointer"
                >
                  <div>
                    {{scope.row.username}}
                  </div>
                  <div>
                    {{scope.row.userMobile}}
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              :label="$t('mintegralExchange.consigneeInformation')"
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
                      :content="$t('mintegralExchange.copyRecipient')"
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
              :label="$t('mintegralExchange.ordererStatus')"
              align="center"
            >
              <template slot-scope="scope">
                {{scope.row.orderStatus === 0?$t('mintegralExchange.pendingPayment'):scope.row.orderStatus === 1?$t('mintegralExchange.customerCancelled'):scope.row.orderStatus === 2?$t('mintegralExchange.sellerClosed'):scope.row.orderStatus === 3?$t('mintegralExchange.tobShipped'):scope.row.orderStatus === 4?$t('mintegralExchange.shipped'):scope.row.orderStatus === 5?$t('mintegralExchange.eeceivedGoods'):scope.row.orderStatus === 6?$t('mintegralExchange.completed'):''}}
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
      :title="$t('mintegralExchange.tips')"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="export_title ">
        <p><img :src="`${$imageHost}/image/admin/notice_img.png`"><span>&nbsp;&nbsp;根据以下条件筛选出{{screenLength}}条数据,是否确认导出？</span></p>
      </div>
      <div class="export_title ">
        <p>{{$t('mintegralExchange.filterCondition')}}</p>
      </div>
      <div class="export_title ">
        <p style="font-weight: bold;">{{$t('mintegralExchange.derivedData')}}</p>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">{{$t('mintegralExchange.cancellation')}}</el-button>
        <el-button
          type="primary"
          @click="handleToClickSure()"
        >{{$t('mintegralExchange.determine')}}</el-button>
      </span>
    </el-dialog>
    <!--点击复制出现的提示部分-->
    <div
      class="model"
      v-if="modelFlag"
    >
      <div class="modelTop">{{$t('mintegralExchange.copiedToClipboard')}}</div>
      <div class="modelContent">
        {{modelData}}
      </div>
    </div>
  </div>
</template>
<script>
import { download } from '@/util/excelUtil.js'
import { integralOrder, orderListExport } from '@/api/admin/marketManage/integralExchange'
export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue'), // 省市区弹窗
    pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页组件
  },
  data () {
    return {
      goodsNameInput: '', // 商品名称input值
      orderStatus: -1, // 订单select选中值
      consigneeName: '', // 收货人姓名
      consigneePhone: '', // 收货人手机号
      orderTime: '', // 下单时间
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      tableData: [

      ], // 列表表格数据
      pageParams: {}, // 分页
      dialogVisible: false, // 筛选导出弹窗
      modelFlag: false, // 遮罩弹窗
      modelData: '', // 遮罩弹窗数据
      selectArea: { // 省市区
        province: '',
        city: '',
        district: ''
      },
      areaDetail: {
        province: '',
        city: '',
        district: ''
      },
      screenLength: 0
    }
  },
  computed: {
    orderStatusOptions () {
      return this.$t('mintegralExchange.orderStatusOptions')
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
      this.selectArea = data
    },
    // 初始化请求数据
    initDataList () {
      let startTime = ''
      let endTime = ''
      if (this.orderTime) {
        startTime = this.orderTime + ' 00:00:00'
        endTime = this.orderTime + ' 23:59:59'
      }
      let params = {
        activityId: this.$route.query.activityId,
        goodsName: this.goodsNameInput,
        orderSn: this.goodsNameInput,
        orderStatus: this.orderStatus === -1 ? '' : this.orderStatus,
        mobile: this.consigneePhone,
        consignee: this.consigneeName,
        startTime: startTime,
        endTime: endTime,
        provinceCode: this.selectArea.province,
        cityCode: this.selectArea.city,
        districtCode: this.selectArea.district,
        page: {
          'currentPage': this.pageParams.currentPage,
          'pageRows': this.pageParams.pageRows
        }
      }
      integralOrder(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
          if (res.content.dataList.length) {
            this.screenLength = res.content.dataList.length
          }
        }
      })
    },
    // 省市区详细数据
    handleAreaDataDetail (res) {
      console.log(res)
      let obj = {
        province: '',
        city: '',
        district: ''
      }
      Object.keys(res).forEach((item, index) => {
        console.log(res[item])
        if (res[item].name) {
          obj[item] = res[item].name
        }
      })
      console.log(obj)
      this.areaDetail = obj
    },
    // 点击复制收件人信息
    handleToCopy (row) {
      // this.$refs.qrCodePageUrlInput.select()
      //       document.execCommand('Copy')
      let area = this.areaDetail.province + this.areaDetail.city + this.areaDetail.district
      let str = row.consignee + ' ' + row.mobile + ' ' + area
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
      this.initDataList()
      this.dialogVisible = true
    },
    // 导出弹窗确定事件
    handleToClickSure () {
      let startTime = ''
      let endTime = ''
      if (this.orderTime) {
        startTime = this.orderTime + ' 00:00:00'
        endTime = this.orderTime + ' 23:59:59'
      }
      let params = {
        activityId: this.$route.query.activityId,
        goodsName: this.goodsNameInput,
        orderSn: this.goodsNameInput,
        orderStatus: this.orderStatus,
        mobile: this.consigneePhone,
        consignee: this.consigneeName,
        startTime: startTime,
        endTime: endTime,
        provinceCode: this.selectArea.province,
        cityCode: this.selectArea.city,
        districtCode: this.selectArea.district
      }
      orderListExport(params).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      })
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
          // /deep/ .el-input__inner {
          //   width: auto;
          // }
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
