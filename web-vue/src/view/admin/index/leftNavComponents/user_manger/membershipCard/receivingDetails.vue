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
              v-for="(item,index) in selectSortOptions"
              :key="index"
              :label="item.name"
              :value="item.batchId"
            >
            </el-option>
          </el-select>
        </div>
        <div style="margin-left:20px">
          <el-button
            type="primary"
            size="small"
            @click="filterData"
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
            prop="name"
            align="center"
            label="批次名称"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="id"
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
                @click="handleToUserDetail(scope.row.userId)"
                style="cursor:pointer;color:#5a8bff"
              >
                {{scope.row.username}}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="receiveTime"
            label="领取时间"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="领取码 / 卡号+密码"
            align="center"
          >
            <template slot-scope="scope">
              <span style="cursor:pointer;color:#5a8bff">
                <ul class="code-list">
                  <span v-if="scope.row.code">
                      <li>{{scope.row.code}}</li>
                  </span>
                  <span v-else>
                    <li>{{scope.row.cardNo}}</li>
                    <li>{{scope.row.cardPwd}}</li>
                  </span>
                </ul>
              </span>
            </template>

          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >

            <template slot-scope="scope">
              <span v-if="scope.row.delFlag===1">
                已废除
              </span>
              <span
                v-else
                style="cursor:pointer;color:#5a8bff"
                @click="handleToOperation(scope.row.id)"
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
import { getReceiveListRequest, getgetCardBatchListRequest, deleteCardBatchRequest } from '@/api/admin/memberManage/memberCard.js'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  data () {
    return {
      pageParams: {
        totalRows: 10,
        currentPage: 1,
        pageRows: 20
      },
      cardId: null, // 会员卡id
      dialogVisible: false,
      phoneNumInput: '',
      carNameInput: '',
      sortNameInput: '',
      selectSortvalue: 0,
      selectSortOptions: [
        {
          batchId: 0,
          name: '全部'
        }
      ],
      tableData: [],
      discardId: null
    }
  },
  created () {
    this.cardId = this.$route.query.cardId
    this.loadAllDefaultData()
  },
  watch: {
    lang () {
      alert('语言切换')
    }
  },
  methods: {
    // 1- 加载数据
    loadAllDefaultData () {
      this.loadAllTableData()
      // 批次数据
      this.getgetCardBatchList()
    },
    // 2- 加载列表数据
    loadAllTableData () {
      let obj = {
        'pageRows': this.pageParams.pageRows,
        'currentPage': this.pageParams.currentPage,
        'cardId': this.cardId,
        'mobile': this.phoneNumInput,
        'username': this.carNameInput,
        'batchId': this.selectSortvalue
      }
      console.log(obj)
      // 会卡领取详情-查询
      this.getReceiveList(obj)
    },

    // 2- 会卡领取详情-查询
    getReceiveList (obj) {
      getReceiveListRequest(obj).then(res => {
        if (res.error === 0) {
          // 设置分页
          this.pageParams = res.content.page
          // 设置数据
          this.tableData = res.content.dataList
          console.log(this.tableData)
        }
      })
    },

    // 3- 获取会员卡领取批次
    getgetCardBatchList () {
      getgetCardBatchListRequest(this.cardId).then(res => {
        if (res.error === 0) {
          this.selectSortOptions.push(...res.content)
        }
      })
    },

    // 4- 筛选
    filterData () {
      this.loadAllTableData()
    },
    search (data) {
      console.log(data)
    },
    // 用户昵称点击
    handleToUserDetail (userId) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId
        }
      })
    },
    // 点击废除
    handleToOperation (id) {
      console.log(id)
      this.discardId = id
      this.dialogVisible = true
    },
    // 废除弹窗确认事件
    handleToSuer () {
      // api调用
      deleteCardBatchRequest(this.discardId).then(res => {
        if (res.error === 0) {
          console.log('discard success')
          // 重新加载数据
          this.loadAllTableData()
        }
      })
      this.dialogVisible = false
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
