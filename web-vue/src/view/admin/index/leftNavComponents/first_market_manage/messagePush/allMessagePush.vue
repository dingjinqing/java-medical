<template>
  <div class="messagePush">

    <!-- 添加消息推送 -->
    <div style="margin-bottom:10px">
      <el-button
        type="primary"
        size="small"
        @click="handleAddMessagePush"
      > 添加消息推送</el-button>
    </div>
    <!-- 筛选条件 -->
    <div>
      <el-form
        :inline="true"
        :model="formData"
        label-width="90px"
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
          <dateTimePicker
            @time="handleGetTime"
            :showPicker=1
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="handleFilter"
            size="small"
          >筛选</el-button>

        </el-form-item>
      </el-form>
    </div>
    <!-- 表格数据 -->
    <div>
      <el-table
        :data="dataList"
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column
          prop="name"
          label="消息名称"
        >
        </el-table-column>
        <el-table-column
          prop="title"
          label="业务标题"
        >
        </el-table-column>
        <el-table-column
          prop="startTime"
          label="发送时间"
        >
        </el-table-column>
        <el-table-column
          prop="sentNumber"
          label="送达数量"
        >
        </el-table-column>
        <el-table-column
          prop="clickedNumber"
          label="回访数量"
        >
        </el-table-column>
        <el-table-column
          prop="percentage"
          label="回访率"
        >
        </el-table-column>
        <el-table-column
          prop="sendStatus"
          label="发送状态"
        >
          <template slot-scope="data">
            <div>
              {{ data.row.sendStatus === 0?`未发送`:`已发送`}}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="id"
          label="操作"
        >
          <template slot-scope="data">
            <div>
              <el-tooltip
                content="查看详情"
                placement="top"
              >
                <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-view"
                  circle
                ></el-button>
              </el-tooltip>
              <el-tooltip
                content="发送记录"
                placement="top"
              >
                <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-s-unfold"
                  circle
                ></el-button>
              </el-tooltip>
              <el-tooltip
                content="删除"
                placement="top"
              >
                <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-delete"
                  circle
                  @click="handleDelTemplate(data.row.id)"
                ></el-button>
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
        label1: `消息名称`,
        label2: `业务标题`,
        label3: `发送时间`
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
  watch: {
    activeName: 'watchActive'
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
        if (error === 0) {
          console.log(res)
          this.dataList = dataList
          this.pageParams = page
          this.loading = false
        }
      }).catch(err => console.log(err))
    },
    // 获取筛选的时间
    handleGetTime (val) {
      const { startTime, endTime } = val
      this.startTime = startTime
      this.endTime = endTime
    },
    // 删除消息推送
    handleDelTemplate (id) {
      console.log(id)
      this.$confirm('此操作将永久删除该消息推送, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        templateDeleteApi(id).then(res => {
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
.messagePush {
  padding: 10px;
}
</style>
