<template>
    <div class="content">
        <div class="main">
            <el-tabs
                    v-model="tabSwitch"
                    @tab-click="handleTabClick"
            >
                <el-tab-pane
                        v-for="(item) in tabInfo"
                        :key="item.name"
                        :label="item.title"
                        :name="item.name"
                >
                    <el-button
                            type="primary"
                            @click="addActivity"
                            class="barginBtn"
                    >{{$t('luckDraw.addActivity')}}
                    </el-button>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div class="table_list">
            <el-table
                    :data="tableData"
                    class="version-manage-table"
                    header-row-class-name="tableClss"
                    border
                    style="width: 100%"
            >
                <el-table-column
                        prop="lotteryName"
                        :label="$t('luckDraw.activityName')"
                        align="center"
                >
                </el-table-column>

                <el-table-column
                        prop="dateValidity"
                        :label="$t('luckDraw.dateValidity')"
                        align="center"
                >
                </el-table-column>

                <el-table-column
                        prop="statusText"
                        :label="$t('luckDraw.activityStatus')"
                        align="center"
                >
                </el-table-column>

                <el-table-column
                        prop="joinNumber"
                        :label="$t('luckDraw.joinNumber')"
                        align="center"
                >
                </el-table-column>

                <el-table-column
                        prop="awardNumber"
                        :label="$t('luckDraw.awardNumber')"
                        align="center"
                >
                </el-table-column>

                <el-table-column
                        :label="$t('luckDraw.option')"
                        align="center"
                >
                    <template slot-scope="scope">
                        <div class="opt">
                            <el-tooltip
                                    :content="$t('luckDraw.edit')"
                                    placement="top"
                            >
                <span
                        class="el-icon-edit-outline"
                        @click="editActivity(scope.row.id)"
                ></span>
                            </el-tooltip>
                            <el-tooltip
                                    :content="$t('luckDraw.share')"
                                    placement="top"
                            >
                                <span class="el-icon-share"></span>
                            </el-tooltip>
                            <el-tooltip
                                    :content="$t('luckDraw.disable')"
                                    placement="top"
                            >
                <span
                        class="el-icon-circle-close"
                        @click="changeStatus(scope.row.id)"
                        v-if="scope.row.status==1"
                > </span>
                            </el-tooltip>
                            <el-tooltip :content="$t('luckDraw.enabled')"
                                    placement="top"
                            >
                <span
                        class="el-icon-circle-check"
                        @click="changeStatus(scope.row.id)"
                        v-if="scope.row.status==0"
                > </span>
                            </el-tooltip>
                            <el-tooltip
                                    :content="$t('luckDraw.delete')"
                                    placement="top"
                            >
                <span
                        class="el-icon-delete"
                        @click="deleteLuckDraw(scope.row.id)"
                ></span>
                            </el-tooltip>
                            <el-tooltip
                                    :content="$t('luckDraw.grouponDetailList')"
                                    placement="top"
                            >
                <span
                        class="el-icon-tickets"
                        @click="luckDrawDetailList(scope.row.id)"
                ></span>
                            </el-tooltip>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <div class="footer">
                <pagination
                        :page-params.sync="pageParams"
                        @pagination="handlePagination"
                />
            </div>
        </div>
    </div>

</template>
<script>
import {
  getLotteryList,
  changeStatus,
  deleteLottery
} from '@/api/admin/marketManage/luckDraw.js'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: {
    pagination
  },
  data () {
    return {
      tableData: [],
      tabSwitch: '2',
      tabInfo: [],
      pageParams: {},
      tabIndex: 2
    }
  },
  watch: {
    lang () {
      this.tabInfo = this.$t('luckDraw.tabInfo')
    }
  },
  mounted () {
    // 初始化国际化
    this.langDefault()
    // 初始化页面数据
    this.initPageData()
  },
  methods: {
    initPageData () {
      let obj = {
        //  1全部，1进行中，2未开始，3已过期，4已停用
        state: this.tabSwitch,
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      getLotteryList(obj).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.resDataFilter(res.content.dataList)
        this.tableData = res.content.dataList
      }).catch(() => {
        this.$message.error('数据加载失败')
      })
    },
    resDataFilter (data) {
      data.map((item, index) => {
        item.dateValidity = item.startTime + '~' + item.endTime
        item.statusText = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
    },
    handlePagination () {
      this.initPageData()
    },
    handleTabClick (tab) {
      this.initPageData()
    },
    addActivity () {
      console.log('addActivity', 'lottery')
    },
    editActivity (id) {
      console.log('editActivity', 'lottery', id)
    },
    changeStatus (id) {
      console.log('changeStatus', id)
      this.$confirm(this.$t('luckDraw.changeStatusComment'), {
        confirmButtonText: this.$t('luckDraw.confirm'),
        cancelButtonText: this.$t('luckDraw.cancel'),
        type: 'warning'
      }).then(() => {
        changeStatus({id: id}).then(res => {
          console.log('res', res)
          if (res.error === 0) {
            this.$message.success(res.message)
            this.initPageData()
          } else {
            this.$message.warning(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('luckDraw.cancelMessage')
        })
      })
    },
    deleteLuckDraw (id) {
      console.log('deleteluckDraw', id)
      this.$confirm(this.$t('luckDraw.deleteLuckDrawComment'), {
        confirmButtonText: this.$t('luckDraw.confirm'),
        cancelButtonText: this.$t('luckDraw.cancel'),
        type: 'warning'
      }).then(() => {
        deleteLottery({id: id}).then(res => {
          console.log('res', res)
          if (res.error === 0) {
            this.$message.success(res.message)
            this.initPageData()
          } else {
            this.$message.warning(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: this.$t('luckDraw.cancelMessage')
        })
      })
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
                display: flex;
                justify-content: space-between;

                .rightContent {
                    .el-button {
                        margin-left: 5px;
                    }

                    span {
                        height: 30px;
                        line-height: 30px;
                    }

                    :nth-of-type(3) {
                        color: #999;
                    }
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
    .opt {
        text-align: left;
        color: #5a8bff;

        span {
            cursor: pointer;
        }
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
        }
    }
</style>
