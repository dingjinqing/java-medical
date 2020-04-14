<template>
  <div class="channel_main">
    <section class="filter_condition">
      <div class="channel_info">
        <div>
          <span>渠道页面分析：</span>
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
          <span>渠道页面分析：</span>
          <el-select
            v-model="param.sourceType"
            placeholder="请选择类型"
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
        <div>
          <span>注册时间：</span>
          <el-date-picker
            type="datetimerange"
            :range-separator="$t('marketCommon.to')"
            :start-placeholder="$t('marketCommon.startTime')"
            :end-placeholder="$t('marketCommon.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
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
          >筛选</el-button>
        </div>
      </div>
    </section>

    <section class="table">
      <div class="table_list">
        <el-table
          header-row-class-name="tableClss"
          style="width: 100%"
          border
        >
          <el-table-column
            prop="pageName"
            label="渠道页面名称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="源页面"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="源路径"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="源页面类型"
            align="center"
          >
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
            prop="delFlag"
            label="状态"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="操作"
            align="center"
          >
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </section>
  </div>
</template>

<script>
import { channelList } from '@/api/admin/marketManage/channelPage.js'

export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  mounted () {
    this.initDataList()
  },
  data () {
    return {
      param: {
        channelName: '',
        sourcePage: '',
        sourceType: -1
      },
      pageParams: {
        currentPage: 1,
        pageRows: 10
      },
      tableData: []
    }
  },
  methods: {
    screening () {
      this.initDataList()
    },
    addChannelPage () {

    },
    initDataList () {
      let obj = {
        'sourceType': 0,
        'startTime': '2019-03-26 15:06:10',
        'endTime': '2019-03-27 15:06:10',
        currentPage: 1,
        pageRows: 10
      }
      channelList(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    }
  }
}

</script>
<style lang="scss" scoped>
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

  .table {
    margin: 0 10px 10px;
    padding: 15px;
    background: #fff;
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
