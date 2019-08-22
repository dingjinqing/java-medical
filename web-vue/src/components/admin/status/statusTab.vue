<!--
* 营销活动状态 tabs
* 状态：全部xx活动、进行中、未开始、已结束、已停用
* @prop activityName 活动名称
* @model 状态id，全部：null，进行中：0，未开始：1，一结束：2，已停用：3
* @author 郑保乐
-->
<template>
  <div>
    <el-tabs
      v-model="tabName"
      @tab-click="tabClick"
    >
      <el-tab-pane
        v-for="(item, index) in labels"
        :key="index"
        :label="getTabName(item)"
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
  props: ['activityName'],
  methods: {
    tabClick () {
      this.$emit('update', getByName(this.tabName).status)
    },
    getTabName (status) {
      if (status.status === null) {
        return '全部' + this.activityName + '活动'
      }
      return status.name
    }
  }
}
</script>
