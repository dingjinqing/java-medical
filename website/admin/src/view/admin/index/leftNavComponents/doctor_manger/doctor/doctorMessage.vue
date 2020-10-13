<template>
  <!-- 店铺基础信息 -->
  <el-container>
    <el-main class="patientConfig">
      <div class="user-info">
        <div class="user-info-content">
          <div class="title">基础信息  <span style="font-weight:400;">{{data.registerHospital}}</span></div>
          <div class="item-box">
            <div class="item">医师姓名：{{data.name}}</div>
            <div class="item">院内编号：{{data.hospitalCode}}</div>
            <div class="item">医师资格编码：{{data.certificateCode}}</div>
            <div class="item">医师执业编码：{{data.professionalCode}}</div>
            <div class="item">性别：{{data.sex == 0 ? '男' : (data.sex == 1 ? '女' : '未知')}}</div>
            <!-- <div class="item">证件类型：{{data.identityType == 1 ? '身份证' : (data.identityType == 1 ? '军人证' : ( data.identityType == 3 ?  '护照' : '社保卡'))}}</div> -->
            <div class="item">医师职称：{{data.titleName}}</div>
            <!-- <div class="item">聘任职务：{{data.diseaseHistoryNameStr}}</div> -->
            <div class="item">科室：
              <span
                v-for="(item,index) in data.departmentNames"
                :key="index"
              >{{item}}</span>
            </div>
            <div class="item">问诊费用：{{data.consultationPrice}}</div>
            <div class="item">擅长疾病：{{data.treatDisease}}</div>
            <div class="item">手机号：{{data.mobile}}</div>
            <div class="item">评价星级：{{data.avgCommentStar}}</div>
            <div class="item">关注数：{{data.attentionNumber}}</div>
            <div class="item">接诊量：{{data.consultationNumber}}</div>
            <div class="item">平均响应时间：{{data.avgAnswerTimeNotSecond}}</div>
            <div class="item">医师签名：{{data.allergyHistory}}</div>
          </div>
        </div>
      </div>
    </el-main>

  </el-container>
</template>

<script>
import { doctorDetail } from '@/api/admin/doctorManage/doctorInfo/doctor'
export default {
  name: 'doctorMessage',
  data () {
    return {
      data: {}
    }
  },
  created () {
    this.langDefault()
  },
  methods: {
    inintData (doctorId) {
      doctorDetail({ doctorId: doctorId }).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        this.data = res.content
      })
    }
  },
  mounted () {
    let doctorId = this.$route.query.id ? this.$route.query.id : 0
    this.inintData(doctorId)
  }
}
</script>

<style lang="scss" scoped>
.el-container {
  padding: 10px 20px;
  background: #fff;
}
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
