<template>
  <div class="integralRule">
    <div class="top">
      {{$t('scoreCfg.prompt')}}
    </div>
    <div class="title">
      <span></span>
      {{$t('scoreCfg.scoreNormalRule')}}
    </div>
    <div class="content">
      <span>{{$t('scoreCfg.scoreEffective')}}</span>
      <div class="radioDiv">
        <div>
          <el-radio
            v-model="message.radio"
            label="0"
          >{{$t('scoreCfg.forever')}}</el-radio>
        </div>
        <div>
          <el-radio
            v-model="message.radio"
            label="1"
          >{{$t('scoreCfg.fromTime')}}</el-radio>
          <div>
            <el-select
              v-model="message.yearValue"
              :placeholder="$t('scoreCfg.choose')"
              size="small"
            >
              <el-option
                v-for="item in yearOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </div>
          <div>
            <el-select
              v-model="message.mounthValue"
              :placeholder="$t('scoreCfg.choose')"
              size="small"
            >
              <el-option
                v-for="item in mounthOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            {{$t('scoreCfg.month')}}
          </div>
          <div>
            <el-select
              v-model="message.dayValue"
              :placeholder="$t('scoreCfg.choose')"
              size="small"
              :disabled="mounthValue==='0'"
            >
              <el-option
                v-for="item in dayOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            {{$t('scoreCfg.day')}}
          </div>
        </div>
        <div style="color:#FF0000">
          {{$t('scoreCfg.exampleTime')}}
        </div>
        <div class="integralNumDiv">
          <el-radio
            v-model="message.radio"
            label="2"
          >{{$t('scoreCfg.fromGetScore')}}</el-radio>
          <el-input-number
            size="small"
            v-model="message.integralNum"
            controls-position="right"
            :min="1"
            :max="100"
          ></el-input-number>
          <div>
            <el-select
              v-model="message.integralDateValue"
              :placeholder="$t('scoreCfg.choose')"
              size="small"
            >
              <el-option
                v-for="item in integralDateOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>

            </el-select>
            {{$t('scoreCfg.innerEffective')}}
          </div>
        </div>
      </div>
    </div>
    <div
      class="content"
      style="margin-top:0"
    >
      <span>{{$t('scoreCfg.exchange')}}</span>
      <div class="radioDiv">
        {{$t('scoreCfg.formula')}}
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ['message'],
  data () {
    return {
      radio: '0',
      yearValue: '4',
      yearOptions: null,
      mounthValue: '4',
      mounthOptions: [
        {
          value: '0',
          label: '请选择：'
        }, {
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        }, {
          value: '7',
          label: '7'
        }, {
          value: '8',
          label: '8'
        }, {
          value: '9',
          label: '9'
        }, {
          value: '10',
          label: '10'
        }, {
          value: '11',
          label: '11'
        }, {
          value: '12',
          label: '12'
        }],
      dayValue: '4',
      dayOptions: [
        {
          value: '0',
          label: '请选择：'
        }, {
          value: '1',
          label: '1'
        }, {
          value: '2',
          label: '2'
        }, {
          value: '3',
          label: '3'
        }, {
          value: '4',
          label: '4'
        }, {
          value: '5',
          label: '5'
        }, {
          value: '6',
          label: '6'
        }, {
          value: '7',
          label: '7'
        }, {
          value: '8',
          label: '8'
        }, {
          value: '9',
          label: '9'
        }, {
          value: '10',
          label: '10'
        }, {
          value: '11',
          label: '11'
        }, {
          value: '12',
          label: '12'
        }, {
          value: '13',
          label: '13'
        }, {
          value: '14',
          label: '14'
        }, {
          value: '15',
          label: '15'
        }, {
          value: '16',
          label: '16'
        }, {
          value: '17',
          label: '17'
        }, {
          value: '18',
          label: '18'
        }, {
          value: '19',
          label: '19'
        }, {
          value: '20',
          label: '20'
        }, {
          value: '21',
          label: '21'
        }, {
          value: '22',
          label: '22'
        }, {
          value: '23',
          label: '23'
        }, {
          value: '24',
          label: '24'
        }, {
          value: '25',
          label: '25'
        }, {
          value: '26',
          label: '26'
        }, {
          value: '27',
          label: '27'
        }, {
          value: '28',
          label: '28'
        }, {
          value: '29',
          label: '29'
        }, {
          value: '30',
          label: '30'
        }, {
          value: '31',
          label: '31'
        }],
      integralNum: '',
      integralDateValue: '2',
      integralDateOptions: [{
        value: '1',
        label: '日'
      }, {
        value: '7',
        label: '周'
      },
      {
        value: '30',
        label: '月'
      }]
    }
  },
  watch: {
    lang () {
      this.integralDateOptions = [{
        value: '1',
        label: this.$t('scoreCfg.day')
      }, {
        value: '7',
        label: this.$t('scoreCfg.week')
      },
      {
        value: '30',
        label: this.$t('scoreCfg.month')
      }]

      this.dayOptions[0].label = this.$t('scoreCfg.choose')
      this.mounthOptions[0].label = this.$t('scoreCfg.choose')
      this.yearOptions = this.$t('scoreCfg.yearOptions')
    },
    mounthValue (newData) {
      console.log('299')
      if (newData === '0') {
        this.dayValue = ''
      }
    },
    message () {
      console.log('数据传递成功')
    },
    '$store.state.util.integralDataNotice' (newData) {
      console.log(newData)
      console.log('正在监控积分通用规则')
      let obj = {
        radio: this.message.radio
      }
      switch (this.message.radio) {
        case '1':
          obj.yearValue = this.message.yearValue
          obj.mounthValue = this.message.mounthValue
          obj.dayValue = this.message.dayValue
          break
        case '2':
          obj.integralNum = this.message.integralNum
          obj.integralDateValue = this.message.integralDateValue
      }
      console.log(obj)
      this.$emit('toNoticeSend', obj, 0)
    }
  },
  mounted () {
    this.langDefault()
  },
  methods: {

  }
}
</script>
<style lang="scss" scoped>
.integralRule {
  .top {
    padding-bottom: 10px;
  }
  .title {
    span {
      display: inline-block;
      margin-left: 20px;
      border-left: 2px solid #5a8bff;
      height: 14px;
      width: 8px;
      margin-bottom: -1px;
    }
    height: 40px;
    line-height: 40px;
    background: #eef1f6;
    font-size: 14px;
  }
  .content {
    margin-top: 30px;
    display: flex;
    .radioDiv {
      display: flex;
      flex-direction: column;
      margin-left: 30px;
    }
    .radioDiv > div {
      display: flex;
      margin-bottom: 30px;
      align-items: center;
      /deep/ .el-radio__label {
        color: #333;
      }
      /deep/ .el-input {
        width: 110px;
      }
    }
    .radioDiv > div > div {
      margin-right: 20px;
    }
    .integralNumDiv {
      /deep/ .el-input {
        width: 130px !important;
      }
    }
  }
}
</style>
