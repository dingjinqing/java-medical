<template>
  <div class="patientContent">
    <div class="patientContent_main">
      <doctorMessage />
      <perfomanceStatistics />
      <patientList />
      <prescriptionList />
      <inquiryRecord />
    </div>
  </div>
</template>

<script>
export default {
  components: {
    doctorMessage: () => import('./doctorMessage'),
    perfomanceStatistics: () => import('./perfomanceStatistics'),
    patientList: () => import('./patientList'),
    prescriptionList: () => import('./prescriptionList'),
    inquiryRecord: () => import('./inquiryRecord')
  },
  data () {
    return {
      showFlag: true,
      acitveName: 'first'
    }
  },
  methods: {
    show (data) {
      console.log('收到')
      console.log(data)
      if (data) {
        if (data.flag === 2) {
          this.acitveName = 'second'
          this.showFlag = false
        }
        if (data.flag === 1) {
          this.acitveName = 'first'
          this.showFlag = true
        }
      }
    },
    handleClick (tab, event) {
      if (tab.name === 'first') {
        this.acitveName = 'first'
        this.showFlag = true
      }
      if (tab.name === 'second') {
        this.acitveName = 'second'
        this.showFlag = false
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.patientContent {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
}
</style>
