<template>
  <div class="problemFeedback">
    <div class="problemFeedbackMain">
      <div class="mainTop">
        <div class="list">
          <div class="li">
            <span>反馈用户：</span>
            <el-input
              v-model="userName"
              size="small"
              placeholder="反馈用户名称"
            ></el-input>
          </div>
          <div class="li">
            <span>问题类型：</span>
            <el-select
              v-model="questionType"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in questionTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div class="li">
            <span>已看状态：</span>
            <el-select
              v-model="statusViewed"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in statusViewedOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="list time">
          <div class="li">
            <span>反馈时间：</span>
            <el-date-picker
              size="small"
              v-model="startTime"
              type="date"
              placeholder="请选择时间"
            >
            </el-date-picker>
            &nbsp;&nbsp;至&nbsp;&nbsp;
            <el-date-picker
              size="small"
              v-model="endTime"
              type="date"
              placeholder="请选择时间"
            >
            </el-date-picker>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <el-button
              size="small"
              type="primary"
              @click="handleToClickSeach()"
            >筛选</el-button>
          </div>
        </div>
      </div>
      <div class="mainBottom">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="tableData"
          ref="singleTable"
          border
          style="width: 100%;margin-top:10px"
        >
          <el-table-column
            prop="submitUser"
            label="客户登录账号"
            align="center"
            width="150"
          >
          </el-table-column>
          <el-table-column
            prop="submitUserPhone"
            label="登录账号手机号"
            align="center"
            width="150"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            label="填写手机号"
            align="center"
            width="150"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="反馈时间"
            align="center"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="version"
            label="使用版本"
            align="center"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="categoryId"
            label="问题类型"
            align="center"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="content"
            label="问题内容"
            align="center"
            width="200"
          >
          </el-table-column>
          <el-table-column
            prop="imageUrls"
            label="图片"
            align="center"
          >
            <template slot-scope="scope">
              <div class="imgStyle">
                <img
                  v-if="scope.row.imageUrls.length"
                  :src="scope.row.imageUrls[0]"
                >
              </div>

            </template>
          </el-table-column>
          <el-table-column
            prop="isLook"
            label="已看状态"
            align="center"
          >
            <template slot-scope="scope">
              <span style="color:#FF0000">
                {{scope.row.isLook?'已查看':'未查看'}}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            label="状态"
            align="center"
          >
            <template slot-scope="scope">
              <span
                @click="handleToClickDetail(scope.row)"
                class="statusStyle"
              >查看详情</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="seacherGroupIntegrationList"
        />
      </div>
    </div>

  </div>
</template>
<script>
import { problemFeedbackList } from '@/api/system/problemFeedback/problemFeedback.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页组件
  },
  data () {
    return {
      userName: '', // 反馈用户
      questionType: null, // 问题类型
      questionTypeOptions: [
        {
          value: null,
          label: '请选择反馈类型'
        },
        {
          value: 1,
          label: '产品建议'
        },
        {
          value: 2,
          label: '网页异常'
        },
        {
          value: 3,
          label: '功能使用咨询'
        },
        {
          value: 0,
          label: '其他'
        }
      ],
      statusViewed: 0, // 已看状态
      statusViewedOption: [
        {
          value: null,
          label: '请选择已看状态'
        },
        {
          value: 0,
          label: '未查看'
        },
        {
          value: 1,
          label: '已查看'
        }
      ],
      startTime: null,
      endTime: null,
      tableData: [],
      pageParams: {
        currentPage: 1,
        pageRows: 20
      }
    }
  },
  mounted () {
    // 初始化数据 problemFeedbackList
    this.handleToInit()
  },
  methods: {
    //  初始化数据
    handleToInit () {
      let params = {
        startTime: this.startTime,
        endTime: this.endTime,
        name: this.userName,
        categoryId: this.questionType,
        lookType: this.statusViewed,
        currentPage: this.pageParams.currentPage
      }
      problemFeedbackList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          res.content.dataList.forEach((item, index) => {
            switch (item.categoryId) {
              case 0:
                item.categoryId = '其他'
                break
              case 1:
                item.categoryId = '产品建议'
                break
              case 2:
                item.categoryId = '网页异常'
                break
              case 3:
                item.categoryId = '功能使用咨询'
            }
          })
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    // 当前页变化
    seacherGroupIntegrationList () {
      this.handleToInit()
    },
    // 点击查看详情
    handleToClickDetail (row) {
      console.log(row)
      this.$router.push({
        path: 'problemFeedback_deatil',
        query: {
          id: row.questionFeedbackId
        }
      })
    },
    // 点击筛选
    handleToClickSeach () {
      this.handleToInit()
    }
  }
}
</script>
<style lang="scss" scoped>
.problemFeedback {
  margin-top: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  .problemFeedbackMain {
    background-color: #fff;
    padding: 15px;
    height: 100%;
    .mainTop {
      padding: 15px 25px;
      .list {
        display: flex;
        margin-bottom: 10px;
        .li {
          display: flex;
          align-items: center;
          margin-right: 20px;
          span {
            white-space: nowrap;
          }
          /deep/ .el-input {
            width: auto;
          }
        }
      }
      .time {
        /deep/ .el-input {
          width: 189px;
        }
      }
    }
    .mainBottom {
      /deep/ .tableClss th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
      }
      img {
        width: 50px;
        height: 48px;
        border-radius: 2px;
        border: 1px solid #ccc;
      }
      .statusStyle {
        cursor: pointer;
        color: #5a8bff;
      }
    }
  }
  .imgStyle {
    display: flex;
  }
}
</style>
