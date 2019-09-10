<template>
  <div>
    <el-date-picker
      size="small"
      style="width:390px"
      v-model="value"
      type="datetimerange"
      range-separator="至"
      start-placeholder="请选择日期"
      end-placeholder="请选择日期"
      @change="handleChange"
    >
    </el-date-picker>
  </div>
</template>
<script>
export default {
  name: `dateTimePicker`,
  data () {
    return {
      // value: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)]
      value: []
    }
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
    }
  }
}
</script>
