<template>
  <div class="evaluationReviewContent">
    <div class="condition clearfix">
      <div class="fl">
        <el-radio-group
          v-model="reviewStatus"
          @change="changeReviewStatus"
        >
          <el-radio :label="0">不用审核</el-radio>
          <el-radio :label="1">先发后审</el-radio>
          <el-radio :label="2">先审后发</el-radio>
        </el-radio-group>
      </div>
      <div class="fr">
        <el-switch
          v-model="hideEvaluation"
          active-color="#f7931e"
          @change="changeHideEvaluation"
        ></el-switch>
        <span>{{hideEvaluation ? '已开启':'已关闭'}}</span>
        <span class="tips">设置前端是否隐藏未填写心得的评价</span>
      </div>
    </div>
    <slot
      name="evaluationRecord"
      v-if="viewReload"
    ></slot>
  </div>
</template>

<script>
import { CommentCheckConfig, CommentSwitchConfig, getCommentConfig, getCommentSwitch } from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
export default {
  data () {
    return {
      reviewStatus: null,
      hideEvaluation: null,
      viewReload: true
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      getCommentConfig().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.reviewStatus = res.content
        }
      })
      getCommentSwitch().then(res => {
        if (res.error === 0) {
          this.hideEvaluation = !!res.content
        }
      })
    },
    changeReviewStatus (val) {
      CommentCheckConfig({ v: val }).then(res => {
        if (res.error === 0) {
          this.$message.success('修改成功')
          this.reload()
        }
      })
    },
    changeHideEvaluation (val) {
      CommentSwitchConfig({ v: val ? 1 : 0 }).then(res => {
        if (res.error === 0) {
          this.$message.success('修改成功')
        }
      })
    },
    reload () {
      this.viewReload = false
      this.$nextTick(function () {
        this.viewReload = true
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.condition {
  background: #fff;
  padding-left: 10px;
  padding-right: 10px;
  padding-bottom: 10px;
  .fl {
    float: left;
  }
  .fr {
    float: right;
  }
  .tips {
    color: #999;
  }
}
.clearfix:after {
  visibility: hidden;
  display: block;
  font-size: 0;
  content: "";
  clear: both;
  height: 0;
}
</style>
