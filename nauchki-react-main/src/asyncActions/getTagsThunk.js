import { PostsAPI } from "../api/api";
import { getTagsAC } from "../store/postsReducer";

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

