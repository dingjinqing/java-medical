<template>
  <!-- 服务管理 -->
  <div class="service_manage_page">
    <el-tabs
      v-model="activeName"
      class="service_manage_tabs"
      @tab-click="tabClickHandle"
    >
      <el-tab-pane
        :label="$t('reservationManage.commentRecord')"
        name="first"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="$t('reservationManage.commentCharge')"
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
      activeName: 'first',
      tab: {
        name: 'first'
      }
    }
  },
  watch: {
    activeName: {
      immediate: true,
      handler (newName) {
        console.log(newName)
      }
    },
    $route: function (newVal) {
      console.log('route', newVal)
      this.initStatus()
    }
  },
  created () {
    this.id = this.$route.query.id
    this.langDefault()
    this.initStatus()
  },
  mounted () {
    this.id = this.$route.query.id
    this.langDefault()
    this.initStatus()
    this.tabClickHandle(this.tab)
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
