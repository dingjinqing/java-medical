<template>
  <!-- 店铺基础信息 -->
  <el-container>
    <el-main class="patientConfig">
      <ul class="program_details">
        <li class="details_item">
          <span class="item_label">患者姓名：</span>
          <div class="item_content">
            <span>张天师</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">患者编号：</span>
          <div class="item_content">
            <span>372828828282</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">手机号：</span>
          <div class="item_content">
            <span>18822223333</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">性别：</span>
          <div class="item_content">
            <span>男</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">患者姓名：</span>
          <div class="item_content">
            <span>张天师</span>
          </div>
        </li>
        <li class="details_item" style="margin:3px 0">
          <span class="item_label">证件类型：</span>
          <div class="item_content">
          <span>身份证</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">证件号：</span>
          <div class="item_content">
            <span>12312323</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">生日：</span>
          <div class="item_content">
            <span>2020-08-09</span>
          </div>
        </li>
      </ul>
    </el-main>

  </el-container>
</template>

<script>
export default {
  name: 'patienMessage',
  data () {
    return {
      data: {
        shopName: '',
        businessState: 0, // 0营业 1未营业
        created: '',
        expireTime: '', // 店铺到期时间
        showLogo: 0, // 是否显示小程序店铺logo
        logoLink: '' // 小程序端店铺链接
      }
    }
  },
  created () {
    this.langDefault()
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped>
.patientConfig {
  padding: 0;
  background: #fff;
  margin-bottom: 50px;
  .title {
    font-weight: 800;
    font-size: 16px;
    margin-bottom: 10px;
  }
  .program_details {
    padding-left: 10px;
  }
}
.details_item {
  display: flex;
  line-height: 32px;
  font-size: 13px;
  .item_label {
    height: 32px;
    width: 70px;
    text-align: right;
    margin-right: 10px;
  }
  .item_content {
    flex: 1;
  }
  .applet_logo {
    display: flex;
    margin: 15px 0;
  }
}
</style>
