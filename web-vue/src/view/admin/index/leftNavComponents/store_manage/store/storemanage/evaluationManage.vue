<template>
  <!-- 服务管理 -->
  <div class="service_manage_page">
    <el-tabs
      v-model="activeName"
      class="service_manage_tabs"
      @tab-click="tabClickHandle"
    >
      <el-tab-pane
        label="评价记录"
        name="first"
      >
      </el-tab-pane>
      <el-tab-pane
        label="评价审核"
        name="second"
      >
      </el-tab-pane>
    </el-tabs>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  data () {
    return {
      id: null,
      businessHours: null,
      activeName: 'first'
    }
  },
  watch: {
    activeName: {
      immediate: true,
      handler (newName) {
        console.log(newName)
      }
    },
    '$route.name': function (newVal) {
      console.log('route', newVal)
      this.initStatus()
    }
  },
  created () {
    this.id = this.$route.query.id
    this.businessHours = this.$route.query.businessHours
    this.langDefault()
  },
  mounted () {
  },
  methods: {
    tabClickHandle (tab) {
      let tabName = tab.name
      switch (tabName) {
        case 'first':
          this.$router.push({
            name: 'store_storemanage_reservation_record',
            query: {
              id: this.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
        case 'second':
          this.$router.push({
            name: 'store_storemanage_reservation_review',
            query: {
              id: this.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
      }
    },
    initStatus () {
      let route = this.$route
      let name = route.name
      if (name === 'store_storemanage_reservation_record') {
        this.activeName = 'first'
      } else if (name === 'store_storemanage_reservation_review') {
        this.activeName = 'second'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .service_manage_page {
    .service_manage_tabs {
      margin: 0 25px;
    }
  }
</style>
