<template>
  <div class="sendRecord">
    <el-card>
      <div>
        <el-form
          :inline="true"
          :form="formData"
          label-width="100px"
        >
          <el-form-item :label="labels.name">
            <el-input
              style="width:120px"
              size="small"
              v-model="formData.name"
            ></el-input>
          </el-form-item>
          <el-form-item :label="labels.templatePlatfrom"></el-form-item>
          <el-form-item :label="labels.isVisit"></el-form-item>
          <el-form-item label="">
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
          </el-form-item>

        </el-form>
      </div>
      <div>
        <el-table
          :data="tableData"
          style="width: 100%"
          center
        >
          <el-table-column
            prop="date"
            :label="tableLabels.name"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            :label="tableLabels.templatePlatfrom"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            :label="tableLabels.sendStatus"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            :label="tableLabels.isVisit"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            :label="tableLabels.visitTime"
          >
          </el-table-column>
          <el-table-column
            prop="date"
            label="日期"
          >
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: `sendRecord`,
  data () {
    return {
      labels: {
        name: `用户昵称`,
        templatePlatfrom: `发送类型`,
        isVisit: `是否已点击`
      },
      /**
       * 表单数据
       */
      formData: {
        name: ``,
        templatePlatfrom: ``,
        isVisit: ``
      },
      /**
       * 表格数据
       */
      tableData: [],
      tableLabels: {
        name: `用户昵称`,
        templatePlatfrom: `发送类型`,
        sendStatus: `是否已送达`,
        isVisit: `是否已点击`,
        visitTime: `首次访问时间`
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.sendRecord {
  padding: 10px;
}
</style>
