<template>
  <div class="week_picker_page">
    <div
      class="prev"
      @click="prevWeek"
    >
      <img :src="$imageHost+'/image/admin/sche_prev.png'">
    </div>
    <div class="week_date">
      <div>{{startDate|formatDate}}</div>
      <div class="line">-</div>
      <div>{{endDate|formatDate}}</div>
    </div>
    <div
      class="next"
      @click="nextWeek"
    >
      <img :src="$imageHost+'/image/admin/sche_next.png'">
    </div>
  </div>
</template>

<script>
import '@/util/date.js'
export default {
  data () {
    return {
      record: 0 // 记录点击的次数
    }
  },
  props: {
    startDate: {
      default: () => new Date()
    },
    endDate: {
      default: () => new Date()
    }
  },
  created () {
    // 初始化周日期
    this.initDate()
  },
  filters: {
    formatDate (date) {
      return new Date(date).format('yyyy-MM-dd')
    }
  },
  methods: {
    initDate () {
      let date = new Date() // 今天
      if (this.nowDate) {
        date = new Date(this.nowDate)
      }
      let now = date.getTime() // 毫秒数
      let week = date.getDay() // 星期日-星期六分别对应：0-6
      let aDay = 1000 * 60 * 60 * 24
      let thisMonday = new Date(now - (week - 1) * aDay)
      let thisSunday = new Date(now + (7 - week) * aDay)
      this.$emit('update:startDate', thisMonday)
      this.$emit('update:endDate', thisSunday)
      this.$nextTick(function () {
        this.$emit('change')
      })
    },
    prevWeek () {
      let aDay = 1000 * 60 * 60 * 24
      let aWeek = aDay * 7
      this.record--
      let Monday = new Date(this.startDate.getTime() - aWeek)
      let Sunday = new Date(this.endDate.getTime() - aWeek)
      this.$emit('update:startDate', Monday)
      this.$emit('update:endDate', Sunday)
      this.$nextTick(function () {
        this.$emit('change')
      })
    },
    nextWeek () {
      let aDay = 1000 * 60 * 60 * 24
      let aWeek = aDay * 7
      this.record++
      let Monday = new Date(this.startDate.getTime() + aWeek)
      let Sunday = new Date(this.endDate.getTime() + aWeek)
      this.$emit('update:startDate', Monday)
      this.$emit('update:endDate', Sunday)
      this.$nextTick(function () {
        this.$emit('change')
      })
    }
  }
}
</script>

<style scoped>
.week_picker_page {
  display: flex;
  justify-content: center;
  align-items: center;
}
.prev {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.next {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.week_date {
  display: flex;
  width: 230px;
  text-align: center;
  justify-content: center;
}
.week_date .line {
  margin: 0 10px;
}
</style>
