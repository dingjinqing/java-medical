<template>
  <!-- 服务管理 -->
  <div class="service_manage_page">
    <el-tabs
      v-model="activeName"
      class="service_manage_tabs"
      @tab-click="tabClickHandle"
    >
      <el-tab-pane
        label="服务列表"
        name="first"
      >
      </el-tab-pane>
      <el-tab-pane
        label="分类管理"
        name="second"
      >
      </el-tab-pane>
      <el-tab-pane
        label="添加服务"
        name="third"
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
      activeName: 'first'
    }
  },
  watch: {
    activeName: {
      immediate: true,
      handler (newName) {
        console.log(newName)
      }
    }
  },
  created () {
    this.id = this.$route.query.id
    this.initStatus()
  },
  mounted () {
  },
  methods: {
    tabClickHandle (tab) {
      let tabName = tab.name
      console.log(222, this.$route)
      switch (tabName) {
        case 'first':
          this.$router.push({
            name: 'store_storemanage_service_list',
            query: {
              id: this.id
            }
          })
          break
        case 'second':
          this.$router.push({
            name: 'store_storemanage_service_classify',
            query: {
              id: this.id
            }
          })
          break
        case 'third':
          this.$router.push({
            name: 'store_storemanage_service_add',
            query: {
              id: this.id
            }
          })
          break
      }
    },
    initStatus () {
      let route = this.$route
      let name = route.name
      if (name === 'store_storemanage_service_list') {
        this.activeName = 'first'
      } else if (name === 'store_storemanage_service_classify') {
        this.activeName = 'second'
      } else if (name === 'store_storemanage_service_add') {
        this.activeName = 'third'
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
