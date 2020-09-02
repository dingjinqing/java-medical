<template>
  <div class="content">
    <div class="main">
      <div class="prescription-content">
        <div class="prescription-item">
          <div class="top_info">
            <div class="item-title">电子处方</div>
            <div class="top_content">
              <div>就诊卡号：{{originalData.patient_treatment_code}}</div>
              <div>编号：{{originalData.prescriptionCode}}</div>
            </div>
          </div>
          <div class="item-list-content">
            <div class="item-list">
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">就诊人信息</div>
              </div>
              <div class="list_content list_patient_info">
                <div class="each_patient_column">
                  <div class="dark_one">{{originalData.patientName}}</div>
                  <div>姓名</div>
                </div>
                <div class="each_patient_column">
                  <div class="dark_one">{{originalData.patientSex  == 0 ? '男' : (originalData.patientSex  == 1 ? '女' : '未知')}}</div>
                  <div>性别</div>
                </div>
                <div class="each_patient_column">
                  <div class="dark_one">{{originalData.patientAge}}岁</div>
                  <div>年龄</div>
                </div>
                <div class="each_patient_column">
                  <div class="dark_one">{{originalData.departmentName}}</div>
                  <div>科室</div>
                </div>
              </div>
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">诊断</div>
              </div>
              <div class="list_content">{{originalData.diagnosisName}}</div>
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">治疗建议</div>
              </div>
              <div
                class="list_content"
                v-for="(item,index) in originalData.itemList"
                :key="index"
              >
                <div class="medicine_name">{{item.goodsCommonName}}</div>
                <div class="medicine_spec">用法用量：{{item.goodsQualityRatio}},{{item.goodsUseMemo}}</div>
              </div>
            </div>
          </div>
          <div class="doctor-info">
            <div class="doctor-name">
              <div>处方医师：{{originalData.doctorName}}
              </div>
              <div>审核医师：{{originalData.pharmacistName}}
              </div>
              <div>发药医师：{{originalData.pharmacistName}}
              </div>
            </div>
            <span class="item-date">日期：{{originalData.prescriptionCreateTime}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getPrescriptionMessage } from '@/api/admin/memberManage/patientManage.js'
export default {
  data () {
    return {
      langDefaultFlag: false,
      originalData: []
    }
  },
  methods: {
    initDataList () {
      let prescriptionCode = this.$route.query.prescriptionCode
      getPrescriptionMessage(prescriptionCode).then((res) => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }
        console.log(res.content)
        this.originalData = res.content
      }).catch(error => {
        console.log(error)
      })
    }
  },
  mounted () {
    console.log(this.$route.query)
    this.initDataList()
  }
}
</script>
<style  scoped>
.main {
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.prescription-content {
  padding: 10px 15px;
  display: flex;
  flex-direction: column;
  min-width: 450px;
}
.prescription-item {
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(13, 19, 24, 0.24), 0 0 0px rgba(10, 16, 20, 0.12);
  position: relative;
  margin-bottom: 15px;
}
.prescription-item + .prescription-item {
  margin-top: 15px;
}
.prescription-item > .top_info {
  color: #fff;
  background-color: skyblue;
  height: 210px;
  background: #26c4bc
    url(http://medicaldevimg.weipubao.cn/image/wxapp/prescription_bg.png)
    no-repeat left top/100% 110px;
  border-radius: 16px 16px 0 0;
}
.prescription-item > .top_info .item-title {
  padding: 23px 0;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
}
.prescription-item > .top_info .top_content {
  padding-left: 34px;
  font-size: 13px;
}
.prescription-item > .top_info .top_content > div:first-of-type {
  margin-bottom: 15px;
}
.prescription-item > .item-list-content {
  padding-left: 30px;
  background-color: #fff;
  position: relative;
  border-radius: 16px 16px 0 0;
  margin-top: -80px;
}
.prescription-item > .item-list-content > .item-list {
  display: flex;
  flex-direction: column;
  line-height: 27px;
  padding: 10px 0 0;
  border-bottom: 2px solid #eee;
}
.prescription-item > .item-list-content > .item-list > .list-item {
  display: flex;
  align-items: center;
}
.prescription-item
  > .item-list-content
  > .item-list
  > .list-item
  > .list-item-dot {
  border: 4px solid skyblue;
  border-radius: 50%;
  height: 10px;
  width: 10px;
  margin-right: 14px;
}
.prescription-item
  > .item-list-content
  > .item-list
  > .list-item
  > .list-item-content {
  font-weight: bold;
}
.prescription-item > .doctor-info {
  padding: 10px 30px;
  align-items: center;
  background: #fff;
  display: flex;
  color: #999;
  position: relative;
}
.prescription-item > .doctor-info::after {
  content: '';
  position: absolute;
  height: 16px;
  width: 100%;
  bottom: -16px;
  left: 0;
  background-image: radial-gradient(
    10px circle at 24px 16px,
    transparent 14px,
    #fff 14px
  );
  background-size: 40px 20px;
}
.prescription-item > .doctor-info > .doctor-name {
  margin-right: auto;
  font-size: 13px;
}
.doctor-name > view {
  display: flex;
  align-items: center;
  margin-top: 30rpx;
}
.prescription-item > .doctor-info > .item-date {
  font-size: 13px;
}
.list_content {
  padding: 15px;
}
.list_patient_info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 30px;
}
.each_patient_column {
  color: #999;
}
.dark_one {
  margin-bottom: 10px;
  color: #333;
}
.medicine_spec {
  color: #999;
  margin-top: 10px;
}
.doctor-name {
  height: 70px;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
}
.item-date {
  position: absolute;
  right: 100px;
  bottom: 17px;
}
</style>
