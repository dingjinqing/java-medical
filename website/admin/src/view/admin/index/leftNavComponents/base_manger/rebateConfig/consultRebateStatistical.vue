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
        <span>返利状态：</span>
        <el-select v-model="rebateStatus" size="small" class="default_input">
          <el-option
            v-for="item in rebateStatusList"
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
        <el-button size="small" @click="exportData">导出</el-button>
      </div>
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
          prop="orderSn"
          label="返利咨询订单号"
        ></el-table-column>
        <el-table-column
          prop="totalMoney"
          label="咨询费总额"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="userName"
          label="下单用户昵称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="totalRebateMoney"
          label="返利金额"
          align="center"
        ></el-table-column>
        <el-table-column prop="status" label="返利状态" align="center">
          <template slot-scope="scope">
            <div>
              {{ scope.row.status | getStatus }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="rebateTime"
          label="返利日期"
          align="center"
        ></el-table-column>
      </el-table>
      <pagination :page-params.sync="pageParams" @pagination="search" />
    </div>
  </div>
</template>

<script>
import { getInquiryOrderRebate, exporTinquiryOrderRebate } from '@/api/admin/basicConfiguration/doctorWithDraw'
import { download } from '@/util/excelUtil.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      doctorName: '',
      rebateStatus: '-1',
      timeType: '-1',
      rebateStatusList: [
        {
          value: '-1',
          label: '全部'
        },
        {
          value: '0',
          label: '待返利'
        },
        {
          value: '1',
          label: '已返利'
        },
        {
          value: '2',
          label: '未返利'
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
      tableList: [
        {
          doctorName: '张三',
          mobile: '17600236996',
          orderSn: 'ksjdkj4564646545644',
          money: '100.00',
          userName: '666',
          rebeatMoney: '666.00',
          rebeatStatus: 1,
          createTime: '2018-05-13 14:12:00'
        },
        {
          doctorName: '张三',
          mobile: '17600236996',
          orderSn: 'ksjdkj4564646545644',
          money: '100.00',
          userName: '666',
          rebeatMoney: '666.00',
          rebeatStatus: 1,
          createTime: '2018-05-13 14:12:00'
        },
        {
          doctorName: '张三',
          mobile: '17600236996',
          orderSn: 'ksjdkj4564646545644',
          money: '100.00',
          userName: '666',
          rebeatMoney: '666.00',
          rebeatStatus: 1,
          createTime: '2018-05-13 14:12:00'
        }
      ],
      startTime: null,
      endTime: null,
      pageParams: {},
      exportParams: {
        doctorName: null,
        status: null,
        startTime: null,
        endTime: null
      }
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
              startTime,
              endTime
            }
          })()
        case '1':
          return (() => {
            const endTime = new Date()
            const startTime = new Date()
            startTime.setTime(startTime.getTime() - 3600 * 1000 * 24 * 7)
            return {
              startTime,
              endTime
            }
          })()

        case '2':
          return (() => {
            const endTime = new Date()
            const startTime = new Date()
            startTime.setTime(startTime.getTime() - 3600 * 1000 * 24 * 30)
            return {
              startTime,
              endTime
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
        0: '待返利',
        1: '已返利',
        2: '未返利'
      }
      return statusName[status]
    }
  },
  methods: {
    search () {
      let params = {
        doctorName: this.doctorName,
        status: this.rebateStatus === '-1' ? null : Number(this.rebateStatus),
        ...this.timeData,
        ...this.pageParams
      }
      getInquiryOrderRebate(params).then(res => {
        if (res.error === 0) {
          this.tableList = res.content.dataList
          this.pageParams = res.content.page
          this.exportParams = JSON.parse(JSON.stringify(params))
        }
      })
    },
    exportData () {
      exporTinquiryOrderRebate({ ...this.exportParams }).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        download(res, decodeURIComponent(fileName))
      })
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
