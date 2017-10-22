/**
 * Created by Administrator on 2017/8/9/009.
 */

var editor = function (id,uploadPath) {
    var E = window.wangEditor;
    var ue = new E(id);
    ue.customConfig.uploadImgMaxLength = 10
    ue.customConfig.uploadImgMaxSize = 4 * 1024 * 1024
    ue.customConfig.uploadFileName = 'file'
    ue.customConfig.uploadImgServer = uploadPath
    // ue.$textContainerElem["0"].clientHeight = 400
    ue.create()
    return ue
}
