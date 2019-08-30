<template>
  <div>
    <wrapper>
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="true"
      />
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form label-width="100px">
            <el-form-item label="活动名称">
              <el-input
                v-model="param.name"
                placeholder="请输入活动名称"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="4"
          :offset=2
        >
          <el-form label-width="100px">
            <el-form-item label="活动时间">
              <el-date-picker
                v-model="param.dateRange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item label="加价购条件:满">
              <el-input
                v-model="param.fullPriceDown"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="3">
          <el-form label-width="50px">
            <el-form-item label="元 至">
              <el-input
                v-model="param.fullPriceUp"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="4"
          :offset=1
        >
          <el-form label-width="100px">
            <el-form-item label="换购条件:满">
              <el-input
                v-model="param.purchasePriceDown"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="3">
          <el-form label-width="50px">
            <el-form-item label="元 至">
              <el-input
                v-model="param.purchasePriceUp"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="2"
          :offset=1
        >
          <el-button
            type="primary"
            @click="initDateList"
          >查询</el-button>
        </el-col>
        <el-col
          :span="4"
          :offset="1"
        >
          <el-button
            type="primary"
            style="float:right;"
          >
            添加加价购活动
          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-table
          class="version-manage-table"
          header-row-class-name="tableHeader"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="name"
            label="活动名称"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="活动时间"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
            </template>
          </el-table-column>
          <el-table-column
            prop="level"
            label="活动优先级"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="rewardType"
            label="活动信息"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="maxChangePurchase"
            label="单笔最大换购数量"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="resaleQuantity"
            label="已换购数量"
            align="center"
          >

          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  content="编辑"
                  placement="top"
                >
                  <span class="el-icon-edit-outline iconSpn"></span>
                </el-tooltip>
                <el-tooltip
                  content="停用"
                  placement="top"
                  v-if="status != '1'"
                >
                  <span
                    class="el-icon-circle-close iconSpn"
                    @click="shutdown(scope.row.id)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="启用"
                  placement="top"
                  v-if="status === '1'"
                >
                  <span
                    class="el-icon-circle-check iconSpn"
                    @click="open(scope.row.id)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="删除"
                  placement="top"
                >
                  <span
                    @click="del(scope.row.id)"
                    class="el-icon-delete iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="查看换购订单"
                  placement="top"
                >
                  <span
                    @click="jumptoRedemptionList(scope.row.id)"
                    class="el-icon-s-cooperation iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="换购明细"
                  placement="top"
                >
                  <span
                    @click="jumptoRedemptionDetail(scope.row.id)"
                    class="el-icon-s-cooperation iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  content="分享"
                  placement="top"
                >
                  <span
                    class="el-icon-share iconSpn"
                    @click="todoItem()"
                  ></span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <div>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDateList"
        />
      </div>
    </wrapper>
  </div>
</template>
<script>
import { getList, changeActivity } from '@/api/admin/marketManage/increasePurchase.js'
// import { getList, changeActivity, add, update, getDetail, share, orderList, detailList, orderExport, detailExport } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import statusTab from '@/components/admin/status/statusTab'
export default {
  components: {
    pagination,
    statusTab,
    wrapper
  },
  mounted () {
    this.langDefault()
  },
  watch: {
    'param.status' (n, o) {
      this.initDateList()
    }
  },
  created () {
    this.initDateList()
  },
  data () {
    return {
      activityName: '加价购',
      tableData: [],
      pageParams: {},
      param: {
        status: 0,
        name: '',
        dateRange: [],
        startTime: null,
        endTime: null,
        fullPriceUp: null,
        fullPriceDown: null,
        purchasePriceUp: null,
        purchasePriceDown: null,
        category: 0,
        // 分页
        currentPage: 0,
        pageRows: 20
      }
    }
  },
  methods: {
    // 换购订单跳转
    jumptoRedemptionList (purchaseId) {
      this.$router.push({
        name: 'purchase_redemption_order',
        params: {
          id: purchaseId,
          flag: true
        }
      })
    },
    // 换购明细跳转
    jumptoRedemptionDetail (purchaseId) {
      this.$router.push({
        name: 'purchase_redemption_detail',
        params: {
          id: purchaseId,
          flag: true
        }
      })
    },
    // 分模块查询数据列表
    initDateList () {
      this.param.category = this.param.status
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      console.log(this.param)
      getList(this.param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content)
          this.pageParams = res.content.page
          this.param.currentPage = res.content.page.currentPage
          this.param.pageRows = res.content.page.pageRows
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.dataList.map((item, index) => {
        console.log(item.purchaseInfo)
      })
      this.tableData = data.dataList
    },
    addActivity () { },
    // 停用分享有礼活动
    shutdown (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 1
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('停用成功！')
        }
      })
    },
    // 启用分享有礼活动
    open (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 0
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('启用成功！')
        }
      })
    },
    // 删除分享有礼活动
    del (shareId) {
      let obj = {
        'shareId': shareId,
        'status': 2
      }
      changeActivity(obj).then(res => {
        if (res.error === 0) {
          alert('删除成功！')
          this.seacherList()
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
