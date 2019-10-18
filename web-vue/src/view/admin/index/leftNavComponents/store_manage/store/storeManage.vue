<template>
  <!-- 门店管理 -->
  <div class="receiveDetail store_manage_page">
    <div class="receiveDetailMain">
      <div class="order-container">
        <div class="order-top">
          <el-tabs
            type="border-card"
            class="store_manage_tabs"
            v-model="activeName"
            @tab-click="tabClickHandle"
          >
            <el-tab-pane
              label="预约管理"
              name="first"
            >
            </el-tab-pane>
            <el-tab-pane
              label="服务管理"
              name="second"
            >
            </el-tab-pane>
            <el-tab-pane
              label="技师管理"
              name="third"
            >
            </el-tab-pane>
            <el-tab-pane
              label="评价管理"
              name="fourth"
            >
            </el-tab-pane>
          </el-tabs>
          <div>
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      id: null, // 门店id
      businessHours: null, // 营业时间
      activeName: 'first'
    }
  },
  created () {
    console.log('$route.....', this.$route)
    this.id = this.$route.query.id
    this.businessHours = this.$route.query.businessHours
    this.initStatus()
  },
  mounted () {
  },
  watch: {
    activeName: {
      immediate: true,
      handler: function (newName) {
        console.log(newName)
      }
    }
  },
  methods: {
    tabClickHandle (tab) {
      const name = tab.name
      switch (name) {
        case 'first':
          this.$router.push({
            name: 'store_storemanage_reserve',
            query: {
              id: this.id
            }
          })
          break
        case 'second':
          this.$router.push({
            name: 'store_storemanage_service_list',
            query: {
              id: this.id,
              businessHours: this.businessHours
            }
          })
          break
        case 'third':
          this.$router.push({
            name: 'store_storemanage_technician_list',
            query: {
              id: this.id,
              businessHours: this.businessHours
            }
          })
          break
        case 'fourth':
          this.$router.push({
            name: 'store_storemanage_comment',
            query: {
              id: this.id
            }
          })
          break
      }
    },
    initStatus () {
      let route = this.$route
      let path = route.path
      if (path.indexOf('reserve') > -1) {
        this.activeName = 'first'
      } else if (path.indexOf('service') > -1) {
        this.activeName = 'second'
      } else if (path.indexOf('technician') > -1) {
        this.activeName = 'third'
      } else if (path.indexOf('comment') > -1) {
        this.activeName = 'fourth'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.order-container {
  padding: 10px;
  .order-top {
    background: #fff;
    padding: 15px 0;
    .store_manage_tabs {
      margin: 0 25px;
    }
    .el-tabs--border-card {
      box-shadow: none;
      height: 40px;
      overflow: hidden;
    }
  }
}
</style>
