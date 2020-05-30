<template>
  <el-dialog
    title="设置分销员分组"
    :visible.sync="groupDialog"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    选择分组：
    <el-select
      v-model="groupValue"
      placeholder="请选择分组"
      size="small"
      class="inputWidth"
    >
      <el-option
        v-for="group in groupNameList"
        :key="group.id"
        :label="group.groupName"
        :value="group.id"
      >
      </el-option>
    </el-select>

    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="cancelGroup"
        size="small"
      >取 消</el-button>
      <el-button
        type="primary"
        size="small"
        @click="sureGroup"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { distributorAllGroup } from '@/api/admin/marketManage/distribution.js'
export default {
  props: {
    // 调起弹窗
    tuneUp: {
      type: Boolean,
      default: () => false
    },
    // 回显数据
    groupBack: {
      type: Number,
      default: () => 0
    }
  },
  data () {
    return {
      groupNameList: [], // 分销员列表
      groupDialog: false, // 分销员分组
      groupValue: null
    }
  },
  watch: {
    tuneUp (newData) {
      this.groupDialog = true
      this.groupValue = this.groupBack ? this.groupBack : null
    }
  },
  mounted () {
    this.groupList()
  },
  methods: {
    // 获取所有分销员分组
    groupList () {
      distributorAllGroup().then(res => {
        this.groupNameList = res.content
      })
    },

    // 确定分销员分组
    sureGroup () {
      this.$emit('resultGroupRow', this.groupValue)
      this.groupDialog = false
      // 复原
      this.allChecked = false
      this.checkedValue = '0'
      this.groupValue = ''
    },

    // 取消分销员分组
    cancelGroup () {
      this.groupDialog = false
      // 复原
      this.allChecked = false
      this.checkedValue = '0'
      this.groupValue = ''
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
