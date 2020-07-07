<template>
  <div class="content">
    <div class="main">
      <div class="prescription-content">
        <div class="prescription-item">
          <div class="top_info">
            <div class="item-title">电子处方1</div>
            <div class="top_content">
              <div>就诊卡号：2006798749374837</div>
              <div>编号：2006798749374837</div>
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
                  <div class="dark_one">张三</div>
                  <div>姓名</div>
                </div>
                <div class="each_patient_column">
                  <div class="dark_one">男</div>
                  <div>性别</div>
                </div>
                <div class="each_patient_column">
                  <div class="dark_one">52岁</div>
                  <div>年龄</div>
                </div>
                <div class="each_patient_column">
                  <div class="dark_one">外科外科外科外科外科外科</div>
                  <div>科室</div>
                </div>
              </div>
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">诊断</div>
              </div>
              <div class="list_content">原发性高血压</div>
              <div class="list-item">
                <span class="list-item-dot"></span>
                <div class="list-item-content">治疗建议</div>
              </div>
              <div class="list_content">
                <div class="medicine_name">苯磺酸氨氯地平片</div>
                <div class="medicine_spec">用法用量：清晨空腹口服，一次5mg，一日1次，用药84天</div>
              </div>
            </div>
          </div>
          <div class="doctor-info">
            <span class="doctor-name">医师：张三</span>
            <span class="item-date">日期：2020.06.30</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {

}
</script>
<style  scoped>
.main {
  padding: 10px;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}
.prescription-content {
  padding: 10px 15px;
  display: flex;
  flex-direction: column;
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
    url(http://medicaldevimg.weipubao.cn/upload/224462/image/20200702/cHqjI9maXz2wGvOzjoHJ.png)
    no-repeat left top/100% 80px;
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
  padding: 10px 0;
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
  height: 80px;
  padding: 0 15px;
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
</style>
