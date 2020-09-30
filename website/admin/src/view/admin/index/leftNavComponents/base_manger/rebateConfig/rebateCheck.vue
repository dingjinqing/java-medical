<template>
  <div class="container">
    <div class="filters">
      <div class="filters_item">
        <span>医生姓名：</span>
        <el-input
          v-model="doctorName"
          size="small"
          class="default_input"
        ></el-input>
      </div>
      <div class="filters_item">
        <span>处理状态：</span>
        <el-select v-model="checkStatus" size="small" class="default_input">
          <el-option
            v-for="item in checkStatusList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="filters_item">
        <span>时间筛选：</span>
        <el-select v-model="timeType" size="small" class="default_input">
          <el-option
            v-for="item in timeTypeList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="filters_item" v-if="timeType === '3'">
        <el-date-picker
          v-model="startTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          class="middle_input"
          @change="datePickerChange(true, orderTime)"
          size="small"
        />
        至
        <el-date-picker
          v-model="endTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          class="middle_input"
          @change="datePickerChange(false, orderTime)"
          :picker-options="orderEndTime"
          default-time="23:59:59"
          size="small"
        />
      </div>
      <div class="filters_item" style="margin-left: 15px">
        <el-button type="primary" size="small" @click="search">搜索</el-button>
      </div>

      <!-- <div class="filters_item">
        <span>时间筛选：</span>
        <el-input
          v-model=""
          size="small"
          class="default_input"
        ></el-input>
      </div> -->
    </div>
    <div class="table-content">
      <el-table
        :data="tableList"
        border
        :header-cell-style="{
          'background-color': '#f5f5f5',
          border: 'none',
          'font-weight': 'bold',
          color: '#000',
          padding: '8px 10px',
        }"
      >
        <el-table-column
          prop="doctorName"
          label="医生姓名"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="申请时间"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderSn"
          label="提现单号"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="withdrawCash"
          label="提现金额"
          align="center"
        ></el-table-column>
        <el-table-column prop="status" label="处理状态" align="center">
          <template slot-scope="scope">
            <div>
              {{ scope.row.status | getStatus }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="refuseDesc"
          label="驳回原因"
          align="center"
        ></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="operate">
              <span
                v-if="[2, 3, 5].inludes(scope.row.status)"
                @click="changeStatus(scope, 'chargeOff')"
                >出账</span
              >
              <span
                v-if="scope.row.status === 1"
                @click="changeStatus(scope, 'pass')"
                >通过</span
              >
              <span
                v-if="[1, 3, 5].inludes(scope.row.status)"
                @click="changeStatus(scope, 'reject')"
                >驳回</span
              >
              <span @click="viewDetail(scope.row.id)">查看详情</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination :page-params.sync="pageParams" @pagination="search" />
    </div>
  </div>
</template>

<script>
import { getDoctorWithdrawList, changeWithdrawStatus } from '@/api/admin/basicConfiguration/doctorWithDraw'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      doctorName: '',
      checkStatus: '-1',
      timeType: '2',
      checkStatusList: [
        {
          value: '-1',
          label: '全部'
        },
        {
          value: '1',
          label: '待审核'
        },
        {
          value: '2',
          label: '已驳回'
        },
        {
          value: '3',
          label: '待出账'
        },
        {
          value: '4',
          label: '已出账'
        },
        {
          value: '5',
          label: '出账失败'
        }
      ],
      timeTypeList: [
        {
          value: '-1',
          label: '全部'
        },
        {
          value: '0',
          label: '最近一天'
        },
        {
          value: '1',
          label: '最近七天'
        },
        {
          value: '2',
          label: '最近三十天'
        },
        {
          value: '3',
          label: '自定义'
        }
      ],
      tableList: [],
      startTime: null,
      endTime: null,
      pageParams: {}
    }
  },
  mounted () {
    this.search()
  },
  computed: {
    timeData () {
      switch (this.timeType) {
        case '-1':
          return {}
        case '0':
          return (() => {
            const endTime = new Date()
            const startTime = new Date()
            startTime.setTime(startTime.getTime() - 3600 * 1000 * 24 * 1)
            return {
              startTime: `${startTime.getFullYear()}-${startTime.getMonth() + 1}-${startTime.getDate() + 1} 00:00:00`,
              endTime: `${endTime.getFullYear()}-${endTime.getMonth() + 1}-${endTime.getDate()} 23:59:59`
            }
          })()
        case '1':
          return (() => {
            const endTime = new Date()
            const startTime = new Date()
            startTime.setTime(startTime.getTime() - 3600 * 1000 * 24 * 7)
            return {
              startTime: `${startTime.getFullYear()}-${startTime.getMonth() + 1}-${startTime.getDate() + 1} 00:00:00`,
              endTime: `${endTime.getFullYear()}-${endTime.getMonth() + 1}-${endTime.getDate()} 23:59:59`
            }
          })()

        case '2':
          return (() => {
            const endTime = new Date()
            const startTime = new Date()
            startTime.setTime(startTime.getTime() - 3600 * 1000 * 24 * 30)
            return {
              startTime: `${startTime.getFullYear()}-${startTime.getMonth() + 1}-${startTime.getDate() + 1} 00:00:00`,
              endTime: `${endTime.getFullYear()}-${endTime.getMonth() + 1}-${endTime.getDate()} 23:59:59`
            }
          })()
        case '3':
          return (() => {
            return {
              startTime: this.startTime,
              endTime: this.endTime
            }
          })()
      }
    }
  },
  filters: {
    getStatus (status) {
      let statusName = {
        1: '待审核',
        2: '已驳回',
        3: '待出账',
        4: '已出账',
        5: '出账失败'
      }
      return statusName[status]
    }
  },
  methods: {
    search () {
      let params = {
        doctorName: this.doctorName,
        status: this.checkStatus === '-1' ? null : this.checkStatus,
        ...this.timeData,
        ...this.pageParams
      }
      getDoctorWithdrawList(params).then(res => {
        if (res.error === 0) {
          this.tableList = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    changeStatus ({ row: data }, actions) {
      switch (actions) {
        case 'pass':
          this.$confirm('确认通过提现审核吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            changeWithdrawStatus({ checkStatus: 3, orderSn: data.orderSn }).then(res => {
              if (res.error === 0) {
                this.$message.success({
                  message: '已通过'
                })
                this.search()
              } else {
                this.$message.error({
                  message: res.message
                })
              }
            })
          })
          break
        case 'chargeOff':
          this.$confirm('确认出账吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            changeWithdrawStatus({ checkStatus: 4, orderSn: data.orderSn }).then(res => {
              if (res.error === 0) {
                this.$message.success({
                  message: '已出账'
                })
                this.search()
              } else {
                this.$message.error({
                  message: res.message
                })
              }
            })
          })
          break

        default:
          this.$prompt('请输入驳回理由', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消'
          }).then(({ value }) => {
            changeWithdrawStatus({ checkStatus: 2, orderSn: data.orderSn, refuseDesc: value }).then(res => {
              if (res.error === 0) {
                this.$message.success({
                  message: '已驳回请求'
                })
                this.search()
              } else {
                this.$message.error({
                  message: res.message
                })
              }
            })
          }).catch(() => {
          })

          break
      }
    },
    viewDetail (id) {
      let newpage = this.$router.resolve({
        name: 'doctorWithdrawInfo',
        query: {
          id
        }
      })
      window.open(newpage.href, '_blank')
    }
  }
}
</script>

<style lang="scss" scoped>
.filters {
  display: flex;
  line-height: 32px;
  flex-wrap: wrap;
  // max-width: 1226px;
  .filters_item {
    display: flex;
    max-width: 480px;
    min-width: 320px;
    margin-bottom: 10px;
    /deep/ .areaLinkage {
      .el-select {
        margin-left: 10px;
        &:first-of-type {
          margin-left: 0;
        }
      }
    }
    > span {
      font-size: 14px;
      text-align: right;
    }
  }
}
.operate {
  text-align: center;
  color: #66b1ff;
  > span {
    cursor: pointer;
  }
}
.default_input {
  width: 180px;
}
.table-content {
  margin-bottom: 20px;
}
.middle_input {
  width: 185px;
}
</style>
