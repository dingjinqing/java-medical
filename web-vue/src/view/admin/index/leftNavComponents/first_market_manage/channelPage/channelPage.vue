<!--
** 渠道分析页面
**
** @author: zhaoxin
--->
<template>
  <div class="channel_main">
    <section class="filter_condition">
      <div class="channel_info">
        <div>
          <span>渠道页面名称：</span>
          <el-input
            v-model="param.channelName"
            placeholder="请输入页面名称"
            size="small"
            class="default_width"
            clearable
          ></el-input>
        </div>

        <div class="source_page">
          <span>来源页面：</span>
          <el-input
            v-model="param.sourcePage"
            placeholder="请输入来源页面"
            size="small"
            class="default_width"
            clearable
          ></el-input>
        </div>

        <div class="source_page">
          <span>来源页面类型：</span>
          <el-select
            v-model="param.sourceType"
            placeholder="请选择类型"
            @change="handleType"
            size="small"
            class="default_width"
          >
            <el-option
              :value="-1"
              label="请选择类型"
            ></el-option>
            <el-option
              :value="0"
              label="自定义页面"
            ></el-option>
            <el-option
              :value="1"
              label="商品详情"
            ></el-option>
          </el-select>
        </div>
      </div>

      <div class="channel_info info_bottom">
        <div style="display: flex">
          <span style="line-height:30px;width:100px;text-align:right">注册时间：</span>
          <div>
            <el-date-picker
              v-model="param.startTime"
              type="date"
              style="width:170px;"
              value-format="yyyy-MM-dd 00:00:00"
              :placeholder="$t('actionRecord.startTime')"
              size="small"
            >
            </el-date-picker>
            <span>至</span>
            <el-date-picker
              v-model="param.endTime"
              type="date"
              style="width:170px;"
              value-format="yyyy-MM-dd 23:59:59"
              :placeholder="$t('actionRecord.endTime')"
              size="small"
            >
            </el-date-picker>
          </div>
        </div>

        <div class="filter">
          <el-button
            size="small"
            type="primary"
            @click="screening"
          >筛选</el-button>
        </div>

        <div class="filter">
          <el-button
            size="small"
            type="primary"
            @click="addChannelPage"
          >添加渠道分析页面</el-button>
        </div>
      </div>
    </section>

    <section class="table">
      <div class="table_list">
        <el-table
          :data="tableData"
          header-row-class-name="tableClss"
          style="width: 100%"
          border
        >
          <el-table-column
            prop="channelName"
            label="渠道页面名称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="pageName"
            label="源页面"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.pageName}}
              <br />
              <span
                class="el-icon-view iconSpan"
                @click="jumpToDataPage(scope.row.id)"
              ></span>
            </template>
          </el-table-column>

          <el-table-column
            prop="share"
            label="源路径"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="源页面类型"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.sourceType === 0 ? '自定义页面' : '商品详情页'}}
            </template>
          </el-table-column>

          <el-table-column
            prop="newUserNum"
            label="拉新用户数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="orderNum"
            label="渠道用户订单数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="yesterdayAccessTimes"
            label="昨日访问次数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="yesterdayAccessNum"
            label="昨日访问人数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            label="添加时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="textStatus"
            label="状态"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="操作"
            align="center"
          >

            <template slot-scope="scope">
              <div class="opt">
                <!-- 分享 -->
                <el-tooltip
                  content="分享"
                  placement="top"
                >
                  <span
                    class="el-icon-share"
                    @click="shareActivity(scope.row.id)"
                  ></span>
                </el-tooltip>

                <!-- 停用 -->
                <el-tooltip
                  content="停用"
                  placement="top"
                  v-if="scope.row.delFlag === 0"
                >
                  <span
                    class="el-icon-circle-close"
                    @click="closeStatus(scope.row.id)"
                  > </span>
                </el-tooltip>

                <!-- 启用 -->
                <el-tooltip
                  content="启用"
                  placement="top"
                  v-if="scope.row.delFlag === 1"
                >
                  <span
                    class="el-icon-circle-check"
                    @click="openStatus(scope.row.id)"
                  > </span>
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
    </section>

    <!-- 分享弹窗 -->
    <shareDialog
      :show="showShareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="showShareDialog=false"
    />

    <addChannelPageVue
      @resultGoodsRow="choosingGoodsResult"
      @handleSelectTemplate="handleSelectTemplate"
      :tuneUpChooseGoods="turnUpChannelDialog"
      :singleElection="true"
    />
  </div>
</template>

<script>
import { channelList, stopChannelPage, openChannelPage, shareChannelPage } from '@/api/admin/marketManage/channelPage.js'
import shareDialog from '@/components/admin/shareDialog'

export default {
  components: {
    shareDialog,
    pagination: () => import('@/components/admin/pagination/pagination'),
    addChannelPageVue: () => import('./addChannelPage')
  },
  mounted () {
    this.initDataList()
  },
  data () {
    return {
      param: {
        startTime: '',
        endTime: '',
        channelName: '',
        sourcePage: '',
        sourceType: ''
      },
      pageParams: {},
      tableData: [],
      turnUpChannelDialog: false,
      showShareDialog: false, // 分享弹窗
      shareImg: '',
      sharePath: '',
      goodsIdList: []

    }
  },
  methods: {
    screening () {
      this.initDataList()
    },
    addChannelPage () {
      this.turnUpChannelDialog = !this.turnUpChannelDialog
    },
    shareActivity (id) {
      this.showShareDialog = !this.showShareDialog
      shareChannelPage(id).then(res => {
        if (res.error === 0) {
          this.shareImg = res.content.imageUrl
          this.sharePath = res.content.pagePath
        }
      })
    },
    // 废除
    closeStatus (id) {
      this.$confirm('确认要废除该渠道页吗?', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        stopChannelPage(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
          this.initDataList()
        })
      }).catch(() => {
        this.$message.info({ message: '已取消' })
      })
    },
    // 开启
    openStatus (id) {
      this.$confirm('确认要恢复该渠道页吗?', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        openChannelPage(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
          this.initDataList()
        })
      }).catch(() => {
        this.$message.info({ message: '已取消' })
      })
    },
    jumpToDataPage (id) {
      console.log(id)
      this.$router.push({
        name: 'channelStatistical',
        query: {
          id: id
        }
      })
    },
    initDataList () {
      let obj = {
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows
      }
      channelList(Object.assign({}, this.param, obj)).then(res => {
        if (res.error === 0) {
          this.pageParams = Object.assign({}, res.content.page)
          this.tableData = res.content.dataList
          this.handleDdta(res.content.dataList)
        }
      })
    },
    handleDdta (data) {
      console.log(data)
      data.map(item => {
        item.textStatus = this.getTextStatus(item.delFlag)
      })
    },
    getTextStatus (val) {
      switch (val) {
        case 0:
          val = '使用中'
          break
        case 1:
          val = '已废除'
          break
      }
      return val
    },
    handleType (type) {
      if (type === -1) {
        this.param.sourceType = ''
      }
    },
    // 商品详情页数据回传
    choosingGoodsResult (row, rows) {
      console.log(row, '-----row', rows)
      if (rows.refresh === true) {
        setTimeout(() => {
          this.initDataList()
        }, 20)
      }
    },
    // 自定义页面数据回传
    handleSelectTemplate (data, datas) {
      console.log(data, '-----data', datas)
      if (datas.refresh === true) {
        setTimeout(() => {
          this.initDataList()
        }, 20)
      }
    }
  }
}

</script>
<style lang="scss" scoped>
* {
  font-size: 14px;
}
.channel_main {
  .filter_condition {
    margin: 10px;
    padding: 20px 30px;
    background: #fff;
    .channel_info {
      display: flex;
      .default_width {
        width: 170px;
      }
      .source_page {
        margin-left: 50px;
      }
    }
    .info_bottom {
      margin-top: 20px;
      .filter {
        margin-left: 30px;
      }
    }
  }

  .opt {
    text-align: center;
    span {
      font-size: 22px;
      color: #5a8bff;
      cursor: pointer;
    }
  }

  .table {
    margin: 0 10px 10px;
    padding: 15px;
    background: #fff;
    .iconSpan {
      font-size: 20px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
}
</style>
