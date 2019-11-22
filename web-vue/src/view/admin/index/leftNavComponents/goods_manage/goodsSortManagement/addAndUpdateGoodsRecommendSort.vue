<template>
  <div class="addGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="isUpdate? 5 : 3"/>
    <div class="content">
      <div class="formItem">
        <div class="formItemKey">
          <span class="mustInput">*</span>{{$t('goodsRecommendSorts.goodsSortName')}}：
        </div>
        <div class="formItemVal">
          <el-input v-model="recommendSort.sortName" size="small"/>
        </div>
      </div>
      <div class="formItem">
        <div class="formItemKey">
          {{$t('goodsRecommendSorts.goodsSortFirst')}}：
        </div>
        <div class="formItemVal">
          <el-input v-model.number="recommendSort.firstBind"  @change="firstChanged" size="small"/>
        </div>
      </div>
      <div class="formItem">
        <div class="formItemKey">
        </div>
        <div class="formItemVal">
          <span class="inputTip">{{$t('goodsRecommendSorts.goodsSortFirstTip')}}：</span>
        </div>
      </div>
      <div class="formItem">
        <div class="formItemKey" style="align-self: flex-start;">
          <span class="mustInput">*</span>{{$t('goodsRecommendSorts.child')}}
        </div>
        <div class="formItemVal">
         <div class="sortChildWrap">
           <div class="sortChild" v-for="(item,index) in recommendSortChildren" :key="index">
             <span class="deleteIcon" @click="deleteSortChild(index)">×</span>
             <div class="formItem">
               <div class="formItemKey">
                 <span class="mustInput">*</span>
                 {{$t('goodsRecommendSorts.childName')}}：
               </div>
               <div class="formItemVal">
                 <el-input v-model="item.sortName" size="small"/>
               </div>
             </div>
             <div class="formItem">
               <div class="formItemKey" style="align-self: flex-start;">
                 <span class="mustInput">*</span>
                 {{$t('goodsRecommendSorts.goodsSortImg')}}：
               </div>
               <div class="formItemVal">
                 <div style="display: flex;justify-content: left;align-items: center;line-height: 20px;">
                   <span style="align-self: flex-start;color: #5a8bff;cursor: pointer;" @click="chooseSortImg(item)">{{$t('goodsRecommendSorts.update')}}</span>
                   <div style="position: relative;margin: 0px 10px;height: 70px;cursor: pointer;" @click="chooseSortImg(item)">
                     <img v-if="item.sortImgObj.imgUrl===undefined"  :src="$imageHost+'/image/admin/sort_moren.png'" style="width: 70px;height: 70px;border: 1px solid #ccc;">
                     <img v-else :src="item.sortImgObj.imgUrl" style="width: 70px;height: 70px;">
                     <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);">
                       {{$t('goodsRecommendSorts.changeIcon')}}
                     </div>
                   </div>
                   <span style="align-self: flex-end;color:#666;">150*140</span>
                 </div>
               </div>
             </div>
             <div class="formItem">
               <div class="formItemKey">
                 {{$t('goodsRecommendSorts.goodsSortImgLink')}}：
               </div>
               <div class="formItemVal">
                 <el-input v-model="item.imgLink" :readonly="true" size="small" style="width: 250px;"/>
                 <el-button @click="chooseImgLink(item)" size="small" style="margin-left: 5px;color: #666666;">
                   {{$t('goodsRecommendSorts.addHeadImgLink')}}
                 </el-button>
               </div>
             </div>
           </div>
           <el-button @click="addSortChild" size="small" type="primary">{{$t('goodsRecommendSorts.add')}}</el-button>
         </div>
        </div>
      </div>
    </div>
    <div class="contentFooter">
      <el-button @click="save" type="primary">{{$t('goodsRecommendSorts.save')}}</el-button>
    </div>
    <!--图片dialog-->
    <ImageDialog :tuneUp="imgDialogShow" pageIndex='pictureSpace' :imageSize="[150,140]" @handleSelectImg='imgDialogSelectedCallback'/>
    <!--链接dialog-->
    <LinkDialog :tuneUpSelectLink="linkDialogShow" @selectLinkPath="imgLinkDialogSelectedCallback"/>
  </div>
</template>

<script>
// 导入api
import {addRecommendGoodsSort, updateRecommendGoodsSort, getGoodsRecommendSort} from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'
// 导入工具
import {isStrBlank, isNumberBlank} from '@/util/goodsUtil'
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
        first: null,
        firstBind: null
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
    firstChanged () {
      // 用户输入为空
      if (isNumberBlank(this.recommendSort.firstBind)) {
        this.recommendSort.first = null
      } else if (typeof this.recommendSort.firstBind !== 'number') {
        // 用户输入的不是数字，则还原数据
        this.recommendSort.firstBind = this.recommendSort.first
      } else if (this.recommendSort.firstBind < 0 || this.recommendSort.firstBind > 100) {
        this.recommendSort.firstBind = this.recommendSort.first
      } else {
        this.recommendSort.first = this.recommendSort.firstBind
      }
    },
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
          first: parentSort.first,
          firstBind: parentSort.first
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
    /* 验证表单数据正确性 */
    _validateFormData () {
      if (isStrBlank(this.recommendSort.sortName)) {
        this.$message.warning({message: this.$t('goodsRecommendSorts.sortNameNotNull')})
        return false
      }
      if (typeof this.recommendSort.first !== 'number') {
        this.$message.warning({message: this.$t('goodsRecommendSorts.pleaseInputRightPriority')})
        return false
      }
      for (let i = 0; i < this.recommendSortChildren.length; i++) {
        let sort = this.recommendSortChildren[i]
        if (isStrBlank(sort.sortName)) {
          this.$message.warning({message: this.$t('goodsRecommendSorts.sortNameNotNull')})
          return false
        }
        if (sort.sortImgObj.imgPath === undefined) {
          this.$message.warning({message: this.$t('goodsRecommendSorts.pleaseInputSortImg')})
          return false
        }
      }
      return true
    },
    /* 获取表单数据 */
    _getFormData () {
      let recommendSort = {
        sortId: this.recommendSort.sortId,
        sortName: this.recommendSort.sortName,
        first: this.recommendSort.first,
        children: []
      }

      this.recommendSortChildren.forEach(item => {
        let updateItem = {
          sortId: item.sortId,
          sortName: item.sortName,
          imgLink: item.imgLink,
          sortImg: item.sortImgObj.imgPath
        }
        if (this.isUpdate) {
          updateItem.parentId = recommendSort.sortId
        }
        recommendSort.children.push(updateItem)
      })

      return recommendSort
    },
    save () {
      if (!this._validateFormData()) {
        return
      }

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
