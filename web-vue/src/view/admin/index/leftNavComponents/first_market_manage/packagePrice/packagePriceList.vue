<template>
  <div>
    <wrapper>
      <statusTab
        v-model="param.tabStatus"
        :activityName="activityName"
        :standard="true"
      ></statusTab>
      <el-row>
        <el-col :span="24">
          <el-form :inline="true">
            <el-form-item label="活动名称">
              <el-input v-model="param.name"></el-input>
            </el-form-item>
            <el-form-item label="活动时间">
              <el-date-picker
                type="datetimerange"
                value-format="yyyy-MM-dd HH:mm:ss"
                v-model="dateRange"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button
                @click="onSubmit"
                type="primary"
              >筛选</el-button>
              <el-button
                type="reset"
                @click="reset"
              >重置</el-button>
            </el-form-item>
            <el-row>
              <el-col>
                <el-button
                  type="primary"
                  @click="onSubmit"
                >添加打包一口价活动</el-button>
              </el-col>
            </el-row>
          </el-form>
        </el-col>
      </el-row>
    </wrapper>
    <wrapper>
      <div class="table_list">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableInfo"
        >
          <el-table-column
            prop="packageName"
            label="活动名称"
            align="center"
          ></el-table-column>
          <el-table-column
            label="活动时间"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.startTime}}
              <br />至<br />
              {{scope.row.endTime}}
            </template>
          </el-table-column>
          <el-table-column
            label="活动信息"
            align="center"
          >
            <template slot-scope="scope">
              {{scope.row.activityInfo}}<br />
              {{scope.row.activityGroup1}}
              <span v-show="scope.row.goodsGroup2 === 1"><br />{{scope.row.activityGroup2}}</span>
              <span v-show="scope.row.goodsGroup3 === 1"><br />{{scope.row.activityGroup3}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="purchasedNum"
            label="已购商品数量"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="orderNum"
            label="订单数量"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="userNum"
            label="下单用户数"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="expireString"
            label="活动状态"
            align="center"
          ></el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="opt">
                <span @click="gotoEdit(scope.row.id)">编辑</span>
                <span @click="shareHandle(scope.row.id)">分享</span>
                <span @click="disableActivity(scope.row.id)">停用</span>
                <span @click="enableActivity(scope.row.id)">启用</span>
                <span @click="gotoOrder(scope.row.id)">查看活动订单</span>
                <span @click="gotoDetail(scope.row.id)">活动明细</span>
                <span @click="deleteActivity(scope.row.id)">删除</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageInfo"
          @pagination="loadTable"
        />
      </div>
    </wrapper>
    <shareDialog
      :imgPath="shareImgPath"
      :pagePath="sharePagePath"
      :show="shareDialogShow"
      @close="shareDialogShow=false"
    />
  </div>
</template>
<script>
import pagination from '@/components/admin/pagination/pagination'
import wrapper from '@/components/admin/wrapper/wrapper'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import status from '@/components/admin/marketManage/status/status'
import { packagePriceList, shareActivity, enableActivity, disableActivity, deleteActivity } from '@/api/admin/marketManage/packagePrice.js'
import shareDialog from '@/components/admin/shareDialog'
export default {
  components: {
    wrapper,
    statusTab,
    status,
    pagination,
    shareDialog
  },
  data () {
    return {
      activityName: '打包一口价',
      shareImgPath: '',
      sharePagePath: '',
      shareDialogShow: false,
      dateRange: [],
      param: {
        name: '',
        startTime: '',
        endTime: '',
        tabStatus: 0
      },
      tableInfo: [],
      pageInfo: {}
    }
  },
  methods: {
    onSubmit () {
      this.pageInfo.currentPage = 1
      this.loadTable()
    },
    reset () {
      this.param.name = ''
      this.dateRange = ''
    },
    loadTable () {
      this.pageInfo.name = this.param.name
      this.pageInfo.startTime = this.param.startTime
      this.pageInfo.endTime = this.param.endTime
      this.pageInfo.activityStatus = this.param.tabStatus
      if (this.dateRange != null) {
        this.pageInfo.startTime = this.dateRange[0]
        this.pageInfo.endTime = this.dateRange[1]
      }
      packagePriceList(this.pageInfo).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.handData(res.content.dataList)
          this.pageInfo = res.content.page
        }
      })
    },
    handData (data) {
      data.map((item, index) => {
        item.num = item.goodsNumber1
        item.activityGroup1 = `${item.groupName1}:${item.goodsNumber1}件`
        if (item.goodsGroup2 === 1) {
          item.activityGroup2 = `${item.groupName2}:${item.goodsNumber2}件`
          item.num += item.goodsNumber2
        }
        if (item.goodsGroup3 === 1) {
          item.activityGroup3 = `${item.groupName3}:${item.goodsNumber3}件`
          item.num += item.goodsNumber3
        }
        item.activityInfo = `${item.totalMoney}元${item.num}件`
        item.expireString = this.getExpireString(item.activityStatus)
      })
      this.tableInfo = data
    },
    shareHandle (id) {
      shareActivity(id).then(res => {
        if (res.error === 0) {
          this.shareImgPath = res.content.imgUrl
          this.sharePagePath = res.content.pageUrl
          this.shareDialogShow = true
        }
      })
    },
    enableActivity (id) {
      this.$alert('确定要启用吗？', '提示', {
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning' }).then(() => {
        enableActivity(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message('启用成功')
            this.loadTable()
          } else {
            this.$message('启用失败')
          }
        })
      })
    },
    disableActivity (id) {
      this.$alert('确定要停用吗？', '提示', {
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning' }).then(() => {
        disableActivity(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message('停用成功')
            this.loadTable()
          } else {
            this.$message('停用失败')
          }
        })
      })
    },
    deleteActivity (id) {
      this.$alert('确定要删除吗？', '提示', {
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning' }).then(() => {
        deleteActivity(id).then(res => {
          console.log(res)
          if (res.error === 0) {
            this.$message('删除成功')
            this.loadTable()
          } else {
            this.$message('删除失败')
          }
        })
      })
    },
    // 对过期状态值设置对应显示
    getExpireString (expire) {
      if (expire === 1) {
        return '进行中'
      } else if (expire === 2) {
        return '未开始'
      } else if (expire === 3) {
        return '已过期'
      } else if (expire === 4) {
        return '已停用'
      }
    },
    gotoDetail (id) {
      this.$router.push(`/admin/home/main/packsale/detail/${id}`)
    }
  },
  watch: {
    'param.tabStatus' (n, o) {
      this.onSubmit()
    }
  },
  mounted () {
    this.onSubmit()
  }
}
</script>
<style lang="scss" scoped>
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
</style>
