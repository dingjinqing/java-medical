<template>
  <!-- 服务管理 -->
  <div class="service_manage_page">
    <el-tabs v-model="activeName">
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
      activeName: 'first',
      serviceCat: null,
      serviceCats: [
        { id: 1, name: '添加' }
      ],
      tableData: [],
      form: {}
    }
  },
  watch: {
    activeName: {
      immediate: true,
      handler (newName) {
        console.log(newName)
        switch (newName) {
          case 'first':
            this.$router.replace({
              name: 'store_storemanage_service_list',
              query: {
                id: this.id
              }
            })
            break
          case 'second':
            this.$router.replace({
              name: 'store_storemanage_service_classify',
              query: {
                id: this.id
              }
            })
            break
          case 'third':
            this.$router.replace({
              name: 'store_storemanage_service_add',
              query: {
                id: this.id
              }
            })
            break
        }
      }
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    searchHandle () {

    },
    edit () {

    },
    initDataList () {

    }
  }
}
</script>

<style lang="scss" scoped>
.service_manage_page {
  .sevice_list {
    .list-info {
    }
  }
  .service_class_info {
    display: flex;
    justify-content: space-between;
  }
}
</style>
