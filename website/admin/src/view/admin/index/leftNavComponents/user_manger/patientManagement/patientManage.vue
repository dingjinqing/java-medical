<template>
  <div class="patientContent">
    <div class="patientContent_main">
      <patientMessage />
      <medicalRecord />
      <inquiryList />
      <doctorList />
      <prescriptionList />
    </div>
  </div>
</template>

<script>
import patientMessage from './patientMessage'
import prescriptionList from './prescriptionList'
import medicalRecord from './medicalRecord'
import inquiryList from './inquiryList'
import doctorList from './doctorList'

export default {
  components: {
    patientMessage,
    medicalRecord,
    inquiryList,
    doctorList,
    prescriptionList
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
