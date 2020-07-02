<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <div class="filters">
          <div class="filters_item">
            <span>手机号：</span>
            <el-input
              v-model="queryParams.doctorNumber"
              size="small"
              style="width:190px;"
              placeholder="请输入患者手机号"
            >
            </el-input>
          </div>
          <div class="filters_item">
            <span>姓名：</span>
            <el-input
              v-model="queryParams.doctorName"
              size="small"
              style="width:190px;"
              placeholder="请输入姓名"
            >
            </el-input>
          </div>
          <div class="btn_wrap">
            <el-button
              type='primary'
              size='small'
              @click="initDataList"
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
            prop='storeName'
            label='患者编号'
          ></el-table-column>
          <el-table-column
            prop='posShopId'
            label='姓名'
          ></el-table-column>
          <el-table-column
            prop='groupName'
            label='手机号'
          ></el-table-column>
          <el-table-column
            prop='registeredHospital'
            label='就诊卡号'
          ></el-table-column>
          <el-table-column
            prop='department'
            label='疾病史'
          ></el-table-column>
          <el-table-column
            prop='jobTitle'
            label='过敏史'
          ></el-table-column>
          <el-table-column
            prop='registeredTime'
            label='注册时间'
          ></el-table-column>
          <el-table-column label='操作'>
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看详情"
                  placement="top"
                >
                  <a @click='handleSeeMessage(134)'>查看详情</a>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
// import pagination from '@/components/admin/pagination/pagination'
export default {
  //   components: { pagination },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      pageParams: {},
      tableData: [{
        storeName: 'liuyang'
      }
      ],
      storeGroup: [],
      queryParams: {
        doctorNumber: null,
        doctorName: null,
        mobile: null
      },
      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      // storeList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
      //   console.log(res)
      //   this.originalData = res.content.storePageListVo.dataList
      //   let originalData = JSON.parse(JSON.stringify(this.originalData))
      //   this.handleData(originalData)
      //   this.loading = false
      // })
    },
    handleSeeMessage (userId) {
      this.$router.push({
        name: 'patient_message',
        query: {
          userId: userId
        }
      })
    }
    // handleData (data) {
    //   this.tableData = data
    //   this.langDefaultFlag = true
    // }
  }
  // watch: {
  //   lang () {
  //     if (this.langDefaultFlag) {
  //       // 重新渲染表格数据
  //       let originalData = JSON.parse(JSON.stringify(this.originalData))
  //       this.handleData(originalData)
  //     }
  //   }
  // },
  // mounted () {
  //   this.initDataList()
  // }
}
</script>

<style scoped lang='scss'>
.main {
  padding: 10px;
  .navBox {
    display: flex;
    width: 100%;
    background-color: #fff;
    padding: 15px;
    .filters {
      flex: 2;
      display: flex;
      flex-wrap: wrap;
      line-height: 32px;
      margin-left: -15px;
      .filters_item {
        width: 250px;
        display: flex;
        justify-content: flex-end;
        margin-left: 15px;
        > span {
          width: 120px;
          font-size: 14px;
          text-align: right;
        }
      }
      .btn_wrap {
        margin-left: 20px;
      }
    }
  }
  .table_box {
    padding: 10px;
    background: #fff;
    margin-top: 10px;
    a {
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
</style>
