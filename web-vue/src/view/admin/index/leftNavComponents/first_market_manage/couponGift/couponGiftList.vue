<template>
  <div class="content">
    <!-- 筛选条件 -->
    <div class="main">
      <div class="leftarea">
        <span>发券活动名称</span>
        <el-input
          :placeholder="请输入发券活动名称"
          size="small"
        ></el-input>
        <el-button
          type="primary"
          size="small"
          @click="onSubmit"
        >查询
        </el-button>
        <el-button
          type="primary"
          size="small"
          class="barginBtn"
          @click="addActive"
        >发券
        </el-button>
      </div>
    </div>
    <!-- 表格数据 -->
    <div class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
      </el-table>
      <!-- 分页组件 -->
      <!-- <pagination
        :page-params.sync="pageParams"
        @pagination="handleClick"
      /> -->
    </div>
  </div>
</template>

<script>
// import { couponGiftList } from '@/api/admin/marketManage/couponGift.js'
// import pagination from '@/components/admin/pagination/pagination'
export default {
  // components: {
  //   pagination
  // },
  data () {
    return {
      tableData: []
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
  .select_info {
    display: flex;
    margin: 10px 0px;
    .leftarea {
      display: flex;
      margin-right: 30px;
    }
    .rightarea {
      display: flex;
      :nth-of-type(1) {
        margin-right: 5px;
      }
    }
    .midarea {
      display: flex;
      margin-right: 30px;
      :nth-of-type(2) {
        margin: 0 0 0 10px;
      }
    }
    span {
      white-space: nowrap;
      height: 32px;
      line-height: 32px;
    }
    /deep/ .el-input__inner {
      width: 200px;
      display: inline-block;
    }
  }
  .footer_right {
    padding: 20px 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    span {
      display: block;
      height: 32px;
      line-height: 32px;
    }
  }
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
.opt {
  display: flex;
  justify-content: space-around;
  > .item {
    font-size: 22px;
    color: #66b1ff;
    cursor: pointer;
  }
}
</style>
