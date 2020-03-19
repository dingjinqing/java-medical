<template>
  <div class="memberListDialog">
    <el-dialog
      :title="title"
      :visible.sync="memberListDialog"
      :before-close="cancelDialog"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      width="50%"
      center
    >
      <div>
        <el-form
          :inline="true"
          :model="pageParams"
        >
          <el-form-item
            :label="labels.label1"
            prop="userId"
          >
            <el-input
              v-model="pageParams.userId"
              size="small"
              style="width:150px"
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="labels.label2"
            prop="username"
          >
            <el-input
              v-model="pageParams.username"
              size="small"
              style="width:150px"
            ></el-input>
          </el-form-item>
          <el-form-item
            :label="labels.label3"
            prop="mobile"
          >
            <el-input
              v-model="pageParams.mobile"
              size="small"
              style="width:150px"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              @click="handleSearch"
              size="small"
              type="primary"
            >搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!-- 表格 -->
      <div class="table">
        <el-table
          ref="table"
          :data="questions"
          tooltip-effect="dark"
          style="width: 100%"
          :row-key="(row)=>{ return row.userId}"
          @selection-change="handleSelectionChange"
        >
          <el-table-column
            type="selection"
            :reserve-selection="true"
          >
          </el-table-column>
          <el-table-column
            label="ID"
            prop="userId"
          >
          </el-table-column>
          <el-table-column
            label="昵称"
            prop="username"
          >
          </el-table-column>
          <el-table-column
            label="手机号"
            prop="mobile"
          >
          </el-table-column>
        </el-table>
        <pagination
          @pagination="getMemberList"
          :pageParams="pageParams"
        />
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          @click="handleSave"
          size="small"
        >确定</el-button>
        <el-button
          type="primary"
          @click="handleCancel"
          size="small"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { membershipListRequest } from '@/api/admin/membershipList'
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: 'memberListDialog',
  components: {
    pagination
  },
  props: {
    /**
     * 作为接口传给父组件，让父组件通过管理这个变量来操作子组件
     */
    memberListDialog: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      /**
       * 弹窗的数据
       */
      title: `用户列表`,
      labels: {
        label1: `ID`,
        label2: `昵称`,
        label3: `手机号`
      },
      pageParams: {
        'currentPage': null,
        'pageRows': null,
        'mobile': null,
        'userId': null,
        'username': null
      },
      /**
       * 翻页的时候将questions的数据在allSelecteds判断是否存在,存在就设置为选中
       */
      questions: [], // 分页时从后台获取的本页面数据
      multipleSelection: [], // 当前页选中的项
      allSelecteds: [], // 所有选中的项
      /**
       * 已选择的数组
       */
      selectedList: []
    }
  },
  created () {
    // 初始化弹窗的数据
    this.getMemberList()
  },
  methods: {
    /**
     * 声明了一个函数的接口给父组件，让父组件可以通过一个函数操作子组件
     */
    cancelDialog () {
      this.$emit('dialog-cancel')
    },
    // 会员列表页
    getMemberList () {
      membershipListRequest(this.pageParams).then(res => {
        const { error, content: { page, dataList } } = res
        if (error === 0) {
          console.log(res)
          this.questions = dataList
          this.pageParams = page
          console.log(this.pageParams)
        }
      }).catch(err => console.log(err))
    },
    handleSelectionChange (val) {
      // console.log(val)
      this.selectedList = val
    },
    // 根据条件筛选
    handleSearch () {
      this.getMemberList()
    },
    // 确定
    handleSave () {
      // 处理完确定之后的操作关闭弹窗
      this.$emit(`userIdList`, this.selectedList)
      this.cancelDialog()
    },
    // 取消
    handleCancel () {
      this.cancelDialog()
    }
  }
}

</script>
<style lang="scss" scoped>
.memberListDialog {
  .table {
    height: 350px;
    width: 100%;
    overflow: auto;
  }
}
</style>
