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
              :label="$t('storeManage.appointmentManagement')"
              name="first"
            >
            </el-tab-pane>
            <el-tab-pane
              :label="$t('storeManage.serviceManagement')"
              name="second"
            >
            </el-tab-pane>
            <el-tab-pane
              :label="$t('storeManage.technicianManagement')"
              name="third"
            >
            </el-tab-pane>
            <el-tab-pane
              :label="$t('storeManage.evaluationManagement')"
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
import { judgeJurisdictionRequest } from '@/api/admin/util.js'
export default {
  data () {
    return {
      id: null, // 门店id
      businessHours: null, // 营业时间
      businessType: 1, // 工作日还是每天
      activeName: 'first'
    }
  },
  created () {
    this.id = this.$route.query.id
    this.businessHours = this.$route.query.businessHours
    this.businessType = this.$route.query.businessType
    this.langDefault()
    this.initStatus()
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
              businessHours: this.businessHours,
              businessType: this.businessType
            }
          })
          break
        case 'third':
          console.log('触发')
          judgeJurisdictionRequest({
            'V-EnName': 'store_service_config',
            'V-VsName': 'service'
          }).then(res => {
            if (res.error === 0) {
              this.$router.push({
                name: 'store_storemanage_technician_list',
                query: {
                  id: this.id,
                  businessHours: this.businessHours,
                  businessType: this.businessType
                }
              })
            } else if (res.error === 10031) {
              this.$http.$emit('jurisdictionDialog')
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
