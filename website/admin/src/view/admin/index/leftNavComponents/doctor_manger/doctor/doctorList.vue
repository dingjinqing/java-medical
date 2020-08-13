<template>
  <div class="main">
    <div class="navBox">
      <div class="filters">
        <!-- <div class="filters_item">
          <span>医师院内编号：</span>
          <el-input
            v-model="queryParams.hospitalCode"
            size="small"
            style="width:190px;"
            placeholder="请输入医师院内编号"
          >
          </el-input>
        </div> -->
        <div class="filters_item">
          <span>姓名：</span>
          <el-input
            v-model="queryParams.name"
            size="small"
            style="width:190px;"
            placeholder="请输入姓名"
          >
          </el-input>
        </div>
        <div class="filters_item">
          <span>科室：</span>
          <el-input
            v-model="queryParams.departmentName"
            size="small"
            style="width:190px;"
            placeholder="请输入科室"
          >
          </el-input>
        </div>
        <div class="btn_wrap">
          <el-button
            type='primary'
            size='small'
            @click="initDataList"
          >搜索</el-button>
          <el-button
            type="primary"
            size="small"
            @click='handleAddDoctor'
          >添加</el-button>
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
            prop='hospitalCode'
            label='医师院内编号'
          ></el-table-column>
          <el-table-column
            label='名称'
          >
            <template slot-scope="scope">
              <div class="doc_name_url">
                <img
                  class="doc_img"
                  v-if='scope.row.url'
                  :src="scope.row.url"
                >
                <div>{{scope.row.name}}</div>
              </div>
            </template>
          </el-table-column>
          <!-- <el-table-column
            prop='name'
            label='姓名'
          ></el-table-column> -->
          <el-table-column
            prop='age'
            label='年龄'
          ></el-table-column>
          <el-table-column
            prop='departmentNames'
            label='科室'
          ></el-table-column>
          <el-table-column
            prop='titleName'
            label='职称'
          ></el-table-column>
          <el-table-column
            prop='mobile'
            label='手机号'
          ></el-table-column>
          <el-table-column
            prop='workTime'
            label='从业时间'
          ></el-table-column>
          <el-table-column
            label='操作'
          >
            <template slot-scope="scope">
              <div class="operation">
                <a
                  href="javaScript:void(0);"
                  class="same_btn"
                  @click="editDoctor(scope.row.id)"
                >编辑</a>
                <a
                  href="javaScript:void(0);"
                  class="same_btn"
                  v-if="scope.row.status == 1"
                  @click="puaseDoctor(scope.row)"
                >停用</a>
                <a
                  href="javaScript:void(0);"
                  class="same_btn"
                  v-if="scope.row.status == 0"
                  @click="beginDoctor(scope.row)"
                >启用</a>
                <a
                  href="javaScript:void(0);"
                  class="same_btn"
                >解除绑定</a>
                <a
                  href="javaScript:void(0);"
                  class="same_btn"
                >停止问诊</a>
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
</template>

<script>
import { doctorList, enableDoctor } from '@/api/admin/doctorManage/doctorInfo/doctor'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,
      pageParams: {},
      tableData: [],
      queryParams: {
        name: null,
        departmentName: null
      },
      // 表格原始数据
      originalData: [],
      imgHost: `${this.$imageHost}`
    }
  },
  methods: {
    // 数据初始化
    initDataList () {
      this.loading = true
      if (this.queryParams.name === '') {
        this.queryParams.name = null
      }
      if (this.queryParams.departmentName === '') {
        this.queryParams.departmentName = null
      }
      doctorList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
        console.log(res)
        this.originalData = res.content.dataList
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        for (let i in originalData) {
          if (originalData[i].departmentNames) {
            originalData[i].departmentNames = originalData[i].departmentNames.join('，')
          }
          if (originalData[i].workTime !== null && originalData[i].workTime !== 0) {
            originalData[i].workTime = originalData[i].workTime.substr(0, 10)
          }
          if (originalData[i].url === '') {
            originalData[i].url = this.imgHost + '/image/admin/doc_url_default.png'
          } else {
            originalData[i].url = this.imgHost + '/' + originalData[i].url
          }
        }
        console.log(originalData)
        this.handleData(originalData)
        this.pageParams = res.content.page
        this.loading = false
      })
    },
    // 渲染数据
    handleData (data) {
      this.tableData = data
      this.langDefaultFlag = true
    },
    // 添加医师
    handleAddDoctor () {
      this.$router.push({name: 'addDoctor'})
      console.log(this.$router)
    },
    // 停用
    puaseDoctor (row) {
      let params = {
        id: row.id,
        status: 0
      }
      this.$confirm('此操作将停用该医生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableDoctor(params).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: '停用成功！' })
            this.initDataList()
          } else {
            this.$message.error({ message: '停用失败' })
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消停用' })
      })
    },
    // 启用
    beginDoctor (row) {
      let params = {
        id: row.id,
        status: 1
      }
      this.$confirm('此操作将启用该医生, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableDoctor(params).then(res => {
          if (res.error === 0) {
            this.$message.success({ message: '启用成功！' })
            this.initDataList()
          } else {
            this.$message.error({ message: '启用失败' })
          }
        })
      }).catch(() => {
        this.$message.info({ message: '已取消启用' })
      })
    },
    // 编辑
    editDoctor (id) {
      this.$router.push({
        path: '/admin/home/main/doctor/updateDoctor',
        query: {
          id: id
        }
      })
    }
  },
  watch: {
    lang () {
      if (this.langDefaultFlag) {
        // 重新渲染表格数据
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
      }
    }
  },
  mounted () {
    this.initDataList()
  }
}
</script>

<style scoped lang='scss'>
@import "@/assets/aliIcon/iconfont.scss";
.main{
    .navBox{
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
                width: 270px;
                display: flex;
                justify-content: flex-end;
                margin-left: 15px;
                > span {
                    width: 140px;
                    font-size: 14px;
                    text-align: right;
                }
            }
            .btn_wrap{
                margin-left: 20px;
            }
        }
    }
    .table_box{
        padding: 10px;
        background: #fff;
        margin-top: 10px;
        .doc_name_url{
          display: flex;
          align-items: center;
          justify-content: center;
          .doc_img{
            width: 45px;
            height: 45px;
            margin-right: 10px;
            border-radius: 100px;
            border: 1px solid #eee;
          }
        }
        .operation {
          display: flex;
          justify-content: center;
          > .same_btn{
              font-size: 12px;
              text-decoration: none;
              cursor: pointer;
              margin-right: 8px;
              color: #5a8bff;
            }
        }
    }
}
</style>
