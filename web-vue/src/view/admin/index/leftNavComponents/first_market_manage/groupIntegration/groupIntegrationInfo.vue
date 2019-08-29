<template>
  <div class="integralAct">
    <el-form
      ref="form"
      :model="activity"
      label-width="150px"
    >
      <el-form-item label="活动名称：">
        <el-input
          v-model="activity.name"
          placeholder="请填写活动名称"
          class="inputWidth"
        ></el-input>
      </el-form-item>
      <el-form-item label="宣传语：">
        <el-input
          v-model="activity.advertise"
          class="inputWidth"
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
        <span>限制十个字以内</span>
      </el-form-item>

      <el-form-item label="有效期: ">
        <el-date-picker
          v-model="activity.startTime"
          type="datetime"
          placeholder="选择开始时间"
          style="width: 200px;"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
        <span>至</span>
        <el-date-picker
          v-model="activity.endTime"
          type="datetime"
          placeholder="选择结束时间"
          style="width: 200px;"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="瓜分积分总数：">
        <el-input
          v-model="activity.inteTotal"
          class="inputWidth"
        ></el-input>
        <span>积分</span>
        <span>0表示不限制数量,修改总量时只能增加,不能减少,请谨慎设置</span>
      </el-form-item>
      <el-form-item label="单团瓜分内容: ">
        <section>
          <el-input
            v-model="activity.limitAmount"
            style="width: 90px"
          />
          <span>人，瓜分</span>
          <el-input
            v-model="activity.inteGroup"
            class="inputWidth"
          />
          <span>积分</span>
          <span>成团人数需≥2人且≤20人,瓜分积分数量需大于成团人数</span>
        </section>
      </el-form-item>
      <el-form-item label="参团限制：">
        <section>
          <span>每人最多参加</span>
          <el-input
            v-model="activity.joinLimit"
            style="width:90px"
          />
          <span>次新团</span>
          <span>默认为1,0表示不限制数量。仅限制参与其他用户所开的团的数量</span>
        </section>
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
      },
      srcList: {
        src1: `${this.$imageHost}/image/admin/share/bargain_share.jpg`
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
.integralAct {
  background: #fff;
  padding-top: 30px;
  .inputWidth {
    width: 200px;
  }
}
</style>
