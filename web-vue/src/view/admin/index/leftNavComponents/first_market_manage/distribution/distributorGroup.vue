<template>
  <div class="content">
    <div class='main_content'>
      <span>分销员分组：</span>
      <el-input
        v-model="groupName"
        class="groupName"
        size="medium"
        placeholder="请输入内容"
      ></el-input>
      <el-button
        @click="groupList"
        size="small"
        type="primary"
      >筛选</el-button>
      <span class="showCfg">分组是否展示在小程序端：</span>
      <el-radio
        class="radio"
        v-model="radio"
        label="1"
      >展示</el-radio>
      <el-radio
        class="radio"
        v-model="radio"
        label="2"
      >不展示</el-radio>
      <el-button
        size="small"
        type="primary"
        plain
      >保存</el-button>
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
          prop="groupName"
          label="分组名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="distributorAmount"
          label="包含分销员数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="isDefault"
          label="是否为默认分组"
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
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="groupList"
    />
  </div>
</template>

<script>
import { distributionGroup } from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      tableData: [],
      radio: '1',
      currentPage: null,
      pageParams: {},
      groupName: null
    }
  },
  created () {
    this.groupList()
  },
  mounted () {
    this.groupList()
  },
  methods: {
    // 当前页发生变化
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    groupList () {
      this.pageParams.groupName = this.groupName
      console.log(this.pageParams)
      distributionGroup(this.pageParams).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
          this.pageParams = res.content.page
        }
      })
    },
    handleData (data) {
      data.map((item, index) => {
        if (item.isDefault === 1) {
          item.isDefault = '是 取消默认'
        } else {
          item.isDefault = '否 设为默认'
        }
      })
      this.tableData = data
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
  .main_content {
    position: flex;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
}

.groupName {
  width: 12%;
  margin-right: 10px;
}
span {
  height: 40px;
  line-height: 40px;
}
.showCfg {
  margin-left: 30px;
}
.radio {
  line-height: 40px;
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
</style>
