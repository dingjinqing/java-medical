<template>
  <div class="viewOrders">
    <div class="viewOrdersMain">
      <div class="topDiv">
        <div>
          <span>订单号：</span>
          <el-input
            v-model="orderNumInput"
            size="small"
            placeholder="请输入订单号"
          ></el-input>
        </div>
        <div>
          <span>会员昵称：</span>
          <el-input
            v-model="userNameInput"
            placeholder="请输入会员昵称"
            size="small"
          ></el-input>
        </div>
        <div style="margin-right:20px">
          <span>手机号：</span>
          <el-input
            v-model="phoneNumInput"
            placeholder="请输入会员手机号"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>次数使用类型</span>
          <el-select
            v-model="selectValue"
            size="small"
          >
            <el-option
              v-for="item in selectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>

      </div>
      <div class="topDiv middle">
        <div>
          <span>次数变动时间：</span>
          <el-date-picker
            size="small"
            v-model="dateValue"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format='yyyy-MM-dd'
          >
          </el-date-picker>
        </div>
        <div style="margin-left:20px">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button
            type="info"
            plain
            size="small"
          >导出表格</el-button>
        </div>
      </div>
    </div>
    <div class="viewOrdersMain">
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
            label="单号"
          >
            <template slot-scope="scope">

              <span
                @click="hanldeToClick(0,scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >{{scope.row.userID}}</span>

            </template>
          </el-table-column>
          <el-table-column
            prop="userID"
            label="核销内容"
            align="center"
          >
            <template slot-scope="scope">
              <div class="content">
                <img :src="scope.row.imgUrl">
                <span>{{scope.row.userID}}</span>
              </div>

            </template>
          </el-table-column>
          <el-table-column
            prop="inviter"
            label="会员昵称"
            align="center"
          >
            <template slot-scope="scope">
              <span
                @click="hanldeToClick(1,scope.row)"
                style="cursor:pointer;color:#5a8bff"
              >{{scope.row.inviter}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="phoneNum"
            label="手机号"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="变动次数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="cardNum"
            label="次数使用类型"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="status"
            label="次数变动时间"
            align="center"
          >
          </el-table-column>
        </el-table>
        <Pagination
          :page-params.sync="pageParams"
          @pagination="search"
        />
      </div>
    </div>
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
      orderNumInput: '',
      userNameInput: '',
      phoneNumInput: '',
      selectValue: '0',
      selectOptions: [{
        value: '0',
        label: '请选择'
      }, {
        value: '1',
        label: '兑换商品'
      }, {
        value: '2',
        label: '门店服务'
      }],
      dateValue: '',
      tableData: [
        {
          userID: '51',
          phoneNum: '18811309193',
          sickName: '啦啦啦',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常',
          imgUrl: this.$imageHost + '/image/admin/first_5.png'
        },
        {
          userID: '12',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常',
          imgUrl: this.$imageHost + '/image/admin/first_5.png'
        },
        {
          userID: '43',
          sickName: '啦啦啦',
          phoneNum: '18811309193',
          inviter: '帅飞',
          date: '20190828 14:40:44',
          cardNum: '2342342334235',
          status: '正常',
          imgUrl: this.$imageHost + '/image/admin/first_5.png'
        }
      ]
    }
  },
  methods: {
    search (data) {
      console.log(data)
    },
    // 表格中单号和会员昵称点击
    hanldeToClick (flag, row) {
      switch (flag) {
        case 0:
          break
        case 1:
          this.$router.push({
            name: 'membershipInformation'
          })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.viewOrders {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .viewOrdersMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px 10px;
    margin-bottom: 10px;
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
    .middle {
      display: flex;
      justify-content: space-between;
      & div:nth-of-type(2) {
        display: flex;
      }
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
      .content {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        img {
          width: 68px;
          height: 68px;
        }
      }
    }
  }
}
</style>
