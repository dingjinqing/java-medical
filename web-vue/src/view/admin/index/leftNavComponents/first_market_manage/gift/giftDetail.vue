<!--
* 赠送明细
*
* @author 郑保乐
-->
<template>
  <div class="giftDetail">
    <wrapper>
      <el-row style="margin-bottom:-20px">
        <el-col :span="4">
          <el-form>
            <el-form-item
              label="手机号"
              label-width="60px"
            >
              <el-input
                v-model="param.mobile"
                placeholder="手机号"
                size="small"
                style="width:160px"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form>
            <el-form-item
              label="用户昵称"
              label-width="80px"
            >
              <el-input
                size="small"
                v-model="param.username"
                placeholder="微信昵称"
                style="width:160px"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="赠送时间">
              <el-date-picker
                size="small"
                v-model="dateRange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="2"
          :offset="5"
        >
          <el-button
            size="small"
            type="primary"
            @click="loadData"
            style="margin-top:3px"
          >查询</el-button>
        </el-col>
      </el-row>
    </wrapper>
    <wrapper>
      <el-row>
        <el-table
          class="version-manage-table"
          header-row-class-name="tableHeader"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="orderSn"
            label="订单号"
            align="center"
          > </el-table-column>
          <el-table-column
            prop="userId"
            label="用户ID"
            align="center"
          > </el-table-column>
          <el-table-column
            prop="username"
            label="昵称"
            align="center"
          > </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
            align="center"
          > </el-table-column>
          <el-table-column
            prop="createTime"
            label="赠送时间"
            align="center"
          > </el-table-column>
          <el-table-column
            prop="giftAmount"
            label="赠品件数"
            align="center"
          > </el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-col
          :offset="17"
          :span="10"
        >
          <el-pagination
            @size-change="loadData"
            @current-change="loadData"
            :current-page="param.currentPage"
            layout="total, sizes, prev, pager, next, jumper"
            :total="page.totalRows"
          >
          </el-pagination>
        </el-col>
      </el-row>
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import { getGiftGiftDetail } from '@/api/admin/marketManage/gift'
import { format } from '@/util/date'
export default {
  components: {
    wrapper
  },
  data () {
    return {
      dateRange: [],
      page: {
        totalRows: 0
      },
      param: {
        giftId: this.$route.params.id,
        mobile: '',
        username: '',
        startTime: '',
        endTime: '',
        currentPage: 1,
        pageRows: 20
      },
      tableData: []
    }
  },
  methods: {
    loadData () {
      const { dateRange } = this
      const param = {
        startTime: format(dateRange[0]),
        endTime: format(dateRange[1])
      }
      this.param = {
        ...this.param,
        ...param
      }
      getGiftGiftDetail(this.param).then(({ content }) => {
        const { dataList, page } = content
        this.tableData = dataList
        this.page = page
      })
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>
<style scoped>
</style>
