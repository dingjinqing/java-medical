<template>
<div id="coupon-pack-id">
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
  <span class="link-tip" @click="refreshData">{{$t('memberCard.refresh')}}</span>
  <span>|</span>
  <span class="link-tip" @click="jumpToCouponPackPage">{{$t('memberCard.couponPackManage')}}</span>
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
      ],
      refresh: false,
      loading: null
    }
  },
  mounted () {
    this.langDefault()
    this.initData()
  },
  methods: {
    initData () {
      getAllValidCouponPack().then(res => {
        this.options.splice(1, this.options.length - 1)
        if (res.error === 0) {
          this.options.push(...res.content)
          if (this.refresh) {
            setTimeout(() => {
              this.loading.close()
              this.refresh = false
              this.$message.success(this.$t('memberCard.loadSuccess'))
            }, 1000)
          }
        }
      })
    },
    selectCouponPack (val) {
      this.$emit('selectCouponPack', this.options.filter(item => item.id === val)[0])
    },
    refreshData () {
      console.log('刷新数据')
      this.refresh = true
      var target = document.getElementById('coupon-pack-id')
      this.loading = this.$loading({
        target,
        lock: true,
        text: this.$t('memberCard.loading'),
        spinner: 'el-icon-loading',
        background: 'rgba(255, 255, 255, 0)'
      })
      this.initData()
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
