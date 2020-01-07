<template>
  <div class="labelManagement">
    <div class="labelManagementMain">
      <div class="top">
        <el-button
          type="primary"
          size="small"
          @click="addHandler"
        >{{$t('tag.createNewTag')}}</el-button>
        <el-input
          v-model="searchTag"
          :placeholder="$t('tag.queryPrompt')"
          size="small"
          style="width:170px;"
          clearable
        ></el-input>
        <el-button
          type="primary"
          size="small"
          @click="getTagList"
        >{{$t('tag.query')}}</el-button>
      </div>
      <div class="tableMain">
        <el-table
          class="version-manage-table"
          :data="tableData"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="tagName"
            align="center"
            :label="$t('tag.tagName')"
          >
          </el-table-column>
          <el-table-column
            :label="$t('tag.createTime')"
            align="center"
            prop="createTime"
          >
          </el-table-column>
          <el-table-column
            prop="count"
            :label="$t('tag.numberOfUser')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            :label="$t('tag.operation')"
            align="center"
          >
            <template slot-scope="scope">
              <el-tooltip
                :content="$t('tag.edit')"
                placement="top"
              >
                <span
                  class="el-icon-edit-outline iconStyle"
                  @click="getTagDetail(scope.row.tagId)"
                ></span>

              </el-tooltip>
              <el-tooltip
                :content="$t('tag.remove')"
                placement="top"
              >
                <span
                  class="el-icon-delete iconStyle"
                  @click="deleteHandler(scope.row.tagId)"
                ></span>
              </el-tooltip>

              <el-tooltip
                :content="$t('tag.viewUser')"
                placement="top"
              >
                <span
                  class="el-icon-user-solid iconStyle"
                  @click="viewUserHanlder(scope.row.tagId)"
                ></span>
              </el-tooltip>
            </template>
          </el-table-column>

        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="getTagList"
        />
      </div>
    </div>
    <!--新建标签弹窗-->
    <el-dialog
      :title="dialogType === true ? '新建标签' : '编辑标签'"
      :visible.sync="dialogVisible"
      width="350px"
      :close-on-click-modal="false"
      center
    >
      <div class="labelDialog">
        <div style="margin-bottom:10px">{{ $t('tag.tagName')}}</div>
        <el-input
          v-model="dialogTag"
          :placeholder="$t('tag.inputContent')"
          size="small"
        ></el-input>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          @click="cancelTagHandler"
        >{{$t('tag.cancel')}}</el-button>
        <el-button
          type="primary"
          size="small"
          @click="saveTagHandler"
        >{{$t('tag.ok')}}</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import { getAllTagListByName, getTagDetail, addTag, updateTag, deleteTag } from '@/api/admin/memberManage/tagManage/tagManage.js'
export default {

  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      searchTag: '',
      tableData: [], // 表格数据
      pageParams: {}, // 分页
      requestParams: {},
      dialogVisible: false, // 标签弹窗
      dialogTag: '',
      dialogType: true, // 弹窗类型( true: 新建, false: 编辑)
      editId: null // 编辑id
    }
  },
  watch: {
    lang () {
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.getTagList()
  },
  methods: {
    // 标签管理列表
    getTagList () {
      this.requestParams.tagName = this.searchTag
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getAllTagListByName(this.requestParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },

    // 新建
    addHandler () {
      this.dialogType = true
      this.dialogTag = ''
      this.dialogVisible = !this.dialogVisible
    },

    // 编辑详情
    getTagDetail (id) {
      this.dialogType = false
      this.editId = id
      getTagDetail().then((res) => {
        if (res.error === 0) {
          res.content.forEach((item, index) => {
            if (id === item.id) {
              this.dialogTag = item.value
              this.dialogVisible = !this.dialogVisible
            }
          })
        }
      })
    },

    // 保存
    saveTagHandler () {
      if (this.dialogTag === '') {
        this.$message.warning('请填写标签名')
        return
      }
      if (this.dialogType === true) {
        // 添加
        addTag({ tagName: this.dialogTag }).then((res) => {
          if (res.error === 0) {
            this.$message.success('新建成功!')
            this.getTagList()
          }
        })
      } else {
        // 编辑
        updateTag({
          tagId: this.editId,
          tagName: this.dialogTag
        }).then((res) => {
          if (res.error === 0) {
            this.$message.success('编辑成功!')
            this.getTagList()
          }
        })
      }

      this.dialogVisible = !this.dialogVisible
    },

    // 取消
    cancelTagHandler () {
      this.dialogVisible = false
      this.dialogTag = ''
    },

    // 删除
    deleteHandler (id) {
      this.$confirm(this.$t('seckill.deleteTip'), {
        confirmButtonText: this.$t('seckill.sure'),
        cancelButtonText: this.$t('seckill.cancel'),
        type: 'warning'
      }).then(() => {
        deleteTag({ tagId: id }).then(res => {
          if (res.error === 0) {
            this.$message.success(this.$t('tag.removeTagSuccess'))
            this.getTagList()
          }
        })
      }).catch(() => {
        this.$message.info({ message: this.$t('seckill.deleteFail') })
      })
    },

    // 查看用户明细
    viewUserHanlder (tagId) {
      this.$router.push({
        path: '/admin/home/main/membershipList',
        query: {
          tagId: tagId
        }
      })
    }

  }
}
</script>
<style lang="scss" scoped>
.labelManagement {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .labelManagementMain {
    position: relative;
    overflow: hidden;
    overflow-y: auto;
    .top {
      padding: 15px;
      background: #fff;
      margin-bottom: 10px;
      /deep/ .el-button {
        width: 100px;
      }
      /deep/ .el-input {
        width: 150px;
        margin: 0 20px;
      }
      display: flex;
    }
    .tableMain {
      position: relative;
      background-color: #fff;
      overflow: hidden;
      overflow-y: auto;
      padding: 15px;
      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
        .el-checkbox {
          margin-left: -4px;
        }
      }
      .iconStyle {
        font-size: 22px;
        color: #5a8bff;
        cursor: pointer;
      }
      .operation {
        display: flex;
        justify-content: space-around;
        span {
          cursor: pointer;
          color: #5a8bff;
        }
      }
    }
  }
  /deep/ .el-dialog__header {
    text-align: center;
    background-color: #f3f3f3;
  }
  .labelDialog {
    /deep/ .el-input {
      width: 280px;
    }
  }
}
</style>
