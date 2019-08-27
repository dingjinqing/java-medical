<template>
  <div class="distributorListContent">
    <div class="searchInfo_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <span class="labelClass">手机号</span>
            <el-input
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
              placeholder="请填写微信昵称"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span class="labelClass">真实姓名</span>
            <el-input
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
              v-model="time1"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>
            <span>至</span>
            <el-date-picker
              v-model="time2"
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
          style="width: 100%"
        >
          <el-table-column
            prop="title"
            label="推广语标签"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="promotionLanguage"
            label="推广语内容"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            label="创建时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="updateTime"
            label="更新时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="isBlock"
            label="状态"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="操作"
            align="center"
          >

          </el-table-column>
        </el-table>
        <div class="footer">
          <span>当前页面1/1，总记录4条</span>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="4"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      options: [{
        value: '选项1',
        label: '黄金糕'
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
      currentPage: 1
    }
  },
  methods: {
    handleCurrentChange () { }
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
</style>
