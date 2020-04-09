
<template>
<!-- 用户自定激活组件 -->
  <div>
      <span @click="showDialog=true">{{msg}}</span>

     <my-dialog :visiable.sync="showDialog"/>
  </div>
</template>

<script>
import cardSelfDefDialog from './dialog/CardSelfDefDialog'
export default {
  components: {
    myDialog: cardSelfDefDialog
  },
  data () {
    return {
      msg: '自定义激活项',
      showDialog: false,
      myData: []
    }
  }
}
</script>

<style>

</style>
