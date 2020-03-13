<template>
    <div class="rightsContainer">
        <el-form label-width="200px">
            <el-form-item>
                <el-checkbox v-model="turnOn" >自定义权益</el-checkbox>
            </el-form-item>
            <el-form-item v-if="turnOn">
                <el-button size="small" @click="callRightDialog(-1)">自定义权益</el-button>
                <span class="rightTip">最多可添加10个自定义权益</span>
                <div class="content-container" v-if="showContentOn">
                    <div class="content" v-for="(item,index) in customRightsAll"
                        :key="index">
                        <span class="content-item">{{item.crightName}}</span>
                        <el-tooltip content="编辑" placement="top" effect="light">
                            <i class="el-icon-edit-outline icon-style" @click="callRightDialog(index)"></i>
                        </el-tooltip>
                        <el-tooltip content="删除" placement="top" effect="light">
                            <i class="el-icon-delete icon-style" @click="deleteRight(index)"></i>
                        </el-tooltip>
                    </div>
                </div>
                <div class="content-container no-content" v-else>
                    <span>你还没有定义权益</span>
                </div>
            </el-form-item>
        </el-form>
        <cardCustomRightsDialog
            :visible.sync="showDialog"
            v-bind.sync="rightObj"
            @createNewRight="setNewRight">

        </cardCustomRightsDialog>
    </div>
</template>
<script>
export default {
  components: {
    cardCustomRightsDialog: () => import('./cardCustomRightsDialog')
  },
  props: {
    customRightsFlag: {
      type: String,
      default: 'off'
    },
    customRightsAll: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  computed: {
    turnOn: {
      get () {
        return this.customRightsFlag === 'on'
      },
      set (val) {
        let result = val ? 'on' : 'off'
        this.$emit('update:customRightsFlag', result)
      }
    },
    rightObj () {
      return this.customRightsAll[this.currentIndex]
    },
    showContentOn () {
      return this.customRightsAll.length !== 0
    }
  },
  data () {
    return {
      showDialog: false,
      // 当前的选项
      currentIndex: -1
    }
  },
  methods: {
    callRightDialog (val) {
      if (this.customRightsAll.length >= 10) {
        this.$message.warning('已达上限')
        return
      }
      this.showDialog = !this.showDialog
      this.currentIndex = val
    },
    setNewRight (res) {
      console.log('receive', res)
      this.customRightsAll.push(res)
    },
    deleteRight (index) {
      this.customRightsAll.splice(index, 1)
    }
  }
}
</script>
<style lang="scss" scoped>
.rightsContainer{
    margin-bottom: 20px;
    /deep/ .el-form-item{
        margin-bottom: 2px;
    }
    .rightTip{
        color: #999;
        margin-left: 10px;
    }
    .content-container{
        background: #fff;
        border: 1px dashed #ccc;
        margin-right: 50%;
        margin-top: 10px;
        padding-bottom: 10px;
        .content{
            padding: 10px 0 0 20px;
            .content-item{
                display: inline-block;
                width: 230px;
            }
            .icon-style{
                font-size: 22px;
                color: #5a8bff;
                cursor: pointer;
            }
            i{
                margin-left: 10px;
            }
        }

    }
    .no-content{
        text-align: center;
        color: #999;
    }

}
</style>
