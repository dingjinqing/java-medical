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
        activityName="赠品"
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
          > </el-table-column>
        </el-table>
      </el-row>
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import status, { getById, getByName } from '@/components/admin/status/status'
import statusTab from '@/components/admin/status/statusTab'

export default {

  components: {
    wrapper,
    statusTab
  },
  data () {
    return {
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
    loadData () {

    },
    tabClick (tab, event) {
      this.param.status = getByName(this.tabName).status
    },
    getStatus (v) {
      return getById(v).name
    }
  },
  computed: {
  }
}
</script>
<style lang="scss" scoped>
</style>
