<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="tabSwitch"
        @tab-click="initDataList"
      >
        <el-tab-pane
          v-for="item in tabInfo"
          :key="item.name"
          :label="item.title"
          :name="item.name"
        >
          <el-button
            type="primary"
            size="medium"
            v-if="tableListView"
            @click="addPolicy"
          >添加返利策略</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 添加 / 编辑 -->
    <addPolicy
      :isEdite="isEdite"
      :editId="editId"
      v-if="tableListView===false"
      @addPolicySubmit="addPolicySubmit"
    />

    <div
      class="table_list"
      v-if="tableListView"
    >
      <el-table
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop=""
          label="返利策略名称"
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
          label="返利比列"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="优先级"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="创建时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
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
            <span @click="editHandler(scope.row.id)">编辑</span>
            <span @click="deleteHandler(scope.row.id)">删除</span>
            <span @click="stopHandler(scope.row.id)">停用</span>
            <span @click="startHandler(scope.row.id)">启用</span>
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
import pagination from '@/components/admin/pagination/pagination.vue'
import addPolicy from './policyAdd.vue'
export default {
  components: {
    pagination,
    addPolicy
  },
  data () {
    return {
      // tabs
      tableListView: true, // tab显示隐藏
      tabSwitch: '2',
      tabInfo: [{
        title: '全部策略',
        name: '1'
      }, {
        title: '进行中',
        name: '2'
      }, {
        title: '未开始',
        name: '3'
      }, {
        title: '已过期',
        name: '4'
      }, {
        title: '已停用',
        name: '5'
      }],
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {},
      editId: '', // 编辑的活动id
      isEdite: true // 编辑状态
    }
  },
  mounted () {
    // 初始化数据
    this.langDefault()
    this.initDataList()
  },
  methods: {
    // 返利列表
    initDataList () {

    },

    // 添加
    addPolicy () {
      this.isEdite = false
      this.showTabAddGroup('添加返利策略')
    },

    // 编辑
    editHandler (id) {
      this.editId = id
      this.isEdite = true
      this.showTabAddGroup('返利编辑策略')
    },

    // 保存
    addPolicySubmit () {

    },

    // 删除
    deleteHandler () {

    },

    // 启用
    startHandler () {

    },

    // 停用
    stopHandler () {

    },

    showTabAddGroup (title) {
      if (this.tabSwitch === '6' || this.tabInfo.length > 5) {
        this.closeTabAddGroup()
      }
      this.tabInfo.push({
        title: title,
        name: '6'
      })
      this.tabSwitch = '6'
      this.tableListView = false
    },

    closeTabAddGroup () {
      // 新增标签
      if (this.tabSwitch === '6') {
        return
      }
      // 不是新增
      if (this.tabInfo.length > 5) {
        this.tableListView = true
        this.tabInfo.pop({
          title: '编辑返利策略',
          name: '6'
        })
        console.log('closeTabAddGroup', this.tabInfo)
      }
      return this.tabInfo
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
  padding: 10px 0px 10px;
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
