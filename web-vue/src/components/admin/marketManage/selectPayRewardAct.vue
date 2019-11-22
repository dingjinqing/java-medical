<template>
  <!-- 幸运大抽奖下拉组件 -->
  <div>
    <el-select
      v-model="value"
      @change="changeHandle"
      style="width:170px;"
      size="small"
    >
      <el-option
        v-for="(item,index) in selects"
        :key="index"
        :label="item.name"
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
import { selectPayRewardApi } from '@/api/admin/marketManage/openScreen.js'
export default {
  props: {
    value: [Number, String]
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
  mounted () {
    this.initSelectData()
  },
  methods: {
    initSelectData () {
      selectPayRewardApi().then(res => {
        if (res.error === 0) {
          console.log(res)
          this.selects = res.content
        }
      })
    },
    changeHandle (val) {
      this.$emit('change', val)
    },
    toAdd () {
      this.$router.push({
        name: 'lottery_activity',
        query: {
          add: 1
        }
      })
    },
    toList () {
      this.$router.push({
        name: 'lottery_activity'
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
