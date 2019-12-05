<template>
  <div class="experience-version">
    <el-table
      class="experience-log mt-10 formTable"
      header-row-class-name="table-th"
      :data="formTable"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="shopId"
        label="店铺ID"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="shopName"
        label="店铺名称"
        align="center"
      >
        {{sendData.shopName}}
      </el-table-column>
      <el-table-column
        prop="mobile"
        align="center"
        label="手机号"
      >
      </el-table-column>
      <el-table-column
        prop="accountName"
        align="center"
        label="所属账号"
      >
      </el-table-column>
      <el-table-column
        prop="renewType"
        align="center"
        label="续费类型"
        :formatter="renewTypeForm"
      >
      </el-table-column>
      <el-table-column
        prop="renewMoney"
        align="center"
        label="续费金额"
      >
      </el-table-column>
      <el-table-column
        prop="renewDurationDis"
        align="center"
        label="续费时长"
        :formatter="renewDurationeForm"
      >
      </el-table-column>
      <el-table-column
        prop="renewDate"
        align="center"
        label="续费时间"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.renewDate!==null">{{moment(scope.row.renewDate).format('YYYY-MM-DD')}}</div>
          <div v-if="scope.row.renewDate!==null">{{moment(scope.row.renewDate).format('HH:mm:ss')}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="sendTypeDis"
        align="center"
        label="赠送类型"
        :formatter="sendTypeFrom"
      >
      </el-table-column>
      <el-table-column
        prop="sendContent"
        align="center"
        label="赠送内容"
        :formatter="sendContentForm"
      >
      </el-table-column>
      <el-table-column
        prop="expireTime"
        align="center"
        label="到期时间"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.expireTime!==null">{{moment(scope.row.expireTime).format('YYYY-MM-DD')}}</div>
          <div v-if="scope.row.expireTime!==null">{{moment(scope.row.expireTime).format('HH:mm:ss')}}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="operatorName"
        align="center"
        label="操作员"
      >
      </el-table-column>
      <el-table-column
        prop="renewDesc"
        align="center"
        label="说明"
        show-overflow-tooltip
      >
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
import { renewShopListRequest } from '@/api/system/shopList.js'

export default {
  name: 'shopRenenwList',
  data () {
    return {
      currentPage3: 1,
      pagination_b: true,
      formTable: [{
        shopId: '',
        shopName: '',
        accountName: '',
        mobile: '',
        renewType: '',
        renewMoney: '',
        renewDuration: '',
        sendType: '',
        sendContent: '',
        expireTime: '',
        sysId: '',
        renewDesc: ''

      }],
      totalRows: null,
      pageRows: '',
      currentPage: '',
      pageCount: null,
      sendData: this.sendShowReData
    }
  },
  props: ['sendShowReData'],
  mounted () {
    console.log('进入续费详情')
    console.log(this.sendData)
    this.search()
  },
  methods: {
    // currnentPage 改变时会触发
    handleCurrentChange () {
      this.search()
    },
    search () {
      renewShopListRequest(this.sendData).then((res) => {
        console.log(res)
        const { error, content } = res
        if (error === 0) {
          this.formTable = res.content.dataList
          console.log(this.formTable)
          this.currentPage = content.page.currentPage
          this.pageRows = content.page.pageRows
          this.pageCount = content.page.pageCount
          this.totalRows = content.page.totalRows
          this.firstPage = content.page.firstPage
          this.lastPage = content.page.lastPage
          this.nextPage = content.page.nextPage
        } else {
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('操作失败')
      })
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
    renewTypeForm (row, col) {
      switch (row.renewType) {
        case 1: row.renewType = '续费'
          break
        case 2: row.renewType = '试用'
          break
        case 3: row.renewType = '赠送'
          break
        case 4: row.renewType = '退款'
          break
        case 5: row.renewType = '初次付费'
          break
        case 6: row.renewType = '试用转付费'
          break
      }
      return row.renewType
    },
    renewDurationeForm (row, col) {
      if (row !== null) {
        let rows = row.renewDuration.split(',')
        console.log('切割')
        console.log(rows)
        row.renewDurationDis = rows[0] + '年' + rows[1] + '月'
        console.log(row.renewDurationDis)
      }
      return row.renewDurationDis
    },
    // 赠送类型：0无，1时间，2功能
    sendTypeFrom (row, col) {
      switch (row.sendType) {
        case 0: row.sendTypeDis = '无'
          break
        case 1: row.sendTypeDis = '时间'
          break
        case 2: row.sendTypeDis = '功能'
          break
      }
      return row.sendTypeDis
    },
    sendContentForm (row, col) {
      if (row !== null && row.sendType === 1) {
        let rows = row.renewDuration.split(',')
        console.log('切割')
        console.log(rows)
        row.renewDurationDis = rows[0] + '年' + rows[1] + '月'
        console.log(row.renewDurationDis)
      }
      return row.renewDurationDis
    }
  }
}
</script>

<style lang="scss" scoped>
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
</style>
