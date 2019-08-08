<template>
  <div class="experience-version">
    <div class="select-menu top">
      <div>{{this.$store.state.userName}}</div>
      <el-select
        v-model="mainData.state"
        placeholder="选择审核状态"
        size="small"
        class="select-input ml-6"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-input
        v-model="mainData.keywords"
        placeholder="请输入用户名、昵称"
        size="small"
        class="select-input ml-6"
      ></el-input>
      <el-input
        v-model="mainData.company"
        placeholder="请输入公司名称"
        size="small"
        class="select-input ml-6"
      ></el-input>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="searchAccount()"
      >
        搜索
      </el-button>
    </div>

    <el-table
      class="experience-log mt-10 formTable"
      header-row-class-name="table-th"
      :data="formTable"
      border
      style="width: 100%"
      height="400"
    >
      <el-table-column
        prop="userName"
        label="用户名"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="keywords"
        label="昵称"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="company"
        align="center"
        label="公司名称"
      >
      </el-table-column>
      <el-table-column
        prop="state"
        align="center"
        :formatter="numberChange"
        label="审核状态"
      >
      </el-table-column>
      <el-table-column
        prop="shopGrade"
        align="center"
        :formatter="levelChange"
        label="店铺等级"
      >
      </el-table-column>
      <el-table-column
        prop="shopNumber"
        align="center"
        label="店铺总数"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.shopNumber }}</span>
          <i
            class="el-icon-circle-plus-outline"
            @click="jumptoNewShop(scope.row.userName)"
            style="cursor: pointer"
          ></i>
        </template>
      </el-table-column>
      <el-table-column
        prop="addTime"
        align="center"
        label="添加时间"
      >
      </el-table-column>
      <el-table-column
        prop="buyTime"
        align="center"
        label="首次续费"
      >
      </el-table-column>
      <el-table-column
        prop="endTime"
        align="center"
        label="到期时间"
      >
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        label="续费总额"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        align="center"
        :formatter="mobile"
        label="手机号"
      >
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        label="操作"
      >
        <el-button
          type="text"
          style="color:#000"
          @click="handleEditAccount"
        >编辑</el-button>
      </el-table-column>
    </el-table>

    <div class="footer clearfixed pagination-wrap">
      <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage3"
        layout="prev, pager, next, jumper"
        :page-count="pageCount"
        :small="pagination_b"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { searchAccountRequest } from '@/api/system/accountList.js'

export default {
  name: 'experienceVersion',
  data () {
    return {
      options: [{
        value: '1',
        label: '审核中'
      }, {
        value: '2',
        label: '审核通过'
      }, {
        value: '3',
        label: '审核不通过'
      }, {
        value: '4',
        label: '已禁用'
      }],
      mainData: {
        currentPage3: 1,
        state: '',
        keywords: '',
        company: ''
      },
      formTable: [{
        usreName: '',
        company: '',
        state: '',
        shopGrade: '',
        shopNumber: '',
        addTime: '',
        buyTime: '',
        endTime: '',
        renewMoney: '',
        mobile: ''
      }],
      totalRows: null,
      pageRows: '',
      currentPage: '',
      currentPage3: 1,
      pageCount: null,
      pagination_b: true,
      value: '',
      text: ''
    }
  },
  created () {
    this.searchAccount()
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.searchAccount()
    },

    jumptoNewShop (userName) {
      console.log(this.userName)
      this.$router.push({
        name: 'shopList',
        params: {
          name: userName,
          flag: true
        }
      })
      // bus.$emit('revice', userName)
    },
    handleEditAccount () {
      this.name = 'third'
      this.$emit('send', this.name)
    },
    // 审核状态的四种数字转化为文字
    numberChange (row, rol) {
      for (var i in this.formTable) {
        if (this.formTable[i].state === 1) {
          return '申请中'
        } else if (this.formTable[i].state === 2) {
          return '审核通过'
        } else if (this.formTable[i].state === 3) {
          return '审核不通过'
        } else {
          return '已禁用'
        }
      }
    },
    // 店铺等级的数字转化为文字
    levelChange (row, rol) {
      for (var i in this.formTable) {
        if (this.formTable[i].shopGrade === 1) {
          return '普通店'
        }
      }
    },
    // 电话号码文字处理
    mobile (row, rol) {
      for (var i in this.formTable) {
        if (this.formTable[i].mobile === '') {
          return '未设置'
        } else {
          return this.formTable[i].mobile
        }
      }
    },
    // 商家账户列表查询
    searchAccount () {
      let obj1 = {
        'currentPage': this.currentPage3,
        'pageRows': '20',
        'state': '',
        'keywords': '',
        'company': ''
      }
      let parameter = Object.assign(obj1, this.mainData)
      searchAccountRequest(parameter).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          let formList = content.dataList
          let pageObj = content.page
          this.totalRows = pageObj.totalRows
          this.currentPage = pageObj.currentPage
          this.firstPage = pageObj.firstPage
          this.lastPage = pageObj.lastPage
          this.nextPage = pageObj.nextPage
          this.pageCount = pageObj.pageCount
          this.pageRows = pageObj.pageRows

          this.formTable = formList
        }
      }).catch(() => {
        this.$message.error('保存失败')
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.select-menu {
  display: flex;
  padding: 10px;
  background: #fff;
}
.select-input {
  width: 200px;
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
