<template>
  <div class="addCalendarMain">
    <div class="mainTop">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item
          label="事件名称："
          prop="eventName"
        >
          <el-input
            size="small"
            v-model="ruleForm.eventName"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="活动时间："
          required
          prop="date"
        >
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="ruleForm.date"
            size="small"
            style="width:188px"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <div class="explain">
        <div class="explainTop">
          <span>事件说明：</span>
          <div>
            <el-button
              size="small"
              type="primary"
              @click="handleToClickExplain()"
            >编辑</el-button>
            <div
              class="rich"
              v-html="richText"
            >
            </div>
          </div>

        </div>

      </div>
    </div>
    <div
      class="mainBottom"
      style="margin-top:10px"
    >
      内容
    </div>
    <!--事件说明弹窗-->
    <EventExplainDialog
      :explainVisible.sync="explainVisible"
      :backText="backText"
      @input="handleToGetRich"
    />
  </div>
</template>
<script>
export default {
  components: {
    EventExplainDialog: () => import('./eventExplainDialog')
  },
  data () {
    return {
      ruleForm: {
        eventName: '',
        date: ''
      },
      rules: {
        eventName: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        date: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ]
      },
      explainVisible: false,
      backText: '',
      richText: ''
    }
  },
  methods: {
    // 点击事件说明编辑
    handleToClickExplain () {
      this.explainVisible = true
    },
    // 获取富文本输入内容
    handleToGetRich (res) {
      console.log(res)
      this.richText = res
    }
  }
}
</script>
<style lang="scss" scoped>
.addCalendarMain {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .mainTop,
  .mainBottom {
    background-color: #fff;
    padding: 15px;
  }
  .mainTop {
    /deep/ .el-input {
      width: auto;
    }
    .explain {
      padding-left: 18px;
      .explainTop {
        display: flex;
        span {
          display: block;
          margin-right: 13px;
          height: 32px;
          line-height: 32px;
        }
      }
      .rich {
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
      }
    }
  }
}
</style>
