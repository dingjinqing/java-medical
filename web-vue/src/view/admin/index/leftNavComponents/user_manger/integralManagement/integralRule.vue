<template>
  <div class="integralRule">
    <div class="top">
      请在此设置店铺积分管理相关规则：
    </div>
    <div class="title">
      <span></span>
      积分通用规则
    </div>
    <div class="content">
      <span>积分有效期</span>
      <div class="radioDiv">
        <div>
          <el-radio
            v-model="radio"
            label="0"
          >永久有效</el-radio>
        </div>
        <div>
          <el-radio
            v-model="radio"
            label="1"
          >从获得开始至</el-radio>
          <div>
            <el-select
              v-model="yearValue"
              placeholder="请选择"
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
              v-model="mounthValue"
              placeholder="请选择"
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
            月
          </div>
          <div>
            <el-select
              v-model="dayValue"
              placeholder="请选择"
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
            日
          </div>
        </div>
        <div style="color:#FF0000">
          例如，设置未"明年的01月01日",即是指用户今年获得的积分将在明年1月1日24:00:00失效
        </div>
        <div class="integralNumDiv">
          <el-radio
            v-model="radio"
            label="2"
          >从获得积分当天起</el-radio>
          <el-input-number
            size="small"
            v-model="integralNum"
            controls-position="right"
            :min="1"
            :max="100"
          ></el-input-number>
          <div>
            <el-select
              v-model="integralDateValue"
              placeholder="请选择"
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
            内有效
          </div>
        </div>
      </div>
    </div>
    <div
      class="content"
      style="margin-top:0"
    >
      <span>积分润换比</span>
      <div class="radioDiv">
        100积分=1元RMB
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      radio: '0',
      yearValue: '4',
      yearOptions: [{
        value: '0',
        label: '一年后'
      }, {
        value: '1',
        label: '两年后'
      }, {
        value: '2',
        label: '三年后'
      }, {
        value: '3',
        label: '四年后'
      }, {
        value: '4',
        label: '五年后'
      }],
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
        value: '0',
        label: '日'
      }, {
        value: '1',
        label: '周'
      },
      {
        value: '2',
        label: '月'
      }]
    }
  },
  watch: {
    mounthValue (newData) {
      console.log('299')
      if (newData === '0') {
        this.dayValue = ''
      }
    },
    '$store.state.util.integralDataNotice' (newData) {
      console.log('正在监控积分通用规则')
      let obj = {
        radio: this.radio
      }
      switch (this.radio) {
        case '1':
          obj.yearValue = this.yearValue
          obj.mounthValue = this.mounthValue
          obj.dayValue = this.dayValue
          break
        case '2':
          obj.integralNum = this.integralNum
          obj.integralDateValue = this.integralDateValue
      }
      console.log(obj)
      this.$emit('toNoticeSend', obj, 0)
    }
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
