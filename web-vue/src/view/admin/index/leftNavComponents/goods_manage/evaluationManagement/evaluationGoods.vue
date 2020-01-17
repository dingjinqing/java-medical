<template>
  <div class="table_box">
    <el-table
      :data="dataList"
      style="width:100%;margin-bottom:10px;"
      border
      :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
      :cell-style="{
            'text-align':'center'
          }"
    >
      <el-table-column
        :label="$t('evaluation.goodsName')"
        width="200"
      >
        <template slot-scope="scope">
          <div class="goods_info">
            <img
              :src="$imageHost+'/'+scope.row.goodsImg"
              alt=""
            >
            <div class="right_info">
              <div class="goods_name">{{scope.row.goodsName}}</div>
              <div class="goods_spec">{{scope.row.goodsAttr}}</div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('evaluation.productCode')"
        prop="goodsSn"
      >
      </el-table-column>
      <el-table-column
        :label="$t('allGoods.allGoodsHeaderData.category')"
        prop="catName"
      >
      </el-table-column>
      <el-table-column
        :label="$t('allGoods.allGoodsData.shopPrice')"
        prop="shopPrice"
      >
      </el-table-column>
      <el-table-column
        :label="$t('allGoods.allGoodsData.goodsNumber')"
        prop="goodsNumber"
      >
      </el-table-column>
      <el-table-column
        :label="$t('evaluation.uv')"
        prop="uv"
      >
      </el-table-column>
      <el-table-column
        :label="$t('evaluation.pv')"
        prop="pv"
      >
      </el-table-column>
      <el-table-column
        :label="$t('evaluation.actualEvaluationNum')"
        prop="realCommNum"
      >
      </el-table-column>
      <el-table-column
        :label="$t('evaluation.addEvaluationNum')"
        prop="shopCommNum"
      >
      </el-table-column>
    </el-table>
    <el-form
      :model="sendParams"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      size="small"
    >
      <el-form-item
        :label="$t('evaluation.userName')"
        prop="bogusUsername"
      >
        <el-input
          v-model="sendParams.bogusUsername"
          class="default_width"
        ></el-input>
      </el-form-item>
      <el-form-item
        :label="$t('evaluation.avatar')"
        prop="bogusUserAvatar"
      >
        <div class="imgWrap">
          <div
            class="imgItem"
            v-if="sendParams.bogusUserAvatar"
          >
            <el-image
              style="width: 80px; height: 80px"
              :src="$imageHost+'/'+sendParams.bogusUserAvatar"
              fit="cover"
            >
            </el-image>
            <i
              class="el-icon-circle-close"
              @click="delImg('Avatar')"
            ></i>
          </div>
          <div
            class="imgItem"
            v-if="!sendParams.bogusUserAvatar"
            @click="addUserAvatar"
          >
            <el-image
              style="width: 80px; height: 80px"
              :src="`${$imageHost}/image/admin/add_img.png`"
              fit="scale-down"
            >
            </el-image>
          </div>
          <span class="tips">建议尺寸：200*200像素</span>

        </div>
      </el-form-item>
      <el-form-item
        :label="$t('evaluation.evaluationTime')"
        prop="createTime"
      >
        <el-date-picker
          v-model="sendParams.createTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item
        :label="$t('evaluation.grade')"
        prop="commstar"
      >
        <div class="commstar">
          <el-rate
            v-model="sendParams.commstar"
            :colors="{5:'#ff6666'}"
          ></el-rate>
        </div>

      </el-form-item>
      <el-form-item
        :label="$t('evaluation.experience')"
        prop="commNote"
      >
        <el-input
          type="textarea"
          resize="none"
          v-model.trim="sendParams.commNote"
          :rows="5"
          style="width:300px;"
        ></el-input>
      </el-form-item>
      <el-form-item label="晒单">
        <div class="imgWrap">
          <template v-for="(item,index) in sendParams.commImg">
            <div
              class="imgItem"
              v-if="sendParams.commImg.length"
              :key="index"
            >
              <el-image
                style="width: 80px; height: 80px"
                :src="$imageHost+'/'+item"
                fit="cover"
              >
              </el-image>
              <i
                class="el-icon-circle-close"
                @click="delImg('commImg',index)"
              ></i>
            </div>
          </template>
          <div
            class="imgItem"
            v-if="sendParams.commImg.length < 9"
            @click="addCommImg"
          >
            <el-image
              style="width: 80px; height: 80px"
              :src="`${$imageHost}/image/admin/add_img.png`"
              fit="scale-down"
            >
            </el-image>
          </div>
          <span class="tips">最多9张，建议尺寸：800*800像素</span>

        </div>
      </el-form-item>
      <el-form-item :label="$t('evaluation.anonymous')">
        <el-checkbox
          :label="$t('evaluation.yes')"
          v-model="sendParams.anonymousFlag"
        ></el-checkbox>
        <span class="tips">勾选时将不显示用户头像</span>
      </el-form-item>
    </el-form>
    <imageDialog
      pageIndex="pictureSpace"
      :tuneUp="showImageDialog"
      @handleSelectImg="getImgData"
    />
    <div class="footer">
      <el-button
        @click="Submit()"
        type="primary"
        size="small"
      >{{$t('marketCommon.save')}}</el-button>
    </div>
  </div>
</template>

<script>
import { goodsAddComment } from '@/api/admin/goodsManage/evaluationManagement/evaluationManagement'
export default {
  components: {
    imageDialog: () => import('@/components/admin/imageDalog')
  },
  data () {
    return {
      dataList: [this.goodsInfo],
      sendParams: {
        goodsId: this.goodsInfo.goodsId,
        bogusUsername: null,
        bogusUserAvatar: null,
        createTime: null,
        commstar: 5,
        commNote: null,
        anonymousFlag: false,
        commImg: []
      },
      triggerSource: null,
      showImageDialog: false
    }
  },
  mounted () {
    console.log(this.goodsInfo)
  },
  methods: {
    // 调起添加用户头像
    addUserAvatar () {
      this.showImageDialog = !this.showImageDialog
      this.triggerSource = 'Avatar'
    },
    // 调起添加评价图片
    addCommImg () {
      this.showImageDialog = !this.showImageDialog
      this.triggerSource = 'CommImg'
    },
    // 删除图片
    delImg (target, index) {
      if (target === 'Avatar') {
        this.sendParams.bogusUserAvatar = null
      } else {
        this.sendParams.commImg.splice(index, 1)
      }
    },
    // 获取图片
    getImgData (data) {
      console.log(data)
      if (this.triggerSource === 'Avatar') {
        this.sendParams.bogusUserAvatar = data.imgPath
      } else {
        this.sendParams.commImg.push(data.imgPath)
      }
    },
    // 添加商品评价
    Submit () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let obj = {
            ...this.sendParams,
            anonymousFlag: this.sendParams.anonymousFlag ? '1' : '0',
            commImg: this.sendParams.commImg.length ? this.sendParams.commImg.join(',') : '',
            commNote: this.sendParams.commNote || null
          }
          goodsAddComment(obj).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.$message.success({
                message: '添加成功',
                duration: '2000',
                onClose: () => {
                  this.$emit('update:target', 'first')
                }
              })
            }
          })
        } else {
          return false
        }
      })
    }
  },
  computed: {
    rules () {
      return {
        bogusUsername: { required: true, message: this.$t('evaluation.input', [this.$t('evaluation.userName')]), trigger: 'blur' },
        createTime: { required: true, message: this.$t('evaluation.select', [this.$t('evaluation.evaluationTime')]), trigger: 'change' },
        commNote: { required: true, message: this.$t('evaluation.input', [this.$t('evaluation.experience')]), trigger: 'blur' },
        commstar: { required: true, message: this.$t('evaluation.select', [this.$t('evaluation.grade')]), trigger: 'change' },
        bogusUserAvatar: { required: true, message: this.$t('evaluation.select', [this.$t('evaluation.avatar')]), trigger: 'change' }
      }
    }
  },
  props: {
    goodsInfo: {
      type: Object
    }
  }
}
</script>

<style lang="scss" scoped>
.goods_info {
  display: flex;
  > img {
    width: 60px;
    height: 60px;
    margin-right: 5px;
  }
  > .right_info {
    flex: 1;
    display: flex;
    flex-direction: column;
    text-align: left;
    justify-content: space-between;
    .goods_name {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      overflow: hidden;
      /*! autoprefixer: off */
      -webkit-box-orient: vertical;
      text-align: left;
      line-height: 1;
    }
  }
}
.imgWrap {
  display: flex;
  flex-wrap: wrap;
  margin-left: -8px;
  align-items: center;
  > .imgItem {
    border: 1px solid #999;
    width: 82px;
    height: 82px;
    margin-left: 8px;
    position: relative;
    > .el-icon-circle-close {
      position: absolute;
      font-size: 16px;
      right: -8px;
      top: -8px;
      cursor: pointer;
    }
  }
}
.default_width {
  width: 220px;
}
.tips {
  color: #999;
  margin-bottom: 0;
  margin-left: 10px;
}
.commstar {
  .el-rate {
    line-height: inherit;
    /deep/ .el-rate__icon {
      line-height: inherit;
    }
  }
}
.footer {
  position: fixed;
  bottom: 0;
  left: 160px;
  right: 10px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  z-index: 3;
}
</style>
