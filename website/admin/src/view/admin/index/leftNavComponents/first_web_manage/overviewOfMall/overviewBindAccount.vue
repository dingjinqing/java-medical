<template>
  <div>
    <div class="one_piece">
      <el-row>
        <div v-if="act==='del_bind'">
          <span>{{this.nickName!==null ? '微信昵称:'+this.nickName : $t('overview.bindButtonDelMsg')}}{{$t('overview.bindButtonDelBindMessage')}}</span>
          <el-button
            class="btn_follow"
            @click="handleSubmit()"
          >{{$t('overview.bindButtonDelBind')}}</el-button>
        </div>
        <div v-else-if="act==='bind'">
          <span>{{$t('overview.bindButtonBindMessage')}}</span>
          <el-button
            class="btn_follow"
            @click="handleSubmit()"
          >{{$t('overview.bindButtonBind')}}</el-button>
        </div>
        <div v-else>
          <span>{{$t('overview.bindButtonNullMessage')}}</span>
          <el-button
            class="btn_follow"
            @click="handleSubmit()"
          >{{$t('overview.bindButtonNull')}}</el-button>
        </div>
      </el-row>
    </div>
    <el-dialog
      :title="$t('overview.imageTitle')"
      :visible.sync="centerDialogVisible"
      width="30%"
    >
      <span>{{$t('overview.imageMessage')}}</span>

      <div class="off-area">

        <img
          :src="this.imgsrc"
          style="width:100px;padding-top: 23px;"
        >

      </div>

    </el-dialog>
  </div>
</template>
<script>
import { getbindUnBindStatus, getOfficialQrCode, checkGoodThingRequest } from '@/api/admin/firstWebManage/shopView/overview.js'
export default {
  name: 'overview',
  data () {
    return {
      nickName: null,
      isBind: 0,
      officialOpenId: null,
      act: null,
      imgsrc: null,
      centerDialogVisible: false
    }
  },
  watch: {
    centerDialogVisible (newData) {
      console.log('centerDialogVisible的值' + newData)
      if (newData === false) {
        console.log('刷新' + newData)
        this.defaluteData()
      }
    }
  },
  mounted () {
    // 初始化数据
    this.defaluteData()
    // 初始化语言
    this.langDefault()
  },
  methods: {
    defaluteData () {
      getbindUnBindStatus().then((res) => {
        if (res.error === 0) {
          this.isBind = res.content.isBind
          this.nickName = res.content.nickName
          this.officialOpenId = res.content.officialOpenId
          if (this.isBind !== 0) {
            this.act = 'del_bind'
          } else if (this.officialOpenId !== null) {
            this.act = 'bind'
          } else {
            this.act = null
          }
          console.log(res)
        }
        console.log(res)
      })
    },
    handleSubmit () {
      this.defaluteData()
      if (this.act === null) {
        getOfficialQrCode().then((res) => {
          console.log(res)
          if (res.error === 0) {
            this.imgsrc = res.content
            this.centerDialogVisible = true
          } else {
            this.$message.error(res.message)
          }
        })
      } else {
        let data2 = {
          act: this.act
        }
        checkGoodThingRequest(data2).then((res) => {
          console.log(res)
          if (res.error === 0) {
          } else {
            this.$message.error(res.message)
          }
          this.defaluteData()
        })
      }
    }
  }
}
</script>
<style scoped lang="scss">
.one_piece {
  margin: 0;
  padding: 0;
  float: right;
  font-size: 14px;
  color: #333;
  background-color: #fff;
  font-weight: normal;
}
/deep/ .btn_follow {
  border: 1px solid #5a8bff;
  color: #5a8bff;
  padding: 5px 10px;
  border-radius: 2px;
  margin-left: 10px;
}
.off-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #666;
}
</style>
