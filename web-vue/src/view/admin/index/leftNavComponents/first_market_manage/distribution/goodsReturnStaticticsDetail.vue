<template>
  <div class="content">
    <div class="main">
      <div class="liNav">
        <span>返利订单号</span>
        <el-input
          v-model="mobile"
          size="small"
          class="ipt"
          placeholder="请输入返利订单号"
        ></el-input>
        <span>下单用户昵称</span>
        <el-input
          v-model="username"
          size="small"
          class="ipt"
          placeholder="请输入下单用户昵称"
        ></el-input>
        <span>下单用户手机号</span>
        <el-input
          v-model="mobile"
          size="small"
          class="ipt"
          placeholder="请输入下单用户手机号"
        ></el-input>
      </div>

      <div class="liNav">
        <span>分销员昵称</span>
        <el-input
          v-model="mobile"
          size="small"
          class="ipt"
          placeholder="请输入分销员昵称"
        ></el-input>
        <span>分销员手机号</span>
        <el-input
          v-model="username"
          size="small"
          class="ipt"
          placeholder="请输入分销员手机号"
        ></el-input>
        <span>分销员真实姓名</span>
        <el-input
          v-model="mobile"
          size="small"
          class="ipt"
          placeholder="请输入分销员真实姓名"
        ></el-input>
      </div>

      <div class="liNav">
        <span>返利时间</span>
        <el-date-picker
          v-model="startCreateTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        >
        </el-date-picker>
        至
        <el-date-picker
          v-model="endCreateTime"
          type="datetime"
          size="small"
          placeholder="选择日期时间"
        >
        </el-date-picker>
        <span class="labelClass">返利状态</span>
        <el-select
          size="small"
          placeholder="请选择状态"
        >
          <el-option
            v-for="item in rebateStatus"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <div class="liNav">
        <span class="labelClass">返利关系</span>
        <el-select
          size="small"
          placeholder="请选择返利关系"
        >
          <el-option
            v-for="item in rebateLevel"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-button
          @click="inviteList"
          type="primary"
          size="small"
        >筛选</el-button>
        <el-button size="small">导出</el-button>
      </div>
    </div>
    <div class="main list_content">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="username"
          label="商品名称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="商品数量"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="返利商品金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="商品订单号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="下单用户昵称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="下单用户手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="返利关系"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="orderNumber"
          label="分销员昵称"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="totalCanFanliMoney"
          label="分销员真实姓名"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="分销员手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="返利比例"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="商品返利佣金金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="返利状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="inviteExpiryDate"
          label="返利日期"
          align="center"
        >
        </el-table-column>
      </el-table>
    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="inviteList"
    />
  </div>
</template>
<script>
import { inviteUserList } from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      tableData: [],
      pageParams: {},
      rebateStatus: [{
        value: '选项1',
        label: '全部'
      }, {
        value: '选项2',
        label: '已返利'
      }, {
        value: '选项3',
        label: '待返利'
      }, {
        value: '选项3',
        label: '不返利'
      }],
      rebateLevel: [{
        value: '选项1',
        label: '全部'
      }, {
        value: '选项2',
        label: '自购返利'
      }, {
        value: '选项3',
        label: '直间返利'
      }, {
        value: '选项3',
        label: '间接返利'
      }]
    }
  },
  mounted () {
    if (this.$route.query.userId > 0) {
      this.userId = this.$route.query.userId
      this.inviteList()
    }
  },
  methods: {
    inviteList () {
      this.pageParams.userId = this.userId
      this.pageParams.mobile = this.mobile
      this.pageParams.username = this.username
      this.pageParams.startCreateTime = this.startCreateTime
      this.pageParams.endCreateTime = this.endCreateTime
      inviteUserList(this.pageParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
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
    .liNav {
      margin-top: 5px;
      margin-bottom: 15px;
    }

    .ipt {
      width: 200px;
      margin-right: 30px;
    }
  }
}
.list_content {
  margin-top: 10px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
