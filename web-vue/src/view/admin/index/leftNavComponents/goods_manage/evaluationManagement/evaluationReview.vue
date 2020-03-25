<template>
  <div class="evaluationReviewContent">
    <slot
      name="evaluationRecord"
      v-if="viewReload"
    ></slot>
  </div>
</template>

<script>
export default {
  data () {
    return {
      viewReload: true
    }
  },
  mounted () {
  },
  methods: {
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
