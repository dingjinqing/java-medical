<template>
  <el-select
    class="rooter"
    v-model="couponPack"
    :placeholder="$t('membershipIntroduction.placeChoise')"
    size="small"
    @change="selectCouponPack"
  >
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item.actName"
      :value="item.id"
    >
    </el-option>
  </el-select>
</template>

<script>
import { getAllValidCouponPack } from '@/api/admin/marketManage/couponGive.js'
export default {
  props: {
    myPack: {
      type: Object,
      default: () => { return null }
    }
  },
  watch: {
    myPack (val) {
      console.log(val)
      this.couponPack = val
    }
  },
  data () {
    return {
      couponPack: this.myPack,
      options: [
        {
          id: 0,
          actName: '请选择'
        }
      ]
    }
  },
  mounted () {
    this.initData()
  },
  methods: {
    initData () {
      getAllValidCouponPack().then(res => {
        if (res.error === 0) {
          this.options.push(...res.content)
        }
      })
    },
    selectCouponPack (val) {
      this.$emit('selectCouponPack', this.options.filter(item => item.id === val)[0])
    }
  }
}
</script>

<style lang="scss" scoped>
.rooter /deep/ .el-input {
  width: 130px !important;
}
</style>
