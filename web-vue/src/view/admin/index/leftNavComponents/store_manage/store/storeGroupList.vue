<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <el-button
          type="primary"
          size="small"
          @click="addGroup"
        >{{$t('marketCommon.add')}}</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>{{$t('storeList.groupName')}}:</span>
            <el-input
              v-model="queryParams.groupName"
              class="inputWidth"
              size="small"
              :placeholder="$t('storeList.groupNamePlaceholder')"
            ></el-input>
          </div>
          <el-button
            @click="initDataList"
            class="btn"
            type="primary"
            size="small"
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop="groupName"
            :label="$t('storeList.groupName')"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('storeCommon.createTime')"
          ></el-table-column>
          <el-table-column
            prop="numbers"
            :label="$t('storeList.storeNumber')"
          >
          </el-table-column>

          <el-table-column
            :label="$t('marketCommon.operate')"
            width="230px"
          >
            <template slot-scope="{row}">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeCommon.edit')"
                  placement="top"
                >
                  <a @click="edit(row.id, 'edit', row)">{{$t('storeCommon.edit')}}</a>
                </el-tooltip>

                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeCommon.delete')"
                  placement="top"
                >
                  <a @click="edit(row.id, 'delete', row)">{{$t('storeCommon.delete')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeList.checkStores')"
                  placement="top"
                >
                  <a @click="edit(row.id, 'dark', row)">{{$t('storeList.checkStores')}}</a>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
    <!-- 添加分组 -->
    <el-dialog
      title="新建分组"
      :visible.sync="addGroupVisible"
      :close-on-click-modal="false"
      width="330px"
    >
      <div>
        <p style="margin-bottom:15px">分组名称</p>
        <el-input
          size="small"
          v-model="groupName"
        ></el-input>
      </div>
      <span slot="footer">
        <el-button
          type="primary"
          size="small"
          @click="confirmAddGroup"
        >确定</el-button>
        <el-button
          size="small"
          @click="cancelAddGroup"
        >取消</el-button>
      </span>
    </el-dialog>
    <!-- 编辑分组 -->
    <el-dialog
      title="编辑分组"
      :visible.sync="editGroupVisible"
      :close-on-click-modal="false"
      :before-close="handleClose"
      width="330px"
    >
      <div>
        <p style="margin-bottom:15px">分组名称</p>
        <el-input
          size="small"
          v-model="editGroupName"
        ></el-input>
      </div>
      <span slot="footer">
        <el-button
          type="primary"
          size="small"
          @click="confirmEditGroup"
        >确定</el-button>
        <el-button
          size="small"
          @click="cancelEditGroup"
        >取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { storeGroupList, addStoreGroup, delStoreGroup, updateStoreGroup } from '@/api/admin/storeManage/store'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      addGroupVisible: false,
      editGroupVisible: false,

      pageParams: {},
      queryParams: {},
      tableData: [],

      // 表格原始数据
      originalData: [],
      groupName: '', // 添加的分组名
      editGroupName: '', // 编辑分组名
      editGroupId: '' // 编辑分组ID
    }
  },
  methods: {
    initDataList () {
      this.loading = true

      storeGroupList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理/渲染
    handleData (data) {
      this.tableData = data
      this.langDefaultFlag = true
    },
    edit (id, operate, row) {
      let that = this
      switch (operate) {
        case 'edit':
          that.editGroupVisible = !that.editGroupVisible
          that.editGroupName = row.groupName
          that.editGroupId = row.groupId
          break
        case 'delete':
          that.$confirm('删除此分组，已有此分组的用户将失去该分组，是否确认删除？', '提醒').then(() => {
            let params = {
              groupId: row.groupId
            }
            delStoreGroup(params).then(res => {
              console.log(res)
              if (res.error === 0) {
                that.$message.success('删除成功')
                that.initDataList()
              } else {
                that.$message.error('删除失败')
              }
            })
          }).catch(() => {
            that.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
          break
        case 'dark':
          that.$router.push({
            path: '/admin/home/main/store/list',
            query: {
              groupId: row.groupId
            }
          })
          break
      }
    },
    addGroup () {
      this.addGroupVisible = !this.addGroupVisible
    },
    confirmAddGroup () {
      let that = this
      let params = {
        groupName: this.groupName
      }
      addStoreGroup(params).then(res => {
        if (res.error === 0) {
          that.$message.success('添加分组成功')
          that.initDataList()
          that.addGroupVisible = false
        } else if (res.error === 180001) {
          that.$message.error(res.message)
        } else {
          that.$message.error('添加分组失败')
        }
      })
    },
    cancelAddGroup () {
      this.groupName = ''
      this.addGroupVisible = false
    },
    confirmEditGroup () {
      let that = this
      let params = {
        groupName: that.editGroupName,
        groupId: that.editGroupId
      }
      updateStoreGroup(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          that.$message.success('更新成功')
          that.initDataList()
          that.editGroupVisible = false
        } else {
          that.$message.error('更新失败')
        }
      })
    },
    handleClose () {
      this.editGroupId = ''
      this.editGroupName = ''
    },
    cancelEditGroup () {
      this.editGroupVisible = false
    }
  },
  watch: {
    // data内变量国际化
    lang () {
      if (this.langDefaultFlag) {
        // 重新渲染表格数据
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
      }
    }
  },
  mounted () {
    this.langDefault()
    this.initDataList()
  }

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .navBox {
    background-color: #fff;
    padding: 15px;
    margin-bottom: 10px;
  }
  .btn {
    margin-left: 5px;
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .filters {
      display: flex;
      line-height: 32px;
      margin-left: -15px;
      margin-bottom: 10px;
      .filters_item {
        max-width: 350px;
        display: flex;
        margin-left: 15px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .operation {
      display: flex;
      flex-wrap: wrap;
      margin-left: -5px;
      > .item {
        font-size: 14px;
        color: #66b1ff;
        cursor: pointer;
        margin-right: 8px;
      }
    }
    .businessStateOperate {
      font-size: 14px;
      color: #66b1ff;
      cursor: pointer;
    }
    .tapOneblock {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      > span {
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>
