export const GET_POSTS = 'GET_POSTS';
export const GET_TAGS = 'GET_TAGS';

export const getPostsAC = (payload) => ({
  type: GET_POSTS,
  payload,
});
export const getTagsAC = (payload) => ({
  type: GET_TAGS,
  payload,
});
