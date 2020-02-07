<template>
  <!-- 技师管理 -->
  <div class="technician_manage_page">
    <el-tabs
      v-model="activeName"
      class="technician_manage_tabs"
      @tab-click="tabClickHandle"
    >
      <el-tab-pane
        :label="technicianList"
        name="first"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="technicianClass"
        name="second"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="addTechnician"
        name="third"
      >
      </el-tab-pane>
    </el-tabs>
    <router-view></router-view>
  </div>
</template>

<script>
import utils from '@/util/public.js'
export default {
  data () {
    return {
      id: '',
      activeName: 'first',
      // 配置技师职称
      technicianList: this.$t('technicianManage.technicianList'),
      technicianClass: this.$t('technicianManage.technicianClass'),
      addTechnician: this.$t('technicianManage.addTechnician')
    }
  },
  mounted () {
    this.id = this.$route.query.id
    this.langDefault()
    this.initStatus()
    utils.getJobTitle().then(res => {
      if (res) {
        this.technicianList = res + this.$t('technicianManage.technicianListR')
        this.technicianClass = res + this.$t('technicianManage.technicianClassR')
        this.addTechnician = this.$t('technicianManage.addTechnicianL') + res
      }
    })
  },
  watch: {
    $route: function (val) {
      this.initStatus()
    },
    lang: function (val) {
      utils.getJobTitle().then(res => {
        if (res) {
          this.technicianList = res + this.$t('technicianManage.technicianListR')
          this.technicianClass = res + this.$t('technicianManage.technicianClassR')
          this.addTechnician = this.$t('technicianManage.addTechnicianL') + res
        }
      })
    }
  },
  methods: {
    tabClickHandle (tab) {
      let tabName = tab.name
      console.log(222, this.$route)
      switch (tabName) {
        case 'first':
          this.$router.push({
            name: 'store_storemanage_technician_list',
            query: {
              id: this.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
        case 'second':
          this.$router.push({
            name: 'store_storemanage_technician_classify',
            query: {
              id: this.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
        case 'third':
          this.$router.push({
            name: 'store_storemanage_technician_add',
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
      if (name === 'store_storemanage_technician_list') {
        this.activeName = 'first'
      } else if (name === 'store_storemanage_technician_classify') {
        this.activeName = 'second'
      } else if (name === 'store_storemanage_technician_add') {
        this.activeName = 'third'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.technician_manage_page {
  .technician_manage_tabs {
    margin: 0 25px;
  }
}
</style>
