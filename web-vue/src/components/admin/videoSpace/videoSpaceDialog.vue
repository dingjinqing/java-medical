<template>
    <div class="video-dialog-container">
        <el-dialog
                :title="$t('videoSpace.upload.browseVideo')"
                :visible.sync="visible"
                width="825px"
                :append-to-body='true'
                @close="closeDialog"
        >
            <videoSpaceCore
                    :dialog-mode="true"
                    @video-click="videoClick"
            />
            <span
                    slot="footer"
                    class="dialog-footer"
            >
          <el-button @click="closeDialog">{{$t('videoSpace.upload.cancel')}}</el-button>
          <el-button
                  type="primary"
                  @click="closeDialog"
          >{{$t('videoSpace.upload.ok')}}</el-button>
        </span>
        </el-dialog>
    </div>
</template>
<script>
import videoSpaceCore from '@/components/admin/videoSpace/videoSpaceCore'
export default {
  components: { videoSpaceCore },
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    videoClick (item) {
      this.item = item
      this.closeDialog()
      this.$emit('video-click', item)
    },
    closeDialog () {
      this.$emit('update:visible', false)
    }
  }
}
</script>
<style scoped>
</style>
