<template>
  <div class="integralAct">
    <el-form
      ref="activity"
      :model="activity"
      :rules="fromRules"
      labelPosition="left"
      label-width="120px"
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
        <el-date-picker
          v-model="activity.startTime"
          type="datetime"
          placeholder="选择开始时间"
          size="small"
          style="width: 160px;"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
        <span style="margin: 0 3px">至</span>
        <el-date-picker
          v-model="activity.endTime"
          type="datetime"
          size="small"
          placeholder="选择结束时间"
          style="width: 160px;"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item
        label="瓜分积分总数："
        prop="inteTotal"
      >
        <el-input
          v-model="activity.inteTotal"
          size="small"
          class="inputWidth"
        ></el-input>
        <span>积分</span>
        <span class="uniteStyle">0表示不限制数量,修改总量时只能增加,不能减少,请谨慎设置</span>
      </el-form-item>
      <el-form-item
        label="单团瓜分内容: "
        required
      >
        <section>
          <el-input
            v-model="activity.limitAmount"
            size="small"
            style="width: 90px"
          />
          <span>人，瓜分</span>
          <el-input
            v-model="activity.inteGroup"
            size="small"
            class="inputWidth"
          />
          <span>积分</span>
          <span class="uniteStyle">成团人数需≥2人且≤20人,瓜分积分数量需大于成团人数</span>
        </section>
      </el-form-item>
      <el-form-item
        label="参团限制："
        prop="joinLimit"
      >
        <section>
          <span>每人最多参加&nbsp;</span>
          <el-input
            v-model="activity.joinLimit"
            size="small"
            style="width:90px"
          />
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
        <el-radio-group v-model="activity.isDayDivide">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        size="small"
        @click="saveActivity()"
      >保存</el-button>
      <!-- 单团瓜分积分数不能大于积分总数 -->
    </div>
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
      fromRules: {
        name: [{ required: true, message: '请填写活动名称', trigger: 'blur' }],
        advertise: [{ required: true, message: '请填写宣传语', trigger: 'blur' }],
        startTime: [{ type: 'date', required: true, message: '请选择时间', trigger: 'blur' }],
        endtTime: [{ type: 'date', required: true, message: '请选择时间', trigger: 'blur' }],
        inteTotal: [{ required: true, message: '请填写瓜分积分数', trigger: 'blur' }],
        limitAmount: [{ required: true, message: '请填写瓜分人数', trigger: 'blur' }],
        inteGroup: [{ required: true, message: '请输入瓜分积分总数', trigger: 'blur' }],
        joinLimit: [{ required: true, message: '请填写参团限制', trigger: 'blur' }],
        divideType: [{ required: true, trigger: 'blur' }],
        isDayDivide: [{ required: true, trigger: 'blur' }]
      },
      srcList: {
        src1: `${this.$imageHost}/image/admin/new_preview_image/pin_integration.jpg`
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
  padding: 30px 0;
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
  .btn {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: absolute;
    z-index: 0;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
    margin-right: 10px;
  }
}
</style>
