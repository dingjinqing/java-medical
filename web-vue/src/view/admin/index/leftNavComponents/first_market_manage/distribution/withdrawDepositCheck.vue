<template>
  <div class="distributorListContent">
    <el-form
      :model="param"
      label-width="120px"
      :label-position="'right'"
    >
      <div>
        <el-form-item
          label="申请人昵称："
          class="item"
        >
          <el-input
            v-model="param.username"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="手机号："
          class="item"
        >
          <el-input
            v-model="param.mobile"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="真实姓名："
          class="item"
        >
          <el-input
            v-model="param.realName"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
      </div>
      <div>
        <el-form-item
          label="提现单号："
          class="item"
        >
          <el-input
            v-model="param.orderSn"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="申请时间："
          class="item"
        >
          <el-date-picker
            v-model="param.startTime"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd 00:00:00"
            size="small"
            class="inputWidth"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            v-model="param.endTime"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd 23:59:59"
            size="small"
            class="inputWidth"
          >
          </el-date-picker>
        </el-form-item>
      </div>
      <div>
        <el-form-item
          label="提现金额："
          class="item"
        >
          <el-input
            v-model="param.minCash"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
          <span>至</span>
          <el-input
            v-model="param.maxCash"
            placeholder="请填写内容"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="处理状态："
          class="item"
        >
          <el-select
            v-model="param.status"
            placeholder="请选择"
            size="small"
            class="inputWidth"
          >
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          class="item"
          label-width="20px"
        >
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
          <el-button size="small">重置筛选</el-button>
          <el-button size="small">导出</el-button>
        </el-form-item>
      </div>
    </el-form>

    <div class="list">
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >

        <el-table-column
          prop="username"
          label="申请人"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="mobile"
          label="手机号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="realName"
          label="真实姓名"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="createTime"
          label="申请时间"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="orderSn"
          label="提现单号"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="withdrawCash"
          label="提现金额"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="checkTime"
          label="操作时间"
          align="
          center"
        >
        </el-table-column>

        <el-table-column
          prop="status"
          label="处理状态"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="refuseDesc"
          label="驳回原因"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="desc"
          label="处理备注"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop=""
          label="操作"
          align="center"
        >
          <template slot-scope="scope">
            <div class="opt">
              <p @click="withdrawDetail(scope.row.id)">查看详情</p>

            </div>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <pagination
      :page-params.sync="pageParams"
      @pagination="list"
    />
  </div>
</template>

<script>
import { withdrawCheck } from '@/api/admin/marketManage/distribution.js'
// 引入分页
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      tableData: [],
      param: {
        username: '',
        mobile: '',
        realName: '',
        orderSn: '',
        startTime: '',
        endTime: '',
        minCash: '',
        maxCash: '',
        status: ''
      },
      statusList: [
        {
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
        }
      ],

      pageParams: {}
    }
  },
  mounted () {
    this.list()
  },
  methods: {
    // 提现审核列表
    list () {
      withdrawCheck(this.pageParams).then(res => {
        if (res.error === 0) {
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    // 查看提现详情
    withdrawDetail (id) {
      this.$router.push({
        path: '/admin/home/main/distribution/withdraw/detail',
        query: {
          id: id
        }
      })
    }
  }

}

</script>
<style lang="scss" scoped>
.distributorListContent {
  padding: 8px;
  padding-bottom: 38px;
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
}
.searchInfo_main {
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  margin-bottom: 10px;
}
.li {
  padding: 8px 0;
  display: flex;
}
.liNav {
  width: 280px;
  display: flex;
}
.liNav span {
  display: block;
  width: 80px;
  line-height: 30px;
  height: 30px;
  text-align: right;
  color: #333;
  margin-right: 25px;
}
.labelClass {
  width: 180px !important;
}
.timeInput {
  width: 180px;
}
.list {
  margin-top: 10px;
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
  text-align: center;
  color: #5a8bff;
}
.item {
  display: inline-block;
}
.inputWidth {
  width: 170px;
}
</style>
