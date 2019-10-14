<template>
  <div>
    <div class="sevice_list">
      <div class="list-info service_class_info">
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
        <div>
          <el-button
            type="primary"
            size="small"
          >查询</el-button>
          <el-input
            placeholder="请输入分类查询"
            style="width: 188px;"
            suffix-icon="el-icon-search"
            size="small"
          ></el-input>
        </div>
      </div>
      <div class="list-table">
        <el-table
          ref="serviceTable"
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
            label="分类名"
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
