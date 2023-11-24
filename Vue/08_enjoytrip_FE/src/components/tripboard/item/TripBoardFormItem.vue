<script setup>
import { ref, watch, onMounted, defineComponent } from "vue";
import { useRoute, useRouter } from "vue-router";
import { modifyArticle, registArticle, detailArticle } from "@/api/tripboard";
import axios from "axios";
// hashtag위한 tagify
import Tagify from "@yaireo/tagify";
//quill
import { Quill, QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
//image upload resize
// import ImageUploader from "vue-image-upload-resize";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const handleImage = () => {
  const input = document.createElement("input");
  input.setAttribute("type", "file");
  input.setAttribute("accept", "image/*");
  input.click();
  input.onchange = async () => {
    const file = input.files[0];

    var bodyData = new FormData();
    var imageFile = file;
    bodyData.append("image", imageFile);
    axios
      .post(
        "https://api.imgbb.com/1/upload?key=14ee02bbc5be28bf666855596b1e051f",
        bodyData
      )
      .then((res) => {
        const url = res.data.data.display_url;
        const index = editor.value.getQuill().getSelection().index;
        editor.value.getQuill().setSelection(index, 1);
        editor.value.getQuill().insertEmbed(index, "image", url);
      })
      .catch((error) => {
        console.log(error);
      });
  };
};
var toolbarOptions = {
  container: [
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
  ], // Selector for toolbar container
  handlers: {
    image: handleImage,
  },
};

function editorChanged() {
  // console.log(editor.value);
  console.log(editor.value.getHTML());
}

//tagify 설정
var hashtagTagify;
var vehicletagTagify;
onMounted(() => {
  article.value.userId = userInfo.value.userId;
  const vehicletagInput = document.getElementById("vehicletag");
  const hashtagInput = document.getElementById("hashtag");
  hashtagTagify = new Tagify(hashtagInput);
  vehicletagTagify = new Tagify(vehicletagInput);
  vehicletagInput.addEventListener("change", onVehicleTagChange);
  hashtagInput.addEventListener("change", onHashTagChange);
});
function onHashTagChange(e) {
  console.log(e.target.value);
  // article.value.hashtag = e.target.value;
  hashtags.value = e.target.value;
}
function onVehicleTagChange(e) {
  console.log(e.target.value);
  // article.value.vehicletag = e.target.value;
  vehicletags.value = e.target.value;
}

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);
const uploadImage = ref(null);

const hashtags = ref([]);
const vehicletags = ref([]);
const editor = ref(null);

const article = ref({
  tripArticleId: 2,
  tags: null,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
  totalClover: 0,
  cost: 0,
  tripBoardFileDto: null,
});

//이미지 선택될 때 ref 변수에 넣어주기
function onSelectImage(event) {
  uploadImage.value = event.target.files[0];
  console.log(uploadImage.value);
}

if (props.type === "modify") {
  let { tripArticleId } = route.params;
  console.log(tripArticleId + "번글 얻어와서 수정할거야");
  // API 호출
  isUseId.value = true;
  article.value.tripArticleId = tripArticleId;
  detailArticle(
    tripArticleId,
    ({ data }) => {
      console.log(data);
      console.log("success");
      article.value = data;
      console.log(article.value.content);

      //받아온 데이터로 quill에 넣어주기
      editor.value.getQuill().pasteHTML(article.value.content);

      //받아온 데이터로 태그 입력해주기
      if (article.value.tags != null && article.value.tags != "") {
        console.log(article.value.tags);
        article.value.tags.forEach((tag) => {
          if (tag.tagType === 1) {
            vehicletagTagify.addTags(tag.tagName);
          } else {
            hashtagTagify.addTags(tag.tagName);
          }
        });
      }
    },
    (error) => {
      console.log(error);
    }
  );
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
// watch(
//   () => article.value.subject,
//   (value) => {
//     let len = value.length;
//     if (len == 0 || len > 30) {
//       subjectErrMsg.value = "제목을 확인해 주세요!!!";
//     } else subjectErrMsg.value = "";
//   },
//   { immediate: true }
// );
// watch(
//   () => article.value.content,
//   (value) => {
//     let len = value.length;
//     if (len == 0 || len > 500) {
//       contentErrMsg.value = "내용을 확인해 주세요!!!";
//     } else contentErrMsg.value = "";
//   },
//   { immediate: true }
// );

function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log(article.value.tripArticleId + "번글 입력하자!!", article.value);

  article.value.content = editor.value.getHTML();

  var tags = [];
  if (hashtags.value != null) {
    const parsedHashtags = JSON.parse(hashtags.value);
    const transformedHashtags = parsedHashtags.map((item) => ({
      tripArticleId: article.value.tripArticleId,
      tagName: item.value,
      tagType: 2,
    }));
    console.log(transformedHashtags);
    tags = tags.concat(transformedHashtags);
  }
  if (vehicletags.value != null) {
    const parsedVehicletags = JSON.parse(vehicletags.value);
    const transformedVehicletags = parsedVehicletags.map((item) => ({
      tripArticleId: article.value.tripArticleId,
      tagName: item.value,
      tagType: 1,
    }));
    console.log(transformedVehicletags);
    tags = tags.concat(transformedVehicletags);
  }
  article.value.tags = tags;
  console.log(article.value.tags);

  const formData = new FormData();
  formData.append("thumbnailImage", uploadImage.value);
  formData.append(
    "tripBoardDto",
    new Blob([JSON.stringify(article.value)], { type: "application/json" })
  );

  registArticle(
    formData,
    ({ data }) => {
      console.log("success");
      moveList();
    },
    (error) => {
      console.log(error);
    }
  );
}

function updateArticle() {
  console.log(article.value.tripArticleId + "번글 수정하자!!", article.value);

  article.value.content = editor.value.getHTML();

  var tags = [];
  if (hashtags.value != null) {
    const parsedHashtags = JSON.parse(hashtags.value);
    const transformedHashtags = parsedHashtags.map((item) => ({
      tripArticleId: article.value.tripArticleId,
      tagName: item.value,
      tagType: 2,
    }));
    console.log(transformedHashtags);
    tags = tags.concat(transformedHashtags);
  }
  if (vehicletags.value != null) {
    const parsedVehicletags = JSON.parse(vehicletags.value);
    const transformedVehicletags = parsedVehicletags.map((item) => ({
      tripArticleId: article.value.tripArticleId,
      tagName: item.value,
      tagType: 1,
    }));
    console.log(transformedVehicletags);
    tags = tags.concat(transformedVehicletags);
  }
  article.value.tags = tags;
  console.log(article.value.tags);

  const formData = new FormData();
  formData.append("thumbnailImage", uploadImage.value);
  formData.append(
    "tripBoardDto",
    new Blob([JSON.stringify(article.value)], { type: "application/json" })
  );

  // API 호출
  modifyArticle(
    formData,
    ({ data }) => {
      console.log("success");
      moveDetail();
    },
    (error) => {
      console.log(error);
    }
  );
}

function moveList() {
  router.push({ name: "trip-article-list" });
}

function moveDetail() {
  router.push({
    name: "trip-article-view",
    params: { tripArticleId: article.value.tripArticleId },
  });
}
</script>

<template>
  <form @submit.prevent="onSubmit" enctype="multipart/form-data">
    <div class="mb-3">
      <label for="upload-image"> 썸네일 업로드 : </label>
      <input type="file" @change="onSelectImage($event)" />
    </div>
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.userId"
        :disabled="true"
        :placeholder="userInfo.userId"
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.subject"
        placeholder="제목..."
      />
    </div>
    <!-- TODO: v-model 연동 -->
    <div class="mb-3">
      <label for="subject" class="form-label">총 경비 : </label>
      <input
        type="number"
        class="form-control"
        v-model="article.cost"
        placeholder="총 경비..."
      />
    </div>

    <div class="mb-3">
      <label for="vehicletag" class="form-label" @change="vchange"
        >교통수단 :
      </label>
      <input
        type="text"
        id="vehicletag"
        v-model="vehicletags"
        name="vehicletag"
        autocomplete
      />
    </div>
    <div class="mb-3">
      <label for="hashtag" class="form-label" @change="hchange"
        >해시태그 :
      </label>
      <input
        type="text"
        id="hashtag"
        v-model="hashtags"
        name="hashtag"
        autocomplete
      />
    </div>

    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <QuillEditor
        theme="snow"
        :toolbar="toolbarOptions"
        id="editor"
        @editor-change="editorChanged()"
        ref="editor"
        v-model:content="article.content"
      />
    </div>

    <div class="col-auto text-center">
      <button
        type="submit"
        class="btn btn-outline-primary mb-3"
        v-if="type === 'regist'"
      >
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>
        글수정
      </button>
      <button
        type="button"
        class="btn btn-outline-danger mb-3 ms-1"
        @click="moveList"
      >
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
