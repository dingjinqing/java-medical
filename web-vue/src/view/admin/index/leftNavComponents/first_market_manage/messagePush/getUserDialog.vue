<template>
  <div class="getUserDialog">
    <!-- 获取人群列表 -->
    <el-dialog
      title="选择用户"
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleClose"
      center
    >

      <div class="title">
        注：因微信小程序官方限制，本日部分用户可接收的消息数量已达上限，故接收不到本次消息推送
      </div>
      <div>
        <el-form
          size="small"
          :inline="true"
          :form="formData"
        >
          <el-form-item :label="labels.id">
            <el-input v-model="formData.id"></el-input>
          </el-form-item>
          <el-form-item :label="labels.userName">
            <el-input v-model="formData.userName"></el-input>
          </el-form-item>
          <el-form-item :label="labels.phone">
            <el-input v-model="formData.phone"></el-input>
          </el-form-item>
          <el-form-item :label="labels.isVisit">
            <el-select
              v-model="value"
              placeholder="是否关注公众号"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              size="small"
              type="primary"
              @click="handleToSerch()"
            >搜索</el-button>
          </el-form-item>
        </el-form>

      </div>
      <!-- table -->
      <div class="main">
        <el-table
          :data="tableData"
          style="width: 100%"
          border
          @selection-change="handleSelectionChangeRoom"
          ref="multipleTable"
          header-row-class-name="tableClss"
          class="version-manage-table"
          highlight-current-row
          :row-key="getRowKeys"
        >

          <el-table-column
            align="center"
            type="selection"
            :selectable='selectDisableRoom'
            width="60"
            :reserve-selection="true"
          >
          </el-table-column>
          <el-table-column
            prop="userId"
            label="id"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="username"
            label="昵称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="subscribe"
            label="是否关注公众号"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.subscribe?'是':'否'}}
            </template>
          </el-table-column>
        </el-table>
        <div class="footer">
          <span>{{$t('formStatisticsHome.currentPage')}}{{currentPage}}/{{totalPage}}，{{$t('formStatisticsHome.generalRecord')}}{{totalRows}}{{$t('formStatisticsHome.strip')}}</span>
          <el-pagination
            @current-change="fetchData"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totalRows"
          >
          </el-pagination>
        </div>
      </div>
      <div>

      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="handleSave"
        >确 定</el-button>
        <el-button
          @click="handleCancle"
          size="small"
        >取 消</el-button>

      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getUserArrayApi } from '@/api/admin/marketManage/messagePush.js'
export default {
  name: `getUserDialog`,
  props: {
    /**
     * 作为接口传给父组件，让父组件通过管理这个变量来操作子组件
     */
    dialogVisible: {
      type: Boolean,
      default: false
    },
    userKey: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      labels: {
        id: `id`,
        userName: `昵称`,
        phone: `手机号`,
        isVisit: `是否关注公众号`
      },
      formData: {
        id: ``,
        userName: ``,
        phone: ``,
        currentPage: ``,
        pageRows: ``,
        isVisit: ``,
        userKey: null
      },
      value: null,
      options: [
        {
          label: '是否关注公众号',
          value: null
        },
        {
          label: `是`,
          value: 1,
          falg: 1
        },
        {
          label: `否`,
          value: 0,
          falg: 0
        }
      ],
      currentPage: 1, // 当前页
      totalPage: 1, // 总页数
      totalRows: 0, // 总条数
      /**
       * 表格数据
       */
      tableData: [],
      selectVal: []
    }
  },
  watch: {
    dialogVisible (newData) {
      if (newData) {
        this.fetchData()
      }
    }
    // userKey (newData, oldData) {
    //   console.log(newData, oldData)
    //   if (newData !== oldData) {
    //     this.fetchData()
    //   }
    // }
  },
  mounted () {

  },
  methods: {
    // 初始化数据
    fetchData () {
      console.log(this.userKey)
      if (!this.userKey) return
      let { id, userName, phone } = this.formData
      let params = {}
      if (this.value !== null) {
        params = {
          userKey: this.userKey,
          id: id,
          userName: userName,
          phone: phone,
          isVisit: this.value,
          currentPage: this.currentPage
        }
      } else {
        params = {
          userKey: this.userKey,
          id: id,
          userName: userName,
          phone: phone,
          currentPage: this.currentPage
        }
      }
      getUserArrayApi(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.totalPage = res.content.page.pageCount
          this.totalRows = res.content.page.totalRows
          console.log(this.tableData, this.$refs.multipleTable)
          this.$refs.multipleTable.clearSelection()
          console.log(this.tableData)
          this.tableData.forEach((item, index) => {
            if (item.isChecked) {
              this.$refs.multipleTable.toggleRowSelection(item)
            }
          })
        }
      }).catch(error => console.log(error))
    },
    // 点击搜索
    handleToSerch () {
      this.fetchData()
    },
    handleClose () {
      this.$emit('dialog-cancel')
    },
    handleCancle () {
      this.handleClose()
    },
    // 多选值改变
    handleSelectionChangeRoom (res) {
      console.log(res)
      this.selectVal = res
    },
    // 禁用设置
    selectDisableRoom (row, index) {
      console.log(row)
      if (row.canSend) {
        return true
      }
    },
    //  点击确定
    handleSave () {
      console.log(this.selectVal)
      this.$emit('handleToGet', this.selectVal)
      this.$emit('dialog-cancel')
    },
    getRowKeys (row) {
      return row.userId
    }
  }
}
</script>

<style lang="scss" scoped>
.getUserDialog {
  .title {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 5px;
  }
  .footer {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    margin-top: 10px;
  }
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
  .main {
    height: 400px;
    overflow-y: auto;
  }
  // /deep/
  //   .el-table__header
  //   .el-table-column--selection
  //   .cell
  //   .el-checkbox:after {
  //   content: null;
  // }
}
</style>
