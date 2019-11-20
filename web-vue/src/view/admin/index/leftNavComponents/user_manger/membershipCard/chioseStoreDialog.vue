<template>
  <div class="addBrandDialog">
    <div class="addBrandDialogMain">
      <el-dialog
        title="添加商品品牌"
        :visible.sync="dialogVisible"
        width="40%"
      >
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
              prop="storeName"
              label="门店名称"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="adress"
              label="门店地址"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="presenter"
              label="负责人"
              align="center"
            >

            </el-table-column>
            <el-table-column
              prop="phoneNum"
              label="联系电话"
              align="center"
            >

            </el-table-column>
            <el-table-column
              prop="creatTime"
              label="营业时间"
              align="center"
            >

            </el-table-column>
            <el-table-column
              prop="status"
              label="营业状态"
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
            @click="dialogVisible = false"
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
export default {
  data () {
    return {
      currentPage: 1,
      totle: 1,
      pageCount: 1,
      dialogVisible: false,
      tableData: [
        {
          ischeck: false,
          id: 1,
          storeName: '牡丹园店1',
          adress: '天博中润1',
          presenter: '孙腾飞1',
          phoneNum: '1111111',
          creatTime: '每天1',
          isFirstPage: true,
          status: '营业1'

        },
        {
          ischeck: false,
          id: 2,
          storeName: '牡丹园店2',
          adress: '天博中润2',
          presenter: '孙腾飞2',
          phoneNum: '1111111',
          creatTime: '每天2',
          isFirstPage: true,
          status: '营业2'

        },
        {
          ischeck: false,
          id: 3,
          storeName: '牡丹园店3',
          adress: '天博中润3',
          presenter: '孙腾飞3',
          phoneNum: '11111113',
          creatTime: '每天3',
          isFirstPage: true,
          status: '营业3'

        },
        {
          ischeck: false,
          id: 4,
          storeName: '牡丹园店4',
          adress: '天博中润4',
          presenter: '孙腾飞4',
          phoneNum: '11111114',
          creatTime: '每天4',
          isFirstPage: true,
          status: '营业4'

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
      this.$http.$on('CallChioseStore', res => {
        this.dialogVisible = true
      })
      this.$http.$on('CallAddBrand', res => {
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
      console.log(arr)
      this.$http.$emit('chioseSureData', arr)
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
