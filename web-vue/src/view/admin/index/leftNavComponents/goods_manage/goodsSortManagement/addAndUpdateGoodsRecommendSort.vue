<template>
  <div class="addGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="isUpdate? 5 : 3"/>
    <div class="content">
      <div class="formItem">
        <div class="formItemKey">
          <span class="mustInput">*</span>分类名称：
        </div>
        <div class="formItemVal">
          <el-input v-model="recommendSort.sortName" size="small"/>
        </div>
      </div>
      <div class="formItem">
        <div class="formItemKey">
          分类优先级：
        </div>
        <div class="formItemVal">
          <el-input v-model="recommendSort.first" size="small"/>
        </div>
      </div>
      <div class="formItem">
        <div class="formItemKey">
        </div>
        <div class="formItemVal">
          <span class="inputTip">仅可填写1到100间的整数数字越大前端排列顺序越靠前</span>
        </div>
      </div>
      <div class="formItem">
        <div class="formItemKey" style="align-self: flex-start;">
          <span class="mustInput">*</span>子类
        </div>
        <div class="formItemVal">
         <div class="sortChildWrap">
           <div class="sortChild" v-for="(item,index) in recommendSortChildren" :key="index">
             <span class="deleteIcon" @click="deleteSortChild(index)">×</span>
             <div class="formItem">
               <div class="formItemKey">
                 <span class="mustInput">*</span>
                 子类名称：
               </div>
               <div class="formItemVal">
                 <el-input v-model="item.sortName" size="small"/>
               </div>
             </div>
             <div class="formItem">
               <div class="formItemKey" style="align-self: flex-start;">
                 <span class="mustInput">*</span>
                 图片：
               </div>
               <div class="formItemVal">
                 <div style="display: flex;justify-content: left;align-items: center;line-height: 20px;">
                   <span style="align-self: flex-start;color: #5a8bff;cursor: pointer;" @click="chooseSortImg(item)">修改</span>
                   <div style="position: relative;margin: 0px 10px;height: 70px;cursor: pointer;border: 1px solid #ccc;" @click="chooseSortImg(item)">
                     <img v-if="item.sortImgObj.imgUrl===undefined"  :src="$imageHost+'/image/admin/sort_moren.png'" style="width: 70px;height: 70px;">
                     <template v-else>
                       <img :src="item.sortImgObj.imgUrl" style="width: 70px;height: 70px;">
                       <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);">更换图标</div>
                     </template>
                   </div>
                   <span style="align-self: flex-end;color:#666;">150*140</span>
                 </div>
               </div>
             </div>
             <div class="formItem">
               <div class="formItemKey">
                 添加链接：
               </div>
               <div class="formItemVal">
                 <el-input v-model="item.imgLink" size="small" style="width: 250px;"/>
                 <el-button @click="chooseImgLink(item)" size="small" style="margin-left: 5px;color: #666666;">添加链接</el-button>
               </div>
             </div>
           </div>
           <el-button @click="addSortChild" size="small" type="primary">添加</el-button>
         </div>
        </div>
      </div>
    </div>
    <div class="contentFooter">
      <el-button @click="save" type="primary">保存</el-button>
    </div>
    <!--图片dialog-->
    <ImageDialog :tuneUp="imgDialogShow" pageIndex='pictureSpace' @handleSelectImg='imgDialogSelectedCallback'/>
    <!--链接dialog-->
    <LinkDialog :tuneUpSelectLink="linkDialogShow" @selectLinkPath="imgLinkDialogSelectedCallback"/>
  </div>
</template>

<script>
// 导入api
import {addRecommendGoodsSort, updateRecommendGoodsSort, getGoodsRecommendSort} from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'

// 组件导入
import allGoodsSortHeaderTab from './allGoodsSortHeaderTab'
import ImageDialog from '@/components/admin/imageDalog'
import LinkDialog from '@/components/admin/selectLinks'

export default {
  name: 'addAndUpdateGoodsRecommendSort',
  components: {
    allGoodsSortHeaderTab,
    ImageDialog,
    LinkDialog
  },
  data () {
    return {
      isUpdate: false,
      recommendSort: {
        sortId: null,
        sortName: null,
        first: 0
      },
      recommendSortChildren: [
        {
          sortId: null,
          sortName: null,
          sortImgObj: {},
          imgLink: null
        }
      ],
      currentSortChild: null,
      imgDialogShow: false,
      linkDialogShow: false
    }
  },
  methods: {
    /* 添加推荐分类子类 */
    addSortChild () {
      this.recommendSortChildren.push({
        sortId: null,
        sortName: null,
        sortImgObj: {},
        imgLink: null
      })
    },
    /* 删除推荐分类子类 */
    deleteSortChild (index) {
      this.recommendSortChildren.splice(index, 1)
    },
    /* 选择图标 */
    chooseSortImg (item) {
      this.imgDialogShow = !this.imgDialogShow
      this.currentSortChild = item
    },
    /* 选择图标回调 */
    imgDialogSelectedCallback (imgObj) {
      this.currentSortChild.sortImgObj = {imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath}
    },
    /* 选择链接 */
    chooseImgLink (item) {
      this.linkDialogShow = !this.linkDialogShow
      this.currentSortChild = item
    },
    /* 选择链接回调 */
    imgLinkDialogSelectedCallback (linkObj) {
      this.currentSortChild.imgLink = linkObj
    },
    _initForUpdate (sortId) {
      getGoodsRecommendSort(sortId).then(res => {
        if (res === null) {
          return
        }
        let parentSort = res.content
        this.recommendSort = {
          sortId: parentSort.sortId,
          sortName: parentSort.sortName,
          first: parentSort.first
        }
        this.recommendSortChildren = []
        parentSort.children.forEach(sort => {
          this.recommendSortChildren.push({
            sortId: sort.sortId,
            sortName: sort.sortName,
            sortImgObj: {
              imgUrl: sort.sortImgUrl,
              imgPath: sort.sortImg
            },
            imgLink: sort.imgLink
          })
        })
      })
    },
    /* 获取表单数据 */
    _getFormData () {
      let recommendSort = {
        sortId: this.recommendSort.sortId,
        sortName: this.recommendSort.sortName,
        first: this.recommendSort.first,
        type: 1,
        parentId: 0,
        level: 0,
        children: []
      }

      this.recommendSortChildren.forEach(item => {
        recommendSort.children.push({
          sortId: item.sortId,
          sortName: item.sortName,
          imgLink: item.imgLink,
          sortImgUrl: item.sortImgObj.imgPath,
          type: 1,
          level: 1
        })
      })

      return recommendSort
    },
    save () {
      let params = this._getFormData()
      let execFunc = this.isUpdate ? updateRecommendGoodsSort : addRecommendGoodsSort
      execFunc(params).then(res => {
        if (res.error !== 0) {
          this.$message.error({message: res.message})
          return
        }
        this.$router.push({name: 'allGoodsRecommendSort'})
      })
    }
  },
  mounted () {
    if (this.$route.params.sortId !== undefined) {
      this.isUpdate = true
      this._initForUpdate(this.$route.params.sortId)
    }
  }
}
</script>

<style scoped>
  .content{
    margin: 20px 0px;
    padding-left: 30px;
  }
  .formItem{
    display: flex;
    align-items: center;
    margin-top: 20px;
  }
  .formItemKey{
    width: 120px;
    text-align: center;
    color: #666666;
  }
  .mustInput{
    color:red;
  }
  .inputTip {
    color: #999;
    display: block;
    font-size: 14px;
  }
  .sortChildWrap{
    background-color: #F8F8F8;
    padding: 10px;
  }
  .sortChild{
    background-color: #fff;
    width: 500px;
    padding: 10px;
    margin: 10px 0px;
    position: relative;
  }
  .deleteIcon {
    width: 17px;
    height: 17px;
    color: #fff;
    background: #ccc;
    border: 1px solid #ccc;
    border-radius: 50%;
    line-height: 17px;
    text-align: center;
    position: absolute;
    top: -8px;
    right: -8px;
    cursor: pointer;
    opacity: 0.8;
  }
  .contentFooter{
    background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 60px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
  }
</style>
