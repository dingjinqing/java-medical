<template>
  <div class="store-permissions-configuration">
    <h2>页面权限</h2>
    <el-checkbox
      class="page-check-all"
      v-model="selectAll"
      @change="selectAllChangeHandle($event)"
    >全选</el-checkbox>
    <table class="page-table">
      <tbody>
        <tr
          v-for="(trs, index) in pagePermissions"
          :key="trs.enName"
        >
          <td>
            <el-checkbox
              v-model="trs.checked"
              @change="changeCheckHandle(index, $event)"
              :checked="trs.checked"
            >{{trs.name}}</el-checkbox>
          </td>
          <td>
            <el-checkbox-group v-model="authTdChecks[index]">
              <el-checkbox
                v-for="(td, i) in trs.sub"
                :key="td.enName+i"
                :label="td.enName"
                :checked="td.check == 1"
              >{{td.name}}</el-checkbox>
            </el-checkbox-group>
          </td>
        </tr>
      </tbody>
    </table>
    <h2>功能权限</h2>
    <el-checkbox-group v-model="functionalAuthority">
      <el-checkbox label="after_sale_process">售后处理</el-checkbox>
      <el-checkbox label="evaluation_review">评价审核</el-checkbox>
      <el-checkbox label="evaluation_del">评价删除</el-checkbox>
      <el-checkbox label="order_modify_price">订单改价</el-checkbox>
    </el-checkbox-group>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
        @click="savePermissionHandle"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import { getSettingApi, setStorePermissionApi } from '@/api/admin/storeManage/storePermission'
export default {
  data () {
    return {
      pagePermissions: [], // 页面权限
      functionalAuthority: [], // 功能权限
      authTdChecks: [],
      selectAll: false
    }
  },
  mounted () {
    this.initCheck()
  },
  watch: {
    authTdChecks: {
      handler: function (newVal, oldVal) {
        let that = this
        let checkNum = 0 // 一共选中了几个左侧标题
        console.log('val:', newVal, oldVal)
        if (Array.isArray(newVal)) {
          let permissions = that.pagePermissions
          // 如果是没有sub，那么subLen为0， item.length 一直为0
          newVal.forEach((item, index) => {
            // 如果让取消门店概况：if ((item.length === permissions[index].subLen && permissions[index].subLen !== 0) || (permissions[index].checked !== false && permissions[index].subLen === 0)) {
            if (item.length === permissions[index].subLen) {
              permissions[index].checked = true
              checkNum++
            } else {
              permissions[index].checked = false
            }
          })
          console.log('checkNum', checkNum)
          if (checkNum === permissions.length) {
            that.selectAll = true
          } else {
            that.selectAll = false
          }
          that.pagePermissions = permissions
        }
      },
      deep: true
    }
  },
  methods: {
    initCheck () {
      let that = this
      getSettingApi().then(res => {
        console.log(res)
        if (res.error === 0) {
          that.pagePermissions = res.content.authList
          that.functionalAuthority = res.content.funCfg
          that.authTdChecks = []
          that.pagePermissions.forEach(item => {
            item.subLen = item.sub.length
            that.authTdChecks.push([])
          })
        }
      })
    },
    // 左侧标题选中切换
    changeCheckHandle (index, val) {
      console.log(index, val)
      let subs = this.pagePermissions[index].sub
      if (val) {
        let checks = subs.map(item => item.enName)
        this.$set(this.authTdChecks, index, checks)
      } else {
        this.$set(this.authTdChecks, index, [])
      }
    },
    // 全选
    selectAllChangeHandle (val) {
      console.log(val)
      if (val) {
        this.pagePermissions.forEach((item, index) => {
          item.checked = true
          this.changeCheckHandle(index, true)
        })
      } else {
        this.pagePermissions.forEach((item, index) => {
          item.checked = false
          this.changeCheckHandle(index, false)
        })
      }
    },
    // 保存
    savePermissionHandle () {
      // 左侧选中
      let menuCfg = this.pagePermissions.filter(item => {
        return item.checked
      })
      menuCfg = menuCfg.map(item => item.enName)
      // 右侧选中
      let subMenuCfg = []
      this.authTdChecks.forEach(item => {
        subMenuCfg.push(...item)
      })
      let params = {
        menu_cfg: menuCfg,
        sub_menu_cfg: subMenuCfg,
        fun_cfg: this.functionalAuthority
      }
      setStorePermissionApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.store-permissions-configuration {
  margin-bottom: 10px;
  h2 {
    padding: 10px 0 5px;
    font-weight: bold;
  }
  .page-check-all {
    padding: 8px 0px;
  }
  .page-table {
    width: 100%;
    td {
      padding: 8px 10px;
      border: 1px solid #ccc;
    }
  }
  .footer {
    position: fixed;
    left: 150px;
    bottom: 0;
    z-index: 100;
    width: calc(100% - 150px);
    line-height: 50px;
    border-top: 1px solid #f2f2f2;
    background: #f8f8fa;
    text-align: center;
  }
}
</style>
