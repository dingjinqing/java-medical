<template>
  <el-dialog
    :visible.sync="cycleShow"
    title="设置活动重复周期"
    custom-class="cycke_set"
    center
  >
    <div class="cycle_set_box">
      <div class="cycle_item">
        <el-radio
          v-model="periodAction"
          :label="1"
        >每天</el-radio>
        <el-time-select
          placeholder="起始时间"
          v-model="startTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59'
          }"
          :disabled="periodAction === 1 ? false : true"
          size="small"
          class="small_input"
        >
        </el-time-select>
        至
        <el-time-select
          placeholder="结束时间"
          v-model="endTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59',
            minTime: startTime
          }"
          :disabled="periodAction === 1 ? false : true"
          size="small"
          class="small_input"
        >
        </el-time-select>
      </div>
      <div class="cycle_item">
        <el-radio
          v-model="periodAction"
          :label="2"
        >每月</el-radio>
        <el-select
          v-model="extendTime1"
          size="small"
          class="small_input"
          :disabled="periodAction === 2 ? false : true"
        >
          <el-option
            v-for="(item,index) in 31"
            :key="index"
            :label="item"
            :value="item"
          ></el-option>
        </el-select>
        日
        <el-time-select
          placeholder="起始时间"
          v-model="startTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59'
          }"
          :disabled="periodAction === 2 ? false : true"
          size="small"
          class="small_input"
        >
        </el-time-select>
        至
        <el-time-select
          placeholder="结束时间"
          v-model="endTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59',
            minTime: startTime
          }"
          :disabled="periodAction === 2 ? false : true"
          size="small"
          class="small_input"
        >
        </el-time-select>
      </div>
      <div class="cycle_item">
        <el-radio
          v-model="periodAction"
          :label="3"
        >每周</el-radio>
        <el-checkbox-group
          v-model="extendTime2"
          size="small"
          class="weeks_choose"
          :disabled="periodAction === 3 ? false : true"
        >
          <el-checkbox-button
            v-for="item in weeks"
            :label="item.value"
            :key="item.value"
          >{{item.label}}</el-checkbox-button>
        </el-checkbox-group>
        <el-time-select
          placeholder="起始时间"
          v-model="startTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end:'23:59'
          }"
          :disabled="periodAction === 3 ? false : true"
          size="small"
          class="small_input"
        >
        </el-time-select>
        至
        <el-time-select
          placeholder="结束时间"
          v-model="endTime"
          :picker-options="{
            start: '00:00',
            step: '00:15',
            end: '23:59',
            minTime: startTime
          }"
          :disabled="periodAction === 3 ? false : true"
          size="small"
          class="small_input"
        >
        </el-time-select>
      </div>
    </div>
    <div
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="cycleShow = false"
        size="small"
      >取 消</el-button>
      <el-button
        type="primary"
        @click="confrimCycleSet"
        size="small"
      >确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      cycleShow: false,
      periodAction: 1,
      startTime: '',
      endTime: '',
      extendTime1: '',
      extendTime2: [],
      weeks: [
        {
          value: 1,
          label: '周一'
        },
        {
          value: 2,
          label: '周二'
        },
        {
          value: 3,
          label: '周三'
        },
        {
          value: 4,
          label: '周四'
        },
        {
          value: 5,
          label: '周五'
        },
        {
          value: 6,
          label: '周六'
        },
        {
          value: 7,
          label: '周日'
        }
      ]
    }
  },
  props: {
    cycleDialog: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    cycleDialog (newVal) {
      console.log(newVal)
      this.cycleShow = newVal
    }
  },
  methods: {
    confrimCycleSet () {
      let data = {}
      data.periodAction = this.periodAction
      if (this.periodAction === 2) {
        // 按月重复，单选一个月的某一天
        data.extendTime = this.extendTime1
      } else if (this.periodAction === 3) {
        // 按周重复，可以多选一周的某些天
        data.extendTime = this.extendTime2.join('@')
      }
      data.pointTime = this.startTime + '@' + this.endTime
      this.$emit('handelCycleData', data)
      this.cycleShow = false
    }
  }
}
</script>

<style lang="scss" scoped>
.cycle_set_box {
  margin-bottom: -10px;
  .cycle_item {
    line-height: 32px;
    margin-bottom: 10px;
  }
}

/deep/ .cycke_set {
  width: 800px;
  .el-dialog__header {
    background: #f3f3f3;
    padding-top: 10px;
    .el-dialog__title {
      font-size: 14px;
    }
    .el-dialog__headerbtn {
      top: 10px;
    }
  }
  .el-checkbox-button.is-disabled .el-checkbox-button__inner {
    background-color: #f5f7fa;
  }
}
.small_input {
  width: 114px;
}
.weeks_choose {
  display: inline-block;
  vertical-align: bottom;
}
</style>
