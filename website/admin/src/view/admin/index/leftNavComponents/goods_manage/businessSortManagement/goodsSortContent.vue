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
          <!-- 分类列表 -->
          <el-row>
            <el-table
              :data="sortTableData"
              style="width: 100%;margin-bottom: 10px;"
              row-key="sortId"
              border
              :default-expand-all="true"
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
            >

              <el-table-column
                align="center"
                prop="sortName"
                label="分类名称"
                width="180"
              >
              </el-table-column>
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
              <el-table-column
                align="center"
                prop="imgLink"
                label="分类连接"
              >
              </el-table-column>
              <el-table-column
                align="center"
                prop="first"
                label="分类优先级"
              >
              </el-table-column>
              <el-table-column
                align="center"
                prop="createTime"
                label="添加时间"
              >
              </el-table-column>
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
          </el-row>
        </el-tab-pane>
        <el-tab-pane
          label="推荐分类"
          name="second"
        >推荐分类</el-tab-pane>
        <el-tab-pane
          v-if="isShowAddSort"
          label="添加分类"
          name="third"
        >
          <addSort />
        </el-tab-pane>
        <el-tab-pane
          v-if="isShowEditSort"
          label="编辑分类"
          name="fourth"
        >
          <editSort />
        </el-tab-pane>
      </el-tabs>
    </el-row>

  </div>
</template>
<script>
// import { getSortList } from '@/api/admin/goodsSort/goodsSort'
// import { getSortList, deleteSort } from '@/api/admin/goodsSort/goodsSort'
// import editSort from '@/view/admin/index/leftNavComponents/goods_manage/goodsSort/editSort'
// import addSort from '@/view/admin/index/leftNavComponents/goods_manage/goodsSort/addSort'
export default {
  // components: { addSort, editSort },
  data () {
    return {
      activeName: 'first',
      sortTableData: [],
      isExpand: false,
      isShowAddSort: false,
      isShowEditSort: true
    }
  },
  created () {
    this.sortTableData = [
      {
        'sortId': 3, // 分类ID
        'sortName': '服装', // 分类名称
        'parentId': 0, // 分类所属父分类，0表示是根分类
        'level': 0, // 分类层级
        'hasChild': 1, // 1有子分类，0无子分类
        'createTime': '2019-07-03 14:00:19',
        'updateTime': '2019-07-03 14:02:16',
        'sortImg': `https://s2.ax1x.com/2019/08/06/efLsm9.jpg`, // 一级分类是头图 其他为分类图标
        'imgLink': '/pages/sort/test', // 图标或者头图链接
        'first': 100, // 0普通分类，推荐分类
        'type': 0,
        'sortDesc': null,
        'children': [
          {
            'sortId': 31, // 分类ID
            'sortName': '女装', // 分类名称
            'parentId': 1, // 分类所属父分类，0表示是根分类
            'level': 1, // 分类层级
            'hasChild': 0, // 1有子分类，0无子分类
            'createTime': '2019-07-03 14:00:19',
            'updateTime': '2019-07-03 14:02:16',
            'sortImg': 'https://s2.ax1x.com/2019/08/06/efLxXQ.jpg', // 一级分类是头图 其他为分类图标
            'imgLink': '/pages/sort/test', // 图标或者头图链接
            'first': 90, // 0普通分类，推荐分类
            'type': 0,
            'sortDesc': null
          },
          {
            'sortId': 32, // 分类ID
            'sortName': '男装', // 分类名称
            'parentId': 1, // 分类所属父分类，0表示是根分类
            'level': 1, // 分类层级
            'hasChild': 0, // 1有子分类，0无子分类
            'createTime': '2019-07-03 14:00:19',
            'updateTime': '2019-07-03 14:02:16',
            'sortImg': 'https://s2.ax1x.com/2019/08/06/efOp0s.jpg', // 一级分类是头图 其他为分类图标
            'imgLink': '/pages/sort/test', // 图标或者头图链接
            'first': 90, // 0普通分类，推荐分类
            'type': 0,
            'sortDesc': null
          }
        ]
      }
    ]
    this.fetchSortList()
  },
  methods: {
    tabsHandleClick (tab, event) {
      console.log(tab, event)
    },
    handleAddSort () {
      this.isShowAddSort = true
      this.activeName = 'third'
    },
    handleEdit () {
      alert('编辑')
    },
    handleDelete (val, row) {
      // 删除的时候
      console.log(val, row)
      // this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning'
      // }).then(() => {
      //   let params = { 'sortId': row.sortId }
      //   console.log(params)
      //   deleteSort(params).then(res => {
      //     const { error } = res
      //     if (error === 0) {
      //       this.fetchSortList()
      //       this.$message({
      //         showClose: true,
      //         message: '删除分类成功',
      //         type: 'success'
      //       })
      //     }
      //   }).catch(err => console.log(err))
      // }).catch(err => console.log(err))
      // 删除结束
    },
    fetchSortList () {
      // let params = {
      //   'type': 0,
      //   'parentId': 0 // 分类类型 0普通分类，1推荐分类
      //   // 'sortName': 'name', // 分类名称
      //   // 'startCreateTime': '2019-01-01 12:02:11', // 分类创建开始时间
      //   // 'endCreateTime': '2020-01-01 00:00:00' // 分类创建结束时间
      // }
      // getSortList(params).then(res => {
      //   const { error, content } = res
      //   if (error === 0) {
      //     console.log(content)
      //     content.forEach((item, index) => {
      //       console.log(item)
      //     })
      //     // this.sortTableData = content
      //   } else {
      //     // this.$message.error('暂时有点小错误')
      //   }
      // }).catch(err => console.log(err))
    }
  }
}
</script>
<style scoped>
.handleAddSort {
  margin-bottom: 10px;
}
</style>
