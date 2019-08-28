<!--
* 赠品活动列表
*
* @author 郑保乐
-->
<template>
  <div>
    <wrapper>
      <statusTab
        v-model="param.status"
        :activityName="activityName"
        :standard="false"
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
          >
            <template slot-scope="scope">
              <inputEdit
                v-model="scope.row.level"
                :init="scope.row.level"
                @update="updateGiftLevel(scope.row.id, scope.row.level)"/>
            </template>
          </el-table-column>
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
              <el-row>
                <el-button
                  size="mini"
                  @click="disableGift(scope.row.id)"
                  v-show="couldStop(scope.row)"
                >停用</el-button>
                <el-button
                  size="mini"
                  @click="enableGift(scope.row.id)"
                  v-show="couldStart(scope.row)"
                >启用</el-button>
                <el-button
                  size="mini"
                  @click="editGift(scope.row.id)"
                  v-show="couldEdit(scope.row)"
                >编辑</el-button>
                <el-button
                  size="mini"
                  @click="gotoGiftDetail(scope.row.id)"
                >赠送明细</el-button>
                <el-button
                  size="mini"
                  @click="deleteGift(scope.row.id)"
                  v-show="couldDelete(scope.row)"
                >删除</el-button>
              </el-row>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-col
          :offset="14"
          :span="10"
        >
          <el-pagination
            @size-change="loadData"
            @current-change="loadData"
            :current-page.sync="param.currentPage"
            :page-size="param.pageRows"
            :total="page.pageRows"
            layout="total, sizes, prev, pager, next, jumper"
          >
          </el-pagination>
        </el-col>
      </el-row>
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import statusTab from '@/components/admin/status/statusTab'
import inputEdit from '@/components/admin/inputEdit'
import status, { getById, couldEdit, couldStop, couldStart, couldDelete } from '@/components/admin/status/status'
import { giftList, deleteGift, disableGift, enableGift, updateGiftLevel } from '@/api/admin/marketManage/gift'

export default {

  components: {
    wrapper,
    statusTab,
    inputEdit
  },
  data () {
    return {
      activityName: '赠品',
      param: {
        name: '',
        status: 0,
        // 分页
        currentPage: 0,
        pageRows: 20
      },
      page: {
        totalRows: 0
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
        const { content: { page, dataList } } = res
        this.tableData = dataList
        this.page = page
      })
    },
    // 删除活动
    deleteGift (id) {
      deleteGift(id).then(r => {
        this.loadData()
        this.success('删除成功')
      })
    },
    // 停用活动
    disableGift (id) {
      disableGift(id).then(r => {
        this.loadData()
        this.success('停用成功')
      })
    },
    // 启用活动
    enableGift (id) {
      enableGift(id).then(r => {
        this.loadData()
        this.success('启用成功')
      })
    },
    // 修改活动优先级
    updateGiftLevel (id, level) {
      updateGiftLevel({
        id, level
      }).then(r => this.success('修改成功'))
    },
    // 跳转创建赠品页
    gotoAddGift () {
      this.$router.push('/admin/home/main/gift/add')
    },
    // 跳转编辑活动页
    editGift (id) {
      this.$router.push(`/admin/home/main/gift/add/${id}`)
    },
    // 跳转赠送明细页
    gotoGiftDetail (id) {
      this.$router.push(`/admin/home/main/gift/giftDetail/${id}`)
    },
    getStatus (v) {
      return getById(v).name
    },
    success (message) {
      this.$message({
        message,
        type: 'success'
      })
    },
    couldEdit (row) {
      return couldEdit(row)
    },
    couldStop (row) {
      return couldStop(row)
    },
    couldStart (row) {
      return couldStart(row)
    },
    couldDelete (row) {
      return couldDelete(row)
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
