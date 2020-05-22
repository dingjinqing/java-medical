<template>
  <section class="label">
    <div class="labelItem">
      {{$t('userStatistics.memberStatistics')}}
      <el-tooltip
        effect="light"
        placement="bottom-start"
      >
        <div
          slot="content"
          style="width: 400px;line-height: 30px;font-size: 14px;padding:10px 5px 10px 10px"
        >
          <section
            style="display: flex"
            v-for="item in memberTipsList"
            :key="item.title"
          >
            <div style="width: 30%;color:#999">{{item.title}}</div>
            <div style="width: 70%;color: #353535">{{item.content}}</div>
          </section>
        </div>
        <i class="el-icon-warning-outline icons"></i>
      </el-tooltip>
    </div>
    <el-select
      v-model="timeSelect"
      size="small"
      clearable
      @change="dateChangeHandler"
      class="timeSelect"
    >
      <el-option
        v-for="item in timeRange"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    <span>{{this.startDate.year}}{{$t('userStatistics.year')}}{{this.startDate.month}}{{$t('userStatistics.month')}}{{this.startDate.day}}{{$t('userStatistics.day')}} - {{this.endDate.year}}{{$t('userStatistics.year')}}{{this.endDate.month}}{{$t('userStatistics.month')}}{{this.endDate.day}}{{$t('userStatistics.day')}}</span>

    <!-- 表格数据部分 -->
    <div class="fromWrapper">
      <div
        class="fromItem"
        v-for="item in table"
        :key="item.name"
      >
        <div>{{item.name}}</div>
        <div
          class="num"
          style="color: #5A8BFF"
        >{{item.number}}</div>
        <div>{{$t('userStatistics.compareLastDay')}} {{item.rate}}</div>
      </div>
    </div>

  </section>
</template>

<script>
import { menberStatistics } from '@/api/admin/firstWebManage/userStatistics/userStatistics.js'

export default {
  watch: {
    lang () {
      this.timeRange = this.$t('userStatistics.timeRange')
    }
  },
  created () {
    this.initData()
  },

  mounted () {
    this.langDefault()
  },

  data () {
    return {
      timeSelect: 1,
      // timeRange: [
      //   { value: 1, label: '最新1天' },
      //   { value: 7, label: '最新7天' },
      //   { value: 30, label: '最新30天' }
      // ],
      timeRange: this.$t('userStatistics.timeRange'),
      params: 1,
      // memberTipsList: [
      //   {
      //     title: '累积会员数',
      //     content: '截至到筛选时间的最后一天，店铺的会员累积人数'
      //   },
      //   {
      //     title: '新增会员数',
      //     content: '筛选时间内，通过领取会员卡，新成为会员的客户数量'
      //   },
      //   {
      //     title: '升级会员数',
      //     content: '筛选时间内，通过会员规则升级的会员数量，一人多次升级记为一人'
      //   },
      //   {
      //     title: '储值会员数',
      //     content: '筛选时间内，进行储值的会员数量，一人多次储值记为一人'
      //   }
      // ],
      memberTipsList: this.$t('userStatistics.memberTipsList'),
      table: [],
      originalData: {
        accumulationNumber: '', // 积累会员数
        accumulationNumberRate: '',
        addNumber: '', // 新增会员数
        addNumberRate: '',
        updateNumber: '', // 升级会员数
        updateNumberRate: '',
        chargeNumber: '', // 储值会员数
        chargeNumberRate: ''
      },
      startDate: {
        year: '',
        month: '',
        day: ''
      },
      endDate: {
        year: '',
        month: '',
        day: ''
      }
    }
  },

  methods: {
    dateChangeHandler (time) {
      this.params = time
      // console.log(this.params)
      this.initData()
    },

    initData () {
      menberStatistics({ 'type': this.params }).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.originalData = res.content
          this.handleData(this.originalData)
        }
      }).catch(err => console.log(err))
    },

    numberChange (number) {
      let str
      if (number > 0) {
        str = '↑' + Number(number * 100).toFixed(2) + '%'
      } else if (number < 0) {
        str = '↓' + Math.abs(Number(number * 100)).toFixed(2) + '%'
      } else {
        str = '--'
      }
      return str
    },

    // 处理返回来的数据
    handleData (data) {
      this.startDate.year = data.startTime.split('-')[0]
      this.startDate.month = data.startTime.split('-')[1]
      this.startDate.day = data.startTime.split('-')[2]

      this.endDate.year = data.endTime.split('-')[0]
      this.endDate.month = data.endTime.split('-')[1]
      this.endDate.day = data.endTime.split('-')[2]

      // 累积会员数
      this.originalData.accumulationNumber = data.userData
      this.originalData.accumulationNumberRate = this.numberChange(data.userDataRate)
      // 新增会员数
      this.originalData.addNumber = data.regUserData
      console.log(this.originalData.addNumber, 'add')
      this.originalData.addNumberRate = this.numberChange(data.regUserDataRate)
      // 升级会员数
      this.originalData.updateNumber = data.upgradeUserData
      this.originalData.updateNumberRate = this.numberChange(data.upgradeUserDataRate)
      // 储值会员数
      this.originalData.chargeNumber = data.chargeUserData
      this.originalData.chargeNumberRate = this.numberChange(data.chargeUserDataRate)

      this.table = [
        {
          name: this.$t('userStatistics.cumulateMemberNumber'),
          number: this.originalData.accumulationNumber,
          rate: this.originalData.accumulationNumberRate
        },
        {
          name: this.$t('userStatistics.addMemberNumber'),
          number: this.originalData.addNumber,
          rate: this.originalData.addNumberRate
        },
        {
          name: this.$t('userStatistics.updateMemberNumber'),
          number: this.originalData.updateNumber,
          rate: this.originalData.updateNumberRate
        },
        {
          name: this.$t('userStatistics.chargeMemberNumber'),
          number: this.originalData.chargeNumber,
          rate: this.originalData.chargeNumberRate
        }
      ]
    }

  }
}

</script>
<style lang="scss" scoped>
.label {
  padding: 10px;
  background: #fff;
  .labelItem {
    height: 50px;
    line-height: 50px;
    color: #333;
  }
  .timeSelect {
    width: 140px;
    margin: 0 10px 0 2px;
  }
}
.fromWrapper {
  border: 1px solid #eee;
  height: 130px;
  width: 85%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px auto 50px;
  .fromItem {
    flex: 1;
    height: 130px;
    position: relative;
    border-right: 1px solid #eee;
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
    :nth-of-type(3) {
      margin-top: 10px;
    }
  }
}
</style>
