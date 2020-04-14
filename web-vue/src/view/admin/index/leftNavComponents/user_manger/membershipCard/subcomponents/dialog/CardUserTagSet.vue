<template>
    <el-dialog
        :visible.sync="dialogVisiable"
        title="标签"
        width="355px"
        >
        <div>
            <div class="clearfix">
                <span class="tip">请选择标签</span>
                <span class="opt">刷新/新建</span>
            </div>
            <el-select v-model="tagArr" size="mini" multiple placeholder="请选择" @change="checkTagLength">
                    <el-option
                     v-for="item in tagOptions"
                     :key="item.id"
                     :label="item.value"
                     :value="item.id"
                     :disabled="item.disabled">

                    </el-option>
            </el-select>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button size="small" @click="dialogVisiable = false">取 消</el-button>
            <el-button size="small" type="primary" @click="dialogVisiable = false">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { allTagRequest } from '@/api/admin/membershipList.js'
export default {
  props: {
    visiable: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    dialogVisiable: {
      get () {
        return this.visiable
      },
      set (val) {
        this.$emit('update:visiable', val)
      }
    }
  },
  mounted () {
    this.getUserTags()
  },
  data () {
    return {
      tagArr: [],
      tagOptions: []
    }
  },
  methods: {
    getUserTags () {
      allTagRequest().then(res => {
        if (res.error === 0) {
          this.tagOptions = res.content
        }
      })
    },
    checkTagLength () {
      if (this.tagArr.length >= 4) {
        this.tagArr.splice(this.tagArr.length - 1, 1)
        this.$message.warning('最多选择三个标签')
      } else {
        console.log(this.tagArr.length)
      }
    }
  }
}
</script>

<style scoped lang="scss">
/deep/ .el-select--mini{
    width: 100%;
}
.clearfix{
    margin-bottom: 10px;
}
.clearfix::after{
 content: "";
  clear: both;
  display: table;
}
.opt{
    float: right;
    color:#5A8BFF;
}
.tip{
 color: #9d9d9d;
}
</style>
