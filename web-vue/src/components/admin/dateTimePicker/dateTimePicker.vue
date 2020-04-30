<template>
  <div>
    <div v-show="showPicker === 1">
      <!-- 日期时间范围 -->
      <el-date-picker
        size="small"
        style="width:390px"
        v-model="value"
        type="datetimerange"
        :range-separator="$t(`dateTimePicker.to`)"
        :start-placeholder="$t(`dateTimePicker.pleaseSelectTheStartTime`)"
        :end-placeholder="$t(`dateTimePicker.pleaseSelectTheEndTime`)"
        :default-time="['00:00:00','23:59:59']"
        @change="handleChange"
      >
      </el-date-picker>
    </div>
    <div v-show="showPicker === 2">
      <!-- 日期和时间点 -->
      <el-date-picker
        v-model="value1"
        type="datetime"
        :placeholder="$t(`dateTimePicker.pleaseSelectDateAndTime`)"
        @change="handleChange1"
      >
      </el-date-picker>
    </div>
    <div v-show="showPicker === 3" class="start-end">
      <!-- 开始时间 -->
      <el-date-picker
        v-model="startTime"
        type="date"
        style="width:130px"
        value-format="yyyy-MM-dd [00]:[00]:[00]"
        size="small"
        :placeholder="$t(`dateTimePicker.startTime`)"
        @change="getStartTime">
      </el-date-picker>
      <span class="to">{{$t(`dateTimePicker.to`)}}</span>
      <el-date-picker
        v-model="endTime"
        type="date"
        style="width:130px"
        value-format="yyyy-MM-dd [23]:[59]:[59]"
        size="small"
        :placeholder="$t(`dateTimePicker.endTime`)"
        @change="getEndTime">
    </el-date-picker>
    </div>
  </div>

</template>
<script>
export default {
  name: `dateTimePicker`,
  props: {
    showPicker: {
      type: Number,
      require: true
    }
  },
  data () {
    return {
      // value: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)]
      value: [],
      value1: ``,
      startTime: null,
      endTime: null

    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    handleChange () {
      // console.log(this.value)
      if (this.value !== null) {
        /**
         * moment(date).format("YYYY-MM-DD HH:mm:ss");
         * moment( )里面放的格式化的对象，format( )目标格式
         */
        let startTime = this.moment(this.value[0]).format('YYYY-MM-DD HH:mm:ss')
        let endTime = this.moment(this.value[1]).format('YYYY-MM-DD HH:mm:ss')
        this.$emit('time', {
          startTime, endTime
        })
      }
    },
    handleChange1 () {
      // console.log(this.value1)

      if (this.value1 !== null) {
        this.$emit('startTime', {
          startTime: this.moment(this.value1).format('YYYY-MM-DD HH:mm:ss')
        })
      }
    },
    getEndTime (val) {
      this.$emit('endTime', val)
    },
    getStartTime (val) {
      this.$emit('startTime', val)
    }
  }
}
</script>
<style lang="scss" scoped>
.start-end{
  .to{
    margin: 0 5px;
  }
  /deep/ .el-input__inner{
   width: 130px !important;
  }
}
</style>
