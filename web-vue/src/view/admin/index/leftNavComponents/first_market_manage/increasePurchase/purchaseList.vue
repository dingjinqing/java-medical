<template>
  <div>
    <wrapper>
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="true"
      />
      <el-row>
        <el-col :span="5">
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.activityName')">
              <el-input
                v-model="param.name"
                :placeholder="$t('purchase.inputactivityName')"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="4"
          :offset=1
        >
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.activityTime')">
              <el-date-picker
                v-model="param.dateRange"
                type="datetimerange"
                :range-separator="$t('purchase.to')"
                :start-placeholder="$t('purchase.startdate')"
                :end-placeholder="$t('purchase.enddate')"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.Priceincreaseconditions')">
              <el-input
                v-model.number="param.fullPriceDown"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="3">
          <el-form label-width="50px">
            <el-form-item :label="$t('purchase.rmbto')">
              <el-input
                v-model.number="param.fullPriceUp"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="1">
          <el-form label-width="30px">
            <el-form-item :label="$t('purchase.rmb')">
            </el-form-item>
          </el-form>
        </el-col>
        <el-col
          :span="4"
          :offset=1
        >
          <el-form label-width="100px">
            <el-form-item :label="$t('purchase.redemptioncondition')">
              <el-input
                v-model.number="param.purchasePriceDown"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="3">
          <el-form label-width="50px">
            <el-form-item :label="$t('purchase.rmbto')">
              <el-input
                v-model.number="param.purchasePriceUp"
                placeholder="0"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="1">
          <el-form label-width="30px">
            <el-form-item :label="$t('purchase.rmb')">
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="2">
          <el-button
            type="primary"
            @click="initDateList"
          >{{$t('purchase.serach')}}</el-button>
        </el-col>
        <el-col :span="4">
          <el-button
            type="primary"
            style="float:right;"
            @click="jump2addPurchase"
          >
            {{$t('purchase.addactivity')}}
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
            :label="$t('purchase.activityName')"
            align="center"
          >

          </el-table-column>
          <el-table-column
            :label="$t('purchase.activityTime')"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.startTime}}<br>{{$t('purchase.to')}}<br>{{scope.row.endTime}}
            </template>
          </el-table-column>
          <el-table-column
            prop="level"
            :label="$t('purchase.activityprioty')"
            align="center"
          >
            <template slot-scope="scope">
              <inputEdit
                v-model="scope.row.level"
                @update="updatePriority(scope.row.id, scope.row.level)"
              />
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('purchase.activityinfo')"
            align="center"
          >
            <template slot-scope="scope">
              <ul>
                <li
                  v-for="(item,index) in scope.row.purchaseInfo"
                  :key="index"
                >
                  <el-form :inline="true">
                    <el-form-item>
                      {{$t('purchase.full')}}{{item.replace('---','加价')}}{{$t('purchase.redemption')}}<br>
                    </el-form-item>
                  </el-form>
                </li>
              </ul>
            </template>
          </el-table-column>
          <el-table-column
            prop="maxChangePurchase"
            :label="$t('purchase.singlemax')"
            align="center"
          >

          </el-table-column>
          <el-table-column
            prop="resaleQuantity"
            :label="$t('purchase.alreadyredemption')"
            align="center"
          >

          </el-table-column>
          <el-table-column
            :label="$t('purchase.opration')"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  :content="$t('purchase.edit')"
                  placement="top"
                  v-if="scope.row.status === 0"
                >
                  <span
                    class="el-icon-edit-outline iconSpn"
                    @click="jump2editPurchase(scope.row.id)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('purchase.Disable')"
                  placement="top"
                  v-if="scope.row.status === 0"
                >
                  <span
                    class="el-icon-circle-close iconSpn"
                    @click="disableShare(scope.row.id)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('purchase.Enable')"
                  placement="top"
                  v-if="scope.row.status === 1"
                >
                  <span
                    class="el-icon-circle-check iconSpn"
                    @click="enableShare(scope.row.id)"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('purchase.delete')"
                  placement="top"
                >
                  <span
                    @click="deleteShare(scope.row.id)"
                    class="el-icon-delete iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('purchase.searchredemptionorder')"
                  placement="top"
                >
                  <span
                    @click="jumptoRedemptionList(scope.row.id)"
                    class="el-icon-s-cooperation iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('purchase.redemptiondetail')"
                  placement="top"
                >
                  <span
                    @click="jumptoRedemptionDetail(scope.row.id)"
                    class="el-icon-s-cooperation iconSpn"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  :content="$t('purchase.share')"
                  placement="top"
                >
                  <span class="el-icon-share iconSpn"></span>
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
import { getList, changeActivity, updatePriority } from '@/api/admin/marketManage/increasePurchase.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import pagination from '@/components/admin/pagination/pagination.vue'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import inputEdit from '@/components/admin/inputEdit'
export default {
  components: {
    pagination,
    statusTab,
    wrapper,
    inputEdit
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
        status: 1,
        category: 0,
        name: '',
        dateRange: [],
        startTime: '',
        endTime: '',
        fullPriceUp: 0,
        fullPriceDown: 0,
        purchasePriceUp: 0,
        purchasePriceDown: 0,
        // 分页
        currentPage: 0,
        pageRows: 20
      }
    }
  },
  methods: {
    // 格式化活动信息
    formater (row, column) {

    },
    // 修改活动优先级
    updatePriority (id, level) {
      let obj = {
        id: id,
        priority: level
      }
      this.$confirm('此操作将修改该活动优先级, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updatePriority(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('修改成功！')
            this.initDateList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消修改！')
      })
    },
    // 添加活动跳转
    jump2addPurchase () {
      this.$router.push({
        name: 'add_increase_purchase',
        params: {
          flag: true
        }
      })
    },
    // 编辑活动页面跳转
    jump2editPurchase (id) {
      this.$router.push({
        name: 'edit_increase_purchase',
        params: {
          flag: true,
          id: id
        }
      })
    },
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
    // 查询数据列表
    initDateList () {
      this.param.category = this.param.status
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      this.param.startTime = this.param.dateRange[0]
      this.param.endTime = this.param.dateRange[1]
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
        // item.purchaseInfo[1] = this.stringReplace(item.purchaseInfo[1])
      })
      this.tableData = data.dataList
    },
    // 停用
    disableShare (shareId) {
      let obj = {
        'id': shareId,
        'status': 1
      }
      this.$confirm('此操作将停用该加价购活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeActivity(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('停用成功！')
            this.initDateList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消停用！')
      })
    },
    // 启用
    enableShare (shareId) {
      let obj = {
        'id': shareId,
        'status': 0
      }
      this.$confirm('此操作将启用该加价购活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeActivity(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('启用成功！')
            this.initDateList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消启用！')
      })
    },
    // 删除
    deleteShare (shareId) {
      let obj = {
        'id': shareId,
        'status': 2
      }
      this.$confirm('此操作将永久删除该加价购活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeActivity(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功！')
            this.initDateList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除！')
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
