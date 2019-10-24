<template>
  <div class="content">
    <div class="main">
      <statusTab
        v-model="nav"
        :activityName="activityName"
        :standard="true"
      />
      <div class="wrapper">
        <el-button
          type="primary"
          size="medium"
        >添加秒杀活动</el-button>
      </div>
    </div>
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="活动名称"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop=""
          label="商品名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="有效期"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop=""
          label="活动状态"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop=""
          label="商品交易数量"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop=""
          label="单用户最大购买数量"
          align="center"
        >

        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="200"
        >
          <template slot-scope="scope">
            <div class="opt">
              <span>编辑</span>
              <span>分享</span>
              <span>停用</span>
              <span>查看秒杀订单</span>
              <span>获取新用户明细</span>
              <span>查看秒杀用户</span>
              <span>活动效果数据</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="handleClick"
      />
    </div>
  </div>
</template>
<script>
// 引入组件
import statusTab from '@/components/admin/status/statusTab'
import pagination from '@/components/admin/pagination/pagination'
import { seckillList } from '@/api/admin/marketManage/seckill.js'
export default {

  components: {
    statusTab,
    pagination
  },
  data () {
    return {
      nav: 0,
      activityName: '秒杀',
      tableData: [],
      pageParams: {}
    }
  },
  mounted () {
    // 初始化数据
    this.handleClick()
  },
  methods: {
    // 秒杀列表
    handleClick () {
      this.pageParams.nav = this.nav
      if (this.actName !== '') {
        this.pageParams.actName = this.actName
      }
      seckillList(this.pageParams).then((res) => {
        if (res.error === 0) {
          // this.handleData(res.content.dataList)
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
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
