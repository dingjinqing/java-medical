<template>
  <!-- 幸运大抽奖下拉组件 -->
  <div>
    <el-select
      v-model="selectValue"
      @change="changeHandle"
      style="width:170px;"
      size="small"
    >
      <el-option
        v-for="item in selects"
        :key="item.id"
        :label="item.actName"
        :value="item.id"
      ></el-option>
    </el-select>
    <el-button
      type="text"
      @click="initSelectData"
    >刷新</el-button>
    <el-divider direction="vertical"></el-divider>
    <el-button
      type="text"
      @click="toAdd"
    >新建</el-button>
    <el-divider direction="vertical"></el-divider>
    <el-button
      type="text"
      @click="toList"
    >管理</el-button>
  </div>
</template>

<script>
import { getAllCoupon } from '@/api/admin/marketManage/evaluationGift.js'
export default {
  props: {
    value: [Number, String],
    isHasStock: { // 是否限制库存 默认true限制
      type: Boolean,
      default: true
    }
  },
  model: {
    prop: 'value',
    event: 'change'
  },
  data () {
    return {
      selects: []
    }
  },
  computed: {
    selectValue: {
      get () {
        return Number(this.value)
      },
      set (val) {
        this.$emit('change', val)
      }
    }
  },
  mounted () {
    this.initSelectData()
  },
  methods: {
    initSelectData () {
      let params = {
        isHasStock: this.isHasStock
      }
      getAllCoupon(params).then(res => {
        if (res.error === 0) {
          this.selects = res.content
        }
      })
    },
    changeHandle (val) {
      this.$emit('change', val)
    },
    toAdd () {
      this.$router.push({
        name: 'add_coupon'
      })
    },
    toList () {
      this.$router.push({
        name: 'ordinary_coupon'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.el-divider--vertical {
  margin: 0 1px;
}
</style>
