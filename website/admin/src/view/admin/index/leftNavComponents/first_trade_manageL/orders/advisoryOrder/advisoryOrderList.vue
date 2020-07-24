<template>
  <div class="main1">
    <div class="nav_box">
        <div class="filters">
            <div class="filters_item">
                <span class="fil_span">医生：</span>
                <el-input
                    v-model="queryParams.doctorName"
                    size="small"
                    style="width:150px;"
                    placeholder="请输入医生姓名"
                ></el-input>
            </div>
            <div class="filters_item">
                <span class="fil_span">科室：</span>
                <el-select
                    size="small"
                    clearable
                    class="timeSelect"
                >
                    <el-option>123</el-option>
                </el-select>
            </div>
            <div class="filters_item">
                <span class="fil_span">患者：</span>
                <el-input
                    v-model="queryParams.patientName"
                    size="small"
                    style="width:150px;"
                    placeholder="请输入患者姓名"
                ></el-input>
            </div>
            <div class="filters_item">
                <span class="fil_span">订单状态：</span>
                <el-select
                    size="small"
                    clearable
                    class="timeSelect"
                >
                    <el-option>123</el-option>
                </el-select>
            </div>
            <div class="btn_wrap">
                <el-button
                    type='primary'
                    size='small'
                >搜索</el-button>
            </div>
        </div>
    </div>
    <div class="table_box">
        <el-table
          v-loading='loading'
          :data='tableData'
          style="width:100%"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none',
            'color': '#000'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
            <el-table-column
                prop=''
                label='医生'
            ></el-table-column>
            <el-table-column
                prop=''
                label='患者'
            ></el-table-column>
            <el-table-column
                prop=''
                label='科室'
            ></el-table-column>
            <el-table-column
                prop=''
                label='咨询费'
            ></el-table-column>
            <el-table-column
                prop=''
                label='订单状态'
            ></el-table-column>
            <el-table-column
                prop=''
                label='提交时间'
            ></el-table-column>
            <el-table-column
                label='操作'
            >
                <template slot-scope="scope">
                    <div class="operation">
                        <a href="##">退款</a>
                        <a href="##" @click='toDetail'>详情</a>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
        />
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: {
    pagination
  },
  data () {
    return {
      queryParams: {
        doctorName: null,
        patientName: null
      },
      pageParams: {},
      tableData: []
    }
  },
  methods: {
    toDetail () {
      this.$router.push({name: 'advisory_order_info'})
      console.log(this.$router)
    }
  }
}
</script>

<style scoped lang='scss'>
.main1{
    margin: 10px;
    min-width: auto !important;
    .nav_box{
        display: flex;
        width: 100%;
        background-color: #fff;
        padding: 15px;
        .filters{
            flex: 2;
            display: flex;
            flex-wrap: wrap;
            line-height: 32px;
            margin-left: -15px;
            .filters_item {
                display: flex;
                justify-content: flex-end;
                margin-left: 15px;
                > span {
                    width: 80px;
                    font-size: 14px;
                    text-align: right;
                }
                .timeSelect {
                    width: 140px;
                    margin: 0 10px 0 10px;
                }
            }
        }
    }
    .table_box{
        padding: 10px;
        background: #fff;
    }
}
</style>
