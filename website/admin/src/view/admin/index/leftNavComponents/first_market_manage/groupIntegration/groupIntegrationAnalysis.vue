<template>
  <div>
    <wrapper class="dataContent">

      <!-- 日期筛选部分 -->
      <div style="display:flex">
        <div style="height:32px;line-height:32px">{{ $t('seckill.screen') }}：</div>
        <div class="selectTime">
          <el-date-picker
            size="small"
            v-model="starDate"
            type="datetime"
            :placeholder="$t('seckill.startTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>{{ $t('seckill.to') }}</span>
          <el-date-picker
            size="small"
            v-model="endDate"
            type="datetime"
            :placeholder="$t('seckill.endTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
          @click="getAnalys"
        >{{ $t('seckill.screen') }}</el-button>
      </div>

      <!-- 表格数据部分 -->
      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">{{$t('groupIntegration.dividedPoints')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupIntegration.dividedPointsInfo')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #3dcf9a"
          >{{ integrationNum }}</div>
         <el-image
            class="left_image"
            :src="urls.url4"
          ></el-image>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">{{$t('groupIntegration.numberUsers')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupIntegration.numberUsersInfo')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #5a8bff"
          >{{ joinNum }}</div>
          <el-image
            class="left_image"
            :src="urls.url1"
          ></el-image>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">{{$t('groupIntegration.groupUsers')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupIntegration.groupUsersInfo')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fc6181"
          >{{ successUserNum }}</div>
          <el-image
            class="left_image"
            :src="urls.url2"
          ></el-image>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="titless">{{$t('groupIntegration.pullNewUsers')}}</div>
            <el-tooltip
              class="item"
              effect="light"
              :content="$t('groupIntegration.pullNewUsersInfo')"
              placement="top"
            >
              <i class="el-icon-warning-outline icons"></i>
            </el-tooltip>
          </div>
          <div
            class="num"
            style="color: #fdb64a"
          >{{ newUser }}</div>
          <el-image
            class="left_image"
            :src="urls.url3"
          ></el-image>
        </div>
      </section>

      <div id="charts">
        <ve-line :data="chartData" :settings="chartSettings" :data-empty="dataEmpty"></ve-line>
      </div>

    </wrapper>
  </div>
</template>

<script>
import { getAnalysisRequest } from '@/api/admin/marketManage/groupIntegrationList.js'
import wrapper from '@/components/admin/wrapper/wrapper'
import VCharts from 'v-charts'
import 'v-charts/lib/style.css'
export default {
  components: { wrapper, VCharts },
  data () {
    this.chartSettings = {
      labelMap: {
        'dateTime': this.$t('groupIntegration.date'),
        'integrationNum': this.$t('groupIntegration.dividedPoints'),
        'joinNum': this.$t('groupIntegration.numberUsers'),
        'successUserNum': this.$t('groupIntegration.groupUsers'),
        'newUser': this.$t('groupIntegration.pullNewUsers')
      },
      legendName: {
        'dateTime': this.$t('groupIntegration.date'),
        'integrationNum': this.$t('groupIntegration.dividedPoints'),
        'joinNum': this.$t('groupIntegration.numberUsers'),
        'successUserNum': this.$t('groupIntegration.groupUsers'),
        'newUser': this.$t('groupIntegration.pullNewUsers')
      }
    }
    return {
      starDate: null,
      endDate: null,
      integrationNum: 0, // 付款订单数
      joinNum: 0, // 参与用户数
      successUserNum: 0, // 成团用户数
      newUser: 0, // 拉新用户数
      chartData: {
        columns: ['dateTime', 'integrationNum', 'joinNum', 'successUserNum', 'newUser'],
        rows: [
        ]
      },
      actId: null,
      dataEmpty: true,
      urls: {
        url1: `${this.$imageHost}/image/admin/any_coner/any_coner_blue.png`,
        url2: `${this.$imageHost}/image/admin/any_coner/any_coner_pink.png`,
        url3: `${this.$imageHost}/image/admin/any_coner/any_coner_orange.png`,
        url4: `${this.$imageHost}/image/admin/any_coner/any_coner_green.png`
      }
    }
  },
  watch: {
    lang () {
      this.changeDivInnerHtml()
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.actId = this.$route.params.id
    this.getAnalys()
  },
  methods: {
    getAnalys () {
      let params = {
        'actId': this.actId,
        'startTime': this.starDate,
        'endTime': this.endDate
      }
      getAnalysisRequest(params).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.starDate = res.content.startTime
          this.endDate = res.content.endTime
          this.chartData.rows = res.content.list
          this.dataEmpty = res.content.list === null
          this.integrationNum = res.content.integrationNum
          this.joinNum = res.content.joinNum
          this.successUserNum = res.content.successUserNum
          this.newUser = res.content.newUser
        } else {
          this.$message.error(res.message)
        }
      })
    },
    changeDivInnerHtml () {
      var charts = document.getElementsByClassName('v-charts-data-empty')
      for (let i = 0; i < charts.length; i++) {
        charts[i].innerHTML = this.$t('userportrait.noData')
      }
    }
  }
}

</script>
<style lang="scss" scoped>
.dataContent {
  padding: 10px 50px;
  font-size: 14px;
  section {
    border: 1px solid #eee;
    height: 130px;
    width: 85%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 30px 0 50px;
    .fromInfo {
      flex: 1;
      height: 130px;
      position: relative;
      border-right: 1px solid #eee;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      .icons {
        margin-left: 10px;
        position: relative;
      }
      .num {
        margin-top: 15px;
        font-size: 30px;
      }
    }
  }
  #charts {
    width: 100%;
    height: 500px;
    left: -30px;
  }
 .left_image {
    position: absolute;
    bottom: 1px;
    left: 0;
    width:44px;
    height:40px;
  }
}
</style>
