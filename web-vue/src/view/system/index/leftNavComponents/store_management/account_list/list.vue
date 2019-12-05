<template>
  <div class="experience-version">
    <div class="select-menu top">
      <div>{{this.$store.state.userName}}</div>
      <el-select
        v-model="mainData.state"
        :placeholder="$t('shopAccountList.selectState')"
        size="small"
        class="select-input ml-6"
        clearable
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
        :placeholder="$t('shopAccountList.inputName')"
        size="small"
        class="select-input ml-6"
        clearable
      ></el-input>
      <el-input
        v-model="mainData.company"
        :placeholder="$t('shopAccountList.inputCompany')"
        size="small"
        class="select-input ml-6"
        clearable
      ></el-input>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="searchAccount()"
      >
        {{$t('shopAccountList.search')}}
      </el-button>
    </div>

    <el-table
      class="experience-log mt-10 formTable"
      header-row-class-name="table-th"
      :data="formTable"
      border
      :style="{maxWidth:'100%',width: '100%',height:tableHeight+ 'px',overflowY:'auto'}"
    >
      <el-table-column
        prop="userName"
        :label="$t('shopAccountList.ashopAccountList.userName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="accountName"
        :label="$t('shopAccountList.ashopAccountList.nickName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="company"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.company')"
      >
      </el-table-column>
      <el-table-column
        prop="state"
        align="center"
        :formatter="changeState"
        :label="$t('shopAccountList.ashopAccountList.state')"
      >
      </el-table-column>
      <el-table-column
        prop="shopGrade"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.shopGrade')"
      >
        <template slot-scope="scope">
          <span>{{scope.row.shopGrade === 1 ? '普通店':scope.row.shopGrade}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="shopNumber"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.shopNumber')"
      >
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.shopNumber }}</span>
          <i
            class="el-icon-circle-plus-outline"
            @click="jumptoNewShop(scope.row.sysId, scope.row.userName)"
            style="cursor: pointer;color:blue; opacity:0.7"
          ></i>
        </template>
      </el-table-column>
      <el-table-column
        prop="addTime"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.addTime')"
      >
        <template slot-scope="scope">
          <div>{{moment(scope.row.addTime).format('YYYY-MM-DD')}}</div>
          <div>{{moment(scope.row.addTime).format('HH:mm:ss')}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="buyTime"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.buyTime')"
      >
        <template slot-scope="scope">
          <div v-show="scope.row.buyTime!==null">{{moment(scope.row.buyTime).format('YYYY-MM-DD')}}</div>
          <div v-show="scope.row.buyTime!==null">{{moment(scope.row.buyTime).format('HH:mm:ss')}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="endTime"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.endTime')"
      >
        <template slot-scope="scope">
          <div v-show="scope.row.endTime!==null">{{moment(scope.row.endTime).format('YYYY-MM-DD')}}</div>
          <div v-show="scope.row.endTime!==null">{{moment(scope.row.endTime).format('HH:mm:ss')}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.renewMoney')"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.mobile')"
      >
        <template slot-scope="scope">
          <span>{{scope.row.mobile === '' ? '未设置':scope.row.mobile}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        :label="$t('shopAccountList.ashopAccountList.operation')"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            style="color:#000"
            @click="handleEditAccount(scope.row)"
          >
            {{$t('shopAccountList.ashopAccountList.edit')}}
          </el-button>
        </template>
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
  name: 'list',
  data () {
    return {
      options: [
        {
          value: '',
          label: this.$t('shopAccountList.stateOption.state0')
        }, {
          value: '1',
          label: this.$t('shopAccountList.stateOption.state1')
        }, {
          value: '2',
          label: this.$t('shopAccountList.stateOption.state2')
        }, {
          value: '3',
          label: this.$t('shopAccountList.stateOption.state3')
        }, {
          value: '4',
          label: this.$t('shopAccountList.stateOption.state4')
        }],
      mainData: {
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
      text: '',
      tableHeight: document.documentElement.clientHeight - 330
    }
  },
  mounted () {
    let toUserName = this.$route.params.toUserName
    if (!this.isEmpty(toUserName)) {
      this.mainData.keywords = toUserName
    }
    this.tableHeight = document.documentElement.clientHeight - 330
    if (this.tableHeight < 0) {
      this.tableHeight = 400
    }
    console.log('toUserName')
    console.log(toUserName)
    console.log(this.$route)
    this.searchAccount()
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.searchAccount()
    },

    jumptoNewShop (sysId, userName) {
      this.$router.push({
        name: 'shopList',
        params: {
          name: sysId,
          flag: true,
          title: userName
        }
      })
    },
    handleEditAccount (data) {
      console.log('点击编辑')
      let params = {
        'sysId': data.sysId,
        'userName': data.userName,
        'name': 'third'
      }
      this.$emit('send', params)
    },
    // 审核状态的四种数字转化为文字
    changeState (row, col) {
      switch (row.state) {
        case 1: row.state = '申请中'
          break
        case 2: row.state = '审核通过'
          break
        case 3: row.state = '审核不通过'
          break
        case 4: row.state = '已禁用'
          break
      }
      return row.state
    },

    // 商家账户列表查询
    searchAccount () {
      searchAccountRequest(this.mainData).then((res) => {
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
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
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
