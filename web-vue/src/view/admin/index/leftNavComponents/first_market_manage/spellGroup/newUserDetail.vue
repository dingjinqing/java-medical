<!--
* 获取新用户明细页面
*
* @author: 赵鑫
-->
<template>
  <div>
    <wrapper>
      <section class="newuserDetailContent">
        <div>
          <span>手机号</span>
          <el-input
            class="inputWidth"
            size="small"
            placeholder="请输入手机号"
          ></el-input>
        </div>
        <div>
          <span>用户昵称</span>
          <el-input
            class="inputWidth"
            size="small"
            placeholder="请输入用户昵称"
          ></el-input>
        </div>
        <div>
          <span>邀请人</span>
          <el-input
            class="inputWidth"
            size="small"
            placeholder="请输入邀请人昵称"
          ></el-input>
        </div>
        <el-button
          class="btn"
          type="primary"
          size="small"
        >查询</el-button>
      </section>
    </wrapper>

    <wrapper>
      <div class="table_list">
        <el-table
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop=""
            label="活动中心"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="新用户ID"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="新用户昵称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="新用户手机号"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="注册时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="邀请人"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
    </wrapper>
  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'

export default {
  components: { wrapper },
  data () {
    return {

    }
  }
}

</script>
<style lang="scss" scoped>
.newuserDetailContent {
  display: flex;
  div {
    margin-right: 10px;
    span {
      margin-right: 10px;
    }
    .inputWidth {
      width: 150px;
    }
  }
  .btn {
    margin-left: 5px;
  }
}

/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
}
</style>
