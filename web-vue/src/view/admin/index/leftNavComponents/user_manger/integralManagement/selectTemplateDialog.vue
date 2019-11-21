<template>
  <div>
    <el-dialog
      title="选择模板"
      :visible.sync="dialogVisible"
      width="40%"
    >
      <div class="containter">
        <div class="selectTemplateMain">
          <div>
            <span>页面名称</span>
            <el-input
              v-model="selectTeminput"
              placeholder="请输入页面名称"
              size="small"
            ></el-input>
          </div>
          <div>
            <span>页面分类</span>
            <el-select
              v-model="classifyValue"
              size="small"
            >
              <el-option
                v-for="item in classifyOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <el-button
              type="primary"
              size="small"
            >搜索</el-button>
          </div>
        </div>
        <div class="footer">
          <div class="tableMain">
            <el-table
              class="version-manage-table"
              :data="tableData"
              header-row-class-name="tableClss"
              border
              style="width: 100%"
              @current-change="handleToSelect"
              highlight-current-row
            >
              <el-table-column
                prop="userID"
                align="center"
                label="页面名称"
              >
              </el-table-column>
              <el-table-column
                prop="date"
                label="创建时间"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="inviter"
                label="是否首页"
                align="center"
              >
              </el-table-column>

            </el-table>

          </div>
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
</template>
<script>
export default {
  data () {
    return {
      dialogVisible: false,
      selectTeminput: '',
      classifyValue: '0',
      classifyOptions: [{
        value: '0',
        label: '请选择分类'
      }, {
        value: '1',
        label: '测试页面'
      }],
      tableData: [
        {
          userID: '51',
          inviter: '否',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '12',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        },
        {
          userID: '43',
          inviter: '是',
          date: '20190828 14:40:44',
          status: '正常'
        }
      ],
      selectData: ''
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    defaultData () {
      this.$http.$on('choiseTemplateDialog', res => {
        console.log(res)
        this.dialogVisible = true
      })
    },
    // 表格选中事件
    handleToSelect (val) {
      console.log(val)
      this.selectData = val
    },
    // 弹窗确认事件
    handleToSure () {
      console.log(this.selectData)
      this.$emit('handleToSendData', this.selectData)
      this.dialogVisible = false
    }
  }
}
</script>
<style lang="scss" scoped>
.containter {
  height: 300px;
  overflow-y: auto;
  .selectTemplateMain {
    display: flex;
    div {
      display: flex;
      align-items: center;
      span {
        display: inline-block;
        width: 73px;
      }
      /deep/ .el-input {
        width: 160px;
      }
      margin-right: 20px;
      /deep/ .el-button {
        width: 70px;
      }
    }
  }
  .tableMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    margin-top: 10px;
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
      span {
        cursor: pointer;
        color: #5a8bff;
      }
    }
  }
}
</style>
