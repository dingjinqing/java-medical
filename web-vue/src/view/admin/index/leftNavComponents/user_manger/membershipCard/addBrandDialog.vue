<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-dialog
        title="添加商品品牌"
        :visible.sync="dialogVisible"
        width="40%"
      >
        <div class="dialogTop">
          <div class="topList">
            <span>品牌名称:</span>
            <el-input
              v-model="input"
              placeholder="请输入内容"
              size="small"
            ></el-input>
          </div>
          <div class="topList">
            <span>品牌分类:</span>
            <el-select
              v-model="classValue"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in classOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <el-button
            size="small"
            type="primary"
          >主要按钮</el-button>
        </div>
        <div class="dialogMiddle">
          <div class="topList">
            <span>品牌来源:</span>
            <el-select
              v-model="fromValue"
              placeholder="请选择品牌来源"
              size="small"
            >
              <el-option
                v-for="item in fromOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="footer">
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
            @selection-change="changeFun"
          >
            <el-table-column
              align="center"
              width="50"
              type="selection"
            >
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.ischeck"></el-checkbox>

              </template>
            </el-table-column>
            <el-table-column
              prop="pageName"
              label="品牌名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="pageClass"
              label="品牌分类"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="creatTime"
              label="创建时间"
              align="center"
            >

            </el-table-column>

          </el-table>
          <div class="pagination">
            <div>{{$t('programVersion.currentPage')}}：{{this.currentPage}}，{{$t('programVersion.totalPage')}}：{{this.pageCount}}，{{$t('programVersion.totalRecord')}}：{{this.totle}}</div>
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="20"
              layout="prev, pager, next, jumper"
              :total="totle"
            >
            </el-pagination>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="handleToSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
export default {
  data () {
    return {
      currentPage: 1,
      totle: 1,
      pageCount: 1,
      dialogVisible: false,
      input: '',
      classValue: '',
      classOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      fromValue: '',
      fromOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }],
      tableData: [
        {
          ischeck: false,
          pageName: '尾浦巴普电商运营',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: true,
          pageClass: '测试页面'

        },
        {
          ischeck: false,
          pageName: '测试页面',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: false,
          pageClass: '测试页面'

        },
        {
          ischeck: false,
          pageName: '帅飞',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: false,
          pageClass: '测试页面'

        },
        {
          ischeck: false,
          pageName: '帅飞啊',
          creatTime: '2018-05-14 13:22:07',
          isFirstPage: false,
          pageClass: '测试页面'

        }
      ]
    }
  },
  watch: {
    allChecked (newData) {
      console.log(newData)
      switch (newData) {
        case true:
          this.tableData.map((item, index) => {
            item.ischeck = true
          })
          break
        case false:
          this.tableData.map((item, index) => {
            item.ischeck = false
          })
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defalutData()
  },
  methods: {
    defalutData () {
      this.$http.$on('CallAddBrand', (res, flag) => {
        console.log(res, flag)

        this.tableData.forEach(item => {
          item.ischeck = false
        })
        if (flag) {
          this.tableData.forEach(item => {
            flag.forEach(itemC => {
              if (item.pageName === itemC.pageName) {
                item.ischeck = true
              }
            })
          })
        }
        this.dialogVisible = true
      })
    },
    // render事件
    renderHeader (h, { column }) {
      return <el-checkbox v-model=''></el-checkbox>
    },
    changeFun (val) {
      if (val.length) {
        this.tableData.forEach((item, index) => {
          item.ischeck = true
        })
      } else {
        this.tableData.forEach((item, index) => {
          item.ischeck = false
        })
      }

      console.log(val)
      this.checkBoxData = val
    },
    // 当前页改变
    handleCurrentChange () {

    },
    // 确定事件
    handleToSure () {
      let arr = []
      this.tableData.forEach(item => {
        if (item.ischeck) {
          arr.push(item)
        }
      })
      this.$http.$emit('addBrandDialogSure', arr)
      this.dialogVisible = false
    }
  }
}
</script>
<style lang="scss" scoped>
.addBrandDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background: #f3f3f3;
  }
  .dialogTop {
    display: flex;
    justify-content: space-between;
    .topList {
      display: flex;
      align-items: center;
      span {
        white-space: nowrap;
        display: inline-block;
        margin-right: 5px;
      }
      /deep/ .el-input {
        width: 140px;
      }
    }
  }
  .dialogMiddle {
    margin-top: 10px;
    margin: 10px 0;
    /deep/ .el-input {
      width: 140px;
    }
  }
  .footer {
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
      .el-checkbox {
        margin-left: -4px;
      }
    }
    .operation {
      display: flex;
      justify-content: space-around;
      .iconSpn {
        display: block;
        font-size: 20px;
        color: #5a8bff;
        cursor: pointer;
      }
    }
  }
  .pagination {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
