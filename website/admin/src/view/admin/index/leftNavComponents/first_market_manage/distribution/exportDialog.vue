
<template>
  <el-dialog
    title="提示"
    :visible.sync="exportDialog"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    <el-alert
      type="warning"
      show-icon
      :closable='false'
    >
      <template slot='title'>
        <p>{{$t('order.orderExportConfirmTip_1')}}{{totalRows}}{{$t('order.orderExportConfirmTip_2')}}</p>
      </template>
    </el-alert>
    <div class="conContent">
      <span class="conTitle">筛选条件：</span>
      <div v-if="dataList && dataList.length > 0">
        <div
          v-for="(item, index) in dataList"
          :key="index"
          class="conItem"
        >
          <span>{{item.label}}：</span>
          <span>{{item.content !== '' ? item.content : '无'}}</span>
        </div>
      </div>
      <span v-else>无</span>
    </div>
    <div class="conContent">
      <span class="conTitle">导出条数：</span>
      <span>( 一次最多导出5000条数据 )</span>
      <div class="limitContent">
        <el-input-number
          v-model="minValue"
          size="small"
          controls-position="right"
          :min="startMin"
          :max="startMax"
          @change="limitChange()"
        ></el-input-number>
        至
        <el-input-number
          v-model="maxValue"
          size="small"
          controls-position="right"
          :min="endMin"
          :max="endMax"
          @change="limitChange()"
        ></el-input-number>
      </div>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        size="small"
        @click="exportDialog = false"
      >取 消</el-button>
      <el-button
        type="primary"
        size="small"
        @click="sureExportHandler"
      >确 定</el-button>
    </span>
  </el-dialog>

</template>

<script>
import { distributorLevelList, distributorAllGroup } from '@/api/admin/marketManage/distribution.js'
export default {
  props: {
    tuneUp: Boolean,
    param: Object, // 用于展示已经选择的条件
    totalRows: Number // 筛选个数
  },
  data () {
    return {
      exportDialog: false,
      selectFlag: false, // 是否有筛选条件
      minValue: 0, // 导出条数最小值
      maxValue: 1, // 导出条数最大值
      startMin: 0,
      startMax: 0,
      endMin: 1,
      endMax: 5000,
      groupLevelList: [],
      groupNameList: [],
      dataList: [], // 渲染筛选条件
      searchList: [{
        label: '手机号',
        value: 'mobile',
        content: ''
      }, {
        label: '微信昵称',
        value: 'username',
        content: ''
      }, {
        label: '真实姓名',
        value: 'realName',
        content: ''
      }, {
        label: '分销员ID',
        value: 'distributorId',
        content: ''
      }, {
        label: '注册开始时间',
        value: 'startCreateTime',
        content: ''
      }, {
        label: '注册结束时间',
        value: 'endCreateTime',
        content: ''
      }, {
        label: '被邀请用户昵称',
        value: 'invitedUserName',
        content: ''
      }, {
        label: '分销员等级',
        value: 'distributorLevel',
        content: ''
      }, {
        label: '分销员分组',
        value: 'distributorGroup',
        content: ''
      }]
    }
  },
  watch: {
    tuneUp (newval) {
      this.exportDialog = true
      this.getDataList()
      this.$nextTick(() => {
        this.initData()
      })
    }
  },
  mounted () {
    this.getDataList()
  },
  methods: {
    // 初始化
    initData () {
      console.log(this.param)

      for (var key in this.param) {
        this.searchList.forEach(item => {
          if (item.value === 'distributorLevel') {
            if (this.param.distributorLevel) {
              var levelData = this.groupLevelList.find(item => { return item.id === this.param.distributorLevel })
              item.content = levelData.levelName
            } else {
              item.content = ''
            }
          } else if (item.value === 'distributorGroup') {
            if (this.param.distributorGroup) {
              var groupData = this.groupNameList.find(item => { return item.id === this.param.distributorGroup })
              item.content = groupData.groupName
            } else {
              item.content = ''
            }
          } else if (item.value === key) {
            item.content = this.param[key]
          }
        })
      }

      this.dataList = []
      this.searchList.forEach(item => {
        if (item.content || item.content === 0) {
          this.dataList.push(item)
        }
      })

      this.minValue = 0
      this.maxValue = 1
      this.startMin = 0
      this.startMax = 0
      this.endMin = 1
      this.endMax = this.totalRows > 5000 ? 5000 : this.totalRows
    },
    getDataList () {
      // 获取分销员等级
      distributorLevelList().then(res => {
        this.groupLevelList = res.content
      })
      // 获取分销员分组
      distributorAllGroup().then(res => {
        this.groupNameList = res.content
      })
    },
    // 限制条数切换
    limitChange () {
      this.startMax = this.maxValue - 1
      this.endMin = this.minValue + 1
    },
    // 导出数据
    sureExportHandler () {
      if (this.totalRows === 0) {
        this.$message.warning('根据当前筛选条件没有数据可导出, 请重新进行选择!')
        return false
      }
      var data = this.param
      data.startNum = this.minValue
      data.endNum = this.maxValue
      this.$emit('export', data)
      this.exportDialog = false
    }
  }

}
</script>

<style lang="scss" scoped>
.conContent {
  padding: 10px;
  border-bottom: 1px dashed #ccc;
  .conTitle {
    font-weight: bold;
  }
  .conItem {
    margin: 10px 0px;
  }
  .limitContent {
    margin-top: 10px;
  }
}
.conContent:last-child {
  border-bottom: none;
}
</style>
