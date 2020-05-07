<template>
<div>
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
  <span class="link-tip" @click="initData">刷新</span>
  <span>|</span>
  <span class="link-tip" @click="jumpToCouponPackPage"> 券礼包管理</span>
</div>
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
      console.log('刷新数据')
      getAllValidCouponPack().then(res => {
        this.options.splice(1, this.options.length - 1)
        if (res.error === 0) {
          this.options.push(...res.content)
        }
      })
    },
    selectCouponPack (val) {
      this.$emit('selectCouponPack', this.options.filter(item => item.id === val)[0])
    },
    jumpToCouponPackPage () {
      this.$router.push({
        name: 'coupon_package'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.rooter /deep/ .el-input {
  width: 130px !important;
}

.link-tip{
  margin: 0 5px;
  color: #409EFF;
  cursor: pointer;
}
</style>
