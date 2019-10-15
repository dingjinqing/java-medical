<template>
  <div>
    <div class="technician_list_page">
      <div class="list_info">
        <label>
          技师姓名：
          <el-input
            size="small"
            class="filter_input"
            placeholder="技师名称"
          ></el-input>
        </label>
        <!-- 服务分类下拉 -->
        <el-select
          size="small"
          v-model="technicianCat"
        >
          <el-option
            label="请选择技师分组"
            :value="null"
          ></el-option>
          <el-option
            v-for="item in technicianCats"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
        <el-input
          type="tel"
          placeholder="技师电话"
          style="width: 188px;"
          size="small"
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
        >查询</el-button>
      </div>
      <div class="list_table">
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
            type="selection"
            align="center"
          ></el-table-column>
          <el-table-column
            label="技师名称"
            prop="name"
          >
            <template slot-scope="{ row }">
              <div>
                <img
                  class="technician_list_img"
                  :src="row.imgPath"
                  alt=""
                >
                <p></p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="技师分组"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="联系电话"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="服务项目"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="添加时间"
            prop="name"
          ></el-table-column>
          <el-table-column
            label="介绍"
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
                  >排班管理</span>
                </el-tooltip>
                <el-tooltip>
                  <span
                    class="iconSpan"
                    @click="edit()"
                  >编辑</span>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="table-page">
          <pagination
            :page-params.sync="pageParams"
            @pagination="initDataList"
          ></pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination },
  data () {
    return {
      technicianCat: null,
      technicianCats: [
        { id: 1, name: '添加' }
      ],
      tableData: [],
      pageParams: {}
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

<style lang="scss" scoped>
.technician_list_page {
  margin: 0 25px;
  .list_info {
    padding-bottom: 10px;
    .filter_input {
      width: 130px;
    }
    .technician_list_img {
      display: inline-block;
      width: 60px;
      height: 60px;
    }
  }
}
</style>
