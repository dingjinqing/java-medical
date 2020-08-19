<template>
  <div class="wrapper">
    <div class="dataFilter">
      <el-form
              ref="userFilterForm"
              :inline="true"
              :model="userFilterForm"
              >
        <el-form-item label="手机号："  prop="mobile">
          <el-input v-model="userFilterForm.mobile" placeholder="请输入手机号" size="small"/>
        </el-form-item>
        <el-form-item label="用户名："  prop="username">
          <el-input v-model="userFilterForm.username" placeholder="请输入用户名" size="small"/>
        </el-form-item>
        <el-form-item label="医院：" prop="shopId">
          <el-select v-model="userFilterForm.shopId" size="small">
            <el-option v-for="(item,index) in hospitalInfos" :key="index" :label="item.shopName" :value="item.shopId" />
          </el-select>
        </el-form-item>
        <el-form-item label="注册时间：">
          <el-date-picker
                  v-model="userFilterForm.startTime"
                  @change="datePickerChange(true)"
                  placeholder="开始时间"
                  size="small"
          />
          至
          <el-date-picker
                  v-model="userFilterForm.endTime"
                  @change="datePickerChange(false)"
                  placeholder="结束时间"
                  size="small"
          />
        </el-form-item>
        <br/>
        <el-form-item>
          <el-button size="small" type="primary" @click="loadUserList">确定</el-button>
          <el-button size="small" @click="userFilterFormReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="dataContent">
      <div class="dataList">
        <el-table
                :data="userListInfos"
                border
                style="width: 100%"
        >
          <el-table-column type="index" width="50" align="center"/>
          <el-table-column label="用户编号" align="center" prop="userId"/>
          <el-table-column label="用户昵称" align="center" prop="username"/>
          <el-table-column label="手机号" align="center" prop="mobile"/>
          <el-table-column label="医院" align="center" prop="shopName"/>
          <el-table-column label="用户余额" align="center" prop="account"/>
          <el-table-column label="积分" align="center" prop="score"/>
          <el-table-column label="注册时间" align="center" prop="createTime"/>
        </el-table>
      </div>
      <div class="footer">
        <span>每页{{this.pageInfo.pageRows}}行记录，当前页面：{{this.pageInfo.currentPage}}，总页数：{{this.pageInfo.pageCount}}，总记录数为：{{this.pageInfo.totalRows}}</span>
        <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="pageInfo.currentPage"
                layout="prev, pager, next, jumper"
                :page-count="pageInfo.pageCount"
                :small="true"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { loadAllShopInfoApi } from '@/api/system/shopList.js'
import { loadUserListApi } from '@/api/system/userManager/user.js'

import { startOrEndDayWithFormat } from '@/util/date.js'
export default {
  data () {
    return {
      userFilterForm: {
        mobile: null,
        username: null,
        shopId: null,
        startTime: null,
        endTime: null
      },
      hospitalInfos: [],
      userListInfos: [],
      pageInfo: {
        totalRows: null,
        pageRows: 20,
        currentPage: 0,
        pageCount: 0
      }
    }
  },
  methods: {
    datePickerChange (isStart) {
      if (this.userFilterForm.startTime == null || this.userFilterForm.endTime == null) {
        return
      }
      let begin = this.userFilterForm.startTime.getTime()
      let end = this.userFilterForm.endTime.getTime()

      if (begin <= end) {
        return
      }
      if (isStart) {
        this.userFilterForm.startTime = null
      } else {
        this.userFilterForm.endTime = null
      }
    },
    userFilterFormReset () {
      this.$refs['userFilterForm'].resetFields()
      this.userFilterForm.startTime = null
      this.userFilterForm.endTime = null
    },
    /** 加载医院列表 */
    loadHospitalInfo () {
      loadAllShopInfoApi().then(res => {
        if (res.error !== 0) {
          return
        }
        this.hospitalInfos = res.content
        if (this.hospitalInfos.length > 0) {
          this.hospitalInfos.unshift({shopId: null, shopName: '所有医院'})
        }
      })
    },
    /** 分页查询用户信息 */
    loadUserList () {
      let data = {
        ...this.userFilterForm,
        ...this.pageInfo
      }

      if (data.startTime !== null) {
        data.startTime = startOrEndDayWithFormat(data.startTime, true)
      }
      if (data.endTime !== null) {
        data.endTime = startOrEndDayWithFormat(data.endTime, false)
      }

      loadUserListApi(data).then(res => {
        if (res.error !== 0) {
          return
        }
        this.pageInfo = {
          ...res.content.page
        }
        this.userListInfos = res.content.dataList
      })
    },
    /** 分页动作 */
    handleCurrentChange () {
      this.loadUserList()
    }
  },
  mounted () {
    this.loadHospitalInfo()
    this.loadUserList()
  }
}
</script>

<style scoped>
  .wrapper{
    margin-top: 18px;
  }
  .dataFilter{
    background:#fff;
    padding: 10px;
    margin-bottom: 10px;
  }
  .dataContent{
    padding: 10px;
    background: #fff;
  }
  .footer {
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  .footer > span {
    font-size: 14px;
  }
</style>
