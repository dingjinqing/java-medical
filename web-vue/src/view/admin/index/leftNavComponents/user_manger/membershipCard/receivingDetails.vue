<template>
  <div class="receivingDetails">
    <div class="receivingDetailsMain">
      <div class="topDiv">
        <div>
          <span>手机号</span>
          <el-input
            v-model="phoneNumInput"
            placeholder="请输入手机号码"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>用户昵称</span>
          <el-input
            v-model="carNameInput"
            placeholder="请输入用户昵称"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>批次名称</span>
          <el-select
            v-model="selectSortvalue"
            placeholder="请选择"
            size="small"
          >
            <el-option
              v-for="item in selectSortOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
        <div style="margin-left:20px">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
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
            prop="userID"
            align="center"
            label="批次名称"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="userID"
            align="center"
            label="ID"
            width="120"
          >
          </el-table-column>
          <el-table-column
            label="用户昵称"
            align="center"
          >
            <template slot-scope="scope">
              <span
                @click="handleToUserDetail(scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >
                {{scope.row.sickName}}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="phoneNum"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="inviter"
            label="领取时间"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="领取码"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <span
                style="cursor:pointer;color:#5a8bff"
                @click="handleToOperation(scope.row)"
              >废除</span>
            </template>
          </el-table-column>

        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="search"
        />
      </div>
    </div>
    <!--废除提示框-->
    <el-dialog
      title='提醒'
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div>确认要废除?</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToSuer()"
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
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 10
      },
      dialogVisible: false,
      phoneNumInput: '',
      carNameInput: '',
      sortNameInput: '',
      selectSortvalue: '0',
      selectSortOptions: [{
        value: '0',
        label: '请选择批次名称'
      }, {
        value: '1',
        label: 'test2'
      }],
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
      userID: ''
    }
  },
  methods: {
    search (data) {
      console.log(data)
    },
    // 用户昵称点击
    handleToUserDetail (row) {
      this.$router.push({
        name: 'membershipInformation'
      })
    },
    // 点击废除
    handleToOperation (row) {
      console.log(row)
      this.userID = row.userID
      this.dialogVisible = true
    },
    // 废除弹窗确认事件
    handleToSuer () {
      console.log(this.userID)
    }
  }
}
</script>
<style lang="scss" scoped>
.receivingDetails {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .receivingDetailsMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .topDiv {
      display: flex;
      margin-bottom: 20px;
      div {
        /deep/ .el-input {
          width: 140px;
        }
        span {
          white-space: nowrap;
          display: inline-block;
          width: 80px;
          text-align: right;
          margin-right: 20px;
        }
        display: flex;
        align-items: center;
        /deep/ .el-button {
          width: 85px;
        }
      }
    }
  }
}
</style>
