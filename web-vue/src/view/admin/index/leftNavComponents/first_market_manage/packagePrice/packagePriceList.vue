<!---
*****
*** 打包一口价列表页面
*****
--->
<template>
  <div class="container">
    <!-- tab切换 -->
    <div class="main">
      <el-tabs
        v-model="param.activityStatus"
        @tab-click="initDataList"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        >
          <div
            class="filter-item"
            v-if="tableListView"
          >
            <div class="act-name">
              <span>活动名称：</span>
              <el-input
                size="small"
                placeholder="请输入活动名称"
                class="input-width"
                v-model="param.name"
                clearable
              ></el-input>
            </div>
            <div class="act-time">
              <span>活动时间：</span>
              <el-date-picker
                type="datetime"
                placeholder="请选择开始日期"
                size="small"
                class="input-width"
                v-model="param.startTime"
                clearable
                default-time="00:00:00"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
              <span>至</span>
              <el-date-picker
                type="datetime"
                placeholder="请选择结束时间"
                size="small"
                class="input-width"
                v-model="param.endTime"
                clearable
                default-time="23:59:59"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              ></el-date-picker>
            </div>
            <el-button
              type="primary"
              size="small"
              class="btn"
              @click="initDataList"
            >筛选</el-button>
            <el-button
              size="small"
              @click="reset"
            >重置</el-button>
          </div>
          <el-button
            type="primary"
            size="small"
            class="pack-button"
            v-if="tableListView"
            @click="addActivity"
          >添加打包一口价</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 添加 / 编辑 -->
    <packagePriceAdd
      isEdite="isEdite"
      v-if="tableListView === false"
    />

    <!-- :editId="editId" -->
    <!-- @addPackageSubmit="addPackageSubmit" -->

    <!-- 表格内容 -->
    <div
      class="table_list"
      v-if="tableListView"
    >
      <el-table
        header-row-class-name="tableClss"
        :data="tableInfo"
        style="width:100%"
        border
      >
        <el-table-column
          prop="packageName"
          label="活动名称"
          align="center"
        ></el-table-column>
        <el-table-column
          label="活动时间"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}
            <br />至<br />
            {{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          label="活动信息"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.activityInfo}}<br />
            {{scope.row.activityGroup1}}
            <span v-show="scope.row.goodsGroup2 === 1"><br />{{scope.row.activityGroup2}}</span>
            <span v-show="scope.row.goodsGroup3 === 1"><br />{{scope.row.activityGroup3}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="purchasedNum"
          label="已购商品数量"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="orderNum"
          label="订单数量"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="userNum"
          label="下单用户数"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="statusText"
          label="活动状态"
          align="center"
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <!-- 编辑 -->
              <el-tooltip
                :content="$t('seckill.edit')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                  @click="gotoEdit(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <!-- 分享 -->
              <el-tooltip
                :content="$t('seckill.share')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                  @click="shareHandle(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <!-- 停用 -->
              <el-tooltip
                :content="$t('seckill.stop')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                  @click="disableActivity(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <!-- 启用 -->
              <el-tooltip
                :content="$t('seckill.start')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="enableActivity(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <!-- 查看活动订单 -->
              <el-tooltip
                content="查看活动订单"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-tickets"
                  @click="gotoOrder(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <!-- 活动明细 -->
              <el-tooltip
                content="活动明细"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-s-unfold"
                  @click="gotoDetail(scope.row.id, scope.row)"
                ></span>
              </el-tooltip>
              <!-- 删除 -->
              <el-tooltip
                :content="$t('seckill.delete')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                  @click="deleteActivity(scope.row.id, scope.row)"
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
    <shareDialog
      :imgPath="shareImgPath"
      :pagePath="sharePagePath"
      :show="shareDialogShow"
      @close="shareDialogShow=false"
    />
  </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import status from '@/components/admin/marketManage/status/status'
import { packagePriceList, shareActivity, disableActivity, enableActivity, deleteActivity } from '@/api/admin/marketManage/packagePrice.js'
import shareDialog from '@/components/admin/shareDialog'
import packagePriceAdd from './packagePriceAdd'
export default {
  components: {
    statusTab,
    status,
    pagination,
    shareDialog,
    packagePriceAdd
  },
  data () {
    return {
      tabInfo: [
        {
          title: '全部满包邮活动',
          name: '0'
        },
        {
          title: '进行中',
          name: '1'
        },
        {
          title: '未开始',
          name: '2'
        },
        {
          title: '已过期',
          name: '3'
        },
        {
          title: '已停用',
          name: '4'
        }
      ],
      activityName: '打包一口价',
      pageParams: {},
      tableListView: true, // tab显示隐藏
      shareImgPath: '',
      sharePagePath: '',
      shareDialogShow: false,
      dateRange: [],
      param: {
        name: '',
        startTime: '',
        endTime: '',
        activityStatus: '1'
      },
      tableInfo: [],
      pageInfo: {},
      isEdit: true // 编辑状态
    }
  },

  watch: {
    'param.activityStatus' (n, o) {
      this.initDataList()
    }
  },
  mounted () {
    this.onSubmit()
    this.initDataList()
  },

  methods: {
    onSubmit () {
      this.pageInfo.currentPage = 1
    },
    reset () {
      this.param.name = ''
      this.dateRange = ''
      this.param.startTime = ''
      this.param.endTime = ''
    },
    initDataList () {
      packagePriceList(Object.assign(this.param, this.pageParams)).then(res => {
        console.log(res, 'res-list')
        if (res.error === 0) {
          this.handData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },
    handData (data) {
      console.log(data, 'res-data')
      data.map((item, index) => {
        item.num = item.goodsNumber1
        item.activityGroup1 = `${item.groupName1}:${item.goodsNumber1}件`
        if (item.goodsGroup2 === 1) {
          item.activityGroup2 = `${item.groupName2}:${item.goodsNumber2}件`
          item.num += item.goodsNumber2
        }
        if (item.goodsGroup3 === 1) {
          item.activityGroup3 = `${item.groupName3}:${item.goodsNumber3}件`
          item.num += item.goodsNumber3
        }
        item.activityInfo = `${item.totalMoney}元${item.num}件`
        item.statusText = this.getActStatusString(item.activityStatus)
      })
      this.tableInfo = data
    },
    shareHandle (id) {
      shareActivity(id).then(res => {
        if (res.error === 0) {
          this.shareImgPath = res.content.imgUrl
          this.sharePagePath = res.content.pageUrl
          this.shareDialogShow = true
        }
      })
    },
    enableActivity (id) {
      this.$alert('确定要启用吗？', '提示', {
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableActivity(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success('启用成功')
            this.initDataList()
          } else {
            this.$message.warning('启用失败')
          }
        })
      })
    },
    disableActivity (id) {
      this.$alert('确定要停用吗？', '提示', {
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        disableActivity(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success('停用成功')
            this.initDataList()
          } else {
            this.$message.warning('停用失败')
          }
        })
      })
    },
    deleteActivity (id) {
      this.$alert('确定要删除吗？', '提示', {
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteActivity(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success('删除成功')
            this.initDataList()
          } else {
            this.$message.warning('删除失败')
          }
        })
      })
    },

    addActivity () {
      this.isEdit = false
      this.showTabAddGroup('添加打包一口价活动')
    },
    gotoEdit (id, row) {
      this.editId = id
      this.isEdit = true
      this.showTabAddGroup('编辑打包一口价活动')
    },
    // 查看砍价订单
    gotoOrder (id, valScope) {
      // console.log(valId, valScope)
      this.$router.push({
        path: `/admin/home/main/packsale/order/${id}`,
        query: {
          id: id
        }
      })
    },
    showTabAddGroup (title) {
      if (this.param.activityStatus === '6' || this.tabInfo.length > 5) {
        console.log(12324325)
        this.closeTabAddGroup()
      }
      this.tabInfo.push({
        title: title,
        name: '5'
      })
      this.param.activityStatus = '6'
      this.tableListView = false
    },
    closeTabAddGroup () {
      // 新增标签
      if (this.param.activityStatus === '6') {
        return
      }
      // 不是新增
      if (this.tableInfo.length > 5) {
        this.tableListView = true
        this.tableInfo.pop({
          title: '编辑打包一口价活动',
          name: '6'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
    },
    gotoDetail (id) {
      this.$router.push(`/admin/home/main/packsale/detail/${id}`)
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    padding: 15px;
    background: #fff;
    .filter-item {
      display: flex;
      margin-top: 10px;
      .input-width {
        width: 185px;
      }
      .act-time {
        margin: 0 0 0 60px;
      }
      .btn {
        margin: 0 10px 0 30px;
      }
    }
    .pack-button {
      margin: 20px 0 0 0;
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
}
</style>
