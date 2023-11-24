import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/qnaboard";
const comment_url = "/qnaboard/comment";

function listArticle(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailArticle(qnaArticleId, success, fail) {
  local.get(`${url}/${qnaArticleId}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local.post(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(qnaArticleId, success, fail) {
  local.get(`${url}/modify/${qnaArticleId}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(qnaArticleId, success, fail) {
  local.delete(`${url}/${qnaArticleId}`).then(success).catch(fail);
}

//댓글

function listArticleComment(qnaArticleId, success, fail) {
  local.get(`${comment_url}/${qnaArticleId}`).then(success).catch(fail);
}

function deleteArticleComment(qnaArticleCommentId, success, fail) {
  local
    .delete(`${comment_url}/${qnaArticleCommentId}`)
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
