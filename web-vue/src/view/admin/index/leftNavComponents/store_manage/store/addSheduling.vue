<template>
  <div class="add_sheduling_page">
    <el-button
      type="primary"
      plain
      size="small"
      @click="clickHandle"
    >{{$t('schedulingManage.setShifts')}}</el-button>
    <!-- 设置班次 -->
    <el-dialog
      :title="$t('schedulingManage.setShifts')"
      :visible.sync="setShiftVisible"
      :close-on-click-modal="false"
      width="735"
    >
      <div class="add_sheduling_model">
        <p class="tips">{{$t('schedulingManage.businessHoursTip')}} {{businessHours}}</p>
        <div class="model_top">
          <el-input
            class="add_sheduling_input"
            size="small"
            v-model="scheduleName"
            :placeholder="$t('schedulingManage.shiftTips')"
          ></el-input>
          <el-time-select
            class="add_sheduling_input"
            size="small"
            :placeholder="$t('schedulingManage.startTimeTips')"
            v-model="begcreateTime"
            :picker-options="{
                start: businessStartTime,
                step: '00:30',
                end: businessEndTime
              }"
          ></el-time-select>
          <el-time-select
            class="add_sheduling_input"
            size="small"
            :placeholder="$t('schedulingManage.endTimeTips')"
            v-model="endTime"
            :picker-options="{
                start: businessStartTime,
                step: '00:30',
                end: businessEndTime,
                minTime: begcreateTime
              }"
          ></el-time-select>
          <el-button
            size="small"
            type="primary"
            style="margin-left:10px;"
            @click="addShedulingHandle"
          >{{$t('schedulingManage.add')}}</el-button>
        </div>
        <div class="model_content">
          <ul>
            <li
              class="model_li"
              v-for="(item,index) in scheduleDatas"
              :key="index"
            >
              <el-input
                class="add_sheduling_input"
                size="small"
                v-model="item.scheduleName"
              ></el-input>
              <el-time-select
                class="add_sheduling_input"
                size="small"
                :placeholder="$t('schedulingManage.startTimeTips')"
                v-model="item.begcreateTime"
                :picker-options="{
                start: businessStartTime,
                step: '00:30',
                end: businessEndTime
              }"
              ></el-time-select>
              <el-time-select
                class="add_sheduling_input"
                size="small"
                :placeholder="$t('schedulingManage.endTimeTips')"
                v-model="item.endTime"
                :picker-options="{
                start: businessStartTime,
                step: '00:30',
                end: businessEndTime,
                minTime: item.begcreateTime
              }"
              ></el-time-select>
              <el-button
                type="primary"
                style="margin-left:10px;"
                size="small"
                plain
                @click="updateSchedule(item)"
              >{{$t('schedulingManage.save')}}</el-button>
              <el-button
                size="small"
                plain
                @click="deleteSchedule(item)"
              >{{$t('schedulingManage.delete')}}</el-button>
            </li>
          </ul>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllShift, addSchedule, updateScheduleAPI, deleteScheduleAPI } from '@/api/admin/storeManage/schedulingManage'
export default {
  data () {
    return {
      setShiftVisible: false,
      scheduleName: '', // 班次名
      begcreateTime: '',
      endTime: '',
      scheduleDatas: []
    }
  },
  props: {
    storeId: [Number, String],
    businessHours: String
  },
  computed: {
    businessStartTime: function () {
      if (this.businessHours) {
        return this.businessHours.split(' ')[0]
      } else {
        return '00:00'
      }
    },
    businessEndTime: function () {
      if (this.businessHours) {
        return this.businessHours.split(' ')[2]
      } else {
        return '24:00'
      }
    }
  },
  created () {
    this.begcreateTime = this.businessStartTime
    this.endTime = this.businessEndTime
    this.langDefault()
    this.initData()
  },
  methods: {
    clickHandle () {
      this.setShiftVisible = true
    },
    initData () {
      let params = {
        storeId: this.storeId
      }
      getAllShift(params).then(res => {
        if (res.error === 0) {
          this.scheduleDatas = [...res.content]
        }
      })
    },
    // 添加班次
    addShedulingHandle () {
      if (!this.validateAddDatas()) return
      let params = {
        storeId: this.storeId,
        scheduleName: this.scheduleName,
        begcreateTime: this.begcreateTime,
        endTime: this.endTime
      }
      addSchedule(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('schedulingManage.added'))
          this.scheduleDatas.push(res.content)
          this.initData()
        }
      })
    },
    validateAddDatas () {
      if (!this.storeId) {
        this.$message.error(this.$t('schedulingManage.idMissing'))
        return false
      } else if (!this.scheduleName) {
        this.$message.warning(this.$t('schedulingManage.shiftTips'))
        return false
      } else if (!this.begcreateTime) {
        this.$message.warning(this.$t('schedulingManage.startTimeTips'))
        return false
      } else if (!this.endTime) {
        this.$message.warning(this.$t('schedulingManage.endTimeTips'))
        return false
      }
      return true
    },
    updateSchedule (item) {
      let params = item
      updateScheduleAPI(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('schedulingManage.updated'))
          this.initData()
        }
      })
    },
    deleteSchedule (item) {
      let params = {
        scheduleId: item.scheduleId
      }
      deleteScheduleAPI(params).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('schedulingManage.deleted'))
          this.initData()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.add_sheduling_page {
  .add_sheduling_model {
    .tips {
      color: #888;
      font-size: 12px;
      margin: 0;
    }
    .add_sheduling_input {
      width: 160px;
      height: 30px;
    }
    .model_top {
      padding: 15px 0;
      border-bottom: 1px solid #e6e9f0;
    }
    .model_content {
      height: 200px;
      overflow-y: auto;
      .model_li {
        margin-top: 10px;
      }
    }
  }
}
</style>
