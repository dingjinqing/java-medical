<!--
* 赠品活动
*
* @author 郑保乐
-->
<template>
  <div>
    <wrapper>
      <statusTab
        v-model="param.status"
        :activityName="activityName"
      />
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input
            v-model="param.name"
            placeholder="活动名称"
          ></el-input>
        </el-col>
        <el-col :span="3">
          <el-button
            type="primary"
            @click="loadData"
          >查询</el-button>
        </el-col>
        <el-col
          :span="4"
          :offset="13"
        >
          <el-button
            type="primary"
            style="float:right;"
            @click="gotoAddGift"
          >
            添加赠品活动
          </el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-table
          class="version-manage-table"
          header-row-class-name="tableHeader"
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="name"
            label="活动名称"
            align="center"
          > </el-table-column>
          <el-table-column
            label="有效期"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.startTime}}<br>至<br>{{scope.row.endTime}}
            </template>
          </el-table-column>
          <el-table-column
            prop="level"
            label="优先级"
            align="center"
          > </el-table-column>
          <el-table-column
            prop="giftTimes"
            label="赠送次数"
            align="center"
          > </el-table-column>
          <el-table-column
            label="活动状态"
            align="center"
          >
            <template slot-scope="scope">
              {{getStatus(scope.row.status)}}
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <span @click="disableGift(scope.row.id)">停用</span>
              <span>赠送明细</span>
              <span @click="deleteGift(scope.row.id)">删除</span>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import statusTab from '@/components/admin/status/statusTab'
import status, { getById } from '@/components/admin/status/status'
import { giftList, deleteGift, disableGift } from '@/api/admin/marketManage/gift'

export default {

  components: {
    wrapper,
    statusTab
  },
  data () {
    return {
      activityName: '赠品',
      tabName: status[0].name,
      param: {
        name: '',
        status: null
      },
      tableData: [{
        name: 'Oh my zsh',
        startTime: '2019-09-09 00:00:00',
        endTime: '2019-09-11 00:00:00',
        level: 1,
        giftTimes: 12,
        status: 0
      }],
      labels: status
    }
  },
  methods: {
    // 列表查询
    loadData () {
      const { param } = this
      giftList(param).then(res => {
        const { content: { dataList } } = res
        this.tableData = dataList
      })
    },
    // 删除活动
    deleteGift (id) {
      deleteGift(id)
      this.loadData()
    },
    // 停用活动
    disableGift (id) {
      disableGift(id)
      this.loadData()
    },
    // 跳转创建赠品页
    gotoAddGift () {
      this.$router.push('/admin/home/main/gift/add')
    },
    getStatus (v) {
      return getById(v).name
    }
  },
  watch: {
    'param.status' (n, o) {
      this.loadData()
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>
<style lang="scss" scoped>
</style>
