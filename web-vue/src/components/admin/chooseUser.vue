<template>
  <!--选择会员-->
  <div class="baseInfo">
    <el-dialog
      :title="$t('membershipIntroduction.chooseUser')"
      :visible.sync="modifypersonDialogVisible"
      width="800px"
      :modal-append-to-body="false"
    >
      <div
        class="modifypersonDiv"
        style="margin-bottom:30px"
      >
        <div class="modifypersonDivTop">
          <div>
            <span>{{ $t('membershipIntroduction.nickname') }}</span>
            <el-input
              size="small"
              v-model="userNameInput"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
            ></el-input>
          </div>
          <div>
            <span style="width:80px">{{ $t('membershipIntroduction.phoneNum') }}</span>
            <el-input
              size="small"
              v-model="mobileInput"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
            ></el-input>
          </div>
          <div>
            <span style="width:90px">{{ $t('membershipIntroduction.Realname') }}</span>
            <el-input
              size="small"
              v-model="realNameInput"
              :placeholder="$t('membershipIntroduction.Pleasecontent')"
            ></el-input>
          </div>
          <el-button
            type="primary"
            size="small"
            @click="getUserTabelListData"
          >{{ $t('membershipIntroduction.search')}}</el-button>
        </div>
        <br>
        <!--底部表格-->
        <div class="table_list">
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="trList"
            border
            height="350"
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%"
            ref="multipleTable"
          >
            <el-table-column
              prop="userId"
              label="Id"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="userName"
              label="昵称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="realName"
              label="真实姓名"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="mobile"
              label="手机号"
              align="center"
            >
            </el-table-column>
          </el-table>
          <div class="table_footer">
            <pagination
              :page-params.sync="pageParams"
              @pagination="getUserTabelListData"
            />
          </div>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
          <el-button
            size="small"
            @click="modifypersonDialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="comfirm()"
          >确 定</el-button>
        </span>
    </el-dialog>
  </div>
</template>
<script>
import { membershipListRequest } from '@/api/admin/membershipList.js'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  // 组件入参
  props: {
    dialogVisible: {
      type: Boolean,
      default: () => false
    },
    queryParams: {
      type: Object,
      default: () => {}
    }
  },
  data () {
    return {
      pageParams: {
        currentPage: 0,
        pageRows: 10
      },
      userNameInput: '', // 用户输入->昵称
      mobileInput: '', // 用户输入->手机号
      realNameInput: '', // 用户输入->真实姓名
      modifypersonDialogVisible: false,
      trList: [],
      rowData: {},
      currentRow: {}
    }
  },
  watch: {
    dialogVisible () {
      this.modifypersonDialogVisible = true
      // 清空弹出框的输入数据
      this.clearInputData()
      this.getUserTabelListData()
    }
  },
  created () {
    this.langDefault()
  },
  methods: {
    // 确定 按钮点击
    comfirm () {
      this.modifypersonDialogVisible = false
      // 返回组件出参
      this.$emit('rowData', this.currentRow)
      this.$refs.multipleTable.clearSelection()
    },
    // 获取会员用户
    getUserTabelListData () {
      let obj = {
        'mobile': this.mobileInput,
        'username': this.userNameInput,
        'realName': this.realNameInput,
        'currentPage': this.pageParams.currentPage,
        'pageRows': this.pageParams.pageRows
      }
      console.log(obj)
      membershipListRequest(obj).then(res => {
        if (res.error === 0) {
          this.trList = res.content.dataList.reverse()
          console.log('会员列表' + this.trList)
          this.pageParams = res.content.page
        }
      })
    },
    // 删除用户输入的查询信息
    clearInputData () {
      this.mobileInput = null
      this.userNameInput = null
      this.realNameInput = null
    },
    // 列表行选中事件
    handleCurrentChange (val) {
      this.currentRow = val
      console.log('当前行数据为：' + this.currentRow)
    }
  }
}
</script>
<style lang="scss" scoped>
  .table_list {
    position: relative;
    .table_footer {
      background: #666;
    }
  }
  .content {
    margin-top: 10px;
  }
  .la
  .modifypersonDivTop .el-input__inner {
    width: 140px !important;
  }
  .modifypersonDivTop,
  .modifypersonDivTop > div {
    display: flex;
  }
  .modifypersonDivTop > div > span {
    line-height: 32px;
    height: 32px;
    display: block;
    width: 56px;
  }
  .baseInfo .el-dialog__body {
    padding-bottom: 0 !important;
  }
  .baseInfo .el-dialog__footer {
    border-top: 1px solid #eee;
  }
  .technician_list_page {
    margin: 0 25px;
    .list_info {
      padding-bottom: 10px;
      .filter_input {
        width: 170px;
      }
      .technician_list_img {
        display: inline-block;
        width: 60px;
        height: 60px;
      }
    }
    .list_table {
      .iconSpan {
        color: #5a8bff;
        text-decoration: none;
        cursor: pointer !important;
      }
    }
  }
</style>
