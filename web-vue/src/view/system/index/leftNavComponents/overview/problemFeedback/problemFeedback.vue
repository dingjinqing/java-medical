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
            prop="accountNumber"
            label="客户登录账号"
            align="center"
            width="150"
          >
          </el-table-column>
          <el-table-column
            prop="phoneNum"
            label="登录账号手机号"
            align="center"
            width="150"
          >
          </el-table-column>
          <el-table-column
            prop="fillMobileNumber"
            label="填写手机号"
            align="center"
            width="150"
          >
          </el-table-column>
          <el-table-column
            prop="feedbackTime"
            label="反馈时间"
            align="center"
            width="200"
          >
          </el-table-column>
          <el-table-column
            prop="useVersion"
            label="使用版本"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="questionType"
            label="问题类型"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="questionContent"
            label="问题内容"
            align="center"
            width="200"
          >
          </el-table-column>
          <el-table-column
            prop="fillMobileNumber"
            label="图片"
            align="center"
          >
            <template slot-scope="scope">
              <img
                v-if="scope.row.img"
                :src="scope.row.img"
              >

            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="已看状态"
            align="center"
          >
            <template slot-scope="scope">
              <span style="color:#FF0000">
                {{scope.row.status}}
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
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination.vue') // 分页组件
  },
  data () {
    return {
      userName: '', // 反馈用户
      questionType: -1, // 问题类型
      questionTypeOptions: [
        {
          value: -1,
          label: '请选择反馈类型'
        },
        {
          value: 0,
          label: '产品建议'
        },
        {
          value: 1,
          label: '网页异常'
        },
        {
          value: 2,
          label: '功能使用咨询'
        },
        {
          value: 3,
          label: '其他'
        }
      ],
      statusViewed: 0, // 已看状态
      statusViewedOption: [
        {
          value: -1,
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
      startTime: '',
      endTime: '',
      tableData: [
        {
          accountNumber: 'tester001',
          phoneNum: '13683043470',
          fillMobileNumber: '18236936251',
          feedbackTime: '2018-08-27 09:29:01',
          useVersion: '旗舰版',
          questionType: '功能使用咨询',
          questionContent: '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈',
          img: 'http://mpdevimg2.weipubao.cn/upload/0/image/20200217/crop_KC9xqSLdERhny50V.jpeg',
          status: '未查看',
          id: 1
        },
        {
          accountNumber: 'tester002',
          phoneNum: '13683043470',
          fillMobileNumber: '18236936251',
          feedbackTime: '2018-08-27 09:29:01',
          useVersion: '旗舰版',
          questionType: '功能使用咨询',
          questionContent: '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈',
          img: '',
          status: '未查看',
          id: 1
        },
        {
          accountNumber: 'tester003',
          phoneNum: '13683043470',
          fillMobileNumber: '18236936251',
          feedbackTime: '2018-08-27 09:29:01',
          useVersion: '旗舰版',
          questionType: '功能使用咨询',
          questionContent: '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈',
          img: 'http://mpdevimg2.weipubao.cn/upload/0/image/20200217/crop_KC9xqSLdERhny50V.jpeg',
          status: '未查看',
          id: 1
        }
      ],
      pageParams: {
        currentPage: 1,
        pageRows: 20,
        totalRows: 3,
        pageCount: 1
      }
    }
  },
  mounted () {
    // 初始化数据
  },
  methods: {
    // 当前页变化
    seacherGroupIntegrationList () {

    },
    // 点击查看详情
    handleToClickDetail (row) {
      console.log(row)
      this.$router.push({
        name: 'problemFeedbackDeatil'
      })
    },
    // 点击筛选
    handleToClickSeach () {

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
}
</style>
