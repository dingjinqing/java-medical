<template>
    <div class="rightsContainer">
        <el-form label-width="200px">
            <el-form-item>
                <el-checkbox >自定义权益</el-checkbox>
            </el-form-item>
            <el-form-item>
                <el-button size="small">自定义权益</el-button>
                <span class="rightTip">最多可添加10个自定义权益</span>
                <div class="content-container">
                    <div class="content">
                        <span class="content-item">标签1</span>
                        <i class="el-icon-edit-outline icon-style"></i>
                        <i class="el-icon-delete icon-style"></i>
                    </div>

                </div>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
export default {
  data () {
    return {
      showDialog: false
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

}
</style>
