<template>
  <div class="container">
    <div class="main">
      <div class="titleTip">
        <i
          class="el-icon-warning"
          style="color: #E6A23C;"
        ></i>
        <span>若系统开启分销员审核功能，则该文案展示在用户申请页面。或分销员可在分销中心邀请其他用户注册为分销员，此时普通用户通过邀请链接点击也是查看该推广文案。</span>
      </div>
      <div class="content">
        <div class="leftContent">
          <div class="leftName">{{ form.title }}</div>
          <img
            class="leftTitle"
            src="http://mpdevimg2.weipubao.cn/image/admin/shop_beautify/phone_tops.png"
            alt=""
          >
          <div class="leftPass"></div>
        </div>
        <div class="rightContent">
          <el-form
            ref="form"
            :model="form"
            label-width="100px"
          >
            <el-form-item label="分享地址：">
              <span>pages/distributionspread/distributionspread</span>
              <span class="text">复制</span>
              <span class="text">立即分享</span>
            </el-form-item>
            <el-form-item label="页面标题：">
              <el-input
                v-model="form.title"
                style="width: 180px;"
              ></el-input>
            </el-form-item>
            <el-form-item label="页面内容：">
              <p class="template">使用模板文案</p>
              <TinymceEditor />
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    TinymceEditor: () => import('@/components/admin/tinymceEditor/tinymceEditor') // 富文本编辑器
  },
  data () {
    return {
      form: {
        title: '分销员推广测试'
      }
    }
  },
  methods: {
  }

}

</script>
<style scoped>
.container {
  width: 100%;
  padding: 10px;
}

.main {
  background: #fff;
  padding: 10px 25px;
  overflow: hidden;
}

.titleTip {
  padding: 10px 0;
  width: 950px;
  margin: 0 auto;
  text-align: center;
  color: #666;
  font-size: 12px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
}

.content {
  width: 950px;
  margin: 0 auto;
  background: #fff;
  padding: 10px 0;
  padding-bottom: 0;
}

.leftContent {
  position: relative;
  float: left;
  width: 330px;
  height: 600px;
  border: 1px solid #ededed;
}

.leftContent .leftName {
  width: 100%;
  position: absolute;
  top: 0;
  height: 55px;
  color: #fff;
  text-align: center;
  line-height: 70px;
}

.leftContent .leftTitle {
  width: 330px;
  height: 55px;
}

.leftContent .leftPass {
  width: 330px;
  height: 540px;
  overflow-y: auto;
  padding: 0 8px;
}

.rightContent {
  float: right;
  width: 600px;
  padding: 10px 20px;
  height: 630px;
  background: #f8f8f8;
  border: 1px solid #ededed;
}

span {
  margin-right: 10px;
}

.text {
  color: #5a8bff;
  cursor: pointer;
}

.template {
  color: #5a8bff;
  margin-bottom: 5px;
  cursor: pointer;
}
</style>
