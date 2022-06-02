import { PostsAPI } from '../api/api';
import { getPostsAC } from '../store/posts/actions';

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
