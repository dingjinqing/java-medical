<template>
  <div class="container">
    <div class="top">
      <ul class="filters">
        <li>
          <label>标题：</label>
          <el-input
            class="filter-input"
            size="small"
            v-model="queryParams.title"
          ></el-input>
        </li>
        <li>
          <label for="">发布状态：</label>
          <el-select
            v-model="queryParams.status"
            size="small"
          >
            <el-option
              label="全部"
              :value="-1"
            ></el-option>
            <el-option
              label="已发布"
              :value="1"
            ></el-option>
            <el-option
              label="未发布"
              :value="0"
            ></el-option>
          </el-select>
        </li>
        <li>
          <el-button
            size="small"
            type="primary"
            @click="initDataList"
          >筛选</el-button>
        </li>
      </ul>
      <div style="margin-top:10px;">
        <el-button
          type="primary"
          size="small"
          @click="addAnnouncement"
        >添加公告</el-button>
      </div>
    </div>
    <div class="content">
      <el-table
        :data="tableData"
        header-row-class-name="tableClss"
        border
      >
        <el-table-column
          label="标题"
          align="center"
          prop="title"
        ></el-table-column>
        <el-table-column
          label="更新时间"
          align="center"
          prop="updateTime"
        ></el-table-column>
        <el-table-column
          label="发布状态"
          align="center"
          prop="status"
          :formatter="statusFmt"
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              <el-tooltip
                content="编辑"
                placement="top"
                effect="light"
              >
                <i
                  class="el-icon-edit-outline iconSpan"
                  @click="edit('edit', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
                effect="light"
              >
                <i
                  class="el-icon-delete iconSpan"
                  @click="edit('delete', row)"
                ></i>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { announcementListApi, announcementDeleteApi } from '@/api/admin/storeManage/storeAnnouncement.js'
export default {
  data () {
    return {
      queryParams: {
        title: '',
        status: -1
      },
      pageParams: {
        currentPage: 1
      },
      tableData: []
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      announcementListApi(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        } else {
          this.$message.error(res.message)
        }
      })
    },
    addAnnouncement () {
      this.$router.push({
        path: '/admin/home/main/store/storeAnnouncementAdd'
      })
    },
    statusFmt (row, column) {
      console.log(row)
      let val = row.status
      if (val === 0) {
        return '未发布'
      }
      return '已发布'
    },
    edit (operate, row) {
      let that = this
      let id = row.articleId
      switch (operate) {
        case 'edit':
          that.$router.push({
            path: '/admin/home/main/store/storeAnnouncementAdd',
            query: {
              articleId: id
            }
          })
          break
        case 'delete':
          that.$confirm('确定要删除该', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            announcementDeleteApi({
              articleId: id
            }).then(res => {
              if (res.error === 0) {
                that.$message.success('删除成功')
                that.initDataList()
              } else {
                that.$message.error(res.message)
              }
            })
          })
          break
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  font-size: 14px;
  color: #333;
  .top {
    padding: 15px;
    background: #fff;
    .filters {
      li {
        display: inline-block;
        margin-right: 10px;
      }
      label {
        display: inline-block;
      }
    }
    .filter-input {
      width: 170px;
    }
  }
  .content {
    padding: 15px;
    margin-top: 10px;
    background: #fff;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
    .iconSpan {
      font-size: 22px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
</style>
