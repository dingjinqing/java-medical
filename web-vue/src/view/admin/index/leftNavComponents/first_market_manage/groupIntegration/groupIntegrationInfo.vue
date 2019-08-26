<template>
  <div>
    <el-form
      ref="form"
      :model="activity"
      label-width="150px"
    >
      <el-form-item label="活动名称：">
        <el-input
          v-model="activity.name"
          placeholder="请填写活动名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="宣传语：">
        <el-input v-model="activity.advertise"></el-input>
      </el-form-item>
      <el-form-item label="有效期：">
        <el-col :span="11">
          <el-date-picker
            type="datetime"
            v-model="activity.startTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%;"
          ></el-date-picker>
        </el-col>
        <el-col
          class="line"
          :span="2"
        >-</el-col>
        <el-col :span="11">
          <el-date-picker
            type="datetime"
            v-model="activity.endTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%;"
          >
          </el-date-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="瓜分积分总数：">
        <el-input v-model="activity.inteTotal"></el-input>
      </el-form-item>
      <el-form-item label="单团瓜分内容：">
        <el-col>
          <el-input v-model="activity.limitAmount" />
        </el-col>
        <el-col>人，瓜分</el-col>
        <el-col>
          <el-input v-model="activity.inteGroup" />
        </el-col>
        <el-col>
          积分
        </el-col>
      </el-form-item>
      <el-form-item label="参团限制：">
        <el-col :span="3">每人最多参加</el-col>
        <el-col :span="1">
          <el-input v-model="activity.joinLimit" />
        </el-col>
        <el-col :span="3">次新团</el-col>
      </el-form-item>
      <el-form-item label="瓜分方式：">
        <el-radio-group v-model="activity.divideType">
          <el-radio :label="0">按邀请好友数量瓜分</el-radio>
          <el-radio :label="1">好友均分</el-radio>
          <el-radio :label="2">随机瓜分</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="瓜分限制：">
        <span>用户开团24小时后,拼团未满员是否可以瓜分积分</span>
        <el-radio-group v-model="activity.isDayDivide">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="saveActivity()"
        >保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { createGroupIntegration, editGroupIntegration, selectGroupIntegration } from '@/api/admin/marketManage/groupIntegrationList.js'
export default {
  data () {
    return {
      edit: false,
      paramId: null,
      activity: {
        id: null,
        name: '',
        advertise: '积分购物可抵现金',
        startTime: '',
        endTime: '',
        inteTotal: '',
        inteGroup: '',
        limitAmount: '',
        joinLimit: '',
        divideType: 0,
        isDayDivide: 0
      }
    }
  },
  methods: {
    loadInfo (id) {
      selectGroupIntegration(id).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.activity = res.content
        }
      })
    },
    saveActivity () {
      if (!this.edit) {
        this.addActivity()
      } else {
        this.editActivity()
      }
    },
    addActivity () {
      createGroupIntegration(this.activity).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message({
            message: '创建活动成功',
            type: 'success'
          })
        }
      })
    },
    editActivity () {
      editGroupIntegration(this.activity).then((res) => {
        console.log(res)
        if (res.error === 0) {
          this.$message({
            message: '保存活动成功',
            type: 'success'
          })
        }
      })
    }

  },
  mounted () {
    const id = this.$route.params.id
    this.edit = !!id
    if (!this.edit) {
      return
    }
    this.paramId = id
    this.loadInfo(id)
  }

}
</script>
<style lang="scss" scoped>
</style>
