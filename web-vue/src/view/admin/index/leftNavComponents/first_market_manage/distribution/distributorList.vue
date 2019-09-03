<template>
  <div class="distributorListContent">
    <div class="searchInfo_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <span class="labelClass">手机号</span>
            <el-input
              v-model="param.mobile"
              placeholder="请填写手机号"
              size="small"
            ></el-input>
          </div>
          <div
            class="liNav"
            style="margin: 0 100px"
          >
            <span class="labelClass">微信昵称</span>
            <el-input
              v-model="param.username"
              placeholder="请填写微信昵称"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span class="labelClass">真实姓名</span>
            <el-input
              v-model="param.realName"
              placeholder="请填写真实姓名"
              size="small"
            >
            </el-input>
          </div>
        </li>
      </ul>
      <ul class="uls">
        <li class="li">
          <div class="liNav">
            <span class="labelClass invitationPhone">被邀请用户手机号</span>
            <el-input
              placeholder="请填写被邀请用户手机号"
              size="small"
            >
            </el-input>
          </div>
        </li>
        <li
          class="li"
          style="margin-left: 20px"
        >
          <div class="liNav timeLine">
            <span class="labelClass date">注册时间</span>
            <el-date-picker
              v-model="param.startCreateTime"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>
            <span>至</span>
            <el-date-picker
              v-model="param.endCreateTime"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>

          </div>
        </li>
      </ul>
      <ul class="uls">
        <li class="li">
          <div class="liNav">
            <span class="labelClass invitation">被邀请用户昵称</span>
            <el-input
              v-model="param.invitedUsername"
              placeholder="请填写被邀请用户昵称"
              size="small"
            >
            </el-input>
          </div>
        </li>
        <li class="li">
          <div
            class="liNav"
            style="margin: 0 100px"
          >
            <span class="labelClass">分销员等级</span>
            <el-select
              size="small"
              v-model="value"
              placeholder="请选择等级"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </li>
        <li class="li">
          <div class="liNav">
            <span class="labelClass">分销员分组</span>
            <el-select
              size="small"
              v-model="value"
              placeholder="请选择分组"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </li>
        <li
          class="li"
          style="margin: 0 10px 0 50px"
        >
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </li>
        <li class="li">
          <el-button size="small">导出</el-button>
        </li>
      </ul>
    </div>
    <div class="tableInfo">
      <div class="notice">
        <span>注：</span>
        <span>未开启分销员审核时，列表只展示有下级用户的分销员</span>
      </div>
      <div class="table_list">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          border
          :data="tableData"
          style="width: 100%"
        >
          <el-table-column
            prop="userId"
            label="ID"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="username"
            label="分销员昵称"
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
            prop="createTime"
            label="注册时间"
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
            prop="groupName"
            label="分销员分组"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="levelName"
            label="分销员等级"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="nextNumber"
            label="下级用户数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="sublayerNumber"
            label="间接邀请用户数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="totalCanFanliMoney"
            label="累计返利商品总额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="totalFanliMoney"
            label="累计获得佣金金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="waitFanliMoney"
            label="待返利佣金金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="操作"
            align="center"
          >
            <template>
              <div class="opt">
                <p>查看已邀请用户</p>
                <p>查看返利佣金明细</p>
                <p>清除</p>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        :page-params.sync="pageParams"
        @pagination="list"
      />
    </div>
  </div>
</template>

<script>
import { distributorList, distributorLevelList, distributorGroupList } from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'

export default {
  components: { pagination },
  data () {
    return {
      options: [{
        value: '选项1',
        label: 'levelName'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value: '',
      time1: '',
      time2: '',
      tableData: [],
      currentPage: 1,
      pageParams: {},
      param: {

      }
    }
  },
  mounted () {
    this.list()
    this.levelList()
    this.groupList()
  },

  methods: {
    handleCurrentChange () {

    },
    list () {
      distributorList(this.pageParams).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    levelList () {
      distributorLevelList().then(res => {
        console.log(res)
      })
    },
    groupList () {
      distributorGroupList().then(res => {
        console.log(res)
      })
    }

  }
}

</script>
<style scoped>
.distributorListContent {
  padding: 10px;
  padding-bottom: 68px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.searchInfo_main {
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
}
.li {
  padding: 10px 0;
  display: flex;
}
.liNav {
  width: 280px;
  display: flex;
}
.liNav span {
  display: block;
  width: 80px;
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
}
.labelClass {
  width: 100px !important;
}
.invitation {
  width: 200px !important;
}
.invitationPhone {
  width: 250px !important;
}
.timeLine {
  width: 500px !important;
}
.uls {
  margin-top: 10px;
  display: flex;
}
.uls span {
  width: 56px;
}
.date {
  width: 350px !important;
}
.tableInfo {
  margin-top: 30px;
}
.notice {
  margin-bottom: 20px;
}
.notice :first-child {
  color: red;
}
.tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
  background-color: #fff;
  padding: 0px 20px 10px 20px;
}
.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
}
.footer > span {
  display: block;
  height: 32px;
  line-height: 32px;
}
.opt {
  text-align: center;
  color: #5a8bff;
}
</style>
