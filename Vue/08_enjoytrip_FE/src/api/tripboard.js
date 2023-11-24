import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/tripboard";
const comment_url = "/tripboard/comment";

function listArticle(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailArticle(tripArticleId, success, fail) {
  local.get(`${url}/${tripArticleId}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("tripboard article", article);
  local
    .post(`${url}`, article, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(success)
    .catch(fail);
}

function getModifyArticle(tripArticleId, success, fail) {
  local.get(`${url}/modify/${tripArticleId}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  console.log("tripboard article", article);
  local
    .put(`${url}`, article, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(success)
    .catch(fail);
}

function deleteArticle(tripArticleId, success, fail) {
  local.delete(`${url}/${tripArticleId}`).then(success).catch(fail);
}

//댓글

function listArticleComment(tripArticleId, success, fail) {
  local.get(`${comment_url}/${tripArticleId}`).then(success).catch(fail);
}

function deleteArticleComment(tripArticleCommentId, success, fail) {
  local
    .delete(`${comment_url}/${tripArticleCommentId}`)
    .then(success)
    .catch(fail);
}

function modifyArticleComment(articlecomment, success, fail) {
  local
    .put(`${comment_url}`, JSON.stringify(articlecomment))
    .then(success)
    .catch(fail);
}

function registArticleComment(articlecomment, success, fail) {
  local
    .post(`${comment_url}`, JSON.stringify(articlecomment))
    .then(success)
    .catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  listArticleComment,
  deleteArticleComment,
  modifyArticleComment,
  registArticleComment,
};
