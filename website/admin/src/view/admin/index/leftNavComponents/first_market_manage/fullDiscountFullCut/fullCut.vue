<!--
***  满折满减列表页面
-->
<template>
  <div class="fullCut">
    <div class="mainContent">
      <!-- tab切换内容部分 -->
      <statusTab
        v-model="params.state"
        :activityName="activityName"
        :standard="true"
      />

      <div class="addActivity">
        <el-button
          type="primary"
          size="small"
          @click="addActivity()"
        >{{$t('fullCuti18n.addActivity')}}</el-button>
      </div>
    </div>

    <!-- 表格内容部分 -->
    <div class="table_list">
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="actName"
          :label="$t('fullCuti18n.eventName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="type"
          :label="$t('fullCuti18n.actType')"
          align="center"
          :formatter="formatStatus"
        >
        </el-table-column>
        <el-table-column
          prop="strategyPriority"
          :label="$t('fullCuti18n.priority')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          :label="$t('fullCuti18n.prules')"
          align="center"
        >
          <template slot-scope="{ row }">
            <div
              v-for="(item, index) in row.condition"
              :key="index"
            >
              <span v-if="row.type == 2 && item.fullMoney && item.reduceMoney">{{$t('fullCuti18n.full')}}{{item.fullMoney}}{{$t('fullCuti18n.yuan')}}{{$t('fullCuti18n.less')}}{{item.reduceMoney}}{{$t('fullCuti18n.yuan')}}</span>
              <span v-else-if="row.type == 2 && item.amount && item.reduceMoney">{{$t('fullCuti18n.full')}}{{item.amount}}{{$t('fullCuti18n.piece')}}{{$t('fullCuti18n.less')}}{{item.reduceMoney}}{{$t('fullCuti18n.yuan')}}</span>
              <span v-else-if="row.type == 1 && item.fullMoney && item.reduceMoney">{{$t('fullCuti18n.perFull')}}{{item.fullMoney}}{{$t('fullCuti18n.yuan')}}{{$t('fullCuti18n.less')}}{{item.reduceMoney}}{{$t('fullCuti18n.yuan')}}</span>
              <span v-else-if="row.type == 1 && item.amount && item.reduceMoney">{{$t('fullCuti18n.perFull')}}{{item.amount}}{{$t('fullCuti18n.piece')}}{{$t('fullCuti18n.less')}}{{item.reduceMoney}}{{$t('fullCuti18n.yuan')}}</span>
              <span v-else-if="row.type == 3 && item.fullMoney && item.discount">{{$t('fullCuti18n.full')}}{{item.fullMoney}}{{$t('fullCuti18n.yuan')}}{{$t('fullCuti18n.hit')}}{{item.discount}}{{$t('fullCuti18n.fold')}}</span>
              <span v-else-if="row.type == 3 && item.amount && item.discount">{{$t('fullCuti18n.full')}}{{item.amount}}{{$t('fullCuti18n.piece')}}{{$t('fullCuti18n.hit')}}{{item.discount}}{{$t('fullCuti18n.fold')}}</span>
              <span v-else-if="row.type == 4">{{$t('fullCuti18n.no')}}{{item.amount}}{{$t('fullCuti18n.piece')}},{{$t('fullCuti18n.hit')}}{{item.discount}}{{$t('fullCuti18n.fold')}}</span>
              <span v-else>{{row.type}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          :label="$t('fullCuti18n.validity')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br />{{$t('fullCuti18n.to')}}<br />{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="statusText"
          :label="$t('fullCuti18n.status')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="operate"
          :label="$t('fullCuti18n.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('fullCuti18n.edit')"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2"
              >
                <span
                  class="iconfont iconbianji"
                  @click="editHandle(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('fullCuti18n.enable')"
                placement="top"
                v-if="scope.row.currentState === 4"
              >
                <span
                  class="iconfont iconqiyong"
                  @click="openHandle(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('fullCuti18n.disable')"
                placement="top"
                v-if="scope.row.currentState === 1 || scope.row.currentState === 2"
              >
                <span
                  class="iconfont icontingyong"
                  @click="closeHandle(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('fullCuti18n.delete')"
                placement="top"
                v-if="scope.row.currentState === 3 || scope.row.currentState === 4"
              >
                <span
                  class="iconfont iconshanchu2"
                  @click="deleteHandle(scope.row.id)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('fullCuti18n.viewOrder')"
                placement="top"
              >
                <span
                  class="iconfont icondingdan"
                  @click="goOrderList(scope.row)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('fullCuti18n.actEffect')"
                placement="top"
              >
                <span
                  class="iconfont iconfankuitongji"
                  style="font-size:20px;"
                  @click="goStatistics(scope.row)"
                ></span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="params"
          @pagination="tableDataSearch"
        />
      </div>

    </div>
  </div>
</template>

<script>
import { fullCutTableDataSearchApi, updateFullCut, deleteActivity } from '@/api/admin/marketManage/fullDiscountFullCut'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: { statusTab, pagination },
  data () {
    return {
      activityName: this.$t('fullCuti18n.fdfr'),
      params: {
        'state': 1,
        'currentPage': 1,
        'pageRows': 20
      },
      tableData: []
    }
  },
  watch: {
    'params.state' (n, o) {
      this.tableDataSearch()
    }
  },
  mounted () {
    this.tableDataSearch()
    if (this.$route.params.calenderAdd) {
      this.addActivity()
    } else if (this.$route.params.calenderEdit) {
      this.editHandle(this.$route.params.id)
    }
  },
  methods: {
    tableDataSearch () {
      fullCutTableDataSearchApi(this.params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.params = Object.assign(res.content.page, this.params)
          this.tableData = res.content.dataList
          this.tableData.map((item, index) => {
            item.statusText = this.getActStatusString(item.currentState)
          })
        }
      }).catch(err => console.log(err))
    },
    formatStatus (row, column) {
      let type = ''
      switch (row.type) {
        case 1:
          type = this.$t('fullCuti18n.efr')
          break
        case 2:
          type = this.$t('fullCuti18n.fullMinus')
          break
        case 3:
          type = this.$t('fullCuti18n.fullFold')
          break
        case 4:
          type = this.$t('fullCuti18n.xth')
          break
      }
      return type
    },
    addActivity (id) {
      this.$router.push({
        name: 'fullCutActivity'
      })
    },
    editHandle (row) {
      console.log(row.id, 'activity id')
      this.$router.push({
        name: 'fullCutActivity',
        query: {
          id: row.id
        }
      })
    },
    openHandle (row) {
      let openParams = {
        'id': row.id,
        'status': 1
      }
      this.$confirm(this.$t('fullCuti18n.ayEnable'), {
        confirmButtonText: this.$t('fullCuti18n.confirm'),
        cancelButtonText: this.$t('fullCuti18n.cancel'),
        type: 'warning'
      }).then(() => {
        updateFullCut(openParams).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('fullCuti18n.activated') })
            this.tableDataSearch()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('fullCuti18n.deactivated') })
      })
    },
    closeHandle (row) {
      let stopParams = {
        'id': row.id,
        'status': 0
      }
      this.$confirm(this.$t('fullCuti18n.ayStop'), {
        confirmButtonText: this.$t('fullCuti18n.confirm'),
        cancelButtonText: this.$t('fullCuti18n.cancel'),
        type: 'warning'
      }).then(() => {
        updateFullCut(stopParams).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('fullCuti18n.disabled') })
            this.tableDataSearch()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('fullCuti18n.ccDeactivated') })
      })
    },
    deleteHandle (id) {
      this.$confirm(this.$t('fullCuti18n.ayDelete'), {
        confirmButtonText: this.$t('fullCuti18n.confirm'),
        cancelButtonText: this.$t('fullCuti18n.cancel'),
        type: 'warning'
      }).then(() => {
        deleteActivity({ id: id }).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: this.$t('fullCuti18n.deleted') })
            this.tableDataSearch()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('fullCuti18n.ccDeleted') })
      })
    },
    goStatistics (row) {
      console.log(row)
      let startTime = row.startTime || ''
      let endTime = row.endTime || ''
      let id = row.id
      this.$router.push({
        name: 'fullCutStatistics',
        query: {
          startTime,
          endTime,
          id
        }
      })
    },
    goOrderList (row) {
      this.$router.push({
        name: 'fullCutOrder',
        query: {
          id: row.id
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.fullCut {
  padding: 10px;
  min-width: 100%;
  height: 100%;
  font-size: 14px;
  .mainContent {
    padding: 15px;
    background: #fff;
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
    padding: 15px;
    margin-top: 10px;
    background-color: #fff;
    .opt {
      text-align: center;
      color: #5a8bff;
      span {
        cursor: pointer;
        font-size: 22px;
      }
    }
  }
}
</style>
