<template>
  <div class="container">
    <div class="top">
      <ul class="filters">
        <li>
          <label>{{$t('storeAnnouncement.title')}}：</label>
          <el-input
            class="filter-input"
            size="small"
            v-model="queryParams.title"
          ></el-input>
        </li>
        <li>
          <label for="">{{$t('storeAnnouncement.postStatus')}}：</label>
          <el-select
            v-model="queryParams.status"
            size="small"
          >
            <el-option
              :label="$t('storeAnnouncement.all')"
              :value="-1"
            ></el-option>
            <el-option
              :label="$t('storeAnnouncement.published')"
              :value="1"
            ></el-option>
            <el-option
              :label="$t('storeAnnouncement.unPublished')"
              :value="0"
            ></el-option>
          </el-select>
        </li>
        <li>
          <el-button
            size="small"
            type="primary"
            @click="initDataList"
          >{{$t('storeAnnouncement.filter')}}</el-button>
        </li>
      </ul>
      <div style="margin-top:10px;">
        <el-button
          type="primary"
          size="small"
          @click="addAnnouncement"
        >{{$t('storeAnnouncement.addAnnouncement')}}</el-button>
      </div>
    </div>
    <div class="content">
      <el-table
        :data="tableData"
        header-row-class-name="tableClss"
        border
      >
        <el-table-column
          :label="$t('storeAnnouncement.title')"
          align="center"
          prop="title"
        ></el-table-column>
        <el-table-column
          :label="$t('storeAnnouncement.updateTime')"
          align="center"
          prop="updateTime"
        ></el-table-column>
        <el-table-column
          :label="$t('storeAnnouncement.postStatus')"
          align="center"
          prop="status"
          :formatter="statusFmt"
        ></el-table-column>
        <el-table-column
          :label="$t('storeAnnouncement.operate')"
          align="center"
        >
          <template slot-scope="{row}">
            <div>
              <el-tooltip
                :content="$t('storeAnnouncement.edit')"
                placement="top"
                effect="light"
              >
                <i
                  class="el-icon-edit-outline iconSpan"
                  @click="edit('edit', row)"
                ></i>
              </el-tooltip>
              <el-tooltip
                :content="$t('storeAnnouncement.delete')"
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
      <pagination
        :page-params.sync="pageParams"
        @pagination="initDataList"
      ></pagination>
    </div>
  </div>
</template>

<script>
import { announcementListApi, announcementDeleteApi } from '@/api/admin/storeManage/storeAnnouncement.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      queryParams: {
        title: '',
        status: -1
      },
      pageParams: {
        currentPage: 1,
        totalRows: 0
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
      if (Number(val) === 0) {
        return this.$t('storeAnnouncement.unPublished')
      }
      return this.$t('storeAnnouncement.published')
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
          that.$confirm(this.$t('storeAnnouncement.ayDelete'), {
            confirmButtonText: this.$t('storeAnnouncement.yes'),
            cancelButtonText: this.$t('storeAnnouncement.no'),
            type: 'warning'
          }).then(() => {
            announcementDeleteApi({
              articleId: id
            }).then(res => {
              if (res.error === 0) {
                that.$message.success(that.$t('storeAnnouncement.deleteSuccess'))
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
