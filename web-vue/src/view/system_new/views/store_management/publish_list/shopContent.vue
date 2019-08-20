<template>
  <div class="experience-version">
    <div class="select-menu top">
      <el-input
        v-model="mainData.keywords"
        :placeholder="$t('publishList.inputName')"
        size="small"
        class="select-input ml-6"
        clearable
      ></el-input>
      <el-select
        v-model="mainData.state"
        :placeholder="$t('publishList.shopOptions.selectType')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in shopOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-select
        v-model="mainData.state"
        :placeholder="$t('publishList.payOptions.selectType')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in payOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-select
        v-model="mainData.state"
        :placeholder="$t('publishList.disabledOption.selectType')"
        size="small"
        class="select-input ml-6"
        clearable
      >
        <el-option
          v-for="item in disabledOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        size="small"
        class="ml-6"
        type="primary"
        @click="searchAccount()"
      >
        {{$t('publishList.search')}}
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
        :label="$t('publishList.table.shopID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="accountName"
        :label="$t('publishList.table.wechatName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="company"
        align="center"
        :label="$t('publishList.table.companyName')"
      >
      </el-table-column>
      <el-table-column
        prop="state"
        align="center"
        :formatter="changeState"
        :label="$t('publishList.table.createTime')"
      >
      </el-table-column>
      <el-table-column
        prop="shopGrade"
        align="center"
        :label="$t('publishList.table.startTime')"
      >
        <template slot-scope="scope">
          <span>{{scope.row.shopGrade === 1 ? '普通店':scope.row.shopGrade}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="shopNumber"
        align="center"
        :label="$t('publishList.table.nowTime')"
      >
      </el-table-column>
      <el-table-column
        prop="addTime"
        align="center"
        :label="$t('publishList.table.pay')"
      >
      </el-table-column>
      <el-table-column
        prop="buyTime"
        align="center"
        :label="$t('publishList.table.shopType')"
      >
      </el-table-column>
      <el-table-column
        prop="endTime"
        align="center"
        :label="$t('publishList.table.shopState')"
      >
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        :label="$t('publishList.table.disabled')"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        align="center"
        :label="$t('publishList.table.money')"
      >
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        :label="$t('publishList.table.endTime')"
      >
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        :label="$t('publishList.table.view')"
      >
        <!-- <el-button
          type="text"
          style="color:#000"
          @click="handleView"
        >{{$t('publishList.table.view')}}</el-button> -->
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
// import { searchAccountRequest } from '@/api/system/accountList.js'

export default {
  name: 'experienceVersion',
  data () {
    return {
      shopOptions: [{
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

      payOptions: [{
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

      disabledOptions: [{
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

    jumptoNewShop (sysId, userName) {
      this.$router.push({
        name: 'shopList',
        params: {
          name: sysId,
          flag: true,
          title: userName
        }
      })
      // bus.$emit('revice', userName)
      console.log(this.userName)
    },
    handleView () {
      this.name = 'third'
      this.$emit('send', this.name)
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
      // let obj1 = {
      // }
      // let parameter = Object.assign(obj1, this.mainData)
      // searchAccountRequest(parameter).then((res) => {
      //   // console.log(res)
      //   const { error, content } = res
      //   if (error === 0) {
      //     let formList = content.dataList
      //     let pageObj = content.page
      //     this.totalRows = pageObj.totalRows
      //     this.currentPage = pageObj.currentPage
      //     this.firstPage = pageObj.firstPage
      //     this.lastPage = pageObj.lastPage
      //     this.nextPage = pageObj.nextPage
      //     this.pageCount = pageObj.pageCount
      //     this.pageRows = pageObj.pageRows

      //     this.formTable = formList
      //   }
      // }).catch(() => {
      //   this.$message.error('保存失败')
      // })
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
