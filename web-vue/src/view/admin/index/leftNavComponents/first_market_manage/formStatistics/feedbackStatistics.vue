<template>
  <div class="feedbackDetails">
    <div class="feedbackDetailsMain">
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
              {{validityPeriod===1?'永久有效':'期限内有效'}}
            </span>
          </li>
          <li>
            状态：<span>
              {{status===1?'已发布':'未发布'}}
            </span>
          </li>

        </ul>
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
      validityPeriod: '', // 有效期
      status: '' // 发布状态
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

        }
      })
      let content = {
        'participantsNum': 1,
        'submitNum': 1,
        'validityPeriod': 1,
        'status': 2,
        'sexList': [
          {
            'moduleType': '男',
            'votes': 1,
            'percentage': 1.0
          },
          {
            'moduleType': '女',
            'votes': 0,
            'percentage': 0.0
          }
        ],
        'slideList': [
          {
            'moduleType': '下拉选项1',
            'votes': 0,
            'percentage': 0.0
          },
          {
            'moduleType': '下拉选项2',
            'votes': 0,
            'percentage': 0.0
          }
        ],
        'chooseList': [
          {
            'moduleType': '选项选择1',
            'votes': 0,
            'percentage': 0.0
          },
          {
            'moduleType': '选项选择2',
            'votes': 0,
            'percentage': 0.0
          }
        ]
      }
      this.participantsNum = content.participantsNum
      this.submitNum = content.submitNum
      this.validityPeriod = content.validityPeriod
      this.status = content.status
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
  .feedbackDetailsMain {
    position: relative;
    margin-top: 10px;
    background-color: #fff;
    padding: 10px 20px 10px 25px;
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
}
</style>
