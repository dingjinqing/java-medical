<template>
  <div class="container">
    <div class="top">
      <div class="filter-list">
        <el-tabs
          v-model="activeName"
          @tab-click="tabClickHandle"
        >
          <el-tab-pane
            label="门店账户列表"
            name="1"
          >
          </el-tab-pane>
          <el-tab-pane
            label="店员权限配置"
            name="2"
          ></el-tab-pane>
        </el-tabs>
        <el-button
          class="go-store-backstage"
          type="primary"
          size="small"
        >门店后台</el-button>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeName: '1'
    }
  },
  mounted () {
    console.log(this.$route)
    if (this.$route.name === 'store_permission_list') {
      this.activeName = '1'
    } else {
      this.activeName = '2'
    }
  },
  methods: {
    tabClickHandle (tab, e) {
      console.log(tab, e)
      let name = tab.name
      if (name === '1') {
        this.$router.push({
          path: '/admin/home/main/store/storePermission/list'
        })
      } else if (name === '2') {
        this.$router.push({
          path: '/admin/home/main/store/storePermission/setting'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  width: 100%;
  height: 100%;
  font-size: 14px;
  color: #333;
  .top {
    background: #fff;
    padding: 15px;
    margin-bottom: 50px;
    .filter-list {
      position: relative;
      .go-store-backstage {
        position: absolute;
        right: 10px;
        top: 0px;
      }
    }
  }
}
</style>
