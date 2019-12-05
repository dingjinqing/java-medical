<template>
  <div>
    <div class="footer">
      <div
        class="save"
        @click="handleToSave()"
      >{{$t('shopStyle.saveText')}}</div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      flag: true
    }
  },
  methods: {
    // 点击保存
    handleToSave () {
      this.flag = !this.flag
      this.$store.commit('TOCHANGE_NOTICEINTEGRALDATA', this.flag)
      // this.$http.$emit('frontEndDisplaySaveSignal', this.flag)
      this.$http.$emit('saveIntegralDescription', this.flag)
    }
  }
}
</script>
<style lang="scss" scoped>
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
  margin-right: 1%;
  .save {
    width: 70px;
    height: 30px;
    line-height: 30px;
    border: none;
    background: #5a8bff;
    color: #fff;
    margin: auto;
    cursor: pointer;
  }
}
</style>
