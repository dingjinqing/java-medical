<template>
  <div class="introductionContainer">
    <div class="introductionMain">
      <div class="introductionMain_top">
        <div class="topLeft">说明：会员导入适用于会员迁移得场景，系统会导入增量会员并更新未激活会员得信息，不会更新已激活会员得信息</div>
        <div class="topRight">
          <el-button
            type="primary"
            size="small"
          >设置激活通知</el-button>
          <el-button
            type="primary"
            size="small"
          >会员导入</el-button>
        </div>
      </div>
      <div class="introductionMain_middle">
        <div class="batchNumber">
          <span>批次号</span>
          <el-input
            v-model="input"
            placeholder="请输入内容"
            size="small"
          ></el-input>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped lang="scss">
.introductionContainer {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 1300px;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .introductionMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 10px;
    .introductionMain_top {
      display: flex;
      justify-content: space-between;
      background-color: #f5f5f5;
      padding: 10px;
      .topLeft {
        height: 32px;
        line-height: 32px;
      }
    }
    .introductionMain_middle {
      padding: 8px 0;
      span {
        white-space: normal;
        display: block;
        height: 32px;
        line-height: 32px;
        margin: 0 25px;
      }
      .batchNumber {
        display: flex;
        /deep/ .el-input {
          width: 150px;
        }
      }
    }
  }
}
</style>
