<template>
  <div class="main-container">
    <div class="card-content">
      <div class="main-title">
        <span>出勤统计</span>
      </div>
      <div class="panel-content">
        <div class="canvas-content">
          <ve-pie
            :data="chartData"
            width
            height="350px"
            :extend="chartEntend"
          />
        </div>
        <div class="table-content">
          <div>
            <el-table
              :data="docterAttendanceTable"
              border
              header-row-class-name="tableClss"
            >
              <el-table-column
                label="医师姓名"
                prop="doctorName"
                align="center"
              ></el-table-column>
              <el-table-column
                label="最近登录时间"
                prop="loginTime"
                align="center"
              ></el-table-column>
              <el-table-column
                label="出勤率"
                prop="attendance"
                align="center"
              ></el-table-column>
              <el-table-column
                label="院内排名"
                prop="rank"
                align="center"
              ></el-table-column>
            </el-table>
            <pagination
              :page-params.sync="docterAttendancePageParams"
              @pagination="initDataList"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="card-content">
      <div class="main-title">
        <span>业绩统计</span>
      </div>
      <div style="margin-top: 20px">
        <el-table
          :data="docterPerformanceTable"
          border
          header-row-class-name="tableClss"
        >
          <el-table-column
            label="医师姓名"
            prop="doctorName"
            align="center"
          ></el-table-column>
          <el-table-column
            label="科室"
            prop="department"
            align="center"
          ></el-table-column>
          <el-table-column
            label="累计处方数"
            prop="total1"
            align="center"
          ></el-table-column>
          <el-table-column
            label="累计处方金额"
            prop="total2"
            align="center"
          ></el-table-column>
          <el-table-column
            label="累计咨询单数"
            prop="total3"
            align="center"
          ></el-table-column>
          <el-table-column
            label="累计咨询金额"
            prop="total4"
            align="center"
          ></el-table-column>
          <el-table-column
            label="累计消费金额"
            prop="total5"
            align="center"
          ></el-table-column>
          <el-table-column
            label="院内排名"
            prop="rank"
            align="center"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="docterPerformancePageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination'),
    've-pie': () => import('v-charts/lib/pie.common')
  },
  data () {
    return {
      docterAttendanceTable: [
        { doctorName: '刘医师', loginTime: '2020-08-20 19:47:56', attendance: '70%', rank: '1' },
        { doctorName: '薛院长', loginTime: '2020-08-20 19:47:56', attendance: '80%', rank: '2' },
        { doctorName: '陈医师', loginTime: '2020-08-20 19:47:56', attendance: '90%', rank: '3' },
        { doctorName: '孙院长', loginTime: '2020-08-20 19:47:56', attendance: '40%', rank: '4' },
        { doctorName: '李医师', loginTime: '2020-08-20 19:47:56', attendance: '30%', rank: '5' }
      ],
      docterPerformanceTable: [
        { doctorName: '刘医师', department: '口腔科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '薛院长', department: '泌尿科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '陈医师', department: '内分泌', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '孙院长', department: '急诊', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '李医师', department: '皮肤科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '刘医师', department: '口腔科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '薛院长', department: '泌尿科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '陈医师', department: '内分泌', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '孙院长', department: '急诊', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '李医师', department: '皮肤科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '刘医师', department: '口腔科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '薛院长', department: '泌尿科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '陈医师', department: '内分泌', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '孙院长', department: '急诊', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '李医师', department: '皮肤科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '刘医师', department: '口腔科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '薛院长', department: '泌尿科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '陈医师', department: '内分泌', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '孙院长', department: '急诊', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' },
        { doctorName: '李医师', department: '皮肤科', total1: '70%', total2: '100', total3: '300', total4: '500', total5: '600', rank: '1' }
      ],
      docterAttendancePageParams: {},
      docterPerformancePageParams: {},
      chartData: {
        columns: ['日期', '访问用户'],
        rows: [
          { '日期': '1/1', '访问用户': 1393 },
          { '日期': '1/2', '访问用户': 3530 },
          { '日期': '1/3', '访问用户': 2923 },
          { '日期': '1/4', '访问用户': 1723 },
          { '日期': '1/5', '访问用户': 3792 },
          { '日期': '1/6', '访问用户': 4593 }
        ]
      },
      chartEntend: {
        legend: {
          // 可查看官方配置文档，left：'center'的意思是居中
          left: 'right',
          // 垂直排列
          orient: 'vertical',
          // 设置高度
          height: '100px',
          bottom: 100
        }
      }
    }
  },
  methods: {
    initDataList () {

    }
  }
}
</script>

<style lang="scss" scoped>
.main-container {
  padding: 10px;
  display: flex;
  flex-direction: column;
  .card-content {
    background-color: #fff;
    padding: 10px;
    & + .card-content {
      margin-top: 10px;
    }
    .main-title {
      height: 40px;
      background-color: #eef1f6;
      line-height: 40px;
      span {
        color: #333;
        font-size: 14px;
        font-weight: 600;
        margin-left: 28px;
        position: relative;
        &::before {
          content: ' ';
          position: absolute;
          height: 100%;
          width: 2px;
          background-color: #5a8bff;
          top: 0;
          left: -14px;
        }
      }
    }
    .panel-content {
      display: flex;
      margin-top: 20px;
      height: 350px;
      > div {
        flex: 1;
        min-width: 0;
        overflow: hidden;
        position: relative;
        &.table-content {
          > div {
            position: absolute;
            width: 100%;
          }
        }
        &.canvas-content {
          /deep/ canvas {
            top: -50px !important;
            left: -130px !important;
          }
        }
      }
    }
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
</style>
