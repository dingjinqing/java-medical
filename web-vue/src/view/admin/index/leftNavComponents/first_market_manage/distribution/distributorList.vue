<template>
  <div class="distributorListContent">
    <div class="searchInfo_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <span class="phoneClass">手机号</span>
            <el-input
              placeholder="请填写手机号"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
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
        <li class="li">
          <div class="liNav">
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
          <div class="liNav">
            <span class="labelClass">分销员等级</span>
            <el-select
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
        <li>
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </li>
        <li>
          <el-button size="small">导出</el-button>
        </li>
      </ul>
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
      time2: ''
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
.uls {
  margin-top: 10px;
  display: flex;
}
.uls span {
  width: 56px;
}
.date .el-input__inner {
  width: 350px !important;
}
</style>
