<script>
import { ref } from "vue";
import { Quill, QuillEditor } from "@vueup/vue-quill";
import { localAxios } from "@/util/http-commons";
import ImageUploader from "quill-image-uploader";

export default {
  components: {
    QuillEditor,
  },
  setup: () => {
    const editor = ref(null);
    const local = localAxios();

    function onEditorChanged() {
      console.log(editor.value.getHTML());
    }

    const modules = {
      // name: "imageUploader",
      // module: ImageUploader,
      // options: {
      //   upload: (file) => {
      //     return new Promise((resolve, reject) => {
      //       const formData = new FormData();
      //       formData.append("image", file);
      //       console.log("in imageUploader");
      //       local
      //         .post("/tripboard/upload-image", formData, {
      //           headers: { "Content-Type": "multipart/form-data" },
      //         })
      //         .then((res) => {
      //           console.log(res);
      //           resolve(res.data.url);
      //         })
      //         .catch((err) => {
      //           reject("Upload failed");
      //           console.log("Error:", err);
      //         });
      //     });
      //   },
      // },
    };

    var toolbarOptions = [
      ["bold", "italic", "underline", "strike"], // toggled buttons
      ["blockquote", "code-block"],

      // [{ header: 1 }, { header: 2 }], // custom button values
      [{ list: "ordered" }, { list: "bullet" }],
      // [{ script: "sub" }, { script: "super" }], // superscript/subscript
      [{ indent: "-1" }, { indent: "+1" }], // outdent/indent
      [{ direction: "rtl" }], // text direction

      [{ size: ["small", false, "large", "huge"] }], // custom dropdown
      [{ header: [1, 2, 3, 4, 5, 6, false] }],

      [{ color: [] }, { background: [] }], // dropdown with defaults from theme
      [{ font: [] }],
      [{ align: [] }],

      ["link", "image"],

      ["clean"], // remove formatting button
    ];

    return { modules, toolbarOptions, onEditorChanged, editor };
  },
};
</script>

<template>
  <QuillEditor
    :toolbar="toolbarOptions"
    :modules="modules"
    ref="editor"
    id="editor"
    theme="snow"
    @editor-change="onEditorChanged()"
  />
</template>

<style scoped></style>
