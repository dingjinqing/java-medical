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
          <el-row
            :gutter="20"
            type="flex"
          >
            <el-col
              :span="8"
              style="height:30px;line-height: 37px;font-size: 14px;"
            >{{ $t('gift.activityName') }}</el-col>
            <el-input
              :span="3"
              clearable
              v-model="activityName"
              :placeholder="$t('gift.searchTip')"
            ></el-input>
            <el-col :span="3">
              <el-button
                type="primary"
                @click="initDataList"
              >{{ $t('gift.search') }}</el-button>
            </el-col>
            <el-col
              :span="4"
              :offset="15"
            >
              <el-button
                type="primary"
                size="medium"
                @click="gotoAddGift"
              >{{ $t('gift.addGift') }}</el-button>
            </el-col>

          </el-row>

        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 表格 -->
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          :label="$t('gift.activityName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="validity"
          :label="$t('gift.validDate')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="level"
          :label="$t('gift.level')"
          align="center"
        >
          <template slot-scope="scope">
            <inputEdit
              v-model="scope.row.level"
              @update="updateGiftLevel(scope.row.id, scope.row.level)"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="giftTimes"
          :label="$t('gift.giftTimes')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('gift.activityStatus')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          :label="$t('gift.option')"
          align="center"
        >
          <template slot-scope="scope">
            <el-row>
              <el-tooltip
                effect="dark"
                :content="$t('gift.edit')"
                placement="top"
                v-if="tabSwitch === '2'"
              >
                <i
                  @click="editGift(scope.row.id)"
                  class="el-icon-edit-outline"
                  style="color:#409EFF;fontSize:20px;cursor:pointer;"
                ></i>
              </el-tooltip>
              <el-tooltip
                effect="dark"
                :content="$t('gift.stop')"
                placement="top"
                v-if="tabSwitch === '2'"
              >
                <i
                  @click="disableGift(scope.row.id)"
                  class="el-icon-remove-outline"
                  style="color:#409EFF;fontSize:20px;cursor:pointer;"
                ></i>
              </el-tooltip>
              <el-tooltip
                effect="dark"
                :content="$t('gift.start')"
                placement="top"
                v-if="tabSwitch === '4'"
              >
                <i
                  @click="enableGift(scope.row.id)"
                  class="el-icon-circle-check"
                  style="color:#409EFF;fontSize:20px;cursor:pointer;"
                ></i>
              </el-tooltip>
              <el-tooltip
                effect="dark"
                :content="$t('gift.detail')"
                placement="top"
              >
                <i
                  @click="gotoGiftDetail(scope.row.id)"
                  class="el-icon-present"
                  style="color:#409EFF;fontSize:20px;cursor:pointer;"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('gift.delete')"
                placement="top"
              >
                <i
                  @click="deleteGift(scope.row.id)"
                  class="el-icon-delete"
                  style="color:#409EFF;fontSize:20px;cursor:pointer;"
                ></i>
              </el-tooltip>
            </el-row>
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
// 引入组件
import inputEdit from '@/components/admin/inputEdit'
import pagination from '@/components/admin/pagination/pagination.vue'
import { giftList, deleteGift, disableGift, enableGift, updateGiftLevel } from '@/api/admin/marketManage/gift'

export default {

  components: {
    inputEdit,
    pagination
  },
  data () {
    return {
      tabSwitch: '1',
      tabInfo: this.$t('gift.tabInfo'),
      activityName: '', // 活动名称
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {}
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('gift.tabInfo')
      this.initDataList()
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    // 赠品列表
    initDataList () {
      this.requestParams.name = this.activityName
      this.requestParams.status = Number(this.tabSwitch)
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      giftList(this.requestParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
          this.tableData.map((item, index) => {
            item.validity = `${item.startTime}` + `至` + `${item.endTime}`
            item.statusText = this.getActStatusString(item.status, item.startTime, item.endTime)
          })
        }
      })
    },

    // 修改活动优先级
    updateGiftLevel (id, level) {
      updateGiftLevel({ id, level }).then((res) => {
        this.$message.success({ message: '修改成功!' })
      })
    },

    // 删除
    deleteGift (id) {
      this.$confirm('此操作将永久删除该, 是否继续?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteGift(id).then((res) => {
          if (res.error === 0) {
            this.initDataList()
            this.$message.success({ message: '删除成功!' })
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消删除' })
      })
    },

    // 停用
    disableGift (id) {
      this.$confirm('此操作将停用活动, 是否继续?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        disableGift(id).then((res) => {
          if (res.error === 0) {
            this.initDataList()
            this.$message.success({ message: '停用成功!' })
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消停用' })
      })
    },

    // 启用
    enableGift (id) {
      this.$confirm('此操作将启用活动, 是否继续?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableGift(id).then((res) => {
          if (res.error === 0) {
            this.initDataList()
            this.$message.success({ message: '启用成功!' })
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消启用' })
      })
    },

    // 跳转创建赠品页
    gotoAddGift () {
      this.$router.push('/admin/home/main/gift/add')
    },
    // 跳转编辑活动页
    editGift (id) {
      this.$router.push(`/admin/home/main/gift/add/${id}`)
    },
    // 跳转赠送明细页
    gotoGiftDetail (id) {
      this.$router.push(`/admin/home/main/gift/giftDetail/${id}`)
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
