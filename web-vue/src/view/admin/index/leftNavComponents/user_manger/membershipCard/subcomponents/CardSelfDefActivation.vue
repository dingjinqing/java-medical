
<template>
<!-- 用户自定激活组件 -->
  <div>
      <el-button size="small" @click="showActionDialog(-1)">{{msg}}</el-button>
      <div class="container" v-if="myData.length>0">
          <div v-for="(item,index) in myData" :key="index">
              <el-checkbox v-model="item.checked"></el-checkbox>
              <span class="title">{{item.title}}</span>
              <el-tooltip content="编辑" placement="top" effect="light">
                <i class="el-icon-edit-outline icon-style" @click="showActionDialog(index)"></i>
              </el-tooltip>
              <el-tooltip content="删除" placement="top" effect="light">
                <i class="el-icon-delete icon-style" @click="deleteAction(index)"></i>
              </el-tooltip>
              <span v-if="item.conditionChecked" class="tip">必填</span>
          </div>
      </div>
     <my-dialog :visiable.sync="showDialog"
                :customAction.sync="myData[currentIndex]"
      @setNewCustomAction="setNewCustomAction"/>
  </div>
</template>

<script>
import cardSelfDefDialog from './dialog/CardSelfDefDialog'
export default {
  components: {
    myDialog: cardSelfDefDialog
  },
  props: {
    myData: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      msg: '自定义激活项',
      showDialog: false,
      currentIndex: -1
    }
  },
  methods: {
    setNewCustomAction (action) {
      this.myData.push({
        ...action,
        checked: false
      })
      console.log(this.myData)
    },
    showActionDialog (index) {
      this.currentIndex = index
      this.showDialog = true
    },
    deleteAction (index) {
      this.myData.splice(index, 1)
    }
  }
}
</script>

<style scoped lang="scss">
  .container{
    background-color: #fff;
    border: 1px dashed #ccc;
    width: 60%;
    padding-left: 30px;
    .title{
      padding-left: 10px;
      display: inline-block;
      width: 180px;
    }
    .icon-style{
      color: #5a8bff;
      cursor: pointer;
    }
    .tip{
      margin-left: 10px;
      color: #999;
      font-size: 12px;
    }
  }

</style>
