<template>
  <div class="evaluationReviewContent">
    <div class="condition clearfix">
      <div class="fl">
        <el-radio-group v-model="reviewStatus">
          <el-radio :label="0">不用审核</el-radio>
          <el-radio :label="1">先发后审</el-radio>
          <el-radio :label="2">先审后发</el-radio>
        </el-radio-group>
      </div>
      <div class="fr">
        <el-switch
          v-model="hideEvaluation"
          active-color="#f7931e"
        ></el-switch>
        <span>{{hideEvaluation ? '已开启':'已关闭'}}</span>
        <span class="tips">设置前端是否隐藏未填写心得的评价</span>
      </div>
    </div>
    <slot name="evaluationRecord"></slot>
  </div>
</template>

<script>
export default {
  data () {
    return {
      reviewStatus: 0,
      hideEvaluation: 0
    }
  }
}
</script>

<style lang="scss" scoped>
.condition {
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
