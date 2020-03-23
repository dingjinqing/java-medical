<template>
  <div class="messagePush">

    <!-- 添加消息推送 -->
    <div style="margin-bottom:10px">
      <el-button
        type="primary"
        size="small"
        @click="handleAddMessagePush"
      >{{$t(`messagePush.addMessagePush`)}}</el-button>
    </div>
    <!-- 筛选条件 -->
    <div>
      <el-form
        :inline="true"
        :model="formData"
      >
        <el-form-item :label="labels.label1">
          <el-input
            style="width:120px"
            v-model="formData.messageName"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="labels.label2">
          <el-input
            style="width:120px"
            v-model="formData.businessTitle"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item :label="labels.label3">
          <el-date-picker
            size="small"
            v-model="startTime"
            type="datetime"
            style="width:200px "
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time='00:00:00'
          >
          </el-date-picker>
          &nbsp;至&nbsp;
          <el-date-picker
            size="small"
            v-model="endTime"
            type="datetime"
            style="width:200px "
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time='23:59:59'
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="handleFilter"
            size="small"
          >{{$t(`messagePush.btnFilter`)}}</el-button>

        </el-form-item>
      </el-form>
    </div>
    <!-- 表格数据 -->
    <div>
      <el-table
        :data="dataList"
        style="width: 100%"
        border
        class="version-manage-table"
        header-row-class-name="tableClass"
        v-loading="loading"
      >
        <el-table-column
          prop="name"
          :label="$t(`messagePush.name`)"
          align="center"
          width="200"
        >
        </el-table-column>
        <el-table-column
          prop="title"
          :label="$t(`messagePush.thebusinessTitle`)"
          width="200"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="startTime"
          :label="$t(`messagePush.sendtime`)"
          width="300"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="sentNumber"
          :label="$t(`messagePush.sentNumber`)"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="clickedNumber"
          :label="$t(`messagePush.returnTheNumber`)"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="percentage"
          :label="$t(`messagePush.rateOfReturn`)"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.percentage}}%
          </template>
        </el-table-column>
        <el-table-column
          prop="sendStatus"
          :label="$t(`messagePush.sendStatus`)"
          align="center"
        >
          <template slot-scope="data">
            <div>
              {{ data.row.sendStatus === 0?"未发送":"已发送"}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="id"
          :label="$t(`messagePush.operation`)"
          width="120"
          align="center"
        >
          <template slot-scope="data">
            <div class="operationTip">
              <el-tooltip
                :content="$t(`messagePush.viewDetail`)"
                placement="top"
              >
                <span
                  class="iconfont iconchakanxiangqing iconSpan"
                  @click="handleGetDetails(data.row)"
                >
                </span>
              </el-tooltip>
              <el-tooltip
                :content="$t(`messagePush.sendTheRecord`)"
                placement="top"
              >
                <span
                  class="iconfont iconmingxi1 iconSpan"
                  @click="handleSendRecord(data.row)"
                >
                </span>
              </el-tooltip>
              <el-tooltip
                :content="$t(`messagePush.delete`)"
                placement="top"
              >
                <span
                  class="iconfont iconshanchu2 iconSpan"
                  @click="handleDelTemplate(data.row)"
                >
                </span>
              </el-tooltip>
              <!-- <i class="el-icon-view"></i>
                <i class="el-icon-s-unfold"></i>
                <i class="el-icon-delete"></i> -->
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div>
      <pagination
        :pageParams="pageParams"
        @pagination="initData"
      />
    </div>
  </div>

</template>
<script>
import pagination from '../../../../../../components/admin/pagination/pagination'
import dateTimePicker from '../../../../../../components/admin/dateTimePicker/dateTimePicker'
import { messageTemplateListApi, templateDeleteApi } from '@/api/admin/marketManage/messagePush'
export default {
  name: 'messagePush',
  components: { dateTimePicker, pagination },
  data () {
    return {

      /**
       * formData 的相关数据
       */
      formData: {
        messageName: ``,
        businessTitle: ``

      },
      startTime: null,
      endTime: null,
      labels: {
        label1: this.$t(`messagePush.messageName`),
        label2: this.$t(`messagePush.theBusinessTitle`),
        label3: this.$t(`messagePush.sendTime`)
      },
      /**
       * tableData
       */
      dataList: [
        {
          'id': 1,
          'name': '名称',
          'title': '标题',
          'startTime': '2019-09-03 15:04:32',
          'sentNumber': 0,
          'clickedNumber': 0,
          'percentage': 0.0,
          'sendStatus': 0
        }
      ],
      loading: false,
      pageParams: {

      }
    }
  },
  created () {
    this.initData() // 初始化获取数据
  },
  mounted () {
    this.langDefault()
  },
  watch: {
    activeName: 'watchActive',
    lang () {
      this.labels = {
        label1: this.$t(`messagePush.messageName`),
        label2: this.$t(`messagePush.theBusinessTitle`),
        label3: this.$t(`messagePush.sendTime`)
      }
    }
  },
  methods: {

    // 初始化数据方法
    initData () {
      this.loading = true
      // 请求参数

      messageTemplateListApi(this.pageParams).then(res => {
        const { error, content: { page, dataList } } = res
        if (error === 0) {
          console.log(res)
          this.dataList = dataList
          this.pageParams = page
          this.loading = false
        }
      }).catch(err => console.log(err))
    },
    // 点击标签页
    handleClick (val) {
      console.log(val.index)
    },
    // 添加消息推送
    handleAddMessagePush () {
      this.$router.push({
        path: `/api/admin/market/messagePush/addMessage`
      })
    },
    // 筛选
    handleFilter () {
      const params = {
        currentPage: this.pageParams.currentPage,
        pageRows: this.pageParams.pageRows,
        messageName: this.formData.messageName,
        businessTitle: this.formData.businessTitle,
        startTime: this.startTime,
        endTime: this.endTime
      }
      console.log(params)
      this.loading = true
      messageTemplateListApi(params).then(res => {
        const { error, content: { page, dataList } } = res
        console.log(res)

        if (error === 0) {
          this.dataList = dataList
          this.pageParams = page
          this.loading = false
        }
      }).catch(err => console.log(err))
    },
    // 发送记录
    handleSendRecord (row) {
      console.log(row)
      this.$router.push({
        name: `send_record`,
        query: {
          id: row.id
        }
      })
    },
    // 查看详情
    handleGetDetails (row) {
      this.$router.push({
        name: `template_detail`,
        query: {
          id: row.id
        }
      })
    },
    // 删除消息推送
    handleDelTemplate (row) {
      console.log(row)
      this.$confirm('此操作将永久删除该消息推送, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        templateDeleteApi(row.id).then(res => {
          console.log(res)
          const { error } = res
          if (error === 0) {
            this.$message.success({
              type: 'success',
              message: '删除成功'
            })
            this.initData()
          }
        }).catch(err => console.log(err))
      }).catch(() => {
        this.$message.success({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.messagePush {
  padding: 10px;
  /deep/ .tableClass th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .operationTip {
    display: flex;
    justify-content: space-between;
    .iconSpan {
      font-size: 22px;
      color: #5a8bff;
      cursor: pointer !important;
      margin-top: 5px;
    }
  }
}
</style>
