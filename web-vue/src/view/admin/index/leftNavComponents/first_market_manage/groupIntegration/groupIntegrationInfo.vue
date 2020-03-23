<template>
  <div class="groupwrapper">
    <div class="integralAct" v-show="isMain">
      <el-form
        ref="activity"
        :model="activity"
        :rules="fromRules"
        labelPosition="left"
        label-width="130px"
        style="padding-left:50px;"
      >
        <el-form-item
          :label="$t('groupIntegration.name')+'：'"
          prop="name"
        >
          <el-input
            v-model="activity.name"
            :placeholder="$t('groupIntegration.ruleInfo1')"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          :label="$t('groupIntegration.advertise')+'：'"
          prop="advertise"
        >
          <el-input
            v-model="activity.advertise"
            size="small"
            class="inputWidth"
            :disabled="edit"
          ></el-input>
          <el-popover
            placement="right-start"
            width="220"
            trigger="hover"
          >
            <el-image :src="srcList.src1"></el-image>
            <el-button
              slot="reference"
              type="text"
              style="margin: 0 20px"
            >{{$t('groupIntegration.viewExample')}}</el-button>
          </el-popover>
          <span class="uniteStyle">{{$t('groupIntegration.limit')}}</span>
        </el-form-item>

        <el-form-item
          :label="$t('groupIntegration.actDate')+':' "
          required
        >
          <section style="display: flex">
            <div>
              <el-form-item
                prop='startTime'
                style="margin-bottom:0;"
              >
                <el-date-picker
                  v-model="activity.startTime"
                  type="datetime"
                  :placeholder="$t('groupIntegration.pleaseSelectStartTime')"
                  size="small"
                  style="width: 186px;"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :disabled="edit"
                >
                </el-date-picker>
              </el-form-item>
            </div>
            <span style="margin: 0 3px">{{$t('groupIntegration.to')}}</span>
            <div>
              <el-form-item
                prop="endTime"
                style="margin-bottom:0"
              >
                <el-date-picker
                  v-model="activity.endTime"
                  type="datetime"
                  size="small"
                  :placeholder="$t('groupIntegration.pleaseSelectEndTime')"
                  style="width: 186px;"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  default-time="23:59:59"
                  :disabled="edit"
                >
                </el-date-picker>
              </el-form-item>
            </div>
          </section>
        </el-form-item>

        <el-form-item
          :label="$t('groupIntegration.inteTotal')+':' "
          prop="inteTotal"
        >
          <el-input
            v-model="activity.inteTotal"
            size="small"
            class="inputWidth"
            :disabled="edit"
          ></el-input>
          <span>{{$t('groupIntegration.tip2')}}</span>
          <span class="uniteStyle">{{$t('groupIntegration.showTipe')}}<!--,修改总量时只能增加,不能减少,请谨慎设置--></span>
        </el-form-item>
        <el-form-item
         :label="$t('groupIntegration.limitAmountShow')+':' "
          required
        >
          <section style="display: flex">
            <el-form-item
              prop="limitAmount"
              style="margin-bottom:0"
            >
              <el-input
                v-model="activity.limitAmount"
                size="small"
                style="width: 90px"
                :disabled="edit"
              />
            </el-form-item>
            <span>&nbsp;{{$t('groupIntegration.showTipe2')}}&nbsp;</span>
            <el-form-item
              prop="inteGroup"
              style="margin-bottom:0"
            >
              <el-input
                v-model="activity.inteGroup"
                size="small"
                class="inputWidth"
                :disabled="edit"
              />
            </el-form-item>
            <span>&nbsp;{{$t('groupIntegration.tip2')}}</span>
            <span class="uniteStyle">{{$t('groupIntegration.showTipe3')}}</span>
          </section>
        </el-form-item>
        <el-form-item
          :label="$t('groupIntegration.joinLimit')+':' "
          prop="joinLimit"
        >
          <section style="display: flex">
            <span>{{$t('groupIntegration.showTipe4')}}&nbsp;</span>
            <el-form-item
              prop="joinLimit"
              style="margin-bottom:0"
            >
              <el-input
                v-model="activity.joinLimit"
                size="small"
                style="width:90px"
                :disabled="edit"
              />
            </el-form-item>
            <span>&nbsp;{{$t('groupIntegration.showTipe5')}}</span>
            <span class="uniteStyle">{{$t('groupIntegration.showTipe6')}}</span>
          </section>
        </el-form-item>
        <el-form-item
          :label="$t('groupIntegration.divideType')+':' "
          prop="divideType"
        >
          <el-radio-group
            v-model="activity.divideType"
            style="line-height:40px"
            :disabled="edit"
          >
            <el-radio :label="0">{{$t('groupIntegration.divideTypeTipe0')}}
              <span style="color: #999">&nbsp;&nbsp;{{$t('groupIntegration.divideTypeTipe01')}}</span>
            </el-radio>
            <br>
            <el-radio :label="1">{{$t('groupIntegration.divideTypeTipe1')}}
              <span style="color:#999">&nbsp;&nbsp;{{$t('groupIntegration.divideTypeTipe11')}}</span>
            </el-radio>
            <br>
            <el-radio :label="2">{{$t('groupIntegration.divideTypeTipe2')}}
              <span style="color:#999">&nbsp;&nbsp;{{$t('groupIntegration.divideTypeTipe21')}}</span>
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          :label="$t('groupIntegration.isDayDivide')+':' "
          prop="isDayDivide"
        >
          <span>{{$t('groupIntegration.isDayDivideInfo')}}</span>
          <br>
          <el-radio-group v-model="activity.isDayDivide" :disabled="edit">
            <el-radio :label="1">{{$t('groupIntegration.yes')}}</el-radio>
            <el-radio :label="0">{{$t('groupIntegration.no')}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          :label="$t('groupIntegration.activityInfo')+':' "
           prop="activityInfo"
        >
        <el-button type="primary" @click="showAct" size="small">{{$t('groupIntegration.setActivityInfo')}}</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="btn" v-show="isMain">
      <el-button
        type="primary"
        size="small"
        @click="saveActivity()"
      >{{$t('groupIntegration.save')}}</el-button>
    </div>
    <groupIntegrationActivityWrtie v-if="!isMain" @ActivityMsg="activityMsg" :sendMsg="sendMsg"/>
  </div>
</template>
<script>
import { createGroupIntegration, editGroupIntegration, selectGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
import groupIntegrationActivityWrtie from './groupIntegrationActivityWrtie'
export default {
  props: {
    isEditId: {
      type: Number,
      default: 0
    }
  },
  components: {
    groupIntegrationActivityWrtie
  },
  data () {
    var checklimitAmount = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('groupIntegration.info1')))
      } else {
        if (value < 2) {
          callback(new Error(this.$t('groupIntegration.info2')))
        }
        if (value > 20) {
          callback(new Error(this.$t('groupIntegration.info3')))
        }
        let flag = new RegExp(this.rgexp).test(value)
        if (!flag) {
          callback(new Error(this.$t('groupIntegration.info4')))
        }
        callback()
      }
    }
    var checkInteGroup = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('groupIntegration.info5')))
      } else {
        if (value < 0) {
          callback(new Error(this.$t('groupIntegration.info51')))
        }
        let flag = new RegExp(this.rgexp).test(value)
        if (!flag) {
          callback(new Error(this.$t('groupIntegration.info4')))
        }
        callback()
      }
    }
    var checkJoinLimit = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('groupIntegration.info7')))
      } else {
        if (value < 0) {
          callback(new Error(this.$t('groupIntegration.info8')))
        }
        let flag = new RegExp('^[0-9]([0-9])*$').test(value)
        if (!flag) {
          callback(new Error(this.$t('groupIntegration.info4')))
        }
        callback()
      }
    }
    var checkinteTotal = (rule, value, callback) => {
      console.log('瓜分积分数' + value)
      if (this.isEmpty(value)) {
        callback(new Error(this.$t('groupIntegration.info9')))
      } else {
        if (value < 0) {
          callback(new Error(this.$t('groupIntegration.info10')))
        }
        let flag = new RegExp('^[0-9]([0-9])*$').test(value)
        if (!flag) {
          callback(new Error(this.$t('groupIntegration.info4')))
        }
        callback()
      }
    }
    var checkActivityInfo = (rule, value, callback) => {
      callback()
    }
    return {
      isMain: true,
      edit: false,
      paramId: null,
      sendMsg: null,
      canSave: false,
      activity: {
        id: null,
        name: '',
        advertise: this.$t('groupIntegration.info11'),
        startTime: '',
        endTime: '',
        inteTotal: null,
        inteGroup: null,
        limitAmount: null,
        joinLimit: 1,
        divideType: 0,
        isDayDivide: 0,
        activityCopywriting: {
          document: null,
          is_use_default: null
        }
      },
      fromRules: {
        name: [{ required: true, message: this.$t('groupIntegration.ruleInfo1'), trigger: 'blur' }],
        advertise: [{ required: true, message: this.$t('groupIntegration.ruleInfo2'), trigger: 'blur' }],
        startTime: [{ required: true, message: this.$t('groupIntegration.ruleInfo3'), trigger: 'blur' }],
        endTime: [{ required: true, message: this.$t('groupIntegration.ruleInfo4'), trigger: 'blur' }],
        inteTotal: [{ required: true, validator: checkinteTotal, trigger: 'blur' }],
        limitAmount: [{ required: true, validator: checklimitAmount, trigger: 'blur' }],
        inteGroup: [{ required: true, validator: checkInteGroup, trigger: 'blur' }],
        joinLimit: [{ required: true, validator: checkJoinLimit, trigger: 'blur' }],
        divideType: [{ required: true, trigger: 'blur' }],
        isDayDivide: [{ required: true, trigger: 'blur' }],
        activityInfo: [{ required: true, validator: checkActivityInfo }]
      },
      srcList: {
        src1: `${this.$imageHost}/image/admin/new_preview_image/pin_integration.jpg`
      },
      rgexp: '^[1-9]([0-9])*$'
    }
  },
  methods: {
    loadInfo (id) {
      selectGroupIntegration(id).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.activity = res.content
          if (!this.isEmpty(res.content.activityCopywriting.document)) {
            this.canSave = true
          }
          console.log('编辑返回')
          console.log(this.activity)
        }
      })
    },
    saveActivity () {
      this.$refs.activity.validate((valid) => {
        if (valid) {
          if (!this.checkInfo()) {
            return false
          }
          if (!this.edit) {
            this.addActivity()
          } else {
            this.editActivity()
          }
        } else {
          this.$message.warning(this.$t('groupIntegration.ruleInfo5'))
          return false
        }
      })
    },
    addActivity () {
      createGroupIntegration(this.activity).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(this.$t('groupIntegration.createSuccess'))
          this.backHome()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    editActivity () {
      editGroupIntegration(this.activity).then((res) => {
        console.log(res)
        if (res.error === 0) {
          if (res.error === 0) {
            this.$message.success(this.$t('groupIntegration.editSuccess'))
            this.backHome()
          } else {
            this.$message.error(res.message)
          }
        }
      })
    },
    backHome () {
      console.log('点了')
      let params = {
        'flag': 6
      }
      this.$emit('backHome', params)
    },
    checkInfo () {
      if (parseInt(this.activity.inteGroup) < parseInt(this.activity.limitAmount)) {
        this.$message.warning(this.$t('groupIntegration.warningInfo1'))
        return false
      }
      if (parseInt(this.activity.inteTotal) > 0 && parseInt(this.activity.inteGroup) > parseInt(this.activity.inteTotal)) {
        this.$message.warning(this.$t('groupIntegration.warningInfo2'))
        return false
      }
      if (parseInt(this.activity.startTime) > parseInt(this.activity.endTime)) {
        this.$message.warning(this.$t('groupIntegration.warningInfo3'))
        return false
      }
      if (!this.canSave) {
        this.$message.warning(this.$t('groupIntegration.warningInfo4'))
        return false
      }
      return true
    },
    isEmpty (obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 显示活动规则说明
    showAct () {
      this.isMain = false
      console.log(this.activity.activityCopywriting)
      this.sendMsg = this.activity.activityCopywriting
    },
    activityMsg (data) {
      console.log('回来的值')
      console.log(data)
      this.canSave = true
      this.activity.activityCopywriting = data
      this.isMain = true
    }
  },
  mounted () {
    // const id = this.$route.params.id
    // this.edit = !!id
    this.langDefault()
    console.log('编辑的值' + this.isEditId)
    if (this.isEditId === 0) {
      this.edit = false
      return
    }
    this.edit = true
    console.log('是不是编辑' + this.edit)
    this.paramId = this.isEditId
    this.loadInfo(this.isEditId)
  }

}
</script>
<style lang="scss" scoped>
.groupwrapper {
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .integralAct {
    position: relative;
    background: #fff;
    padding: 30px 0;
    margin-bottom: 52px;
    .inputWidth {
      width: 160px;
    }
    .el-form-item {
      margin-bottom: 15px;
    }
    .uniteStyle {
      color: #999;
      margin-left: 10px;
    }
  }
  .btn {
    position: absolute;
    bottom: 0;
    right: 27px;
    left: 160px;
    height: 52px;
    padding: 10px 0;
    background-color: #fff;
    text-align: center;
  }
}
</style>
