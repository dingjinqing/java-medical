<template>
  <div>
    <el-tabs
      v-model="tabName"
      @tab-click="tabClick"
    >
      <el-tab-pane
        v-for="(item, index) in labels"
        :key="index"
        :label="item.name"
        :name="item.name"
      ></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import status, { getByName } from './status'
export default {
  data () {
    return {
      tabName: status[0].name,
      status: status[0].status,
      labels: status
    }
  },
  model: {
    prop: 'status',
    event: 'update'
  },
  methods: {
    tabClick () {
      this.$emit('update', getByName(this.tabName).status)
    }
  }
}
</script>
