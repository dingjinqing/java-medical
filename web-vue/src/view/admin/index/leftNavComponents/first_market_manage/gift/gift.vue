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
      <el-row
        :gutter="20"
        type="flex"
      >
        <el-col
          :span="7"
          style="height:30px;line-height: 30px;font-size: 14px;"
        >活动名称</el-col>
        <el-input
          v-model="param.name"
          placeholder="请输入活动名称"
          size="small"
          suffix-icon="el-icon-search"
          :span="3"
        ></el-input>
        <el-col :span="3">
          <el-button
            size="small"
            type="primary"
            @click="loadData"
          >查询</el-button>
        </el-col>
        <el-col
          :span="4"
          :offset="15"
        >
          <el-button
            size="small"
            type="primary"
            style="float:right;"
            @click="gotoAddGift"
          >
            添加赠品活动
          </el-button>
        </el-col>
      </el-row>
    </wrapper>
    <wrapper>
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
                @update="updateGiftLevel(scope.row.id, scope.row.level)"
              />
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
                <!-- <el-button
                  size="mini"
                  @click="disableGift(scope.row.id)"
                  v-show="couldStop(scope.row)"
                >停用</el-button> -->
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="停用"
                  placement="top"
                >
                  <i
                    @click="disableGift(scope.row.id)"
                    v-show="couldStop(scope.row)"
                    class="el-icon-remove-outline"
                    style="color:#409EFF;fontSize:20px"
                  ></i>
                </el-tooltip>
                <!-- <el-button
                  size="mini"
                  @click="enableGift(scope.row.id)"
                  v-show="couldStart(scope.row)"
                >启用</el-button> -->
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="启用"
                  placement="top"
                >
                  <i
                    @click="enableGift(scope.row.id)"
                    v-show="couldStart(scope.row)"
                    class="el-icon-circle-check"
                    style="color:#409EFF;fontSize:20px"
                  ></i>
                </el-tooltip>
                <!-- <el-button
                  size="mini"
                  @click="editGift(scope.row.id)"
                  v-show="couldEdit(scope.row)"
                >编辑</el-button> -->
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="编辑"
                  placement="top"
                >
                  <i
                    @click="editGift(scope.row.id)"
                    v-show="couldEdit(scope.row)"
                    class="el-icon-edit-outline"
                    style="color:#409EFF;fontSize:20px"
                  ></i>
                </el-tooltip>
                <!-- <el-button
                  size="mini"
                  @click="gotoGiftDetail(scope.row.id)"
                >赠送明细</el-button> -->
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="赠送明细"
                  placement="top"
                >
                  <i
                    @click="gotoGiftDetail(scope.row.id)"
                    class="el-icon-present"
                    style="color:#409EFF;fontSize:20px"
                  ></i>
                </el-tooltip>
                <!-- <el-button
                  size="mini"
                  @click="deleteGift(scope.row.id)"
                  v-show="couldDelete(scope.row)"
                >删除</el-button> -->
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="top"
                >
                  <i
                    @click="deleteGift(scope.row.id)"
                    v-show="couldDelete(scope.row)"
                    class="el-icon-delete"
                    style="color:#409EFF;fontSize:20px"
                  ></i>
                </el-tooltip>
              </el-row>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <!-- <el-row>
        <el-col
          :offset="17"
          :span="10"
        > -->

      <!-- <el-pagination
        @size-change="loadData"
        @current-change="loadData"
        :current-page.sync="param.currentPage"
        :page-size="param.pageRows"
        :total="page.pageRows"
        layout="total, sizes, prev, pager, next, jumper"
      >
      </el-pagination> -->
      <!-- </el-col>
      </el-row> -->

      <!-- <div style="background: red"> -->
      <pagination
        :page-params.sync="param"
        @pagination="loadData"
      />
      <!-- </div> -->
    </wrapper>
  </div>
</template>
<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import statusTab from '@/components/admin/status/statusTab'
import inputEdit from '@/components/admin/inputEdit'
import pagination from '@/components/admin/pagination/pagination.vue'
import { getById, couldEdit, couldStop, couldStart, couldDelete } from '@/components/admin/status/status'
import { giftList, deleteGift, disableGift, enableGift, updateGiftLevel } from '@/api/admin/marketManage/gift'

export default {

  components: {
    wrapper,
    statusTab,
    inputEdit,
    pagination
  },
  data () {
    return {
      activityName: '赠品',
      param: {
        // name: '',
        // status: 0,
        // // 分页
        // currentPage: 0,
        // pageRows: 20
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
      }]
    }
  },
  methods: {
    // 列表查询
    loadData () {
      // const { param } = this
      giftList(this.param).then(res => {
        const { content: { page, dataList } } = res
        this.tableData = dataList
        this.param = page
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
.item {
  font-size: 22px;
  color: #66b1ff;
  cursor: pointer;
}
</style>
