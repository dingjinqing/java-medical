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
        v-model="mainData.shopType"
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
        v-model="mainData.openPay"
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
        v-model="mainData.isEnabled"
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
    >
      <el-table-column
        prop="shopId"
        :label="$t('publishList.table.shopID')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="nickName"
        :label="$t('publishList.table.wechatName')"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="principalName"
        align="center"
        :label="$t('publishList.table.companyName')"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        align="center"
        :label="$t('publishList.table.createTime')"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.createTime!=null">
            <div>{{moment(scope.row.createTime).format('YYYY-MM-DD')}}</div>
            <div>{{moment(scope.row.createTime).format('HH:mm:ss')}}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('publishList.table.startTime')"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.startTime!=null">
            <div>{{moment(scope.row.startTime).format('YYYY-MM-DD')}}</div>
            <div>{{moment(scope.row.startTime).format('HH:mm:ss')}}</div>
            <div>({{scope.row.userVersion}})</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="lastUploadTime"
        align="center"
        :label="$t('publishList.table.nowTime')"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.lastUploadTime!==null">
            <div>{{moment(scope.row.lastUploadTime).format('YYYY-MM-DD')}}</div>
            <div>{{moment(scope.row.lastUploadTime).format('HH:mm:ss')}}</div>
            <div>({{scope.row.bindUserVersion}})</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="openPay"
        align="center"
        :label="$t('publishList.table.pay')"
        :formatter="openPayFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="shopType"
        align="center"
        :label="$t('publishList.table.shopType')"
        :formatter="shopTypeFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="shopExpireStatus"
        align="center"
        :label="$t('publishList.table.shopState')"
        :formatter="shopExpireStatusFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="isEnabled"
        align="center"
        :label="$t('publishList.table.disabled')"
        :formatter="isEnabledFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        :label="$t('publishList.table.money')"
      >
      </el-table-column>
      <el-table-column
        prop="expireTime"
        align="center"
        :label="$t('publishList.table.endTime')"
      >
        <template slot-scope="scope">
          <div>{{moment(scope.row.expireTime).format('YYYY-MM-DD')}}</div>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        :label="$t('publishList.table.view')"
      >
        <template slot-scope="scope">
          <el-button
            class="xbutton"
            type="text"
            @click="show(scope.row.appId)"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="footer clearfixed pagination-wrap">
      <span>每页{{this.pageRows}}行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数为：{{this.totalRows}}</span>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        layout="prev, pager, next, jumper"
        :page-count="pageCount"
        :small="pagination_b"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getListRequest } from '@/api/system/shopContent.js'

export default {
  name: 'experienceVersion',
  data () {
    return {
      shopOptions: this.$t('shopAccountList.auth_state'),

      payOptions: [{
        value: '',
        label: '选择支付类型'
      }, {
        value: '0',
        label: '未开通'
      }, {
        value: '1',
        label: '已开通'
      }],
      disabledOptions: [{
        value: '',
        label: '选择禁用类型'
      }, {
        value: '0',
        label: '未禁用'
      }, {
        value: '1',
        label: '已禁用'
      }],
      mainData: {
        keywords: null,
        shopType: null,
        openPay: null,
        isEnabled: null
      },
      formTable: [],
      totalRows: 0,
      pageRows: 0,
      currentPage: 1,
      pageCount: 0,
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
      let param = {
        'keywords': this.mainData.keywords,
        'shopType': this.mainData.shopType,
        'openPay': this.mainData.openPay,
        'isEnabled': this.mainData.isEnabled
      }
      getListRequest(param).then((res) => {
        if (res.error === 0) {
          console.log('结果')
          console.log(res.content.dataList)
          this.formTable = res.content.dataList
          this.currentPage = res.content.page.currentPage
          this.pageRows = res.content.page.pageRows
          this.pageCount = res.content.page.pageCount
          this.totalRows = res.content.page.totalRows
          this.firstPage = res.content.page.firstPage
          this.lastPage = res.content.page.lastPage
          this.nextPage = res.content.page.nextPage
        } else {

        }
      })
    },
    openPayFormatter (row, column) {
      switch (row.openPay) {
        case 0: row.openPaySing = '无支付'
          break
        case 1: row.openPaySing = '有支付'
          break
      }
      return row.openPaySing
    },
    shopTypeFormatter (row, column) {
      switch (row.shopType) {
        case 'v1': row.shopTypeSing = '体验版'
          break
        case 'v2': row.shopTypeSing = '基础版'
          break
        case 'v3': row.shopTypeSing = '高级版'
          break
        case 'v4': row.shopTypeSing = '旗舰版'
          break
      }
      return row.shopTypeSing
    },
    shopExpireStatusFormatter (row, column) {
      switch (row.shopExpireStatus) {
        case '0': row.shopExpireStatusSing = '未过期'
          break
        case '1': row.shopExpireStatusSing = '已过期'
          break
      }
      return row.shopExpireStatusSing
    },
    isEnabledFormatter (row, column) {
      switch (row.isEnabled) {
        case 0: row.isEnabledSing = '未禁用'
          break
        case 1: row.isEnabledSing = '已禁用'
          break
      }
      return row.isEnabledSing
    },
    show (data) {
      this.$router.push({
        name: 'programManage',
        params: {
          page: 'authMsg',
          appId: data
        }
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
