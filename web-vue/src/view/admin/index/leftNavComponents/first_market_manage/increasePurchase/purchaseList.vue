<template>
  <div class="container">
    <div class="top">
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="true"
      />
      <div>
        <el-button
          type="primary"
          @click="jump2addPurchase"
          size="small"
        >
          {{$t('purchase.addactivity')}}
        </el-button>
      </div>
    </div>
    <div class="content">
      <!-- 查询条件 -->
      <el-form
        :inline="true"
        size="small"
      >
        <el-form-item :label="$t('purchase.activityName')+'：'">
          <el-input
            v-model="param.name"
            :placeholder="$t('purchase.inputactivityName')"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('purchase.activityTime')+'：'">
          <el-date-picker
            type="datetime"
            v-model="param.startTime"
            :placeholder="$t('purchase.startdate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 190px;"
            align="center"
          ></el-date-picker>
          {{$t('purchase.to')}}
          <el-date-picker
            type="datetime"
            v-model="param.endTime"
            :placeholder="$t('purchase.enddate')"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 190px;"
            align="center"
            default-time="23:59:59"
          ></el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('purchase.Priceincreaseconditions')+'：'">
          {{$t('purchase.full')}}
          <el-input
            v-model.number="param.fullPriceDown"
            style="width:100px"
          ></el-input>
          {{$t('purchase.rmbto')}}
          <el-input
            v-model.number="param.fullPriceUp"
            style="width:100px"
          ></el-input>
          {{$t('purchase.rmb')}}
        </el-form-item>
        <el-form-item :label="$t('purchase.redemptioncondition')+'：'">
          {{$t('purchase.full')}}
          <el-input
            v-model.number="param.purchasePriceDown"
            style="width:100px"
          ></el-input>
          {{$t('purchase.rmbto')}}
          <el-input
            v-model.number="param.purchasePriceUp"
            style="width:100px"
          ></el-input>
          {{$t('purchase.rmb')}}
          <el-button
            type="primary"
            @click="initDataList"
            style="margin-left:10px;"
          >{{$t('purchase.serach')}}</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格数据 -->
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
                {{$t('purchase.full')}}{{item.split('---')[0]}} {{$t('purchase.addPrice')}} {{item.split('---')[1]}}{{$t('purchase.redemption')}}<br>
              </li>
            </ul>
          </template>
        </el-table-column>
        <el-table-column
          prop="maxChangePurchase"
          :label="$t('purchase.singlemax')"
          align="center"
        >
          <template slot-scope="{row}">
            <div v-if="row.maxChangePurchase === 0">
              不限制
            </div>
            <div
              v-else
              v-text="row.maxChangePurchase"
            ></div>
          </template>
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
              <!-- 编辑 -->
              <el-tooltip
                :content="$t('purchase.edit')"
                placement="top"
                v-if="scope.row.category === 1 || scope.row.category === 2"
              >
                <span
                  class="el-icon-edit-outline iconSpn"
                  @click="jump2editPurchase(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 分享 -->
              <el-tooltip
                :content="$t('purchase.share')"
                placement="top"
                v-if="scope.row.category === 1 || scope.row.category === 2"
              >
                <span
                  class="el-icon-share iconSpn"
                  @click="shareActity(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 停用 -->
              <el-tooltip
                :content="$t('purchase.Disable')"
                placement="top"
                v-if="scope.row.category === 1 || scope.row.category === 2"
              >
                <span
                  class="el-icon-circle-close iconSpn"
                  @click="disableShare(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 启用 -->
              <el-tooltip
                :content="$t('purchase.Enable')"
                placement="top"
                v-if="scope.row.category === 4 && notExpired(scope.row)"
              >
                <span
                  class="el-icon-circle-check iconSpn"
                  @click="enableShare(scope.row.id)"
                ></span>
              </el-tooltip>
              <!-- 查看换购订单 -->
              <el-tooltip
                :content="$t('purchase.searchredemptionorder')"
                placement="top"
              >
                <span
                  @click="jumptoRedemptionList(scope.row.id)"
                  class="el-icon-s-cooperation iconSpn"
                ></span>
              </el-tooltip>
              <!-- 换购明细 -->
              <el-tooltip
                :content="$t('purchase.redemptiondetail')"
                placement="top"
              >
                <span
                  @click="jumptoRedemptionDetail(scope.row.id)"
                  class="el-icon-s-order iconSpn"
                ></span>
              </el-tooltip>
              <!-- 删除 -->
              <el-tooltip
                :content="$t('purchase.delete')"
                placement="top"
                v-if="scope.row.category === 3 || scope.row.category === 4"
              >
                <span
                  @click="deleteShare(scope.row.id)"
                  class="el-icon-delete iconSpn"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
    <!-- 分享弹窗组件 -->
    <shareDialog
      :show="showShareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="showShareDialog=false"
      style="width:400px;margin:0 auto;"
    />
  </div>
</template>
<script>
import { getList, changeActivity, updatePriority, share } from '@/api/admin/marketManage/increasePurchase.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import inputEdit from '@/components/admin/inputEdit'
import shareDialog from '@/components/admin/shareDialog'
export default {
  components: {
    pagination,
    statusTab,
    inputEdit,
    shareDialog
  },
  mounted () {
    this.langDefault()
  },
  created () {
    this.initDataList()
  },
  data () {
    return {
      activityName: this.$t('purchase.addPriceBuy'),
      tableData: [],
      pageParams: {},
      param: {
        status: 1,
        category: 0,
        name: '',
        // dateRange: [],
        startTime: '',
        endTime: '',
        fullPriceUp: '',
        fullPriceDown: '',
        purchasePriceUp: '',
        purchasePriceDown: '',
        // 分页
        currentPage: 0,
        pageRows: 20
      },
      showShareDialog: false,
      shareImg: '',
      sharePath: ''
    }
  },
  watch: {
    'param.status': function (val) {
      this.initDataList()
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
            this.initDataList()
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
    initDataList () {
      this.param.category = this.param.status
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      getList(this.param).then((res) => {
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
            this.initDataList()
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
            this.initDataList()
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
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除！')
      })
    },
    // 分享
    shareActity (shareId) {
      let obj = {
        'id': shareId,
        'status': 0
      }
      share(obj).then(res => {
        if (res.error === 0) {
          this.shareImg = res.content.imageUrl
          this.sharePath = res.content.pagePath
          this.showShareDialog = !this.showShareDialog
        }
      })
    },
    notExpired (row) {
      let time = new Date(row.timestamp)
      let endTime = new Date(row.endTime)
      if (time < endTime) {
        return true
      } else {
        return false
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
  font-size: 14px;
}
.top {
  padding: 15px;
  background: #fff;
}
.content {
  margin-top: 10px;
  padding: 15px;
  background: #fff;
  .el-form-item {
    width: 500px;
  }
  .iconSpn {
    font-size: 22px;
    color: #5a8bff;
    text-decoration: none;
    cursor: pointer;
  }
}
</style>
