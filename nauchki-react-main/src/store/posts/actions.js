import { PostsAPI } from '../../api/api';

export const GET_POSTS = 'GET_POSTS';
export const GET_TAGS = 'GET_TAGS';

const getPostsAC = (payload) => ({
  type: GET_POSTS,
  payload,
});
const getTagsAC = (payload) => ({
  type: GET_TAGS,
  payload,
});

export const getPostThunk = (tag) => {
  return async (dispatch) => {
    try {
      await PostsAPI.getPosts(tag).then((res) => {
        dispatch(getPostsAC(res.data));
      });
    } catch (error) {
      console.log(error);
    }
  };
};

export const getTagsThunk = () => {
  return async (dispatch) => {
    try {
      PostsAPI.getTags().then((res) => {
        dispatch(getTagsAC(res.data));
      });
    } catch (error) {
      console.log(error);
    }
  };
};
