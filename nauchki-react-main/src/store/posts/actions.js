import { PostsAPI } from '../../api/api';

export const GET_POSTS = 'GET_POSTS';
export const GET_TAGS = 'GET_TAGS';

export const ADD_POST = 'ADMIN::ADD_POST';
export const EDIT_POST = 'ADMIN::EDIT_POST';
export const DELETE_POST = 'ADMIN::DELETE_POST';

export const POST_LOADING = 'ADMIN::POST_LOADING';
export const POST_ERROR = 'ADMIN::POST_ERROR';

const getPostsAC = (payload) => ({
  type: GET_POSTS,
  payload,
});
const getTagsAC = (payload) => ({
  type: GET_TAGS,
  payload,
});

export const addPost = (data) => ({
  type: ADD_POST,
  payload: data,
});

export const editPost = (id, data) => ({
  type: EDIT_POST,
  payload: data,
});

export const deletePost = (id) => ({
  type: DELETE_POST,
  payload: id,
});

export const setPostError = (error) => ({
  type: POST_ERROR,
  payload: error,
});

export const setLoadingPost = () => ({
  type: POST_LOADING,
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


export const addPostThunk = (formData) => (dispatch) => {
  try {
    dispatch(setLoadingPost);

    const data = PostsAPI.addPost(formData);

    if (data) {
      dispatch(addPost(data));
    }
  } catch (error) {
    dispatch(setPostError(error));
  }
};

export const removePostThunk = (id) => (dispatch) => {
  try {
    const data = PostsAPI.deletePost(id);
    console.log(data);
    dispatch(deletePost(id));

  } catch (error) {
    console.log('ошибка', error);
  }
};


// export const editPostThunk = (id) => (dispatch) => {
//   try {
//     console.log('delete');
//   } catch (error) {
//     console.log('ошибка', error);
//   }
// };
