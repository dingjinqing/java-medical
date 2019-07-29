<template>
  <div class="brandManagementContent">
    <div class="brandManagementContent_main">
      <ul>
        <li class="li">
          <div class="liNav">
            <span>手机号</span>
            <el-input
              v-model="phoneNum"
              placeholder="请输入手机号"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span>微信昵称</span>
            <el-input
              v-model="vxName"
              placeholder="请输入微信昵称"
              size="small"
            ></el-input>
          </div>
          <div class="liNav">
            <span>来源</span>
            <el-select
              v-model="sourceValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in sourceOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>会员卡</span>
            <el-select
              v-model="membershipCardVal"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in membershipCardOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </li>
      </ul>
      <ul class="uls">
        <li>
          <div class="liNav">
            <span>标签</span>
            <el-input
              v-model="labelVal"
              placeholder="请输入标签"
              size="small"
            ></el-input>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      phoneNum: '',
      vxName: '',
      sourceOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      sourceValue: '',
      membershipCardOptions: [{
        value: '选项1',
        label: '会员1'
      }, {
        value: '选项2',
        label: '会员2'
      }, {
        value: '选项3',
        label: '会员3'
      }],
      membershipCardVal: '',
      labelVal: ''
    }
  }

}
</script>
<style scoped>
.brandManagementContent {
  padding: 10px;
  padding-right: 23px;
  min-width: 1400px;
  font-size: 14px;
  /* height: 100%; */
  position: relative;
}
.brandManagementContent_main {
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  overflow-y: auto;
  padding: 15px 25px;
}
.li {
  padding: 8px 0;
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
.uls {
  margin-top: 10px;
}
</style>
<style>
.liNav .el-input__inner {
  width: 150px !important;
}
</style>
