<template>
  <div class="store-permissions-configuration">
    <h2>页面权限</h2>
    <el-checkbox
      class="page-check-all"
      v-model="selectAll"
    >全选</el-checkbox>
    <table class="page-table">
      <tbody>
        <tr
          v-for="(trs, index) in pagePermissions"
          :key="trs.enName"
        >
          <td>
            <el-checkbox :checked="trs.checked == 1">{{trs.name}}</el-checkbox>
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
      <el-checkbox>售后处理</el-checkbox>
      <el-checkbox>评价审核</el-checkbox>
      <el-checkbox>评价删除</el-checkbox>
      <el-checkbox>订单改价</el-checkbox>
    </el-checkbox-group>
    <div class="footer">
      <el-button
        type="primary"
        size="small"
      >保存</el-button>
    </div>
  </div>
</template>

<script>
import { getSettingApi } from '@/api/admin/storeManage/storePermission'
export default {
  data () {
    return {
      selectAll: false,
      pagePermissions: [], // 页面权限
      functionalAuthority: [], // 功能权限
      authTdChecks: []
    }
  },
  mounted () {
    this.initCheck()
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
            that.authTdChecks.push([])
          })
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
