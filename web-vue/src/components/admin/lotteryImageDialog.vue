<template>
    <div>
        <el-dialog
                :title="$t('imgageDalog.title')"
                :visible.sync="dialogTableVisible"
                width="635px"
                :append-to-body='true'
        >
            <div class="selectImgList">
                <ul>
                    <li class="radio_li"
                        v-for="(item,index) in img_list"
                        :key="index">
                        <el-radio v-model="radio" :label="index">
                            <img class="raido_image" :src="item.url" @click="handleChecked(index)">

                        </el-radio>
                    </li>
                    <div style="clear:both;"></div>
                </ul>
            </div>
            <span
                    slot="footer"
                    class="dialog-footer"
            >
                <el-button @click="dialogTableVisible = false">{{$t('imgageDalog.cancel')}}</el-button>
                <el-button
                        type="primary"
                        @click="handleToSure()"
                >{{$t('imgageDalog.Determine')}}</el-button>
            </span>
        </el-dialog>

    </div>
</template>
<script>

export default {
  props: {
    pageIndex: String, // 数据库选择
    tuneUp: { // 调起弹窗
      type: Boolean,
      default: () => false
    }
  },
  data () {
    return {
      dialogTableVisible: false,
      radio: 1,
      checked: {},
      img_list: [
        {url: this.$imageHost + '/image/admin/icon_lottery/1.png', imgPath: '/image/admin/icon_lottery/1.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/2.png', imgPath: '/image/admin/icon_lottery/2.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/3.png', imgPath: '/image/admin/icon_lottery/3.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/4.png', imgPath: '/image/admin/icon_lottery/4.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/5.png', imgPath: '/image/admin/icon_lottery/5.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/6.png', imgPath: '/image/admin/icon_lottery/6.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/7.png', imgPath: '/image/admin/icon_lottery/7.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/8.png', imgPath: '/image/admin/icon_lottery/8.png'},
        {url: this.$imageHost + '/image/admin/icon_lottery/thank.png', imgPath: '/image/admin/icon_lottery/thank.png'}
      ]
    }
  },
  watch: {
    tuneUp (newData) {
      console.log(newData)
      this.dialogTableVisible = true
    }
  },
  methods: {
    handleToSure () {
      this.$emit('handleSelectImg', this.checked)
      this.dialogTableVisible = false
    },
    handleChecked (index) {
      console.log(index)
      this.checked = this.img_list[index]
      this.$emit('handleSelectImg', this.img_list[index])
    }
  }
}
</script>
<style scoped>
.radio_li{
    float: left;
    margin-right: 35px;
    width: 50px;
    height: 75px;
    text-align: center;
    margin-top: 15px;
}
.selectImgList {
    width: 100%;
    margin-top: 3px;
    padding: 3px 0;
    min-height: 60px;
}
    .raido_image{
        width: 50px;
        height: 50px;
    }
.el-radio__input{
    position: absolute;
    margin-top: 70px;
    margin-left: 30px;
}
</style>
<style>

</style>
