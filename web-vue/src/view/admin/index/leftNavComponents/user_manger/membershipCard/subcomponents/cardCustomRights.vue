<template>
    <div class="rightsContainer">
        <el-form label-width="200px">
            <el-form-item>
                <el-checkbox >自定义权益</el-checkbox>
            </el-form-item>
            <el-form-item>
                <el-button size="small" @click="callRightDialog(-1)">自定义权益</el-button>
                <span class="rightTip">最多可添加10个自定义权益</span>
                <div class="content-container" v-if="showContentOn">
                    <div class="content" v-for="(item,index) in myRights"
                        :key="index">
                        <span class="content-item">{{item.crightName}}</span>
                        <i class="el-icon-edit-outline icon-style" @click="callRightDialog(index)"></i>
                        <i class="el-icon-delete icon-style" @click="deleteRight(index)"></i>
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
  computed: {
    rightObj () {
      return this.myRights[this.currentIndex]
    },
    showContentOn () {
      return this.myRights.length !== 0
    }
  },
  data () {
    return {
      showDialog: false,
      myRights: [{
        crightName: 'hhh1',
        crightImage: null,
        crightContent: 'Good Night'
      },
      {
        crightName: 'hhh2',
        crightImage: null,
        crightContent: 'Good Night'
      }],
      // 当前的选项
      currentIndex: -1
    }
  },
  methods: {
    callRightDialog (val) {
      this.showDialog = !this.showDialog
      this.currentIndex = val
    },
    setNewRight (res) {
      console.log('receive', res)
      this.myRights.push(res)
    },
    deleteRight (index) {
      this.myRights.splice(index, 1)
    }
  }
}
</script>
<style lang="scss" scoped>
.rightsContainer{
    background: white;
    height: 500px;
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
        .content{
            padding: 10px 0 0 20px;
            .content-item{
                display: inline-block;
                width: 180px;
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
