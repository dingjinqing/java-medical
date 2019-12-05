<template>
  <div class="labelManagement">
    <div class="labelManagementMain">
      <div class="top">
        <el-button
          type="primary"
          size="small"
          @click="handleToButton(0)"
        >{{$t('tag.createNewTag')}}</el-button>
        <el-input
          v-model="labelInput"
          :placeholder="$t('tag.queryPrompt')"
          size="small"
          style="width:170px;"
          clearable
        ></el-input>
        <el-button
          type="primary"
          size="small"
          @click="handleToButton(1)"
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
                <span class="el-icon-edit-outline iconStyle"></span>
                <!-- @click="editHandler(scope.row.skId, scope.row)" -->
              </el-tooltip>
              <el-tooltip
                :content="$t('tag.remove')"
                placement="top"
              >
                <span
                  style="font-size: 22px;color: #5a8bff;cursor:pointer;"
                  class="el-icon-delete iconStyle"
                ></span>
                <!-- @click="deleteHandler(scope.row.skId)" -->
              </el-tooltip>

              <el-tooltip
                :content="$t('tag.viewUser')"
                placement="top"
              >
                <span
                  style="font-size: 22px;color: #5a8bff;cursor:pointer;"
                  class="el-icon-user-solid iconStyle"
                ></span>
                <!-- @click="seckillUserHanlder(scope.row.skId, scope.row.name)" -->
              </el-tooltip>
            </template>
          </el-table-column>

        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="pageQuery"
        />
      </div>
    </div>
    <!--新建标签弹窗-->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="350px"
      :close-on-click-modal="false"
      center
    >
      <div class="labelDialog">
        <div style="margin-bottom:10px">{{ $t('tag.tagName')}}</div>
        <el-input
          v-model="labelDialogInput"
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
          @click="dialogVisible = false"
        >{{$t('tag.cancel')}}</el-button>
        <el-button
          type="primary"
          size="small"
          @click="handleTagEditOrCreateOption"
        >{{$t('tag.ok')}}</el-button>
      </span>
    </el-dialog>
    <!--删除弹窗-->
    <el-dialog
      :title="$t('tag.remind')"
      :visible.sync="dialogDelVisible"
      width="30%"
    >
      <span>{{$t('tag.deletePrompt')}}</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogDelVisible = false">{{$t('tag.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="handleToDeleteTagDialog"
        >{{$t('tag.ok')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { appendTag, getAllTagListByName, modifyTagName, deleteTag } from '@/api/admin/memberManage/tagManage/tagManage.js'
export default {

  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      dialogDelVisible: false,
      dialogTitle: '',
      dialogVisible: false,
      labelInput: '',
      tableData: null,
      pageParams: {
        totalRows: null,
        currentPage: 1,
        pageRows: 20
      },
      labelDialogInput: '',
      currentOptionTagId: null,
      createNewTagDialogOn: false
    }
  },
  created () {
    this.loadAllTagList()
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  watch: {
    lang () {
    }
  },
  methods: {
    loadAllTagList () {
      let param = {
        ...this.pageParams,
        tagName: this.labelInput
      }
      console.log(param)
      this.getAllTagList(param)
    },
    getAllTagList (param) {
      getAllTagListByName(param).then(res => {
        console.log(res)
        if (res.error === 0) {
          console.log('success')
          this.dealPage(res.content.page)
          this.dealTagListForHtmlShow(res.content.dataList)
        }
      })
    },
    dealPage (page) {
      this.pageParams = page
    },
    dealTagListForHtmlShow (dataList) {
      this.tableData = dataList
      console.log(this.tableData)
    },
    pageQuery (data) {
      console.log(data)
      this.loadAllTagList()
    },
    showCreateNewTagDialog () {
      this.labelDialogInput = ''
      this.dialogTitle = this.$t('tag.createNewTag')
      this.dialogVisible = true
      this.createNewTagDialogOn = true
    },
    handleTagEditOrCreateOption () {
      if (this.labelDialogInput !== '') {
        if (this.createNewTagDialogOn) {
          this.handleToCreatNewDialog()
        } else {
          this.handleToEditDialog()
        }
      } else {
        this.$message.warning('请填写标签名')
      }
    },
    handleToCreatNewDialog () {
      this.dialogVisible = false
      this.createNewTagDialogOn = false
      this.handleToCreateNewTag()
    },
    handleToCreateNewTag () {
      appendTag({ tagName: this.labelDialogInput }).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('tag.addTagSuccess'))
          this.loadAllTagList()
        }
      })
    },
    handleToEditDialog () {
      this.dialogVisible = false
      this.handleModifyTagName()
    },
    handleToDeleteTagDialog () {
      this.dialogDelVisible = false
      this.handleDeleteTag({ tagId: this.currentOptionTagId })
    },
    handleDeleteTag (data) {
      deleteTag(data).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('tag.removeTagSuccess'))
          this.loadAllTagList()
        }
      })
    },
    handleModifyTagName () {
      let param = {
        tagId: this.currentOptionTagId,
        tagName: this.labelDialogInput
      }
      modifyTagName(param).then(res => {
        if (res.error === 0) {
          this.$message.success(this.$t('tag.modifyTagSuccess'))
          this.loadAllTagList()
        }
      })
    },
    // 操作部分点击
    handleToOperation (row, index) {
      console.log(row, index)
      this.temporarySaveCurrentRowTag(row)
      switch (index) {
        case 0:
          this.dialogTitle = this.$t('tag.editTag')
          this.dialogVisible = true
          break
        case 1:
          this.dialogDelVisible = true
          break
        case 2:
          this.$router.push({
            name: 'user_list',
            params: {
              ...row
            }
          })
      }
    },
    temporarySaveCurrentRowTag (row) {
      this.labelDialogInput = row.tagName
      this.currentOptionTagId = row.tagId
    },
    // 顶部按钮点击
    handleToButton (flag) {
      switch (flag) {
        case 0:
          this.showCreateNewTagDialog()
          break
        case 1:
          this.loadAllTagList()
      }
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
    // background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    // padding: 15px 25px;
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
      // margin-top: 10px;
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
