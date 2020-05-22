<template>
  <div class="container">
    <section>
      <el-button
        size="mini"
        type="primary"
        class="handleAddSort"
        @click="handleAddSort"
      >添加分类</el-button>
      <span>商品来源:</span>
      <el-select
        style="width:160px"
        size="small"
        v-model="value"
        placeholder="请选择商品来源"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </section>
    <section>
      <el-table
        :data="tableData"
        style="width: 100%;margin-bottom: 10px;"
        row-key="sortId"
        border
        :default-expand-all="true"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <!-- 分类名称 -->
        <el-table-column
          align="center"
          prop="sortName"
          label="分类名称"
        >
        </el-table-column>
        <!-- 分类图标 -->
        <el-table-column
          align="center"
          prop="sortImg"
          label="分类图标"
          width="180"
        >
          <template slot-scope="scope">
            <el-image
              :src="scope.row.sortImg"
              min-width="70"
              height="70"
            >
              <div
                slot="error"
                class="image-slot"
              >
                <i class="el-icon-picture-outline"></i>
                <span>无图片</span>
              </div>
            </el-image>
          </template>

        </el-table-column>
        <!-- 分类链接 -->
        <el-table-column
          align="center"
          prop="imgLink"
          label="分类连接"
        >
        </el-table-column>
        <!-- 分类优先级 -->
        <el-table-column
          align="center"
          prop="first"
          label="分类优先级"
        >
        </el-table-column>
        <!-- 添加时间 -->
        <el-table-column
          align="center"
          prop="createTime"
          label="添加时间"
        >
        </el-table-column>
        <!-- 操作 -->
        <el-table-column
          label="操作"
          align="center"
        >
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
    </section>
  </div>
</template>
<script>
import { getSortList, deleteSort } from '@/api/admin/goodsManage/goodsSortManagement/businessSortManagement'
export default {
  // 组件名
  name: '',
  // data 数据
  data () {
    return {
      options: [{
        label: `自营分类`,
        value: `自营分类`
      }, {
        label: `非自营分类`,
        value: `非自营分类`
      }],
      value: ``,
      tableData: []
    }
  },
  // 生命周期钩子
  created () {
    // 获取数据
    this.fetchSortList()
  },
  mounted () {

  },
  // 方法
  methods: {
    // 添加分类
    handleAddSort () {
      this.$router.push({
        name: `addSort`
      })
    },
    // 获取分类列表函数
    fetchSortList () {
      let params = {
        'type': 0,
        'parentId': 0
      }
      getSortList(params).then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(content)
          this.tableData = content
        }
      }).catch(err => console.log(err))
    },
    // 删除分类
    handleDelete (val, row) {
      this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = { 'sortId': row.sortId }
        console.log(params)
        deleteSort(params).then(res => {
          const { error } = res
          if (error === 0) {
            this.fetchSortList()
            this.$message({
              showClose: true,
              message: '删除分类成功',
              type: 'success'
            })
          }
        }).catch(err => console.log(err))
      }).catch(err => console.log(err))
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
