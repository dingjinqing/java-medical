<template>
  <div class="formStatisticsHome">
      <div class="formStatisticsTop">
      <el-form :inline="true" :model="form" label-width="90px"  class="form-inline" size="small">
        <el-form-item label="直播间标题">
          <el-input v-model="form.name" size="small"></el-input>
        </el-form-item>
        <el-form-item label="开播时间">
            <el-date-picker
            v-model="form.beginStartTime"
            type="datetime"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间">
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            size="small"
            v-model="form.beginEndTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="直播状态">
          <el-select v-model="form.liveStatus" size="small" placeholder="直播状态">
          <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="主播昵称">
          <el-input v-model="form.anchorName" size="small"></el-input>
        </el-form-item>
        <el-form-item label="结束时间">
            <el-date-picker
            v-model="form.finishStartTime"
            type="datetime"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间">
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="form.finishEndTime"
            type="datetime"
            size="small"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="23:59:59"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="" style="margin-left: 22px;">
          <el-button type="primary" @click="getList" size="small">查询</el-button>
        </el-form-item>
      </el-form>
      </div>
    <div class="formStatisticsBottom">
      <div class="formStatisticsBottom">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column
            prop="name"
            label="直播间标题"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="anchorName"
            label="主播昵称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="startTime"
            label="开播时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="endTime"
            label="结束时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="liveStatusSign"
            label="直播状态"
            :formatter="liveStatusMatter"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="观看人数"
            align="center"
          >
          -
          </el-table-column>
          <el-table-column
            prop="goodsListNum"
            label="活动商品"
            align="center"
          >
          <template slot-scope="scope">
            <el-link type="primary" :underline="false" @click="showGoods(scope.row.id)">{{scope.row.goodsListNum}}</el-link>
          </template>
          </el-table-column>
          <el-table-column
            prop="addCartNum"
            label="活动加购"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="orderNum"
            label="活动订单"
            align="center"
          >
          </el-table-column>
        </el-table>
        <div class="footer">
          <span>{{$t('formStatisticsHome.currentPage')}}{{currentPage}}/{{totalPage}}，{{$t('formStatisticsHome.generalRecord')}}{{totalRows}}{{$t('formStatisticsHome.strip')}}</span>
          <el-pagination
            @current-change="handleDetailCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totalRows"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!--商品弹窗-->
    <el-dialog title="活动商品" :visible.sync="dialogTableVisible" width="900px">
      <liveGoods v-if="dialogTableVisible" :liveId="liveId"/>
    </el-dialog>
  </div>
</template>
<script>
import liveGoods from './liveGoods'
import { getLiveList } from '@/api/admin/marketManage/live'
export default {
  components: {
    liveGoods
  },
  data () {
    return {
      currentPage: 1, // 当前页
      totalPage: 1, // 总页数
      totalRows: 0, // 总条数
      tableData: [],
      goodList: [],
      dialogTableVisible: false,
      liveId: 0,
      form: {
        liveStatus: -1
      },
      statusOptions: [
        {
          value: -1,
          label: '全部'
        },
        {
          value: 101,
          label: '直播中'
        }, {
          value: 102,
          label: '未开始'
        }, {
          value: 103,
          label: '已结束'
        }, {
          value: 104,
          label: '禁播'
        }, {
          value: 105,
          label: '暂停中'
        }, {
          value: 106,
          label: '异常'
        }, {
          value: 107,
          label: '已过期'
        }
      ]
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 数据初始化查询
    this.getList()
  },
  methods: {
    getList () {
      let params = {
        name: this.form.name,
        beginStartTime: this.form.beginStartTime,
        beginEndTime: this.form.beginEndTime,
        liveStatus: this.form.liveStatus,
        anchorName: this.form.anchorName,
        finishStartTime: this.form.finishStartTime,
        finishEndTime: this.form.finishEndTime,
        currentPage: this.currentPage,
        pageRows: 20
      }
      getLiveList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.pageList.dataList
          this.totalPage = res.content.pageList.page.pageCount
          this.totalRows = res.content.pageList.page.totalRows
        } else {
          this.$message.error(res.message)
        }
      })
    },
    liveStatusMatter (row, column) {
      console.log('column')
      console.log(column)
      let sign = '未知'
      switch (row.liveStatus) {
        case 101:
          sign = '直播中'
          break
        case 102:
          console.log('进来')
          sign = '未开始'
          break
        case 103:
          sign = '已结束'
          break
        case 104:
          sign = '禁播'
          break
        case 105:
          sign = '暂停中'
          break
        case 106:
          sign = '异常'
          break
        case 107:
          sign = '已过期'
          break
      }
      row.liveStatusSign = sign
      return row.liveStatusSign
    },
    handelToCopy () {
      console.log('submit!')
    },
    handleDetailCurrentChange () {
      this.getList()
    },
    showGoods (data) {
      this.liveId = data
      this.dialogTableVisible = true
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.formStatisticsHome {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .formStatisticsTop {
    display: flex;
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 30px;
    .form-inline{
      width: 1200px;
    }
    .tipsDiv {
      font-size: 12px;
      position: relative;
      cursor: pointer;
      height: 30px;
      line-height: 30px;
      color: #999;
      padding: 0 12px;
      &:hover .tipsHidden {
        display: block;
      }
      img {
        margin-left: 5px;
        position: relative;
        top: 2px;
      }
      .tipsHidden {
        display: none;
        position: absolute;
        top: 32px;
        left: 0;
        width: 300px;
        padding: 10px;
        background: #fff;
        box-shadow: 0px 0px 10px #f0f0f0;
        z-index: 100;
        .tipsTop {
          line-height: 30px;
          margin-bottom: 35px;
          p {
            color: #666;
            font-size: 12px;
            border-bottom: 1px solid #efedee;
          }
        }
        .tipsBottom {
          display: flex;
          justify-content: center;
        }
      }
    }
  }
  .formStatisticsBottom {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 0;
    .formStatisticsTop {
      .list {
        span {
          display: inline-block;
          padding: 0 5px;
        }
        /deep/ .el-input {
          width: 150px;
        }
      }
      /deep/ .el-button {
        margin-left: 20px;
      }
    }
    .formStatisticsBottom {
      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
      }
      .operation {
        display: flex;
        flex-wrap: wrap;
        // justify-content: space-around;
        .iconSpn {
          display: block;
          font-size: 22px;
          color: #5a8bff;
          cursor: pointer;
          margin-right: 3px;
        }
      }
      .footer {
        display: flex;
        justify-content: flex-end;
        align-items: center;
        margin-top: 10px;
      }
    }
  }
}
.copyContainer {
  display: flex;
  justify-content: center;
  .copy {
    cursor: pointer;
    color: #5a8bff;
  }
  /deep/ .el-input {
    width: 200px;
    margin: 0 10px;
  }
}
.copyDiv {
  align-items: center;
  margin-top: 20px;
  span {
    white-space: nowrap;
  }
}
.formStatisData {
  /deep/ .el-input {
    width: 182px !important;
  }
}
</style>
