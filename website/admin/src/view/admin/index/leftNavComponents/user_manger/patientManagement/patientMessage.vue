<template>
  <!-- 店铺基础信息 -->
  <el-container>
    <el-main class="patientConfig">
      <ul class="program_details">
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
            <span>{{data.sex}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">证件类型：</span>
          <div class="item_content">
            <span>{{data.identityType}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">证件号：</span>
          <div class="item_content">
            <span>{{data.identityNo}}</span>
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
            <span>{{data.diseaseHistory}}</span>
          </div>
        </li>
        <li class="details_item">
          <span class="item_label">过敏史：</span>
          <div class="item_content">
            <span>{{data.allergyHistory}}</span>
          </div>
        </li>
      </ul>
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
