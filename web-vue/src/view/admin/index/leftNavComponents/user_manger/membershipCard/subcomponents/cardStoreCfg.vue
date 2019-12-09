<template>
  <div class="card-store-div">
    <el-form
      :model="ruleForm"
      ref="ruleForm"
      label-width="100px"
    >
      <el-form-item
        class="card-store-item"
        :label="$t('memberCard.usingStore')"
        :rules="[{required: true}]"
      >
        <div class="store-top">
          <el-radio
            v-model="ruleForm.storeListType"
            label="0"
          >
            {{ $t('memberCard.allStores') }}
          </el-radio>
          <el-radio
            v-model="ruleForm.storeListType"
            label="1"
          >{{ $t('memberCard.partStores') }}</el-radio>
          <el-radio
            v-model="ruleForm.storeListType"
            label="-1"
          >{{ $t('memberCard.banStore') }}</el-radio>
        </div>
        <div class="store-tip">
          {{ $t('memberCard.storeConfig') }}
        </div>
        <div class="store-bottom">
          <div v-if="ruleForm.storeListType === '1'">
            <div
              v-if="ruleForm.choosedStore.length"
              class="store-table"
            >
              <el-table
                class="version-manage-table"
                header-row-class-name="table-class"
                :data="ruleForm.choosedStore"
                border
                style="width: 40%"
              >
                <el-table-column
                  :label="$t('memberCard.storeName')"
                  prop="storeName"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  :label="$t('memberCard.options')"
                  align="center"
                >
                  <template slot-scope="scope">
                    <span
                      @click="deleteCurrentStore(scope)"
                      class="store-delete"
                    >
                      {{ $t('memberCard.delete') }}
                    </span>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div
              class="store-add"
              @click="callChooseStoreDialog()"
            >
              <img :src="$imageHost+'/image/admin/icon_jia.png'">
              <span>{{ $t('memberCard.addStore') }}</span>
            </div>
          </div>
        </div>
        <div
          v-if="ruleForm.cardType===1 && ruleForm.storeListType !== '-1'"
          class="limit-card"
        >
          <div class="use-time">
            <span>允许适用时间</span>
            <el-checkbox>工作日 </el-checkbox>
            <el-checkbox>双休日 </el-checkbox>
          </div>
          <div class="use-num">
            <span>允许适用</span>
            <el-input-number
              size="small"
              :controls="false"
              :min="0"
              :max="999999999"
            >
            </el-input-number>
            <span>次</span>
          </div>
        </div>
      </el-form-item>
    </el-form>
    <!--添加门店弹窗-->
    <ChioseStoreDialog
      :tuneUpChooseStore="chooseStoreDialogVisiable"
      :storeBackData="ruleForm.choosedStore"
      @getChoosedStore="dealWithChooseStore"
    />
  </div>
</template>
<script>
export default {
  components: {
    ChioseStoreDialog: () => import('../chioseStoreDialog')
  },
  props: {
    val: {
      type: Object,
      require: true
    }
  },
  computed: {
    ruleForm: {
      get () {
        return this.val
      },
      set () {
        this.$emit('input', this.ruleForm)
      }
    }
  },
  watch: {
    'ruleForm': {
      handler (newName, oldName) {
        this.val = newName
        this.ruleForm = this.val
      },
      deep: true
    }
  },
  data () {
    return {
      chooseStoreDialogVisiable: false
    }
  },
  methods: {
    callChooseStoreDialog () {
      this.chooseStoreDialogVisiable = !this.chooseStoreDialogVisiable
      console.log(this.ruleForm.choosedStore)
    },
    dealWithChooseStore (data) {
      this.ruleForm.choosedStore = data.map(({ storeId, storeName }) => {
        return { storeId, storeName }
      })
      console.log('门店id', this.ruleForm.choosedStore)
    },
    deleteCurrentStore (row) {
      console.log(row.$index)
      let { $index } = row
      this.ruleForm.choosedStore.splice($index, 1)
      console.log(this.ruleForm.choosedStore)
    }
  }
}
</script>
<style scoped lang="scss">
.card-store-div {
  .card-store-item {
    padding-left: 100px;
    .store-tip {
      color: #9d9d9d;
      font-size: 13px;
    }
    .store-bottom {
      .store-table {
        /deep/ .table-class {
          th {
            padding: 0 !important;
            background-color: #f8f8f8;
            color: #333;
          }
        }
        .store-delete {
          color: #5a8bff;
          cursor: pointer;
        }
      }
      .store-add {
        margin-top: 10px;
        color: #5a8bff;
        width: 120px;
        text-align: center;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        height: 30px;
        line-height: 30px;
        padding-left: 5px;
        display: inline-block;
      }
    }
    .limit-card {
      .use-time {
        span {
          margin-right: 20px;
        }
      }
      .use-num {
        span {
          margin-right: 20px;
        }
        /deep/ .el-input {
          width: 90%;
        }
      }
    }
  }
}
</style>
