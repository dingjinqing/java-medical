<template>
  <el-dialog
    title="标签"
    :visible.sync="tagsDialog"
    :close-on-click-modal="false"
    width="30%"
    center
  >
    <div>
      <el-form
        label-width="85px"
        label-position="right"
      >
        <el-form-item
          label="已选标签："
          v-if="tagsList1 && tagsList1.length > 0"
        >
          <div
            v-for="item in tagsList1"
            :key="item.id"
            class="tagsContent"
          >
            {{item.value}}
            <i
              class="el-icon-close"
              @click="deleteTags(item.id)"
              style="color: #999; margin-left: 3px;cursorL pointer;"
            ></i>
          </div>
        </el-form-item>
        <el-form-item label="选择标签：">
          <el-select
            v-model="tagsValue"
            size="small"
            placeholder="请选择"
            style="width: 170px;"
            @visible-change="handleTagsChange"
          >
            <el-option
              v-for="item in tagsList2"
              :key="item.id"
              :label="item.value"
              :value="item.id"
            >
            </el-option>
          </el-select>

          <span
            class="tagsStyle"
            @click="refreshTagsHandler()"
          >刷新</span>
          <span
            class="tagsStyle"
            @click="addTagsHandler()"
          >添加</span>
        </el-form-item>
      </el-form>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="closeTagsDialog"
        size="small"
      >取 消</el-button>
      <el-button
        type="primary"
        size="small"
        @click="sureTagsDialog"
      >确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { allTagRequest } from '@/api/admin/membershipList.js'
export default {
  props: {
    // 调起弹窗
    tuneUp: {
      type: Boolean,
      default: () => false
    },
    // 已选择值
    selectTags: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      tagsDialog: false, // 会员备注弹窗
      tagsList: [], // 标签数据
      tagsList1: [], // 已选
      tagsList2: [], // 未选
      tagsValue: null // 选中值
    }
  },
  watch: {
    tuneUp (newData) {
      this.tagsDialog = !this.tagsDialog
      this.getAllData()
    }
  },
  mounted () {
    this.getAllData()
  },
  methods: {
    // 获取标签列表
    async getAllData () {
      await allTagRequest().then(res => {
        if (res.error === 0) {
          this.tagsList = res.content
        }
      })

      this.selectTagsChange(this.selectTags)
    },

    // 切换标签
    selectTagsChange (value) {
      this.tagsList1 = this.tagsList.filter(item => {
        return value.includes(item.id) === true
      })
      this.tagsList2 = this.tagsList.filter(item => {
        return value.includes(item.id) === false
      })
    },

    // 添加标签
    addTagsHandler () {
      let routeData = this.$router.resolve({ name: 'user_tag' })
      window.open(routeData.href, '_blank')
    },

    // 刷新标签
    refreshTagsHandler () {
      allTagRequest().then(res => {
        if (res.error === 0) {
          this.tagsList = res.content
          this.$message.success('刷新成功!')
        }
      })
    },

    // 选中数据
    handleTagsChange (val) {
      if (val === false) {
        this.selectTags.push(this.tagsValue)
        this.selectTagsChange(this.selectTags)
        this.tagsValue = null
      }
    },

    // 删除标签
    deleteTags (id) {
      var index = this.selectTags.findIndex(item => { return item === id })
      this.selectTags.splice(index, 1)
      this.selectTagsChange(this.selectTags)
    },

    // 确定弹窗
    sureTagsDialog () {
      this.tagsDialog = false
      var data = []
      this.tagsList1.forEach(item => {
        data.push(item.id)
      })
      this.$emit('resultTags', data)
    },

    // 取消弹窗
    closeTagsDialog () {
      this.tagsDialog = false
      this.$emit('cancelTags')
    }
  }
}
</script>
<style lang="scss" scoped>
.tagsStyle {
  color: #5a8bff;
  cursor: pointer;
  margin-left: 10px;
}
.tagsContent {
  display: inline-block;
  height: 30px;
  background: #ebf1ff;
  border: 1px solid #b4caff;
  border-radius: 2px;
  text-align: center;
  line-height: 30px;
  padding: 0 10px;
  margin-right: 10px;
  color: #666;
}
</style>
