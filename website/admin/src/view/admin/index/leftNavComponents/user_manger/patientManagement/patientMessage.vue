<template>
  <!-- 店铺基础信息 -->
  <el-container>
    <el-main class="patientConfig">
      <!-- <ul class="program_details">
        <li class="details_item">
          <span class="item_label">患者姓名：</span>
          <div class="item_content">
            <span>{{data.name}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">患者编号：</span>
          <div class="item_content">
            <span>{{data.id}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">手机号：</span>
          <div class="item_content">
            <span>{{data.mobile}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">性别：</span>
          <div class="item_content">
            <span>{{data.sex == 0 ? '男' : (data.sex == 1 ? '女' : '未知')}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">证件类型：</span>
          <div class="item_content">
            <span>{{data.identityType == 1 ? '身份证' : (data.identityType == 1 ? '军人证' : ( data.identityType == 3 ?  '护照' : '社保卡'))}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">证件号：</span>
          <div class="item_content">
            <span>{{data.identityCode}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">生日：</span>
          <div class="item_content">
            <span>{{data.birthday}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">疾病史：</span>
          <div class="item_content">
            <span>{{data.diseaseHistoryNameStr}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">过敏史：</span>
          <div class="item_content">
            <span>{{data.allergyHistory}}</span>
          </div>
        </li>
      </ul> -->
      <div class="user-info">
        <div class="user-info-content">
          <div class="title">患者信息</div>
          <div class="item-box">
            <div class="item">患者姓名：{{data.name}}</div>
            <div class="item">患者编号：{{data.id}}</div>
            <div class="item">手机号：{{data.mobile}}</div>
            <div class="item">性别：{{data.sex == 0 ? '男' : (data.sex == 1 ? '女' : '未知')}}</div>
            <div class="item">证件类型：{{data.identityType == 1 ? '身份证' : (data.identityType == 1 ? '军人证' : ( data.identityType == 3 ?  '护照' : '社保卡'))}}</div>
            <div class="item">证件号：{{data.identityCode}}</div>
            <div class="item">生日：{{data.birthday}}</div>
            <div class="item">疾病史：{{data.diseaseHistoryNameStr}}</div>
            <div class="item">过敏史：{{data.allergyHistory}}</div>
          </div>
        </div>
      </div>
    </el-main>

  </el-container>
</template>

<script>
import { getPatientMessage } from '@/api/admin/memberManage/patientManage.js'
export default {
  name: 'patienMessage',
  data () {
    return {
      data: {}
    }
  },
  created () {
    this.langDefault()
  },
  methods: {
    inintData (id) {
      getPatientMessage(id).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        this.data = res.content
      })
    }
  },
  mounted () {
    let id = this.$route.query.id ? this.$route.query.id : 0
    this.inintData(id)
  }
}
</script>

<style lang="scss" scoped>
.patientConfig {
  padding: 0;
  background: #fff;
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
.user-info {
  display: flex;
  margin-left: -30px;
  > div {
    margin-left: 30px;
    border: 1px solid #cfd6ff;
    flex: 1;
    padding: 0 30px;
    .title {
      font-weight: 600;
      color: #333;
      font-size: 14px;
      margin-bottom: 10px;
      margin-top: 10px;
    }
    .item-box {
      display: flex;
      flex-wrap: wrap;
      line-height: 30px;
      color: #666;
      .item {
        min-width: 210px;
      }
    }
  }
}
</style>
