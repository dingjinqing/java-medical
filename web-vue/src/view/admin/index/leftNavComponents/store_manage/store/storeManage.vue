<template>
  <!-- 门店管理 -->
  <div class="receiveDetail store_manage_page">
    <div class="receiveDetailMain">
      <div class="order-container">
        <div class="order-top">
          <el-tabs
            type="border-card"
            v-model="activeName"
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
  props: {
    id: Number
  },
  data () {
    return {
      activeName: 'first'
    }
  },
  mounted () {
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
  },
  watch: {
    activeName: {
      immediate: true,
      handler: function (newName) {
        console.log(newName)
        switch (newName) {
          case 'first':
            this.$router.replace({
              name: 'store_storemanage_reserve',
              query: {
                id: this.id
              }
            })
            break
          case 'second':
            this.$router.replace({
              name: 'store_storemanage_service',
              query: {
                id: this.id
              }
            })
            break
          case 'third':
            this.$router.replace({
              name: 'store_storemanage_technician',
              query: {
                id: this.id
              }
            })
            break
          case 'fourth':
            this.$router.replace({
              name: 'store_storemanage_comment',
              query: {
                id: this.id
              }
            })
            break
        }
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
    padding: 15px 18px;
    .el-tabs--border-card {
      box-shadow: none;
      height: 40px;
      overflow: hidden;
    }
  }
}
</style>
