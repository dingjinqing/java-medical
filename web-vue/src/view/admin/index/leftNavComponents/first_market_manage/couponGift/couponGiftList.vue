<template>
  <div class="content">
    <!-- 筛选条件 -->
    <wrapper>
      <div class="main">
        <div
          class="leftarea"
          style="display:flex"
        >
          <span style="width: 180px;height:30px;line-height: 30px">发券活动名称</span>
          <el-input
            size="small"
            v-model="actName"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            @click="handleSelect"
          >查询
          </el-button>
          <el-button
            type="primary"
            size="small"
            class="barginBtn"
          >发券
          </el-button>
        </div>
      </div>
    </wrapper>
    <!-- 表格数据 -->
    <wrapper>
      <div class="table_list">
        <section>
          <table
            border
            style="width: 100%"
          >
            <thead>
              <tr>
                <th>发券活动名称</th>
                <th>创建时间</th>
                <th>活动人群</th>
                <th>发放类型</th>
                <th>发放状态</th>
                <th>优惠券名称</th>
                <th>优惠券使用条件</th>
                <th>优惠券价值</th>
                <th>优惠券有效期</th>
                <th>操作</th>
              </tr>
            </thead>

            <tbody>
              <tr
                v-for="(item,index) in tableData"
                :key="index"
              >
                <!-- 1 -->
                <td>{{item.actName}}</td>
                <!-- 2 -->
                <td>{{item.createTime}}</td>
                <!-- 3 -->
                <td>{{item.people}}</td>
                <!-- 4 -->
                <td>{{item.sendAction | sendAction}}</td>
                <!-- 5 -->
                <td>{{item.sendStatus | sendStatus}}</td>
                <!-- 6 -->
                <td>
              <tr
                v-for="(it,i) in item.couponGiveListConditionVo"
                :key="i"
              >
                <td>
                  {{it.couponName}}
                </td>
              </tr>
              </td>
              <!-- 7 -->
              <td>
                <tr
                  v-for="(it,i) in item.couponGiveListConditionVo"
                  :key="i"
                >
                  <td>
                    {{it.leastConsume | leastConsume}}
                  </td>
                </tr>
              </td>
              <!-- 8 -->
              <td>
                <tr
                  v-for="(it,i) in item.couponGiveListConditionVo"
                  :key="i"
                >
                  <td>
                    {{it.denomination | denomination}}
                  </td>
                </tr>
              </td>
              <!--9 -->
              <td>
                <tr
                  v-for="(it,i) in item.couponGiveListConditionVo"
                  :key="i"
                >
                  <td>
                    {{it.time}}
                  </td>
                </tr>
              </td>
              <!-- 10 -->
              <td>
                <tr
                  v-for="(it,i) in item.couponGiveListConditionVo"
                  :key="i"
                >
                  <td>
                    发放明细
                  </td>
                </tr>
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
import { couponGiftList } from '@/api/admin/marketManage/couponGift.js'
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
        actNname: null
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
      couponGiftList(this.pageParams).then(res => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          console.log('tableData:', res.content.dataList)
          this.pageParams = res.content.page
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },
    // 表格数据处理
    handleData (data) {
      data.forEach((item, index) => {
        data[index][`couponGiveListConditionVo`].forEach((item, index) => {
          if (item.validityType === 0) {
            item['time'] = `${item.startTime}至${item.endTime}`
          } else {
            item['time'] = `自领取之日起 ${item.validity}天 ${item.validityHour}小时 ${item.validityMinute}分钟 内有效`
          }
        })
        data[index].obj = JSON.parse(data[index]['sendCondition'])
        data[index].people = []
        if (data[index].obj.member_box === '1') {
          // data[index].people = '手动添加会员'
          data[index].people.push('手动添加会员')
        }
        if (data[index].obj.cart_box === '1') {
          data[index].people.push('30天内在本店内有加入购物车行为人群')
        }
        if (data[index].obj.goods_box === '1') {
          data[index].people.push('购买指定商品')
        }
        if (data[index].obj.card_box === '1') {
          data[index].people.push('持有会员卡')
        }
        if (data[index].obj.tag_box === '1') {
          data[index].people.push('属于标签')
        }
        if (data[index].obj.custom_box === '1') {
          if (data[index].havePay != null) {
            data[index].people.push(`${item.havePay}天内有交易记录`)
          }
          if (data[index].noPay != null) {
            data[index].people.push(`${item.noPay}天内无交易记录`)
          }
          if (data[index].minCount != null) {
            data[index].people.push(`购买次数大于${item.minCount}次`)
          }
          if (data[index].maxCount != null) {
            data[index].people.push(`购买次数小于${item.maxCount}次`)
          }
          if (data[index].minAvePrice != null) {
            data[index].people.push(`购买商品均价大于${item.minAvePrice}元`)
          }
          if (data[index].maxAvePrice != null) {
            data[index].people.push(`购买商品均价小于${item.maxAvePrice}元`)
          }
          if (data[index].obj.point_start_time != null && data[index].obj.point_end_time != null) {
            data[index].people.push(`${item.obj.point_start_time}-${item.obj.point_start_time}内有登录记录`)
          }
        }
        console.log(item.people)
        data[index].people.forEach((item, index) => {
          console.log(item)
        })
      })
      this.tableData = data
      console.log(data)
    }
  },
  filters: {
    sendAction: function (value) {
      if (value === '0') return '立即发放'
      else return '定时发放'
    },
    sendStatus: function (value) {
      if (value === '0') return '未发放'
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
}
table,
th,
td {
  border: 1px solid #ddd;
}
</style>
