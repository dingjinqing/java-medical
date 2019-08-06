<template>
  <div class="goodsSortContent">
    <el-row>
      <el-tabs
        v-model="activeName"
        @tab-click="tabsHandleClick"
      >
        <el-tab-pane
          label="分类列表"
          name="first"
        >
          <el-row>
            <el-button
              size="mini"
              type="primary"
              class="handleAddSort"
              @click="handleAddSort"
            >添加分类</el-button>
          </el-row>
          <el-row>
            <el-table
              :data="sortTableData"
              style="width: 100%;margin-bottom: 10px;"
              row-key="id"
              border
              :default-expand-all="isExpand"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
            >
              <el-table-column
                prop="sortName"
                label="分类名称"
                width="180"
              >
              </el-table-column>
              <el-table-column
                prop="sortImg"
                label="分类图标"
                width="180"
              >
              </el-table-column>
              <el-table-column
                prop="imgLink"
                label="分类连接"
              >
              </el-table-column>
              <el-table-column
                prop="first"
                label="分类优先级"
              >
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="添加时间"
              >
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)"
                  >编辑</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                  >删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-row>
        </el-tab-pane>
        <el-tab-pane
          label="推荐分类"
          name="second"
        >推荐分类</el-tab-pane>
      </el-tabs>
    </el-row>

  </div>
</template>
<script>
export default {
  data () {
    return {
      activeName: 'first',
      sortTableData: [
        { id: 1,
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          children: [
            {
              id: 11,
              date: '2016-05-01',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1519 弄'
            },
            {
              id: 12,
              date: '2016-05-01',
              name: '王小虎',
              address: '上海市普陀区金沙江路 1519 弄'
            }
          ]
        }
      ],
      isExpand: false
    }
  },
  methods: {
    tabsHandleClick (tab, event) {
      console.log(tab, event)
    },
    handleAddSort () {
      alert('添加分类')
    },
    handleEdit () {
      alert('编辑')
    },
    handleDelete () {
      alert('删除')
    }
  }
}
</script>
<style scoped>
.handleAddSort {
  margin-bottom: 10px;
}
</style>
