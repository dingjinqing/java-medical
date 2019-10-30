<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="tabClickHandler"
        :lazy="true"
      >
        <el-tab-pane
          v-for="(item, index) in tabInfo"
          :key="index"
          :label="item.title"
          :name="item.name"
        ></el-tab-pane>
      </el-tabs>
      <div class="wrapper">
        <el-button
          type="primary"
          size="medium"
          v-if="tabSwitch !== '5'"
          @click="addSeckill"
        >{{$t('seckill.addSeckill')}}</el-button>
      </div>
    </div>
    <div
      class="table_list"
      v-if="tabSwitch !== '5'"
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
          :label="$t('seckill.activityName')"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="goodsName"
          :label="$t('seckill.goodsName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="validity"
          :label="$t('seckill.validDate')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('seckill.activityStatus')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="saleNum"
          :label="$t('seckill.saleNum')"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="limitAmount"
          :label="$t('seckill.limitAmount')"
          align="center"
        >

        </el-table-column>
        <el-table-column
          :label="$t('seckill.option')"
          align="center"
          width="200"
          style="margin: 0 auto;"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('seckill.edit')"
                placement="top"
                v-if="scope.row.status == 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-edit-outline"
                  @click="editHandler(scope.row.skId, scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.share')"
                placement="top"
                v-if="scope.row.status == 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-share"
                  @click="shareHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.stop')"
                placement="top"
                v-if="scope.row.status == 1"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-close"
                  @click="stopHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.start')"
                placement="top"
                v-if="scope.row.status == 0"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-circle-check"
                  @click="startHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.order')"
                placement="top"
                v-if="scope.row.status == 0"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-tickets"
                  @click="seckillOrderHanlder(scope.row.skId, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.detail')"
                placement="top"
                v-if="scope.row.status == 0"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-user-solid"
                  @click="seckillDetailHanlder(scope.row.skId, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.user')"
                placement="top"
                v-if="scope.row.status == 0"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-s-unfold"
                  @click="seckillUserHanlder(scope.row.skId, scope.row.name)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.delete')"
                placement="top"
                v-if="scope.row.status == 0"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-delete"
                  @click="deleteHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('seckill.effect')"
                placement="top"
              >
                <span
                  style="font-size: 22px;"
                  class="el-icon-s-data"
                  @click="seckillEffectHandler(scope.row.skId)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleClick"
      />
    </div>
    <addSeckill
      :editData="editData"
      :isEdite="isEdite"
      :editId="editId"
      @addSeckillSubmit="addSeckillSubmit"
      v-if="tabSwitch==='5'"
    />

    <!-- 分享dislog -->
    <el-dialog
      :title="$t('seckill.shareTitle')"
      :visible.sync="shareDialog"
      width="350px"
      center
      :close-on-click-modal="false"
    >
      <div style="width: 100%; text-align: center; margin-bottom: 15px; border-bottom: 1px solid #ccc;">
        <div>
          <img
            :src="shareImg"
            alt=""
            style="width:160px;height:160px;"
          >
        </div>
        <div style="margin: 20px; 0">
          <a
            :href="shareImg"
            download
            style="color: #999;text-decoration: none;"
          >{{ $t('seckill.downLoad') }}</a>
        </div>
      </div>
      <div>
        <el-input v-model="sharePath">
          <el-button
            slot="append"
            v-clipboard:copy="sharePath"
            v-clipboard:success="copyHandler"
          >{{ $t('seckill.copy') }}</el-button>
        </el-input>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="shareDialog = false">{{ $t('seckill.cancel') }}</el-button>
        <el-button
          type="primary"
          @click="shareDialog = false"
        >{{ $t('seckill.sure') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
// 引入组件
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination'
import addSeckill from './seckillAdd.vue'
import { seckillList, deleteSeckillList, shareSeckillList, getSeckillList, updateSeckillList } from '@/api/admin/marketManage/seckill.js'
export default {

  components: {
    statusTab,
    pagination,
    addSeckill
  },
  data () {
    return {
      tabSwitch: '1',
      tabInfo: this.$t('seckill.tabInfo'),
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {},
      shareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',
      editData: {}, // 编辑数据
      editId: '', // 编辑的活动id
      isEdite: true // 编辑状态
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('seckill.tabInfo')
      // this.activityTypeText = this.$t('groupBuy.grouponType')
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.handleClick()
  },
  methods: {
    // 秒杀列表
    handleClick () {
      this.requestParams.state = [this.tabSwitch]
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      seckillList(this.requestParams).then((res) => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map((item, index) => {
            item.validity = `${item.startTime}` + `至` + `${item.endTime}`
            item.statusText = this.getActStatusString(item.status, item.startTime, item.endTime)
          })
        } else {
          this.$message.error(res.message)
        }
      })
    },

    // 添加秒杀活动
    addSeckill () {
      this.isEdite = false
      this.tabInfo.push({
        title: this.$t('seckill.addSeckill'),
        name: '5'
      })
      this.tabSwitch = '5'
    },

    // tab栏切换
    tabClickHandler () {
      this.requestParams.state = this.tabSwitch
      if (this.tabSwitch !== '5') {
        this.tabInfo = this.tabInfo.slice(0, 5)
      }
      this.handleClick()
    },

    // 编辑秒杀活动
    editHandler (id, row) {
      console.log(row)
      this.isEdite = true
      this.tabInfo.push({
        title: this.$t('seckill.editSeckill'),
        name: '5'
      })
      this.tabSwitch = '5'
      this.editId = id
      getSeckillList({ skId: id }).then((res) => {
        if (res.error === 0) {
          this.editData = res.content
        }
      })
    },

    // 保存
    addSeckillSubmit () {
      this.tabInfo = this.tabInfo.slice(0, 5)
      this.tabSwitch = '1'
      this.handleClick()
    },

    // 删除
    deleteHandler (id) {
      this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteSeckillList({ skId: id }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '删除成功!' })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消删除' })
      })
    },

    // 分享
    shareHandler (id) {
      this.shareDialog = true
      shareSeckillList(id).then((res) => {
        if (res.error === 0) {
          this.shareImg = res.content.imageUrl
          this.sharePath = res.content.pagePath
        }
      })
    },

    // 复制
    copyHandler (e) {
      this.$message.success({ message: '复制成功!' })
    },

    // 停用
    stopHandler (id) {
      this.$confirm('此操作将停用活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateSeckillList({
          skId: id,
          status: 0
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '停用成功!' })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消停用' })
      })
    },

    // 启用
    startHandler (id) {
      this.$confirm('此操作将启用活动, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateSeckillList({
          skId: id,
          status: 1
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success({ message: '启用成功!' })
            this.handleClick()
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消删除' })
      })
    },

    // 查看秒杀订单
    seckillOrderHanlder (id, name) {
      this.$router.push({ name: 'seckill_order_view', query: { id: id, name: name } })
    },

    // 查看用户明细
    seckillDetailHanlder (id, name) {
      this.$router.push({ name: 'seckill_detail_view', query: { id: id, name: name } })
    },

    // 查看秒杀用户
    seckillUserHanlder (id, name) {
      this.$router.push({ name: 'seckill_user_view', query: { id: id, name: name } })
    },

    // 查看活动数据效果
    seckillEffectHandler (id) {
      this.$router.push({ name: 'seckill_effect_view', query: { id: id } })
    }

  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
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
  padding: 10px 20px 10px 20px;
}
.balanceDialo .el-dialog__body {
  padding-bottom: 0 !important;
}
.balanceDialo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.setUpDialog .el-dialog__body {
  padding-top: 10px !important;
}
.add_coupon {
  float: left;
  margin-left: 65%;
}
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
    float: left;
  }
}
.search_content {
  width: 220px;
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
