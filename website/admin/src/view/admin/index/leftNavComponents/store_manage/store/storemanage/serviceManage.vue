<template>
  <!-- 服务管理 -->
  <div class="service_manage_page">
    <el-tabs
      v-model="activeName"
      class="service_manage_tabs"
      @tab-click="tabClickHandle"
    >
      <el-tab-pane
        :label="$t('serviceManage.serviceList')"
        name="first"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="$t('serviceManage.classificationManagement')"
        name="second"
      >
      </el-tab-pane>
      <el-tab-pane
        :label="$t('serviceManage.addService')"
        name="third"
      >
      </el-tab-pane>
    </el-tabs>
    <router-view></router-view>
  </div>
</template>

<script>
import { delayJudgment } from '@/util/pageName'
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
    '$route.name': {
      handler: function (newVal) {
        console.log('route', newVal)
        this.initStatus()
      },
      immediate: true
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
      console.log(tabName)
      switch (tabName) {
        case 'first':
          this.$router.push({
            name: 'store_storemanage_service_list',
            query: {
              id: this.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
        case 'second':
          this.$router.push({
            name: 'store_storemanage_service_classify',
            query: {
              id: this.id,
              businessHours: this.$route.query.businessHours,
              businessType: this.$route.query.businessType
            }
          })
          break
        case 'third':
          delayJudgment(this, this.id, this.$t('pageName.addService'))
          this.$router.push({
            name: 'store_storemanage_service_add',
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
