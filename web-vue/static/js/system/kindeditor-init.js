(function () {
    window.initKindEditor = function (editor_id, ready_cb, text_mode) {
        KindEditor.plugin('my_image_upload', function (K) {
            var editor = this, name = 'my_image_upload';
            // 点击图标时执行
            editor.clickToolbar(name, function () {
                $.jImageManager({
                    max_img_num: 0,
                    ok_cb: function (img_arr) {
                        for (var i in img_arr) {
                            editor.insertHtml("<img src=\"" + img_arr[i].url + "\"  alt=\"\" />");
                        }
                    }
                });
            });
        });
        KindEditor.lang({
            my_image_upload: '图片空间'
        });

        var full_items =
            [
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|','fontsize',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'my_image_upload',
                'table', 'hr', 'emoticons', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about','fullscreen'
            ];
        var text_mode_items = [
            'undo', 'redo', '|', 'preview', 'print', 'cut', 'copy', 'paste',
            'plainpaste', 'selectall',
            'link', 'unlink', '|', 'about','fullscreen'
        ];

        var items = text_mode ? text_mode_items : full_items;

        KindEditor.ready(function (K) {
            window.keditor = K.create(editor_id,
                {
                    uploadJson: '/system/image/keditor/upload',
                    allowFileManager: false,
                    afterCreate: function () {
                    },
                    pasteType: 1,
                    autoHeightMode: false,
                    items: items,
                    resizeType:0
                });
            K.create('#my_image_upload_id', {
                items: ['my_image_upload']
            });
            if (ready_cb) {
                ready_cb();
            }
        });
    }

}())