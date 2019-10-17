<template>
  <div class="scheduling_page">
    <div class="order_container">
      <div class="order_header">
        <el-button
          type="primary"
          plain
          size="small"
        >设置班次</el-button>
        <span class="tips">门店营业时间为：每天 09:00-22:00</span>
      </div>
      <div class="order_content">
        <div class="week_picker_wrap">
          <WeekPicker></WeekPicker>
        </div>
      </div>
      <!-- 设置班次 -->
      <el-dialog>

      </el-dialog>
      <!-- 编辑排班 -->
      <el-dialog>

      </el-dialog>
    </div>
  </div>
</template>

<script>
import WeekPicker from '@/components/admin/weekPicker'
export default {
  components: { WeekPicker },
  data () {
    return {}
  }
}
</script>

<style lang="scss" scoped>
.scheduling_page {
  padding: 10px;
  font-size: 14px;
  .order_container {
    padding: 0 20px;
    background: #fff;
    .order_header {
      padding: 18px 0;
      border-bottom: 1px solid #eee;
    }
    .order_content {
      .week_picker_wrap {
        display: flex;
      }
    }
  }
}
</style>
