<template>
  <div class="distributorListContent">
    <el-form
      :model="param"
      label-width="120px"
      label-position="right"
    >
      <div>
        <el-form-item
          label="申请人昵称："
          class="item"
        >
          <el-input
            v-model="param.username"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="手机号："
          class="item"
        >
          <el-input
            v-model="param.mobile"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="真实姓名："
          class="item"
        >
          <el-input
            v-model="param.realName"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="提现单号："
          class="item"
        >
          <el-input
            v-model="param.orderSn"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
      </div>
      <div>
        <el-form-item
          label="申请时间："
          class="item"
        >
          <el-date-picker
            v-model="param.startCreateTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            style="width: 190px;"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="param.endCreateTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            size="small"
            style="width: 190px;"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="操作时间："
          class="item"
        >
          <el-date-picker
            v-model="param.startOptTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
            style="width: 190px;"
          >
          </el-date-picker>
          至
          <el-date-picker
            v-model="param.endOptTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            size="small"
            style="width: 190px;"
          >
          </el-date-picker>
        </el-form-item>
      </div>
      <div>
        <el-form-item
          label="提现金额："
          class="item"
        >
          <el-input
            v-model="param.startWithdrawCash"
            placeholder="请填写内容"
            size="small"
            style="width: 190px;"
          ></el-input>
          <span>至</span>
          <el-input
            v-model="param.endWithdrawCash"
            placeholder="请填写内容"
            size="small"
            style="width: 190px;"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="处理状态："
          class="item"
        >
          <el-select
            v-model="param.status"
            placeholder="请选择"
            size="small"
            class="inputWidth"
            clearable
          >
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          class="item"
          label-width="20px"
        >
          <el-button
            type="primary"
            size="small"
            @click="initData()"
          >筛选</el-button>
          <el-button
            size="small"
            @click="resetHandler()"
          >重置筛选</el-button>
          <!-- <el-button
            size="small"
            @click="exportDataList()"
          >导出</el-button> -->
        </el-form-item>
      </div>
    </el-form>

    <div class="list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <template slot="empty">
          <tableEmpty />
        </template>
        <el-table-column
          prop="username"
          label="申请人"
          align="center"
        >
          <template slot-scope="scope">
            <span
              class="opt"
              @click="userNameHandler(scope.row.userId)"
            >{{scope.row.username}}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="realName"
          label="真实姓名"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="申请时间"
          align="center"
          width="100px"
        >
        </el-table-column>

        <el-table-column
          prop="orderSn"
          label="提现单号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="提现金额"
          align="center"
        >
          <template slot-scope="scope">
            <span>￥{{scope.row.withdrawCash}}</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="checkTime"
          label="操作时间"
          align="center"
          width="100px"
        >
        </el-table-column>

        <el-table-column
          prop="statusName"
          label="处理状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="驳回原因"
          align="center"
        >
          <template slot-scope="scope">
            <span>{{scope.row.moreFlag ? scope.row.refuseDescMore : scope.row.refuseDesc}}</span>
            <span
              class="opt"
              v-if="scope.row.moreFlag && scope.row.moreType"
              @click="showMoreHandler(scope.row.id)"
            >更多</span>
            <span
              class="opt"
              v-if="scope.row.moreFlag && !scope.row.moreType"
              @click="showMoreHandler(scope.row.id)"
            >收起</span>
          </template>
        </el-table-column>

        <el-table-column
          prop="desc"
          label="处理备注"
          align="center"
        >
        </el-table-column>

        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <p @click="withdrawDetailHandler(scope.row.id)">查看详情</p>
            </div>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="initData()"
    />

    <!-- 导出数据弹窗 -->
    <exportDialog
      :tuneUp="exportDialog"
      :param="this.param"
      :totalRows="totalRows"
      :type="5"
      @export="exportHandler"
    />
  </div>
</template>

<script>
import { withdrawList } from '@/api/admin/marketManage/distribution.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    exportDialog: () => import('./moneyExportDialog')
  },
  props: {
    username: {
      type: String,
      default: () => ''
    },
    status: {
      type: Number,
      default: () => null
    }
  },
  data () {
    return {
      tableData: [],
      pageParams: {},
      param: {
        username: '',
        mobile: '',
        realName: '',
        orderSn: '',
        startCreateTime: '',
        endCreateTime: '',
        startOptTime: '',
        endOptTime: '',
        startWithdrawCash: '',
        endWithdrawCash: '',
        minCash: '',
        maxCash: '',
        status: ''
      },
      // 提现处理状态
      statusList: [{
        value: 1,
        label: '待审核'
      },
      {
        value: 2,
        label: '已驳回申请'
      },
      {
        value: 3,
        label: '已审核, 待出账'
      },
      {
        value: 4,
        label: '出账成功'
      },
      {
        value: 5,
        label: '出账失败'
      }],

      exportDialog: false, // 导出数据弹窗
      totalRows: 0 // 筛选个数
    }
  },
  mounted () {
    this.param.username = this.username
    this.param.status = this.status
    this.initData()
  },
  methods: {
    // 提现审核列表
    initData () {
      return new Promise((resolve, reject) => {
        var obj = this.param
        obj.currentPage = this.pageParams.currentPage
        obj.pageRows = this.pageParams.pageRows
        withdrawList(obj).then(res => {
          if (res.error === 0) {
            var data = res.content.dataList || []
            if (data && data.length > 0) {
              data.forEach(item => {
                item.moreFlag = false
                item.moreType = true // 类型
                item.refuseDescMore = ''
                // 驳回内容
                if (item.refuseDesc && item.refuseDesc.length > 50) {
                  item.moreFlag = true
                  item.refuseDescMore = item.refuseDesc.substring(0, 49) + '...'
                }
                // 审核状态
                this.statusList.forEach(val => {
                  if (item.status === val.value) {
                    item.statusName = val.label
                  }
                })
              })
            }

            this.tableData = data
            this.pageParams = res.content.page
            resolve(this.pageParams)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 重置筛选
    resetHandler () {
      this.param = {
        username: '',
        mobile: '',
        realName: '',
        orderSn: '',
        startTime: '',
        endTime: '',
        minCash: '',
        maxCash: '',
        status: ''
      }
    },

    // 导出数据
    exportDataList () {
      this.initData().then(() => {
        this.totalRows = this.pageParams.totalRows
        this.exportDialog = !this.exportDialog
      })
    },

    // 导出数据弹窗回调函数
    exportHandler (data) {
      // 搜索条件
      var obj = {}
      for (var i in data) {
        if (i === 'startNum' || i === 'endNum') {
          obj[i] = data[i]
        } else if (data[i]) {
          obj[i] = data[i]
        }
      }
      // withdrawExport(obj).then(res => {
      //   let fileName = localStorage.getItem('V-content-disposition')
      //   fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : '返利提现列表导出.xlsx'
      //   download(res, decodeURIComponent(fileName))
      // })
    },

    // 驳回内容切换
    showMoreHandler (id) {
      var currentData = this.tableData.find(item => { return item.id === id })
      if (currentData.moreType) {
        currentData.refuseDescMore = currentData.refuseDesc
      } else {
        currentData.refuseDescMore = currentData.refuseDesc.substring(0, 49) + '...'
      }
      currentData.moreType = !currentData.moreType
    },

    // 用户昵称跳转
    userNameHandler (id) {
      this.$router.push({
        path: '/admin/home/main/membershipInformation',
        query: {
          userId: id
        }
      })
    },

    // 查看提现详情
    withdrawDetailHandler (id) {
      this.$router.push({
        path: '/admin/home/main/distribution/withdraw/detail',
        query: {
          id: id
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.distributorListContent {
  padding: 8px;
  padding-bottom: 38px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.labelClass {
  width: 180px !important;
}
.timeInput {
  width: 180px;
}
.list {
  margin-top: 10px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.opt {
  text-align: center;
  color: #5a8bff;
  cursor: pointer;
}
.item {
  display: inline-block;
}
.inputWidth {
  width: 170px;
}
</style>
