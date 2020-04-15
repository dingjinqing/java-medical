<template>
    <div>
        <el-form label-width="180px" label-position="right">
            <el-form-item label="同步打标签:">
                <el-checkbox v-model="cardTag.cardTag" :true-label="on" :false-label="off">给领卡用户打标签 </el-checkbox>
                <span class="choose-label" @click="visiable=true">选择标签</span>
                <div v-if="showUserTagDetail">
                    <div class="tip">最多可设置3个标签</div>
                    <div v-for="item in cardTag.cardTagId" :key="item.id" class="user-tag">
                        <span>{{item.value}}</span><img :src="imgUrl" class="close-tag" @click="deleteUserTag(item)" />
                    </div>
                </div>
            </el-form-item>
            <el-form-item label="会员卡转赠:" v-if="isLimitCard">
                <el-switch
                 v-model="cardGive.cardGiveAway"
                 active-color="#f7931e"
                 inactive-color="#C0CCDA"
                 :active-value="on"
                 :inactive-value="off">
                </el-switch>
                <span>{{switchInfo}}</span>
                <span class="tip">开启后，用户可以将会员卡转赠给好友，好友领取后用户自己的会员卡将失效</span>
                <div>
                    <el-checkbox v-model="cardGive.cardGiveContinue"  :true-label="on" :false-label="off">勾选后，用户转赠给好友，好友还可以继续转赠给其他人</el-checkbox>
                    <div>
                        最大转赠次数
                        <span class="max-give">
                            <el-input v-model.number="cardGive.mostGiveAway" size="small"></el-input>
                        </span>
                        <span class="tip">填0表示不限制</span>
                    </div>
                </div>
            </el-form-item>
        </el-form>
        <user-tag :visiable.sync="visiable"
            :tags="cardTag.cardTagId"
            @chooseUserTag="setUserTag"/>
    </div>
</template>

<script>
export default {
  props: {
    cardType: {
      type: Number,
      default: 0
    },
    cardTag: {
      type: Object,
      required: true,
      default: () => {
        return {
          cardTag: null,
          cardTagId: []
        }
      }
    },
    cardGive: {
      type: Object,
      default: () => {
        return {
          cardGiveAway: null,
          cardGiveContinue: null,
          mostGiveAway: null
        }
      }
    }
  },
  components: {
    userTag: () => import('./dialog/CardUserTagSet')
  },
  computed: {
    isLimitCard () {
      return this.cardType === 1
    },
    switchInfo () {
      return this.val === 'on' ? '已开启' : '已关闭'
    },
    showUserTagDetail () {
      return this.cardTag.cardTagId.length > 0
    }
  },
  data () {
    return {
      val: false,
      visiable: false,
      on: 'on',
      off: 'off',
      imgUrl: this.$imageHost + '/image/admin/cash_close.png'
    }
  },
  methods: {
    setUserTag (data) {
      this.cardTag.cardTagId = data
    },
    deleteUserTag (tag) {
      this.cardTag.cardTagId = this.cardTag.cardTagId.filter(item => item.id !== tag.id)
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
.user-tag{
    display: inline-block;
    padding: 0 10px;
    height: 30px;
    line-height: 30px;
    background: rgba(235,241,255,1);
    border: 1px solid rgba(180,202,255,1);
    align-items: center;
    margin-right: 10px;
}
.close-tag{
    margin-left: 10px;
    font-size: 15px;
    color:#7e7b7b;
    cursor: pointer;
}
</style>
