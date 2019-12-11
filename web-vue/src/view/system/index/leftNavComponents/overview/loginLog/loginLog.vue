<template>
  <div class="experience-version">
    <div class="select-menu top">
      <div class='options'>
        <span>店铺ID：</span>
        <el-input
          v-model="param.shopId"
          placeholder="请输入内容"
          size="small"
          style="width:150px;"
        ></el-input>
      </div>
      <div class='options'>
        <span>店铺名称：</span>
        <el-input
          v-model="param.shopName"
          placeholder="请输入内容"
          size="small"
          style="width:150px;"
        ></el-input>
      </div>
      <div class='options'>
        <span>账户ID：</span>
        <el-input
          v-model="param.userId"
          placeholder="请输入内容"
          size="small"
          style="width:150px;"
        ></el-input>
      </div>
      <div class='options'>
        <span>账户名称：</span>
        <el-input
          v-model="param.userName"
          placeholder="请输入内容"
          size="small"
          style="width:150px;"
        ></el-input>
      </div>
    </div>
    <div class='select-menu2'>
      <div class='options'>
        <span>登录时间：</span>
        <el-date-picker
          v-model="param.startAddTime"
          type="datetime"
          placeholder="选择日期时间"
          size="small"
          style="width:200px;"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
        至
        <el-date-picker
          v-model="param.endAddTime"
          type="datetime"
          placeholder="选择日期时间"
          size="small"
          style="width:200px;"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </div>
      <div class='options'>
        <span>账户类型：</span>
        <el-select
          v-model="param.accountType"
          placeholder="请选择"
          size="small"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-button
          type="primary"
          size="mini"
          @click="list()"
        >筛选</el-button>
      </div>
    </div>
    <div class='select-menu'>
      <el-table
        class="experience-log mt-10 param"
        header-row-class-name="table-th"
        :data='tableData'
        border
        :style="{maxWidth:'100%',width: '100%',height:tableHeight+ 'px',overflowY:'auto'}"
      >
        <el-table-column
          prop="sysId"
          label="账号ID"
          align="center"
          style="width:10px;"
        >
        </el-table-column>
        <el-table-column
          prop="userId"
          label="用户ID"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="userName"
          label="账号名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="accountType"
          label="账户类型"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="shopId"
          label="店铺ID"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="shopName"
          label="店铺名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="nickName"
          label="小程序名称"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="userIp"
          label="IP"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="userName"
          label="所在地区"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="addTime"
          label="登录时间"
          align="center"
        >
        </el-table-column>
      </el-table>

    </div>
    <div class='page'>
      <pagination
        :page-params.sync="pageParams"
        @pagination="list"
      />
    </div>

  </div>
</template>
<script>
import pagination from '@/components/system/pagination/pagination.vue'
import { loginLog } from '@/api/system/overView/loginLog.js'
export default {
  components: {
    pagination
  },
  data () {
    return {
      param: {
        shopId: '',
        shopName: '',
        userId: '',
        userName: '',
        startAddTime: '',
        endAddTime: '',
        accountType: ''
      },
      tableData: [],
      options: [{
        value: '',
        label: '全部'
      }, {
        value: '0',
        label: '商家账号'
      }, {
        value: '1',
        label: '系统管理员账号'
      }],
      value: '',
      pageParams: {},
      tableHeight: document.documentElement.clientHeight - 330
    }
  },
  mounted () {
    this.list()
  },
  methods: {
    list () {
      this.param.currentPage = this.pageParams.currentPage
      this.param.pageRows = this.pageParams.pageRows
      loginLog(this.param).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.tableData.map((item, index) => {
            if (item.accountType === 0) {
              item.accountType = '商家账号'
            } else {
              item.accountType = '系统管理员账号'
            }
          })
          this.pageParams = res.content.page
        }
      })
    }
  }

}
</script>

<style lang="scss" scoped>
.experience-version {
  height: 100%;
}
.select-menu {
  display: flex;
  padding: 10px;
  background: #fff;
  margin-top: 10px;
}
.select-input {
  width: 200px;
}
.footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.footer > span {
  font-size: 14px;
}
.options {
  font-size: 14px;
  margin-left: 10px;
  margin-right: 40px;
}
.select-menu2 {
  display: flex;
  padding: 10px;
  background: #fff;
}
.page {
  // margin-bottom: 10px;
  margin-top: -20px;
}
</style>
