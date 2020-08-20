
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
    type: Number, // 页面类型(1: 分销员列表, 2: 佣金统计, 3: 商品返利统计, 4: 商品返利明细, 5: 返利提现)
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
      }],
      // 佣金统计列表
      moneySearchList: [{
        label: '手机号',
        value: 'distributorMobile',
        content: ''
      }, {
        label: '微信昵称',
        value: 'distributorName',
        content: ''
      }, {
        label: '下单用户手机号',
        value: 'mobile',
        content: ''
      }, {
        label: '下单用户昵称',
        value: 'username',
        content: ''
      }, {
        label: '下单开始时间',
        value: 'startCreateTime',
        content: ''
      }, {
        label: '下单结束时间',
        value: 'endCreateTime',
        content: ''
      }, {
        label: '返利订单号',
        value: 'orderSn',
        content: ''
      }, {
        label: '返利开始日期',
        value: 'startRebateTime',
        content: ''
      }, {
        label: '返利结束日期',
        value: 'endRebateTime',
        content: ''
      }, {
        label: '返利状态',
        value: 'settlementFlag',
        content: ''
      }, {
        label: '分销员分组',
        value: 'distributorGroup',
        content: ''
      }, {
        label: '返利关系',
        value: 'rebateLevel',
        content: ''
      }],
      // 返利状态列表
      statusList: [{
        label: '待返利',
        value: 0
      }, {
        label: '已返利',
        value: 1
      }, {
        label: '不返利',
        value: 2
      }],
      // 返利关系列表
      relationshipList: [{
        label: '自购返利',
        value: 0
      }, {
        label: '直接返利',
        value: 1
      }, {
        label: '间接返利',
        value: 2
      }],
      // 商品返利统计列表
      goodsSearchList: [{
        label: '商品分类',
        value: 'goodsSort',
        content: ''
      }, {
        label: '商品名称',
        value: 'goodsName',
        content: ''
      }],
      // 商品返利明细
      goodsDetailList: [{
        label: '返利订单号',
        value: 'rebateOrderSn',
        content: ''
      }, {
        label: '下单用户昵称',
        value: 'username',
        content: ''
      }, {
        label: '下单用户手机号',
        value: 'mobile',
        content: ''
      }, {
        label: '分销员昵称',
        value: 'distributorName',
        content: ''
      }, {
        label: '分销员手机号',
        value: 'distributorMobile',
        content: ''
      }, {
        label: '分销员真实姓名',
        value: 'distributorRealName',
        content: ''
      }, {
        label: '返利开始时间',
        value: 'startRebateTime',
        content: ''
      }, {
        label: '返利结束时间',
        value: 'endRebateTime',
        content: ''
      }, {
        label: '返利状态',
        value: 'rebateStatus',
        content: ''
      }, {
        label: '返利关系',
        value: 'rebateLevel',
        content: ''
      }],
      // 返利提现
      withdrawList: [{
        label: '申请人昵称',
        value: 'username',
        content: ''
      }, {
        label: '手机号',
        value: 'mobile',
        content: ''
      }, {
        label: '真实姓名',
        value: 'realName',
        content: ''
      }, {
        label: '提现单号',
        value: 'orderSn',
        content: ''
      }, {
        label: '申请开始时间',
        value: 'startTime',
        content: ''
      }, {
        label: '申请结束时间',
        value: 'endTime',
        content: ''
      }, {
        label: '提现最小金额',
        value: 'minCash',
        content: ''
      }, {
        label: '提现最大金额',
        value: 'maxCash',
        content: ''
      }, {
        label: '处理状态',
        value: 'status',
        content: ''
      }],
      // 返利状态
      returnStatusList: [{
        value: '0',
        label: '全部处理状态'
      },
      {
        value: '1',
        label: '出账失败'
      },
      {
        value: '2',
        label: '出账成功'
      },
      {
        value: '3',
        label: '已审核, 待出账'
      },
      {
        value: '4',
        label: '已驳回申请'
      },
      {
        value: '5',
        label: '待审核'
      }]
    }
  },
  watch: {
    tuneUp (newval) {
      this.exportDialog = true
      this.getAllData()
    }
  },
  mounted () {
    this.getAllData()
  },
  methods: {
    async getAllData () {
      // 获取分销员等级
      await distributorLevelList().then(res => {
        this.groupLevelList = res.content
      })
      // 获取分销员分组
      await distributorAllGroup().then(res => {
        this.groupNameList = res.content
      })

      this.initData()
    },

    // 初始化
    initData () {
      console.log(this.param)
      if (this.type === 1) {
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
      } else if (this.type === 2) {
        for (var key1 in this.param) {
          this.moneySearchList.forEach(item => {
            if (item.value === 'settlementFlag') {
              item.content = []
              if (this.param.settlementFlag && this.param.settlementFlag.length > 0) {
                this.param.settlementFlag.forEach(item2 => {
                  var data = this.statusList.find(item3 => { return item3.value === item2 })
                  item.content.push(data.label)
                })
                item.content = item.content.toString()
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
            } else if (item.value === 'rebateLevel') {
              if (this.param.rebateLevel || this.param.rebateLevel === 0) {
                var relationData = this.relationshipList.find(item => { return item.value === this.param.rebateLevel })
                item.content = relationData.label
              } else {
                item.content = ''
              }
            } else if (item.value === key1) {
              item.content = this.param[key1]
            }
          })
        }
        this.dataList = []
        this.moneySearchList.forEach(item => {
          if (item.content || item.content === 0) {
            this.dataList.push(item)
          }
        })
      } else if (this.type === 3) {
        for (var key2 in this.param) {
          this.goodsSearchList.forEach(item => {
            if (item.value === 'goodsSort') {
              if (this.param.goodsSort || this.param.goodsSort === 0) {
                item.content = this.param.sortName
              } else {
                item.content = ''
              }
            } else if (item.value === key2) {
              item.content = this.param[key2]
            }
          })
        }
        this.dataList = []
        this.goodsSearchList.forEach(item => {
          if (item.content || item.content === 0) {
            this.dataList.push(item)
          }
        })
      } else if (this.type === 4) {
        for (var key3 in this.param) {
          this.goodsDetailList.forEach(item => {
            if (item.value === 'rebateStatus') {
              if (this.param.rebateStatus || this.param.rebateStatus === 0) {
                var statusData = this.statusList.find(item => { return item.value === this.param.rebateStatus })
                item.content = statusData.label
              } else {
                item.content = ''
              }
            } else if (item.value === 'rebateLevel') {
              if (this.param.rebateLevel || this.param.rebateLevel === 0) {
                var relationData = this.relationshipList.find(item => { return item.value === this.param.rebateLevel })
                item.content = relationData.label
              } else {
                item.content = ''
              }
            } else if (item.value === key3) {
              item.content = this.param[key3]
            }
          })
        }
        this.dataList = []
        this.goodsDetailList.forEach(item => {
          if (item.content || item.content === 0) {
            this.dataList.push(item)
          }
        })
      } else if (this.type === 5) {
        for (var key4 in this.param) {
          this.withdrawList.forEach(item => {
            if (item.value === 'status') {
              if (this.param.status || this.param.status === 0) {
                var statusData = this.returnStatusList.find(item => { return item.value === this.param.status })
                item.content = statusData.label
              } else {
                item.content = ''
              }
            } else if (item.value === key4) {
              item.content = this.param[key4]
            }
          })
        }
        this.dataList = []
        this.withdrawList.forEach(item => {
          if (item.content || item.content === 0) {
            this.dataList.push(item)
          }
        })
      }

      this.minValue = 0
      if (this.totalRows > 0) {
        this.maxValue = this.totalRows > 5000 ? 5000 : this.totalRows
      } else {
        this.maxValue = 1
      }

      this.startMin = 0
      this.startMax = this.maxValue - 1
      this.endMin = 1
      this.endMax = this.totalRows > 5000 ? 5000 : this.totalRows
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
