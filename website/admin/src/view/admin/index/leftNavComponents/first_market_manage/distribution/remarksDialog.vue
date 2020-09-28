<template>
  <el-dialog
    title="会员备注信息"
    :visible.sync="remarksDialog"
    :close-on-click-modal="false"
    width="50%"
    center
  >
    <el-form>
      <el-form-item label="备注信息：">
        <el-input
          v-model="remarksText"
          type="textarea"
          placeholder="请输入备注内容"
          :autosize="{ minRows: 5 }"
          maxlength="200"
          :show-word-limit="remarksText ? true : false"
          style="width: 60%;"
        >
        </el-input>
        <el-button
          type="primary"
          size="small"
          @click="addRemarksHandler"
        >添加</el-button>
      </el-form-item>
      <el-form-item>
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="remarksList"
          border
          style="width: 100%"
        >
          <template slot="empty">
            <tableEmpty />
          </template>
          <el-table-column
            label="序号"
            align="center"
          >
            <template slot-scope="scope">{{ scope.$index + 1 }}</template>
          </el-table-column>
          <el-table-column
            prop="addTime"
            label="添加时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="remark"
            label="内容"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <span
                style="font-size: 22px;color: #5a8bff;"
                class="el-icon-delete"
                @click="delRemarksHandler(scope.row.id)"
              ></span>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>

    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="closeremarks"
        size="small"
      >取 消</el-button>
      <el-button
        type="primary"
        size="small"
        @click="closeremarks"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { getRemarksList, addRemarks, delRemarks } from '@/api/admin/marketManage/distribution.js'
export default {
  props: {
    // 调起弹窗
    tuneUp: {
      type: Boolean,
      default: () => false
    },
    // 用户id
    userId: {
      type: Number,
      default: () => null
    }
  },
  data () {
    return {
      remarksDialog: false, // 会员备注弹窗
      remarksUserId: null,
      remarksText: '',
      remarksList: []
    }
  },
  watch: {
    tuneUp (newData) {
      this.remarksDialog = true
      this.remarksUserId = this.userId
      this.getRemarksList()
    }
  },
  mounted () {
  },
  methods: {
    // 获取备注列表
    getRemarksList () {
      getRemarksList({ userId: this.remarksUserId }).then(res => {
        if (res.error === 0) {
          this.remarksList = res.content
          this.remarksText = ''
        }
      })
    },

    // 添加备注信息
    addRemarksHandler () {
      if (this.remarksText === '') {
        this.$message.warning('请填写备注信息')
        return false
      }
      addRemarks({
        userId: this.remarksUserId,
        remark: this.remarksText
      }).then(res => {
        if (res.error === 0) {
          this.getRemarksList()
        }
      })
    },

    // 删除备注信息
    delRemarksHandler (id) {
      delRemarks(id).then(res => {
        if (res.error === 0) {
          this.$message.success('删除成功')
          this.getRemarksList()
        }
      })
    },

    // 关闭会员备注弹窗
    closeremarks () {
      this.remarksText = ''
      this.remarksDialog = false
    }
  }
}
</script>
<style lang="scss" scoped>
/deep/ .el-textarea .el-input__count {
  line-height: 12px;
}
/deep/ .el-textarea__inner {
  padding-bottom: 20px;
}
</style>
