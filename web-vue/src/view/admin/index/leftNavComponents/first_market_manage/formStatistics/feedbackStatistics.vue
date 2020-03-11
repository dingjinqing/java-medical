<template>
  <div class="feedbackDetails">
    <div class="feedbackDetailsMain">
      <div class="feedback_container">
        <div class="form_name">
          {{$route.query.row.pageName}}
        </div>
        <div class="form_info">
          <ul>
            <li>
              参与人数：<span>{{participantsNum}}</span>
            </li>
            <li>
              反馈数：<span>{{submitNum}}</span>
            </li>
            <li style="width:360px">
              有效期：<span>
                {{isForeverValid===1?'永久有效':'期限内有效'}}
              </span>
            </li>
            <li>
              状态：<span>
                {{state===1?'已发布':'未发布'}}
              </span>
            </li>

          </ul>
        </div>
      </div>
      <div class="feedback">
        <div class="info">
          <table>
            <thead>
              <tr>
                <td>(单选/选填)性别</td>
                <td>票数</td>
                <td width="30%">占比</td>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item,index) in sexList"
                :key="index"
              >
                <td>{{item.moduleType}}</td>
                <td>{{item.votes}}</td>
                <td>
                  <div class="progress">
                    <el-progress
                      :text-inside="true"
                      :stroke-width="26"
                      :percentage="item.percentage"
                    ></el-progress>
                  </div>
                </td>
              </tr>
              <tr>
                <td
                  colspan="3"
                  style="text-align: right;height: 40px;line-height: 40px;padding-right: 20px"
                ><span>本题有效票数合计：2</span><span style="margin-left: 20px">本题总票数合计：{{sexTotal}}</span></td>
              </tr>
            </tbody>
          </table>
          <table>
            <thead>
              <tr>
                <td>(单选/选填)下拉</td>
                <td>票数</td>
                <td width="30%">占比</td>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item,index) in slideList"
                :key="index"
              >
                <td>{{item.moduleType}}</td>
                <td>{{item.votes}}</td>
                <td>
                  <div class="progress">
                    <el-progress
                      :text-inside="true"
                      :stroke-width="26"
                      :percentage="item.percentage"
                    ></el-progress>
                  </div>
                </td>
              </tr>
              <tr>
                <td
                  colspan="3"
                  style="text-align: right;height: 40px;line-height: 40px;padding-right: 20px"
                ><span>本题有效票数合计：2</span><span style="margin-left: 20px">本题总票数合计：{{slideTotal}}</span></td>
              </tr>
            </tbody>
          </table>
          <table>
            <thead>
              <tr>
                <td>(单选/选填)选项</td>
                <td>票数</td>
                <td width="30%">占比</td>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(item,index) in chooseList"
                :key="index"
              >
                <td>{{item.moduleType}}</td>
                <td>{{item.votes}}</td>
                <td>
                  <div class="progress">
                    <el-progress
                      :text-inside="true"
                      :stroke-width="26"
                      :percentage="item.percentage"
                    ></el-progress>
                  </div>
                </td>
              </tr>
              <tr>
                <td
                  colspan="3"
                  style="text-align: right;height: 40px;line-height: 40px;padding-right: 20px"
                ><span>本题有效票数合计：2</span><span style="margin-left: 20px">本题总票数合计：{{chooseTotal}}</span></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { feedbackStatisticsQuery } from '@/api/admin/marketManage/formDecoration'
export default {
  data () {
    return {
      participantsNum: '', // 参与人数
      submitNum: '', // 反馈数量
      isForeverValid: '', // 有效期
      status: '', // 发布状态
      sexList: [], // 性别表单元素统计结果
      slideList: [], // 下拉表单元素统计结果
      chooseList: [], // 选项统计结果
      sexTotal: '', // 性别表单元素总反馈票数
      slideTotal: '', // 下拉表单元素总反馈票数
      chooseTotal: '' // 选项表单元素总反馈票数
    }
  },
  mounted () {
    console.log(this.$route)
    // 初始化请求数据
    this.handleToInitData()
  },
  methods: {
    // 初始化请求数据
    handleToInitData () {
      feedbackStatisticsQuery({ pageId: this.$route.query.row.pageId }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.participantsNum = res.content.participantsNum
          this.submitNum = res.content.submitNum
          this.isForeverValid = res.content.isForeverValid
          this.sexList = res.content.sexList
          this.slideList = res.content.slideList
          this.chooseList = res.content.chooseList
          this.sexTotal = res.content.sexTotal
          this.slideTotal = res.content.slideTotal
          this.chooseTotal = res.content.chooseTotal
          this.state = res.content.state
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.feedbackDetails {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  padding-top: 0;
  .feedbackDetailsMain {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 25px;
    .feedback_container {
      margin: 10px 30px;
      border-bottom: 1px solid #eeeeee;
      .form_name {
        height: 40px;
        line-height: 40px;
        font-size: 16px;
        color: #333;
        font-weight: bold;
        width: 100%;
        text-align: center;
        margin: 10px auto;
        margin-bottom: 0px;
      }
      .form_info ul li {
        display: inline-block;
        width: 190px;
        margin: 20px 20px 20px 20px;
        text-align: center;
      }
    }
    .feedback {
      padding: 0px 70px;
      margin-bottom: 10px;
      table {
        margin-top: 15px;
        width: 100%;
        border: 1px solid #eeeeee;
        thead {
          td {
            background: #f5f5f5;
            padding: 0;
            text-align: center;
            border: 1px solid #eeeeee;
            color: #333;
            font-size: 14px;
            height: 40px;
            line-height: 40px;
          }
        }
        tr {
          td {
            text-align: center;
            height: 40px;
            line-height: 40px;
            border: 1px solid #eeeeee;
            color: #333;
            font-size: 14px;
            .progress {
              padding: 0 20px;
            }
          }
        }
      }
    }
  }
}
</style>
