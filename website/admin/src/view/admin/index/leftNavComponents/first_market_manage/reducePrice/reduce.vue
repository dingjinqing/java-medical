<template>
  <div class="content">

    <!-- tab -->
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="initDataList"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            type="primary"
            size="small"
            v-if="tableListView"
            @click="addReduce"
          >添加限时降价活动</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 添加 / 编辑 -->
    <addReduce
      :isEdite="isEdite"
      :editId="editId"
      :isGoing="isGoing"
      @addReduceSubmit="addReduceSubmit"
      v-if="tableListView===false"
    />

    <!-- 表格 -->
    <div
      class="table_list"
      v-if="tableListView"
    >
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          label="活动名称"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="first"
          label="优先级"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="goodsAmount"
          label="商品数量"
          align="center"
        ></el-table-column>
        <el-table-column
          label="有效期"
          align="center"
          width="160px"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="statusText"
          label="活动状态"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderAmount"
          label="付款订单数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="userAmount"
          label="付款用户数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="paymentTotalAmount"
          label="付款总金额"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="option"
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                content="编辑"
                placement="top"
                v-if="scope.row.currentState !== 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconbianji"
                  @click="editHandler(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="停用"
                placement="top"
                v-if="scope.row.currentState === 1"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icontingyong"
                  @click="stopHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="启用"
                placement="top"
                v-if="scope.row.currentState === 4"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconqiyong"
                  @click="startHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="订单明细"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont icondingdan"
                  @click="reduceOrderHanlder(scope.row.id, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
                v-if="scope.row.currentState !== 1"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconshanchu2"
                  @click="deleteHandler(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                content="活动效果数据"
                placement="top"
                v-if="scope.row.currentState !== 2"
              >
                <span
                  style="font-size: 22px;"
                  class="iconfont iconfankuitongji"
                  @click="reduceEffectHandler(scope.row.id, scope.row.startTime, scope.row.endTime)"
                ></span>
              </el-tooltip>
            </div>

          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      />
    </div>

  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
import addReduce from './addReduce.vue'
import batchReduce from './batchReduce.vue'
import batchMarkUpRate from './batchMarkUpRate.vue'
import { reducePriceList, deleteReducePrice, updateReducePrice } from '@/api/admin/marketManage/reducePrice.js'
export default {
  components: {
    pagination,
    addReduce,
    batchReduce,
    batchMarkUpRate
  },
  data () {
    return {
      loading: false,
      tabSwitch: '1',
      tableListView: true, // tab显示隐藏
      tabInfo: this.$t('reducePriceList.tabInfo'),
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {},
      editId: '', // 编辑的活动id
      isEdite: true, // 编辑状态
      isGoing: false // 是否是进行中
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('reducePriceList.tabInfo')
      this.initDataList()
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
    if (this.$route.params.calenderAdd) {
      this.addReduce()
    } else if (this.$route.params.calenderEdit) {
      this.editHandler(this.$route.params.id)
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.state = this.tabSwitch
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      this.closeTabAddGroup()
      reducePriceList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        // item.validDate = `${item.startTime}` + `<br>` + `${this.$t('marketCommon.to')}` + `<br>` + `${item.endTime}`
        item.statusText = this.getActStatusString(item.currentState)
      })
      this.tableData = data
    },

    // 添加
    addReduce () {
      this.isEdite = false
      this.isGoing = false
      this.showTabAddGroup('添加限时降价活动')
    },

    // 编辑
    editHandler (id, row) {
      if (row.currentState === 1) {
        this.isEdite = true
      }
      if (row.currentState === 2) {
        this.isEdite = false
      }
      this.editId = id
      this.isGoing = true
      this.showTabAddGroup('编辑限时降价活动')
    },

    showTabAddGroup (title) {
      if (this.tabSwitch === '6' || this.tabInfo.length > 5) {
        this.closeTabAddGroup()
      }
      this.tabInfo.push({
        title: title,
        name: '6'
      })
      this.tabSwitch = '6'
      this.tableListView = false
    },

    closeTabAddGroup () {
      // 新增标签
      if (this.tabSwitch === '6') {
        return
      }
      // 不是新增
      if (this.tabInfo.length > 5) {
        this.tableListView = true
        this.tabInfo.pop({
          title: '编辑限时降价活动',
          name: '6'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
    },

    // 保存
    addReduceSubmit () {
      this.tabSwitch = '1'
      this.initDataList()
    },

    // 删除
    deleteHandler (id) {
      this.$confirm(this.$t('seckill.deleteTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        deleteReducePrice({ id: id }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.deleteSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.deleteFail') })
      })
    },

    // 停用
    stopHandler (id) {
      this.$confirm(this.$t('seckill.stopTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        updateReducePrice({
          id: id,
          status: 0
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.stopSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.stopFail') })
      })
    },

    // 启用
    startHandler (id) {
      this.$confirm(this.$t('seckill.startTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        updateReducePrice({
          id: id,
          status: 1
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('seckill.startSuccess') })
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.startFail') })
      })
    },

    // 订单明细
    reduceOrderHanlder (id, actName) {
      this.$router.push({ name: 'reduce_price_order', query: { id: id, actName: actName } })
    },

    // 活动效果数据
    reduceEffectHandler (id, startTime, endTime) {
      this.$router.push({ name: 'reduce_price_effect', query: { id: id, startTime: startTime, endTime: endTime } })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 15px;
    .wrapper {
      .el-button {
        margin-left: 5px;
      }
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 15px;
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
