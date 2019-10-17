<template>
  <div>
    <div class="technician_classify_page">
      <div class="list-info technician_class_info">
        <div>
          <el-input
            placeholder="请输入分类名称"
            style="width: 188px;"
            size="small"
            v-model="newTechnicianGroupName"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            @click="addTechnicianGroupHandle"
          >保存</el-button>
        </div>
      </div>
      <div class="list-table">
        <el-table
          ref="technicianTable"
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
            label="分组名称"
            prop="groupName"
          >
            <template slot-scope="{row}">
              <div v-if="row.edit">
                <el-input
                  ref="editInput"
                  v-model="row.groupName"
                  @blur="updateTechnicianGroupHandle(row)"
                ></el-input>
              </div>
              <div v-else>{{row.groupName}}</div>
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
            <template slot-scope="{ row }">
              <div>
                <el-tooltip content="编辑">
                  <span
                    class="iconSpan"
                    @click="edit('edit', row)"
                  >编辑</span>
                </el-tooltip>
                <el-tooltip content="删除">
                  <span
                    class="iconSpan"
                    @click="edit('delete', row)"
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
import { getTechnicianGroupList, addTechnicianGroup, updateTechnicianGroup, deleteTechnicianGroup } from '@/api/admin/storeManage/storemanage/technicianManage'
export default {
  data () {
    return {
      newTechnicianGroupName: '', // 添加技师分组的名字
      queryParams: {
        storeId: ''
      },
      tableData: [],
      pageParams: {}
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getTechnicianGroupList(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
        }
      })
    },
    addTechnicianGroupHandle () {
      if (!this.newTechnicianGroupName) return false
      let params = {
        groupName: this.newTechnicianGroupName,
        storeId: this.queryParams.storeId
      }
      addTechnicianGroup(params).then(res => {
        if (res.error === 0) {
          this.$message.success('成功添加技师分组')
          this.initDataList()
        }
      })
    },
    edit (operate, row) {
      switch (operate) {
        case 'edit':
          this.$set(row, 'edit', true)
          this.$nextTick(() => {
            this.$refs.editInput.focus()
          })
          break
        case 'delete':
          let params = {
            id: row.groupId
          }
          deleteTechnicianGroup(params).then(res => {
            if (res.error === 0) {
              this.$message.success('删除分组成功')
              this.initDataList()
            }
          })
          break
      }
    },
    updateTechnicianGroupHandle (row) {
      this.$set(row, 'edit', !row.edit)
      let params = {
        groupId: row.groupId,
        groupName: row.groupName,
        storeId: this.queryParams.storeId
      }
      updateTechnicianGroup(params).then(res => {
        if (res.error === 0) {
          this.$message.success('更新分组成功')
          this.initDataList()
        } else {
          this.$message.error('更新分组失败')
          this.initDataList()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.technician_classify_page {
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
