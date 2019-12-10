<template>
  <div>
    <div class="technician_list_page">
      <div class="list_info">
        <label style="font-size: 14px;">
          订单号：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.orderSn"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          服务名称：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.serviceName"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          会员手机号：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.mobile"
          ></el-input>
        </label>
        <label style="font-size: 14px;">
          {{$t('technicianList.technicianName')}}：
          <el-input
            size="small"
            class="filter_input"
            :placeholder="$t('technicianList.technicianName')"
            v-model="queryParams.technicianName"
          ></el-input>
        </label>
        <!-- 审核状态下拉 -->
        <label style="font-size: 14px;">审核状态：</label>
        <template>
          <el-select
          size="small"
          style="width:170px;"
          v-model="queryParams.flag"
        >
          <el-option
            v-for="item in chargeStatus"
            :key="item.label"
            :label="item.value"
            :value="item.label"
          ></el-option>
        </el-select>
        </template>
        <label style="font-size: 14px;">评价星级： </label>
        <!-- 评价星级下拉 -->
        <template>
          <el-select
            size="small"
            style="width:170px;"
            v-model="queryParams.commstar"
          >
            <el-option
              v-for="item in starSelect"
              :key="item.label"
              :label="item.value"
              :value="item.label"
            ></el-option>
          </el-select>
        </template>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('technicianList.inquire')}}</el-button>
      </div>
      <div class="list_table">
        <el-table
          ref="technicianTable"
          :data="tableData"
          class="tableClass"
          max-height="500"
          border
          @selection-change="handleSelectionChange"
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'border':'none'
          }"
        >
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            label="服务信息"
          >
            <template slot-scope="{ row }">
              <el-row :gutter=20>
                <el-col :span="20">
                  <label style="font-size: 14px;">订单号： {{row.orderSn}}</label>
                </el-col>
              </el-row>
              <el-row :gutter=20>
                <el-col :span="5">
                  <img :src="row.serviceImg">
                </el-col>
                <el-col :span="15">
                  <label style="font-size: 14px;">{{row.serviceName}}</label>
                </el-col>
              </el-row>
            </template>
          </el-table-column>
          <el-table-column
            label="用户信息"
          >
            <template slot-scope="{ row }">
              <el-row :gutter=20>
                <el-col :span="20">
                  <label style="font-size: 14px;">用户名： {{row.username}}</label>
                </el-col>
              </el-row>
              <el-row :gutter=20>
                <el-col :span="20">
                  <label style="font-size: 14px;">手机号： {{row.mobile}}</label>
                </el-col>
              </el-row>
            </template>
          </el-table-column>
          <el-table-column
            label="评价内容"
          >
            <template slot-scope="{ row }">
              <el-row :gutter=20>
                <el-col :span="20">
                  <label style="font-size: 14px;">评分： {{row.commstar}}</label>
                  <img :src="imgHost + '/image/admin/comstar_{{row.commstar}}.png'" alt="">
                </el-col>
              </el-row>
              <el-row :gutter=20>
                <el-col :span="20">
                  <label style="font-size: 14px;">评价：{{row.commNote}}</label>
                </el-col>
              </el-row>
              <el-row :gutter=20>
                <el-col :span="20">
                  <img :src="row.commImg">
                </el-col>
              </el-row>
            </template>
          </el-table-column>
          <el-table-column
            label="技师名称"
            prop="technicianName"
          ></el-table-column>
          <el-table-column
            label="评价时间"
            prop="createTime"
          ></el-table-column>
          <el-table-column
            label="匿名评价"
            prop="anonymousflag"
          ></el-table-column>
          <el-table-column
            label="审核状态"
            prop="flag"
          ></el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
            align="center"
          >
            <template slot-scope="{ row }">
              <div style="margin-top:10px;">
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    @click="singleDelete(row.id)"
                  >删除</span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    v-if="row.flag!==1"
                    @click="singlePass(row.id)"
                  >通过</span>
                </el-tooltip>
                <el-tooltip :content="$t('technicianList.edit')">
                  <span
                    class="iconSpan"
                    v-if="row.flag===0"
                    @click="singleRefuse(row.id)"
                  >拒绝</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 15px">
          <el-row :gutter=25>
            <el-col :span="15">
              <el-button
                type="primary"
                size="small"
                @click="batchDelete"
              >批量删除</el-button>
              <el-button
                type="primary"
                size="small"
                @click="batchPass"
              >批量通过</el-button>
              <el-button
                type="primary"
                size="small"
                @click="batchRefuse"
              >批量拒绝</el-button>
            </el-col>
            <el-col :span="9">
              <pagination
                :page-params.sync="pageParams"
                @pagination="initDataList"
              ></pagination>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getList, batchDel, pass, refuse } from '@/api/admin/storeManage/storemanage/evaluationManage'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      chargeStatus: [{
        label: -1,
        value: '全部'
      }, {
        label: 0,
        value: '待审核'
      }, {
        label: 1,
        value: '通过'
      }, {
        label: 2,
        value: '未通过'
      }],
      starSelect: [{
        label: 0,
        value: '全部'
      }, {
        label: 1,
        value: '一星'
      }, {
        label: 2,
        value: '二星'
      }, {
        label: 3,
        value: '三星'
      }, {
        label: 4,
        value: '四星'
      }, {
        label: 5,
        value: '五星'
      }],
      queryParams: {
        orderSn: '',
        serviceName: '',
        storeId: 0,
        mobile: '',
        technicianName: '',
        commstar: 0,
        flag: -1
      },
      tableData: [],
      multipleSelection: [],
      pageParams: {},
      imgHost: `${this.$imageHost}`
    }
  },
  created () {
    this.queryParams.storeId = this.$route.query.id
    this.langDefault()
    this.initDataList()
  },
  methods: {
    handleSelectionChange (val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    },
    // single删除
    singleDelete (id) {
      this.$confirm('此操作将删除该评价, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          commentIdList: []
        }
        obj.commentIdList.push(id)
        batchDel(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功！')
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除！')
      })
    },
    // 批量删除
    batchDelete () {
      if (this.multipleSelection.length === 0) {
        this.$message.info('至少选中一行记录！')
      } else {
        this.$confirm('此操作将删除所有选中评价, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let obj = {
            commentIdList: []
          }
          this.multipleSelection.map((item, index) => {
            obj.commentIdList.push(item.id)
          })
          batchDel(obj).then(res => {
            if (res.error === 0) {
              this.$message.success('删除成功！')
              this.initDataList()
            }
          })
        }).catch(() => {
          this.$message.info('已取消删除！')
        })
      }
    },
    // 审核拒绝
    singleRefuse (id) {
      this.$confirm('此操作将拒绝该评价, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          commentIdList: []
        }
        obj.commentIdList.push(id)
        refuse(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('审核拒绝！')
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消操作！')
      })
    },
    // 批量拒绝
    batchRefuse () {
      if (this.multipleSelection.length === 0) {
        this.$message.info('至少选中一行记录！')
      } else {
        this.$confirm('此操作将拒绝所有选中评价, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let obj = {
            commentIdList: []
          }
          this.multipleSelection.map((item, index) => {
            obj.commentIdList.push(item.id)
          })
          refuse(obj).then(res => {
            if (res.error === 0) {
              this.$message.success('拒绝成功！')
              this.initDataList()
            }
          })
        }).catch(() => {
          this.$message.info('已取消操作！')
        })
      }
    },
    // 审核通过
    singlePass (id) {
      this.$confirm('此操作将通过该评价, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          commentIdList: []
        }
        obj.commentIdList.push(id)
        pass(obj).then(res => {
          if (res.error === 0) {
            this.$message.success('审核通过！')
            this.initDataList()
          }
        })
      }).catch(() => {
        this.$message.info('已取消操作！')
      })
    },
    // 批量通过
    batchPass () {
      if (this.multipleSelection.length === 0) {
        this.$message.info('至少选中一行记录！')
      } else {
        this.$confirm('此操作将通过所有选中评价, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let obj = {
            commentIdList: []
          }
          this.multipleSelection.map((item, index) => {
            obj.commentIdList.push(item.id)
          })
          pass(obj).then(res => {
            if (res.error === 0) {
              this.$message.success('审核通过！')
              this.initDataList()
            }
          })
        }).catch(() => {
          this.$message.info('已取消操作！')
        })
      }
    },
    initDataList () {
      if (this.queryParams.commstar === 0) {
        this.queryParams.commstar = null
      }
      if (this.queryParams.flag === -1) {
        this.queryParams.flag = null
      }
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getList(params).then(res => {
        if (res.error === 0) {
          this.tableData = [...res.content.dataList]
          this.pageParams = Object.assign({}, res.content.page)
          this.tableData.map((item, index) => {
            if (item.anonymousflag === 0) {
              item.anonymousflag = '否'
            } else {
              item.anonymousflag = '是'
            }
            switch (item.flag) {
              case 0:
                item.flag = '未审批'
                break
              case 1:
                item.flag = '审批通过'
                break
              case 2:
                item.flag = '审批未通过'
                break
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .technician_list_page {
    margin: 0 25px;
    .list_info {
      padding-bottom: 10px;
      .filter_input {
        width: 170px;
      }
      .technician_list_img {
        display: inline-block;
        width: 60px;
        height: 60px;
      }
    }
    .list_table {
      .iconSpan {
        color: #5a8bff;
        text-decoration: none;
        cursor: pointer !important;
      }
    }
  }
</style>
