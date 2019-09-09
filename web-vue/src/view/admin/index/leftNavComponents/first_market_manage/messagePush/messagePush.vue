<template>
  <div class="messagePush">
    <!-- 消息推送卡片 -->
    <el-card>
      <!-- header -->
      <div>
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            v-for="(item,index) in headers"
            :label="item.label"
            :name="item.name"
            :key="index"
          ></el-tab-pane>
        </el-tabs>
      </div>
      <!-- 添加消息推送 -->
      <div>
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
          label-width="120px"
        >
          <el-form-item :label="labels.label1">
            <el-input
              v-model="formData.name"
              size="small"
            ></el-input>
          </el-form-item>
          <el-form-item :label="labels.label2">
            <el-input
              v-model="formData.title"
              size="small"
            ></el-input>
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
          </el-table-column>
          <el-table-column
            prop="operating"
            label="操作"
          >
            <template slot-scope="">
              <div>
                <!-- {{scope.row}} -->
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
    </el-card>
  </div>
</template>
<script>
import { messageTemplateListApi } from '@/api/admin/marketManage/messagePush'
export default {
  name: 'messagePush',
  data () {
    return {
      activeName: `0`,
      /**
       * header 标签页的数据
       */
      headers: [
        {
          label: `全部消息推送`,
          name: `0`
        },
        {
          label: `推送统计`,
          name: `1`
        }
      ],
      /**
       * formData 的相关数据
       */
      formData: {
        name: ``,
        title: ``

      },
      labels: {
        label1: `消息名称`,
        label2: `业务标题`
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
      ]
    }
  },
  created () {
    // this.initData() // 初始化获取数据
  },
  methods: {
    // 初始化数据方法
    initData () {
      // 请求参数
      const params = {
        'currentPage': 1,
        'pageRows': 1
      }
      messageTemplateListApi(params).then(res => {
        console.log(res)
        const { error, content: { page, dataList } } = res
        if (error === 0) {
          console.log(page)
          console.log(dataList)
        }
      }).catch(err => console.log(err))
    },
    // 点击标签页
    handleClick () {

    },
    // 添加消息推送
    handleAddMessagePush () {
      this.$router.push({
        path: `/api/admin/market/messagePush/addMessage`
      })
    },
    // 筛选
    handleFilter () {

    }
  }
}
</script>
<style lang="scss">
.messagePush {
  padding: 10px;
}
</style>
