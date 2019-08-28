<template>
  <div class="content">
    <el-row class="main">
      <el-col :span="4">
        <el-form label-width="100px">
          <el-form-item label="手机号">
            <el-input
              v-model="param.mobile"
              placeholder="请输入手机号"
            ></el-input>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="4">
        <el-form label-width="100px">
          <el-form-item label="用户昵称">
            <el-input
              v-model="param.username"
              placeholder="请输入昵称"
            ></el-input>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="4">
        <el-form label-width="100px">
          <el-form-item label="商品名称">
            <el-input
              v-model="param.goodsName"
              placeholder="请输入商品名称"
            ></el-input>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="4">
        <el-form label-width="100px">
          <el-form-item label="奖励等级">
            <template>
              <el-select
                v-model="param.rewardLevel"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </template>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col
        :span="3"
        :offset="3"
      >
        <el-form label-width="100px">
          <el-button
            type="primary"
            @click="searchByCondition"
          >查询</el-button>
        </el-form>
      </el-col>
    </el-row>

    <el-row class="table_list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="userId"
          label="用户id"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户昵称"
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
          prop="goodsName"
          label="分享商品"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="awardLevel"
          label="奖励级别"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="inviteUserNum"
          label="邀请用户总数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="inviteNewUserNum"
          label="邀请新用户数"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="rewardType"
          label="活动奖励"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="领取时间"
          align="center"
        >
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
      <el-col
        :offset="14"
        :span="10"
      >
        <pagination
          :page-params.sync="pageParams"
          @pagination="defaultSeach"
        />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getReceiveDetail } from '@/api/admin/marketManage/sharePolite.js'
import pagination from '@/components/admin/pagination/pagination.vue'
export default {
  components: {
    pagination
  },
  data () {
    return {
      tableData: [],
      param: {
        shareId: this.$route.params.id,
        mobile: '',
        username: '',
        goodsName: '',
        rewardLevel: '',
        currentPage: 1,
        pageRows: 20
      },
      pageParams: {},
      options: [{
        value: 1,
        label: '一级'
      }, {
        value: 2,
        label: '二级'
      }, {
        value: 3,
        label: '三级'
      }]
    }
  },
  mounted () {
    this.langDefault()
    // 页面加载时调用接口初始化数据
    this.defaultSeach()
  },
  methods: {
    defaultSeach () {
      let obj = {
        'shareId': this.$route.params.id,
        'currentPage ': this.pageParams.currentPage,
        'pageRows ': this.pageParams.pageRows
      }
      getReceiveDetail(obj).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    created () {
      console.log(this.$router.params)
      if (this.$router.params.flag === true) {
        this.shareId = this.$router.params.id
      }
    },
    // 条件查询
    searchByCondition () {
      console.log(this.param)
      getReceiveDetail(this.param).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.handleData(res.content.dataList)
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      //   数组遍历jsv
      data.map((item, index) => {
        switch (item.rewardType) {
          case 1:
            item.rewardType = '积分'
            break
          case 2:
            item.rewardType = '优惠券'
            break
          case 3:
            item.rewardType = '幸运大转盘'
            break
          default:
            item.rewardType = ''
            break
        }
      })
      this.tableData = data
    }
  }
}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
}
.condition {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.p_top_right {
  display: flex;
  /deep/ .el-button {
    padding: none;
    height: 32px;
  }
  span {
    white-space: nowrap;
    height: 32px;
    line-height: 32px;
    margin-right: 10px;
  }
  .topRightDiv {
    &:nth-of-type(2) {
      margin: 0 10px 0 30px;
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
.balanceDialo .el-dialog__body {
  padding-bottom: 0 !important;
}
.balanceDialo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.setUpDialog .el-dialog__body {
  padding-top: 10px !important;
}
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
