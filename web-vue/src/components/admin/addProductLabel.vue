<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-dialog
        title="添加商品标签"
        :visible.sync="callAddProductLabel"
        width="40%"
        :modal-append-to-body='false'
      >
        <div class="dialogTop">
          <div class="topList">
            <span>标签名称:</span>
            <el-input
              v-model="classValue"
              size="small"
            ></el-input>
          </div>
          <el-button
            size="small"
            type="primary"
            @click="queryLabelData()"
          >查询</el-button>
        </div>
        <div
          class="footer"
          :class="isElection?'isElection':''"
        >
          <el-table
            class="version-manage-table"
            header-row-class-name="tableClss"
            :data="tableData"
            border
            style="width: 100%"
            @selection-change="changeFun"
            ref="labelTable"
          >
            <el-table-column
              align="center"
              width="100"
              type="selection"
            >
            </el-table-column>
            <el-table-column
              prop="name"
              label="标签名称"
              align="center"
              class="name"
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
          <el-button @click="$emit('update:callAddProductLabel', false)">取 消</el-button>
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
import { getGoodsLabelPageList } from '@/api/admin/goodsManage/goodsLabel/goodsLabel.js'
export default {
  props: {
    callAddProductLabel: { // 弹窗调起
      type: Boolean,
      default: () => false
    },
    brandBackData: { // 回显数据
      type: Array,
      default: () => []
    },
    singleElection: Boolean
  },
  data () {
    return {
      currentPage: 1,
      totle: 1,
      pageCount: 1,
      classValue: '',
      tableData: [],
      backFlag: false,
      isElection: false
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
    callAddProductLabel (newData) {
      if (!newData) {
        this.$emit('update:callAddProductLabel', false)
      }
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
    },
    brandBackData (arr) {
      console.log(arr)
      //  labelTable
      if (arr.length) {
        arr.forEach(row => {
          this.$refs.labelTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.labelTable.clearSelection()
      }
    },
    singleElection (newVal) {
      this.isElection = newVal
    }
  },
  mounted () {
    // 初始化数据
    this.defalutData()
  },
  methods: {
    defalutData () {
      this.queryLabelData()
    },
    queryLabelData () {
      let params = {
        labelName: this.classValue,
        currentPage: this.currentPage,
        pageRows: 20
      }
      getGoodsLabelPageList(params).then((res) => {
        if (res.error === 0) {
          res.content.dataList.forEach((item, index) => {
            this.$set(item, 'ischeck', false)
          })
          this.totle = res.content.page.totalRows
          this.pageCount = res.content.page.pageCount
          this.tableData = res.content.dataList
          this.backFlag = true
        }
        console.log(res)
      })
    },
    changeFun (val) {
      console.log(val)
      // if (val.length) {
      //   this.tableData.forEach((item, index) => {
      //     item.ischeck = true
      //   })
      // } else {
      //   this.tableData.forEach((item, index) => {
      //     item.ischeck = false
      //   })
      // }
      if (val.length > 1) {
        this.$refs.labelTable.clearSelection()
        this.$refs.labelTable.toggleRowSelection(val.pop())
        this.checkBoxData = [val[0]]
      } else {
        this.checkBoxData = val
      }
      console.log(val)
    },
    // 当前页改变
    handleCurrentChange () {
      this.queryLabelData()
    },
    // 确定事件
    handleToSure () {
      // let arr = []
      // this.tableData.forEach(item => {
      //   if (item.ischeck) {
      //     arr.push(item)
      //   }
      // })
      this.$emit('handleToGetBackData', this.checkBoxData)
      this.$emit('update:callAddProductLabel', false)
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
    margin-bottom: 10px;
    .topList {
      display: flex;
      align-items: center;
      margin-right: 5px;
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
  .isElection {
    /deep/ .is-leaf {
      .cell {
        display: none;
      }
    }
    /deep/ .el-table_1_column_2 {
      .cell {
        display: block;
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
