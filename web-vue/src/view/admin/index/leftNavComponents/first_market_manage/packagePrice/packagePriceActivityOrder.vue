<!--
* 打包一口价-活动订单页面
* @author:赵鑫
-->
<template>
  <div class="wrapper">
    <section class="info_content">
      <div class="info1">
        <div>
          <span>商品名称：</span>
          <el-input
            v-model="params.goodsName"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>订单号：</span>
          <el-input
            v-model="params.orderSn"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>订单状态：</span>
          <el-select
            v-model="params.selectedOrderStatus"
            placeholder="请选择"
            size="small"
            class="input_width"
            @change="handleChange"
          >
            <el-option
              v-for="item in orderStatusOptions"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="info2">
        <div>
          <span>收货人姓名：</span>
          <el-input
            v-model="params.consignee"
            size="small"
            class="input_width"
          ></el-input>
        </div>
        <div>
          <span>收货人手机号：</span>
          <el-input
            v-model="params.mobile"
            size="small"
            class="input_width"
          ></el-input>
        </div>
      </div>
      <div class="info3">
        <div>
          <span style="margin-top:7px">收获地址：</span>
          <areaLinkage
            :areaCode="areaLinkage"
            @areaData="handleAreaData"
            style="width:365px;"
          />
        </div>
        <el-button
          size="small"
          type="primary"
          class="btn"
          @click="filter()"
        >筛选</el-button>
        <el-button
          size="small"
          @click="handleToClickSure()"
        >导出表格</el-button>
      </div>
    </section>

    <div class="table_list">
      <table class="table_form">
        <thead>
          <tr>
            <th width="20%">订单号</th>
            <th width="25%">商品信息</th>
            <th width="15%">商品数量</th>
            <th width="15%">下单时间</th>
            <th width="15%">收货人信息</th>
            <th width="10%">订单状态</th>
          </tr>
        </thead>
        <tbody class="hasborder">
          <template v-for="(item) in tableData">
            <tr
              v-for="(goodsItem,goodsIndex) in item.goods"
              :key="goodsItem.recId"
            >
              <td
                v-if="goodsIndex === 0"
                :rowspan="item.goods.length"
              >{{item.orderSn}}</td>
              <td>
                <div class="goodImge">
                  <div>
                    <img :src="$imageHost+'/'+goodsItem.goodsImg">
                  </div>
                  <div class="name">
                    {{goodsItem.goodsName}}
                  </div>
                </div>
              </td>
              <td>
                {{goodsItem.goodsNumber}}
              </td>
              <td
                v-if="goodsIndex === 0"
                :rowspan="item.goods.length"
              >{{item.createTime}}</td>
              <td
                v-if="goodsIndex === 0"
                :rowspan="item.goods.length"
              >
                {{item.consignee}}<br />
                <div style="margin-top: 10px;">{{item.mobile}}</div>
              </td>
              <td
                v-if="goodsIndex === 0"
                :rowspan="item.goods.length"
              >{{item.orderStatusText}}</td>
            </tr>
          </template>

        </tbody>
      </table>
      <pagination
        :page-params.sync="params"
        @pagination="initDataList"
      />
    </div>

    <!--导出弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="export_title ">
        <p><img :src="`${$imageHost}/image/admin/notice_img.png`"><span>&nbsp;&nbsp;根据以下条件筛选出{{screenLength}}条数据,是否确认导出？</span></p>
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
  </div>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'
import { activityOrder, exporOrderExcel } from '@/api/admin/marketManage/packagePrice'

export default {
  components: {
    pagination,
    areaLinkage
  },
  mounted () {
    if (this.$route.query.id > 0) {
      this.initDataList()
      this.orderStatusMap = new Map(this.$t('order.orderStatusList'))
    }
  },
  data () {
    return {
      params: {
        activityId: Number(this.$route.query.id),
        goodsName: '',
        mobile: '',
        orderSn: '',
        selectedOrderStatus: null,
        orderStatus: [],
        consignee: '',
        provinceCode: '',
        cityCode: '',
        districtCode: '',
        createTime: '',
        currentPage: 1,
        pageRows: 20
      },
      areaLinkage: {
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      },
      tableData: [],
      dialogVisible: false,
      orderStatusMap: {},
      orderStatusOptions: [
        [null, '全部订单'],
        [0, '待付款'],
        [1, '订单取消'],
        [2, '订单关闭'],
        [3, '待发货/待核销'],
        [4, '已发货'],
        [5, '已收货/已自提'],
        [6, '订单完成'],
        [7, '售后中'],
        [8, '售后完成'],
        [9, '待接单'],
        [10, '已接单-取件中'],
        [11, '已取件-配送中']
      ],
      screenLength: 0,
      showFilterInfo: false
    }
  },
  methods: {
    initDataList () {
      if (this.params.selectedOrderStatus !== null) {
        this.params.orderStatus = []
        this.params.orderStatus.push(this.params.selectedOrderStatus)
      } else if (this.params.selectedOrderStatus === null) {
        this.params.orderStatus = []
      }
      activityOrder(this.params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          let data = res.content.dataList
          data.forEach(item => {
            item.orderStatusText = this.orderStatusMap.get(item.orderStatus)
          })
          if (data.length) {
            this.screenLength = data.length
          }
        }
      }).catch(err => console.log(err))
    },
    handleChange (val) {
      console.log(val)
    },
    handleAreaData (data) {
      let returnProvince = data.province
      if (returnProvince === 1) {
        this.params.provinceCode = ''
      } else {
        this.params.provinceCode = returnProvince
      }
      this.params.cityCode = data.city
      this.params.districtCode = data.district
    },
    // 筛选
    filter () {
      this.initDataList()
    },
    // 导出
    handleToExport () {
      this.dialogVisible = true
    },
    // 导出弹窗确定事件
    handleToClickSure () {
      exporOrderExcel(this.params).then(res => {
        console.log(res)
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      }).catch(err => console.log(err))
    }
  }
}
</script>

<style lang="scss" scoped>
.wrapper {
  font-size: 14px;
  .info_content {
    padding: 15px 30px;
    margin: 10px;
    background: #fff;
    .info1,
    .info2,
    .info3 {
      display: flex;
      div {
        span {
          display: inline-block;
          width: 105px;
          text-align: right;
        }
      }
    }
    .info1,
    .info2 {
      margin-bottom: 20px;
      :nth-of-type(2) {
        margin: 0 60px;
      }
    }
    .info3 {
      > div {
        display: flex;
      }
      /deep/ .areaLinkage {
        .el-select {
          margin-right: 0;
          &:first-of-type {
            margin-left: 0;
          }
          .el-input {
            width: 112px;
          }
        }
      }
      span {
        min-width: 100px;
        font-size: 14px;
        text-align: right;
      }
      .btn {
        margin: 0 0 0 10px;
      }
    }
    .input_width {
      width: 175px;
    }
  }
  .table_list {
    margin: 0 10px;
    padding: 15px;
    background: #fff;
    .table_form {
      width: 100%;
      thead {
        background-color: #f5f5f5;
        tr th {
          padding: 8px 10px;
          border: 1px solid #eee;
        }
      }
      .hasborder {
        border: 1px solid #eee;
        td {
          border: 1px solid #eee;
          text-align: center;
          vertical-align: middle;
        }
      }
    }
  }
  /deep/ .tableHeader th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
  .export_title {
    margin-bottom: 10px;
  }
  .have_export_info {
    p {
      margin-bottom: 10px;
    }
  }
  .goodImge {
    display: flex;
    padding: 8px 10px;
    & + .goodImge {
      border-top: 1px solid #eee;
    }
    img {
      width: 50px;
      height: 50px;
      line-height: 50px;
      border: 1px solid #ccc;
    }
    .name {
      width: 115px;
      height: 42px;
      text-overflow: ellipsis;
      overflow: hidden;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      display: -webkit-box;
      margin-left: 12px;
      text-align: left;
    }
  }
  .goodsNumber {
    & + .goodsNumber {
      border-top: 1px solid #eee;
    }
  }
}
</style>
