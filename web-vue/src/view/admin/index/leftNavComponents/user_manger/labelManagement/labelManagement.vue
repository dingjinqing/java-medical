<template>
  <div class="labelManagement">
    <div class="labelManagementMain">
      <div class="top">
        <el-button
          type="primary"
          size="small"
          @click="handleToButton(0)"
        >新建标签</el-button>
        <el-input
          v-model="labelInput"
          placeholder="请输入标签查询"
          size="small"
          suffix-icon="el-icon-search"
        ></el-input>
        <el-button
          type="primary"
          size="small"
          @click="handleToButton(1)"
        >查询</el-button>
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
            prop="sickName"
            align="center"
            label="标签名"
          >
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="userID"
          >
          </el-table-column>
          <el-table-column
            prop="phoneNum"
            label="用户数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <span
                  v-for="(item,index) in operation"
                  :key="index"
                  @click="handleToOperation(scope.row,index)"
                >{{item}}</span>
              </div>
            </template>
          </el-table-column>

        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="search"
        />
      </div>
    </div>
    <!--新建标签弹窗-->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="labelDialog">
        <div style="margin-bottom:10px">标签名称</div>
        <el-input
          v-model="labelDialogInput"
          placeholder="请输入内容"
          size="small"
        ></el-input>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--删除弹窗-->
    <el-dialog
      title="提醒"
      :visible.sync="dialogDelVisible"
      width="30%"
    >
      <span>删除此标签，已有此标签的用户将失去该标签，是否确认删除?</span>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogDelVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogDelVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      dialogDelVisible: false,
      dialogTitle: '',
      dialogVisible: false,
      labelInput: '',
      tableData: [
        {
          userID: '51',
          phoneNum: '18811309193',
          sickName: '啦啦啦',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        },
        {
          userID: '12',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        },
        {
          userID: '43',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常'
        }
      ],
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 10
      },
      operation: ['编辑', '删除', '查看用户'],
      labelDialogInput: ''
    }
  },
  methods: {
    search (data) {
      console.log(data)
    },
    // 操作部分点击
    handleToOperation (row, index) {
      console.log(row, index)
      switch (index) {
        case 0:
          this.dialogTitle = '编辑标签'
          this.labelDialogInput = row.sickName
          this.dialogVisible = true
          break
        case 1:
          this.dialogDelVisible = true
          console.log(row)
          break
        case 2:
          this.$router.push({
            name: 'user_list'
          })
      }
    },
    // 顶部按钮点击
    handleToButton (flag) {
      switch (flag) {
        case 0:
          this.labelDialogInput = ''
          this.dialogTitle = '新建标签'
          this.dialogVisible = true
          break
        case 1:
          break
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
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .top {
      margin-bottom: 20px;
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
      margin-top: 10px;
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
