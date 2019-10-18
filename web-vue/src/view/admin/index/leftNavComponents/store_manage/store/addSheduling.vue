<template>
  <div class="add_sheduling_page">
    <el-button
      type="primary"
      plain
      size="small"
      @click="clickHandle"
    >设置班次</el-button>
    <!-- 设置班次 -->
    <el-dialog
      title="设置班次"
      :visible.sync="setShiftVisible"
      :close-on-click-modal="false"
      width="735"
    >
      <div class="add_sheduling_model">
        <p class="tips">店铺营业时间为：每天 {{businessHours}}</p>
        <div class="model_top">
          <el-input
            class="add_sheduling_input"
            size="small"
            v-model="scheduleName"
            placeholder="请输入班次名称"
          ></el-input>
          <el-time-select
            class="add_sheduling_input"
            size="small"
            placeholder="请选择开始时间"
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
            placeholder="请选择结束时间"
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
          >添加</el-button>
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
                placeholder="请选择开始时间"
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
                placeholder="请选择结束时间"
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
              >保存</el-button>
              <el-button
                size="small"
                plain
                @click="deleteSchedule(item)"
              >删除</el-button>
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
          this.$message.success('添加成功')
          this.scheduleDatas.push(res.content)
          this.initData()
        }
      })
    },
    validateAddDatas () {
      if (!this.storeId) {
        this.$message.error('门店ID丢失')
        return false
      } else if (!this.scheduleName) {
        this.$message.warning('请输入班次名称')
        return false
      } else if (!this.begcreateTime) {
        this.$message.warning('请选择开始时间')
        return false
      } else if (!this.endTime) {
        this.$message.warning('请选择结束时间')
        return false
      }
      return true
    },
    updateSchedule (item) {
      let params = item
      updateScheduleAPI(params).then(res => {
        if (res.error === 0) {
          this.$message.success('更新成功')
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
          this.$message.success('删除成功')
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
