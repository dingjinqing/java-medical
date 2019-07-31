<template>
  <div class="outside">
    <div class="content">
      <div class="name">
        <span>系统管理后台</span>
        <span>/</span>
        <span>店铺列表</span>
      </div>

      <div class="nav">
        <div
          class="nav_left"
          :class="{'title-active':!isSubLogin}"
          @click="switchTab(false)"
        >
          {{title[0]}}
        </div>
        <div
          class="nav_right"
          :class="{'title-active':isSubLogin}"
          @click="switchTab(true)"
        >
          {{title[1]}}
        </div>
      </div>

      <div class="wrapper">
        <div
          class="nav_content"
          v-show="!isSubLogin"
        >
          <div class="nav_content_panel">
            <input
              type="text"
              placeholder="账号ID、公司"
            >
            <input
              type="text"
              placeholder="店铺ID、账户名称、手机号、小程序名"
            >
            <select class="select_first">
              <option value="input">选择店铺状态</option>
              <option value="input">使用中</option>
              <option value="input">即将过期</option>
              <option value="input">已过期</option>
            </select>
            <select class="select_second">
              <option value="input">选择店铺类型</option>
              <option value="input">基础版</option>
              <option value="input">高级版</option>
              <option value="input">旗舰版</option>
            </select>
            <select class="select_third">
              <option value="input">选择店铺标识</option>
              <option value="input">店+</option>
              <option value="input">欧派</option>
              <option value="input">寺库</option>
            </select>
          </div>
          <div class="nav_content_panel ">
            <select class="select_first">
              <option value="input">选择禁用状态</option>
              <option value="input">已禁用</option>
              <option value="input">未禁用</option>
            </select>
            <select class="select_second">
              <option value="input">底部导航</option>
              <option value="input">隐藏</option>
              <option value="input">显示</option>
            </select>
            <div class="time_limit">
              <div class="start_time">店铺到期时间</div>
              <input type="text">
              <div class="end_time">至</div>
              <input type="text">
            </div>
            <button>搜索</button>
          </div>
        </div>

        <div v-show="isSubLogin">我是付费版页面内容</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isSubLogin: false,
      title: ['体验版', '付费版']
    }
  },
  methods: {
    switchTab (subLogin) {
      this.isSubLogin = subLogin
    }
  }
}
</script>
<style scoped>
.outside {
  background: #e6e9f0;
  overflow-y: scroll;
  overflow-x: scroll;
}
.content {
  width: 1120px;
  margin: 10px 5px;
  overflow-y: scroll;
  overflow-x: hidden;
}
.name {
  background: #f5f5f5;
  border-radius: 2px;
  height: 34px;
  line-height: 34px;
  margin-bottom: 18px;
}
.name span {
  margin-right: 5px;
  font-size: 14px;
  line-height: 24px;
  height: 24px;
  color: #999;
}
.name span:nth-child(1) {
  color: #333;
  margin-left: 15px;
}
.nav {
  display: flex;
  width: 100%;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: 14px;
  background: #eef1f6;
  /* border-bottom: 1px solid #ddd; */
}
.nav_left {
  width: 106px;
}
.nav_right {
  width: 106px;
}
.nav_content {
  border: 1px solid #000;
  width: 100%;
  padding: 15px;
  margin: 8px 0;
  background: #fff;
  padding: 15px;
}
.nav_content_panel {
  padding: 15px;
  display: flex;
}
.nav_content_panel > input,
select {
  height: 30px;
  padding-left: 12px;
  border: 1px solid #ccc;
  vertical-align: middle;
  margin-right: 20px;
}
.nav_content_panel > .time_limit {
  display: flex;
  font-size: 14px;
}
.time_limit > div {
  line-height: 30px;
}
.start_time {
  margin-right: 10px;
}
.end_time {
  margin: 0 10px;
}
.nav_content_panel > button {
  width: 60px;
  height: 30px;
  margin-left: 15px;
  border: 1px solid #86a7cb;
  border-color: #ccc;
  background: #86a7cb;
  color: #fff;
}
.title-active {
  font-weight: 700;
  border: 1px solid #ddd;
  border-bottom-color: transparent;
  background: #fff;
  border-top: 2px solid green;
  cursor: pointer;
}
</style>
