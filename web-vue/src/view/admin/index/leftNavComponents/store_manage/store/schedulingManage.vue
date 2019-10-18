<template>
  <div class="scheduling_page">
    <div class="order_container">
      <div class="order_header">
        <addSheduling
          :businessHours="businessHours"
          :storeId="storeId"
        ></addSheduling>
        <span class="tips">门店营业时间为：每天 {{businessHours}}</span>
      </div>
      <div class="order_content">
        <div class="week_picker_wrap">
          <WeekPicker
            ref="weekPicker"
            :startDate.sync="queryParams.beginTime"
            :endDate.sync="queryParams.endTime"
            @change="weekChangeHandle"
          ></WeekPicker>
          <span
            class="reload_week"
            @click="resetWeek"
          >本周</span>
        </div>
        <el-table
          ref="schedulingTable"
          :data="tableData"
          class="tableClass"
          max-height="500"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'border':'none'
          }"
        >
          <el-table-column
            label="技师"
            prop="technicianName"
          >
          </el-table-column>
          <el-table-column
            label="周一"
            prop="monday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="周二"
            prop="tuesday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="周三"
            prop="wednesday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="周四"
            prop="thursday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="周五"
            prop="friday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="周六"
            prop="saturday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="周日"
            prop="sunday"
          >
            <template slot-scope="{row}">
              <div>
                <p class="iconSpan">{{row.monday.scheduleName}}</p>
                <p v-if="row.monday.begcreateTime">{{row.monday.begcreateTime}} - {{row.monday.endTime}}</p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
            align="center"
          >
            <template slot-scope="{row}">
              <div>
                <editTechnicianscheduling
                  :storeId="storeId"
                  :datas="row"
                ></editTechnicianscheduling>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { getScheduleList } from '@/api/admin/storeManage/schedulingManage'
import WeekPicker from '@/components/admin/weekPicker'
import addSheduling from './addSheduling'
import editTechnicianscheduling from './editTechnicianscheduling'

export default {
  components: { WeekPicker, addSheduling, editTechnicianscheduling },
  data () {
    return {
      storeId: '',
      businessHours: '', // 营业时间
      technicianId: null, // 技师id
      technicianName: '', // 技师名称
      queryParams: {
        beginTime: '',
        endTime: ''
      },
      tableData: [{
        technicianName: '',
        monday: {
          scheduleName: '无排班'
        },
        tuesday: {
          scheduleName: '无排班'
        },
        wednesday: {
          scheduleName: '无排班'
        },
        thursday: {
          scheduleName: '无排班'
        },
        friday: {
          scheduleName: '无排班'
        },
        saturday: {
          scheduleName: '无排班'
        },
        sunday: {
          scheduleName: '无排班'
        }
      }]
    }
  },
  created () {
    this.storeId = this.$route.query.id
    this.businessHours = this.$route.query.businessHours
    this.technicianId = this.$route.query.technicianId
    this.technicianName = this.$route.query.technicianName
    this.$set(this.tableData[0], 'technicianName', this.technicianName)
  },
  methods: {
    initDataList () {
      let params = Object.assign({
        storeId: Number(this.storeId),
        technicianId: Number(this.technicianId)
      }, this.queryParams)
      getScheduleList(params).then(res => {
        if (res.error === 0) {
          console.log(res.content)
          // this.tableData = [...res.content]
          if (res.content.length > 0) {
            res.content.forEach(item => {
              // 遍历返回值：workDate - queryParams.startTime
              let workDate = new Date(item.workDate)
              let startDate = new Date(this.queryParams.startTime)
              let oneDayTime = 24 * 60 * 60 * 1000
              switch (Math.floor((workDate - startDate) / oneDayTime)) {
                case -1:
                  this.tableData.monday = Object.assign({}, item)
                  break
                case 0:
                  this.tableData.tuesday = Object.assign({}, item)
                  break
                case 1:
                  this.tableData.wednesday = Object.assign({}, item)
                  break
                case 2:
                  this.tableData.thursday = Object.assign({}, item)
                  break
                case 3:
                  this.tableData.friday = Object.assign({}, item)
                  break
                case 4:
                  this.tableData.saturday = Object.assign({}, item)
                  break
                case 5:
                  this.tableData.sunday = Object.assign({}, item)
                  break
              }
            })
          }
        }
      })
    },
    // 初始化时也会调用
    weekChangeHandle () {
      this.initDataList()
    },
    resetWeek () {
      this.$refs.weekPicker.initDate()
    },
    addShedulingHandle () {

    },
    // 编辑 班次
    setScheduling (row) {
      console.log(row)
    }
  }
}
</script>

<style lang="scss" scoped>
.scheduling_page {
  padding: 10px;
  font-size: 14px;
  .order_container {
    padding: 0 20px 20px;
    background: #fff;
    .order_header {
      display: flex;
      line-height: 30px;
      padding: 18px 0;
      border-bottom: 1px solid #eee;
      .tips {
        margin-left: 10px;
      }
    }
    .order_content {
      .week_picker_wrap {
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        padding: 25px 0;
        .reload_week {
          position: absolute;
          right: 10px;
          color: #5a8bff;
          text-decoration: none;
          cursor: pointer !important;
        }
      }
      .iconSpan {
        color: #5a8bff;
        text-decoration: none;
        cursor: pointer !important;
      }
    }
  }
}
</style>
