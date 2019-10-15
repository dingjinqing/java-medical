<template>
  <div>
    <div class="sevice_classify_page">
      <div class="list-info technician_class_info">
        <div>
          <el-input
            placeholder="请输入分类名称"
            style="width: 188px;"
            size="small"
          ></el-input>
          <el-button
            type="primary"
            size="small"
          >保存</el-button>
        </div>
      </div>
      <div class="list-table">
        <el-table
          ref="technicianTable"
          :data="tableData"
          class="tableClass"
          max-height="500"
          border
          :header-cell-style="{
                'background-color':'#f5f5f5',
                'border':'none'
              }"
        >
          <el-table-column
            label="分组名称"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="创建时间"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="操作"
            prop="operate"
          >
            <template slot-scope="{ row }">
              <div>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit(row.id)"
                  >编辑</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >删除</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      tableData: []
    }
  },
  methods: {
    edit () {

    }
  }
}
</script>

<style lang="scss" scoped>
.sevice_classify_page {
  margin: 0 25px;
  .list-info {
    display: flex;
    justify-content: space-between;
    padding-bottom: 10px;
  }
}
</style>
