<template>
  <div class="content">
    <!-- 筛选条件 -->
    <div class="main">
      <div
        class="leftarea"
        style="margin-left: 30px;"
      >
        {{$t('couponGive.actName') + '：'}}
        <el-input
          v-model="actName"
          placeholder="请输入发券活动名称"
          clearable
          size="small"
          style="width: 170px;margin: 0 10px;"
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
          @click="handleSelect"
        >{{$t('couponGive.select')}}</el-button>
      </div>
      <div style="position: absolute;right: 20px;">
        <el-button
          type="primary"
          size="small"
          @click="addAct"
        >{{$t('couponGive.giveCoupon')}}</el-button>
      </div>
    </div>

    <!-- 表格数据 -->
    <div class="tableContent">
      <table
        border
        style="width: 100%"
      >
        <thead>
          <tr>
            <th style="width:10%">{{$t('couponGive.actName')}}</th>
            <th style="width:10%">{{$t('couponGive.creationTime')}}</th>
            <th style="width:17%">{{$t('couponGive.actCrowd')}}</th>
            <th style="width:7%">{{$t('couponGive.grantType')}}</th>
            <th style="width:7%">{{$t('couponGive.grantState')}}</th>
            <th>
              <table>
                <thead>
                  <tr>
                    <th style="width:7%">{{$t('couponGive.couponName')}}</th>
                    <th style="width:7%">{{$t('couponGive.usingCondition')}}</th>
                    <th style="width:7%">{{$t('couponGive.couponValue')}}</th>
                    <th style="width:9%">{{$t('couponGive.couponValidityPeriod')}}</th>
                    <th style="width:4%">{{$t('couponGive.operate')}}</th>
                  </tr>
                </thead>
              </table>
            </th>
          </tr>
        </thead>
        <tbody v-if="this.tableData.length>0">
          <tr
            v-for="(item,index) in tableData"
            :key="index"
          >
            <td>{{item.actName}}</td>
            <td>{{item.createTime}}</td>
            <td><span v-html="item.people.join(`<br/>`)"></span></td>
            <td>{{item.sendAction | sendAction}}</td>
            <td>{{item.sendStatus | sendStatus}}</td>
            <!-- 6 ~ 10 -->
            <td style="padding: 0;">
              <table
                class="nestTable"
                border
              >
                <tbody style="border-style: hidden;">
                  <tr
                    v-for="(it,i) in item.couponGiveListConditionVo"
                    :key="i"
                  >
                    <td style="width:7%;">{{it.couponName}}</td>
                    <td style="width:9%;">{{it.leastConsume | leastConsume}}</td>
                    <td style="width:7%;">{{it.denomination | denomination}}</td>
                    <td style="width:9%;"><span v-html="it.time"></span></td>
                    <td style="width:4%;">
                      <el-button
                        @click="receiveDetails(item.id,it.couponId)"
                        type="text"
                      >{{$t('couponGive.grantDetail')}}</el-button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </td>
          </tr>
        </tbody>
      </table>
      <div
        class="tips"
        v-if="this.tableData.length==0"
      >暂无数据</div>
      <!-- 分页组件 -->
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleSelect"
      />
    </div>

  </div>

</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import { couponGiveList } from '@/api/admin/marketManage/couponGive.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination, wrapper
  },
  data () {
    return {
      actName: '',
      tableData: [],
      pageParams: {
        pageRows: 20
      },
      requestParam: {}
    }
  },
  mounted () {
    // 初始化数据
    this.handleSelect()
  },
  methods: {
    handleSelect () {
      couponGiveList({
        actName: this.actName,
        page: {
          currentPage: 1,
          pageRows: 20
        }
      }).then((res) => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.handleData(res.content.dataList)
        }
      }).catch(() => {
        this.$message.error(this.$t('couponGive.operationFailed'))
      })
    },

    // 表格数据处理
    handleData (data) {
      data.forEach((item, index) => {
        data[index][`couponGiveListConditionVo`].forEach((item, index) => {
          if (item.validityType === 0) {
            item['time'] = `${item.startTime}<br/>${this.$t('couponGive.to')}<br/>${item.endTime}`
          } else {
            item['time'] = `${this.$t('couponGive.fromTime')} ${item.validity}${this.$t('couponGive.day')} ${item.validityHour}${this.$t('couponGive.hour')} ${item.validityMinute}${this.$t('couponGive.minute')} ${this.$t('couponGive.effective')}`
          }
        })
        data[index].obj = JSON.parse(data[index]['sendCondition'])
        data[index].people = []
        if (data[index].obj.member_box === 1) {
          data[index].people.push(`${this.$t('couponGive.member')}`)
        }
        if (data[index].obj.cart_box === 1) {
          data[index].people.push(`${this.$t('couponGive.addCart')}`)
        }
        if (data[index].obj.goods_box === 1) {
          data[index].people.push(`${this.$t('couponGive.addGoods')}`)
        }
        if (data[index].obj.card_box === 1) {
          data[index].people.push(`${this.$t('couponGive.holdCard')} ${data[index].cardName}`)
        }
        if (data[index].obj.tag_box === 1) {
          data[index].people.push(`${this.$t('couponGive.belongTag')} ${data[index].tagName}`)
        }
        if (data[index].obj.custom_box === 1) {
          if (data[index].havePay != null) {
            data[index].people.push(`${item.havePay}${this.$t('couponGive.haveRecord')}`)
          }
          if (data[index].noPay != null) {
            data[index].people.push(`${item.noPay}${this.$t('couponGive.noRecord')}`)
          }
          if (data[index].minCount != null) {
            data[index].people.push(`${this.$t('couponGive.buyMore')}${item.minCount}`)
          }
          if (data[index].maxCount != null) {
            data[index].people.push(`${this.$t('couponGive.buyLess')}${item.maxCount}`)
          }
          if (data[index].minAvePrice != null) {
            data[index].people.push(`${this.$t('couponGive.priceHigher')}${item.minAvePrice}`)
          }
          if (data[index].maxAvePrice != null) {
            data[index].people.push(`${this.$t('couponGive.priceLess')}${item.maxAvePrice}`)
          }
          if (data[index].obj.point_start_time != null && data[index].obj.point_end_time != null) {
            data[index].people.push(`${item.obj.point_start_time}-${item.obj.point_start_time}${this.$t('couponGive.loginRecord')}`)
          }
        }
        console.log(item.people.join(`<br/>`))
        data[index].people.forEach((item, index) => {
          console.log(item)
        })
      })
      this.tableData = data
      console.log(data)
    },

    receiveDetails (id, couponId) {
      this.$router.push({
        path: `/admin/home/main/couponGive/receiveDetails/${id}/${couponId}`
      })
    },

    // 发券
    addAct () {
      this.$router.push({
        path: `/admin/home/main/couponGive/grantCoupons`
      })
    }
  },
  filters: {
    sendAction: function (value) {
      if (value === 0) return `立即发放`
      else return `定时发放`
    },
    sendStatus: function (value) {
      if (value === 0) return '未发放'
      else return '已发放'
    },
    leastConsume: function (value) {
      if (value === 0) return '无限制'
      else return '满' + value + '元可用'
    },
    denomination: function (value) {
      return value + '元'
    }
  }
}
</script>
<style scoped>
.content {
  min-width: 100%;
  font-size: 14px;
  padding: 10px;
}

.main {
  position: relative;
  height: 70px;
  line-height: 70px;
  background-color: #fff;
  display: flex;
  margin-bottom: 10px;
}

.tableContent {
  background: #fff;
  padding: 8px 12px;
}

.tableContent thead {
  height: 35px;
  line-height: 35px;
  background: #f5f5f5;
}

.tableContent tbody {
  text-align: center;
  margin: 0 auto;
}

table {
  height: 100%;
}

tr {
  height: 100%;
}

td {
  border: 1px solid #efedee;
  border-collapse: collapse;
  vertical-align: middle;
  padding: 10px 0;
  height: 100%;
}

td span {
  line-height: 1.5;
}

.tips {
  height: 50px;
  line-height: 50px;
  text-align: center;
  border: 1px solid #efedee;
  border-top: none;
}
</style>
