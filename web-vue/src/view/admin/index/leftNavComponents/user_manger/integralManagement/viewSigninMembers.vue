<template>
  <div class="viewSigninMembers">
    <div class="viewSigninMembersMain">
      <div class="top">
        <div>
          <span>会员信息：</span>
          <el-input
            v-model="MemberInforInput"
            style="width:170px;"
            placeholder="请输入会员昵称/手机号"
            size="small"
          ></el-input>
        </div>
        <div>
          <span>签到时间：</span>
          <el-date-picker
            size="small"
            v-model="date"
            type="daterange"
            range-separator="至"
            start-placeholder="签到时间"
            end-placeholder="签到时间"
            value-format='yyyy-MM-dd HH:mm:ss'
            :default-time="['00:00:00','23:59:59']"
          >
          </el-date-picker>
        </div>
      </div>
      <div
        class="top"
        style="margin-top:15px"
      >
        <div>
          <span>标签：</span>
          <el-select
            v-model="label"
            filterable
            placeholder="最多选择三个标签"
            size="small"
            @change="chooseLabel"
            style="width:170px;"
            >
              <el-option
                v-for="(item,index) in tagOptions"
                :key="index"
                :label="item.value"
                :value="item">
              </el-option>
            </el-select>
        </div>
        <span class="tag-add">
          <span v-for="(item,index) in chooseTag"
            :key="index">
            {{item.value}}
            <i class="el-icon-circle-close icon-style" @click="chooseTag.splice(index,1)"></i>
          </span>
        </span>
        <div class="btn">
          <el-button
            type="primary"
            size="small"
            @click="filterTableData"
          >筛选</el-button>
        </div>
      </div>
    </div>
    <div
      class="viewSigninMembersMain"
      style="margin-top:10px"
    >
      <div class="tableMain">
        <el-table
          class="version-manage-table"
          :data="tableData"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            align="center"
            label="会员昵称"
            width="200px"
          >
          <template slot-scope="scope">
              <span
                @click="handleToUserDetail(scope.row)"
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
            width="200px"
          >

          </el-table-column>
          <el-table-column
            prop="userShowTag"
            align="center"
            label="会员标签"
            width="300"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="签到时间"
            align="center"
            width="250"
          >
          </el-table-column>
          <el-table-column
            prop="usableScore"
            label="获得积分数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="continueDays"
            align="center"
          >
          <template slot="header">
            连续签到天数
             <el-tooltip effect="light" content="本此签到时间点连续签到的天数" placement="top">
                <i class="el-icon-question icon-style"></i>
            </el-tooltip>
          </template>
          </el-table-column>
          <el-table-column
            prop="totalScore"
            align="center"
          >
             <template slot="header">
              本次签到累计获得积分数
             <el-tooltip effect="light" placement="top">
               <div slot="content">本次签到累计获得的积分数，如本次连<br/>续签到天数为3，则累计获得积分数为<br/>3天签到获得的积分数之和</div>
               <i class="el-icon-question icon-style"></i>
            </el-tooltip>
          </template>
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
import { allTagRequest } from '@/api/admin/membershipList.js'
import { userScoreSign } from '@/api/admin/memberManage/scoreManage/scoreCfg.js'
export default {
  components: { Pagination: () => import('@/components/admin/pagination/pagination') },
  mounted () {
    this.initTableData()
  },
  data () {
    return {
      tagOptions: [{id: null, value: null}],
      chooseTag: [],
      MemberInforInput: '',
      date: '',
      label: null,
      tableData: [],
      pageParams: {
        totalRows: 0,
        currentPage: 1,
        pageRows: 20
      }
    }
  },
  methods: {
    // 初始化数据
    initTableData () {
      this.initTagData()
      this.loadData({})
    },
    loadData (obj) {
      userScoreSign(obj).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = {
            totalRows: res.content.page.totalRows,
            currentPage: res.content.page.currentPage,
            pageRows: res.content.page.pageRows
          }
        }
      })
    },
    // 筛选
    filterTableData () {
      let obj = {
        search: this.MemberInforInput,
        startTime: this.date ? this.date[0] : null,
        endTime: this.date ? this.date[1] : null,
        tagIds: this.chooseTag.map(r => r.id)
      }
      console.log(obj)
      this.loadData(obj)
    },
    initTagData () {
      allTagRequest().then(res => {
        if (res.error === 0) {
          this.tagOptions = res.content
        }
      })
    },
    search (data) {
      console.log(data)
    },
    // 跳转会员信息编辑页面
    handleToUserDetail (row) {
      this.$router.push({
        name: 'membershipInformation',
        query: {
          userId: row.userId
        }
      })
    },
    chooseLabel (val) {
      this.label = null
      if (this.chooseTag.length === 3) {
        return
      }
      if (!this.chooseTag.map(tag => tag.id).includes(val.id)) {
        this.chooseTag.push(val)
      }
      console.log(this.chooseTag)
    }
  }
}
</script>
<style lang="scss" scoped>
.viewSigninMembers {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .viewSigninMembersMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .top {
      display: flex;
      div {
        display: flex;
        align-items: center;
        span {
          display: block;
          width: 100px;
          text-align: right;
          margin-right: 20px;
        }
      }
      .btn {
        margin-left: 44px;
        /deep/ .el-button {
          width: 85px;
        }
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
    }
  }
  .icon-style{
    font-size: 15px;
    color: #b8bbbb;
    cursor: pointer;
  }
  .tag-add{
    width: auto;
    margin-left: 20px;
    span{
      background: #f4f4f4;
      color: #333;
      padding: 5px 10px;
      display: inline-block;
      height: auto;
      width: auto;
      line-height: 24px;
      text-align: center;
      margin-right: 10px;
      position: relative;

      i{
        position: absolute;
        right: -6px;
        top: -6px;
        cursor: pointer;
      }
      .icon-style{
        font-size: 18px;
      }
    }
  }
}
</style>
