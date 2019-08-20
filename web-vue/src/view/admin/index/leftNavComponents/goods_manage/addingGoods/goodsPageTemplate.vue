<template>
  <div class="goodsPageTemplate">
    <el-dialog
      title="选择模板"
      center
      :visible.sync="visible"
      :before-close="handleClose"
    >
      <el-form
        :inline="true"
        :model="formData"
      >
        <el-form-item
          label="页面名称"
          prop="name"
        >
          <el-input
            placeholder="请输入页面名称"
            size="small"
            v-model="formData.name"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="页面分类"
          prop="sort"
        >
          <el-select
            size="small"
            v-model="value"
            placeholder="请选择分类"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="handleSearch"
            type="primary"
            size="small"
          >搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData">
        <el-table-column
          property=""
          label="页面名称"
          width="150"
        ></el-table-column>
        <el-table-column
          property=""
          label="创建时间"
          width="200"
        ></el-table-column>
        <el-table-column
          property=""
          label="是否首页"
        ></el-table-column>
      </el-table>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleClose"
          size="small"
        >确 定</el-button>
        <el-button
          @click="handleClose"
          size="small"
        >取 消</el-button>
        <el-button @click="handleTest">测试按钮</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { shopDecorateList } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
export default {
  name: 'goodsPageTemplate',
  props: {
    visible: {
      type: Boolean
    }
  },
  data () {
    return {
      tableData: [],
      options: [],
      value: '',
      formData: {
        name: '',
        sort: ''
      }
    }
  },
  methods: {
    handleSearch () {

    },
    handleClose () {
      this.$emit('close')
    },
    handleTest () {
      // 列表
      shopDecorateList({
        'pageName': null,
        'catId': null
      }).then(res => console.log(res)).catch(err => console.log(err))
    }
  }
}
</script>
<style lang="css">
</style>
