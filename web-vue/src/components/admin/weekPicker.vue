<template>
  <div class="week_picker_wrap">
    <div class="prev"></div>
    <div class="week_date">
      <div>{{startDate}}</div>
      <div>-</div>
      <div>{{endDate}}</div>
    </div>
    <div class="next"></div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      startDate: '',
      endDate: ''
    }
  },
  created () {

  },
  methods: {}
}
</script>

<style scoped>
.week_picker_wrap {
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
</style>
