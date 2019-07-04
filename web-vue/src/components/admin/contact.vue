<template>
  <div class="contact_container">
    <div class="mobile common">
      <img src="../../assets/adminImg/left_menu_phone.png">
      <div
        class="suspension_message"
        v-if="hidden_phone"
      >
        <img src="../../assets/adminImg/left_menu_jt.png">
        客服电话：400-010-1039
      </div>
    </div>
    <div class="news common">
      <img src="../../assets/adminImg/left_menu_zx.png">
      <div
        class="suspension_message new_calss"
        v-if="hidden_new"
      >
        <img src="../../assets/adminImg/left_menu_jt.png">
        在线咨询
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      hidden_phone: false,
      hidden_new: false
    }
  }
}
</script>
<style scoped>
.contact_container {
  position: fixed;
  width: 50px;
  height: 75px;
  z-index: 10;
  bottom: 10px;
}
.common {
  position: relative;
  width: 40px;
  height: 40px;
  margin-bottom: 2px;
  background-color: #515768;
  border-radius: 0 4px 4px 0;
}
.common {
  display: flex;
}
.common img {
  margin: auto;
}
.suspension_message {
  height: 30px;
  background-color: white;
  position: absolute;
  left: 44px;
  color: #5a8bff;
  line-height: 26px;
  font-size: 12px;
  text-align: left;
  padding-left: 5px;
  border-radius: 2px;
  border: 1px solid #eee;
  top: 4px;
  width: 150px;
}
.suspension_message > img {
  position: absolute;
  left: -4px;
  top: 10px;
}

.new_calss {
  width: 60px;
}
</style>
