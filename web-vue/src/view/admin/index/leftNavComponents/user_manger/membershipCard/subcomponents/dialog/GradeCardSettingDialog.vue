<template>
    <div id="gradeStop">
        <el-dialog
        title="提示"
        :visible.sync="visiable"
        width="30%"
        >
            <div class="top">
                共0位会员拥有该卡
            </div>
            <div class="middle">
                <el-radio v-model="radio" :label="1">直接停用</el-radio>
                <el-radio v-model="radio" :label="2">停用后置换为其他等级卡</el-radio>
            </div>
            <div class="bottom">
                <p v-if="radio===1">
                    直接停用本卡，则已经领取的等级用户也会停用该等级卡，不再享受等级会员权限
                </p>
                <div v-else-if="radio === 2">
                    <p>
                        <span class="stop-tip">停用后拥有该卡用户默认设置为:</span>
                        <el-select size="small" style="width: 130px;"></el-select>
                    </p>
                    <p>
                        直接停用本卡，拥有本卡的用户批量更换为设置的新等级卡
                    </p>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="visiable = false">确 定</el-button>
                <el-button @click="visiable = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
  props: {
    dialogVisiable: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    visiable: {
      get () {
        return this.dialogVisiable
      },
      set (val) {
        return this.$emit('update:dialogVisiable', val)
      }
    }
  },
  data () {
    return {
      radio: 2
    }
  }
}
</script>

<style lang="scss" scoped>
#gradeStop{
    font-size: 12px;
    line-height: 24px;
}
.top{
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}
.middle{
    margin: 20px 0;
    /deep/ .el-radio:last-child{
        margin-left: 22px;
    }
}
.bottom{
    height: 70px;
    background-color: #F3F3F3;
    padding: 10px;
    span.stop-tip{
        margin-right: 5px;
    }
    display: flex;
    align-items: center;
    justify-content: left;

}
</style>
