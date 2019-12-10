<!--
* 营销活动状态 tabs
* 状态：全部xx活动、进行中、未开始、已结束、已停用
* 注意：需要添加 :standard = "true" 属性
* @prop activityName 活动名称
* @model 状态id，全部：0，进行中：1，未开始：2，一结束：3，已停用：4
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
import status, { getByName, getById, standardStatus, getByNameStandard, getByIdStandard } from './status'
export default {
  data () {
    const { standard } = this
    let labels = status
    if (standard) {
      labels = standardStatus
    }
    const _status = this.$attrs.status
    const tabName = ((standard ? getByIdStandard(_status) : getById(_status)) || labels[0]).name
    return {
      tabName,
      labels
    }
  },
  model: {
    prop: 'status',
    event: 'update'
  },
  props: ['activityName', 'standard'],
  watch: {
    '$attrs.status' (newVal) {
      if (newVal || newVal === 0) {
        const { standard } = this
        let _status = newVal
        this.tabName = ((standard ? getByIdStandard(_status) : getById(_status)) || this.labels[0]).name
      }
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {
    tabClick () {
      const { standard } = this
      const _status = (standard ? getByNameStandard(this.tabName) : getByName(this.tabName)).status
      this.$emit('update', _status)
    },
    getTabName (status) {
      const { standard } = this
      const allCode = standard ? 0 : null
      if (status.status === allCode) {
        return this.$t('statusTab.all') + ' ' + this.activityName + ' ' + this.$t('statusTab.activity')
      } else if (status.status === 1) {
        return this.$t('statusTab.processing')
      } else if (status.status === 2) {
        return this.$t('statusTab.prepared')
      } else if (status.status === 3) {
        return this.$t('statusTab.expired')
      } else if (status.status === 4) {
        return this.$t('statusTab.terminated')
      }
      return status.name
    }
  }
}
</script>
