<template>
  <div class="main-container">
    <div class="content">
      <div class="wrap">
        <div
          class="title"
          :style="
            'background:url(' +
              $imageHost +
              '/image/admin/shop_beautify/phone_tops.png) 100%/100% no-repeat;'
          "
        ></div>
        <div class="wrap-content">
          <template v-for="(item, index) in recordList">
            <div
              class="user_con"
              :class="{ con_left: !item.doctor, con_right: item.doctor }"
              :key="index"
            >
              <div
                class="user_icon"
                :class="{ user_left: !item.doctor, user_right: item.doctor }"
              >
                <img
                  :src="
                    !item.doctor
                      ? $imageHost + '/image/wxapp/user_default_icon.png'
                      : $imageHost + '/image/wxapp/doctor_default_icon.png'
                  "
                  alt=""
                />
              </div>
              <div
                class="origin_message"
                :class="{
                  origin_left: !item.doctor,
                  origin_right: item.doctor
                }"
                v-if="item.type === 0"
              >
                {{ JSON.parse(item.message).content }}
              </div>
              <div
                class="patient_message"
                :class="{ p_d_left: !item.doctor, p_d_right: item.doctor }"
                v-if="item.type === 3"
              >
                <div class="p_m_top">
                  <span>患者: {{ JSON.parse(item.message).content.name }}</span>
                  <span>{{ JSON.parse(item.message).content.sex }}</span>
                  <span>{{ JSON.parse(item.message).content.age }}岁</span>
                </div>
                <div class="p_m_bot">
                  <span>病情描述：</span>
                  <span>{{ JSON.parse(item.message).content.mess }}</span>
                </div>
              </div>
              <div
                class="origin_message"
                :class="{
                  origin_left: !item.doctor,
                  origin_right: item.doctor
                }"
                v-if="item.type === 1"
              >
                <el-image
                  style="width: 180px"
                  :src="JSON.parse(item.message).content"
                  fit="contain"
                ></el-image>
              </div>
              <div
                slot="doctor_pres_message"
                class="prescription-item"
                :class="{ p_d_left: !item.doctor, p_d_right: item.doctor }"
                v-if="item.type === 2"
                @click="
                  viewPrescriptionInfo(
                    JSON.parse(item.message).content.prescriptionCode
                  )
                "
              >
                <div
                  class="item-title"
                  :style="
                    'background:#26c4bc url(' +
                      $imageHost +
                      '/image/wxapp/inedx-prescription-bg.png) no-repeat left top/100% 40px;'
                  "
                >
                  电子处方
                </div>
                <div class="item-list-content">
                  <div class="item-list">
                    <div class="list-item">
                      <div class="list-item-content">
                        <span class="list-item-dot"></span>
                        <span
                          >诊断：{{
                            JSON.parse(item.message).content.diagnosisName
                          }}</span
                        >
                      </div>
                    </div>
                    <div class="list-item">
                      <div class="list-item-content">
                        <span class="list-item-dot"></span>
                        <span
                          >科室：{{
                            JSON.parse(item.message).content.departmentName
                          }}</span
                        >
                      </div>
                    </div>
                  </div>
                </div>
                <div class="doctor-info">
                  <span class="doctor-name"
                    >医师：{{
                      JSON.parse(item.message).content.doctorName
                    }}</span
                  >
                  <span class="item-date"
                    >日期：{{ JSON.parse(item.message).content.time }}</span
                  >
                </div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getRecordList } from '@/api/admin/orderManage/order.js'
export default {
  data () {
    return {
      recordList: []
    }
  },
  mounted () {
    this.getRecord()
  },
  methods: {
    getRecord () {
      getRecordList({ orderSn: this.$route.query.orderSn }).then(res => {
        if (res.error === 0) {
          this.recordList = res.content
        }
      })
    },
    viewPrescriptionInfo (code) {
      const { href } = this.$router.resolve({
        name: 'prescription_message',
        query: {
          prescriptionCode:code
        }
      })
      window.open(href, '_blank')
    }
  }
}
</script>

<style lang="scss" scoped>
.main-container {
  padding: 10px;
  .content {
    background-color: #fff;
    height: 100%;
    position: relative;
    .wrap {
      border: 1px solid #ccc;
      background: #f5f5f5;
      position: absolute;
      left: 50%;
      margin-left: -176px;
      top: 20px;
      bottom: 20px;
      width: 352px;
      .title {
        height: 55px;
        color: white;
        text-align: center;
      }
      .wrap-content {
        background-color: #f5f5f5;
        position: absolute;
        top: 55px;
        bottom: 0;
        left: 0;
        right: 0;
        padding: 10px 0;
        overflow-y: scroll;
      }
    }
  }
}
.user_icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
}

.user_icon img {
  width: 100%;
  height: 100%;
}

.user_left {
  margin-left: 15px;
}

.user_right {
  margin-right: 15px;
}

.user_con {
  display: flex;
  margin-bottom: 15px;
}

.con_right {
  flex-direction: row-reverse;
}
.origin_message {
  max-width: 225px;
  min-height: 25px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  padding: 10px;
  position: relative;
  word-wrap: break-word;
  word-break: break-all;
}
.origin_left {
  background: #fff;
  font-size: 16px;
  color: #333;
  margin-left: 10px;
}
.origin_left::before,
.p_d_left::before {
  content: '';
  width: 0;
  height: 0;
  position: absolute;
  top: 17px;
  left: -5px;
  border-top: solid 5px transparent;
  border-right: solid 5px #fff;
  border-bottom: solid 5px transparent;
}
.origin_right {
  background: #26c4bc;
  font-size: 16px;
  color: #fff;
  margin-right: 10px;
}
.origin_right::before,
.p_d_right::before {
  content: '';
  width: 0;
  height: 0;
  position: absolute;
  top: 17px;
  right: -5px;
  border-top: solid 5px transparent;
  border-left: solid 5px #26c4bc;
  border-bottom: solid 5px transparent;
}
.patient_message {
  width: 240px;
  min-height: 80px;
  border-radius: 5px 5px 0px 0px;
  background: #fff;
  position: relative;
}
.p_d_left {
  margin-left: 10px;
}
.p_d_right {
  margin-right: 10px;
}
.p_m_top {
  background: #26c4bc;
  height: 40px;
  line-height: 40px;
  color: #fff;
  padding-left: 10px;
  border-radius: 5px 5px 0 0;
}
.p_m_top text {
  font-size: 16px;
  margin-right: 15px;
}
.p_m_bot {
  padding: 10px;
}
.p_m_bot text:first-of-type {
  color: #999;
}
.p_m_bot text:last-of-type {
  color: #333;
  line-height: 19px;
}
.prescription-item {
  border-radius: 8px;
  position: relative;
  width: 240px;
  min-height: 150px;
}
.prescription-item + .prescription-item {
  margin-top: 15px;
}
.prescription-item > .item-title {
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-align: center;
  background-color: skyblue;
  height: 50px;
  border-radius: 8px 8px 0 0;
}
.prescription-item > .item-list-content {
  padding-left: 15px;
  background-color: #fff;
  position: relative;
  border-radius: 8px 8px 0 0;
  margin-top: -10px;
}
.prescription-item > .item-list-content > .item-list {
  display: flex;
  flex-direction: column;
  line-height: 22.5px;
  padding: 7.5px 0;
  border-bottom: 1px solid #eee;
}
.prescription-item > .item-list-content > .item-list > .list-item {
  display: flex;
  align-items: center;
}
.prescription-item > .item-list-content .list-item-dot {
  border: 2px solid skyblue;
  border-radius: 50%;
  height: 5px;
  width: 5px;
  margin-right: 10px;
  display: inline-block;
}
.prescription-item
  > .item-list-content
  > .item-list
  > .list-item
  > .list-item-content {
  color: #666;
  font-size: 13px;
}
.prescription-item > .doctor-info {
  height: 40px;
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
  height: 8px;
  width: 100%;
  bottom: -8px;
  left: 0;
  background-image: radial-gradient(
    5px circle at 12px 8px,
    transparent 7px,
    #fff 7px
  );
  background-size: 20px 10px;
}
.prescription-item > .doctor-info > .doctor-name {
  margin-right: auto;
  font-size: 13px;
}
.prescription-item > .doctor-info > .item-date {
  font-size: 13px;
}
</style>
