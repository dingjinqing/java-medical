<!--
* 效果数据页面
*
* @author:赵鑫
-->
<template>
  <div>
    <wrapper class="dataContent">
      <div style="display:flex">
        <div style="height:32px;line-height:32px">筛选日期：</div>
        <div class="selectTime">
          <el-date-picker
            size="small"
            v-model="value1"
            type="datetime"
            placeholder="选择开始时间"
            default-time="12:00:00"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            size="small"
            v-model="value1"
            type="datetime"
            placeholder="选择接受时间"
            default-time="12:00:00"
          >
          </el-date-picker>
        </div>
        <el-button
          style="margin-left: 10px;"
          type="primary"
          size="mini"
        >筛选</el-button>
      </div>

      <section>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">活动实付总金额(元)</div>
            <i class="el-icon-warning-outline icons"></i>
          </div>
          <div
            class="num"
            style="color: #5A8BFF"
          >0</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">活动优惠总金额(元)</div>
            <i class="el-icon-warning-outline icons"></i>
          </div>
          <div
            class="num"
            style="color: #fc6181;"
          >0</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">费效比</div>
            <i class="el-icon-warning-outline icons"></i>
          </div>
          <div
            class="num"
            style="color: #fdb64a;"
          >0%</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">新成交用户数</div>
            <i class="el-icon-warning-outline icons"></i>
          </div>
          <div
            class="num"
            style="color: #3dcf9a;"
          >0</div>
        </div>
        <div class="fromInfo">
          <div style="display:flex">
            <div class="title">老成交用户数</div>
            <i class="el-icon-warning-outline icons"></i>
          </div>
          <div
            class="num"
            style="color: #8379f7;"
          >0</div>
        </div>
      </section>

    </wrapper>
  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'

export default {
  components: { wrapper },
  data () {
    return {
      value1: ''
    }
  }
}

</script>
<style lang="scss" scoped>
.dataContent {
  padding: 10px 50px;
  font-size: 14px;
  section {
    border: 1px solid #eee;
    height: 130px;
    width: 85%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 30px 0 20px 0;
    .fromInfo {
      flex: 1;
      height: 130px;
      position: relative;
      border-right: 1px solid #eee;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      .icons {
        margin-left: 10px;
        position: relative;
      }
      .num {
        margin-top: 15px;
        font-size: 30px;
      }
    }
  }
}
</style>
