<template>
    <div>
        <el-form label-width="180px" label-position="right">
            <el-form-item label="同步打标签:">
                <el-checkbox  label="1">给领卡用户打标签 </el-checkbox>
                <span class="choose-label" @click="visiable=true">选择标签</span>
            </el-form-item>
            <el-form-item label="会员卡转赠:">
                <el-switch
                 v-model="val"
                 active-color="#f7931e"
                 inactive-color="#C0CCDA">
                </el-switch>
                <span>{{switchInfo}}</span>
                <span class="tip">开启后，用户可以将会员卡转赠给好友，好友领取后用户自己的会员卡将失效</span>
                <div>
                    <el-checkbox label="1">勾选后，用户转赠给好友，好友还可以继续转赠给其他人</el-checkbox>
                    <div>
                        最大转赠次数
                        <span class="max-give">
                            <el-input size="small"></el-input>
                        </span>
                        <span class="tip">填0表示不限制</span>
                    </div>
                </div>
            </el-form-item>
        </el-form>
        <user-tag :visiable.sync="visiable"/>
    </div>
</template>

<script>
export default {
  components: {
    userTag: () => import('./dialog/CardUserTagSet')
  },
  computed: {
    switchInfo () {
      return this.val ? '已开启' : '已关闭'
    }
  },
  data () {
    return {
      val: true,
      visiable: false
    }
  }
}
</script>

<style scoped lang="scss">
.tip{
    color: #9d9d9d;
    padding-left: 0px;
}
.choose-label{
    color: #5A8BFF;
    cursor: pointer;
    margin-left: 10px;
}
.max-give{
    display: inline-block;
    width: 60px;
}
</style>
