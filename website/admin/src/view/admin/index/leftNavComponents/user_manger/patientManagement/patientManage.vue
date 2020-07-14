<template>
  <div class="patientContent">
    <div class="patientContent_main">
      <el-tabs
        v-model="acitveName"
        @tab-click="handleClick"
      >
        <!--店铺子账户管理 -->
        <el-tab-pane
          label="基础信息"
          name="first"
        >
          <patientMessage
            v-if="showFlag"
            @patientConfig="show"
          />
        </el-tab-pane>
        <!--权限组管理 -->
        <el-tab-pane
          label="处方列表"
          name="second"
        >
          <prescriptionList
            v-if="!showFlag"
            @patientConfig="show"
          />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import patientMessage from './patientMessage'
import prescriptionList from './prescriptionList'

export default {
  components: {
    patientMessage,
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
  .patientContent_main {
    background-color: #fff;
    padding: 10px 20px;
  }
}
</style>
