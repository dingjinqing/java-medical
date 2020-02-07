<template>
  <div class="payContent">
    <el-dialog
      :title="$t('authRoleList.addRoleGroup')"
      :visible.sync="centerDialogVisible"
      v-if="centerDialogVisible"
    >

      <el-tabs
        v-model="defaultBtn"
        @tab-click="handleClick"
      >
        <!--页面权限管理 -->
        <el-tab-pane
          :label="$t('authRoleList.firstPage')"
          name="first"
        >
          <authRoleList
            v-if="showFlag"
            @privilegeInfo="sendList"
            ref="authRoleList"
            :isEdit="edit"
          />
        </el-tab-pane>
        <!--功能权限管理 -->
        <el-tab-pane
          :label="$t('authRoleList.secondPage')"
          name="second"
        >
          <authRolePwd
            v-if="showFlagTwo"
            @privilegePassInfo="sendPwdList"
            ref="authRolePwd"
            :isEdit="edit"
          />
        </el-tab-pane>
      </el-tabs>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="mini"
          @click="centerDialogVisible=false"
        >{{$t('authRoleList.cancel')}}</el-button>
        <el-button
          size="mini"
          type="primary"
          @click="befSub()"
        >{{$t('authRoleList.sure')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import authRoleList from './authRoleList'
import authRolePwd from './authRolePwd'
import { addRoleListRequest, editUpdateRoleRequest } from '@/api/admin/basicConfiguration/shopAuthority.js'
export default {
  components: {
    authRoleList,
    authRolePwd
  },
  props: {
    tuneUp: Boolean, // 调起弹窗
    isEdit: { // 是否是编辑
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      showFlag: true,
      defaultBtn: 'first',
      centerDialogVisible: false,
      haveClick: false,
      haveClick1: false,
      authRoleListData: null,
      showFlagTwo: true,
      authRolePwdListData: [],
      flag1: false,
      flag2: false,
      edit: this.isEdit
    }
  },
  watch: {
    tuneUp (newData) {
      console.log('监听')
      console.info(newData)
      if (newData) {
        this.centerDialogVisible = true
      } else {
        this.$emit('update:tuneUp', false)
      }
    },
    isEdit (newData) {
      console.log('监听')
      console.info(newData)
      this.edit = newData
      if (newData !== 0) {
        this.centerDialogVisible = true
      }
    },
    centerDialogVisible (newData) {
      console.log('监听centerDialogVisible')
      console.info(newData)
      if (newData === false) {
        this.sendFa()
      }
    }
  },
  methods: {
    show (data) {
      console.log('收到')
      console.log(data)
      if (data) {
        if (data.flag === 2) {
          this.defaultBtn = 'second'
          this.showFlagTwo = true
        }
        if (data.flag === 1) {
          this.defaultBtn = 'first'
          this.showFlag = true
        }
      }
    },
    handleClick (tab, event) {
      if (tab.name === 'first') {
        this.defaultBtn = 'first'
        this.showFlag = true
      }
      if (tab.name === 'second') {
        this.defaultBtn = 'second'
        this.showFlagTwo = true
      }
    },
    sendList (data) {
      console.log('收到111')
      console.log(data)
      this.authRoleListData = data
      this.flag1 = true
      if (!this.showFlagTwo) {
        // 功能权限管理页面没有打开，也就没数据
        this.flag2 = true
      }
      this.submitInfo()
    },
    sendPwdList (data) {
      console.log('收到222')
      console.log(data)
      this.authRolePwdListData = data
      this.flag2 = true
      this.submitInfo()
    },
    befSub () {
      this.flag1 = false
      this.flag2 = false
      this.$refs.authRoleList.uploadData()
      this.$refs.authRolePwd.uploadData()
    },
    submitInfo () {
      console.log('submitInfosubmitInfosubmitInfosubmitInfosubmitInfo')
      if (this.flag1 && this.flag2) {
        console.log('编辑')
        console.log(this.authRoleListData)
        console.log(this.authRolePwdListData.privilegePass)
        let params = {
          'roleName': this.authRoleListData.roleName,
          'privilegeList': this.authRoleListData.privilegeList,
          'privilegePass': this.authRolePwdListData.privilegePass,
          'loginPass': this.authRolePwdListData.loginPass,
          'rolePass': this.authRolePwdListData.rolePass,
          'roleId': this.edit
        }
        console.log('发送')
        console.log(params)
        if (this.edit === 0) {
          // 添加
          this.add(params)
        } else {
          // 编辑

          this.editUpdate(params)
        }
      }
    },
    add (params) {
      addRoleListRequest(params).then((res) => {
        console.log('添加权限组')
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          this.centerDialogVisible = false
          this.sendFa()
        } else {
          console.log('错误，重新赋值可点击')
          this.haveClick = false
          this.$message.error(res.message)
        }
      })
    },
    editUpdate (params) {
      editUpdateRoleRequest(params).then((res) => {
        console.log('编辑添加权限组')
        console.log(res)
        if (res.error === 0) {
          this.$message.success(res.message)
          this.centerDialogVisible = false
          this.sendFa()
        } else {
          this.haveClick = false
          this.$message.error(res.message)
        }
      })
    },
    sendFa () {
      console.log('让父页面刷新')
      let params = {
        'refresh': true
      }
      this.$emit('refreshFa', params)
    }
  }
}

</script>
<style lang="scss" scoped>
.payContent {
  margin: 10px;
  padding: 10px 20px 0 20px;
  font-size: 14px;
  height: 100%;
  background: #fff;
}
</style>
