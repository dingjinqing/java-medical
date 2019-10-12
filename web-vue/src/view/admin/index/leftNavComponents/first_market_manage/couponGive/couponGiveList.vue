<template>
  <div class="content">
    <!-- 筛选条件 -->
    <wrapper>
      <div class="main">
        <div
          class="leftarea"
          style="display:flex"
        >
          <span style="width: 180px;height:30px;line-height: 30px">{{$t('couponGive.actName')}}</span>
          <el-input
            size="small"
            v-model="actName"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            @click="handleSelect"
            style="margin-left:10px"
          >{{$t('couponGive.select')}}
          </el-button>
          <el-button
            type="primary"
            size="small"
            class="barginBtn"
            @click="addAct"
          >{{$t('couponGive.giveCoupon')}}
          </el-button>
        </div>
      </div>

      <!-- 表格数据 -->

      <div class="table_list">
        <section>
          <table
            border
            style="width: 100%"
          >
            <thead>
              <tr class="tableHeader">
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

            <tbody>
              <tr
                v-for="(item,index) in tableData"
                :key="index"
                class="tableBody"
              >
                <!-- 1 -->
                <td>{{item.actName}}</td>
                <!-- 2 -->
                <td>{{item.createTime}}</td>
                <!-- 3 -->
                <td><span v-html="item.people.join(`<br/>`)"></span></td>
                <!-- 4 -->
                <td>{{item.sendAction | sendAction}}</td>
                <!-- 5 -->
                <td>{{item.sendStatus | sendStatus}}</td>
                <!-- 6 ~ 10 -->
                <td class="rightContent">
                  <table>
                    <tbody>
                      <tr
                        v-for="(it,i) in item.couponGiveListConditionVo"
                        :key="i"
                        style="border: 1px solid yellow"
                      >
                        <td
                          style="width:7%"
                          class="listContent"
                        >{{it.couponName}}</td>
                        <td
                          style="width:7%"
                          class="listContent"
                        >{{it.leastConsume | leastConsume}}</td>
                        <td
                          style="width:7%"
                          class="listContent"
                        >{{it.denomination | denomination}}</td>
                        <td
                          style="width:9%"
                          class="listContent"
                        ><span v-html="it.time"></span></td>
                        <td
                          style="width:4%"
                          class="listContent"
                        >
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
        </section>
        <!-- 分页组件 -->
        <pagination
          :page-params.sync="pageParams"
          @pagination="handleSelect"
        />
      </div>
    </wrapper>
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

      }
    }
  },
  created () {
    this.handleSelect()
  },
  methods: {
    // 分页信息查询
    handleSelect () {
      this.pageParams.actName = this.actName
      couponGiveList(this.pageParams).then(res => {
        console.log('pageParams:', this.pageParams)
        console.log('tableData:', res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          console.log('tableData:', res.content.dataList)
          this.pageParams = res.content.page
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
<style lang="scss" scoped>
.content {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    display: flex;
  }
  .table_list {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 0 0 10px 0;
    .tableHeader {
      border: none;
      background: #eee;
      text-align: center;
    }
    .tableBody {
      td {
        text-align: center;
        vertical-align: middle;
        border: 1px solid #ddd;
      }
      .rightContent {
        width: 100%;
        height: 100%;
        margin: 0 auto;
        border: 1px solid #ddd;
        .listContent {
          border: 1px solid #ddd;
          height: 100% !important;
        }
      }
    }
  }
}
</style>
