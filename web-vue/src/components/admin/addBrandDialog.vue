<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-dialog
        title="添加商品品牌"
        :visible.sync="callAddBrand"
        width="50%"
        :modal-append-to-body='false'
      >
        <div
          class="dialogTop"
          style="margin-bottom:10px"
        >
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
            @click="handleToQueryData()"
          >查询</el-button>
        </div>
        <!-- <div class="dialogMiddle">
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
        </div> -->
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
              width="100"
              type="selection"
            >
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.ischeck"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column
              prop="brandName"
              label="品牌名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="classifyName"
              label="品牌分类"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="createTime"
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
          <el-button
            size="small"
            @click="$emit('update:callAddBrand', false)"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="handleToSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>
<script>
import { brandAllGetRequest } from '@/api/admin/brandManagement.js'
export default {
  props: {
    callAddBrand: { // 弹窗调起
      type: Boolean,
      default: () => false
    },
    brandBackData: { // 回显数据
      type: Array,
      default: () => []
    }
  },
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
        label: '111'
      }, {
        value: '选项2',
        label: '222'
      }, {
        value: '选项3',
        label: '333'
      }],
      fromValue: '',
      fromOptions: [{
        value: '选项1',
        label: '444'
      }, {
        value: '选项2',
        label: '555'
      }, {
        value: '选项3',
        label: '666'
      }],
      tableData: [],
      backFlag: false
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
    },
    callAddBrand (newData) {
      if (!newData) {
        this.$emit('update:callAddBrand', false)
      } else {
        this.tableData.forEach(item => {
          item.ischeck = false
          if (this.brandBackData.length > 0) {
            this.brandBackData.forEach(itemC => {
              if (item.id === itemC) {
                item.ischeck = true
              }
            })
          }
        })
      }
    },
    input () {
      this.handleToQueryData()
    },
    classValue () {
      this.handleToQueryData()
    },
    backFlag (newData) {
      if (newData) {
        console.log(this.tableData)
        this.tableData.forEach(item => {
          item.ischeck = false
          if (this.brandBackData.length > 0) {
            this.brandBackData.forEach(itemC => {
              if (item.id === itemC) {
                item.ischeck = true
              }
            })
          }
        })
        console.log(this.tableData)
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defalutData()
  },
  methods: {
    defalutData () {
      this.handleToQueryData()
      console.log(this.tableData)
    },
    // 分页查询
    handleToQueryData () {
      let params = {
        brandName: this.input, // 品牌名称
        classifyId: this.classValue, // 品牌分类
        currentPage: this.currentPage, // 当前页码
        pageRows: 20 // 显示行数
      }
      brandAllGetRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          res.content.dataList.forEach((item, index) => {
            this.$set(item, 'ischeck', false)
          })
          this.totle = res.content.page.totalRows
          this.pageCount = res.content.page.pageCount
          this.tableData = res.content.dataList
          this.backFlag = true
        }
      })
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
      this.handleToQueryData()
    },
    // 确定事件
    handleToSure () {
      let arr = []
      this.tableData.forEach(item => {
        if (item.ischeck) {
          arr.push(item)
        }
      })
      this.$emit('handleToGetBackData', arr)
      this.$emit('update:callAddBrand', false)
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
    height: 300px;
    overflow-y: auto;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      // padding: 8px 10px;
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
    /deep/
      .el-table__header
      .el-table-column--selection
      .cell
      .el-checkbox:after {
      content: " 本页全选";
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
