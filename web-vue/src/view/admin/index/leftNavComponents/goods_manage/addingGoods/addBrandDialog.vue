<template>
  <div class="addBrandDialog">
    <el-dialog
      title="添加品牌"
      :visible.sync="dialogVisible"
      width="60%"
    >
      <el-form :model="formData">
        <el-form-item
          label="品牌名称："
          :label-width="formLabelWidth"
        >
          <el-input
            size="small"
            v-model="formData.brandName"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="品牌分类："
          :label-width="formLabelWidth"
        >
          <el-select
            size="small"
            v-model="formData.classifyId"
            placeholder="请选择品牌分类"
          >

          </el-select>
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          type="primary"
          @click="handleSave"
        >确 定</el-button>
        <el-button
          @click="SHOW_DIALOG"
          size="small"
        > 取 消</el-button>

      </div>
    </el-dialog>

  </div>
</template>
<script>
import { mapGetters, mapMutations } from 'vuex'
export default {
  name: 'addBrandDialog',
  created () {
    // console.log(this.dialogVisible)
  },
  computed: {
    ...mapGetters(['dialogVisible'])
  },

  data () {
    return {
      formData: {
        brandName: '',
        classifyId: ''

      },
      formLabelWidth: '120px'
    }
  },

  methods: {
    ...mapMutations(['SHOW_DIALOG']),
    handleSave () {
      this.SHOW_DIALOG()
    },
    handleCancel () {

    }
  }
}
</script>
<style scoped>
</style>
