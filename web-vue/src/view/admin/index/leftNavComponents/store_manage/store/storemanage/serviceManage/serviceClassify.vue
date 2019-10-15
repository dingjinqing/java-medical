<template>
  <div>
    <div class="sevice_classify_page">
      <div class="list-info service_class_info">
        <div>
          <el-input
            v-model="catName"
            placeholder="请输入分类名称"
            style="width: 188px;"
            size="small"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            @click="addCatHandle"
          >保存</el-button>
        </div>
        <div>
          <el-button
            type="primary"
            size="small"
            @click="initDataList"
          >查询</el-button>
          <el-input
            v-model="queryParams.catName"
            placeholder="请输入分类查询"
            style="width: 188px;"
            suffix-icon="el-icon-search"
            size="small"
          ></el-input>
        </div>
      </div>
      <div class="list-table">
        <el-table
          ref="serviceTable"
          :data="tableData"
          class="tableClass"
          max-height="500"
          border
          :header-cell-style="{
                'background-color':'#f5f5f5',
                'border':'none'
              }"
        >
          <el-table-column
            label="分类名"
            prop="catName"
          >
            <template slot-scope="{ row , $index}">
              <div v-if="row.edit">
                <el-input
                  ref="editInput"
                  size="small"
                  v-model="row.catName"
                  @blur="editHandle(row.catName, $index)"
                ></el-input>
              </div>
              <span v-else>{{row.catName}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row, $index }">
              <div>
                <el-tooltip content="编辑">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row, $index)"
                  >编辑</span>
                </el-tooltip>
                <el-tooltip content="删除">
                  <span
                    class="iconSpan"
                    @click="edit('delete', row, $index)"
                  >删除</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { getServiceCatsList, addServiceCat, updateServiceCat, deleteServiceCat } from '@/api/admin/storeManage/storemanage/serviceManage'
export default {
  data () {
    return {
      storeId: '',
      catName: '',
      queryParams: {
        storeId: '',
        catName: ''
      },
      tableData: [],
      pageParams: {},
      oldCatName: ''
    }
  },
  created () {
    this.storeId = this.$route.query.id
    this.queryParams.storeId = this.storeId
    this.initDataList()
  },
  directives: {
    focus: {
      // 指令的定义
      inserted: function (el) {
        el.focus()
      }
    }
  },
  methods: {
    addCatHandle () {
      // 验证是否填写类别名
      if (!this.catName) return false
      let params = {
        storeId: this.storeId,
        catName: this.catName
      }
      addServiceCat(params).then(res => {
        if (res.error === 0) {
          this.$message.success('添加服务分类成功')
          this.initDataList()
        }
      })
    },
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getServiceCatsList(params).then(res => {
        if (res.error === 0) {
          this.pageParams = res.content.page
          this.tableData = res.content.dataList
        }
      })
    },
    edit (prop, row, index) {
      switch (prop) {
        case 'edit':
          // this.tableData[index].edit = true
          this.$set(this.tableData[index], 'edit', true)
          this.$nextTick(function () {
            this.oldCatName = row.catName
            this.$refs.editInput.focus()
          })
          break
        case 'delete':
          let params = {
            catId: row.catId
          }
          deleteServiceCat(params).then(res => {
            if (res.error === 0) {
              this.$message.success('删除成功')
              this.initDataList()
            }
          })
          break
      }
    },
    editHandle (name, index) {
      let _this = this
      _this.$set(this.tableData[index], 'edit', false)
      if (name === _this.oldCatName) return false
      let params = {
        catId: _this.tableData[index].catId,
        catName: name
      }
      updateServiceCat(params).then(res => {
        if (res.error === 0) {
          _this.$message.success('更新成功')
          _this.initDataList()
        } else {
          _this.$message.error('更新失败')
          _this.$set(_this.tableData[index], 'catName', _this.oldCatName)
        }
      }).catch(err => {
        _this.$set(_this.tableData[index], 'catName', _this.oldCatName)
        throw err
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.sevice_classify_page {
  margin: 0 25px;
  .iconSpan {
    color: #5a8bff;
    text-decoration: none;
    cursor: pointer !important;
  }
  .list-info {
    display: flex;
    justify-content: space-between;
    padding-bottom: 10px;
  }
}
</style>
