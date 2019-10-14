<template>
  <div>
    <div class="sevice_list">
      <div class="list_info">
        <!-- 服务分类下拉 -->
        <el-select
          size="small"
          v-model="serviceCat"
        >
          <el-option
            label="请选择服务分类"
            :value="null"
          ></el-option>
          <el-option
            v-for="item in serviceCats"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
        <el-input
          placeholder="搜索服务"
          style="width: 188px;"
          size="small"
        >
          <i
            class="el-icon-search el-input__icon"
            slot="suffix"
            @click="searchHandle"
          >
          </i>
        </el-input>
        <el-button
          type="primary"
          size="small"
        >查询</el-button>
      </div>
      <div class="list_table">
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
            type="selection"
            align="center"
          ></el-table-column>
          <el-table-column
            label="服务名称"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="价格"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="服务分类"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="销量"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="添加时间"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="状态"
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
                  >上架</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >分享</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >查看评价</span>
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
      serviceCat: null,
      serviceCats: [
        { id: 1, name: '添加' }
      ],
      tableData: []
    }
  },
  methods: {
    searchHandle () {

    },
    edit () {

    },
    initDataList () {

    }
  }
}
</script>
