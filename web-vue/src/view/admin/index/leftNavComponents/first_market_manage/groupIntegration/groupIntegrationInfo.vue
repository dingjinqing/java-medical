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
          label="活动名称："
          prop="name"
        >
          <el-input
            v-model="activity.name"
            placeholder="请填写活动名称"
            size="small"
            class="inputWidth"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="宣传语："
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
            >查看示例</el-button>
          </el-popover>
          <span class="uniteStyle">限制十个字以内</span>
        </el-form-item>

        <el-form-item
          label="有效期: "
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
                  placeholder="选择开始时间"
                  size="small"
                  style="width: 186px;"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :disabled="edit"
                >
                </el-date-picker>
              </el-form-item>
            </div>
            <span style="margin: 0 3px">至</span>
            <div>
              <el-form-item
                prop="endTime"
                style="margin-bottom:0"
              >
                <el-date-picker
                  v-model="activity.endTime"
                  type="datetime"
                  size="small"
                  placeholder="选择结束时间"
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
          label="瓜分积分总数："
          prop="inteTotal"
        >
          <el-input
            v-model="activity.inteTotal"
            size="small"
            class="inputWidth"
            :disabled="edit"
          ></el-input>
          <span>积分</span>
          <span class="uniteStyle">0表示不限制数量<!--,修改总量时只能增加,不能减少,请谨慎设置--></span>
        </el-form-item>
        <el-form-item
          label="单团瓜分内容: "
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
            <span>&nbsp;人，瓜分&nbsp;</span>
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
            <span>&nbsp;积分</span>
            <span class="uniteStyle">成团人数需≥2人且≤20人,瓜分积分数量需大于成团人数</span>
          </section>
        </el-form-item>
        <el-form-item
          label="参团限制："
          prop="joinLimit"
        >
          <section style="display: flex">
            <span>每人最多参加&nbsp;</span>
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
            <span>&nbsp;次新团</span>
            <span class="uniteStyle">默认为1,0表示不限制数量。仅限制参与其他用户所开的团的数量</span>
          </section>
        </el-form-item>
        <el-form-item
          label="瓜分方式："
          prop="divideType"
        >
          <el-radio-group
            v-model="activity.divideType"
            style="line-height:40px"
            :disabled="edit"
          >
            <el-radio :label="0">按邀请好友数量瓜分
              <span style="color: #999">&nbsp;&nbsp;(邀请好友数量越多获得积分越多)</span>
            </el-radio>
            <br>
            <el-radio :label="1">好友均分
              <span style="color:#999">&nbsp;&nbsp;(每个人获得积分数量相同)</span>
            </el-radio>
            <br>
            <el-radio :label="2">随机瓜分
              <span style="color:#999">&nbsp;&nbsp;(每个人获得随机数量积分)</span>
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="瓜分限制："
          prop="isDayDivide"
        >
          <span>用户开团24小时后,拼团未满员是否可以瓜分积分</span>
          <br>
          <el-radio-group v-model="activity.isDayDivide" :disabled="edit">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="活动规则说明："
           prop="activityInfo"
        >
        <el-button type="primary" @click="showAct" size="small">设置规则说明</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="btn" v-show="isMain">
      <el-button
        type="primary"
        size="small"
        @click="saveActivity()"
      >保存</el-button>
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
        callback(new Error('请填写瓜分人数'))
      } else {
        if (value < 2) {
          callback(new Error('成团人数应大于等于2人'))
        }
        if (value > 20) {
          callback(new Error('成团人数应小于等于20人'))
        }
        let flag = new RegExp(this.rgexp).test(value)
        if (!flag) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    var checkInteGroup = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入瓜分积分数'))
      } else {
        if (value < 0) {
          callback(new Error('请输入瓜分积分数为大于0'))
        }
        let flag = new RegExp(this.rgexp).test(value)
        if (!flag) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    var checkJoinLimit = (rule, value, callback) => {
      if (this.isEmpty(value)) {
        callback(new Error('请输入参团限制'))
      } else {
        if (value < 0) {
          callback(new Error('请输入参团限制为大于0'))
        }
        let flag = new RegExp('^[0-9]([0-9])*$').test(value)
        if (!flag) {
          callback(new Error('请输入正整数'))
        }
        callback()
      }
    }
    var checkinteTotal = (rule, value, callback) => {
      console.log('瓜分积分数' + value)
      if (this.isEmpty(value)) {
        callback(new Error('请输入瓜分积总数'))
      } else {
        if (value < 0) {
          callback(new Error('请输入瓜分积总数为大于0'))
        }
        let flag = new RegExp('^[0-9]([0-9])*$').test(value)
        if (!flag) {
          callback(new Error('请输入正整数'))
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
        advertise: '积分购物可抵现金',
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
        name: [{ required: true, message: '请填写活动名称', trigger: 'blur' }],
        advertise: [{ required: true, message: '请填写宣传语', trigger: 'blur' }],
        startTime: [{ required: true, message: '请选开始时间', trigger: 'blur' }],
        endTime: [{ required: true, message: '请选择结束时间', trigger: 'blur' }],
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
          this.$message.warning('请输入必输项')
          return false
        }
      })
    },
    addActivity () {
      createGroupIntegration(this.activity).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success('创建活动成功')
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
            this.$message.success('修改活动成功')
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
        this.$message.warning('瓜分积分数需要大于成团人数')
        return false
      }
      if (parseInt(this.activity.inteTotal) > 0 && parseInt(this.activity.inteGroup) > parseInt(this.activity.inteTotal)) {
        this.$message.warning('单团瓜分积分数不能大于总积分数')
        return false
      }
      if (parseInt(this.activity.startTime) > parseInt(this.activity.endTime)) {
        this.$message.warning('开始时间应小于结束时间')
        return false
      }
      if (!this.canSave) {
        this.$message.warning('请填写活动规则说明')
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
    if (this.isEditId === 0) {
      this.edit = false
      return
    }
    this.edit = true
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
